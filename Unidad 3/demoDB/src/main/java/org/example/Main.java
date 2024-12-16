package org.example;

import com.sun.tools.jconsole.JConsoleContext;

import java.sql.*;

public class Main {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        String name = "Machine Learning"; // Nombre del curso
        String url = "jdbc:postgresql://localhost:5432/Empresa";
        String user = "postgres";
        String password = "boca";
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);
            try{
                Statement statement = conn.createStatement();
                Class.forName("org.postgresql.Driver");

                /**String SQLsentence = "INSERT INTO courses (code, name) VALUES (?, ?)";
                 PreparedStatement pstmt = conn.prepareStatement(SQLsentence);
                 pstmt.setInt(1, 5);
                 pstmt.setString(2, name);
                 pstmt.executeUpdate();**/


                /**String SQLsentence = "INSERT INTO courses (code, name) VALUES (4, 'JS')";
                 // Ejecutar la sentencia con executeUpdate
                 int rowsAffected = statement.executeUpdate(SQLsentence);
                 // Mostrar un mensaje de confirmación
                 if (rowsAffected > 0) {
                 System.out.println("El curso ha sido agregado correctamente.");
                 } else {
                 System.out.println("No se agregó ningún curso.");
                 }**/
                //String SQLsentenc = "SELECT * FROM get_employees_by_job('CLERK')";
                //String SQLsentenc = "SELECT * FROM get_employees_by_DEPT(10)";
                String SQLsentenc = "SELECT * FROM get_name_employee('a')";
                ResultSet rs = statement.executeQuery(SQLsentenc);
                System.out.println("empno" + "\t" + "Name"+ "\t" + "Job" + "\t" + "deptno");
                System.out.println("-----------------------------------------");
                while (rs.next()) {
                    System.out.println(rs.getString(1) + "\t " +
                            rs.getString(2) + rs.getString(3) + rs.getString(4));
                }
                statement.close();
                conn.commit();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                conn.rollback();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}