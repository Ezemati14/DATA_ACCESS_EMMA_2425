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

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") int id) {
        Optional<Employee> emp = serviceEmp.empleadoPorId(id);
        if(emp.isPresent()) {
            return ResponseEntity.ok().body(emp.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/employees/save")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeesDTO emp) {

        Employee employee = new Employee();
        employee.setId(emp.getEmpno());
        employee.setEname(emp.getName());
        employee.setJob(emp.getJob());

        Employee savedEmployee = serviceEmp.saveEmpl(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    //Definimos el endpoints que recibe un id por parametro
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmploye(@PathVariable(value = "id")Integer id, @RequestBody Employee emp) {
        //Buscamos al empleoad por la base de datos, el resultado es optional
        //Si no existe retorna un 404 not found
        Optional<Employee> employe = serviceEmp.empleadoPorId(id);

        if(!employe.isPresent()) {
            return ResponseEntity.notFound().build();//Retorna un 404 si no se encuentra
        }
        //Con esto obtenemos el empleado encontado
        Employee existEmployee = employe.get();
        //Actualizamos los datos necesarios
        if(emp.getEname() != null) {
            existEmployee.setEname(emp.getEname());
        }
        if(emp.getJob() != null) {
            existEmployee.setJob(emp.getJob());
        }
        //Guardamos los datos en la base de datos, y retornamos el empleado actualizado
        Employee updatedEmployee = serviceEmp.saveEmpl(existEmployee);
        return ResponseEntity.ok(updatedEmployee);
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

    @GetMapping("/dto/{id}")
    public ResponseEntity<EmployeesDTO> getEmployeesDtoId(@PathVariable(value = "id") int id) {
        Optional<Employee> emp = empDao.findById(id);

        //MAPEO MANUAL  DE NUTRO DTO
        if(emp.isPresent()) {
            Optional<Dept> departamentos = deptDap.findById(emp.get().getDeptno());

            EmployeesDTO empDTO = new EmployeesDTO();
            empDTO.setEmpno(emp.get().getId());
            empDTO.setName(emp.get().getEname());
            empDTO.setJob(emp.get().getJob());
            empDTO.setDepno(emp.get().getDeptno());
            empDTO.setDeptName(departamentos.get().getDname());
            empDTO.setDeptLoc(departamentos.get().getLoc());

            return ResponseEntity.ok().body(empDTO);
        }else {
            return ResponseEntity.notFound().build();
        }

        //MAPEADO CON MODELMAPPER
        /**if(emp.isPresent()) {
            Optional<Dept> departamentos = deptDap.findById(emp.get().getDeptno());

            ModelMapper mapper = new ModelMapper();
            EmployeesDTO employeesDTO = mapper.map(emp.get(), EmployeesDTO.class);
            mapper.typeMap(Dept.class, EmployeesDTO.class)
                    .addMappings( m -> m.skip(EmployeesDTO::setName));
            mapper.map(departamentos.get(), employeesDTO);

            return ResponseEntity.ok().body(employeesDTO);
        }else {
            return ResponseEntity.notFound().build();
        }**/
    }

}
