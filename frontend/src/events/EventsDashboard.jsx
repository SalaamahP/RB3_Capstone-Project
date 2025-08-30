import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
//import { createHashRouter, RouterProvider } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
//import DashboardLayout from './components/DashboardLayout';

import NotificationsProvider from '../crud-dashboard/hooks/useNotifications/NotificationsProvider';
import DialogsProvider from '../crud-dashboard/hooks/useDialogs/DialogsProvider';
import AppTheme from '../shared-theme/AppTheme';
import DashboardLayout from '../crud-dashboard/components/DashboardLayout';
import {
  dataGridCustomizations,
  datePickersCustomizations,
  sidebarCustomizations,
  formInputCustomizations,
} from '../crud-dashboard/theme/customizations';

import EventsList from "./pages/EventsList";
import EventsCreate from "./pages/EventsCreate";
import EventsEdit from "./pages/EventsEdit";
import EventsShow from "./pages/EventsShow";




// const router = createHashRouter([
//   {
//     Component: DashboardLayout,
//     children: [
//       {
//         path: '/events',
//         Component: EventsList,
//       },
//       {
//         path: '/events/:eventId',
//         Component: EventsShow,
//       },
//       {
//         path: '/events/new',
//         Component: EventsCreate,
//       },
//       {
//         path: '/events/:eventId/edit',
//         Component: EventsEdit,
//       },
//       // Fallback route for the example routes in dashboard sidebar items
//       {
//         path: '*',
//         Component: EventsList,
//       },
//     ],
//   },
// ]);

const themeComponents = {
  ...dataGridCustomizations,
  ...datePickersCustomizations,
  ...sidebarCustomizations,
  ...formInputCustomizations,
};

export default function EventsDashboard(props) {
  return (
    <AppTheme {...props} themeComponents={themeComponents}>
      <CssBaseline enableColorScheme />
      <NotificationsProvider>
        <DialogsProvider>
          <Routes>
            <Route path="/" element={<EventsList />} />
            <Route path="new" element={<EventsCreate />} />
            <Route path=":employeeId" element={<EventsShow />} />
            <Route path=":employeeId/edit" element={<EventsEdit />} />
            <Route path="*" element={<EventsList />} />
          </Routes>
        </DialogsProvider>
      </NotificationsProvider>
    </AppTheme>
  );
}
