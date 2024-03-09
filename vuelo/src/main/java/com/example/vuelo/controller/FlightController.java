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
import com.example.vuelo.utils.FilterSearchParams;

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
            // Podemos usar QueryParam o RequestPAram
            @RequestParam() Map<String, String> requestParams
            ) throws NoSuchMethodException {

        if (requestParams.containsKey(FilterSearchParams.ORIGIN)
                && requestParams.containsKey(FilterSearchParams.DESTINATION)){
            return flightService.searchFlightsByOriginDestination(
                    requestParams.get(FilterSearchParams.ORIGIN),
                    requestParams.get(FilterSearchParams.DESTINATION));
        }
        else if (requestParams.containsKey(FilterSearchParams.START_DATE) && requestParams.containsKey(FilterSearchParams.END_DATE)) {
            return flightService.searchFlightsByDates(requestParams.get(FilterSearchParams.START_DATE), requestParams.get(FilterSearchParams.END_DATE));
        } else if (requestParams.containsKey(FilterSearchParams.ORIGIN)) {
            return flightService.searchFlightsByOrigin(requestParams.get(FilterSearchParams.ORIGIN));
        } else if (requestParams.containsKey(FilterSearchParams.DESTINATION)) {
            return flightService.searchFlightsByDestination(requestParams.get(FilterSearchParams.DESTINATION));
        } else if (requestParams.containsKey(FilterSearchParams.PRICE_RANGE)) {
            String priceRange = requestParams.get(FilterSearchParams.PRICE_RANGE);
            return flightService.searchFlightsByPriceRange(priceRange);
        } else if (requestParams.containsKey(FilterSearchParams.MAX_PRICE)) {
            double maxPrice = Double.parseDouble(requestParams.get(FilterSearchParams.MAX_PRICE));
            return flightService.searchFlightsByMaxPrice(maxPrice);
        } else if (requestParams.containsKey(FilterSearchParams.AIRLINE)) {
            String airline = requestParams.get(FilterSearchParams.AIRLINE);
            return flightService.searchFlightsByAirline(airline);
        } else {
            throw new NoSuchMethodException("Invalid parameter.");
        }

    }
}
