package com.example.cars.controller;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.cars.model.Car;
import com.example.cars.service.similarity.ISimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuggestionsController {

    @Autowired
    ISimilarity similarity;

    @GetMapping("/suggestions/{id}")
    public ResponseEntity<List<Car>> fetchSuggestionsByCarId(@PathVariable String id) {
        try {
            List<Car> suggestions = similarity.fetchSimilarCars(id); // Fetch similar cars
            if (suggestions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 No Content if no suggestions found
            }
            return ResponseEntity.ok(suggestions); // Return 200 OK with suggestions
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 in case of error
        }
    }
}
