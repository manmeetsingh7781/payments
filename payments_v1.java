import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class payments {

    // Initilize the Main Parts
    private static ArrayList<Double> nums = new ArrayList<Double>();
    private static ArrayList<String> dates = new ArrayList<String>();
    private static ArrayList<Double> deposit_amounts = new ArrayList<Double>();
    private static double total = 0d;
    private static double deposits = 0d;

    // The main Thread to call
    public static void main(String[] args) {
        // Printing because it returns the output
        System.out.println(fileReader("C:\\Users\\Honey Singh\\Downloads\\Checking1.csv"));
    }

    // This will read the file url in the local disk
    private static String fileReader(String file_path){
        try {
            // Initilze the file
            File myfile = new File(file_path);

            // Reads the file
            Scanner scanner = new Scanner(myfile);

            // Reads until has next line
            while (scanner.hasNext()) {

                // Saving each line in a string and working with it for each loop
                String each_line = scanner.nextLine();

                // Pattern for date
                String pattern  = "[.\\d]\\S*/[.\\d]\\S*/[.\\d]*";

                //Pattern for spent money
                String amount =   "-[\\d]+[.]+[\\d]\\S";

                //Pattern for deposits
                String deposit_pattern = ",\\d+[.]\\S\\d*";

                // For backfall deposits
                String deposit_pattern_new = "[\\d]+[.]\\S\\d";

                // Compiling the patterns [ Deposit Pattern ]
                Pattern deposit_p = Pattern.compile(deposit_pattern);
                Matcher deposit_match = deposit_p.matcher(each_line);

                // Compiling the patterns [ Dates  Pattern ]
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(each_line);

                // Compiling the patterns [ Spending Pattern ]
                Pattern money_p = Pattern.compile(amount);
                Matcher match = money_p.matcher(each_line);


                // Compiling the patterns [ Spending Pattern ]
                Pattern deposit_new = Pattern.compile(deposit_pattern_new);
                Matcher deposit_new_match = deposit_new.matcher(each_line);


//                 If matches
//                 Adding elements into the array and replacing those
                if(deposit_match.find() || deposit_new_match.find()) {

                    if(deposit_new_match.find()) {

                        try {
                            deposit_amounts.add(Double.parseDouble(deposit_match.group().replace(",", "")));
                        } catch (Exception e) {
                            double each_deposit = Double.parseDouble(deposit_new_match.group().replace("\"", ""));
                            if (each_deposit > 0) {
                                System.out.println(each_deposit);
                            }
                        }
                    }else{
                        try {
                            deposit_amounts.add(Double.parseDouble(deposit_match.group().replace(",", "")));
                        } catch (Exception e) {
                            double each_deposit = Double.parseDouble(deposit_new_match.group().replace("\"", ""));
                            if (each_deposit > 0) {
                                System.out.println(each_deposit);
                            }
                        }

                    }


                }
                if(m.find()){
                    dates.add(m.group());
                }

                if(match.find()){
                    try {
                        nums.add(Double.parseDouble(match.group().replace(",","")));
                    }
                    catch (NumberFormatException e){
                        System.out.println((match.group().replace("\"", "")));
                    }
                }
            }

            // Tracking last transition
            String last_date = (dates.get(dates.size()-1));

            // Adding total of each spending we have made
            for(double neg: nums){

                // Negative because in file it will also be a negative for example: -10.99
                if(neg < 0){
                    total+= Math.abs(neg);
                }
            }

            // Adding deposit value into the total which is > 0 for example: 200.99
            for(double dep: deposit_amounts){
                deposits+=dep;
            }

            // Returning all the data we have collected
            return "You Have Spent: $" + total+ " [ Rounded : $" + Math.round(total) +" ]"+   "\nDeposits made: $" + deposits + "  [ Rounded : $" + Math.round(deposits) +" ]"+"\nFrom: " + last_date + " Till you're last transition " + dates.get(0);

            // Catching errors if somethings might happen
        } catch (FileNotFoundException e) {
            return e.getMessage();
        }
    }

}

///// Developed by Manmeet Singh 2018///
