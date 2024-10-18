package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllRoomTypes {
    
    @Autowired
    RoomTypeRepository roomRepository;

    public List<RoomTypeEntity> execute() {
        var allRoomTypes = roomRepository.findAll();
        if (allRoomTypes.isEmpty()) {
            throw new EntityNotFoundException("RoomType not Registered");
        }
        return allRoomTypes;
    }
}

