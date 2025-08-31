# Frontend: Notifications & Ticket Booking Details (CRUD)

This adds full CRUD UI for:
- **Notifications** (`/notifications`)
- **TicketBookingDetails** (`/bookings`)

It uses the existing Axios base URL in `frontend/src/api/axios.js` which points to `http://localhost:8080/SEMS`.
The backend controllers are mounted under `/notifications` and `/bookings` respectively.

## How to run
```bash
cd frontend
npm install
npm run dev
```
Navigate to:
- http://localhost:5173/notifications
- http://localhost:5173/bookings

## Files created
- `frontend/src/api/notificationService.js`
- `frontend/src/api/bookingService.js`
- `frontend/src/notifications/*` (List/Create/Edit/Form)
- `frontend/src/bookings/*` (List/Create/Edit/Form)
- Routes injected into `frontend/src/App.jsx`
