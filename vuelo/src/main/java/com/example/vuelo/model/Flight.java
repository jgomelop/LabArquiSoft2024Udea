package com.example.vuelo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 * Ejemplo de Modelo:
 *     "id": 1,
 *     "airline": "Airways Inc.",
 *     "origin": "Bogota",
 *     "destination": "Cali",
 *     "departureDate": "2024-01-01T08:00:00",
 *     "arrivalDate": "2024-01-01T10:00:00",
 *     "price": 159
 */
public class Flight {
    private int id;
    private String airline;
    private String origin;
    private String destination;
    private int price;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate departureDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate arrivalDate;

    public Flight() {
    }

    public Flight(int id, String airline, String origin, String destination, int price, LocalDate departureDate, LocalDate arrivalDate) {
        this.id = id;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
