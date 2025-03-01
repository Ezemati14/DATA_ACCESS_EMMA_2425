package org.emma.unit4.demo1.unit;

import org.emma.unit4.demo1.model.Seller;

import java.io.*;
//Permite guardar leer y borrar informacion recordado en el sistema
public class ObjectUnit {
    //Nombre del archivo donde se va almacenar
    static String sellersPath = "sellers.txt";

    public static Seller readObj(){
        //Almacena el objete seller
        Seller userRemembered = null;
        //Representa el archivo sellers.txt
        File sellersObj = null;

        try {
            //Si el archivo no exite, lo crea
            sellersObj = new File(sellersPath);
            //Verifica si el archivo existe
            if(!sellersObj.exists()){
                sellersObj.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Object sellers could not be created");
        }
        //Leer el objeto desde el archivo
        //solamente si existe el archivo y no esta vacio
        if (sellersObj.exists() && sellersObj.length() != 0) {
            //ObjectInputStream = flujo de entrada de objetos
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(sellersPath))) {
                //readObject = leer objeto
                userRemembered = (Seller) in.readObject();
            } catch (FileNotFoundException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userRemembered;
    }

    public static boolean writeSellersToObj(Seller seller){
        try {
            //Esto crea el archivo si no esxiste
            File sellersObj = new File(sellersPath);
            if(!sellersObj.exists()){
                sellersObj.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("DO NOT CREATE SELLER");
            return false;
        }
        //Esto escribe el objeto seller en el archivo
        //ObjectOutputStream = flujo de salida de objeto
        //FileOutputStream = flujo de salida de archivos
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(sellersPath))) {
            //Escribir objeto
            out.writeObject(seller);
            return true;
        } catch (IOException e) {
            System.err.println("Error while writing on file: " + e.getMessage());
        }
        return false;
    }
    //borra el contenido del archivo
    public static void cleanRememberUser() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(sellersPath);
            fileWriter.write("");
        } catch (IOException e) {
            System.err.println("Error while cleaning users file: " + e.getMessage());
        }finally{
            if(fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.err.println("Error while closing file writer: " + e.getMessage());
                }
            }
        }
    }
}
