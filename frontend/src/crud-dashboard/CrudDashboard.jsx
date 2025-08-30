import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import { createHashRouter, RouterProvider } from 'react-router';
import DashboardLayout from './components/DashboardLayout';
import EmployeeList from './components/EmployeeList';
import EmployeeShow from './components/EmployeeShow';
import EmployeeCreate from './components/EmployeeCreate';
import EmployeeEdit from './components/EmployeeEdit';
import NotificationsProvider from './hooks/useNotifications/NotificationsProvider';
import DialogsProvider from './hooks/useDialogs/DialogsProvider';
import { Routes, Route } from 'react-router-dom';
import AppTheme from '../shared-theme/AppTheme';
import {
  dataGridCustomizations,
  datePickersCustomizations,
  sidebarCustomizations,
  formInputCustomizations,
} from './theme/customizations';

const router = createHashRouter([
  {
    Component: DashboardLayout,
    children: [
      {
        path: '/employees',
        Component: EmployeeList,
      },
      {
        path: '/employees/:employeeId',
        Component: EmployeeShow,
      },
      {
        path: '/employees/new',
        Component: EmployeeCreate,
      },
      {
        path: '/employees/:employeeId/edit',
        Component: EmployeeEdit,
      },
      // Fallback route for the example routes in dashboard sidebar items
      {
        path: '*',
        Component: EmployeeList,
      },
    ],
  },
]);

const themeComponents = {
  ...dataGridCustomizations,
  ...datePickersCustomizations,
  ...sidebarCustomizations,
  ...formInputCustomizations,
};

export default function CrudDashboard(props) {
  return (
    <AppTheme {...props} themeComponents={themeComponents}>
      <CssBaseline enableColorScheme />
      <NotificationsProvider>
        <DialogsProvider>
          <Routes>
            <Route path="employees" element={<EmployeeList />} />
            <Route path="employees/new" element={<EmployeeCreate />} />
            <Route path="employees/:employeeId" element={<EmployeeShow />} />
            <Route path="employees/:employeeId/edit" element={<EmployeeEdit />} />
            <Route path="*" element={<EmployeeList />} />
          </Routes>
        </DialogsProvider>
      </NotificationsProvider>
    </AppTheme>
  );
}
