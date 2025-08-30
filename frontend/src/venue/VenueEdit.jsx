import * as React from 'react';
import PropTypes from 'prop-types';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import { useNavigate, useParams } from 'react-router';
import useNotifications from './../crud-dashboard/hooks/useNotifications/useNotifications';
// import {
//   getOne as getEmployee,
//   updateOne as updateEmployee,
//   validate as validateEmployee,
// } from '../data/employees';
//import EmployeeForm from '../../crud-dashboard/components/EmployeeForm';
import PageContainer from './../crud-dashboard/components/PageContainer';
import VenueForm from './VenueForm';
import { getVenue, updateVenue } from './../api/venueService';

export default function VenueEdit() {
  const { venueId } = useParams();
  const navigate = useNavigate();

  const notifications = useNotifications();

  const [venue, setVenue] = React.useState(null);
  const [isLoading, setIsLoading] = React.useState(true);
  const [error, setError] = React.useState(null);

  const loadData = React.useCallback(async ()  => {
    setError(null);
    setIsLoading(true);
    try{
      const response = await getVenue(venueId);
      setVenue(response.data);
    } catch(err) {
      setError(err);
    } finally {
      setIsLoading(false);
    }
  }, [venueId]);

  React.useEffect(() =>{
    loadData();
  }, [loadData]
  );
  const handleFieldChange = React.useCallback((field, value) => {
    setVenue((prev) => ({
      ...prev,
      [field]: value,
    }));
      }, []);

      const handleSubmit = React.useCallback(async () => {
        try {
          await updateVenue(venue);
          notifications.show(`Venue updated successfully`,{
            severity: 'success',
            autoHideDuration: 3000,
          });
          navigate('/venue');
        } catch (err) {
          notifications.show(`Update failed. Cause: ${err.message}`, {
            severity: 'error',
            autoHideDuration: 3000,
          });
        }
      }, [venue, navigate, notifications]);
      
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
      title={`Edit Venue ${venueId}`}
      breadcrumbs={[
        { title: 'Venue', path: '/venue' },
        { title: `Venue ${venueId}`, path: `/venue/${venueId}` },
        { title: 'Edit' },
      ]}
    >
      <VenueForm
      formState={{values: venue, errors: {} }}
      onFieldChange={handleFieldChange}
      onSubmit={handleSubmit}
      submitButtonLabel="Save"
      />
    </PageContainer>
  );
}
