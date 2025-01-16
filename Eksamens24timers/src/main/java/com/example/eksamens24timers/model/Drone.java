package com.example.eksamens24timers.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID serialNumber;

    @ManyToOne
    private Station station;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    private List<Delivery> deliveries = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DroneStatus status;

    public DroneStatus getStatus() {
        return status;
    }
    public void setStatus(DroneStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
    }



    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }


    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
