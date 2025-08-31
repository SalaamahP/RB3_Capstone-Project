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
import { getOrganizer, deleteOrganizer } from '../../api/organizerService';

export default function OrganizerShow() {
    const { id } = useParams();
    const navigate = useNavigate();
    const dialogs = useDialogs();
    const notifications = useNotifications();
    const [organizer, setOrganizer] = React.useState(null);
    const [isLoading, setIsLoading] = React.useState(true);
    const [error, setError] = React.useState(null);

    React.useEffect(() => {
        async function fetchOrganizer() {
            setIsLoading(true);
            setError(null);
            try {
                const response = await getOrganizer(id);
                setOrganizer(response.data);
            } catch (err) {
                setError(err);
            }
            setIsLoading(false);
        }
        fetchOrganizer();
    }, [id]);

    const handleBack = () => {
        navigate('/user/organizer');
    };

    const handleEdit = () => {
        navigate(`/user/organizer/${id}/edit`);
    };

    const handleDelete = async () => {
        const confirmed = await dialogs.confirm(
            `Do you wish to delete ${organizer?.name}?`,
            {
                title: 'Delete organizer?',
                severity: 'error',
                okText: 'Delete',
                cancelText: 'Cancel',
            }
        );
        if (confirmed) {
            setIsLoading(true);
            try {
                await deleteOrganizer(Number(id));
                notifications.show('Organizer deleted successfully.', {
                    severity: 'success',
                    autoHideDuration: 3000,
                });
                navigate('/user/organizer');
            } catch (deleteError) {
                notifications.show(
                    `Failed to delete organizer. Reason: ${deleteError.message}`,
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
            title="Organizer Details"
            breadcrumbs={[{ title: 'Organizers', path: '/user/organizer' }, { title: 'Details' }]}
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
                                <Typography variant="body1">{organizer.name}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Surname</Typography>
                                <Typography variant="body1">{organizer.surname}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Email</Typography>
                                <Typography variant="body1">{organizer.email}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Phone</Typography>
                                <Typography variant="body1">{organizer.phone}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="subtitle2">Organizer Type</Typography>
                                <Typography variant="body1">{organizer.organizerType}</Typography>
                            </Grid>
                        </Grid>
                    </Paper>
                ) : (
                    <Alert severity="info">No organizer found.</Alert>
                )}
            </Box>
        </PageContainer>
    );
}