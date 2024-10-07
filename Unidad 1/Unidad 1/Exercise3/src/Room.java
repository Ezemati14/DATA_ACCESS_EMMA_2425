import java.util.ArrayList;

public class Room{
    private String nameRoom;
    private ArrayList<Work> exhibition = new ArrayList<Work>();

    public Room(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public ArrayList<Work> getExhibition() {
        return exhibition;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public void addExhibition(Work work){
        exhibition.add(work);
    }

    public void removeExhibition(Work work){
        exhibition.remove(work);
    }
}