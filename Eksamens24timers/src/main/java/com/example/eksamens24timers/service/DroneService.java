package com.example.eksamens24timers.service;

import com.example.eksamens24timers.model.Drone;
import com.example.eksamens24timers.model.DroneStatus;
import com.example.eksamens24timers.model.Station;
import com.example.eksamens24timers.repository.DroneRepository;
import com.example.eksamens24timers.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<Station> station = stationRepository.findStationWithFewestDrones();
        if (station.isPresent()) {
            Drone drone = new Drone();
            drone.setSerialNumber(UUID.randomUUID());
            drone.setStation(station.get());
            drone.setStatus(DroneStatus.IN_OPERATION);
            return droneRepository.save(drone);
        } else {
            throw new RuntimeException("No stations available to assign a drone.");
        }
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
