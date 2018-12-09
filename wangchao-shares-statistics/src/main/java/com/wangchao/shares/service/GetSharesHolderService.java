package com.wangchao.shares.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangchao.shares.dao.ShareConfigDoMapper;
import com.wangchao.shares.dao.ShareHolderDoMapper;
import com.wangchao.shares.dao.SharesInfoDoMapper;
import com.wangchao.shares.dataobject.ShareConfigDo;
import com.wangchao.shares.dataobject.ShareHolderDo;
import com.wangchao.shares.util.HTTPUtils;
import com.wangchao.shares.vo.StockholderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        shareConfigDo.setKey(countDate);
        String result = shareConfigMapper.selectOne(shareConfigDo).getValue();

        List<String> sharesCode = sharesInfoDoMapper.findShareCodes();

        for (String code : sharesCode) {
            String url = "http://data.eastmoney.com/DataCenter_V3/gdfx/stockholder.ashx?code=" + code + "&date=" + result + "&type=Lt";
            String response=restTemplate.getForObject(url,String.class);
            JSONObject jsonObject=JSONObject.parseObject(response);
            String message=jsonObject.get("data").toString();
            List<StockholderVo> obj = JSONObject.parseArray(message, StockholderVo.class);

            for(StockholderVo stockholderVo:obj){
                ShareHolderDo shareHolderDo=new ShareHolderDo();
                shareHolderDo.setShareCode(stockholderVo.getSCODE());
                shareHolderDo.setShareName(stockholderVo.getSNAME());
                shareHolderDo.set
                stockholderVo.getSHAREHDCODE();
            }
        }

    }


}
