import java.time.LocalDate;
import java.time.Period;

public class Person {
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
