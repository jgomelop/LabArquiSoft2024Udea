import logo from './logo.svg';
import './App.css';
import React from 'react';
import FlightSearch from './FlightSearch';

function App() {
  return (

    <div>
      <h1>
        Buscador de vuelos
        <FlightSearch />
      </h1>
    </div>
  );
}

export default App;
