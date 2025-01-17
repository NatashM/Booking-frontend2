package com.example.eksamens24timers.controller;

import com.example.eksamens24timers.dto.DeliveryRequest;
import com.example.eksamens24timers.dto.DroneAssignmentRequest;
import com.example.eksamens24timers.model.Delivery;
import com.example.eksamens24timers.model.DeliveryStatus;
import com.example.eksamens24timers.model.Drone;
import com.example.eksamens24timers.service.DeliveryService;
import com.example.eksamens24timers.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin("*")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DroneService droneService;

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/queue")
    public ResponseEntity<List<Delivery>> getDeliveriesWithoutDrone() {
        List<Delivery> deliveries = deliveryService.getDeliveriesWithoutDrone();
        return ResponseEntity.ok(deliveries);
    }

    @PostMapping("/add")
    public ResponseEntity<Delivery> addDelivery(@RequestBody DeliveryRequest deliveryRequest) {
        // Handle the pizzaName instead of pizzaId
        Delivery savedDelivery = deliveryService.addDelivery(deliveryRequest.getPizzaName(), deliveryRequest.getAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDelivery);
    }



    @PostMapping("/{id}/schedule")
    public ResponseEntity<Delivery> scheduleDelivery(@PathVariable Long id, @RequestBody DroneAssignmentRequest request) {
        // Fetch the delivery
        Delivery delivery = deliveryService.getDeliveryById(id);
        if (delivery == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Check if droneId is null in the request
        Long droneId = request.getDroneId();
        if (droneId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);  // Just return a null body (you can customize this if needed)
        }

        // Fetch the drone using the provided droneId
        Drone drone = droneService.getDroneById(droneId);
        if (drone == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);  // Just return a null body (you can customize this if needed)
        }

        // Assign the drone to the delivery
        delivery.setDrone(drone);

        // Save the updated delivery
        deliveryService.saveDelivery(delivery);

        // Return the updated delivery object as a response
        return ResponseEntity.ok(delivery);
    }

    // Assuming you're using Spring Boot for the backend
    @PostMapping("/{id}/update-status")
    public ResponseEntity<?> updateDeliveryStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        Delivery delivery = deliveryService.getDeliveryById(id);
        if (delivery == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery not found");
        }

        String newStatus = statusUpdate.get("status");
        if (newStatus == null || newStatus.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status is required");
        }

        try {
            DeliveryStatus status = DeliveryStatus.valueOf(newStatus.toUpperCase());
            delivery.setDeliveryStatus(status);
            deliveryService.saveDelivery(delivery);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status");
        }
    }

    @PostMapping("/finish")
    public ResponseEntity<Void> finishDelivery(@RequestParam Long deliveryId) {
        deliveryService.finishDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        Delivery delivery = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(delivery);
    }

}
