package com.projeto_integrador.projeto_integrador.modules.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;

public interface TimeRepository extends JpaRepository<TimeEntity, Long>{}