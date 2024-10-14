package com.example.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarSearchRequest {
    private String make;
    private String model;
    private Integer year;
    private Double minPrice;
    private Double maxPrice;
    private String fuelType;
    private String transmission;
    private SafetyFeaturesDto safetyFeatures;
    private TechnologyAndComfortDto technologyAndComfort;
    private EVSpecificationsDto evSpecifications;
    private PerformanceDto performance;
    private FuelAndEfficiencyDto fuelAndEfficiency;
    private TransmissionAndDrivetrainDto transmissionAndDrivetrain;
    private DimensionsAndWeightDto dimensionsAndWeight;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SafetyFeaturesDto {
        private String safetyRating;
        private Integer airbags;
        private Boolean abs;
        private Boolean tractionControl;
        private Boolean laneAssist;
        private Boolean blindSpotMonitor;
        private Boolean adaptiveCruiseControl;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TechnologyAndComfortDto {
        private String infotainmentSystem;
        private String climateControl;
        private String seatMaterial;
        private Boolean heatedSeats;
        private Boolean sunroof;
        private Boolean parkingAssist;
        private Boolean headUpDisplay;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EVSpecificationsDto {
        private Double batteryCapacity; // in kWh
        private Double chargingTime;    // in hours
        private Double electricRange;   // in km
        private Boolean fastCharging;
        private Boolean regenerativeBraking;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PerformanceDto {
        private String engine;
        private Integer horsepower;
        private Integer torque;
        private Integer topSpeed;
        private Double acceleration;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FuelAndEfficiencyDto {
        private String fuelType;
        private Double mileage;
        private Double fuelCapacity;
        private Double range; // For hybrid or electric vehicles
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransmissionAndDrivetrainDto {
        private String transmission;
        private String drivetrain;
        private String gearbox;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DimensionsAndWeightDto {
        private Double length;
        private Double width;
        private Double height;
        private Double wheelbase;
        private Double groundClearance;
        private Double curbWeight;
    }
}
