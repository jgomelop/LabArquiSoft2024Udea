package com.example.vuelo.service;

import com.example.vuelo.model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    // Ruta del archivo
    private final String filePath = "classpath:flights.json";

    private boolean isDateInRange(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate){
        // Verifica si la fecha está en el rango correcto
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }

    // Método de la lógica de búsqueda de vuelos
    public List<List<Flight>> searchFlights(LocalDate startDate, LocalDate endDate) {
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("flights.json");
             if (inputStream != null) {
                 Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                 return Arrays.asList(
                         Arrays.stream(flights)
                                 .filter(flight -> isDateInRange(flight.getDepartureDate(), startDate, endDate))
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
