import React, { useState } from "react";
import PersonalPlan from "./pages/PersonalPlan";
import WorkPlan from "./pages/WorkPlan";

function App() {
    const [tab, setTab] = useState("personal");

    return (
        <div>
            <h1>Booking Plan</h1>

            <button onClick={() => setTab("personal")}>
                Personlig plan
            </button>

            <button onClick={() => setTab("work")}>
                Arbejdsplan
            </button>

            {tab === "personal" && <PersonalPlan />}
            {tab === "work" && <WorkPlan />}
        </div>
    );
}

export default App;