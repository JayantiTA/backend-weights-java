package com.example.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.backend.dao.entity.WeightDO;
import com.example.backend.dto.WeightDTO;

@Mapper
public interface WeightMapper {
    WeightMapper INSTANCE = Mappers.getMapper(WeightMapper.class);

    WeightDTO map(WeightDO weightDO);

    WeightDO map(WeightDTO weightDTO);

    @Mapping(target = "date", ignore = true)
    @Mapping(target = "max", ignore = true)
    @Mapping(target = "min", ignore = true)
    WeightDO toDo(WeightDTO weightDTO, WeightDO weightDO);
}
