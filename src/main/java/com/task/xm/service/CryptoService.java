package com.task.xm.service;

import com.task.xm.dto.CryptoNormalizedRange;
import com.task.xm.dto.CryptoStats;
import com.task.xm.model.CryptoData;
import com.task.xm.repository.CryptoDataRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoService {

    Logger logger = LoggerFactory.getLogger(CryptoService.class);
    private final CryptoDataRepository cryptoDataRepository;

    public List<CryptoNormalizedRange> getNormalizedRanges(){

        List<CryptoNormalizedRange> cryptoNormalizedRanges = new LinkedList<>();
        List<String> cryptoNames = cryptoDataRepository.findDistinctName();

        for(String cryptoName : cryptoNames){
            cryptoNormalizedRanges.add(calculateNormalizedRange(cryptoName));
        }

        Collections.sort(cryptoNormalizedRanges);
        logger.info("normalized ranges calculated");
        return cryptoNormalizedRanges;
    }

    private CryptoNormalizedRange calculateNormalizedRange(String cryptoName){
        List<BigDecimal> cryptoPriceHistory = cryptoDataRepository.findByNameIgnoreCase(cryptoName)
                .stream().map(CryptoData::getPrice).toList();

        BigDecimal max = Collections.max(cryptoPriceHistory);
        BigDecimal min = Collections.min(cryptoPriceHistory);

        BigDecimal normalizedRange = max.subtract(min).divide(min, RoundingMode.HALF_EVEN);

        return CryptoNormalizedRange.builder()
                .cryptoName(cryptoName)
                .normalizedRange(normalizedRange)
                .build();
    }


    public List<CryptoStats> getCryptoStats(String cryptoName) {

        List<String> cryptoNames = cryptoDataRepository.findDistinctName();
        if(!cryptoNames.contains(cryptoName.toUpperCase())){
            logger.error("invalid crypto name for crypto stats");
            throw new IllegalArgumentException(cryptoName + " crypto does not exist");
        }

        BigDecimal maxPrice = cryptoDataRepository.findTopByNameOrderByPriceDesc(cryptoName).getPrice();
        BigDecimal minPrice = cryptoDataRepository.findTopByNameOrderByPriceAsc(cryptoName).getPrice();
        BigDecimal oldestPrice = cryptoDataRepository.findTopByNameOrderByDateAsc(cryptoName).getPrice();
        BigDecimal newestPrice = cryptoDataRepository.findTopByNameOrderByDateDesc(cryptoName).getPrice();

        List<CryptoStats> cryptoStatsList = new LinkedList<>();
        CryptoStats stats = CryptoStats.builder().maxPrice(maxPrice)
                .minPrice(minPrice)
                .oldestPrice(oldestPrice)
                .newestPrice(newestPrice)
                .build();
        cryptoStatsList.add(stats);
        logger.info("crypto stats calculated");
        return cryptoStatsList;
    }
}
