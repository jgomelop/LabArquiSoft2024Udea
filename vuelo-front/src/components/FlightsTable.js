import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@mui/material';

const cellStyles = { color: '#5D3FD3', fontWeight: 'bold' };

export const FlightsTable = ({ flights = [] }) => {
  return (
    <TableContainer sx={{ maxWidth: 1200 }} component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="center" sx={cellStyles}>
              Aerolínea
            </TableCell>
            <TableCell align="center" sx={cellStyles}>
              Origen
            </TableCell>
            <TableCell align="center" sx={cellStyles}>
              Destino
            </TableCell>
            <TableCell align="center" sx={cellStyles}>
              Fecha de Salida
            </TableCell>
            <TableCell align="center" sx={cellStyles}>
              Fecha de Llegada
            </TableCell>
            <TableCell align="center" sx={cellStyles}>
              Precio
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {flights?.map((flight) => (
            <TableRow
              key={flight.id}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell align="center">{flight.airline}</TableCell>
              <TableCell align="center">{flight.origin}</TableCell>
              <TableCell align="center">{flight.destination}</TableCell>
              <TableCell align="center">{flight.departureDate}</TableCell>
              <TableCell align="center">{flight.arrivalDate}</TableCell>
              <TableCell align="center">{flight.price}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};
