package com.example.eksamens24timers.service;

import com.example.eksamens24timers.model.Drone;
import com.example.eksamens24timers.model.DroneStatus;
import com.example.eksamens24timers.model.Station;
import com.example.eksamens24timers.repository.DroneRepository;
import com.example.eksamens24timers.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private StationRepository stationRepository;


    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    public Drone addDrone() {
        List<Station> stations = stationRepository.findAll();
        if (stations.isEmpty()) {
            throw new IllegalStateException("No station are able to add a drone.");
        }

        Station chosenStation = stations.stream()
                .min(Comparator.comparingInt(station -> station.getDrones().size()))
                .orElseThrow();

        Drone newDrone = new Drone(UUID.randomUUID(), DroneStatus.IN_OPERATION, chosenStation);
        droneRepository.save(newDrone);
        return newDrone;
    }
    public void updateDroneStatus(Long serialNumber, DroneStatus status) {
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new RuntimeException("Drone not found"));
        drone.setStatus(status);
        droneRepository.save(drone);
    }

    public Drone getDroneById(Long id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drone not found"));
    }

}
