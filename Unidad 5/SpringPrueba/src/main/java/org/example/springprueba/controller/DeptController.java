package org.example.springprueba.controller;

import org.example.springprueba.model.dao.IDeptDap;
import org.example.springprueba.model.entities.Dept;
import org.example.springprueba.model.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeptController {

    @Autowired
    private IDeptDap deptDap;

    @GetMapping()
    public List<Dept> getEmployees() {
        return  (List<Dept>) deptDap.findAll();
    }

    @GetMapping("/depts")
    public List<Dept> getNameDepts(@RequestParam(name = "dname", required = false) String dname){
        if(dname == null || dname.isEmpty()){
            return (List<Dept>) deptDap.findAll();
        }else {
            return deptDap.findByDnameContainsIgnoreCase(dname);
        }
    }

    @GetMapping("/loc")
    public List<Dept> getLocDepts(@RequestParam(name = "loc", required = false)String loc){
        if(loc == null || loc.isEmpty()) {
            return (List<Dept>) deptDap.findAll();
        }else {
            return deptDap.findByLocContainsIgnoreCase(loc);
        }
    }

    @GetMapping("/depts/{dname}")
    public List<Dept> getNameDeptsId(@PathVariable("dname") String dname) {
        return deptDap.findByDnameContainsIgnoreCase(dname);
    }

    @GetMapping("/loc/{loc}")
    public List<Dept> getLocDeptsId(@PathVariable("loc") String loc) {
        return deptDap.findByLocContainsIgnoreCase(loc);
    }
}
