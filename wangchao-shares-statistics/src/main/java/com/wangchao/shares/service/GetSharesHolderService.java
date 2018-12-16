package com.wangchao.shares.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangchao.shares.dao.ShareConfigDoMapper;
import com.wangchao.shares.dao.ShareHolderDoMapper;
import com.wangchao.shares.dao.SharesInfoDoMapper;
import com.wangchao.shares.dataobject.ShareConfigDo;
import com.wangchao.shares.dataobject.ShareHolderDo;
import com.wangchao.shares.util.DateUtil;
import com.wangchao.shares.vo.StockholderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class GetSharesHolderService {


    @Autowired
    private ShareConfigDoMapper shareConfigMapper;

    @Autowired
    private ShareHolderDoMapper shareHolderMapper;

    @Autowired
    private SharesInfoDoMapper sharesInfoDoMapper;

    @Autowired
    private RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private static final String countDate = "share_holder_data";

    public void getSharesHolder() throws Exception{
        ShareConfigDo shareConfigDo = new ShareConfigDo();
        shareConfigDo.setIsDelete(0);
        shareConfigDo.setConfigKey(countDate);
        String result = shareConfigMapper.selectOne(shareConfigDo).getConfigValue();

        List<String> sharesCode = sharesInfoDoMapper.findShareCodes();

        List<ShareHolderDo> shareHolderDos=new LinkedList<>();
        for (String code : sharesCode) {
            ShareHolderDo shareHolderDo1 = new ShareHolderDo();
            shareHolderDo1.setCountData(DateUtil.parseDate(result,DateUtil.webFormat));
            shareHolderDo1.setShareCode(code);
            int count=shareHolderMapper.selectCount(shareHolderDo1);
            if(count>0){
                continue;
            }
            String url = "http://data.eastmoney.com/DataCenter_V3/gdfx/stockholder.ashx?code=" + code + "&date=" + result + "&type=Lt";
            String response=restTemplate.getForObject(url,String.class);
            JSONObject jsonObject=JSONObject.parseObject(response);
            String message=jsonObject.get("data").toString();
            logger.warn("股东数据位"+ message);
            List<StockholderVo> obj = JSONObject.parseArray(message, StockholderVo.class);


            for(StockholderVo stockholderVo:obj){
                logger.warn("当前数据位"+ JSON.toJSONString(stockholderVo));
                ShareHolderDo shareHolderDo=new ShareHolderDo();
                shareHolderDo.setShareCode(stockholderVo.getSCODE());
                shareHolderDo.setShareName(stockholderVo.getSNAME());
                shareHolderDo.setShareHolderName(stockholderVo.getSHAREHDNAME());
                shareHolderDo.setShareHolderType(stockholderVo.getSHAREHDTYPE());
                shareHolderDo.setShareHolderAmount(stockholderVo.getSHAREHDNUM());
                shareHolderDo.setShareHolderBaifenbi(stockholderVo.getZB().multiply(new BigDecimal(100)).setScale(3, RoundingMode.HALF_UP));
                if(!"不变".equals(stockholderVo.getBZ())&&!"新进".equals(stockholderVo.getBZ())) {
                    shareHolderDo.setChangeBaifenbi(new BigDecimal(stockholderVo.getBDBL()).multiply(new BigDecimal(100)).setScale(3, RoundingMode.HALF_UP));
                }
                shareHolderDo.setCountData(DateUtil.parseDate(result,DateUtil.webFormat));
                shareHolderDo.setShareType(stockholderVo.getSHARESTYPE());
                shareHolderDo.setCreateData(new Date());
                try {
                    shareHolderMapper.insert(shareHolderDo);
                }catch (DuplicateKeyException e){
                    logger.error("share_holder 唯一约束失效！", e);
                }

                try{
                   Thread.sleep(500);
                }catch (Exception e){
                  logger.error("线程休眠",e);
                }
            }


        }

    }


}
