public class Contact {
    String name;
    String cell;
    String email;

    public Contact(String name, String cell, String email) {
        this.name = name;
        this.cell = cell;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", cell='" + cell + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
