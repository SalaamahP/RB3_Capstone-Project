import * as React from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import FormControl from '@mui/material/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormHelperText from '@mui/material/FormHelperText';
import Grid from '@mui/material/Grid';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useNavigate } from 'react-router-dom';
import { createOrganizer, updateOrganizer } from '../../api/organizerService';

function OrganizerForm(props) {
    const {
        formState,
        onFieldChange,
        onSubmit,
        onReset,
        submitButtonLabel,
        backButtonPath
    } = props;

    const ORGANIZER_TYPES = [
        { value: 'STUDENT_CLUB', label: 'Student Club' },
        { value: 'FACULTY', label: 'Faculty' },
        { value: 'CORPORATION', label: 'Corporation' },
    ];

    const formValues = formState.values;
    const formErrors = formState.errors;

    const navigate = useNavigate();
    const [isSubmitting, setIsSubmitting] = React.useState(false);

    const handleSubmit = React.useCallback(
        async (event) => {
            event.preventDefault();

            setIsSubmitting(true);
            try {
                await onSubmit(formValues);
            } finally {
                setIsSubmitting(false);
            }
        },
        [formValues, onSubmit],
    );

    const handleTextFieldChange = React.useCallback(
        (event) => {
            onFieldChange(event.target.name, event.target.value);
        },
        [onFieldChange],
    );

    const handleSelectFieldChange = React.useCallback(
        (event) => {
            onFieldChange(event.target.name, event.target.value);
        },
        [onFieldChange],
    );

    const handleReset = React.useCallback(() => {
        if (onReset) {
            onReset(formValues);
        }
    }, [formValues, onReset]);

    const handleBack = React.useCallback(() => {
        navigate(backButtonPath ?? '/user/organizer');
    }, [navigate, backButtonPath]);

    return (
        <Box component="form"
             onSubmit={handleSubmit}
             noValidate autoComplete="off"
             onReset={handleReset}
             sx={{ width: '100%' }}
        >
            <FormGroup>

                <Grid container spacing={2} sx={{ mb: 2, width: '100%' }}>
                    <Grid size={{ xs: 12, sm: 6 }} sx={{ display: 'flex' }}>
                        <TextField
                            value={formValues.name ?? ''}
                            onChange={handleTextFieldChange}
                            name="name"
                            label="Name"
                            error={!!formErrors.name}
                            helperText={formErrors.name ?? ' '}
                            fullWidth
                        />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }} sx={{ display: 'flex' }}>
                        <TextField
                            value={formValues.surname ?? ''}
                            onChange={handleTextFieldChange}
                            surname="surname"
                            label="Surname"
                            error={!!formErrors.surname}
                            helperText={formErrors.surname ?? ' '}
                            fullWidth
                        />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }} sx={{ display: 'flex' }}>
                        <TextField
                            value={formValues.email ?? ''}
                            onChange={handleTextFieldChange}
                            name="email"
                            label="Email"
                            error={!!formErrors.email}
                            helperText={formErrors.email ?? ' '}
                            fullWidth
                        />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }} sx={{ display: 'flex' }}>
                        <TextField
                            value={formValues.phone ?? ''}
                            onChange={handleTextFieldChange}
                            name="phone"
                            label="Phone"
                            error={!!formErrors.phone}
                            helperText={formErrors.phone ?? ' '}
                            fullWidth
                        />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }} sx={{ display: 'flex' }}>
                        <TextField
                            value={formValues.password ?? ''}
                            onChange={handleTextFieldChange}
                            name="password"
                            label="Password"
                            error={!!formErrors.password}
                            helperText={formErrors.password ?? ' '}
                            fullWidth
                        />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }} sx={{ display: 'flex' }}>
                        <FormControl fullWidth error={!!formErrors.organizerType}>
                            <InputLabel id="organizerType-label">Organizer Type</InputLabel>
                            <Select
                                labelId="organizerType-label"
                                name="organizerType"
                                value={formValues.organizerType ?? ''}
                                label="Organizer Type"
                                onChange={handleSelectFieldChange}
                            >
                                <MenuItem value="" disabled>Select Organizer Type</MenuItem>
                                {ORGANIZER_TYPES.map(type => (
                                    <MenuItem key={type.value} value={type.value}>{type.label}</MenuItem>
                                ))}
                            </Select>
                            {formErrors.organizerType && <span style={{ color: 'red' }}>{formErrors.organizerType}</span>}
                        </FormControl>
                    </Grid>
                </Grid>
            </FormGroup>

            <Stack direction="row" spacing={2} justifyContent="space-between">
                <Button
                    variant="contained"
                    startIcon={<ArrowBackIcon />}
                    onClick={handleBack}>
                    Back
                </Button>
                <Button type="submit" variant="contained" size="large" disabled={isSubmitting}>
                    {submitButtonLabel}
                </Button>
                <Button
                    type="submit"
                    variant="contained"
                    size="large"
                    loading={isSubmitting}
                >
                    {submitButtonLabel}
                </Button>
            </Stack>
        </Box>
    );
}

OrganizerForm.propTypes = {
    backButtonPath: PropTypes.string,
    formState: PropTypes.shape({
        errors: PropTypes.shape({
            name: PropTypes.string,
            surname: PropTypes.string,
            email: PropTypes.string,
            phone: PropTypes.string,
            password: PropTypes.string,
            organizerType: PropTypes.string,
        }).isRequired,
        values: PropTypes.shape({
            name: PropTypes.string,
            surname: PropTypes.string,
            email: PropTypes.string,
            phone: PropTypes.string,
            password: PropTypes.string,
            organizerType: PropTypes.string,
        }).isRequired,
    }).isRequired,
    onFieldChange: PropTypes.func.isRequired,
    onReset: PropTypes.func,
    onSubmit: PropTypes.func.isRequired,
    submitButtonLabel: PropTypes.string.isRequired,
};

export default OrganizerForm;