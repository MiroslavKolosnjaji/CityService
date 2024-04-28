package com.example.cityservice.repository;

import com.example.cityservice.model.City;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import static org.junit.jupiter.api.Assertions.*;
@DataR2dbcTest
class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;


    @Test
    void testCreateJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(getTestCity()));
    }

    @Test
    void name() {
        cityRepository.save(getTestCity())
                .subscribe(city -> assertNotNull(city.getId()));
    }

    City getTestCity(){
        return City.builder()
                .name("Belgrade")
                .zipCode("11000")
                .build();
    }
}