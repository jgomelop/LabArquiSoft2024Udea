import { Box, CircularProgress } from '@mui/material';

export const Spinner = () => {
  return (
    <Box sx={{ display: 'flex', marginBottom: '2rem' }}>
      <CircularProgress />
    </Box>
  );
};
