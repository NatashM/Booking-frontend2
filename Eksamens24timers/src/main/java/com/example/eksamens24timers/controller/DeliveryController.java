package com.example.eksamens24timers.controller;

import com.example.eksamens24timers.dto.DeliveryRequest;
import com.example.eksamens24timers.model.Delivery;
import com.example.eksamens24timers.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin("http://localhost:63342")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

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



    @PostMapping("/schedule")
    public ResponseEntity<Delivery> scheduleDelivery(@RequestParam Long deliveryId, @RequestParam Long droneSerialNumber) {
        Delivery scheduledDelivery = deliveryService.scheduleDelivery(deliveryId, droneSerialNumber);
        return ResponseEntity.ok(scheduledDelivery);
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
