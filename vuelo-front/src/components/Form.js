import { Button, Grid, TextField } from '@mui/material';
import axios from 'axios';
import { useState } from 'react';

export const Form = ({ setLoading, setFlights }) => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleSearch = async (e) => {
    e.preventDefault();

    // Validaciones

    setLoading(true);
    try {
      const response = await // notar el tipo de comillas. Debe ser ``
      // https no funciona
      axios.get(
        `http://localhost:8080/flights/search?startDate=${startDate}&endDate=${endDate}`
      );
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
