import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
//import { createHashRouter, RouterProvider } from 'react-router-dom';
import { Routes, Route, Navigate } from 'react-router-dom';
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
// student
import StudentList from "../user/student/StudentList";
import StudentCreate from "../user/student/StudentCreate";
import StudentEdit from "../user/student/StudentEdit";
import StudentShow from "../user/student/StudentShow";
import StudentForm from "../user/student/StudentForm";

// admin
import AdminList from "../user/admin/AdminList";
import AdminCreate from "../user/admin/AdminCreate";
import AdminEdit from "../user/admin/AdminEdit";
import AdminShow from "../user/admin/AdminShow";
import AdminForm from "../user/admin/AdminForm";

//organizer
import OrganizerList from "../user/organizer/OrganizerList";
import OrganizerCreate from "../user/organizer/OrganizerCreate";
import OrganizerEdit from "../user/organizer/OrganizerEdit";
import OrganizerShow from "../user/organizer/OrganizerShow";
import OrganizerForm from "../user/organizer/OrganizerForm";

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

export default function UserDashboard(props) {
  return (
      <AppTheme {...props} themeComponents={themeComponents}>
        <CssBaseline enableColorScheme />

        <Routes>
          // student crud
          <Route path ="student">
            <Route index element={<StudentList/>}/>
            <Route path="new" element={<StudentCreate/>}/>
            <Route path=":studentID/edit" element={<StudentEdit />} />
            <Route path=":studentID" element={<StudentShow />} />
          </Route>
          <Route path="/" element={<StudentList />} />
          <Route path="new" element={
            <StudentForm
                formState={{ values: {}, errors: {} }}
                oneFieldChange={(field, value) => console.log("change", field, value)}
                onSubmit={(values) => console.log("submit", values) }
                submitButtonLabel="Add Student"
            />
          }/>
          <Route path=":id/edit" element={
            <StudentForm
                formState={{ values: {/* preload from getStudent(id)*/}, errors: {} }}
                neFieldChange={(field, value) => console.log("update", field, value)}
                onSubmit={(values) => console.log("update", field, value)}
                submitButtonLabel="Update Student"
            />
          } />
          <Route path=":id" element={<StudentShow />} />

          <Route path="*" element={<StudentList />} />

          <Route path="admin">
            <Route index element={<AdminList />} />
            <Route path="new" element={<AdminCreate />} />
            <Route path=":adminID/edit" element={<AdminEdit />} />
            <Route path=":adminID" element={<AdminShow />} />
          </Route>
          <Route path="/" element={<AdminList />} />
          <Route path="new" element={
            <AdminForm
                formState={{ values: {}, errors: {} }}
                oneFieldChange={(field, value) => console.log("change", field, value)}
                onSubmit={(values) => console.log("submit", values) }
                submitButtonLabel="Add Admin"
            />
          }/>
          <Route path=":id/edit" element={
            <AdminForm
                formState={{ values: {/* preload from getAdmin(id)*/}, errors: {} }}
                neFieldChange={(field, value) => console.log("update", field, value)}
                onSubmit={(values) => console.log("update", field, value)}
                submitButtonLabel="Update Admin"
            />
          } />
          <Route path=":id" element={<AdminShow />} />

          <Route path="*" element={<AdminList />} />

          <Route path="organizer">
            <Route index element={<OrganizerList />} />
            <Route path="new" element={<OrganizerCreate />} />
            <Route path=":organizerID/edit" element={<OrganizerEdit />} />
            <Route path=":organizerID" element={<OrganizerShow />} />
          </Route>
          <Route path="/" element={<OrganizerList />} />
          <Route path="new" element={
            <OrganizerForm
                formState={{ values: {}, errors: {} }}
                oneFieldChange={(field, value) => console.log("change", field, value)}
                onSubmit={(values) => console.log("submit", values) }
                submitButtonLabel="Add Organizer"
            />
          }/>
          <Route path=":id/edit" element={
            <OrganizerForm
                formState={{ values: {/* preload from getOrganizer(id)*/}, errors: {} }}
                oneFieldChange={(field, value) => console.log("update", field, value)}
                onSubmit={(values) => console.log("update", field, value)}
                submitButtonLabel="Update Organizer"
            />
          } />
          <Route path=":id" element={<OrganizerShow />} />

          <Route path="*" element={<OrganizerList />} />
        </Routes>

      </AppTheme>
  );
}
