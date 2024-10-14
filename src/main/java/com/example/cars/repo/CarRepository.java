package com.example.cars.repo;

import com.example.cars.model.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface CarRepository extends ElasticsearchRepository<Car, String> {
}

