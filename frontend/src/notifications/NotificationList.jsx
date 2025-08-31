import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    listNotifications,
    deleteNotification,
} from "../api/notificationService";
import {
    Box,
    Paper,
    Stack,
    Typography,
    Button,
    IconButton,
    Tooltip,
    Alert,
    CircularProgress,
} from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import AddIcon from "@mui/icons-material/Add";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import RefreshIcon from "@mui/icons-material/Refresh";

function normalizeId(n) {
    // DataGrid needs an "id" field. Backends vary in naming.
    return {
        ...n,
        id: n.id ?? n.notificationID ?? n.notificationId ?? n.notification_id,
    };
}

export default function NotificationList() {
    const [rows, setRows] = useState([]);
    const [selection, setSelection] = useState([]);
    const [loading, setLoading] = useState(true);
    const [err, setErr] = useState(null);
    const [busy, setBusy] = useState(false);
    const navigate = useNavigate();

    const fetchData = async () => {
        setErr(null);
        setLoading(true);
        try {
            const data = await listNotifications();
            const normalized = (Array.isArray(data) ? data : []).map(normalizeId);
            setRows(normalized);
        } catch (e) {
            console.error(e);
            setErr("Failed to load notifications. Check your backend and API baseURL.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
        // eslint-disable-next-line
    }, []);

    const handleAdd = () => navigate("/notifications/new");
    const handleEdit = () => {
        if (selection.length !== 1) return;
        navigate(`/notifications/${selection[0]}/edit`);
    };
    const handleDelete = async () => {
        if (selection.length < 1) return;
        if (!window.confirm(`Delete ${selection.length} notification(s)?`)) return;
        setBusy(true);
        try {
            for (const id of selection) {
                await deleteNotification(id);
            }
            await fetchData();
            setSelection([]);
        } catch (e) {
            console.error(e);
            setErr("Failed to delete. Make sure the item exists and the backend allows deletion.");
        } finally {
            setBusy(false);
        }
    };

    const columns = useMemo(
        () => [
            { field: "id", headerName: "ID", width: 100 },
            { field: "notificationID", headerName: "Notification ID", width: 160, valueGetter: (p) => p.row.notificationID ?? p.row.id },
            { field: "studentID", headerName: "Student ID", width: 140 },
            { field: "eventID", headerName: "Event ID", width: 120 },
            { field: "message", headerName: "Message", flex: 1, minWidth: 200 },
            { field: "timestamp", headerName: "Timestamp", width: 220 },
        ],
        []
    );

    return (
        <Box sx={{ p: 2 }}>
            <Paper sx={{ p: 2 }}>
                <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ mb: 2 }}>
                    <Typography variant="h5">Notifications</Typography>
                    <Stack direction="row" spacing={1}>
                        <Tooltip title="Refresh">
              <span>
                <IconButton onClick={fetchData} disabled={loading || busy}>
                  <RefreshIcon />
                </IconButton>
              </span>
                        </Tooltip>
                        <Tooltip title="Add">
              <span>
                <Button startIcon={<AddIcon />} variant="contained" onClick={handleAdd} disabled={busy}>
                  Add
                </Button>
              </span>
                        </Tooltip>
                        <Tooltip title="Edit (select 1)">
              <span>
                <Button startIcon={<EditIcon />} onClick={handleEdit} disabled={selection.length !== 1 || busy}>
                  Edit
                </Button>
              </span>
                        </Tooltip>
                        <Tooltip title="Delete">
              <span>
                <Button color="error" startIcon={<DeleteIcon />} onClick={handleDelete} disabled={selection.length === 0 || busy}>
                  Delete
                </Button>
              </span>
                        </Tooltip>
                    </Stack>
                </Stack>

                {err && <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert>}
                {loading ? (
                    <Box sx={{ display: "flex", justifyContent: "center", py: 8 }}>
                        <CircularProgress />
                    </Box>
                ) : (
                    <Box sx={{ height: 520, width: "100%" }}>
                        <DataGrid
                            rows={rows}
                            columns={columns}
                            checkboxSelection
                            disableRowSelectionOnClick
                            onRowSelectionModelChange={(ids) => setSelection(ids)}
                            rowSelectionModel={selection}
                            pageSizeOptions={[5, 10, 25]}
                            initialState={{ pagination: { paginationModel: { pageSize: 10 } } }}
                        />
                    </Box>
                )}
            </Paper>
        </Box>
    );
}
