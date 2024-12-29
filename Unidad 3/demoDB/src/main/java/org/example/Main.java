package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) {
       SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
       Session session = sessionFactory.openSession();
       if(session != null) {
           System.out.println("Este es el session");
       }else {
           System.out.println("Este es el session no existe");
       }

        Query<Employee> myQuery = session.createQuery("from org.example.Employee");
       List<Employee> listEmployees = myQuery.list();
       for(Employee employee : listEmployees) {
           System.out.printf("Number : %d Name: %s%n", employee.getId(), employee.getEname());
       }

    }
}