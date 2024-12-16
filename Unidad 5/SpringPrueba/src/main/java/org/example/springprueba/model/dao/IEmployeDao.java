package org.example.springprueba.model.dao;

import org.example.springprueba.model.entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeDao extends CrudRepository<Employee, Integer> {

    //Este metodo ignora mayus y minisculas
    List<Employee> findByJobContainsIgnoreCase(String lastName);
}
