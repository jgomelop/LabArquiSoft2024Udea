import { Button, Grid, TextField, MenuItem, Select, FormControl, InputLabel, InputAdornment } from '@mui/material';
import axios from 'axios';
import { useState } from 'react';
import FlightIcon from '@mui/icons-material/Flight';
import SportsScoreIcon from '@mui/icons-material/SportsScore';
import TourIcon from '@mui/icons-material/Tour';
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';

export const Form = ({ setLoading, setFlights }) => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [origin, setOrigin] = useState('');
  const [airline, setAirline] = useState('');
  const [destination, setDestination] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [minPrice, setMinPrice] = useState('');

  const handleSearch = async (e) => {
    e.preventDefault();

    // Validaciones

    setLoading(true);
    try {
      let response;
      if (origin.trim() !== '' & destination.trim() === '') {
        // Si origin no está vacío, enviar solicitud solo con origin
        response = await axios.get(
          `http://localhost:8080/flights/search?origin=${origin}`
        );

      } else if (origin.trim() !== '' & destination !== '') {
        // Si destino no está vacío, enviar solicitud solo con destino
        response = await axios.get(
          `http://localhost:8080/flights/search?origin=${origin}&destination=${destination}`
        );

      } else if (origin.trim() === '' & destination !== '') {
        // Si origen no está vacío y tampoco destino, enviar solicitud con ambos parametros
        response = await axios.get(
          `http://localhost:8080/flights/search?destination=${destination}`
        );

      } else if (airline.trim() !== '') {
        // Si airline no está vacío, enviar solicitud solo con airline
        response = await axios.get(
          `http://localhost:8080/flights/search?airline=${airline}`
        );

      } else if (maxPrice.trim() !== '' & minPrice==='') {
        // Si maxPrice no está vacío, enviar solicitud solo con precio máximo
        response = await axios.get(
          `http://localhost:8080/flights/search?maxPrice=${maxPrice}`
        );
      
      } else if (maxPrice.trim() !== '' & minPrice!=='') {
        // Si maxPrice no está vacío y minPrice tampoco, enviar solicitud con ambos precios
        response = await axios.get(
          `http://localhost:8080/flights/search?rangePrice=${minPrice}-${maxPrice}`
        );
      
      } else {
        // Si startDate no está vacío y airline está vacío, enviar solicitud con ambas fechas
        response = await axios.get(
          `http://localhost:8080/flights/search?startDate=${startDate}&endDate=${endDate}`
        );
      }
      setFlights(response.data[0]);
      console.log(response.data);
    } catch (error) {
      console.log(error.message);
    }
    setLoading(false);
  };

  return (
    <form onSubmit={handleSearch}>
      {/* Fila */}
      <Grid container spacing={1}>
        <Grid item xs={12} sm={6}>
          <TextField
            fullWidth
            label="Fecha de Inicio"
            type="date"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
            InputLabelProps={{
              shrink: true,
            }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            fullWidth
            label="Fecha Fin"
            type="date"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
            InputLabelProps={{
              shrink: true,
            }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <InputLabel id="origin-label">Origen<TourIcon/></InputLabel>
            <Select
              labelId="origin-label"
              value={origin}
              onChange={(e) => setOrigin(e.target.value)}
            >
              <MenuItem value="">Seleccionar Origen</MenuItem>
              <MenuItem value="Bogota">Bogota</MenuItem>
              <MenuItem value="Cali">Cali</MenuItem>
              <MenuItem value="Medellin">Medellin</MenuItem>
              <MenuItem value="Santa Marta">Santa Marta</MenuItem>
              <MenuItem value="Cartagena">Cartagena</MenuItem>
              <MenuItem value="Miami">Miami</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <InputLabel id="destination-label">Destino<SportsScoreIcon/></InputLabel>
            <Select
              labelId="destination-label"
              value={destination}
              onChange={(e) => setDestination(e.target.value)}
            >
              <MenuItem value="">Seleccionar Destino </MenuItem>
              <MenuItem value="Bogota">Bogota</MenuItem>
              <MenuItem value="Cali">Cali</MenuItem>
              <MenuItem value="Medellin">Medellin</MenuItem>
              <MenuItem value="Santa Marta">Santa Marta</MenuItem>
              <MenuItem value="Cartagena">Cartagena</MenuItem>
              <MenuItem value="Miami">Miami</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <InputLabel id="airline-label">Aerolínea<FlightIcon/></InputLabel>
            <Select
              labelId="airline-label"
              value={airline}
              onChange={(e) => setAirline(e.target.value)}
            >
              <MenuItem value="">Seleccionar aerolínea</MenuItem>
              <MenuItem value="Airways Inc.">Airways Inc.</MenuItem>
              <MenuItem value="Avianca">Avianca</MenuItem>
              <MenuItem value="JetFly">JetFly</MenuItem>
              <MenuItem value="Latam">Latam</MenuItem>
              <MenuItem value="Wingo">Wingo</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
    <Grid container spacing={1}>
      <Grid item xs={6}>
        <TextField
          fullWidth
          label="Precio mínimo"
          type="number"
          value={minPrice}
          onChange={(e) => setMinPrice(e.target.value)}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <MonetizationOnIcon />
              </InputAdornment>
            ),
          }}
        />
      </Grid>
      <Grid item xs={6}>
        <TextField
          fullWidth
          label="Precio máximo"
          type="number"
          value={maxPrice}
          onChange={(e) => setMaxPrice(e.target.value)}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <MonetizationOnIcon />
              </InputAdornment>
            ),
          }}
        />
      </Grid>
    </Grid>
  </Grid>
      </Grid>
      {/* Fin fila */}

      <Button
        variant="contained"
        type="submit"
        style={{ marginTop: '20px', backgroundColor: '#5D3FD3' }}
      >
        Buscar
      </Button>
    </form>
  );
};