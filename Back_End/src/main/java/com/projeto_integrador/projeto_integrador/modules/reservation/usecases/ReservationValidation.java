package com.projeto_integrador.projeto_integrador.modules.reservation.usecases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.reservation.entity.ReservationEntity;
import com.projeto_integrador.projeto_integrador.modules.reservation.repository.ReservationRepository;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.AuthAdminUseCase;
import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;

@Service
public class ReservationValidation {
    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public void searchConflictReservations(LocalDate date, String weekDay, Long roomId, Long timeId) {
        TimeEntity newReservationTime = timeRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException("Time not found"));
    
        LocalTime newStartTime = LocalTime.parse(newReservationTime.getStartTime(),
                DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime newEndTime = LocalTime.parse(newReservationTime.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
    
        List<ScheduleEntity> conflictingSchedules = scheduleRepository.findByWeekDayAndRoom(weekDay, roomId);
    
        for (ScheduleEntity schedule : conflictingSchedules) {
            Long timeIdFromSchedule = schedule.getTime();
    
            TimeEntity scheduledTime = timeRepository.findById(timeIdFromSchedule)
                    .orElseThrow(() -> new RuntimeException("Scheduled time not found"));
    
            LocalTime scheduledStartTime = LocalTime.parse(scheduledTime.getStartTime(),
                    DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime scheduledEndTime = LocalTime.parse(scheduledTime.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
    
            if (newStartTime.isBefore(scheduledEndTime) && newEndTime.isAfter(scheduledStartTime)) {
                throw new RuntimeException("There is a conflict with an existing fixed schedule.");
            }
        }

        List<ReservationEntity> conflictingReservations = reservationRepository.findByDateAndRoom(date, roomId);
    
        for (ReservationEntity reservation : conflictingReservations) {
            Long timeIdFromReservation = reservation.getTime();
    
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

    public void validateTimeExist(Long timeId) {
        if (timeId != null) {
            Optional<TimeEntity> time = timeRepository.findById(timeId);
            if (time.isEmpty()) {
                throw new RuntimeException("Time not found with ID: " + timeId);
            }
        }
    }

    public void validateTeacherExist(Long teacherId) {
        if (teacherId != null) {
            Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);
            if (teacher.isEmpty()) {
                throw new RuntimeException("Teacher not found with ID: " + teacherId);
            }
        }
    }

    public void validateSubjectExist(Long subjectId) {
        if (subjectId != null) {
            Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
            if (subject.isEmpty()) {
                throw new RuntimeException("Subject not found with ID: " + subjectId);
            }
        }
    }

    public void validateRoomExist(Long roomId) {
        if (roomId != null) {
            Optional<RoomEntity> room = roomRepository.findById(roomId);
            if (room.isEmpty()) {
                throw new RuntimeException("Room not found with ID: " + roomId);
            }
        }
    }

    public void validateCourseExist(Long courseId) {
        if (courseId != null) {
            Optional<CourseEntity> course = courseRepository.findById(courseId);
            if (course.isEmpty()) {
                throw new RuntimeException("Course not found with ID: " + courseId);
            }
        }
    }
}
