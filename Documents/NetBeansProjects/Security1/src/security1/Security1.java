package security1;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Genevieve Suwara
 */
public class Security1 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {                 
        String name;
        String number;
        String date;
        String cvc;
        
        LinkedList results;
        results = getInfo();
        
        int count = results.size();
        
        System.out.println(count + " credit card record(s) found.\n");
        for(int i = 1; i <= results.size(); i++)
        {            
            String result = (String)results.get(i - 1);
            String[] fields = result.split("\\^");
            
            number = fields[0].substring(2, 6) + " " 
                        + fields[0].substring(6, 10) + " " + fields[0].substring(10, 14) + " " + fields[0].substring(14, 18) + " ";
            String[] fullName = fields[1].split("\\/");
            name = fullName[0] + " " + fullName[1];
            date = fields[2].substring(2,4) + "/20" + fields[2].substring(0,2);
            cvc = fields[2].substring(4,7);
            
            System.out.println("<Information of record " + i + ">");
            System.out.println("Cardholder's Name: " + name);
            System.out.println("Card Number: " + number);
            System.out.println("Expiration Date: " + date);
            System.out.println("CVC Number: " + cvc + "\n");
        }
    }
    
    public static LinkedList getInfo()throws FileNotFoundException
    {
        File file = new File("C:\\Users\\smart_000\\Documents\\memorydump(1).dmp");
        LinkedList matches;
        try (Scanner scanner = new Scanner(file)) {
            String charfile = "";
            String input;
            while(scanner.hasNextLine())
            {
                input = scanner.nextLine();
                charfile += input;
            }   
            Pattern pattern;
            pattern = Pattern.compile("\\%(B)\\d*\\^\\p{Alpha}*\\/\\p{Alpha}*\\^\\d*\\w*\\?");
            Matcher matcher;
            matcher = pattern.matcher(charfile);
            int found = 0;
            matches = new LinkedList();
            while (matcher.find()){
                matches.add(matcher.group());
                found += 1;
            }
        }
        return matches;
    }
}
