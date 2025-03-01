package org.emma.unit4.demo1.dao;

import org.emma.unit4.demo1.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO {

    static SessionFactory sessionFactory;
    static Session session = null;

    public static void connectionOpen(){
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            session = sessionFactory.openSession();

            if(session != null){
                System.out.println("Session successfully opened!");
            }else {
                System.out.println("ERROR!!!!");
            }
        }catch (Exception e){
            closeConnection();
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void closeConnection() {
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

    public static List<Product> getProductsFromSeller(int id){
        List<Product> res = new ArrayList<>();
        try {
            connectionOpen();//Se abre la conexion a la base de datos
            //Busca productos, esta query va a buscar los productos asociados a ese seller
            String query = "SELECT p FROM Product p INNER JOIN SellerProduct s ON s.product = p WHERE s.seller.sellerId = :seller_id";
            // Se le pasa la query, y el tipo de objeto esperado
            //res va a guardar la lista de productos
            res = session.createQuery(query, Product.class)
                    .setParameter("seller_id", id)
                    .getResultList();//Se devuelve una lista con todos los productos
        }catch(Exception e){
            System.out.println(e.getMessage());
        } finally{
            closeConnection();
        }

        return res;
    }

    public static boolean isDiscountActive(int productId, LocalDate offerStartDate, LocalDate offerEndDate) {
        boolean isActive = false;
        try {
            connectionOpen();

            String sql = "SELECT is_discount_active(:productId, :offerStartDate, :offerEndDate)";

            isActive = (Boolean) session.createNativeQuery(sql)
                    .setParameter("productId", productId)
                    .setParameter("offerStartDate", java.sql.Date.valueOf(offerStartDate))
                    .setParameter("offerEndDate", java.sql.Date.valueOf(offerEndDate))
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return isActive;
    }

    public static boolean isPRODiscountActive(int sellerId, LocalDate offerStartDate, LocalDate offerEndDate) {
        //Se inicia false, si hay alguno error devuelve false
        boolean isActive = false;
        try {
            //abrimos la conexion con la base de datos
            connectionOpen();
            //funcion almacenada en la base de datos
            //verifica si el vendedor PRO ya tiene una fecha activa con descuento
            String sql = "SELECT is_pro_discount_active(:sellerId, :offerStartDate, :offerEndDate)";
            //va a devolver un boolean, pasamos la sentencia sql
            isActive = (Boolean) session.createNativeQuery(sql)
                    //asigna los valores sellerId a :sellerId
                    .setParameter("sellerId", sellerId)
                    //convierte el LocalDate en java.sql.Date, y le pasamos por parametro la fecha
                    .setParameter("offerStartDate", java.sql.Date.valueOf(offerStartDate))
                    .setParameter("offerEndDate", java.sql.Date.valueOf(offerEndDate))
                    .getSingleResult();//devuelve un solo resultado, que es boolean
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        //devuelve true si hay uno activo, o false si no
        return isActive;
    }

}
