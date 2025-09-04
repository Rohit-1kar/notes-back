package com.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.entity.Notes;

public interface InterfaceRepository extends JpaRepository<Notes, Integer> {

}
