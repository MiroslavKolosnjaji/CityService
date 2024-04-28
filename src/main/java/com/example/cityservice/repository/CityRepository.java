package com.example.cityservice.repository;

import com.example.cityservice.model.City;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CityRepository extends ReactiveCrudRepository<City, Long> {
}
