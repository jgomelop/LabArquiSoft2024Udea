package com.example.vuelo.service;

import com.example.vuelo.model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.vuelo.utils.FilterSearchParams;


@Service
public class FlightService {

    // Ruta del archivo
    private final String filePath = "classpath:flights.json";

    // ======================================================
    // Functions to check in for filters
    private boolean isDateInRange(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate){
        // Verifica si la fecha está en el rango correcto
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }

    private boolean isStringEqual(String str, String strToTest){
        return str.equalsIgnoreCase(strToTest);
    }

    // Predicates for lambda expressions in filters
    private Predicate<Flight> dateRangeFilterExpression(String startDate, String endDate){
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        return flight -> isDateInRange(flight.getDepartureDate(), parsedStartDate, parsedEndDate);
    }
    private Predicate<Flight> originFilterExpression(String origin){
        return flight -> isStringEqual(flight.getOrigin(), origin);
    }

    private Predicate<Flight> destinationFilterExpression(String destination){
        return flight -> isStringEqual(flight.getDestination(), destination);
    }

    private Predicate<Flight> airlineFilterExpression(String airline){
        return flight -> isStringEqual(flight.getAirline(), airline);
    }

    private Predicate<Flight> maxPriceFilterExpression(double maxPrice) {
        return flight -> flight.getPrice() <= maxPrice;
    }


    // =================================================================
    // Actual methods

    private Stream<Flight> prepareFlightServiceStream(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("flights.json");
            if (inputStream != null) {
                Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                return  Arrays.stream(flights);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo JSON",e);
        }
    }

    public List<List<Flight>> searchFlightsByDates(String startDate, String endDate) {
        try {
            return Arrays.asList(
                    prepareFlightServiceStream()
                            .filter(dateRangeFilterExpression(startDate,endDate))
                            .collect(Collectors.toList()));
        } catch (NullPointerException e) {
            throw new RuntimeException("Error filtering by dates.", e);
        }
    }

    public List<List<Flight>> searchFlightsByOriginDestination(String origin, String destination){
        try {
            return Arrays.asList(
                    prepareFlightServiceStream()
                            .filter(originFilterExpression(origin))
                            .filter(destinationFilterExpression(destination))
                            .collect(Collectors.toList()));
        } catch (NullPointerException e) {
            throw new RuntimeException("Error filtering by origin or destination.",e);
        }
    }

    public List<List<Flight>> searchFlightsByOrigin(String origin){
        try {
            return Arrays.asList(
                    prepareFlightServiceStream()
                            .filter(originFilterExpression(origin))
                            .collect(Collectors.toList()));
        } catch (NullPointerException e) {
            throw new RuntimeException("Error filtering by origin", e);
        }
    }

    public List<List<Flight>> searchFlightsByDestination(String destination){
        try {
            return Arrays.asList(
                    prepareFlightServiceStream()
                            .filter(destinationFilterExpression(destination))
                            .collect(Collectors.toList()));
        } catch (NullPointerException e) {
            throw new RuntimeException("Error filtering by destination", e);
        }
    }

    private Predicate<Flight> priceRangeFilterExpression(String priceRange) {
        String[] prices = priceRange.split("-");
        try {
            double minPrice = Double.parseDouble(prices[0]);
            double maxPrice = Double.parseDouble(prices[1]);
            return flight -> flight.getPrice() >= minPrice && flight.getPrice() <= maxPrice;
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e ) {
            throw new IllegalArgumentException("No valid price format" + priceRange);
        }
    }

    public List<List<Flight>> searchFlightsByPriceRange(String priceRange) {
        try {
            return Arrays.asList(
                    prepareFlightServiceStream()
                            .filter(priceRangeFilterExpression(priceRange))
                            .collect(Collectors.toList()));
        } catch (NullPointerException e) {
            throw new RuntimeException("Error filtering by price range.", e);
        }
    }

    public List<List<Flight>> searchFlightsByMaxPrice(double maxPrice) {
        try {
            return Arrays.asList(
                    prepareFlightServiceStream()
                            .filter(maxPriceFilterExpression(maxPrice))
                            .collect(Collectors.toList()));
        } catch (NullPointerException e) {
            throw new RuntimeException("Error filtering by max price.", e);
        }
    }

    public List<List<Flight>> searchFlightsByAirline(String airline) {
        try {
            return Arrays.asList(
                    prepareFlightServiceStream()
                            .filter(airlineFilterExpression(airline))
                            .collect(Collectors.toList()));
        } catch (NullPointerException e) {
            throw new RuntimeException("Error filtering by airline.", e);
        }
    }
}
