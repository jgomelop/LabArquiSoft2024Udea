package com.example.vuelo.service;

import com.example.vuelo.model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlightService {

    // ============================================================================================
    // Funciones auxiliares para las expresiones lambda que se usarán para filtrar elementos
    // de las búsquedas en los Streams.

    /**
     * Funcion auxiliar para verificar si la fecha está en el rango correcto
     * @param dateToCheck Fecha para verificar
     * @param startDate Fecha límite inferior
     * @param endDate Fecha límite superior
     * @return resultado de la verificación.
     */
    private boolean isDateInRange(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate){
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }

    /**
     * Función auxiliar para verificar igualdad entre cadenas, eliminando los espacios
     * en blanco entre estas
     * @param str Primera cadena
     * @param strToTest Segunda cadena
     * @return resultado de la validación.
     */
    private boolean isStringEqual(String str, String strToTest){
        String strClean  = str.replaceAll("\\s+","");
        String strToTestClean = strToTest.replaceAll("\\s+","");
        return strClean.equalsIgnoreCase(strToTestClean);
    }
    // ========================================================================================

    // ========================================================================================
    // Funciones para generar expresiones lambda
    /**
     * Expresión lambda para filtrar elementos dentro de un rango de fechas
     * dentro de Stream.filter()
     * @param startDate Fecha límite inferior
     * @param endDate Fecha límite superior
     */
    private Predicate<Flight> dateRangeFilterExpression(String startDate, String endDate){
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        return flight -> isDateInRange(flight.getDepartureDate(), parsedStartDate, parsedEndDate);
    }

    /**
     * Expresión lambda para buscar elementos por ORIGEN del vuelo
     * @param origin Origen del vuelo
     * @return Expresión lambda
     */
    private Predicate<Flight> originFilterExpression(String origin){
        return flight -> isStringEqual(flight.getOrigin(), origin);
    }

    /**
     * Expresión lambda para buscar elementos por DESTINO del vuelo
     * @param destination destino del vuelo
     * @return Expresión lambda
     */
    private Predicate<Flight> destinationFilterExpression(String destination){
        return flight -> isStringEqual(flight.getDestination(), destination);
    }

    /**
     * Expresión lambda para buscar elementos por AEROLÍNEA del vuelo
     * @param airline Aerolínea
     * @return Expresión lambda
     */
    private Predicate<Flight> airlineFilterExpression(String airline){
        return flight -> isStringEqual(flight.getAirline(), airline);
    }

    /**
     * Expresión lambda para buscar elementos por PRECIO MAXIMO del vuelo
     * @param maxPrice Precio máximo
     * @return Expresión lambda
     */
    private Predicate<Flight> maxPriceFilterExpression(double maxPrice) {
        return flight -> flight.getPrice() <= maxPrice;
    }

    /**
     * Expresión lambda para buscar elementos por RANGO DE PRECIOS
     * @param priceRange Rango de precios: "precio_min-precio_max"
     * @return Expresión lambda
     */
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
    // =================================================================

    // =================================================================
    //

    /**
     * Este método prepara el Stream de vuelos para luego ser llamado por los
     * servicios de búsqueda.
     * @return Stream de vuelos
     */
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
    //
    // =================================================================

    // =================================================================
    // SERVICIOS
    /**
     * Servicio de búsqueda entre un rango de fechas.
     * @param startDate String con Fecha mínima
     * @param endDate String con Fecha máxima
     * @return Lista con listas de los vuelos que están dentro del rango de fechas.
     */
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

    /**
     * Servicio de búsqueda dado un ORIGEN y un DESTINO
     * @param origin String con Origen del vuelo
     * @param destination String con Destino del vuelo
     * @return Lista con listas de los vuelos que cumplen el criterio especificado.
     */
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

    /**
     * Servicio de búsqueda dado un ORIGEN
     * @param origin String con el Origen del vuelo
     * @return Lista con listas de los vuelos que cumplen el criterio especificado.
     */
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

    /**
     * Servicio de búsqueda dado un DESTINO
     * @param destination String con el Origen del vuelo
     * @return Lista con listas de los vuelos que cumplen el criterio especificado.
     */
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


    /**
     * Servicio de búsqueda dado un RANGO DE PRECIOS.
     * @param priceRange String con el Rango de precios con el formato: "minPrice-maxPrice"
     * @return Lista con listas de los vuelos que cumplen el criterio especificado.
     */
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

    /**
     * Servicio de búsqueda dado un PRECIO MÁXIMO
     * @param maxPrice String con el Precio Máximo
     * @return Lista con listas de los vuelos que cumplen el criterio especificado.
     */
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

    /**
     * Servicio de búsqueda dado una AEROLÍNEA
     * @param airline String con la aerolínea
     * @return Lista con listas de los vuelos que cumplen el criterio especificado.
     */
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
    // ==================================================================================
}
