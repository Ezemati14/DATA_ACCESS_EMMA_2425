import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {



    }
}

class Author{

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

class Work{
        private String title;

        public Work(String title){
            this.title = title;
        }

        public String getTitle(){
            return title;
        }

        public void setTitle(String valueTitle){
            title = valueTitle;
        }
}

class Painting extends Work{

    enum paintsType{
        Oil,
        Pastel,
        Watercolor
    }

    private final paintsType paintType;
    private String format;

    public Painting(String title, paintsType paintType, String format){
        super(title);
        this.paintType = paintType;
        this.format = format;
    }

    public paintsType getPaint() {
        return paintType;
    }
    public String getFormat(){
        return format;
    }
    public void setFormat(String valueFormat){
        format = valueFormat;
    }

    public void printDaPainting(){
        System.out.println("Painting Title: " + getTitle() + "; format: " + getFormat() + "; painting type: " + getPaint());
    }
}

class Sculpture extends Work{
    enum typesMaterials{
        Bronze,
        Iron,
        Marble
    }
    enum typesStyles{
        Neoclassical,
        Grecoroman,
        Cubist
    }
    private final typesMaterials typeMaterial;
    private final typesStyles typeStyle;


    public Sculpture(String title, typesMaterials tMaterial, typesStyles tStyle) {
        super(title);
        this.typeMaterial = tMaterial;
        this.typeStyle = tStyle;
    }

    public typesMaterials getMaterial(){
        return typeMaterial;
    }
    public typesStyles getStyle(){
        return typeStyle;
    }

    public void printSclpture(){
        System.out.println("Sculpture Title: " + getTitle() + "; material: " + getMaterial() + "; style: " + getStyle());
    }
}

class Museum{
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

class Room{
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