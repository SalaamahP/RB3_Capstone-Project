import * as React from 'react';
import PropTypes from 'prop-types';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import { useNavigate, useParams } from 'react-router';
import useNotifications from '../../crud-dashboard/hooks/useNotifications/useNotifications';

import PageContainer from '../../crud-dashboard/components/PageContainer';
import OrganizerForm from './OrganizerForm';
import { getOrganizer, updateOrganizer } from '../../api/organizerService';

export default function OrganizerEdit() {
    const { organizerID} = useParams();
    const navigate = useNavigate();

    const notifications = useNotifications();

    const [organizer, setOrganizer] = React.useState(null);
    const [isLoading, setIsLoading] = React.useState(true);
    const [error, setError] = React.useState(null);

    const loadData = React.useCallback(async ()  => {
        setError(null);
        setIsLoading(true);
        try{
            const response = await getOrganizer(organizerID);
            setOrganizer(response.data);
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
        setOrganizer((prev) => ({
            ...prev,
            [field]: value,
        }));
    }, []);

    const handleSubmit = React.useCallback(async () => {
        try {
            await updateOrganizer(organizer);
            notifications.show(`Organizer updated successfully`,{
                severity: 'success',
                autoHideDuration: 3000,
            });
            navigate('/user/organizer');
        } catch (err) {
            notifications.show(`Update failed. Cause: ${err.message}`, {
                severity: 'error',
                autoHideDuration: 3000,
            });
        }
    }, [organizer, navigate, notifications]);

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
            title={`Edit Organizer ${organizerID}`}
            breadcrumbs={[
                { title: 'Organizer', path: '/user/organizer' },
                { title: `Organizer ${organizerID}`, path: `/user/organizer/${organizerID}` },
                { title: 'Edit' },
            ]}
        >
            <OrganizerForm
                formState={{values: organizer, errors: {} }}
                onFieldChange={handleFieldChange}
                onSubmit={handleSubmit}
                submitButtonLabel="Save"
            />
        </PageContainer>
    );
}