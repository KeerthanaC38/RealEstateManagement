import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class Main {
    public static void main(String[] args)throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDao();

        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. Register as Agent");
            System.out.println("2. Register as Renter");
            System.out.println("3. Login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1/2/3/4): ");

            int choice = 0;
            try{
                choice = scanner.nextInt();
                scanner.nextLine(); // To clear the newline character
            }
            catch(Exception e){
                System.out.println("Something went wrong! Re-enter the choice.");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            

            if (choice == 4) {
                System.out.println("Exiting...");
                break;
            }

            System.out.print("Enter your email address: ");
            String emailAddress = scanner.nextLine();

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            if (choice == 1) {
                // Register as Agent
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                System.out.print("Re-enter your password: ");
                String password2 = scanner.nextLine();

                if(!password.equals(password2)){
                    System.out.println("Passwords does not match: Re-register?");
                    continue;
                }

                System.out.print("Enter contact info: ");
                String contactInfo = scanner.nextLine();

                System.out.print("Enter job tile: ");
                String job_title = scanner.nextLine();

                System.out.print("Enter real estate agency: ");
                String agency = scanner.nextLine();

                System.out.print("Enter address: ");
                String address = scanner.nextLine();

                User user = new User(emailAddress, name, password);
                boolean agentRegistered = userDao.registerUser(user, true, contactInfo, job_title, agency, "", "", null,address);
                System.out.println("Agent registration: " + (agentRegistered ? "Success" : "Failure"));
            } else if (choice == 2) {
                // Register as Renter
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                System.out.print("Re-enter your password: ");
                String password2 = scanner.nextLine();

                System.out.print("Enter address: ");
                String address = scanner.nextLine();

                System.out.print("Enter budget: ");
                String budget = scanner.nextLine();

                System.out.print("Enter preferred location: ");
                String preferredLocation = scanner.nextLine();

                System.out.print("Please enter the move in date, format: yyyy/mm/dd ");
                String input;
                Date inpuDate = null;
                java.sql.Date move_in_date;
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                //move_in_date = scanner.nextLine();
                while(inpuDate == null)
                {
                    try
                    {
                        input = scanner.nextLine();
                        inpuDate = (Date) df.parse(input);
                    }
                    catch(ParseException e)
                    {
                        System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
                    }
                }
                move_in_date = new java.sql.Date(inpuDate.getTime());

                if(!password.equals(password2)){
                    System.out.println("Passwords does not match: Re-register?");
                    continue;
                }

                User user = new User(emailAddress, name, password);
                boolean renterRegistered = userDao.registerUser(user, false, "", "", "", budget, preferredLocation, move_in_date, address);
                System.out.println("Renter registration: " + (renterRegistered ? "Success" : "Failure"));
            } else if (choice == 3) {
                // Login
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                String loginResult = userDao.loginUser(emailAddress, password);
                if (loginResult.equals("Agent")) {
                    System.out.println("Login successful! Welcome Agent.");
                    userDao.agentOperations(scanner, emailAddress, password);

                } else if (loginResult.equals("Renter")) {
                    System.out.println("Login successful! Welcome Renter.");
                    userDao.renterOperations(scanner, emailAddress, password);
                    //continue;
                } else {
                    System.out.println("Invalid email address or password. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        //scanner.close();
    }
}