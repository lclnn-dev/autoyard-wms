package com.autoyard.project.service;

import com.autoyard.project.domain.entity.ReceivingAct;

import java.util.List;
import java.util.UUID;

public interface ReceivingActService {

    List<ReceivingAct> findAll();

    ReceivingAct findById(UUID id);

    List<ReceivingAct> findAllByDocNumber(Integer docNumber);

    ReceivingAct add(ReceivingAct receivingAct);

    ReceivingAct update(ReceivingAct receivingAct);

    void deleteById(UUID id);
}
