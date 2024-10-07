import java.util.ArrayList;

public class Author{

    private String name;
    private String nationality;
    private final ArrayList<Work> worksList= new ArrayList<Work>();

    public Author(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }
    public void setName(String value) {
        name = value;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String value) {
        nationality = value;
    }
    public ArrayList<Work> getWorksList(){
        return worksList;
    }
    public void setWorkAuthor(Work value) {
        worksList.add(value);
    }

    public void printWork() {
        worksList.forEach(work -> System.out.println(work.getTitle()));
    }

}