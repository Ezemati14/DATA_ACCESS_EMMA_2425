package org.example.springprueba.model.dao;

import org.example.springprueba.model.entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeDao extends CrudRepository<Employee, Integer> {

    //Este metodo ignora mayus y minisculas
    //encuentra por
    List<Employee> findByJobContainsIgnoreCase(String lastName);
    //buscar por nombre
    List<Employee> findByEname(String ename);

    List<Employee> findAll();

    List<Employee> findByDept_Id(Integer deptno);
}
