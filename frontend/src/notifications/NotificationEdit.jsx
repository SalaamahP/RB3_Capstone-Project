import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Alert, CircularProgress, Paper, Stack, Typography, Box } from "@mui/material";
import NotificationForm from "./NotificationForm";
import { getNotification, updateNotification } from "../api/notificationService";

export default function NotificationEdit() {
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
                const data = await getNotification(id);
                setModel(data);
            } catch (e) {
                console.error(e);
                setErr("Failed to load notification.");
            } finally {
                setLoading(false);
            }
        })();
    }, [id]);

    const submit = async () => {
        setErr(null);
        setSaving(true);
        try {
            await updateNotification(id, model);
            navigate("/notifications");
        } catch (e) {
            console.error(e);
            setErr("Failed to update notification.");
        } finally {
            setSaving(false);
        }
    };

    return (
        <Paper sx={{ p: 2, m: 2 }}>
            <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ mb: 2 }}>
                <Typography variant="h5">Edit Notification #{id}</Typography>
            </Stack>
            {err && <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert>}
            {loading || !model ? (
                <Box sx={{ display: "flex", justifyContent: "center", py: 8 }}>
                    <CircularProgress />
                </Box>
            ) : (
                <NotificationForm value={model} onChange={setModel} onSubmit={submit} submitting={saving} />
            )}
        </Paper>
    );
}
