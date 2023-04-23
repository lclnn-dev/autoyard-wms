package com.autoyard.project.domain.record;

import com.autoyard.project.domain.entity.CurrentStockBalance;
import com.autoyard.project.domain.entity.ReceivingAct;
import com.autoyard.project.domain.entity.VehicleTurnovers;
import com.autoyard.project.domain.enumeration.RecordType;
import com.autoyard.project.service.CurrentStockBalanceService;
import com.autoyard.project.service.VehicleTurnoversService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class ReceivingActRecord {

    private final CurrentStockBalanceService recordStockBalanceService;
    private final VehicleTurnoversService recordVehicleTurnoversService;

    public void add(ReceivingAct receivingAct) {
        clearRecords(receivingAct.getId());
        addRecordStockBalance(receivingAct);
        addRecordVehicleTurnovers(receivingAct);
    }

    public void clearRecords(UUID docId) {
        deleteRecordStockBalance(docId);
        deleteRecordVehicleTurnovers(docId);
    }

    private void addRecordStockBalance(ReceivingAct receivingAct) {
        CurrentStockBalance recordStockBalance = new CurrentStockBalance();
        recordStockBalance.setDateArrival(receivingAct.getDocDateTime());
        recordStockBalance.setVehicle(receivingAct.getVehicle());
        recordStockBalance.setCell(receivingAct.getCell());
        recordStockBalance.setRecorderId(receivingAct.getId());
        recordStockBalance.setQuantity(1);

        recordStockBalanceService.add(recordStockBalance);
    }

    private void addRecordVehicleTurnovers(ReceivingAct receivingAct) {
        VehicleTurnovers recordVehicleTurnovers = new VehicleTurnovers();
        recordVehicleTurnovers.setPeriod(receivingAct.getDocDateTime());
        recordVehicleTurnovers.setRecordType(RecordType.RECEIPT);
        recordVehicleTurnovers.setRecorderId(receivingAct.getId());
        recordVehicleTurnovers.setVehicle(receivingAct.getVehicle());
        recordVehicleTurnovers.setCell(receivingAct.getCell());
        recordVehicleTurnovers.setQuantity(1);

        recordVehicleTurnoversService.add(recordVehicleTurnovers);
    }

    private void deleteRecordStockBalance(UUID docId) {
        CurrentStockBalance existStockBalanceRecord = recordStockBalanceService.findByRecorderId(docId);
        if (existStockBalanceRecord != null) {
            recordStockBalanceService.deleteByRecorderId(docId);
        }
    }

    private void deleteRecordVehicleTurnovers(UUID docId) {
        VehicleTurnovers existVehicleTurnoversRecord = recordVehicleTurnoversService.findByRecorderId(docId);
        if (existVehicleTurnoversRecord != null) {
            recordVehicleTurnoversService.deleteByRecorderId(docId);
        }
    }
}
