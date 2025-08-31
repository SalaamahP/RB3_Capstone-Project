import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import AdminForm from './AdminForm';
import { createAdmin } from '../../api/adminService';
import useNotifications from '../../crud-dashboard/hooks/useNotifications/useNotifications';
import PageContainer from '../../crud-dashboard/components/PageContainer';

const INITIAL_FORM_VALUES = {
    name: '',
    surname: '',
    email: '',
    phone: '',
    password: '',
    adminRole: '',
};

export default function AdminCreate() {
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
            await createAdmin(formState.values);
            notifications.show(`Admin added successfully`,{
                severity: 'success',
                autoHideDuration: 3000,
            });
            navigate('/user/admin');
        } catch (error){
            notifications.show(
                `Failed to add admin. Reason: ${error.message}`,
                {severity: 'error', autoHideDuration: 3000}
            );
        }
    }, [formState.values, navigate, notifications]);
//   const handleSubmit = async () => {
//     try {
//       await createAdmin(formState.values);
//       notifications.show('Admin added successfully', { severity: 'success', autoHideDuration: 3000 });
//       navigate('/user/admin');
//     } catch (error) {
//       notifications.show(`Failed to add admin. Reason: ${error.message}`, { severity: 'error', autoHideDuration: 3000 });
//     }
//   };

    return (
        <PageContainer
            title="New Admin"
            breadcrumbs={[{ title: 'Admins', path: '/user/admin' }, { title: 'New' }]}>

            <AdminForm
                formState={formState}
                onFieldChange={handleFieldChange}
                onSubmit={handleSubmit}
                onReset={handleReset}
                submitButtonLabel="Create"
            />
        </PageContainer>
    );
}