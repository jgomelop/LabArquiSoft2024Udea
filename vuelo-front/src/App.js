import { useState } from 'react';
import { Container, Typography } from '@mui/material';
import { FlightsTable, Form, Message, Spinner } from './components';

function App() {
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(false);

  const showMessage = flights.length <= 0 && !loading;
  const showTable = flights.length > 0;

  return (
    <Container>
      <Typography variant="h1" sx={{ fontSize: '2rem', marginBottom: '2rem' }}>
        Buscador de vuelos
      </Typography>

      <Form setFlights={setFlights} setLoading={setLoading} />

      <Typography variant="h2" sx={{ fontSize: '1.5rem', margin: '2rem 0' }}>
        Resultados de la busqueda
      </Typography>

      {loading && <Spinner />}
      {showMessage && (
        <Message messageType="info">
          ¡Listo para despegar! ¿Tienes en mente el origen, destino, fechas, rango de precios, tope máximo o alguna aerolínea preferida? Cuéntame y encontraremos los vuelos ideales para ti.
        </Message>
      )}
      {showTable && <FlightsTable flights={flights} />}
    </Container>
  );
}
export default App;
