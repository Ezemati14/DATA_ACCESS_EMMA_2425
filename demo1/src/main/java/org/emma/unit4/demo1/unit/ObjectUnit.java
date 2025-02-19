package org.emma.unit4.demo1.unit;

import org.emma.unit4.demo1.model.Seller;

import java.io.*;

public class ObjectUnit {
    static String sellersPath = "sellers.txt";

    public static Seller readObj(){
        Seller userRemembered = null;
        File sellersObj = null;

        try {
            sellersObj = new File(sellersPath);
            if(!sellersObj.exists()){
                sellersObj.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Object sellers could not be created");
        }

        if (sellersObj.exists() && sellersObj.length() != 0) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(sellersPath))) {
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
            File sellersObj = new File(sellersPath);
            if(!sellersObj.exists()){
                sellersObj.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("DO NOT CREATE SELLER");
            return false;
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(sellersPath))) {
            out.writeObject(seller);
            return true;
        } catch (IOException e) {
            System.err.println("Error while writing on file: " + e.getMessage());
        }
        return false;
    }

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
