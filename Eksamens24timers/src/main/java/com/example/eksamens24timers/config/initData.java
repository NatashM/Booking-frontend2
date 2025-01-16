package com.example.eksamens24timers.config;

import com.example.eksamens24timers.model.*;
import com.example.eksamens24timers.repository.DeliveryRepository;
import com.example.eksamens24timers.repository.DroneRepository;
import com.example.eksamens24timers.repository.PizzaRepository;
import com.example.eksamens24timers.repository.StationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class initData {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @PostConstruct
    public void init() {
        // Initialisering af Stationer
        if (stationRepository.count() == 0) {
            stationRepository.save(new Station("København V", 55.41, 12.34));
            stationRepository.save(new Station("København K", 55.43, 12.35));
            stationRepository.save(new Station("Vesterbro", 55.42, 12.33));
        }

        // Initialisering af Pizzaer
        if (pizzaRepository.count() == 0) {
            pizzaRepository.save(new Pizza("Margarita", 90));
            pizzaRepository.save(new Pizza("Pepperoni", 100));
            pizzaRepository.save(new Pizza("Hawaii", 110));
            pizzaRepository.save(new Pizza("Vegetar", 95));
            pizzaRepository.save(new Pizza("Bolognese", 120));
        }

        // Initialisering af Droner
        if (droneRepository.count() == 0) {
            Drone drone1 = new Drone();
            drone1.setSerialNumber(UUID.randomUUID());
            drone1.setStation(stationRepository.findById(1L).orElse(null)); // Assign station
            drone1.setStatus(DroneStatus.IN_OPERATION);
            droneRepository.save(drone1);

            Drone drone2 = new Drone();
            drone2.setSerialNumber(UUID.randomUUID());
            drone2.setStation(stationRepository.findById(2L).orElse(null)); // Assign station
            drone2.setStatus(DroneStatus.OUT_OF_OPERATION);
            droneRepository.save(drone2);
        }

        // Initialisering af Leverancer
        if (deliveryRepository.count() == 0) {
            Delivery delivery1 = new Delivery();
            delivery1.setDrone(droneRepository.findById(1L).orElse(null)); // Assign drone
            delivery1.setPizza(pizzaRepository.findById(1L).orElse(null)); // Assign pizza
            delivery1.setAddress("Street 123, København");
            delivery1.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
            delivery1.setActualDeliveryTime(LocalDateTime.now().plusMinutes(25));
            deliveryRepository.save(delivery1);

            Delivery delivery2 = new Delivery();
            delivery2.setDrone(droneRepository.findById(2L).orElse(null)); // Assign drone
            delivery2.setPizza(pizzaRepository.findById(2L).orElse(null)); // Assign pizza
            delivery2.setAddress("Avenue 456, København");
            delivery2.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(40));
            delivery2.setActualDeliveryTime(LocalDateTime.now().plusMinutes(38));
            deliveryRepository.save(delivery2);
        }
    }
}
