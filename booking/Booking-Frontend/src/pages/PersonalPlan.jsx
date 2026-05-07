import React, { useEffect, useState } from "react";
import { getUserShifts } from "../services/api";
import CalendarView from "../components/CalendarView.jsx";

export default function PersonalPlan() {
    const [shifts, setShifts] = useState([]);

    useEffect(() => {
        getUserShifts(1).then(setShifts); // 👈 test user
    }, []);

    return (
        <div>
            <h1>Personlig Plan</h1>
            <CalendarView shifts={shifts} />

            <h2>Liste</h2>
            {shifts.map(s => (
                <div key={s.id}>
                    {s.date} - {s.type} - {s.teamName}
                </div>
            ))}
        </div>
    );
}