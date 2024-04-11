import React from "react";
import { Container, Toolbar, Typography, AppBar } from "@mui/material";
import FileUpload from "./components/FileUpload";
import FilesList from "./components/FilesList";

import { ThemeProvider } from "@mui/material/styles";
import theme from "./theme";
import "./App.css";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6">My DropLite</Typography>
        </Toolbar>
      </AppBar>
      <Container>
        <FileUpload />
        <FilesList />
      </Container>
    </ThemeProvider>
  );
}

export default App;
