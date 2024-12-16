package org.example.springprueba.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptno")
    @JsonIgnore
    private Dept deptno;

    public Integer getId() {
        return id;
    }

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

    public Integer getDeptno() {
        return deptno.getId();
    }

    public void setDeptno(Dept deptno) {
        this.deptno = deptno;
    }

}