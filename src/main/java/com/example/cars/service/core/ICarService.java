package com.example.cars.service.core;

import com.example.cars.model.Car;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ICarService {
    public void addCar(List<Car> carList);

    Page<Car> fetchAllCars();

    void deleteAllCars();

    Optional<Car> fetchCarById(String id);
}
