package org.example.springprueba.service;

import org.example.springprueba.model.dao.IEmployeDao;
import org.example.springprueba.model.entities.Employee;
import org.example.springprueba.model.entities.EmployeesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceEmployees {

    @Autowired
    private IEmployeDao empDao;

    public List<Employee> buscarEmpleado() {
            return (List<Employee>) empDao.findAll();
    }

    public List<Employee> buscarEmpleadoPorJob(String job) {
        return empDao.findByJobContainsIgnoreCase(job);
    }

    public List<Employee> buscarEmpPorTrabajo(String lastName) {
        return empDao.findByJobContainsIgnoreCase(lastName);
    }

    public Optional<Employee> empleadoPorId(int id) {
       return empDao.findById(id);
    }

    public Employee saveEmpl(Employee emp) {
        return empDao.save(emp);
    }

    public boolean deleteEmployeById(int id) {
        if (empDao.existsById(id)) {
            empDao.deleteById(id);
            return true; // Indica que la eliminación fue exitosa
        }
        return false; // Indica que no se encontró el empleado
    }
}
