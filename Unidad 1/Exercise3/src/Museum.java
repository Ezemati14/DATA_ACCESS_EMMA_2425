import java.util.ArrayList;

public class Museum{
    private String name;
    private String address;
    private String city;
    private String country;
    private final ArrayList<Room> roomList = new ArrayList<Room>();

    public Museum(String name, String address, String city, String country) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }
    public void addRoomsMuseum(Room room){
        roomList.add(room);
    }
    public void removeRoomsMuseum(Room room){
        roomList.remove(room);
    }

}
