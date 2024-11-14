import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream fIn = null;
        FileOutputStream fOn = null;
        try{
            fIn = new FileInputStream("C:\\Users\\Ezemati14\\Desktop\\J\\Prueba.txt");
            fOn = new FileOutputStream("C:\\Users\\Ezemati14\\Desktop\\J\\PruebaOut.txt");

            /**Definimos el tamaño que nos pido 128**/
            byte[] datos = new byte[128];
            int byteLeidos;
            //int byteLeidos = fIn.read( datos );

            /**Este metodo nos devuelve -1 cuando llega al final**/
            //while ( byteLeidos != -1 )
            while( (byteLeidos = fIn.read(datos)) != -1) {
                fOn.write(datos, 0 ,byteLeidos);
                //fOn.write(datos);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fIn.close();
            fOn.close();
        }
    }
}