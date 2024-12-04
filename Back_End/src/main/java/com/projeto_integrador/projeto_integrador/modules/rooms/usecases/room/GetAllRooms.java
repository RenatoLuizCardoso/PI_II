package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;

@Service
public class GetAllRooms {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    public List<Map<String, Object>> execute() {
        var allRooms = roomRepository.findAll();
        if (allRooms.isEmpty()) {
            throw new EntityNotFoundException("Room not Registered");
        }

        return allRooms.stream()
                .map(this::convertRoomToMap)
                .collect(Collectors.toList());
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
