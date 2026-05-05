const baseUrl = "http://localhost:8080";


async function fetchDeliveries() {
    try {
        const response = await fetch(`${baseUrl}/deliveries`);
        if (!response.ok) {
            throw new Error('Failed to fetch deliveries');
        }
        const deliveries = await response.json();
        console.log('Fetched deliveries:', deliveries); //

        const deliveryList = document.getElementById('delivery-list');
        deliveryList.innerHTML = deliveries
            .filter(delivery => delivery.status !== 'DELIVERED')
            .map(delivery => {
                console.log(`Delivery ID: ${delivery.id}, Status: ${delivery.status}`);

                return `<tr data-id="${delivery.id}">
                    <td>${delivery.id}</td>
                    <td>${delivery.address}</td>
                    <td>${delivery.pizza ? delivery.pizza.title : 'Ingen pizza valgt'}</td>
                    <td>${delivery.status || 'Pending'}</td>
                    <td>
                        ${!delivery.drone ?
                    `<button class="assign-drone" onclick="assignDroneToDelivery(${delivery.id})">Tildel Drone</button>` :
                    `Drone Serial: ${delivery.drone.serialNumber}`}
                        ${delivery.status === 'IN_PROGRESS' ?
                    `<button onclick="markAsDelivered(${delivery.id})">Afslut Levering</button>`
                    : ''}
                    </td>
                </tr>`;
            })
            .join('');
    } catch (error) {
        console.error('Error fetching deliveries:', error);
    }
}




// Add a new delivery
document.getElementById("add-delivery-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const pizzaName = document.getElementById("pizzaName").value;
    const address = document.getElementById("address").value;

    try {
        const response = await fetch(`${baseUrl}/deliveries/add`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ pizzaName, address }),
        });
        if (response.ok) {
            alert("Delivery added successfully!");
            document.getElementById("add-delivery-form").reset();
            fetchDeliveries();
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

        const availableDrones = drones.filter(drone => drone.status === "IN_OPERATION" || drone.status === "Idle");

        const droneList = document.getElementById("drone-list");
        droneList.innerHTML = `
    <h3>All Drones</h3>
    ${drones.map(drone => `<p>Drone Serial: ${drone.serialNumber}, Status: ${drone.status}</p>`).join("")}
  
    ${availableDrones.length > 0
            ? availableDrones.map(drone => `<p>Drone Serial: ${drone.serialNumber}, Status: ${drone.status}</p>`).join("")
            : "<p>No available drones</p>"
        }
`;
        return availableDrones;
    } catch (error) {
        console.error("Error fetching drones:", error);
        alert("Failed to fetch drones.");
        return [];
    }
}

// Add a new drone
document.getElementById("add-drone-btn").addEventListener("click", async () => {
    try {
        const response = await fetch(`${baseUrl}/drones/add`, {
            method: "POST",
            headers: { "Content-Type": "application/json" }
        });
        if (response.ok) {
            alert("Drone added successfully!");
            fetchDrones();
        } else {
            console.error("Failed to add drone:", response.statusText);
            alert("Failed to add drone.");
        }
    } catch (error) {
        console.error("Error adding drone:", error);
        alert("An unexpected error occurred.");
    }
});


async function assignDroneToDelivery(deliveryId) {
    try {
        const availableDrones = await fetchDrones();
        if (availableDrones.length > 0) {
            const droneId = availableDrones[0].id;
            const response = await fetch(`${baseUrl}/deliveries/${deliveryId}/schedule`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ droneId }),
            });

            if (!response.ok) {
                const errorDetails = await response.json();
                throw new Error(`Fejl ved tildeling af drone: ${JSON.stringify(errorDetails)}`);
            }

            // Successfully assigned drone, update status to 'IN_PROGRESS'
            const updateResponse = await fetch(`${baseUrl}/deliveries/${deliveryId}/update-status`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ status: 'IN_PROGRESS' })
            });

            if (updateResponse.ok) {
                console.log('Delivery status updated to IN_PROGRESS');


                const deliveryRow = document.querySelector(`tr[data-id='${deliveryId}']`);
                if (deliveryRow) {
                    const statusCell = deliveryRow.querySelector('td:nth-child(4)');
                    if (statusCell) {
                        statusCell.textContent = 'IN_PROGRESS';
                    }
                }
            } else {
                const errorMessage = await updateResponse.text();
                console.error('Failed to update status:', errorMessage);
                alert('Failed to update delivery status');
            }
        } else {
            alert("No available drones to assign.");
        }
    } catch (error) {
        console.error(error);
        alert("An error occurred while assigning the drone.");
    }
}



async function markAsDelivered(deliveryId) {
    try {
        const response = await fetch(`${baseUrl}/deliveries/${deliveryId}/update-status`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ status: 'DELIVERED' })
        });

        if (response.ok) {
            console.log('Delivery marked as delivered');


            const deliveryRow = document.querySelector(`tr[data-id='${deliveryId}']`);
            if (deliveryRow) {
                const statusCell = deliveryRow.querySelector('td:nth-child(4)');
                if (statusCell) {
                    statusCell.textContent = 'DELIVERED';
                }


                setTimeout(() => {
                    deliveryRow.remove();
                }, 500);
            }
        } else {
            const errorMessage = await response.text();
            console.error('Failed to mark delivery as delivered:', errorMessage);
            alert('Failed to mark delivery as delivered');
        }
    } catch (error) {
        console.error(error);
        alert("An error occurred while marking the delivery as delivered.");
    }
}


setInterval(() => {
    fetchDeliveries();
    fetchDrones();
}, 60000);

init();
