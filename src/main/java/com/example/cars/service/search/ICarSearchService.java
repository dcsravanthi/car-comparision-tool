package com.example.cars.service.search;

import com.example.cars.dto.CarSearchRequest;
import com.example.cars.model.Car;

import java.io.IOException;
import java.util.List;

public interface ICarSearchService {
   public List<Car> searchCars(CarSearchRequest searchRequest) throws IOException;
}
