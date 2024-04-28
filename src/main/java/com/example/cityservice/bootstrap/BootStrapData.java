package com.example.cityservice.bootstrap;

import com.example.cityservice.model.City;
import com.example.cityservice.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BootStrapData implements CommandLineRunner {

    private final CityRepository cityRepository;

    @Override
    public void run(String... args) throws Exception {
        cityRepository.deleteAll().doOnSuccess(success -> loadCityData()).subscribe();

        cityRepository.count().subscribe(count -> System.out.println("Count is: " + count));
    }

    private void loadCityData() {
        cityRepository.count().subscribe(count -> {
            City city1 = createCity("Beograd", "11000");
            City city2 = createCity("Novi Sad", "21000");
            City city3 = createCity("Kragujevac", "34000");

            cityRepository.save(city1).subscribe();
            cityRepository.save(city2).subscribe();
            cityRepository.save(city3).subscribe();

        });
    }


    private City createCity(String name, String zipCode){
        return City.builder()
                .name(name)
                .zipCode(zipCode)
                .build();
    }
}
