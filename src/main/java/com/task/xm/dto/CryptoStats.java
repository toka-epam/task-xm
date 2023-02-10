package com.task.xm.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CryptoStats {
    private BigDecimal oldestPrice;
    private BigDecimal newestPrice;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
}
