import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import { Button } from "@mui/material";

// === Auth ===
import SignUp from "./sign-up/SignUp";
import SignInSide from "./sign-in-side/SignInSide";

// === Checkout ===
import Checkout from "./checkout/Checkout";

// === User ===
import Dashboard from "./user/dashboard/Dashboard";
import UserDashboard from "./user/UserDashboard";

// === Events ===
import Events from "./events/Events";
import EventsDashboard from "./events/EventsDashboard";

// === Venue ===
import VenueList from "./venue/VenueList";
import VenueCreate from "./venue/VenueCreate";
import VenueEdit from "./venue/VenueEdit";

// === Bookings (your CRUD) ===
import BookingList from "./bookings/BookingList";
import BookingCreate from "./bookings/BookingCreate";
import BookingEdit from "./bookings/BookingEdit";

// === Notifications (your CRUD) ===
import NotificationList from "./notifications/NotificationList";
import NotificationCreate from "./notifications/NotificationCreate";
import NotificationEdit from "./notifications/NotificationEdit";

// === Providers (global context/hooks) ===
import NotificationsProvider from "./crud-dashboard/hooks/useNotifications/NotificationsProvider";
import DialogsProvider from "./crud-dashboard/hooks/useDialogs/DialogsProvider";

function App() {
    return (
        <NotificationsProvider>
            <DialogsProvider>
                <Routes>
                    {/* Default redirect */}
                    <Route path="/" element={<Navigate to="/events" />} />

                    {/* Auth */}
                    <Route path="/sign-in-side" element={<SignInSide />} />
                    <Route path="/sign-up" element={<SignUp />} />

                    {/* Checkout */}
                    <Route path="/checkout" element={<Checkout />} />

                    {/* Events */}
                    <Route path="/events" element={<Events />} />
                    <Route path="/events-dashboard/*" element={<EventsDashboard />} />

                    {/* User */}
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/user/*" element={<UserDashboard />} />

                    {/* Venue */}
                    <Route path="/venue" element={<VenueList />} />
                    <Route path="/venue/new" element={<VenueCreate />} />
                    <Route path="/venue/:venueId/edit" element={<VenueEdit />} />

                    {/* Bookings CRUD */}
                    <Route path="/bookings" element={<BookingList />} />
                    <Route path="/bookings/new" element={<BookingCreate />} />
                    <Route path="/bookings/:id/edit" element={<BookingEdit />} />

                    {/* Notifications CRUD */}
                    <Route path="/notifications" element={<NotificationList />} />
                    <Route path="/notifications/new" element={<NotificationCreate />} />
                    <Route path="/notifications/:id/edit" element={<NotificationEdit />} />
                </Routes>
            </DialogsProvider>
        </NotificationsProvider>
    );
}

export default App;
