import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int day, month, year;

        System.out.print("Enter a day: ");
        day = sc.nextInt();
        System.out.print("Enter a month: ");
        month = sc.nextInt();
        System.out.print("Enter a year: ");
        year = sc.nextInt();

        Date date = new Date(day, month, year);

        System.out.println("Your introduced date is: " + day + "/" + date.getMonth()
                + "/" + date.getYear());
    }
}