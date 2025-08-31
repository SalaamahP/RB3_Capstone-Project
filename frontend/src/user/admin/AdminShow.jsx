import * as React from 'react';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import Divider from '@mui/material/Divider';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useNavigate, useParams } from 'react-router';
import dayjs from 'dayjs';
import { useDialogs } from '../../crud-dashboard/hooks/useDialogs/useDialogs';
import useNotifications from '../../crud-dashboard/hooks/useNotifications/useNotifications';
import PageContainer from '../../crud-dashboard/components/PageContainer';
import { getAdmin, deleteAdmin } from '../../api/adminService';

export default function AdminShow() {
    const { id } = useParams();
    const navigate = useNavigate();
    const dialogs = useDialogs();
    const notifications = useNotifications();
    const [admin, setAdmin] = React.useState(null);
    const [isLoading, setIsLoading] = React.useState(true);
    const [error, setError] = React.useState(null);

    React.useEffect(() => {
        async function fetchAdmin() {
            setIsLoading(true);
            setError(null);
            try {
                const response = await getAdmin(id);
                setAdmin(response.data);
            } catch (err) {
                setError(err);
            }
            setIsLoading(false);
        }
        fetchAdmin();
    }, [id]);

    const handleBack = () => {
        navigate('/user/admin');
    };

    const handleEdit = () => {
        navigate(`/user/admin/${id}/edit`);
    };

    const handleDelete = async () => {
        const confirmed = await dialogs.confirm(
            `Do you wish to delete ${admin?.name}?`,
            {
                title: 'Delete admin?',
                severity: 'error',
                okText: 'Delete',
                cancelText: 'Cancel',
            }
        );
        if (confirmed) {
            setIsLoading(true);
            try {
                await deleteAdmin(Number(id));
                notifications.show('Admin deleted successfully.', {
                    severity: 'success',
                    autoHideDuration: 3000,
                });
                navigate('/user/admin');
            } catch (deleteError) {
                notifications.show(
                    `Failed to delete admin. Reason: ${deleteError.message}`,
                    {
                        severity: 'error',
                        autoHideDuration: 3000,
                    }
                );
                setIsLoading(false);
            }
        }
    };

    return (
        <PageContainer
            title="Admin Details"
            breadcrumbs={[{ title: 'Admins', path: '/user/admin' }, { title: 'Details' }]}
            actions={
                <Stack direction="row" alignItems="center" spacing={1}>
                    <Button variant="contained" startIcon={<ArrowBackIcon />} onClick={handleBack}>
                        Back
                    </Button>
                    <Button variant="contained" startIcon={<EditIcon />} onClick={handleEdit} disabled={isLoading || !organizer}>
                        Edit
                    </Button>
                    <Button variant="contained" color="error" startIcon={<DeleteIcon />} onClick={handleDelete} disabled={isLoading || !organizer}>
                        Delete
                    </Button>
                </Stack>
            }
        >
            <Box sx={{ flex: 1, width: '100%' }}>
                {isLoading ? (
                    <Stack alignItems="center" justifyContent="center" sx={{ py: 4 }}>
                        <CircularProgress />
                    </Stack>
                ) : error ? (
                    <Alert severity="error">{error.message}</Alert>
                ) : organizer ? (
                    <Paper sx={{ p: 3 }}>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Name</Typography>
                                <Typography variant="body1">{admin.name}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Surname</Typography>
                                <Typography variant="body1">{admin.surname}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Email</Typography>
                                <Typography variant="body1">{admin.email}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Phone</Typography>
                                <Typography variant="body1">{admin.phone}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Admin Role</Typography>
                                <Typography variant="body1">{admin.adminRole}</Typography>
                            </Grid>
                        </Grid>
                    </Paper>
                ) : (
                    <Alert severity="info">No admin found.</Alert>
                )}
            </Box>
        </PageContainer>
    );
}