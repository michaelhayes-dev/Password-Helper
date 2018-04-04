package password.helper;

import java.io.*;
import java.util.*;
import java.security.*;
import java.util.concurrent.TimeUnit;

public class PasswordHelper {
    
    static protected ArrayList usernames;
    static protected ArrayList hashes;
    static protected ArrayList salts;
    static protected long startTime;

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
                    solvePasswords();
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
    
    private static void solvePasswords() throws Exception{
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\nPASSWORD SOLVER");
        System.out.print("Specify file with password hashes: ");
        String passwordFile = scan.next();
        System.out.print("Specify a space-delimited dictionary file: ");
        String dictionaryFile = scan.next();
        System.out.print("Specify a name for the output file: ");
        String outputFile = scan.next();
        
        //set up password file
        Scanner passwordFileScanner;
        try{
            passwordFileScanner = new Scanner(new File(passwordFile));
        }
        catch(Exception e){
            System.out.println("ERROR: Password file could not be found.");
            return;
        }
        //set up dictionary file
        Scanner dictionaryFileScanner;
        try{
            dictionaryFileScanner = new Scanner(new File(dictionaryFile));
        }
        catch(Exception e){
            System.out.println("ERROR: Dictionary file could not be found.");
            return;
        }
        
        //set up output file
        FileOutputStream fileOut = new FileOutputStream(outputFile);
        
        //Sort the inputs and hashes
        usernames = new ArrayList();
        salts     = new ArrayList();
        hashes    = new ArrayList();
        while(passwordFileScanner.hasNext()){
            String[] temp = passwordFileScanner.next().split(":");
            usernames.add(temp[0]);
            salts.add(temp[1]);
            hashes.add(temp[2]);
        }
        
        //get the word dictionary
        ArrayList dictionary = new ArrayList();
        while(dictionaryFileScanner.hasNext()){
            dictionary.add(dictionaryFileScanner.next());
        }
        
        //Set the start time of calculations
        System.out.println("SOLVED PASSWORDS:");
        startTime = System.currentTimeMillis();
/*
        //PERFORM THE CALCULATIONS
        //pass 1 (Plain Words)
        System.out.println("Pass1");
        for(int j=0; j<dictionary.size(); j++){
            String mangledPassword = dictionary.get(j) + "";
            testMangledPassword(mangledPassword, fileOut);
        }
        System.out.println("Pass2");
        //pass 2 (numbers on the end)
        for(int j=0; j<dictionary.size(); j++){
            for(int k=0; k<=99; k++){  
                String mangledPassword = dictionary.get(j) + "" + k;
                testMangledPassword(mangledPassword, fileOut);
            }
        }
        System.out.println("Pass3");
        //pass 3 (capitalization)
        for(int j=0; j<dictionary.size(); j++){
            for(int k=0; k<((dictionary.get(j)+"").length()); k++){  
                char[] capWord = (dictionary.get(j) + "").toCharArray();
                capWord[k] = Character.toUpperCase(capWord[k]);
                String mangledPassword = new String(capWord);
                testMangledPassword(mangledPassword, fileOut);
            }
        }
        System.out.println("Pass4");
        //pass 4 (capitalization with numbers at end)
        for(int j=0; j<dictionary.size(); j++){
            for(int k=0; k<((dictionary.get(j)+"").length()); k++){ 
                for(int l=0; l<=99; l++){
                    char[] capWord = (dictionary.get(j) + "").toCharArray();
                    capWord[k] = Character.toUpperCase(capWord[k]);
                    String mangledPassword = new String(capWord) + l;
                    testMangledPassword(mangledPassword, fileOut);
                }
            }
        }
        
        char[] realLetters = "aeoilt".toCharArray();
        char[] leetLetters = "@30!17".toCharArray();
        System.out.println("Pass5");
        //pass 5 (leet speak)
        for(int j=0; j<dictionary.size(); j++){
            for(int k=0; k<realLetters.length; k++){
                char[] leetWord = (dictionary.get(j) + "").toCharArray();
                for(int l=0; l<leetWord.length; l++){
                   if(leetWord[l] == realLetters[k]){ leetWord[l] = leetLetters[k]; }             
                }
                String mangledPassword = new String(leetWord) + "";
                testMangledPassword(mangledPassword, fileOut);
            }
        }
*/
        String[] tests = "00123456789!abcdefghijklmnopqrstuvwxyz".split("");
        tests[0] = "";
        char[] realLetters = "aeoilt".toCharArray();
        char[] leetLetters = "@30!17".toCharArray();
        
        System.out.println("Pass6");
        //pass 6 (first capitalization with symbols at the end)
        for(int j=0; j<dictionary.size(); j++){
            for(int k=0; k<tests.length; k++){ 
                for(int l=0; l<tests.length; l++){                  
                    char[] capWord = (dictionary.get(j) + "").toCharArray();
                    capWord[0] = Character.toUpperCase(capWord[0]);
                    String mangledPassword = new String(capWord) + "" + tests[k] + tests[l];
                    testMangledPassword(mangledPassword, fileOut);
                    mangledPassword = dictionary.get(j) + "" + tests[k] + tests[l];
                    testMangledPassword(mangledPassword, fileOut);
                }
            }
        }
        
        System.out.println("Pass7");
        //pass 7 (leet speak and symbols)
        for(int j=0; j<dictionary.size(); j++){
            for(int k=0; k<tests.length; k++){ 
                for(int l=0; l<tests.length; l++){
                    for(int m=0; m<realLetters.length; m++){
                        char[] leetWord = (dictionary.get(j) + "").toCharArray();
                        for(int n=0; n<leetWord.length; n++){
                           if(leetWord[n] == realLetters[m]){ leetWord[n] = leetLetters[m]; }             
                        }
                        String mangledPassword = new String(leetWord) + "" + tests[k] + tests[l];
                        testMangledPassword(mangledPassword, fileOut);
                    }
                    char[] capWord = (dictionary.get(j) + "").toCharArray();
                    String mangledPassword = new String(capWord) + "" + tests[k] + tests[l];
                    testMangledPassword(mangledPassword, fileOut);
                }
            }
        }
        System.out.println("Pass7");
        //pass 7 (additive leet speak and symbols)
        for(int j=0; j<dictionary.size(); j++){
            for(int k=0; k<tests.length; k++){ 
                for(int l=0; l<tests.length; l++){
                    char[] leetWord = (dictionary.get(j) + "").toCharArray();
                    for(int m=0; m<realLetters.length; m++){
                        for(int n=0; n<leetWord.length; n++){
                           if(leetWord[n] == realLetters[m]){ leetWord[n] = leetLetters[m]; }             
                        }
                        String mangledPassword = new String(leetWord) + "" + tests[k] + tests[l];
                        testMangledPassword(mangledPassword, fileOut);
                    }
                    char[] capWord = (dictionary.get(j) + "").toCharArray();
                    String mangledPassword = new String(capWord) + "" + tests[k] + tests[l];
                    testMangledPassword(mangledPassword, fileOut);
                }
            }
        }

        System.out.println();    
    }
    
    private static void testMangledPassword(String mangledPassword, FileOutputStream fileOut) throws Exception{
        //prepare the hash calculator
        MessageDigest hashCalculator = MessageDigest.getInstance("MD5");     
        for(int i=0; i<hashes.size(); i++){
            String testPassword = mangledPassword + "" + salts.get(i);
            String dictionaryHash = bytesToHex(hashCalculator.digest(testPassword.getBytes()));
            if(hashes.get(i) != null && hashes.get(i).equals(dictionaryHash)){
                long  millis = (System.currentTimeMillis() - startTime);
                String timeStamp = String.format("%02d:%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)), millis - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis)));
                String logResult = usernames.get(i) + "  \t" + mangledPassword + "   \t" + timeStamp;
                System.out.println(logResult);
                logResult = logResult + '\n';
                fileOut.write(logResult.getBytes());
                hashes.set(i, null);
            }
        }
    }
    
    private static ArrayList removeItems(ArrayList indices, ArrayList items){
        for(int i=0; i<indices.size(); i++){
            int index = (int) indices.get(i);
            items.remove(index);
        }
        return items;
    }
    
    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
