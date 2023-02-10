package com.task.xm.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CryptoNormalizedRange implements Comparable<CryptoNormalizedRange>{
    private String cryptoName;
    private BigDecimal normalizedRange;

    @Override
    public int compareTo(CryptoNormalizedRange o) {
        return o.getNormalizedRange().compareTo(this.getNormalizedRange());
    }
}
