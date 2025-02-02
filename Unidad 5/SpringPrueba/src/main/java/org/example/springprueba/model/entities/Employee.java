package org.example.springprueba.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "empno", nullable = false)
    private Integer id;

    @Column(name = "ename", length = 10)
    private String ename;

    @Column(name = "job", length = 9)
    private String job;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deptno")
    private Dept dept;

    //obtener
    public Integer getId() {
        return id;
    }

    //establecer/colocar
    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Dept getDeptno() {
        return dept;
    }

    public void setDeptno(Dept deptno) {
        this.dept = deptno;
    }

}