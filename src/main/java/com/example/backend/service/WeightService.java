package com.example.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dao.entity.WeightDO;
import com.example.backend.dao.repository.WeightRepository;
import com.example.backend.dto.WeightDTO;

@Service
public class WeightService {
    @Autowired
    private WeightRepository weightRepository;

    public List<WeightDO> getWeights() {
        return weightRepository.findAll();
    }

    public WeightDO getWeight(Integer id) {
        return weightRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public WeightDO getWeightByDate(LocalDateTime date) {
        return weightRepository.findByDate(date).orElse(null);
    }

    public WeightDO createWeight(WeightDTO weight) {
        WeightDO weightData = getWeightByDate(weight.getDate());
        WeightDO weightToBeInserted = new WeightDO();
        if (weightData != null) {
            weightToBeInserted = convertDtoToWeightDo(weight, weightData);
            return weightRepository.save(weightToBeInserted);
        }
        weightToBeInserted = convertDtoToWeightDo(weight);
        return weightRepository.save(weightToBeInserted);
    }

    public WeightDO updateWeight(Integer id, WeightDTO weight) {
        WeightDO weightData = getWeight(id);
        if (weightData == null) {
            return null;
        }
        WeightDO weightToBeUpdated = convertDtoToWeightDo(weight, weightData);
        return weightRepository.save(weightToBeUpdated);
    }

    public String deleteWeight(Integer id) {
        WeightDO weightData = getWeight(id);
        if (weightData == null) {
            return "Weight not found";
        }
        weightRepository.deleteById(Long.valueOf(id));
        return "Success";
    }

    private WeightDO convertDtoToWeightDo(WeightDTO weightDto) {
        return convertDtoToWeightDo(weightDto, null);
    }

    private WeightDO convertDtoToWeightDo(WeightDTO weightDto, WeightDO weightDO) {
        if (weightDO == null) {
            weightDO = new WeightDO();
        }

        if (weightDto.getDate() != null) {
            weightDO.setDate(weightDto.getDate());
        }

        if (weightDto.getMax() != null) {
            weightDO.setMax(weightDto.getMax());
        }

        if (weightDto.getMin() != null) {
            weightDO.setMin(weightDto.getMin());
        }

        return weightDO;
    }
}
