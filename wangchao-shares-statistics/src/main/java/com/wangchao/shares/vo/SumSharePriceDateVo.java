package com.wangchao.shares.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SumSharePriceDateVo  implements Serializable {


    private Integer shareCount;

    private Date countData;


}
