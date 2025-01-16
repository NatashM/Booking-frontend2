package com.example.eksamens24timers.controller;

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
@CrossOrigin("*")
public class DroneController {


        @Autowired
        private DroneService droneService;

        @GetMapping
        public List<Drone> getAllDrones() {
            return droneService.getAllDrones();
        }

        @PostMapping("/add")
        public Drone addDrone() {
            return droneService.addDrone();
        }

        @PostMapping("/enable")
        public void enableDrone(@RequestParam Long serialNumber) {
            droneService.updateDroneStatus(serialNumber, DroneStatus.IN_OPERATION);
        }

        @PostMapping("/disable")
        public void disableDrone(@RequestParam Long serialNumber) {
            droneService.updateDroneStatus(serialNumber, DroneStatus.OUT_OF_OPERATION);
        }

        @PostMapping("/retire")
        public void retireDrone(@RequestParam Long serialNumber) {
            droneService.updateDroneStatus(serialNumber, DroneStatus.RETIRED);
        }

    @GetMapping("/{id}")
    public Drone getDroneById(@PathVariable Long id) {
        return droneService.getDroneById(id);
    }


}
