import * as React from 'react';
import PropTypes from 'prop-types';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import { useNavigate, useParams } from 'react-router';
import useNotifications from '../../crud-dashboard/hooks/useNotifications/useNotifications';
// import {
//   getOne as getEmployee,
//   updateOne as updateEmployee,
//   validate as validateEmployee,
// } from '../data/employees';
//import EmployeeForm from '../../crud-dashboard/components/EmployeeForm';
import PageContainer from '../../crud-dashboard/components/PageContainer';
import StudentForm from './StudentForm';
import { getStudent, updateStudent } from '../../api/studentService';

export default function StudentEdit() {
  const { studentID } = useParams();
  const navigate = useNavigate();

  const notifications = useNotifications();

  const [student, setStudent] = React.useState(null);
  const [isLoading, setIsLoading] = React.useState(true);
  const [error, setError] = React.useState(null);

  const loadData = React.useCallback(async ()  => {
    setError(null);
    setIsLoading(true);
    try{
      const response = await getStudent(studentID);
      setStudent(response.data);
    } catch(err) {
      setError(err);
    } finally {
      setIsLoading(false);
    }
  }, [studentID]);

  React.useEffect(() =>{
    loadData();
  }, [loadData]
  );
  const handleFieldChange = React.useCallback((field, value) => {
    setStudent((prev) => ({
      ...prev,
      [field]: value,
    }));
      }, []);

      const handleSubmit = React.useCallback(async () => {
        try {
          await updateStudent(student);
          notifications.show(`Student updated successfully`,{
            severity: 'success',
            autoHideDuration: 3000,
          });
          navigate('/user/student');
        } catch (err) {
          notifications.show(`Update failed. Cause: ${err.message}`, {
            severity: 'error',
            autoHideDuration: 3000,
          });
        }
      }, [student, navigate, notifications]);
      
      if(isLoading){
        return (
      

      <Box
          sx={{
            flex: 1,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            m: 2,
          }}
        >
          <CircularProgress />
          </Box>
      );
    }

    if (error){
      return <Alert severity="error">{error.message}</Alert>;
    }

  return (
   <PageContainer
      title={`Edit Student ${studentID}`}
      breadcrumbs={[
        { title: 'Student', path: '/user/student' },
        { title: `Student ${studentID}`, path: `/user/student/${studentID}` },
        { title: 'Edit' },
      ]}
    >
      <StudentForm
      formState={{values: student, errors: {} }}
      onFieldChange={handleFieldChange}
      onSubmit={handleSubmit}
      submitButtonLabel="Save"
      />
    </PageContainer>
  );
}
