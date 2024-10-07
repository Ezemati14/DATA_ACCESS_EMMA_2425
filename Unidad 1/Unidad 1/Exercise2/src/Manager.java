import java.time.LocalDate;
import java.util.ArrayList;

public class Manager extends Employee {
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
