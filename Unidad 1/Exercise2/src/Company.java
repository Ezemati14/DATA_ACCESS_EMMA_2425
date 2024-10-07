import java.util.ArrayList;

public class Company {
        private String name;
        private final ArrayList<Customer> customers = new ArrayList<>();

        public Company (String nCompany){
            this.name = nCompany;
        }

        public String getNameCompany(){
            return name;
        }

        public void setNameCompany(String nCompany){
            name = nCompany;
        }

        public ArrayList<Customer> getCustomers(){
            return customers;
        }

        // Methods - Class Company
        public void addCustomer(Customer value){
            customers.add(value);
        }

        public void pCustomers(){
            customers.forEach(customer -> System.out.println(customer.name));
        }
    }