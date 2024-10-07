import java.util.ArrayList;

public class Students {
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

}// close students
