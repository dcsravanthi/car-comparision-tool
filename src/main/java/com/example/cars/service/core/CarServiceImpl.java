package com.example.cars.service.core;

import com.example.cars.model.Car;
import com.example.cars.repo.CarRepository;
import com.example.cars.service.vectorgeneration.IVectorGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements ICarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private IVectorGenerator vectorGenerator;

    @Override
    public void addCar(List<Car> carList) {
        for(Car car: carList) {
            car.setDenseVector(vectorGenerator.generateVector(car));
        }
        carRepository.saveAll(carList);
    }

    @Override
    public Page<Car> fetchAllCars() {
        return (Page<Car>) carRepository.findAll();
    }

    @Override
    public void deleteAllCars() {
         carRepository.deleteAll();
    }

    @Override
    public Optional<Car> fetchCarById(String id) {
        return carRepository.findById(id);
    }
}
