package com.projeto_integrador.projeto_integrador.modules.reservation.usecases;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.reservation.entity.ReservationEntity;
import com.projeto_integrador.projeto_integrador.modules.reservation.repository.ReservationRepository;

@Service
public class CreateReservation {
    
    @Autowired
    ReservationRepository repository;

    @Autowired
    private ReservationValidation reservationValidation;

    public ReservationEntity execute(ReservationEntity reservationEntity){

        if (reservationEntity.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da reserva não pode ser no passado.");
        }


        Long timeId = reservationEntity.getTime().getTimeId();

        Long roomId = reservationEntity.getRoom().getRoomId();

        LocalDate date = reservationEntity.getDate();

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, new Locale("pt", "BR"));

        reservationValidation.searchConflictReservations(date, dayName, roomId, timeId);
        
        return this.repository.save(reservationEntity);
    }
}
