package com.example.eksamens24timers.service;

import com.example.eksamens24timers.model.Delivery;
import com.example.eksamens24timers.model.Drone;
import com.example.eksamens24timers.model.DroneStatus;
import com.example.eksamens24timers.model.Pizza;
import com.example.eksamens24timers.repository.DeliveryRepository;
import com.example.eksamens24timers.repository.DroneRepository;
import com.example.eksamens24timers.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DroneRepository droneRepository;

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public List<Delivery> getDeliveriesWithoutDrone() {
        return deliveryRepository.findByDroneIsNull();
    }

    public Delivery addDelivery(Long pizzaId, String address) {
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));

        Delivery delivery = new Delivery();
        delivery.setPizza(pizza);
        delivery.setAddress(address);
        delivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));

        return deliveryRepository.save(delivery);
    }

    public Delivery scheduleDelivery(Long deliveryId, Long droneSerialNumber) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        if (delivery.getDrone() != null) {
            throw new RuntimeException("Delivery already has a drone assigned.");
        }

        Drone drone = droneRepository.findById(droneSerialNumber)
                .orElseThrow(() -> new RuntimeException("Drone not found"));

        if (drone.getStatus() != DroneStatus.IN_OPERATION) {
            throw new RuntimeException("Drone is not in operation.");
        }

        delivery.setDrone(drone);
        return deliveryRepository.save(delivery);
    }

    public void finishDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        if (delivery.getDrone() == null) {
            throw new RuntimeException("Delivery has no drone assigned.");
        }

        delivery.setActualDeliveryTime(LocalDateTime.now());
        deliveryRepository.save(delivery);
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

}
