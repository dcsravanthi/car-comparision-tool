package com.example.cars.service.vectorgeneration;

import com.example.cars.model.Car;
import org.springframework.stereotype.Service;

@Service
public class generateVectorByNormalisation implements IVectorGenerator {

    @Override
    public float[] generateVector(Car car) {
        float[] vector = new float[10];

        // Encoding a few features into vector space (for simplicity, normalized values)
        vector[0] = (float) car.getPerformance().getHorsepower()/1000;
        vector[1] = (float) car.getPerformance(). getTopSpeed()/ 300.0f;      // Max top speed = 300
        vector[2] = (float) car.getDimensionsAndWeight().getLength() / 5000.0f;  // Normalizing length
        vector[3] = (float) car.getFuelAndEfficiency().getRange() / 1000.0f;  // Electric car range, e.g., max 1000 km

        return vector;
    }
}
