import * as React from 'react';
import { useNavigate } from 'react-router';
import useNotifications from '../../crud-dashboard/hooks/useNotifications/useNotifications';
// import {
//   createOne as createEmployee,
//   validate as validateEmployee,
// } from '../data/employees';
import EmployeeForm from '../../crud-dashboard/components/EmployeeForm';
import PageContainer from '../../crud-dashboard/components/PageContainer';
import { createStudent } from '../../api/studentService';
import StudentForm from './StudentForm';
const INITIAL_FORM_VALUES = {
  studentNumber: '',
  name:'',
  surname: '',
  email: '',
  phone: '',
  //password: '',
};

export default function StudentCreate() {
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
      await createStudent(formState.values);
      notifications.show(`Student added successfully`,{
        severity: 'success',
        autoHideDuration: 3000,
      });
      navigate('/user/student');
    } catch (error){
      notifications.show(
        `Failed to add student. Reason: ${error.message}`,
        {severity: 'error', autoHideDuration: 3000}
      );
    }
  }, [formState.values, navigate, notifications]);

  return (
    <PageContainer
    title= "New Student"
    breadcrumbs={[{title: `Students`, path: `/user/student`}, {title: `New`}]}
    >

   
    <StudentForm
    formState={formState}
    onFieldChange={handleFieldChange}
    onSubmit={handleSubmit}
    onReset={handleReset}
    submitButtonLabel="Create"
    />
    </PageContainer>
  );
      
    }

  // const formValues = formState.values;
  // const formErrors = formState.errors;

  // const setFormValues = React.useCallback((newFormValues) => {
  //   setFormState((previousState) => ({
  //     ...previousState,
  //     values: newFormValues,
  //   }));
  // }, []);

  // const setFormErrors = React.useCallback((newFormErrors) => {
  //   setFormState((previousState) => ({
  //     ...previousState,
  //     errors: newFormErrors,
  //   }));
  // }, []);

  // const handleFormFieldChange = React.useCallback(
  //   (name, value) => {
  //     const validateField = async (values) => {
  //       const { issues } = validateEmployee(values);
  //       setFormErrors({
  //         ...formErrors,
  //         [name]: issues?.find((issue) => issue.path?.[0] === name)?.message,
  //       });
  //     };

//       const newFormValues = { ...formValues, [name]: value };

//       setFormValues(newFormValues);
//       validateField(newFormValues);
//     },
//     [formValues, formErrors, setFormErrors, setFormValues],
//   );

//   const handleFormReset = React.useCallback(() => {
//     setFormValues(INITIAL_FORM_VALUES);
//   }, [setFormValues]);

//   const handleFormSubmit = React.useCallback(async () => {
//     const { issues } = validateEmployee(formValues);
//     if (issues && issues.length > 0) {
//       setFormErrors(
//         Object.fromEntries(issues.map((issue) => [issue.path?.[0], issue.message])),
//       );
//       return;
//     }
//     setFormErrors({});

//     try {
//       await createEmployee(formValues);
//       notifications.show('Employee created successfully.', {
//         severity: 'success',
//         autoHideDuration: 3000,
//       });

//       navigate('/employees');
//     } catch (createError) {
//       notifications.show(
//         `Failed to create employee. Reason: ${createError.message}`,
//         {
//           severity: 'error',
//           autoHideDuration: 3000,
//         },
//       );
//       throw createError;
//     }
//   }, [formValues, navigate, notifications, setFormErrors]);

//   return (
//     <PageContainer
//       title="New Employee"
//       breadcrumbs={[{ title: 'Employees', path: '/employees' }, { title: 'New' }]}
//     >
//       <EmployeeForm
//         formState={formState}
//         onFieldChange={handleFormFieldChange}
//         onSubmit={handleFormSubmit}
//         onReset={handleFormReset}
//         submitButtonLabel="Create"
//       />
//     </PageContainer>
//   );
// }
