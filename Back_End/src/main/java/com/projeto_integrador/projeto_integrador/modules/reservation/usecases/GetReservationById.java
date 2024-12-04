package com.projeto_integrador.projeto_integrador.modules.reservation.usecases;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.reservation.entity.ReservationEntity;
import com.projeto_integrador.projeto_integrador.modules.reservation.repository.ReservationRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetReservationById {

    @Autowired
    ReservationRepository reservationRepository;

    public Map<String, Object> execute(Long id) {
        ReservationEntity course = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        return convertReservationToMap(course);
    }

    private Map<String, Object> convertReservationToMap(ReservationEntity reservation) {
        Map<String, Object> result = new HashMap<>();
        result.put("reservationId", reservation.getReservationId());

        SubjectEntity subject = reservation.getSubject();
        TeacherEntity teacher = reservation.getTeacher();
        TimeEntity time = reservation.getTime();
        LocalDate date = reservation.getDate();
        CourseEntity course = reservation.getCourse();
        RoomEntity room = reservation.getRoom();

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, new Locale("pt", "BR"));

        String subjectName = (subject != null) ? subject.getSubjectName() : "Unknown Subject";
        String teacherName = (teacher != null) ? teacher.getTeacherName() : "Unknown Teacher";
        String timeText = (time != null) ? String.format("%s - %s", time.getStartTime(), time.getEndTime()) : "Unknown Time";
        String courseText = (course != null) ? String.format("%s - %s", course.getCourseName(), course.getCourseSemester()) : "Unknown Course";
        
        String roomText = (room != null) ? String.format("%s - %s", room.getRoomType() != null ? room.getRoomType().getRoomTypeDescription() : "Unknown Type", room.getRoomNumber()) : "Unknown Room";

        result.put("subject", subjectName);
        result.put("teacher", teacherName);
        result.put("date", date);
        result.put("weekDay", dayName);
        result.put("time", timeText);
        result.put("course", courseText);
        result.put("room", roomText);
        return result;
    }
}
