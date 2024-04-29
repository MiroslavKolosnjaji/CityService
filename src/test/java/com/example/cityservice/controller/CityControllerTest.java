package com.example.cityservice.controller;

import com.example.cityservice.dto.CityDTO;
import com.example.cityservice.model.City;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CityControllerTest {

    @Autowired
    WebTestClient webTestClient;


    @Test
    void testCreateCity() {
        webTestClient.post().uri(CityController.CITY_PATH)
                .body(Mono.just(getTestCity()), CityDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/city/4");
    }

    @Test
    void testCreateCityFailure() {

        City city = City.builder().build();

        webTestClient.post().uri(CityController.CITY_PATH)
                .body(Mono.just(city), CityDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void testUpdateCity() {
        webTestClient.put().uri(CityController.CITY_PATH_ID, 1)
                .body(Mono.just(getTestCityWithId()), CityDTO.class)
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    @Order(4)
    void testUpdateCityFailure() {
        City city = getTestCityWithId();
        city.setName("");

        webTestClient.put().uri(CityController.CITY_PATH_ID, 1)
                .body(Mono.just(city), CityDTO.class)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    void testUpdateCityNotFound() {
        City city = getTestCityWithId();
        city.setId(79L);

        webTestClient.put().uri(CityController.CITY_PATH_ID, 99)
                .body(Mono.just(city), CityDTO.class)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void testPatchIdNotFound() {
        City city = getTestCityWithId();
        city.setId(99L);
        webTestClient.patch().uri(CityController.CITY_PATH_ID, 999)
                .body(Mono.just(city), CityDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1)
    void testGetById() {
        webTestClient.get().uri(CityController.CITY_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(CityDTO.class);
    }

    @Test
    void testGetByIdNotFound() {
        webTestClient.get().uri(CityController.CITY_PATH_ID, 99)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(2)
    void testGetAllCities() {
        webTestClient.get().uri(CityController.CITY_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    void testDeleteCity() {
        webTestClient.delete().uri(CityController.CITY_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteCityNotFound() {
        webTestClient.delete().uri(CityController.CITY_PATH_ID, 99)
                .exchange()
                .expectStatus().isNotFound();
    }

    private City getTestCity() {
        return City.builder().name("Belgrade").zipCode("11000").build();
    }

    private City getTestCityWithId() {
        City city = getTestCity();
        city.setId(1L);
        return city;
    }
}