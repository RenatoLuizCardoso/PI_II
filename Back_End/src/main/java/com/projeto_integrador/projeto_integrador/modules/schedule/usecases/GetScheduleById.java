package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetScheduleById {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Map<String, Object> execute(Long id) {
        ScheduleEntity course = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        return convertScheduleToMap(course);
    }

    private Map<String, Object> convertScheduleToMap(ScheduleEntity schedule) {
        Map<String, Object> result = new HashMap<>();
        result.put("scheduleId", schedule.getScheduleId());

        SubjectEntity subject = schedule.getSubject();
        TeacherEntity teacher = schedule.getTeacher();
        TimeEntity time = schedule.getTime();
        CourseEntity course = schedule.getCourse();
        RoomEntity room = schedule.getRoom();
        String weekDay = schedule.getWeekDay();
    
        String subjectName = (subject != null) ? subject.getSubjectName() : "Unknown Subject";
        String teacherName = (teacher != null) ? teacher.getTeacherName() : "Unknown Teacher";
        String timeText = (time != null) ? String.format("%s - %s", time.getStartTime(), time.getEndTime()) : "Unknown Time";
        String courseText = (course != null) ? String.format("%s - %s", course.getCourseName(), course.getCourseSemester()) : "Unknown Course";
        
        String roomText = (room != null) ? String.format("%s - %s", room.getRoomType() != null ? room.getRoomType().getRoomTypeDescription() : "Unknown Type", room.getRoomNumber()) : "Unknown Room";

        result.put("subject", subjectName);
        result.put("teacher", teacherName);
        result.put("time", timeText);
        result.put("course", courseText);
        result.put("weekday", weekDay);
        result.put("room", roomText);
        return result;
    }
}