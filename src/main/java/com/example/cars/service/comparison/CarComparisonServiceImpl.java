package com.example.cars.service.comparison;

import com.example.cars.model.Car;
import com.example.cars.model.CarComparisonResult;
import com.example.cars.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;


@Service
public class CarComparisonServiceImpl implements ICarComparisionService{

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<CarComparisonResult> compareCars(String selectedCarId, List<String> otherCarIds, boolean showDifferences) {
        // Fetch the selected car
        Car selectedCar = carRepository.findById(selectedCarId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Fetch the other cars
        List<Car> otherCars = (List<Car>) carRepository.findAllById(otherCarIds);

        // Initialize a list to hold the comparison results
        List<CarComparisonResult> comparisonResults = new ArrayList<>();

        // Iterate over each car and compare
        for (Car otherCar : otherCars) {
            CarComparisonResult result = compareTwoCars(selectedCar, otherCar, showDifferences);
            comparisonResults.add(result);
        }

        return comparisonResults;
    }

    private CarComparisonResult compareTwoCars(Car car1, Car car2, boolean showDifferences) {
        CarComparisonResult result = new CarComparisonResult();
        result.setCar1Id(car1.getId());
        result.setCar2Id(car2.getId());

        // Compare each category of the cars (basic info, performance, etc.)
        result.setBasicInfo(compareCategory(car1.getBasicInfo(), car2.getBasicInfo(), showDifferences));
        result.setPerformance(compareCategory(car1.getPerformance(), car2.getPerformance(), showDifferences));
        result.setFuelAndEfficiency(compareCategory(car1.getFuelAndEfficiency(), car2.getFuelAndEfficiency(), showDifferences));
        result.setTransmissionAndDrivetrain(compareCategory(car1.getTransmissionAndDrivetrain(), car2.getTransmissionAndDrivetrain(), showDifferences));
        result.setDimensionsAndWeight(compareCategory(car1.getDimensionsAndWeight(), car2.getDimensionsAndWeight(), showDifferences));
        result.setSafetyFeatures(compareCategory(car1.getSafetyFeatures(), car2.getSafetyFeatures(), showDifferences));
        result.setTechnologyAndComfort(compareCategory(car1.getTechnologyAndComfort(), car2.getTechnologyAndComfort(), showDifferences));
        result.setEvSpecifications(compareCategory(car1.getEvSpecifications(), car2.getEvSpecifications(), showDifferences));

        return result;
    }

    // Helper method to compare a category
    private <T> Map<String, Object> compareCategory(T car1Category, T car2Category, boolean showDifferences) {
        Map<String, Object> comparisonMap = new HashMap<>();
        T carCategory = car1Category;
        if(car1Category == null) {
            if(car2Category == null) return null;
            carCategory = car2Category;
        }

        // Use reflection or manually compare each field in the category
        for (Field field : carCategory.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value1 = car1Category == null ? "NA" : field.get(car1Category);
                Object value2 = car2Category == null ? "NA" : field.get(car2Category);

                if (!showDifferences || !Objects.equals(value1, value2)) {
                    comparisonMap.put(field.getName(), Map.of("Car1", value1, "Car2",
                            value2));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // handle error
            }
        }

        return comparisonMap;
    }
}

