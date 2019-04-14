package com.wangchao.shares.service;


import com.alibaba.fastjson.JSON;
import com.wangchao.shares.mapper.SharesInfoDoMapper;
import com.wangchao.shares.mapper.SharesPriceConfigDoMapper;
import com.wangchao.shares.mapper.SharesPriceCountDoMapper;
import com.wangchao.shares.dataobject.SharesPriceConfigDo;
import com.wangchao.shares.dataobject.SharesPriceCountDo;
import com.wangchao.shares.util.DateUtil;
import com.wangchao.shares.vo.SharesInfoReq;
import com.wangchao.shares.vo.SumSharePriceDateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class GetSharesPriceCountService {

    @Autowired
    private SharesPriceConfigDoMapper sharesPriceConfigMapper;

    @Autowired
    private SharesPriceCountDoMapper sharesPriceCountMapper;

    @Autowired
    private SharesInfoDoMapper sharesInfoDoMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public void sumPriceSection() throws Exception{

        SharesPriceConfigDo sharesPriceConfigDo = new SharesPriceConfigDo();
        sharesPriceConfigDo.setIsDelete(0);
        List<SharesPriceConfigDo> sharesPriceConfigDos = sharesPriceConfigMapper.select(sharesPriceConfigDo);
        if(CollectionUtils.isEmpty(sharesPriceConfigDos)){
            return;
        }


        List<SharesPriceCountDo> sharesPriceCountDos=new LinkedList<>();

        for(SharesPriceConfigDo sharesPriceConfigDo1:sharesPriceConfigDos){
            SharesInfoReq sharesInfoReq=new SharesInfoReq();
            sharesInfoReq.setMaxPrice(sharesPriceConfigDo1.getMaxPrice());
            sharesInfoReq.setMinPrice(sharesPriceConfigDo1.getMinPrice());
            if(!"all".equals(sharesPriceConfigDo1.getSharesType())){
                sharesInfoReq.setType(Integer.valueOf(sharesPriceConfigDo1.getSharesType()));
            }
            sharesInfoReq.setCountData(DateUtil.format(new Date(),DateUtil.webFormat));
            List<SumSharePriceDateVo> sumSharePriceDateVos=sharesInfoDoMapper.countSumByPriceAndType(sharesInfoReq);

            for(SumSharePriceDateVo sumSharePriceDateVo:sumSharePriceDateVos){
                SharesPriceCountDo sharesPriceCountDo=new SharesPriceCountDo();
                sharesPriceCountDo.setCountPrice(sumSharePriceDateVo.getShareCount());
                sharesPriceCountDo.setPriceMax(sharesPriceConfigDo1.getMaxPrice());
                sharesPriceCountDo.setPriceMin(sharesPriceConfigDo1.getMinPrice());
                sharesPriceCountDo.setSumCount(sharesInfoDoMapper.countSumShaInfos(DateUtil.format(sumSharePriceDateVo.getCountData(),DateUtil.webFormat)));
                sharesPriceCountDo.setStatisticsTime(sumSharePriceDateVo.getCountData());
                BigDecimal value = new BigDecimal(((float)sumSharePriceDateVo.getShareCount()/sharesPriceCountDo.getSumCount())*100).setScale(2, BigDecimal.ROUND_CEILING);
                sharesPriceCountDo.setPercent(value);
                sharesPriceCountDo.setCreateTime(new Date());
                sharesPriceCountDos.add(sharesPriceCountDo);
            }


        }
        logger.warn(JSON.toJSONString(sharesPriceCountDos));
        sharesPriceCountMapper.insertList(sharesPriceCountDos);

    }


}
