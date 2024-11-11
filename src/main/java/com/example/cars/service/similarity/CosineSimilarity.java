package com.example.cars.service.similarity;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.example.cars.model.Car;
import com.example.cars.repo.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CosineSimilarity implements ISimilarity {

    private final ElasticsearchClient elasticsearchClient;
    private ObjectMapper objectMapper;

    @Autowired
    public CosineSimilarity(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
        objectMapper = new ObjectMapper();
    }


    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> fetchSimilarCars(String id) throws IOException {
        Optional<Car> carOptional = carRepository.findById(id);
        Car car = carOptional.orElse(null);
        if(car == null) throw new RuntimeException("Car not found");
        float[] carVector = car.getDenseVector();
        if (carVector == null) {
            throw new IllegalArgumentException("Car vector is null");
        }

        // Build the script score query
        String scriptSource = "cosineSimilarity(params.query_vector, 'vector') + 1.0";

        Script script = Script.of(s -> s
                .source(scriptSource)
                .params(Map.of("query_vector", JsonData.of(carVector)))
        );

        // Construct the script score query
        Query scriptScoreQuery = Query.of(q -> q
                .scriptScore(ss -> ss
                        .query(Query.of(qb -> qb.matchAll(m -> m)))
                        .script(script)
                )
        );


        // Build the search request with the script score query
        SearchRequest searchRequest = SearchRequest.of(sr -> sr
                .index("cars") // specify the index
                .query(scriptScoreQuery)
                .size(10) // Limit to 10 results
        );

        // Execute the search request

        SearchResponse<JsonData> response = elasticsearchClient.search(searchRequest, JsonData.class);
        List<Car> carList = response.hits().hits().stream()
                .map(this::mapHitToCar)
                .collect(Collectors.toList());

        return carList;
    }

    private Car mapHitToCar(Hit<JsonData> hit) {
        try {
            // Convert JsonData to String and deserialize to Car
            String json = String.valueOf(hit.source().toJson()); // Get JSON string from JsonData
            return objectMapper.readValue(json, Car.class); // Deserialize to Car
        } catch (Exception e) {
            // Log the error with the original JSON for debugging
            System.err.println("Failed to map hit to Car: " + e.getMessage());
            System.err.println("Original JSON: " + hit.source().toJson());
            e.printStackTrace(); // Print stack trace for detailed error information
            throw new RuntimeException("Failed to map hit to Car", e);
        }
    }
}
