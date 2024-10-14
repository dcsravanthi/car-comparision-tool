package com.example.cars.controller;

import com.example.cars.model.CarComparisonResult;
import com.example.cars.service.comparison.ICarComparisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComparisonController {

    @Autowired
    ICarComparisionService comparisonService;

    @GetMapping("/compare")
    public ResponseEntity<List<CarComparisonResult>> compareCars(
            @RequestParam String selectedCarId,
            @RequestParam List<String> otherCarIds,
            @RequestParam(defaultValue = "false") boolean showDifferences
    ) {
        List<CarComparisonResult> result = comparisonService.compareCars(selectedCarId, otherCarIds, showDifferences);
        return ResponseEntity.ok(result);
    }
}
