package com.example.cars.model;

import lombok.Data;

import java.util.Map;

@Data
public class CarComparisonResult {

    private String car1Id;
    private String car2Id;

    private Map<String, Object> basicInfo;
    private Map<String, Object> performance;
    private Map<String, Object> fuelAndEfficiency;
    private Map<String, Object> transmissionAndDrivetrain;
    private Map<String, Object> dimensionsAndWeight;
    private Map<String, Object> safetyFeatures;
    private Map<String, Object> technologyAndComfort;
    private Map<String, Object> evSpecifications;

}
