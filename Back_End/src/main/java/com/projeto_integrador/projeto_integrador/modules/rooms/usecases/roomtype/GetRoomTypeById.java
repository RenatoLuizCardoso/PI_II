package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetRoomTypeById {
    
    @Autowired
    RoomTypeRepository repository;
    
    public RoomTypeEntity execute(@PathVariable Long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("RoomType not found")
        );
    }
}
