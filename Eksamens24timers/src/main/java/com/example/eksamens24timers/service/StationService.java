package com.example.eksamens24timers.service;

import com.example.eksamens24timers.model.Station;
import com.example.eksamens24timers.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public Optional<Station> findStationWithFewestDrones() {
        List<Station> stations = stationRepository.findAll();
        return stations.stream()
                .min(Comparator.comparingInt(station -> station.getDrones().size()));
    }
}
