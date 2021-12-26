package com.derivatemeasure.portal.Model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Currency implements Serializable {

    private String isin;
    private Long wkn;
    private Double ask;
    private Integer askSize;
    private Double bid;
    private Integer bidSize;
    private Long timestamp;
    private String key;
}
