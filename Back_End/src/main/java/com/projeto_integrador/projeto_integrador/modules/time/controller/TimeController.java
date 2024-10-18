package com.projeto_integrador.projeto_integrador.modules.time.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.CreateTime;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.DeleteTimeById;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.GetAllTimes;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.GetTimeById;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.PutTimeById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("time")
@CrossOrigin
public class TimeController {
    
    @Autowired
    TimeRepository repository;

    @Autowired
    CreateTime createTime;

    @Autowired
    GetAllTimes getAllTimes;

    @Autowired
    GetTimeById getTimeById;

    @Autowired
    PutTimeById putTimeById;

    @Autowired
    DeleteTimeById deleteTimeById;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody TimeEntity timeEntity) {
        try {
            var result = this.createTime.execute(timeEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<TimeEntity>> getAllTimes() {
       try {
            var result = this.getAllTimes.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("Time not Registered");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntity> getById(@Valid @PathVariable long id){
       try {
        var time = this.getTimeById.execute(id);
        return ResponseEntity.ok().body(time);
       } catch (Exception e) {
            throw new EntityNotFoundException("Time not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntity> putTime(@Valid @RequestBody TimeEntity timeEntity, @PathVariable Long id) {
        try {
            var updatedTime = this.putTimeById.execute(id, timeEntity);
            return ResponseEntity.ok().body(updatedTime);
        } catch (Exception e) {
            throw new EntityNotFoundException("Time not found");
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@Valid @PathVariable Long id) {
        this.deleteTimeById.execute(id);
        return ResponseEntity.ok().build();
    }


}
