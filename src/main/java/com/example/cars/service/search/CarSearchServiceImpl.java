package com.example.cars.service.search;

import com.example.cars.dto.CarSearchRequest;
import com.example.cars.model.Car;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
//import org.springframework.data.elasticsearch.client.erhlc;


@Service
public class CarSearchServiceImpl implements ICarSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * Perform a flexible search on the Car index based on provided filters.
     *
     * @param searchRequest The DTO containing search criteria.
     * @return SearchHits<Car> The filtered search results.
     */
    @Autowired
    public SearchResponse searchCars(CarSearchRequest searchRequest) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // Add filters to the query based on provided parameters
        if (searchRequest.getMake() != null) {
            boolQuery.filter(QueryBuilders.termQuery("basicInfo.make", searchRequest.getMake()));
        }
        if (searchRequest.getModel() != null) {
            boolQuery.filter(QueryBuilders.termQuery("basicInfo.model", searchRequest.getModel()));
        }
        if (searchRequest.getYear() != null) {
            boolQuery.filter(QueryBuilders.termQuery("basicInfo.year", searchRequest.getYear()));
        }
        if (searchRequest.getMinPrice() != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("basicInfo.price").gte(searchRequest.getMinPrice()));
        }
        if (searchRequest.getMaxPrice() != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("basicInfo.price").lte(searchRequest.getMaxPrice()));
        }

        // Add Fuel and Efficiency filters
        if (searchRequest.getFuelAndEfficiency() != null) {
            CarSearchRequest.FuelAndEfficiencyDto fuelEfficiency = searchRequest.getFuelAndEfficiency();
            if (fuelEfficiency.getFuelType() != null) {
                boolQuery.filter(QueryBuilders.termQuery("fuelAndEfficiency.fuelType", fuelEfficiency.getFuelType()));
            }
            if (fuelEfficiency.getMileage() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("fuelAndEfficiency.mileage").gte(fuelEfficiency.getMileage()));
            }
            if (fuelEfficiency.getFuelCapacity() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("fuelAndEfficiency.fuelCapacity").gte(fuelEfficiency.getFuelCapacity()));
            }
            if (fuelEfficiency.getRange() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("fuelAndEfficiency.range").gte(fuelEfficiency.getRange()));
            }
        }

        // Add Performance filters
        if (searchRequest.getPerformance() != null) {
            CarSearchRequest.PerformanceDto performance = searchRequest.getPerformance();
            if (performance.getEngine() != null) {
                boolQuery.filter(QueryBuilders.termQuery("performance.engine", performance.getEngine()));
            }
            if (performance.getHorsepower() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("performance.horsepower").gte(performance.getHorsepower()));
            }
            if (performance.getTorque() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("performance.torque").gte(performance.getTorque()));
            }
            if (performance.getTopSpeed() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("performance.topSpeed").gte(performance.getTopSpeed()));
            }
            if (performance.getAcceleration() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("performance.acceleration").lte(performance.getAcceleration()));
            }
        }

        // Add Transmission and Drivetrain filters
        if (searchRequest.getTransmissionAndDrivetrain() != null) {
            CarSearchRequest.TransmissionAndDrivetrainDto transmission = searchRequest.getTransmissionAndDrivetrain();
            if (transmission.getTransmission() != null) {
                boolQuery.filter(QueryBuilders.termQuery("transmissionAndDrivetrain.transmission", transmission.getTransmission()));
            }
            if (transmission.getDrivetrain() != null) {
                boolQuery.filter(QueryBuilders.termQuery("transmissionAndDrivetrain.drivetrain", transmission.getDrivetrain()));
            }
            if (transmission.getGearbox() != null) {
                boolQuery.filter(QueryBuilders.termQuery("transmissionAndDrivetrain.gearbox", transmission.getGearbox()));
            }
        }

        // Add Dimensions and Weight filters
        if (searchRequest.getDimensionsAndWeight() != null) {
            CarSearchRequest.DimensionsAndWeightDto dimensions = searchRequest.getDimensionsAndWeight();
            if (dimensions.getLength() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("dimensionsAndWeight.length").gte(dimensions.getLength()));
            }
            if (dimensions.getWidth() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("dimensionsAndWeight.width").gte(dimensions.getWidth()));
            }
            if (dimensions.getHeight() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("dimensionsAndWeight.height").gte(dimensions.getHeight()));
            }
            if (dimensions.getWheelbase() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("dimensionsAndWeight.wheelbase").gte(dimensions.getWheelbase()));
            }
            if (dimensions.getGroundClearance() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("dimensionsAndWeight.groundClearance").gte(dimensions.getGroundClearance()));
            }
            if (dimensions.getCurbWeight() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("dimensionsAndWeight.curbWeight").gte(dimensions.getCurbWeight()));
            }
        }

        // Add safety features filters
        if (searchRequest.getSafetyFeatures() != null) {
            CarSearchRequest.SafetyFeaturesDto s = searchRequest.getSafetyFeatures();
            if (s.getSafetyRating() != null) {
                boolQuery.filter(QueryBuilders.termQuery("safetyFeatures.safetyRating", s.getSafetyRating()));
            }
            if (s.getAirbags() != null && s.getAirbags() > 0) {
                boolQuery.filter(QueryBuilders.termQuery("safetyFeatures.airbags", s.getAirbags()));
            }
            if (s.getAbs() != null) {
                boolQuery.filter(QueryBuilders.termQuery("safetyFeatures.abs", s.getAbs()));
            }
            if (s.getTractionControl() != null) {
                boolQuery.filter(QueryBuilders.termQuery("safetyFeatures.tractionControl", s.getTractionControl()));
            }
            if (s.getLaneAssist() != null) {
                boolQuery.filter(QueryBuilders.termQuery("safetyFeatures.laneAssist", s.getLaneAssist()));
            }
            if (s.getBlindSpotMonitor() != null) {
                boolQuery.filter(QueryBuilders.termQuery("safetyFeatures.blindSpotMonitor", s.getBlindSpotMonitor()));
            }
            if (s.getAdaptiveCruiseControl() != null) {
                boolQuery.filter(QueryBuilders.termQuery("safetyFeatures.adaptiveCruiseControl", s.getAdaptiveCruiseControl()));
            }
        }

        // Add technology and comfort filters
        if (searchRequest.getTechnologyAndComfort() != null) {
            CarSearchRequest.TechnologyAndComfortDto t = searchRequest.getTechnologyAndComfort();
            if (t.getInfotainmentSystem() != null) {
                boolQuery.filter(QueryBuilders.termQuery("technologyAndComfort.infotainmentSystem", t.getInfotainmentSystem()));
            }
            if (t.getClimateControl() != null) {
                boolQuery.filter(QueryBuilders.termQuery("technologyAndComfort.climateControl", t.getClimateControl()));
            }
            if (t.getHeatedSeats() != null) {
                boolQuery.filter(QueryBuilders.termQuery("technologyAndComfort.heatedSeats", t.getHeatedSeats()));
            }
            if (t.getSunroof() != null) {
                boolQuery.filter(QueryBuilders.termQuery("technologyAndComfort.sunroof", t.getSunroof()));
            }
            if (t.getParkingAssist() != null) {
                boolQuery.filter(QueryBuilders.termQuery("technologyAndComfort.parkingAssist", t.getParkingAssist()));
            }
            if (t.getHeadUpDisplay() != null) {
                boolQuery.filter(QueryBuilders.termQuery("technologyAndComfort.headUpDisplay", t.getHeadUpDisplay()));
            }
        }

        // Add EV specifications filters
        if (searchRequest.getEvSpecifications() != null) {
            CarSearchRequest.EVSpecificationsDto e = searchRequest.getEvSpecifications();
            if (e.getBatteryCapacity() != null && e.getBatteryCapacity() > 0) {
                boolQuery.filter(QueryBuilders.rangeQuery("evSpecifications.batteryCapacity").gte(e.getBatteryCapacity()));
            }
            if (e.getChargingTime() != null && e.getChargingTime() > 0) {
                boolQuery.filter(QueryBuilders.rangeQuery("evSpecifications.chargingTime").lte(e.getChargingTime()));
            }
            if (e.getFastCharging() != null) {
                boolQuery.filter(QueryBuilders.termQuery("evSpecifications.fastCharging", e.getFastCharging()));
            }
            if (e.getRegenerativeBraking() != null) {
                boolQuery.filter(QueryBuilders.termQuery("evSpecifications.regenerativeBraking", e.getRegenerativeBraking()));
            }
        }

        // Build the query with filters and pagination
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery((Query) boolQuery)
                .withPageable(PageRequest.of(0, 10)) // Pagination
                .build();

        // Execute the query
        return restHighLevelClient.search(searchQuery);
    }
}
