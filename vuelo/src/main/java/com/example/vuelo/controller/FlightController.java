package com.example.vuelo.controller;

import com.example.vuelo.model.Flight;
import com.example.vuelo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
public class FlightController {
    // Nombre de los
    private final String ORIGIN = "origin";
    private  final String DESTINATION = "destination";
    private final String START_DATE = "startDate";
    private final String END_DATE = "endDate";
    private final String PRICE_RANGE = "rangePrice";
    private final String MAX_PRICE = "maxPrice";
    private final String AIRLINE = "airline";

    @Autowired
    private FlightService flightService;

    /**
     * Recibe los parámetros del Front-end
     * como un Map de strings "<nombreParametro>": "<valorParametro>",
     * el cual se desempaqueta por casos de uso, enviándolos al FlightService
     */
    @GetMapping("/search")
    public List<List<Flight>> searchFlights(
            @RequestParam() Map<String, String> requestParams
            ) throws NoSuchMethodException {

        /*
        Se listan algunos casos posibles que se desean implementar.
        Cada caso tiene su propio servicio de búsqueda.
         */
        if (requestParams.containsKey(ORIGIN)
                && requestParams.containsKey(DESTINATION)){
            return flightService.searchFlightsByOriginDestination(
                    requestParams.get(ORIGIN),
                    requestParams.get(DESTINATION));
        }
        else if (requestParams.containsKey(DESTINATION)) {
            return flightService
                    .searchFlightsByDestination(requestParams.get(DESTINATION));
        }
        else if (requestParams.containsKey(START_DATE)
                && requestParams.containsKey(END_DATE)) {
            return flightService
                    .searchFlightsByDates(
                            requestParams.get(START_DATE),
                            requestParams.get(END_DATE));
        }
        else if (requestParams.containsKey(ORIGIN)) {
            return flightService.searchFlightsByOrigin(requestParams.get(ORIGIN));
        }
        else if (requestParams.containsKey(PRICE_RANGE)) {
            String priceRange = requestParams.get(PRICE_RANGE);
            return flightService.searchFlightsByPriceRange(priceRange);
        }
        else if (requestParams.containsKey(MAX_PRICE)) {
            double maxPrice = Double.parseDouble(requestParams.get(MAX_PRICE));
            return flightService.searchFlightsByMaxPrice(maxPrice);
        }
        else if (requestParams.containsKey(AIRLINE)) {
            String airline = requestParams.get(AIRLINE);
            return flightService.searchFlightsByAirline(airline);
        }
        else {
            // Si no se encuentra el parámetro o dicho caso de uso.
            throw new NoSuchMethodException("Invalid parameter.");
        }

    }
}
