import { useEffect, useState } from 'react';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';
import React from "react";
import { Routes, Route, Navigate } from "react-router-dom"
import SignUp from "./sign-up/SignUp";
import SignInSide from "./sign-in-side/SignInSide";
import Checkout from "./checkout/Checkout";
import Dashboard from './user/dashboard/Dashboard';
import Events from "./events/Events";
import EventsDashboard from "./events/EventsDashboard";
import UserDashboard from "./user/UserDashboard";
import VenueList from './venue/VenueList';
import VenueCreate from './venue/VenueCreate';
import VenueEdit from './venue/VenueEdit';
import NotificationsProvider from './crud-dashboard/hooks/useNotifications/NotificationsProvider';
import DialogsProvider from './crud-dashboard/hooks/useDialogs/DialogsProvider';



import { Button } from "@mui/material";

function App() {
  return (
    // <div>
    //   <h1>Hello SEMS!</h1>
    //   <Button variant="contained" color="primary">
    //     Test MUI Button
    //   </Button>
    // </div>
  <NotificationsProvider>
        <DialogsProvider>
        
        

<Routes>
  <Route path="/" element={<Navigate to="/events"/>}/>

  <Route path="/sign-in-side" element={<SignInSide/>}/>
  <Route path="/sign-up" element={<SignUp/>}/>
  
  <Route path="/checkout" element={<Checkout/>}/>
  <Route path="/events" element={<Events/>}/>


  //Events Routing


<Route path="/events-dashboard/*" element={<EventsDashboard/>}/>

//user Routing
<Route path="/dashboard" element={<Dashboard/>}/>

<Route path="/user/*" element={<UserDashboard/>}/>

//venue
<Route path="venue" element={<VenueList />} />
<Route path="venue/new" element={<VenueCreate />}/>
<Route path="venue/:venueId/edit" element={<VenueEdit />} />
  
</Routes>
</DialogsProvider>
        </NotificationsProvider>

  );
}

export default App;