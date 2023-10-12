package com.example.backend.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.backend.api.model.CreateRequest;
import com.example.backend.api.model.CreateUpdateResponse;
import com.example.backend.api.model.DeleteRequest;
import com.example.backend.api.model.DeleteResponse;
import com.example.backend.api.model.GetWeightResponse;
import com.example.backend.api.model.GetWeightsResponse;
import com.example.backend.api.model.UpdateRequest;

@RequestMapping(path = "api")
public interface WeightAPI {

    @GetMapping("/weights")
    @ResponseBody
    ResponseEntity<GetWeightsResponse> getWeights();

    @GetMapping("/weight/{id}")
    @ResponseBody
    ResponseEntity<GetWeightResponse> getWeightById(@PathVariable Integer id);

    @GetMapping("/weight")
    @ResponseBody
    ResponseEntity<GetWeightResponse> getWeightByDate(@RequestParam(name = "date") String date);

    @PostMapping("/weights")
    @ResponseBody
    ResponseEntity<CreateUpdateResponse> createWeight(
            @RequestBody @Valid CreateRequest createRequest, BindingResult bindingResult);

    @PutMapping("/weights")
    @ResponseBody
    ResponseEntity<CreateUpdateResponse> updateWeight(
            @RequestBody @Valid UpdateRequest updateRequest, BindingResult bindingResult);

    @DeleteMapping("/weights")
    @ResponseBody
    ResponseEntity<DeleteResponse> deleteWeight(
            @RequestBody DeleteRequest deleteRequest);
}
