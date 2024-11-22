package com.projeto_integrador.projeto_integrador.modules.reservation.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.reservation.repository.ReservationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteReservationById {

    @Autowired
    ReservationRepository repository;
    
    public void execute(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Reservation not found");
        }
    }
}
