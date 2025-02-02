package org.example.springprueba.controller;

import jakarta.validation.Valid;
import org.example.springprueba.model.dao.IDeptDap;
import org.example.springprueba.model.dao.IEmployeDao;
import org.example.springprueba.model.entities.Dept;
import org.example.springprueba.model.entities.Employee;
import org.example.springprueba.model.entities.EmployeesDTO;
import org.example.springprueba.service.ServiceEmployees;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private IEmployeDao empDao;
    @Autowired
    private IDeptDap deptDap;
    @Autowired
    private ServiceEmployees serviceEmp;

    @GetMapping("/emp")
    public List<Employee> getEmployees() {
        return serviceEmp.getAllEmployees();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEmployees(@RequestParam("ename") String ename) {
        try{
            List<Employee> employees = serviceEmp.searchEmployeesByName(ename);
            return ResponseEntity.ok(employees);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     *
     *
     *
     *
     *
     *
     */

    @GetMapping("/employees")
    public List<Employee> buscarEmpleado(@RequestParam(name = "job", required = false) String job) {
        if(job == null || job.isEmpty()){
            return serviceEmp.buscarEmpleado();
        }else{
            return serviceEmp.buscarEmpPorTrabajo(job);
        }
    }

    @GetMapping("/employe/{job}")
    public List<Employee> buscarEmpleadoPorJob(@PathVariable("job") String job) {
        return serviceEmp.buscarEmpleadoPorJob(job);
    }

    @PostMapping("/employees/save")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee emp) {

        Employee employee = new Employee();
        employee.setId(emp.getId());
        employee.setEname(emp.getEname());
        employee.setJob(emp.getJob());

        Employee savedEmployee = serviceEmp.saveEmpl(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id")int id) {
        boolean isDeleted = serviceEmp.deleteEmployeById(id);
        if(isDeleted) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
