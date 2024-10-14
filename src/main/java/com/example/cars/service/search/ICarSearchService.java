package com.example.cars.service.search;

import com.example.cars.dto.CarSearchRequest;
import org.elasticsearch.action.search.SearchResponse;

public interface ICarSearchService {
    public SearchResponse searchCars(CarSearchRequest searchRequest);
}
