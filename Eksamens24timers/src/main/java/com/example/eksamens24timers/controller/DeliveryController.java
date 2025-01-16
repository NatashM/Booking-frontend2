package com.example.eksamens24timers.controller;

import com.example.eksamens24timers.model.Delivery;
import com.example.eksamens24timers.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin("*")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/queue")
    public List<Delivery> getDeliveriesWithoutDrone() {
        return deliveryService.getDeliveriesWithoutDrone();
    }

    @PostMapping("/add")
    public Delivery addDelivery(@RequestParam Long pizzaId, @RequestParam String address) {
        return deliveryService.addDelivery(pizzaId, address);
    }

    @PostMapping("/schedule")
    public Delivery scheduleDelivery(@RequestParam Long deliveryId, @RequestParam(required = true) Long droneSerialNumber) {
        return deliveryService.scheduleDelivery(deliveryId, droneSerialNumber);
    }

    @PostMapping("/finish")
    public void finishDelivery(@RequestParam Long deliveryId) {
        deliveryService.finishDelivery(deliveryId);
    }

    @GetMapping("/{id}")
    public Delivery getDeliveryById(@PathVariable Long id) {
        return deliveryService.getDeliveryById(id);
    }

}
