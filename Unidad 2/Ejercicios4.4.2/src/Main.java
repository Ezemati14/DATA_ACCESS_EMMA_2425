import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try{
            /** Ezequiel Matias Maggio
             * Archivos que fui probando
             * Primero fue un .PNG y mostro mensaje de tipo PNG
             * Segundo fue un .JPEG y mostro mensaje de tipo JPEG
             * Tercero voy a probar con un txt, y no lo tendria que reconocer
             **/
            FileInputStream fI = new FileInputStream("C:\\Users\\Ezemati14\\Desktop\\J\\prueba.jpeg");
            int i = fI.read();
            String header = Integer.toHexString(i);

            /**Uso equals para poder comprobar la cadena header**/
            if(header.equals("42")){
                System.out.println("Type .BMP");
            }if(header.equals("89")){
                System.out.println("Type .PNG");
            }if(header.equals("ff")) {
                System.out.println("Type .JPEG");
            }else {
                System.out.println("the file will not be recognized");
            }
            fI.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}