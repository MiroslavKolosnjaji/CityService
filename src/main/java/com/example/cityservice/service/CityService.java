package com.example.cityservice.service;

import com.example.cityservice.dto.CityDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {
    Mono<CityDTO> save(CityDTO cityDTO);
    Mono<CityDTO> update(CityDTO cityDTO);
    Mono<CityDTO> patch(CityDTO cityDTO);
    Mono<CityDTO> findById(Long id);
    Flux<CityDTO> findAll();
    Mono<Void> deleteById(Long id);
}
