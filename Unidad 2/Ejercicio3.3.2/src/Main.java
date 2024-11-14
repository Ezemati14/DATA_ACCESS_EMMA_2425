import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Dia de nacimiento");
        int day = sc.nextInt();
        System.out.print("Mes de nacimiento");
        int month = sc.nextInt();
        System.out.print("Año de nacimiento");
        int year = sc.nextInt();

        if(day < 0 || day > 31) {
            System.out.print("The day introduced is incorrect");
        } else if(month < 0 || month > 12) {
                System.out.print("The month introduced is incorrect");
        }

        System.out.print(" You have a Valid date: " + day + "/" + month + "/" + year);
    }
}