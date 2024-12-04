package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetRoomById {

    @Autowired
    RoomRepository repository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    public Map<String, Object> execute(Long id) {
        RoomEntity room = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        return convertRoomToMap(room);
    }

    private Map<String, Object> convertRoomToMap(RoomEntity room) {
        Map<String, Object> result = new HashMap<>();
        result.put("roomId", room.getRoomId());
        result.put("roomCapacity", room.getRoomCapacity());
        result.put("roomFloor", room.getRoomFloor());
        result.put("roomResources", room.getRoomResources());
        result.put("roomAvailability", room.getRoomAvailability());

        RoomTypeEntity roomType = room.getRoomType();
        String roomTypeName = (roomType != null) ? roomType.getRoomTypeDescription() : "Unknown Subject";

        result.put("roomType", roomTypeName);
        return result;
    }
}
