import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Alert, Paper, Stack, Typography } from "@mui/material";
import BookingForm from "./BookingForm";
import { createBooking } from "../api/bookingService";

export default function BookingCreate() {
    const [model, setModel] = useState({});
    const [saving, setSaving] = useState(false);
    const [err, setErr] = useState(null);
    const navigate = useNavigate();

    const submit = async () => {
        setErr(null);
        setSaving(true);
        try {
            await createBooking(model);
            navigate("/bookings");
        } catch (e) {
            console.error(e);
            setErr("Failed to create booking.");
        } finally {
            setSaving(false);
        }
    };

    return (
        <Paper sx={{ p: 2, m: 2 }}>
            <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ mb: 2 }}>
                <Typography variant="h5">New Booking</Typography>
            </Stack>
            {err && <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert>}
            <BookingForm value={model} onChange={setModel} onSubmit={submit} submitting={saving} />
        </Paper>
    );
}

