package org.emma.unit4.demo1.dao;

import org.emma.unit4.demo1.model.Seller;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SellersDAO {

    static SessionFactory sessionFactory;
    static Session session = null;

    public static void connectionOpen(){
        try {
            if (sessionFactory == null) {
                sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            }
            if (session == null || !session.isOpen()) {
                session = sessionFactory.openSession();
            }

            if(session != null){
                System.out.println("Session successfully opened!");
            }else {
                System.out.println("ERROR!!!!");
            }
        }catch (Exception e){
            closeConnection();
            System.err.println("Error al abrir la conexión: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void closeConnection() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                System.out.println("Session successfully closed.");
            }
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                sessionFactory.close();
                System.out.println("SessionFactory successfully closed.");
            }
        } catch (Exception e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Seller getSellerFromUserAndPass(String user, String password){
        Seller userRes = null;

        try {
            connectionOpen();
            System.out.println("Consultando con CIF: " + user + " y contraseña: " + password);
            String query = "SELECT s FROM Seller s WHERE s.cif = :cif AND s.password = :password";
            System.out.println("Ejecutando consulta con CIF: " + user + " y contraseña: " + password);

            userRes = session.createQuery(query, Seller.class)
                    .setParameter("cif", user)
                    .setParameter("password", password)
                    .uniqueResult();

            if (userRes != null) {
                System.out.println("Usuario encontrado: " + userRes.getName());
            } else {
                System.out.println("No se encontró un usuario con el CIF: " + user);
            }
        }catch(Exception e){
            System.err.println("Error al consultar el usuario: " + e.getMessage());
            e.printStackTrace();
        } finally{
            closeConnection();
        }

        return userRes;
    }

    public static Seller sellerUpdate(Seller seller){
        try{
            connectionOpen();
            session.beginTransaction();
            session.update(seller);
            session.getTransaction().commit();
            return seller;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            return null;
        }finally {
            closeConnection();
        }
    }

}
