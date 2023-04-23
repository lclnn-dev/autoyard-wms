package com.autoyard.project.service.impl;

import com.autoyard.project.api.exception.CustomEntityNotFoundException;
import com.autoyard.project.domain.entity.ReceivingAct;
import com.autoyard.project.domain.record.ReceivingActRecord;
import com.autoyard.project.repository.ReceivingActRepository;
import com.autoyard.project.service.ReceivingActService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReceivingActServiceImpl implements ReceivingActService {

    private final ReceivingActRepository receivingActRepository;
    private final ReceivingActRecord docRecord;

    @Override
    @Transactional(readOnly = true)
    public List<ReceivingAct> findAll() {
        return receivingActRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ReceivingAct findById(UUID id) {
        return receivingActRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(ReceivingAct.class, "id", id.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReceivingAct> findAllByDocNumber(Integer docNumber) {
        return receivingActRepository.findAllByDocNumber(docNumber);
    }

    @Override
    public ReceivingAct add(ReceivingAct receivingAct) {
        ReceivingAct addedDoc = receivingActRepository.save(receivingAct);
        docRecord.add(addedDoc);
        return addedDoc;
    }

    @Override
    public ReceivingAct update(ReceivingAct receivingAct) {
        UUID id = receivingAct.getId();
        ReceivingAct foundDoc = receivingActRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(ReceivingAct.class, "id", id.toString()));

        foundDoc.setDocNumber(receivingAct.getDocNumber());
        foundDoc.setDocDateTime(receivingAct.getDocDateTime());
        foundDoc.setContractor(receivingAct.getContractor());
        foundDoc.setCell(receivingAct.getCell());
        foundDoc.setGrossWeight(receivingAct.getGrossWeight());
        foundDoc.setVehicle(receivingAct.getVehicle());

        docRecord.add(foundDoc);
        return receivingActRepository.save(foundDoc);
    }

    @Override
    public void deleteById(UUID id) {
        receivingActRepository.findById(id).ifPresentOrElse(
                doc -> {
                    docRecord.clearRecords(id);
                    receivingActRepository.deleteById(id);
                },
                () -> {
                    throw new CustomEntityNotFoundException(ReceivingAct.class, "id", id.toString());
                }
        );
    }
}
