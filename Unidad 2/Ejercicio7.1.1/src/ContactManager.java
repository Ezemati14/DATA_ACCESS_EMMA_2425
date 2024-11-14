import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ContactManager {
    private static final String FILE_NAME = "prueba.txt";
    private List<Contact> contacts;

    public ContactManager() {
        contacts = loadContacts();
    }

    private List<Contact> loadContacts() {
        List<Contact> contactList = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                contactList = (List<Contact>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading contacts: " + e.getMessage());
            }
        }
        return contactList;
    }

    private void saveContacts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }

    public void addContact(String name, String surname, String email, String phoneNumber, String description) {
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Invalid phone number format. Please use a 10-digit number.");
            return;
        }

        contacts.add(new Contact(name, surname, email, phoneNumber, description));
        System.out.println("Contact added successfully.");
    }

    public void showContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
                System.out.println("------------------------");
            }
        }
    }

    public void searchContact(String query) {
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getFullName().equalsIgnoreCase(query) || contact.getPhoneNumber().equals(query)) {
                System.out.println(contact);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("\nContact Manager");
            System.out.println("1. Add Contact");
            System.out.println("2. Show Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter surname: ");
                    String surname = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    addContact(name, surname, email, phoneNumber, description);
                    break;
                case 2:
                    showContacts();
                    break;
                case 3:
                    System.out.print("Enter full name or phone number to search: ");
                    String query = scanner.nextLine();
                    searchContact(query);
                    break;
                case 4:
                    saveContacts();
                    System.out.println("Contacts saved. Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (option != 4);
        scanner.close();
    }


}

