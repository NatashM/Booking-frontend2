package com.example.eksamens24timers;
import com.example.eksamens24timers.model.Drone;
import com.example.eksamens24timers.model.DroneStatus;
import com.example.eksamens24timers.repository.DroneRepository;
import com.example.eksamens24timers.service.DroneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class DeliveryServiceTest {


    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneService droneService;

    @Test
    public void testUpdateDroneStatus() {
        Drone drone = new Drone();
        drone.setSerialNumber(UUID.randomUUID());
        drone.setStatus(DroneStatus.IN_OPERATION);

        when(droneRepository.findById(123L)).thenReturn(Optional.of(drone));

        droneService.updateDroneStatus(123L, DroneStatus.OUT_OF_OPERATION);

        assertEquals(DroneStatus.OUT_OF_OPERATION, drone.getStatus());
        verify(droneRepository).save(drone);
    }

    @Test
    public void testUpdateDroneStatusNotFound() {
        when(droneRepository.findById(123L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            droneService.updateDroneStatus(123L, DroneStatus.OUT_OF_OPERATION);
        });

        assertEquals("Drone not found", exception.getMessage());
    }
}
