import * as React from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";

export default function BookingForm({ value, onChange, onSubmit, submitting }) {
    const v = value || {};
    const set = (key) => (e) => onChange({ ...v, [key]: e.target.value });

    return (
        <Box
            component="form"
            onSubmit={(e) => {
                e.preventDefault();
                onSubmit && onSubmit();
            }}
            sx={{ p: 2 }}
        >
            <Grid container spacing={2}>
                {/* You can hide BookingID if backend auto-generates it */}
                <Grid item xs={12} md={6}>
                    <TextField label="Booking ID" value={v.bookingID ?? v.id ?? ""} onChange={set("bookingID")} fullWidth />
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField label="Student ID" value={v.studentID ?? ""} onChange={set("studentID")} fullWidth required />
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField label="Event ID" value={v.eventID ?? ""} onChange={set("eventID")} fullWidth required />
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField label="Seats" type="number" value={v.seats ?? ""} onChange={set("seats")} fullWidth required />
                </Grid>
                <Grid item xs={12}>
                    <Button variant="contained" type="submit" disabled={!!submitting}>
                        Save
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
}

