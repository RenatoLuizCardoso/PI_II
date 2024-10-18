package com.projeto_integrador.projeto_integrador.modules.rooms;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;


@Service
public class RoomValidation {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public void validateRoomTypeExist(Long roomTypeId) {
        if (roomTypeId != null) {
            Optional<RoomTypeEntity> roomType = roomTypeRepository.findById(roomTypeId);
            if (roomType.isEmpty()) {
                throw new RuntimeException("RoomType not found with ID: " + roomTypeId);
            }
        }
    }
}