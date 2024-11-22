package com.projeto_integrador.projeto_integrador.modules.reservation.usecases;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.reservation.entity.ReservationEntity;
import com.projeto_integrador.projeto_integrador.modules.reservation.repository.ReservationRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.FKValidation;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutReservationById {

    @Autowired
    ReservationRepository repository;

    @Autowired
    private FKValidation fkValidation;

    @Autowired
    private ReservationValidation reservationValidation;
    
    public ReservationEntity execute(Long id, ReservationEntity reservationEntity){
        
        ReservationEntity updateReservation = this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Reservation not found with id: " + id)
        );

        if (reservationEntity.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da reserva não pode ser no passado.");
        }

        Long subjectId = reservationEntity.getSubject();
        fkValidation.validateSubjectExist(subjectId);

        Long timeId = reservationEntity.getTime();
        fkValidation.validateTimeExist(timeId);

        Long teacherId = reservationEntity.getTeacher();
        fkValidation.validateTeacherExist(teacherId);

        Long roomId = reservationEntity.getRoom();
        fkValidation.validateRoomExist(roomId);

        Long courseId = reservationEntity.getCourse();
        fkValidation.validateCourseExist(courseId);

        LocalDate date = reservationEntity.getDate();

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, new Locale("pt", "BR"));
        reservationValidation.searchConflictReservations(date, dayName, roomId, timeId);

        updateReservation.setTime(timeId);
        updateReservation.setTeacher(teacherId);
        updateReservation.setDate(reservationEntity.getDate());
        updateReservation.setSubject(subjectId);
        updateReservation.setRoom(roomId);
        updateReservation.setCourse(courseId);


        return this.repository.save(updateReservation);
    }
}
