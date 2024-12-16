package org.example.springprueba.model.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class EmployeesDTO {
    private int empno;
    @Basic
    @NotEmpty(message = "el nombre no puede estas vacio")
    @Size(min = 2, max = 10, message = "el nombre tiene que tener entre2 y 10")
    @Column(name = "name", nullable = true, length = 10)
    private String name;
    private String job;
    private int depno;
    private String deptName;
    private String deptLoc;

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public void setDeptLoc(String deptLoc) {
        this.deptLoc = deptLoc;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setDepno(int depno) {
        this.depno = depno;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmpno() {
        return empno;
    }

    public String getDeptLoc() {
        return deptLoc;
    }

    public String getDeptName() {
        return deptName;
    }

    public int getDepno() {
        return depno;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }
}
