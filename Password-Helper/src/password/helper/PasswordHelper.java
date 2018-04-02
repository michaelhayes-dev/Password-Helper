package password.helper;

import java.io.*;
import java.util.*;

public class PasswordHelper {

    public static void main(String[] args) throws Exception {
        
        System.out.println("Welcome to the Password Helper tool!\nCreated by Michael Hayes\n");
        System.out.println("This tool can be used to help solve a password file of MD5-hashed passwords.");
        System.out.println("The user provides a dictionary file, a password file, and the name of an output file.");
        System.out.println("The program outputs the username, solved password, and timestamp to the output file.\n");
        System.out.println("The password file should be formatted as follows:  <username>:<salt>:<MD5 hash>");
        System.out.println("The dictionary file should contain a list of space-delimited words");
        System.out.println("This password helper tool comes with a dictionary parser which can take a file");
        System.out.println("and convert it into a dictionary file.\n");
        
        Scanner scan = new Scanner(System.in);
        
        while(true){
            System.out.print("MENU:\nS: Solve a password file    D: Create a dictionary file from a text file    Q: Quit\n> ");
            String menuChoice = scan.next();
            switch(menuChoice.toUpperCase()){
                case "D":            
                    createDictionary();
                    break;
                
                case "S":
                    
                    break;
                    
                case "Q":
                    return;
                
                default:
                    System.out.println("Incorrect Input:\n");
                    break;
            
            }
        }
    }
    
    private static void createDictionary() throws IOException{
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\nDICTIONARY CREATOR");
        System.out.print("Specify input text file: ");
        String inputFile = scan.next();
        System.out.print("Specify a name for the output file: ");
        String outputFile = scan.next();
        
        //set up input file
        Scanner fileIn;
        try{
            fileIn = new Scanner(new File(inputFile));
        }
        catch(Exception e){
            System.out.println("ERROR: Input file could not be found.");
            return;
        }
        
        //set up output file
        FileOutputStream fileOut = new FileOutputStream(outputFile);
        
        //set up the list of words that we've already found
        ArrayList a = new ArrayList();
        
        while(fileIn.hasNext()){
            String temp = fileIn.next().toLowerCase();
            temp = temp.replaceAll("[^a-zA-Z]", "");
            if(a.contains(temp)){
                continue;
            }
            else if(!(temp.equals("")||temp.equals(" "))){
                a.add(temp);
                //System.out.println(temp);
                temp = temp + ' '; //space-delimited list
                fileOut.write(temp.getBytes());
            }
        }
        System.out.println();
    }
}
