package com.example.cityservice.controller;

import com.example.cityservice.dto.CityDTO;
import com.example.cityservice.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CityController {

    public static final String CITY_PATH = "/api/city";
    public static final String CITY_PATH_ID = CITY_PATH + "/{id}";
    public static final String HTTP_URL_PATH = "http://localhost:8080/" + CITY_PATH + "/";

    private final CityService cityService;


    @PostMapping(CITY_PATH)
    Mono<ResponseEntity<Void>> createNewCity(@Validated @RequestBody CityDTO cityDTO) {
        return cityService.save(cityDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                        .fromHttpUrl(HTTP_URL_PATH + savedDto.getId()).build().toUri())
                        .build());
    }

    @PutMapping(CITY_PATH_ID)
    ResponseEntity<Void> updateCity(@Validated @RequestBody CityDTO cityDTO) {

        cityService.update(cityDTO).subscribe();

        return ResponseEntity.ok().build();

    }

    @PatchMapping(CITY_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingCity(@Validated @RequestBody CityDTO cityDTO) {
        return cityService.patch(cityDTO)
                .map(updatedDto -> ResponseEntity.ok().build());
    }

    @GetMapping(CITY_PATH_ID)
    Mono<CityDTO> getCityById(@PathVariable("id") Long id) {
        return cityService.findById(id);
    }

    @GetMapping(CITY_PATH)
    Flux<CityDTO> getAllCities() {
        return cityService.findAll();
    }

    @DeleteMapping(CITY_PATH_ID)
    Mono<ResponseEntity<Void>> deleteCity(@PathVariable("id") Long id) {
        return cityService.deleteById(id)
                .map(savedDto -> ResponseEntity.noContent().build());
    }
}
