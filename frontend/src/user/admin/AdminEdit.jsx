import * as React from 'react';
import PropTypes from 'prop-types';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import { useNavigate, useParams } from 'react-router';
import useNotifications from '../../crud-dashboard/hooks/useNotifications/useNotifications';

import PageContainer from '../../crud-dashboard/components/PageContainer';
import AdminForm from './AdminForm';
import { getAdmin, updateAdmin } from '../../api/adminService';

export default function AdminEdit() {
    const { adminID} = useParams();
    const navigate = useNavigate();

    const notifications = useNotifications();

    const [admin, setAdmin] = React.useState(null);
    const [isLoading, setIsLoading] = React.useState(true);
    const [error, setError] = React.useState(null);

    const loadData = React.useCallback(async ()  => {
        setError(null);
        setIsLoading(true);
        try{
            const response = await getAdmin(adminID);
            setAdmin(response.data);
        } catch(err) {
            setError(err);
        } finally {
            setIsLoading(false);
        }
    }, [adminID]);

    React.useEffect(() =>{
            loadData();
        }, [loadData]
    );
    const handleFieldChange = React.useCallback((field, value) => {
        setAdmin((prev) => ({
            ...prev,
            [field]: value,
        }));
    }, []);

    const handleSubmit = React.useCallback(async () => {
        try {
            await updateAdmin(admin);
            notifications.show(`Admin updated successfully`,{
                severity: 'success',
                autoHideDuration: 3000,
            });
            navigate('/user/admin');
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
            title={`Edit Admin ${adminID}`}
            breadcrumbs={[
                { title: 'Admin', path: '/user/admin' },
                { title: `Admin ${adminID}`, path: `/user/admin/${adminID}` },
                { title: 'Edit' },
            ]}
        >
            <AdminForm
                formState={{values: admin, errors: {} }}
                onFieldChange={handleFieldChange}
                onSubmit={handleSubmit}
                submitButtonLabel="Save"
            />
        </PageContainer>
    );
}