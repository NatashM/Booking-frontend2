import React from "react";

function getShiftClass(type) {
    if (type.startsWith("DAG")) return "dag";
    if (type.startsWith("AFTEN")) return "aften";
    if (type.startsWith("NAT")) return "nat";
}

export default function ShiftCard({ shift, onApply }) {
    return (
        <div className={`shift-card ${getShiftClass(shift.type)}`}>
            <p><b>Dato:</b> {shift.date}</p>
            <p><b>Vagt:</b> {shift.type}</p>
            <p><b>Team:</b> {shift.teamName}</p>

            {shift.userName && (
                <p><b>Med:</b> {shift.userName}</p>
            )}

            {shift.open && (
                <button onClick={() => onApply(shift.id)}>
                    Tag vagt
                </button>
            )}
        </div>
    );
}