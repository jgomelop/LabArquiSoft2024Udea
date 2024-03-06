package com.example.vuelo.controller;

import com.example.vuelo.model.Flight;
import com.example.vuelo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
public class FlightController {

    // Inyección de dependencias
    @Autowired
    private FlightService flightService;

    private final String ORIGIN = "origin";
    private final String DESTINATION = "destination";
    private final String START_DATE = "startDate";
    private final String END_DATE = "endDate";

    // Debo mapear cada método para el protocolo HTTP
    @GetMapping("/search")
    public List<List<Flight>> searchFlights(
            // fechas van sobre la ruta de la URL
            // Podemos usar QueryParam o RequestPAram
            @RequestParam() Map<String, String> requestParams
            ) throws NoSuchMethodException {

        if (requestParams.containsKey(ORIGIN) && requestParams.containsKey(DESTINATION)){
            return flightService.searchFlightsByOriginDestination(
                    requestParams.get(ORIGIN),
                    requestParams.get(DESTINATION)
            );
        } else if (requestParams.containsKey(START_DATE) && requestParams.containsKey(END_DATE)) {
            return flightService.searchFlightsByDates(requestParams.get(START_DATE), requestParams.get(END_DATE));
        } else if (requestParams.containsKey(ORIGIN)) {
            return flightService.searchFlightsByOrigin(requestParams.get(ORIGIN));
        } else {
            throw new NoSuchMethodException();
        }

    }
}
