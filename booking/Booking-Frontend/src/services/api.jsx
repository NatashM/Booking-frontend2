const BASE_URL = "http://localhost:8080/api";

// 🔹 Hent ledige vagter
export async function getOpenShifts() {
    const res = await fetch(`${BASE_URL}/shifts/open`);
    return res.json();
}

// 🔹 Hent egne vagter
export async function getUserShifts(userId) {
    const res = await fetch(`${BASE_URL}/shifts/user/${userId}`);
    return res.json();
}

// 🔹 Ansøg om vagt (DTO!)
export async function applyForShift(shiftId, userId) {
    const res = await fetch(`${BASE_URL}/shifts/${shiftId}/apply`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ userId }) // 👈 matcher ApplyShiftDTO
    });

    if (!res.ok) {
        const text = await res.text();
        throw new Error(text);
    }

    return res.json();
}