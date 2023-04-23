package com.autoyard.project.mapper;

import com.autoyard.project.api.dto.response.CurrentStockBalanceResponse;
import com.autoyard.project.domain.entity.CurrentStockBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CurrentStockBalanceMapper {

    public List<CurrentStockBalanceResponse> toCurrentStockBalanceResponseList(List<CurrentStockBalance> stockList) {
        List<CurrentStockBalanceResponse> stockResponses = new ArrayList<>();
        for (CurrentStockBalance stock : stockList) {
            stockResponses.add(toCurrentStockBalanceResponse(stock));
        }
        return stockResponses;
    }

    private CurrentStockBalanceResponse toCurrentStockBalanceResponse(CurrentStockBalance stock) {
        var stockResponse = new CurrentStockBalanceResponse();
        stockResponse.setDateArrival(stock.getDateArrival());
        stockResponse.setRecorderId(stock.getRecorderId());
        stockResponse.setVehicleId(stock.getVehicle().getId());
        stockResponse.setCellId(stock.getCell().getId());

        return stockResponse;
    }
}
