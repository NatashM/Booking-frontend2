import React, { useEffect, useState } from "react";
import { getOpenShifts, applyForShift } from "../services/api";
import ShiftCard from "../components/ShiftCard";

export default function WorkPlan() {
    const [shifts, setShifts] = useState([]);
    const userId = 1;

    useEffect(() => {
        load();
    }, []);

    async function load() {
        const data = await getOpenShifts();
        setShifts(data);
    }

    async function handleApply(shiftId) {
        try {
            await applyForShift(shiftId, userId);
            load(); // reload
        } catch (err) {
            alert(err.message);
        }
    }

    return (
        <div>
            <h1>Arbejdsplan</h1>

            {shifts.map(shift => (
                <ShiftCard
                    key={shift.id}
                    shift={shift}
                    onApply={handleApply}
                />
            ))}
        </div>
    );
}