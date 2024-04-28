package com.example.cityservice.service.impl;

import com.example.cityservice.dto.CityDTO;
import com.example.cityservice.mapper.CityMapper;
import com.example.cityservice.repository.CityRepository;
import com.example.cityservice.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public Mono<CityDTO> save(CityDTO cityDTO) {
        return cityRepository.save(cityMapper.cityDTOToCity(cityDTO))
                .map(cityMapper::cityToCityDTO);
    }

    @Override
    public Mono<CityDTO> update(CityDTO cityDTO) {

        return cityRepository.findById(cityDTO.getId())
                .map(foundCity -> {
                    foundCity.setName(cityDTO.getName());
                    foundCity.setZipCode(cityDTO.getZipCode());
                    return foundCity;
                }).flatMap(cityRepository::save)
                .map(cityMapper::cityToCityDTO);
    }

    @Override
    public Mono<CityDTO> patch(CityDTO cityDTO) {
        return cityRepository.findById(cityDTO.getId())
                .map(foundCity -> {

                    if (StringUtils.hasText(cityDTO.getName()))
                        foundCity.setName(cityDTO.getName());

                    if (StringUtils.hasText(cityDTO.getZipCode()))
                        cityDTO.setZipCode(cityDTO.getZipCode());

                    return foundCity;

                }).flatMap(cityRepository::save)
                .map(cityMapper::cityToCityDTO);
    }

    @Override
    public Mono<CityDTO> findById(Long id) {
        return cityRepository.findById(id).map(cityMapper::cityToCityDTO);
    }

    @Override
    public Flux<CityDTO> findAll() {
        return cityRepository.findAll().map(cityMapper::cityToCityDTO);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return cityRepository.deleteById(id);
    }
}
