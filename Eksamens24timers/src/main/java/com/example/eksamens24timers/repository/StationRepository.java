package com.example.eksamens24timers.repository;

import com.example.eksamens24timers.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    @Query("SELECT s FROM Station s WHERE (SELECT COUNT(d) FROM Drone d WHERE d.station = s) = " +
            "(SELECT MIN((SELECT COUNT(d2) FROM Drone d2 WHERE d2.station = s2)) FROM Station s2)")
    Optional<Station> findStationWithFewestDrones();




}
