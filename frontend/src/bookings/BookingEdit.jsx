import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Alert, CircularProgress, Paper, Stack, Typography, Box } from "@mui/material";
import BookingForm from "./BookingForm";
import { getBooking, updateBooking } from "../api/bookingService";

export default function BookingEdit() {
    const { id } = useParams();
    const [model, setModel] = useState(null);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [err, setErr] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        (async () => {
            setErr(null);
            setLoading(true);
            try {
                const data = await getBooking(id);
                setModel(data);
            } catch (e) {
                console.error(e);
                setErr("Failed to load booking.");
            } finally {
                setLoading(false);
            }
        })();
    }, [id]);

    const submit = async () => {
        setErr(null);
        setSaving(true);
        try {
            await updateBooking(id, model);
            navigate("/bookings");
        } catch (e) {
            console.error(e);
            setErr("Failed to update booking.");
        } finally {
            setSaving(false);
        }
    };

    return (
        <Paper sx={{ p: 2, m: 2 }}>
            <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ mb: 2 }}>
                <Typography variant="h5">Edit Booking #{id}</Typography>
            </Stack>
            {err && <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert>}
            {loading || !model ? (
                <Box sx={{ display: "flex", justifyContent: "center", py: 8 }}>
                    <CircularProgress />
                </Box>
            ) : (
                <BookingForm value={model} onChange={setModel} onSubmit={submit} submitting={saving} />
            )}
        </Paper>
    );
}

