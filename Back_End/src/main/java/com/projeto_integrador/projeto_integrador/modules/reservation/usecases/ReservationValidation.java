package com.projeto_integrador.projeto_integrador.modules.reservation.usecases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.reservation.entity.ReservationEntity;
import com.projeto_integrador.projeto_integrador.modules.reservation.repository.ReservationRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;

@Service
public class ReservationValidation {
    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void searchConflictReservations(LocalDate date, String weekDay, Long roomId, Long timeId) {
        RoomEntity room = roomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Room not found"));
            
        TimeEntity newReservationTime = timeRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException("Time not found"));
        
        LocalTime newStartTime = LocalTime.parse(newReservationTime.getStartTime(),
                DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime newEndTime = LocalTime.parse(newReservationTime.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));

        
        // Verificando conflitos de agendamento
        List<ScheduleEntity> conflictingSchedules = scheduleRepository.findByWeekDayAndRoom(weekDay, room);
        
        for (ScheduleEntity schedule : conflictingSchedules) {
            Long timeIdFromSchedule = schedule.getTime().getTimeId();
            
            TimeEntity scheduledTime = timeRepository.findById(timeIdFromSchedule)
                    .orElseThrow(() -> new RuntimeException("Scheduled time not found"));
            
            LocalTime scheduledStartTime = LocalTime.parse(scheduledTime.getStartTime(),
                    DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime scheduledEndTime = LocalTime.parse(scheduledTime.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
            
            if (newStartTime.isBefore(scheduledEndTime) && newEndTime.isAfter(scheduledStartTime)) {
                throw new RuntimeException("There is a conflict with an existing fixed schedule.");
            }
        }
    
        // Verificando conflitos de reservas existentes
        List<ReservationEntity> conflictingReservations = reservationRepository.findByDateAndRoom(date, room);
        
        for (ReservationEntity reservation : conflictingReservations) {
            Long timeIdFromReservation = reservation.getTime().getTimeId();
            
            TimeEntity reservedTime = timeRepository.findById(timeIdFromReservation)
                    .orElseThrow(() -> new RuntimeException("Reserved time not found"));
            
            LocalTime reservedStartTime = LocalTime.parse(reservedTime.getStartTime(),
                    DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime reservedEndTime = LocalTime.parse(reservedTime.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
            
            if (newStartTime.isBefore(reservedEndTime) && newEndTime.isAfter(reservedStartTime)) {
                throw new RuntimeException("There is a conflict with an existing reservation.");
            }
        }
    }
    


}
