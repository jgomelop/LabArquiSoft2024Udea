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

    // =================================================================
    // Actual methods


    public List<List<Flight>> searchFlightsByDates(String startDate, String endDate) {
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("flights.json");
             if (inputStream != null) {
                 Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                 return Arrays.asList(
                         Arrays.stream(flights)
                                 .filter(dateRangeFilterExpression(startDate,endDate))
                                 .collect(Collectors.toList())
                 );
             } else {
                 return null;
             }

         } catch (IOException e) {
             throw new RuntimeException("Error leyendo archivo JSON",e);
        }
    }

    public List<List<Flight>> searchFlightsByOriginDestination(String origin, String destination){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("flights.json");
            if (inputStream != null) {
                Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                return Arrays.asList(
                        Arrays.stream(flights)
                                .filter(originFilterExpression(origin))
                                .filter(destinationFilterExpression(destination))
                                .collect(Collectors.toList())
                );
            } else {
                return null;
            }

        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo JSON",e);
        }
    }

    public List<List<Flight>> searchFlightsByOrigin(String origin){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("flights.json");
            if (inputStream != null) {
                Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                return Arrays.asList(
                        Arrays.stream(flights)
                                .filter(originFilterExpression(origin))
                                .collect(Collectors.toList())
                );
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo JSON",e);
        }
    }

}
