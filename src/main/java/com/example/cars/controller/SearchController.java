package com.example.cars.controller;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.cars.dto.CarAdditionRequest;
import com.example.cars.dto.CarSearchRequest;
import com.example.cars.model.Car;
import com.example.cars.service.search.ICarSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final ICarSearchService carSearchService;

    public SearchController(ICarSearchService carSearchService) {
        this.carSearchService = carSearchService;
    }

    @PostMapping("/cars")
    public ResponseEntity<List<Car>> seachCar(@RequestBody CarSearchRequest req) {
        try {
            List<Car> carList = carSearchService.searchCars(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(carList); // Return 201 Created with car list in body
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 on error
        }
    }
}
