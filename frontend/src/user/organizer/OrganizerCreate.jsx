import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import OrganizerForm from './OrganizerForm';
import { createOrganizer } from '../../api/organizerService';
import useNotifications from '../../crud-dashboard/hooks/useNotifications/useNotifications';
import PageContainer from '../../crud-dashboard/components/PageContainer';

const INITIAL_FORM_VALUES = {
    name: '',
    surname: '',
    email: '',
    phone: '',
    password: '',
    organizerType: '',
};

export default function OrganizerCreate() {
    const navigate = useNavigate();
    const notifications = useNotifications();

    const [formState, setFormState] = React.useState(() => ({
        values: INITIAL_FORM_VALUES,
        errors: {},
    }));

    const handleFieldChange = React.useCallback((field, value) => {
        setFormState((prev)=> ({
            ...prev,
            values: {...prev.values, [field]: value},

        }));
    }, []);

    const handleReset = React.useCallback(()=> {
        setFormState({values: INITIAL_FORM_VALUES, errors: {}});
    }, []);

    const handleSubmit = React.useCallback(async () => {
        try{
            await createOrganizer(formState.values);
            notifications.show(`Organizer added successfully`,{
                severity: 'success',
                autoHideDuration: 3000,
            });
            navigate('/user/organizer');
        } catch (error){
            notifications.show(
                `Failed to add organizer. Reason: ${error.message}`,
                {severity: 'error', autoHideDuration: 3000}
            );
        }
    }, [formState.values, navigate, notifications]);
//   const handleSubmit = async () => {
//     try {
//       await createOrganizer(formState.values);
//       notifications.show('Organizer added successfully', { severity: 'success', autoHideDuration: 3000 });
//       navigate('/user/organizer');
//     } catch (error) {
//       notifications.show(`Failed to add organizer. Reason: ${error.message}`, { severity: 'error', autoHideDuration: 3000 });
//     }
//   };

    return (
        <PageContainer
            title="New Organizer"
            breadcrumbs={[{ title: 'Organizers', path: '/user/organizer' }, { title: 'New' }]}>

            <OrganizerForm
                formState={formState}
                onFieldChange={handleFieldChange}
                onSubmit={handleSubmit}
                onReset={handleReset}
                submitButtonLabel="Create"
            />
        </PageContainer>
    );
}