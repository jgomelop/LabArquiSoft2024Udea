import React, { useState } from "react";
import axios from "axios";

function FlightSearch() { // Nombre de función debe ser igual al del archivo?

    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [flights, setFlights] = useState([]);
    const [loading, setLoading] = useState(false);

    const handleSearch = async () => {
        setLoading(true);
        try {
            const response = await
            // notar el tipo de comillas. Debe ser ``
            // https no funciona
                axios.get(`http://localhost:8080/flights/search?startDate=${startDate}&endDate=${endDate}`); 
            setFlights(response.data);
            console.log(response.data);

        } catch (error) {
            //console.log('Error en la carga de datos de vuelo')
            console.log(error)
            
        }
        setLoading(false);
    };


    return (
        <div>
            <h2>Buscar Vuelos</h2>
            <div>
                <label>Fecha de Inicio:</label>
                <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />

            </div>

            <div>
                <label>Fecha Fin:</label>
                <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />

            </div>

            <button onClick={handleSearch}>Buscar </button>

            {loading && <p>Cargando....</p>}

            <h2>Resultados de la busqueda..</h2>
            {flights.length > 0 ? (
                <ul>
                    {flights.map((flight, index) => (
                        <li key={index}>
                            {Object.keys(flight).map(key => (
                                <div key={key}>
                                    {`${key}: ${typeof flight[key] === 'object' ? JSON.stringify(flight[key]) : flight[key]}`}
                                </div>

                            ))}

                        </li>
                    ))}
                </ul>
            ) : (

                <p>No se encontraron vuelos</p>
            )}
        </div>
    );

}
export default FlightSearch;