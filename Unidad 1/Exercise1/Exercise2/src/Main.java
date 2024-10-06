import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

    }
}

    class Person {
        protected String name;
        protected LocalDate birthDate;
        protected int age;

        public Person(String name, LocalDate birthDate) {
            setName(name);
            setBirthDate(birthDate);
        }

        public String getName(){
            return name;
        }

        public void setName(String name){
            this.name = name;
        }

        public LocalDate getBirthDate(){
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate){
            if(birthDate.isAfter(LocalDate.now())){
                throw new IllegalArgumentException("Invalid birth date");
            }
            this.birthDate = birthDate;

            calculateAge();
        }

        private void calculateAge(){
            this.age = Period.between(birthDate, LocalDate.now()).getYears();
        }

        public int getAge() {
            return age;
        }
        public String showData() {
            return "Name: " + this.name + " - Age: " + getAge();
        }
}

    class Employee extends Person {
            private double grossSalary;

            public double getGrossSalary(){
                return grossSalary;
            }

            public void setGrossSalary(double grossSalary){
                if(grossSalary < 0) {
                    throw new IllegalArgumentException("Invalid salary");
                }
                this.grossSalary = grossSalary;
            }

            @Override
            public String showData() {
                return "Name: " + this.name + " - Age: " + getAge() + " - salary: " + this.grossSalary + " €";
            }

        public Employee(String name, LocalDate birthDate, double grossSalary){
            super(name, birthDate);
            setGrossSalary(grossSalary);
        }
    }

    class Manager extends Employee{
        private String category;
        private final ArrayList<Employee> supervises = new ArrayList<>();

        public Manager(String name, LocalDate birthDate, double grossSalary, String category) {
            super(name, birthDate, grossSalary);
            this.category = category;
        }

        public String getCategory(){
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public ArrayList<Employee> getSupervises(){
            return this.supervises;
        }

        public void setSupervises(Employee value){
            System.out.println("Now, " + value.name + " are in " + category + " " + this.name + "'s staff.");
        }

        public void addSupervises(Employee employee){
            supervises.add(employee);
        }
    }

    class Customer extends Person{

        private String phoneNumber;

        public Customer(String name, LocalDate birthDate, String phoneNumber){
            super(name, birthDate);
            this.phoneNumber = phoneNumber;
        }

        public void setPhoneNumber(String phone) {
            phoneNumber = phone;
        }

        public String getPhoneNumber(){
            return phoneNumber;
        }

        @Override
        public String showData(){ // Overriding method from the parent class
            return "Name: " + this.name + " - Age: " + getAge() + " - phone number: " + phoneNumber;
        }
    }

    class Company {
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