package com.example.eksamens24timers.repository;

import com.example.eksamens24timers.model.Drone;
import com.example.eksamens24timers.model.DroneStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByStatus(DroneStatus status);
}
