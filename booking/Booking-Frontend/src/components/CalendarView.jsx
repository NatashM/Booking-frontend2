import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";

function formatTitle(shift) {
    return `${shift.type} - ${shift.teamName}`;
}

export default function CalendarView({ shifts }) {
    const events = shifts.map(s => ({
        title: formatTitle(s),
        date: s.date
    }));

    return (
        <FullCalendar
            plugins={[dayGridPlugin]}
            initialView="dayGridMonth"
            events={events}
        />
    );
}