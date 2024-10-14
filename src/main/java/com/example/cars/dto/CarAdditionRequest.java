package com.example.cars.dto;

import com.example.cars.model.Car;
import lombok.Data;

import java.util.List;

@Data
public class CarAdditionRequest {
    private List<Car> carList;
}
