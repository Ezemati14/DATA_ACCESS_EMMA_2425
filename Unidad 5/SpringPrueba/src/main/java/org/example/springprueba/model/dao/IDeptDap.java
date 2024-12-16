package org.example.springprueba.model.dao;

import org.example.springprueba.model.entities.Dept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeptDap extends CrudRepository<Dept, Integer> {

    List<Dept> findByDnameContainsIgnoreCase(String name);
    List<Dept> findByLocContainsIgnoreCase(String name);
}
