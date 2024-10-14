package com.example.cars.service.similarity;

import com.example.cars.model.Car;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;
import java.util.Map;


public class CosineSimilarity implements ISimilarity {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Car> fetchSimilarCars(Car car) {
        // Fetch the vector for the selected car
        float[] carVector = car.getDenseVector();
        if (carVector == null) {
            throw new IllegalArgumentException("Car vector is null");
        }
        // Vector search query using Elasticsearch
        StringBuilder vectorString = new StringBuilder("[");
        for (int i = 0; i < carVector.length; i++) {
            vectorString.append(carVector[i]);
            if (i < carVector.length - 1) {
                vectorString.append(", ");
            }
        }
        vectorString.append("]");

        // Build the query using inline vector representation
        String script = "cosineSimilarity(" + vectorString + ", 'vector') + 1.0";

        Query query = new NativeQueryBuilder()
                .withQuery(QueryBuilders.scriptScoreQuery(
                        QueryBuilders.matchAllQuery(),
                        ScoreFunctionBuilders.scriptFunction(script)
                                .setParams(Map.of("query_vector", carVector))
                ))
                .withPageable(PageRequest.of(0, 10))  // Limit to 10 results
                .build();

        // Create a NativeSearchQuery using the script score query
        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(query)
                .build();

        // Execute the search and return the hits
        return elasticsearchOperations.search(searchQuery, Car.class).toList();
}
}
