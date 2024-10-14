package com.example.cars.service.similarity;

import com.example.cars.model.Car;
import java.util.List;

public interface ISimilarity {

    public List<Car> fetchSimilarCars(Car car);

}
