const baseUrl = "http://localhost:8080"; // Erstat med din backend's URL

// Fetch and display deliveries
async function fetchDeliveries() {
    try {
        const response = await fetch(`${baseUrl}/deliveries`);
        if (!response.ok) {
            throw new Error("Failed to fetch deliveries");
        }
        const deliveries = await response.json();
        const deliveryList = document.getElementById("delivery-list");

        // Sort deliveries by creation time (oldest first)
        deliveries.sort((a, b) => new Date(a.creationTime) - new Date(b.creationTime));

        deliveryList.innerHTML = deliveries
            .map(delivery => {
                return `<tr>
                    <td>${delivery.id}</td>
                    <td>${delivery.address}</td>
                    <td>${delivery.pizza.title || 'Ingen pizza valgt'}</td>
                    <td>${delivery.status || 'Pending'}</td>
                    <td>
                        ${delivery.status === 'Pending' ?
                    `<button onclick="assignDrone(${delivery.id})">Tildel Drone</button>` : ''}
                        ${delivery.status === 'Assigned' ?
                    `<button onclick="markAsDelivered(${delivery.id})">Afslut Levering</button>` : ''}
                    </td>
                </tr>`;
            })
            .join("");
    } catch (error) {
        console.error("Error fetching deliveries:", error);
        alert("Failed to fetch deliveries.");
    }
}

// Add a new delivery
document.getElementById("add-delivery-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const pizzaName = document.getElementById("pizzaName").value;  // Get pizza name from dropdown
    const address = document.getElementById("address").value;

    try {
        const response = await fetch(`${baseUrl}/deliveries/add`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ pizzaName, address }),
        });
        if (response.ok) {
            alert("Delivery added successfully!");
            document.getElementById("add-delivery-form").reset(); // Clear the form fields
            fetchDeliveries(); // Refresh the delivery list
        } else {
            const errorMessage = await response.text();
            console.error("Failed to add delivery:", errorMessage);
            alert(`Error: ${errorMessage}`);
        }
    } catch (error) {
        console.error("Error adding delivery:", error);
        alert("An unexpected error occurred.");
    }
});

// Fetch and display drones
async function fetchDrones() {
    try {
        const response = await fetch(`${baseUrl}/drones`);
        if (!response.ok) {
            throw new Error("Failed to fetch drones");
        }
        const drones = await response.json();
        const droneList = document.getElementById("drone-list");
        droneList.innerHTML = drones
            .map(drone => `<p>Drone Serial: ${drone.serialNumber}, Status: ${drone.status}</p>`)
            .join("");
    } catch (error) {
        console.error("Error fetching drones:", error);
        alert("Failed to fetch drones.");
    }
}

// Add a new drone
document.getElementById("add-drone-btn").addEventListener("click", async () => {
    try {
        const response = await fetch(`${baseUrl}/drones/add`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ serialNumber: "NewSerial001", status: "Idle" }) // Example data for drone
        });
        if (response.ok) {
            alert("Drone added successfully!");
            fetchDrones(); // Refresh the drone list
        } else {
            console.error("Failed to add drone:", response.statusText);
            alert("Failed to add drone.");
        }
    } catch (error) {
        console.error("Error adding drone:", error);
        alert("An unexpected error occurred.");
    }
});

// Assign drone to delivery
async function assignDrone(deliveryId) {
    try {
        const response = await fetch(`${baseUrl}/deliveries/${deliveryId}/assign-drone`, {
            method: "POST",
            headers: { "Content-Type": "application/json" }
        });
        if (response.ok) {
            alert("Drone assigned successfully!");
            fetchDeliveries(); // Refresh the delivery list
        } else {
            console.error("Failed to assign drone:", response.statusText);
            alert("Failed to assign drone.");
        }
    } catch (error) {
        console.error("Error assigning drone:", error);
        alert("An unexpected error occurred.");
    }
}

// Mark delivery as delivered
async function markAsDelivered(deliveryId) {
    try {
        const response = await fetch(`${baseUrl}/deliveries/${deliveryId}/mark-delivered`, {
            method: "POST",
            headers: { "Content-Type": "application/json" }
        });
        if (response.ok) {
            alert("Delivery marked as delivered!");
            fetchDeliveries(); // Refresh the delivery list
        } else {
            console.error("Failed to mark as delivered:", response.statusText);
            alert("Failed to mark as delivered.");
        }
    } catch (error) {
        console.error("Error marking as delivered:", error);
        alert("An unexpected error occurred.");
    }
}

// Initialize page with deliveries and drones
function init() {
    fetchDeliveries(); // Fetch initial list of deliveries
    fetchDrones(); // Fetch initial list of drones
    setInterval(() => fetchDeliveries(), 60000); // Refresh the deliveries every 60 seconds
}

init();
