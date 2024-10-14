package com.example.cars.controller;

import com.example.cars.dto.CarAdditionRequest;
import com.example.cars.model.Car;
import com.example.cars.service.core.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


@RestController
public class CarsController {

    @Autowired
    ICarService carService;

    @PostMapping("/car")
    public ResponseEntity<Void> addCar(@RequestBody CarAdditionRequest req) {
        try {
            carService.addCar(req.getCarList());
            return ResponseEntity.status(HttpStatus.CREATED).build(); // Return 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 on error
        }
    }

    @GetMapping("/car")
    public ResponseEntity<Page<Car>> fetchAllCars() {
        try {
            Page<Car> cars = carService.fetchAllCars(); // Fetch all cars
            return ResponseEntity.ok(cars); // Return 200 OK with cars
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/car")
    public String deleteAll() {
        carService.deleteAllCars();
        return "Deleted";
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> fetchCarById(@PathVariable String id) {
        // Fetch car from the service layer
        Optional<Car> carOptional = carService.fetchCarById(id);
        Car car = carOptional.orElse(null);

        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);  // Return the car with 200 OK status
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if car not found
        }
    }

    @GetMapping("/test")
    public String testConnection() {
        return "Connected to Elasticsearch successfully!";
    }
}
