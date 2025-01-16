package com.example.eksamens24timers.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.eksamens24timers.model.Drone;
import com.example.eksamens24timers.model.DroneStatus;
import com.example.eksamens24timers.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/drones")
@CrossOrigin("http://localhost:63342")
public class DroneController {


    @Autowired
    private DroneService droneService;

    @GetMapping
    public ResponseEntity<List<Drone>> getAllDrones() {
        List<Drone> drones = droneService.getAllDrones();
        return ResponseEntity.ok(drones);
    }

    @PostMapping("/add")
    public ResponseEntity<Drone> addDrone() {
        Drone drone = droneService.addDrone();
        return ResponseEntity.status(HttpStatus.CREATED).body(drone);
    }

    @PostMapping("/enable")
    public ResponseEntity<Void> enableDrone(@RequestParam Long serialNumber) {
        droneService.updateDroneStatus(serialNumber, DroneStatus.IN_OPERATION);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disable")
    public ResponseEntity<Void> disableDrone(@RequestParam Long serialNumber) {
        droneService.updateDroneStatus(serialNumber, DroneStatus.OUT_OF_OPERATION);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/retire")
    public ResponseEntity<Void> retireDrone(@RequestParam Long serialNumber) {
        droneService.updateDroneStatus(serialNumber, DroneStatus.RETIRED);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drone> getDroneById(@PathVariable Long id) {
        Drone drone = droneService.getDroneById(id);
        return ResponseEntity.ok(drone);
    }



}
