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
import java.util.Optional;
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
        if (stationRepository.count() == 0) {
            stationRepository.save(new Station("København V", 55.41, 12.34));
            stationRepository.save(new Station("København K", 55.43, 12.35));
            stationRepository.save(new Station("Vesterbro", 55.42, 12.33));
        }

        if (pizzaRepository.count() == 0) {
            pizzaRepository.save(new Pizza("Margarita", 90));
            pizzaRepository.save(new Pizza("Pepperoni", 100));
            pizzaRepository.save(new Pizza("Hawaii", 110));
            pizzaRepository.save(new Pizza("Vegetar", 95));
            pizzaRepository.save(new Pizza("Bolognese", 120));
        }


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


        if (deliveryRepository.count() == 0) {
            Optional<Drone> drone1 = droneRepository.findById(1L);
            Optional<Drone> drone2 = droneRepository.findById(2L);
            Optional<Pizza> pizza1 = pizzaRepository.findById(1L);
            Optional<Pizza> pizza2 = pizzaRepository.findById(2L);

            Delivery delivery1 = new Delivery();
            drone1.ifPresent(delivery1::setDrone);
            pizza1.ifPresent(delivery1::setPizza);
            delivery1.setAddress("Street 123, København");
            delivery1.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
            delivery1.setActualDeliveryTime(LocalDateTime.now().plusMinutes(25));
            deliveryRepository.save(delivery1);

            Delivery delivery2 = new Delivery();
            drone2.ifPresent(delivery2::setDrone);
            pizza2.ifPresent(delivery2::setPizza);
            delivery2.setAddress("Avenue 456, København");
            delivery2.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(40));
            delivery2.setActualDeliveryTime(LocalDateTime.now().plusMinutes(38));
            deliveryRepository.save(delivery2);
        }
    }
}
