package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteRoomById {

    @Autowired
    RoomRepository repository;
    
    public void execute(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Room not found");
        }
    }
}
