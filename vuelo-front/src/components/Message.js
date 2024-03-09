import { Alert } from '@mui/material';

export const Message = ({ messageType, children }) => {
  return <Alert severity={messageType}>{children}</Alert>;
};
