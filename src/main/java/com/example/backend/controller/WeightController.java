package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.example.backend.api.WeightAPI;
import com.example.backend.api.model.CreateRequest;
import com.example.backend.api.model.CreateUpdateResponse;
import com.example.backend.api.model.DeleteRequest;
import com.example.backend.api.model.DeleteResponse;
import com.example.backend.api.model.GetWeightResponse;
import com.example.backend.api.model.GetWeightsResponse;
import com.example.backend.api.model.UpdateRequest;
import com.example.backend.api.model.WeightData;
import com.example.backend.dao.entity.WeightDO;
import com.example.backend.dto.WeightDTO;
import com.example.backend.service.WeightService;
import com.example.backend.util.Converter;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WeightController implements WeightAPI {
    @Autowired
	private WeightService weightService;

	@Override
	public ResponseEntity<GetWeightsResponse> getWeights() {
        GetWeightsResponse getWeightsResponse = new GetWeightsResponse();
        getWeightsResponse.setData(new java.util.ArrayList<WeightData>());
		List<WeightDO> weightList = weightService.getWeights();

        for (WeightDO weightDo : weightList) {
            getWeightsResponse.getData().add(convertWeightDoToWeightData(weightDo));
        }

        getWeightsResponse.setMessage("Success");
        return new ResponseEntity<GetWeightsResponse>(getWeightsResponse, null, HttpStatus.OK);
	}

    @Override
    public ResponseEntity<GetWeightResponse> getWeightById(Integer id) {
        GetWeightResponse getWeightResponse = new GetWeightResponse();
        WeightDO weightDo = new WeightDO();

        try {
            weightDo = weightService.getWeight(id);
        } catch (Exception e) {
            log.error("Failed to get weight", e);
            getWeightResponse.setMessage("Failed to get weight");
            return new ResponseEntity<GetWeightResponse>(getWeightResponse, null, HttpStatus.NOT_FOUND);
        }

        if (weightDo == null) {
            getWeightResponse.setMessage("Weight not found");
            return new ResponseEntity<GetWeightResponse>(getWeightResponse, null, HttpStatus.NOT_FOUND);
        }

        getWeightResponse.setData(convertWeightDoToWeightData(weightDo));
        getWeightResponse.setMessage("Success");
        return new ResponseEntity<GetWeightResponse>(getWeightResponse, null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetWeightResponse> getWeightByDate(String date) {
        GetWeightResponse getWeightResponse = new GetWeightResponse();
        WeightDO weightDo = new WeightDO();

        try {
            weightDo = weightService.getWeightByDate(Converter.stringToDate(date));
        } catch (Exception e) {
            log.error("Failed to get weight", e);
            getWeightResponse.setMessage("Failed to get weight");
            return new ResponseEntity<GetWeightResponse>(getWeightResponse, null, HttpStatus.NOT_FOUND);
        }

        if (weightDo == null) {
            getWeightResponse.setMessage("Weight not found");
            return new ResponseEntity<GetWeightResponse>(getWeightResponse, null, HttpStatus.NOT_FOUND);
        }

        getWeightResponse.setData(convertWeightDoToWeightData(weightDo));
        getWeightResponse.setMessage("Success");
        return new ResponseEntity<GetWeightResponse>(getWeightResponse, null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreateUpdateResponse> createWeight(CreateRequest createRequest, BindingResult bindingResult) {
        CreateUpdateResponse createUpdateResponse = new CreateUpdateResponse();
        WeightDO savedWeight = new WeightDO();

        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            createUpdateResponse.setMessage(error.getDefaultMessage());
            return new ResponseEntity<CreateUpdateResponse>(createUpdateResponse, null, HttpStatus.BAD_REQUEST);
        }

        try {
            savedWeight = weightService.createWeight(convertWeightRequestToWeightDto(createRequest));
        } catch (Exception e) {
            log.error("Failed to create weight", e);
            createUpdateResponse.setMessage("Failed to create weight");
            return new ResponseEntity<CreateUpdateResponse>(createUpdateResponse, null, HttpStatus.NOT_FOUND);
        }

        if (savedWeight == null) {
            createUpdateResponse.setMessage("Failed to create weight");
            return new ResponseEntity<CreateUpdateResponse>(createUpdateResponse, null, HttpStatus.NOT_FOUND);
        }

        createUpdateResponse.setMessage("Success");
        return new ResponseEntity<CreateUpdateResponse>(createUpdateResponse, null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreateUpdateResponse> updateWeight(UpdateRequest updateRequest, BindingResult bindingResult) {
        CreateUpdateResponse createUpdateResponse = new CreateUpdateResponse();
        WeightDO savedWeight = new WeightDO();

        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            createUpdateResponse.setMessage(error.getDefaultMessage());
            return new ResponseEntity<CreateUpdateResponse>(createUpdateResponse, null, HttpStatus.BAD_REQUEST);
        }

        try {
            savedWeight = weightService.updateWeight(updateRequest.getId(), convertWeightRequestToWeightDto(updateRequest));
        } catch (Exception e) {
            log.error("Failed to update weight", e);
            createUpdateResponse.setMessage("Failed to update weight");
            return new ResponseEntity<CreateUpdateResponse>(createUpdateResponse, null, HttpStatus.NOT_FOUND);
        }

        if (savedWeight == null) {
            createUpdateResponse.setMessage("Failed to update weight");
            return new ResponseEntity<CreateUpdateResponse>(createUpdateResponse, null, HttpStatus.NOT_FOUND);
        }

        createUpdateResponse.setMessage("Success");
        return new ResponseEntity<CreateUpdateResponse>(createUpdateResponse, null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeleteResponse> deleteWeight(DeleteRequest deleteRequest) {
        DeleteResponse deleteResponse = new DeleteResponse();
        String message = "";

        try {
            message = weightService.deleteWeight(deleteRequest.getId());
        } catch (Exception e) {
            log.error("Failed to delete weight", e);
            deleteResponse.setMessage("Failed to delete weight");
            return new ResponseEntity<DeleteResponse>(deleteResponse, null, HttpStatus.NOT_FOUND);
        }

        deleteResponse.setMessage(message);
        if (!message.equals("Success")) {
            return new ResponseEntity<DeleteResponse>(deleteResponse, null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DeleteResponse>(deleteResponse, null, HttpStatus.OK);
    }

    private WeightData convertWeightDoToWeightData(WeightDO weightDo) {
        WeightData weightData = new WeightData();
        weightData.setId(weightDo.getId().intValue());
        weightData.setDate(weightDo.getDate());
        weightData.setMax(weightDo.getMax());
        weightData.setMin(weightDo.getMin());
        return weightData;
    }

    private WeightDTO convertWeightRequestToWeightDto(CreateRequest weightRequest) {
        WeightDTO weightDto = new WeightDTO();

        if (weightRequest.getDate() != null) {
            weightDto.setDate(weightRequest.getDate().atStartOfDay());
        }

        if (weightRequest.getMax() != null) {
            weightDto.setMax(weightRequest.getMax());
        }

        if (weightRequest.getMin() != null) {
            weightDto.setMin(weightRequest.getMin());
        }

        return weightDto;
    }

    private WeightDTO convertWeightRequestToWeightDto(UpdateRequest weightRequest) {
        WeightDTO weightDto = new WeightDTO();

        if (weightRequest.getDate() != null) {
            weightDto.setDate(Converter.stringToDate(weightRequest.getDate()));
        }

        if (weightRequest.getMax() != null) {
            weightDto.setMax(weightRequest.getMax());
        }

        if (weightRequest.getMin() != null) {
            weightDto.setMin(weightRequest.getMin());
        }

        return weightDto;
    }
}
