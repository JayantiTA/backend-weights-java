package com.example.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.backend.dao.entity.WeightDO;
import com.example.backend.dao.repository.WeightRepository;
import com.example.backend.dto.WeightDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeightService {

    private static final String CACHE_PREFIX = "WGT_";

    @Autowired
    private WeightRepository weightRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Cacheable(value = "weights")
    public List<WeightDO> getWeights() {
        return weightRepository.findAll();
    }

    @Cacheable(value = "weight", key = "#id")
    public WeightDO getWeight(Integer id) {
        return weightRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @Cacheable(value = "weightByDate", key = "#date")
    public WeightDO getWeightByDate(LocalDateTime date) {
        return weightRepository.findByDate(date).orElse(null);
    }

    @CachePut(value = "weights")
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

    @Caching(evict = {
            @CacheEvict(value = "weight"),
            @CacheEvict(value = "weightByDate") }, put = { @CachePut(value = "weights") })
    public WeightDO updateWeight(Integer id, WeightDTO weight) {
        WeightDO weightData = getWeight(id);
        if (weightData == null) {
            return null;
        }
        WeightDO weightToBeUpdated = convertDtoToWeightDo(weight, weightData);
        return weightRepository.save(weightToBeUpdated);
    }

    @Caching(evict = {
            @CacheEvict(value = "weights"),
            @CacheEvict(value = "weight"),
            @CacheEvict(value = "weightByDate"),
    })
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
