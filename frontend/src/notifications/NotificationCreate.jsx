import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Alert, Paper, Stack, Typography } from "@mui/material";
import NotificationForm from "./NotificationForm";
import { createNotification } from "../api/notificationService";

export default function NotificationCreate() {
    const [model, setModel] = useState({});
    const [saving, setSaving] = useState(false);
    const [err, setErr] = useState(null);
    const navigate = useNavigate();

    const submit = async () => {
        setErr(null);
        setSaving(true);
        try {
            await createNotification(model);
            navigate("/notifications");
        } catch (e) {
            console.error(e);
            setErr("Failed to create notification.");
        } finally {
            setSaving(false);
        }
    };

    return (
        <Paper sx={{ p: 2, m: 2 }}>
            <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ mb: 2 }}>
                <Typography variant="h5">New Notification</Typography>
            </Stack>
            {err && <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert>}
            <NotificationForm value={model} onChange={setModel} onSubmit={submit} submitting={saving} />
        </Paper>
    );
}

