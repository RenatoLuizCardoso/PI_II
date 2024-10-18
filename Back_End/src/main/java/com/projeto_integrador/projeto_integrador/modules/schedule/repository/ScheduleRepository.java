package com.projeto_integrador.projeto_integrador.modules.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;


public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long>{}