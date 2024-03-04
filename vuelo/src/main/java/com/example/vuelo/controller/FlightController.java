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

@RestController
@RequestMapping("/flights")
public class FlightController {

    // Inyección de dependencias
    @Autowired
    private FlightService flightService;

    // Debo mapear cada método para el protocolo HTTP
    @GetMapping("/search")
    public List<List<Flight>> searchFlights(
            // fechas van sobre la ruta de la URL
            // Podemos usar QueryParam o REquestPAram
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate
            ){
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        return flightService.searchFlights(parsedStartDate,parsedEndDate);
    }
}
