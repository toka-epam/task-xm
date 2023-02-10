package com.task.xm.controller;

import com.task.xm.dto.CryptoNormalizedRange;
import com.task.xm.dto.CryptoStats;
import com.task.xm.service.CryptoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("crypto-stats")
@Tag(name = "api for crypto analysis")
public class CryptoController {
    private final CryptoService cryptoService;
    Logger logger = LoggerFactory.getLogger(CryptoController.class);

    @GetMapping("/normalized-range")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get normalized ranges for all crypto")
    public List<CryptoNormalizedRange> cryptoNormalizedRangeList(){
        return cryptoService.getNormalizedRanges();
    }

    @GetMapping("/{crypto-name}")
    @Operation(summary = "get crypto stats", description = "get min/max price and oldest/newest price")
    public List<CryptoStats> getCryptoStats(@Parameter(description = "crypto name (ex: eth)") @PathVariable("crypto-name") String cryptoName){
        return  cryptoService.getCryptoStats(cryptoName);
    }

}
