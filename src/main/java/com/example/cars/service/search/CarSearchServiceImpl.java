package com.example.cars.service.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.example.cars.config.ElasticsearchConfig;
import com.example.cars.dto.CarSearchRequest;
import com.example.cars.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CarSearchServiceImpl implements ICarSearchService {



    private final ElasticsearchClient elasticsearchClient;
    private ObjectMapper objectMapper;

    @Autowired
    public CarSearchServiceImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
        objectMapper = new ObjectMapper();
    }

    /**
     * Perform a flexible search on the Car index based on provided filters.
     *
     * @param carSearchRequest The DTO containing search criteria.
     * @return SearchHits<Car> The filtered search results.
     */
    @Override
    public List<Car> searchCars(CarSearchRequest carSearchRequest) throws IOException {
        List<Query> filters = new ArrayList<>();

        // Basic Info filters
        if (carSearchRequest.getMake() != null) {
            filters.add(Query.of(q -> q.term(t -> t.field("basicInfo.make").value(carSearchRequest.getMake()))));
        }
        if (carSearchRequest.getModel() != null) {
            filters.add(Query.of(q -> q.term(t -> t.field("basicInfo.model").value(carSearchRequest.getModel()))));
        }
        if (carSearchRequest.getYear() != null) {
            filters.add(Query.of(q -> q.term(t -> t.field("basicInfo.year").value(carSearchRequest.getYear()))));
        }
        if (carSearchRequest.getMinPrice() != null) {
            Query byMinPrice = RangeQuery.of(r -> r
                    .number(n -> n
                            .field("basicInfo.price")
                            .gte(carSearchRequest.getMinPrice()))
            )._toQuery();
            filters.add(byMinPrice);
        }
        if (carSearchRequest.getMaxPrice() != null) {
            Query byMaxPrice = RangeQuery.of(r -> r
                    .number(n -> n
                            .field("basicInfo.price")
                            .lte(carSearchRequest.getMaxPrice()))
            )._toQuery();
            filters.add(byMaxPrice);
        }
        // Fuel and Efficiency filters
        if (carSearchRequest.getFuelAndEfficiency() != null) {
            CarSearchRequest.FuelAndEfficiencyDto fuelEfficiency = carSearchRequest.getFuelAndEfficiency();
            if (fuelEfficiency.getFuelType() != null) {
                filters.add(Query.of(q -> q.term(t -> t.field("fuelAndEfficiency.fuelType").value(fuelEfficiency.getFuelType()))));
            }
            if (fuelEfficiency.getMileage() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("fuelAndEfficiency.mileage")
                                .gte(fuelEfficiency.getMileage()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (fuelEfficiency.getFuelCapacity() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("fuelAndEfficiency.fuelCapacity")
                                .gte(fuelEfficiency.getFuelCapacity()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (fuelEfficiency.getRange() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("fuelAndEfficiency.range")
                                .gte(fuelEfficiency.getRange()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
        }

        // Performance filters
        if (carSearchRequest.getPerformance() != null) {
            CarSearchRequest.PerformanceDto performance = carSearchRequest.getPerformance();
            if (performance.getEngine() != null) {
                filters.add(Query.of(q -> q.term(t -> t.field("performance.engine").value(performance.getEngine()))));
            }
            if (performance.getHorsepower() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("performance.horsepower")
                                .gte(Double.valueOf(performance.getHorsepower())))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (performance.getTorque() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("performance.torque")
                                .gte(Double.valueOf(performance.getTorque())))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (performance.getTopSpeed() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("performance.topSpeed")
                                .gte(Double.valueOf(performance.getTopSpeed())))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (performance.getAcceleration() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("performance.acceleration")
                                .gte(performance.getAcceleration()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
        }

        // Transmission and Drivetrain filters
        if (carSearchRequest.getTransmissionAndDrivetrain() != null) {
            CarSearchRequest.TransmissionAndDrivetrainDto transmission = carSearchRequest.getTransmissionAndDrivetrain();
            if (transmission.getTransmission() != null) {
                filters.add(Query.of(q -> q.term(t -> t.field("transmissionAndDrivetrain.transmission").value(transmission.getTransmission()))));
            }
            if (transmission.getDrivetrain() != null) {
                filters.add(Query.of(q -> q.term(t -> t.field("transmissionAndDrivetrain.drivetrain").value(transmission.getDrivetrain()))));
            }
            if (transmission.getGearbox() != null) {
                filters.add(Query.of(q -> q.term(t -> t.field("transmissionAndDrivetrain.gearbox").value(transmission.getGearbox()))));
            }
        }

        // Dimensions and Weight filters
        if (carSearchRequest.getDimensionsAndWeight() != null) {
            CarSearchRequest.DimensionsAndWeightDto dimensions = carSearchRequest.getDimensionsAndWeight();
            if (dimensions.getLength() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("dimensionsAndWeight.length")
                                .gte(dimensions.getLength()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (dimensions.getWidth() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("dimensionsAndWeight.width")
                                .gte(dimensions.getWidth()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (dimensions.getHeight() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("dimensionsAndWeight.height")
                                .gte(dimensions.getHeight()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (dimensions.getWheelbase() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("dimensionsAndWeight.wheelbase")
                                .gte(dimensions.getWheelbase()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (dimensions.getGroundClearance() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("dimensionsAndWeight.groundClearance")
                                .gte(dimensions.getGroundClearance()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (dimensions.getCurbWeight() != null) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("dimensionsAndWeight.curbWeight")
                                .gte(dimensions.getCurbWeight()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
        }

        // Safety features filters
        if (carSearchRequest.getSafetyFeatures() != null) {
            CarSearchRequest.SafetyFeaturesDto safety = carSearchRequest.getSafetyFeatures();
            if (safety.getSafetyRating() != null) {
                filters.add(Query.of(q -> q.term(t -> t.field("safetyFeatures.safetyRating").value(safety.getSafetyRating()))));
            }
            if (safety.getAirbags() != null && safety.getAirbags() > 0) {
                filters.add(Query.of(q -> q.term(t -> t.field("safetyFeatures.airbags").value(safety.getAirbags()))));
            }
            // Add similar term filters for other safety features
        }

        // EV specifications filters
        if (carSearchRequest.getEvSpecifications() != null) {
            CarSearchRequest.EVSpecificationsDto ev = carSearchRequest.getEvSpecifications();
            if (ev.getBatteryCapacity() != null && ev.getBatteryCapacity() > 0) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("evSpecifications.batteryCapacity")
                                .gte(ev.getBatteryCapacity()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (ev.getChargingTime() != null && ev.getChargingTime() > 0) {
                Query byMaxPrice = RangeQuery.of(r -> r
                        .number(n -> n
                                .field("evSpecifications.chargingTime")
                                .gte(ev.getChargingTime()))
                )._toQuery();
                filters.add(byMaxPrice);
            }
            if (ev.getFastCharging() != null) {
                filters.add(Query.of(q -> q.term(t -> t.field("evSpecifications.fastCharging").value(ev.getFastCharging()))));
            }
        }

        // Construct BoolQuery with all filters
        Query boolQuery = Query.of(b -> b.bool(BoolQuery.of(bq -> bq.filter(filters))));

        // Create SearchRequest and execute search
    SearchRequest searchRequest = SearchRequest.of(s -> s
            .index("cars")
            .query(boolQuery)
            .size(100) // Customize size if needed
    );
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
