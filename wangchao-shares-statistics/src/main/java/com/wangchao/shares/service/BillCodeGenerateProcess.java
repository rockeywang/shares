package com.wangchao.shares.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangchao.shares.config.RedisLock;
import com.wangchao.shares.mapper2.WaybillSectionDoMapper;
import com.wangchao.shares.util.StatusConstants;
import com.wangchao.shares.vo.WaybillBufferVo;
import com.wangchao.shares.vo.WaybillSectionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class BillCodeGenerateProcess extends Thread {



    private int billCodeType = 1100;

    private boolean exitFlag = false;

    private final static String redisKey="UBS_Master_lockKey";

    /** 缓存最低值 */
    private int cacheMinNum = 5000;

    /** 缓存最高值 */
    private int cacheMaxNum = 10000;

    /** 等待时间 60秒 */
    private long waitTime = 60;

    private int lockTime = 1;
    private int lockWaitTimeSeconds = 3;
    //系统启动时锁时间
    private String bootLockTime="10";

    @Autowired
    private RedisLock redisLock;

    private static HashMap<Integer, BillCodeGenerateProcess> waybillUtilMap = new HashMap<Integer, BillCodeGenerateProcess>();

    @Autowired
    private WaybillSectionList waybillSectionList;

    @Autowired
    private WaybillCodeQueue waybillCodeQueue;

    @Autowired
    private WaybillSectionDoMapper waybillSectionDao;



    public static BillCodeGenerateProcess getInstance(int billCodeType) {
        return waybillUtilMap.get(billCodeType);
    }
    //放入单号
    public  void putBillCode(String billCode){
        WaybillBufferVo buffer = new WaybillBufferVo();
        buffer.setBillCode(billCode);
        buffer.setBillCodeType(this.billCodeType);
        String wayBillBuffer = JSONObject
                .toJSONString(buffer);
        //list.add(wayBillBuffer);
        waybillCodeQueue.in(wayBillBuffer);
    }


    public void init() {
        //log.warn("【电子面单号段管理】【启动】，单号类型：" + billCodeType);
        log.warn("【电子面单号段管理】【启动】，单号类型：" + billCodeType + "开始获取号段锁");
       // lock = redissonClient.getFairLock(redisKey);
        try {
            redisLock.lock(redisKey,bootLockTime);
            waybillUtilMap.put(billCodeType, this);
            WaybillSectionVo sectionSearch = new WaybillSectionVo();
            // 未完成,处理中
            sectionSearch.setFinishFlag(false);
            sectionSearch.setStatus(StatusConstants.PROCESSING);
            sectionSearch.setBillCodeType(billCodeType);
            List<WaybillSectionVo> sectionList = waybillSectionDao
                    .findByCondition(sectionSearch);
            if (sectionList != null && !sectionList.isEmpty()) {
                for (WaybillSectionVo section : sectionList) {
                    // 当是处理中时的记录，直接跳过一次，防止重复单号的产生
                    section.setGenerateSumQty(section.getGenerateSumQty()
                            + section.getGenerateQty());
                    section.setGenerateEndNo(section.getGenerateEndNo()
                            + section.getGenerateSumQty());
                    if (section.getEndNo().equals(section.getGenerateEndNo())) {
                        section.setFinishFlag(true);
                        section.setStatus(StatusConstants.PROCESS_END);
                    } else {
                        section.setStatus(StatusConstants.PROCESS_SUCCESS);
                    }
                    waybillSectionDao.updateStatusById(section);
                }
            }
            waybillSectionList.trim(1, 0);// 重启清空redis中号段
            sectionList = waybillSectionDao
                    .findByUnfinishSection(billCodeType);
            if (sectionList != null && !sectionList.isEmpty()) {
                // 将号段放入redis中
                for (WaybillSectionVo waybillSectionDto : sectionList) {
                    String sectionListJsonStr = JSON
                            .toJSONString(waybillSectionDto);
                    waybillSectionList.in(sectionListJsonStr);
                }
            }

        } finally {
            redisLock.unlock(redisKey,bootLockTime);
        }
        this.start();
    }

    @Override
    public void run() {
        // 初始完毕
        boolean errorFlag = false;
        while (!exitFlag) {
            try {
                String sectionStr = "";
                if (errorFlag) {
                    errorFlag = false;
                    sleepNow(waitTime * 1000);
                    continue;
                } else if (waybillCodeQueue.length() >= cacheMinNum) {
                    sleepNow(waitTime * 1000);
                    continue;
                } else {
                    long generateCount = 0;
                    do {
                        redisLock.lock(redisKey,bootLockTime);
                        if (waybillCodeQueue.length() >= cacheMaxNum) {
                            redisLock.unlock(redisKey,bootLockTime);
                            sleepNow(waitTime * 1000);
                            continue;
                        }
                        try {
                            long productStart = System.currentTimeMillis();
                            if (this.waybillSectionList == null
                                    || this.waybillSectionList.length() <= 0) {
                                if (!reFreshWaybillSectionToCache()) {
                                    errorFlag = true;
                                    sleepNow(waitTime * 1000);
                                    break;
                                }
                            }
                            WaybillSectionVo section = null;
                            List<WaybillSectionVo> sectionDtos = waybillSectionList
                                    .getWaybillSections();
                            for (WaybillSectionVo waybillSectionDto : sectionDtos) {
                                if (!waybillSectionDto.getFinishFlag()) {
                                    section = waybillSectionDto;
                                    sectionStr = JSON.toJSONString(section);
                                    break;
                                }
                            }
                            if (section == null) {
                                log.warn("无可用号段!");
                                continue;
                            }
                            // startNo 1110001
                            // endNo 1119999
                            // generateStartNo 1111500
                            // generateEndNo 1111599
                            // qty 100
                            // 更新号段状态为处理中
                            String startNo = section.getStartNo();
                            String endNo = section.getEndNo();
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < startNo.length(); i++) {
                                if (startNo.charAt(i) != endNo.charAt(i)) {
                                    break;
                                }
                                sb.append(startNo.charAt(i));
                            }
                            String prefix = sb.toString(); // 111
                            String startPart = null;
                            String endPart = endNo.substring(prefix.length()); // 9999
                            long startNum = 0;
                            if (section.getGenerateEndNo() != null) {
                                // 如果是正常结束的,取上次产生的结束单号加1
                                if (section.getStatus() !=
                                        StatusConstants.PROCESS_FAIL) {
                                    startPart =
                                            section.getGenerateEndNo().substring(prefix.length());
                                    startNum = Long.parseLong(startPart) + 1; //

                                } else { // 如果是非正常结束的,则取上次产生的开始单号
                                    startPart = section.getGenerateStartNo()
                                            .substring(prefix.length());
                                    startNum = Long.parseLong(startPart); // 1500
                                }
                            } else {
                                startPart = startNo.substring(prefix.length());
                                startNum = Long.parseLong(startPart); // 0001
                            }
                            long endNum = startNum
                                    + section.getPerGenerateQty() - 1; // 1599
                            long endNum2 = Long.parseLong(endPart); // 9999
                            if (endNum > endNum2) {
                                endNum = endNum2;
                            }
                            section.setGenerateQty(endNum - startNum + 1); // 1599
                            // -
                            // 1500
                            // +
                            // 1
                            int suffixLength = startPart.length(); // 4
                            String suffixStr = "";
                            for (int i = 0; i < suffixLength; i++) {
                                suffixStr += "0";
                            } // 0000
                            DecimalFormat df = new DecimalFormat(suffixStr);
                            section.setGenerateStartNo(prefix
                                    + df.format(startNum)); // 111 + 1500
                            section.setGenerateEndNo(prefix + df.format(endNum)); // 111
                            // +
                            // 1599
                            section.setStatus(StatusConstants.PROCESSING);
                            int result = waybillSectionDao.updateGenerateInfo(section);
                            if (0==result) {
                                sleepNow(waitTime * 1000);
                                continue;
                            }
                            waybillSectionList.set(0,
                                    JSON.toJSONString(section));
                            List<String> list = new ArrayList<String>();
                            WaybillBufferVo buffer = null;
                            for (long i = startNum; i <= endNum; i++) {
                                buffer = new WaybillBufferVo();
                                buffer.setBillCodeType(section
                                        .getBillCodeType());
                                buffer.setSectionId(section.getId());
                                buffer.setStatus(StatusConstants.UNPROCESSED);
                                buffer.setCreateTime(new Date());
                                buffer.setBillCode(prefix + df.format(i));
                                String wayBillBuffer = JSONObject
                                        .toJSONString(buffer);
                                list.add(wayBillBuffer);
                            }
                            String[] wayBillArray = list.toArray(new String[list.size()]);
                            waybillCodeQueue.inAll(wayBillArray);

                            log.warn("【电子面单号段管理】产生单号：startNo:"  + prefix + startNum + ","  + prefix + endNum);
                            generateCount = list.size() + generateCount;
                            long cacheCode = System.currentTimeMillis()
                                    - productStart;
                            log.warn("【电子面单号段管理】【产生单号预产生单号到redis完成】耗时时间ubs："
                                    + cacheCode + "当前队列大小:"
                                    + waybillCodeQueue.length());
                            // 更新号段状态为已成功
                            section.setGenerateSumQty(section
                                    .getGenerateSumQty()
                                    + section.getGenerateQty());
                            if (section.getEndNo().equals(
                                    section.getGenerateEndNo())) {
                                section.setFinishFlag(true);
                                section.setStatus(StatusConstants.PROCESS_END);
                            } else {
                                section.setStatus(StatusConstants.PROCESS_SUCCESS);
                            }
                            waybillSectionDao.updateStatusById(section);
                            if (section.getFinishFlag()) {
                                waybillSectionList.remove(0, sectionStr);
                            } else {
                                waybillSectionList.set(0,
                                        JSON.toJSONString(section));// 刷新缓存
                            }
                            long productCost = System.currentTimeMillis()
                                    - productStart;
                            log.warn("【电子面单号段管理】【产生单号预产生单号完成】耗时时间："
                                    + productCost + "当前队列大小:"
                                    + waybillCodeQueue.length());
                        } finally {
                            //waybillSectionLock.unlock();
                            redisLock.unlock(redisKey,bootLockTime);
                        }
                    } while (generateCount < cacheMaxNum);
                }
            } catch (Exception e) {
                errorFlag = true;
                log.error("【电子面单号段管理】异常", e);
            }
        }
    }

    private void sleepNow(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }

    private boolean reFreshWaybillSectionToCache() throws Exception {
        List<WaybillSectionVo> sectionList = waybillSectionDao
                .findByUnfinishSection(billCodeType);
        if (sectionList != null && !sectionList.isEmpty()) {
            for (WaybillSectionVo waybillSectionDto : sectionList) {
                String sectionJsonStr = JSON.toJSONString(waybillSectionDto);
                waybillSectionList.in(sectionJsonStr);
                log.warn("【号段管理】刷新缓存号段成功：" + sectionJsonStr);
            }
            return true;
        } else {
            log.warn("【号段管理】刷新缓存号段失败,没有可用号段!");
            return false;
        }
    }


}
