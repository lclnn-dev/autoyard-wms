package com.autoyard.project.api.controller;

import com.autoyard.project.api.dto.request.ReceivingActRequest;
import com.autoyard.project.api.dto.response.ReceivingActResponse;
import com.autoyard.project.domain.entity.ReceivingAct;
import com.autoyard.project.mapper.ReceivingActMapper;
import com.autoyard.project.service.ReceivingActService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/receiving_acts")
@RequiredArgsConstructor
public class ReceivingActController {

    private final ReceivingActService receivingActService;
    private final ReceivingActMapper receivingActMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReceivingActResponse> getAllReceivingActs() {
        List<ReceivingAct> docs = receivingActService.findAll();
        return receivingActMapper.toReceivingActResponseList(docs);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReceivingActResponse getReceivingActById(@PathVariable("id") UUID id) {
        ReceivingAct foundDoc = receivingActService.findById(id);
        return receivingActMapper.toReceivingActResponse(foundDoc);
    }

    @GetMapping("/doc-number/{docNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReceivingActResponse> getReceivingActByDocNumber(@PathVariable("docNumber") Integer docNumber) {
        List<ReceivingAct> foundDocs = receivingActService.findAllByDocNumber(docNumber);
        return receivingActMapper.toReceivingActResponseList(foundDocs);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReceivingActResponse createReceivingAct(@RequestBody ReceivingActRequest docRequest) {
        ReceivingAct docEntity = receivingActMapper.toReceivingActEntity(docRequest);
        ReceivingAct savedDocEntity = receivingActService.add(docEntity);

        return receivingActMapper.toReceivingActResponse(savedDocEntity);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReceivingActResponse updateReceivingAct(@PathVariable("id") UUID id, @RequestBody ReceivingActRequest docRequest) {
        ReceivingAct docEntity = receivingActMapper.toReceivingActEntity(docRequest);
        docEntity.setId(id);
        ReceivingAct updatedDocEntity = receivingActService.update(docEntity);

        return receivingActMapper.toReceivingActResponse(updatedDocEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReceivingAct(@PathVariable UUID id) {
        receivingActService.deleteById(id);
    }
}
