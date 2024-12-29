module org.emma.unit4.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    exports org.emma.unit4.demo1.Controller to javafx.fxml;
    exports org.emma.unit4.demo1;
    opens org.emma.unit4.demo1.Controller to javafx.fxml;
    opens org.emma.unit4.demo1.model to org.hibernate.orm.core;
}