package com.example.cars.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

// Main Car Class
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "cars")
public class Car {

    @Id
    private String id;

    // Nested categories
    private BasicInfo basicInfo;
    private Performance performance;
    private FuelAndEfficiency fuelAndEfficiency;
    private TransmissionAndDrivetrain transmissionAndDrivetrain;
    private DimensionsAndWeight dimensionsAndWeight;
    private SafetyFeatures safetyFeatures;
    private TechnologyAndComfort technologyAndComfort;
    private EVSpecifications evSpecifications; // For electric cars
    @Field(type = FieldType.Dense_Vector, dims = 10)
    private float[] denseVector;


// 1. Basic Information Category
@Data
@AllArgsConstructor
@NoArgsConstructor
public static class  BasicInfo {
    private String make;
    private String model;
    private int year;
    private double price;
    private String bodyStyle;
    private String color;
    private int doors;
}

// 2. Performance Specifications Category
@Data
@AllArgsConstructor
@NoArgsConstructor
 public static class Performance {
    private String engine;
    private int horsepower;
    private int torque;
    private int topSpeed;
    private double acceleration;
}

// 3. Fuel and Efficiency Category
@Data
@AllArgsConstructor
@NoArgsConstructor
 public static class FuelAndEfficiency {
    private String fuelType;
    private double mileage;
    private double fuelCapacity;
    private double range;  // For hybrid or electric vehicles
}

// 4. Transmission and Drivetrain Category
@Data
@AllArgsConstructor
@NoArgsConstructor
 public static class TransmissionAndDrivetrain {
    private String transmission;
    private String drivetrain;
    private String gearbox;
}

// 5. Dimensions and Weight Category
@Data
@AllArgsConstructor
@NoArgsConstructor
 public static class DimensionsAndWeight {
    private double length;
    private double width;
    private double height;
    private double wheelbase;
    private double groundClearance;
    private double curbWeight;
}

// 6. Safety Features Category
@Data
@AllArgsConstructor
@NoArgsConstructor
 public static class SafetyFeatures {
    private String safetyRating;
    private int airbags;
    private boolean abs;  // Anti-lock Braking System
    private boolean tractionControl;
    private boolean laneAssist;
    private boolean blindSpotMonitor;
    private boolean adaptiveCruiseControl;
}

// 7. Technology and Comfort Category
@Data
@AllArgsConstructor
@NoArgsConstructor
 public static class TechnologyAndComfort {
    private String infotainmentSystem;
    private String climateControl;
    private String seatMaterial;
    private boolean heatedSeats;
    private boolean sunroof;
    private boolean parkingAssist;
    private boolean headUpDisplay;
}

// 8. Electric Vehicle (EV) Specifications Category
@Data
@AllArgsConstructor
@NoArgsConstructor
 public static class EVSpecifications {
    private double batteryCapacity;  // in kWh
    private double chargingTime;     // in hours
    private double electricRange;    // in km
    private boolean fastCharging;
    private boolean regenerativeBraking;
}
}
