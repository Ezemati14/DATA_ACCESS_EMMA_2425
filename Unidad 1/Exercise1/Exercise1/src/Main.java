import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Student stud1 = new Student("Ezequiel matias Maggio" , 3);
        Student stud2 = new Student("Jose Carlos" , 8);
        Student stud3 = new Student("Adrian lopez" , 7);

        System.out.println("nota de " + stud1.getName() + " " + stud1.getGrade());
        System.out.println("nota de " + stud2.getName() + " " + stud2.getGrade());
        System.out.println("nota de " + stud3.getName() + " " + stud3.getGrade());

        Students Classroom = new Students();
        Classroom.setStudentsList(stud1);
        Classroom.setStudentsList(stud2);
        Classroom.setStudentsList(stud3);
    }
}
class Student {
    private String name;
    private int grade;

    public Student(String name, int grade){
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }
    public void setName(String value) {
        name = value;
    }

    public int getGrade() {
        return grade;
    }
    public void setGrade(int value) {
        grade = value;
    }
    //Methods
    //I saved some lines, and put it together in a method
    public boolean Passed(int value) {
        if(value >= 5){
            return true;
        } else {
            return false;
        }
    }

} //close student

class Students {
    private final ArrayList<Student> studentsList = new ArrayList<Student>();

    public int getStudentsList() {
        return studentsList.size();
    }
    public void setStudentsList(Student student) {
        studentsList.add(student);
    }

    public String positionStudent(int position) {
        if(position > 0 && position <= studentsList.size()){
            return studentsList.get(position).getName();
        }else return null;
    }

    public float averageGrade() {
        float average = 0;
        if( studentsList.size() == 0 ){
            return 0;
        }else {
            for (int i = 0; i < studentsList.size(); i++) {
                average += studentsList.get(i).getGrade();
            }
            return (average / studentsList.size());
        }
    }
} // close students