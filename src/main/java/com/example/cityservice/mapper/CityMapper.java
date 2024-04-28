package com.example.cityservice.mapper;

import com.example.cityservice.dto.CityDTO;
import com.example.cityservice.model.City;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {

    CityDTO cityToCityDTO(City city);
    City cityDTOToCity(CityDTO cityDTO);
}
