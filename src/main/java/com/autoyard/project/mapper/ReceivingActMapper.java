package com.autoyard.project.mapper;

import com.autoyard.project.api.dto.request.ReceivingActRequest;
import com.autoyard.project.api.dto.response.ReceivingActResponse;
import com.autoyard.project.domain.entity.ReceivingAct;
import com.autoyard.project.service.CellOfYardService;
import com.autoyard.project.service.ContractorService;
import com.autoyard.project.service.ReceivingActService;
import com.autoyard.project.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReceivingActMapper {

    private final ContractorService contractorService;
    private final VehicleService vehicleService;
    private final CellOfYardService cellService;
    private final ReceivingActService receivingActService;

    public List<ReceivingActResponse> toReceivingActResponseList(List<ReceivingAct> docs) {
        List<ReceivingActResponse> docsResponses = new ArrayList<>();
        for (ReceivingAct doc : docs) {
            docsResponses.add(toReceivingActResponse(doc));
        }
        return docsResponses;
    }

    public ReceivingActResponse toReceivingActResponse(ReceivingAct doc) {
        var docResponse = new ReceivingActResponse();
        docResponse.setId(doc.getId());
        docResponse.setDocNumber(doc.getDocNumber());
        docResponse.setDocDateTime(doc.getDocDateTime());
        docResponse.setContractorId(doc.getContractor().getId());
        docResponse.setVehicleId(doc.getVehicle().getId());
        docResponse.setCellId(doc.getCell().getId());
        docResponse.setGrossWeight(doc.getGrossWeight());

        return docResponse;
    }

    @Transactional
    public ReceivingAct toReceivingActEntity(ReceivingActRequest docRequest) {
        ReceivingAct docEntity = new ReceivingAct();
        docEntity.setDocDateTime(docRequest.getDocDateTime());
        docEntity.setContractor(contractorService.findById(docRequest.getContractorId()));
        docEntity.setVehicle(vehicleService.findById(docRequest.getVehicleId()));
        docEntity.setCell(cellService.findById(docRequest.getCellId()));
        docEntity.setGrossWeight(docRequest.getGrossWeight());

        return docEntity;
    }

    @Transactional
    public ReceivingAct toReceivingActEntity(ReceivingActRequest docRequest, UUID id) {
        ReceivingAct foundDocEntity = receivingActService.findById(id);
        foundDocEntity.setDocDateTime(docRequest.getDocDateTime());
        foundDocEntity.setContractor(contractorService.findById(docRequest.getContractorId()));
        foundDocEntity.setVehicle(vehicleService.findById(docRequest.getVehicleId()));
        foundDocEntity.setCell(cellService.findById(docRequest.getCellId()));
        foundDocEntity.setGrossWeight(docRequest.getGrossWeight());

        return foundDocEntity;
    }
}
