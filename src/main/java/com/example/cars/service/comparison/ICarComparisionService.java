package com.example.cars.service.comparison;

import com.example.cars.model.CarComparisonResult;

import java.util.List;

public interface ICarComparisionService {
    public List<CarComparisonResult> compareCars(String selectedCarId, List<String> otherCarIds,
                                                  boolean showDifferences);
}
