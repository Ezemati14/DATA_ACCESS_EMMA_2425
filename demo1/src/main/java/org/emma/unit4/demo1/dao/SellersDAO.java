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

    //Esta funcion cumple el rol de buscar por la base de datos un usuario
    //Se le pasa por paramtro el usaurio y contraseña
    public Seller getSellerFromUserAndPass(String user, String password){
        //Lo iniciamos en null ya que no se encuentra el usuario
        Seller userRes = null;

        try {
            //abrimos la conexion
            connectionOpen();
            //Definimos la consulta a la base de datos, busca el cif y la password filtrada
            String query = "SELECT s FROM Seller s WHERE s.cif = :cif AND s.password = :password";

            //Lo que sucede aca es que el cif y password, se asignan a las variables que vienen por
            //parametro en la funcion user y password
            //Busca un registro en la tabla seller donde esos setParameter coincidan
            userRes = session.createQuery(query, Seller.class)
                    //Si los coinciden, userRes pasa a valer ese Seller, y tendria
                    //todos sus campos con los datos de ese Seller
                    .setParameter("cif", user)
                    .setParameter("password", password)
                    //Esto devuelve un solo resultado, o null si no hay concidencia
                    .uniqueResult();
            //ahora el userRes tiene datos, si el usuario es diferente de null
            if (userRes != null) {
                //
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
        //Se envia al loginController el objeto Seller que tiene los datos del usuario
        return userRes;
    }

    //Por paramatro se le pasa el seller completo
    public static Seller sellerUpdate(Seller seller){
        try{
            //abrimos la conexion con la base de datos,
            //esto es fundamental para interactuar con ella
            connectionOpen();
            //inicia la transaccion, esto lo hace para que se completen
            //de manera atomica a todo o nada
            session.beginTransaction();
            session.update(seller);//actualiza el objeto seller en la base de datos
            session.getTransaction().commit();//confirma la transaccion
            return seller;//retorna el seller
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
