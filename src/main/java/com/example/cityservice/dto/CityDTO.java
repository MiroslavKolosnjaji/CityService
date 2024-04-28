package com.example.cityservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDTO {

    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 5, max = 5)
    private String zipCode;
}
