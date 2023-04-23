package com.autoyard.project.api.controller;

import com.autoyard.project.api.dto.response.CurrentStockBalanceResponse;
import com.autoyard.project.domain.entity.CurrentStockBalance;
import com.autoyard.project.mapper.CurrentStockBalanceMapper;
import com.autoyard.project.service.CurrentStockBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report/stock")
@RequiredArgsConstructor
public class CurrentStockBalanceController {

    private final CurrentStockBalanceService stockBalanceService;
    private final CurrentStockBalanceMapper stockBalanceMapper;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CurrentStockBalanceResponse> getAll() {
        List<CurrentStockBalance> stock = stockBalanceService.getAll();
        return stockBalanceMapper.toCurrentStockBalanceResponseList(stock);
    }

}
