package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllSchedules {
    
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TimeRepository timeRepository;

    @Autowired
    CourseRepository courseRepository;
    

    public List<Map<String, Object>> execute() {
        var allSchedules = scheduleRepository.findAll();
        if (allSchedules.isEmpty()) {
            throw new EntityNotFoundException("Schedule not Registered");
        }

        return allSchedules.stream()
                          .map(this::convertScheduleToMap)
                          .collect(Collectors.toList());
    }

    private Map<String, Object> convertScheduleToMap(ScheduleEntity schedule) {
        Map<String, Object> result = new HashMap<>();
        result.put("scheduleId", schedule.getScheduleId());

        Long subjectId = schedule.getSubject();
        Long teacherId = schedule.getTeacher();
        Long timeId = schedule.getTime();
        Long courseId = schedule.getCourse();

        Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
        String subjectName = subject.map(SubjectEntity::getSubjectName)
                                    .orElse("Unknown Subject");

        Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);
        String teacherName = teacher.map(TeacherEntity::getTeacherName)
                                    .orElse("Unknown Teacher");

        Optional<TimeEntity> time = timeRepository.findById(timeId);
        String timeText = time.map(t -> String.format("%s - %s (%s)", t.getStartTime(), t.getEndTime(), t.getWeekDay()))
                                    .orElse("Unknown Time");

        Optional<CourseEntity> course = courseRepository.findById(courseId);
        String courseText = course.map(c -> String.format("%s - %s", c.getCourseName(), c.getCourseSemester()))
                                    .orElse("Unknown Course");


        result.put("subject", subjectName);
        result.put("teacher", teacherName);
        result.put("time", timeText);
        result.put("course", courseText);
        return result;
    }
}

