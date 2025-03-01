package org.emma.unit4.demo1.dao;

import org.emma.unit4.demo1.model.SellerProduct;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Seller_ProductsDAO {

    static SessionFactory sessionFactory = null;
    static Session session = null;

    //function to open connection
    public static void connectionOpen(){
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                    .buildSessionFactory();

            session = sessionFactory.openSession();

            if (session != null) {
                System.out.println("Session successfully opened!");
            } else {
                System.out.println("Error opening session!");
            }
        } catch (Exception e) {
            closeConnection();
            System.err.println("Error initializing Hibernate: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void closeConnection(){
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //Por parametro se pasa el id del seller, y id del producto
    public static SellerProduct getFromSellerAndProduct(int sellerId, int productId){
        SellerProduct sellerProducts = new SellerProduct(); //Aca almacenamos el resultado
        try {
            connectionOpen();//Abrimos la conexion
            session.beginTransaction();
            //Esta consulta busca los sellerProduct que coincidan con productId
            //Los parametros q se pasaron a esta funcion, se ingresan aca
            String hql = "SELECT sp FROM SellerProduct sp WHERE sp.product.id = :productId AND sp.seller.sellerId = :sellerId";
            sellerProducts = (SellerProduct) session.createQuery(hql)
                    //El valor del productId se asigna a :productId
                    .setParameter("productId", productId)
                    //el valor de sellerId, se asigna a :sellerId
                    .setParameter("sellerId", sellerId)
                    .uniqueResult();//Si no se encuentra ningun registra, returna null
            //Si tod0 esta correcto los cambios se guardan en la base de datos
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return sellerProducts;
    }

    public static SellerProduct updateSeller(SellerProduct seller_products) {
        try {
            connectionOpen();
            session.beginTransaction();
            //Actualizamos el objeto en la base de datos
            session.update(seller_products);
            session.getTransaction().commit();
            return seller_products;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return null;
        } finally {
            closeConnection();
        }
    }

}
