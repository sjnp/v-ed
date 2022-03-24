import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { AuthProvider } from './context/AuthProvider';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from "react-redux"
import { configureStore } from "@reduxjs/toolkit"
import userReducer from "./redux/user"
import { createTheme, CssBaseline, ThemeProvider } from '@mui/material';

const store = configureStore({
  reducer: {
    user: userReducer,
  }
})

const theme = createTheme();

ReactDOM.render(
  <React.StrictMode>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
    />
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <BrowserRouter>
        <AuthProvider>
          <Provider store={store}>
            <Routes>
              <Route path="/*" element={<App />} />
            </Routes>
          </Provider>
        </AuthProvider>
      </BrowserRouter>
    </ThemeProvider>

  </React.StrictMode>,
  document.getElementById('root')
);
