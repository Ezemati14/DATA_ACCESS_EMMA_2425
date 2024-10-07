import java.time.LocalDate;

public class Employee extends Person {
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
