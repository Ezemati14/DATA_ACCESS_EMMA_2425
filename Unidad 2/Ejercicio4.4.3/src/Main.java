import java.io.FileInputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try{
            FileInputStream fin = new FileInputStream("C:\\Users\\Ezemati14\\Desktop\\J\\archivo.bmp");
            byte[] data = new byte[54];

            fin.read(data);

            int size_bytes = 256*256*256*data[2] + 256*256*data[3] + 256*data[4] + data[5];
            System.out.println(".bmp size " + size_bytes + " bytes");

            int width_pixels = 256*256*256*data[18] + 256*256*data[19] + 256*data[20] + data[21];
            System.out.println(".bmp width " + width_pixels + " pixels");

            int height_pixels = 256*256*256*data[22] + 256*256*data[23] + 256*data[24] + data[25];
            System.out.println(".bmp height " + height_pixels + " pixels");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}