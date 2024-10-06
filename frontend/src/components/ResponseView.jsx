/* eslint-disable react/prop-types */
import { Snackbar, Alert } from "@mui/material";

const ResponseView = ({handleClose,msg,open}) =>{

    return <Snackbar open={open} autoHideDuration={1000} onClose={handleClose}>
    <Alert
      severity="info"
      variant="filled"
      onClose={handleClose}
      sx={{ width: '100%' }}
    >
      {msg}
    </Alert>
  </Snackbar>
}

export default ResponseView;
