import java.time.LocalDate;

public class Customer extends Person {

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
