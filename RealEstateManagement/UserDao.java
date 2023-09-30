import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDao {
    static int pgid=0;
    public boolean registerUser(User user, boolean isAgent, String contactInfo, String job_title, String agency, String budget, String preferredLocation, java.sql.Date move_in_date, String address)throws SQLException {
        try{
            if (isAgent) {
                //sql = "INSERT INTO agent (email, name) VALUES (?, ?)";
                return registerAgent(user.getEmailAddress(), user.getName(), user.getPassword(), contactInfo, job_title, agency, address);
            } else {
                //sql = "INSERT INTO perspective_renter (email, name) VALUES (?, ?)";
                return registerRenter(user.getEmailAddress(), user.getName(), user.getPassword(), budget, preferredLocation, move_in_date, address);
            }
        }
        catch(Exception e){
            return false;
        }

    }
    public static boolean registerAgent(String email, String name, String password, String contactInfo, String jobTitle, String realEstateAgency, String address) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String insertAgentSql = "INSERT INTO agent (email, name, contact, job_title, agency, password) VALUES (?, ?, ?, ?, ?, ?)";
            String insertUserSql = "INSERT INTO users (name, email, password, role ) VALUES (?, ?, ?, ?)";
            String insertAgentAddressSql = "INSERT INTO agent_address(email,address) VALUES (?,?)";
            try (PreparedStatement agentStmt = conn.prepareStatement(insertAgentSql);
                PreparedStatement userStmt = conn.prepareStatement(insertUserSql);
                PreparedStatement agentAddressStmt = conn.prepareStatement(insertAgentAddressSql)
                ) {
                conn.setAutoCommit(false);

                agentStmt.setString(1, email);
                agentStmt.setString(2, name);
                agentStmt.setString(3, contactInfo);
                agentStmt.setString(4, jobTitle);
                agentStmt.setString(5, realEstateAgency);
                agentStmt.setString(6, password);
                agentStmt.executeUpdate();

                userStmt.setString(1, name);
                userStmt.setString(2, email);
                userStmt.setString(3, password);
                userStmt.setString(4, "agent");
                userStmt.executeUpdate();
                
                agentAddressStmt.setString(1,email);
                agentAddressStmt.setString(2,address);
                agentAddressStmt.executeUpdate();

                conn.commit();

                System.out.println("Agent registered successfully!");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerRenter(String email, String name, String password, String budget, String preferredLocation, java.sql.Date moveInDate, String address) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String insertRenterSql = "INSERT INTO perspective_renter (email, name, budget, preferred_location, move_in_date, password) VALUES (?, ?, ?, ?, ?, ?)";
            String insertUserSql = "INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, ?)";
            String insertRenterAddressSql = "INSERT INTO perspective_renter_address(email,address) VALUES (?,?)";

            try (PreparedStatement renterStmt = conn.prepareStatement(insertRenterSql);
                PreparedStatement userStmt = conn.prepareStatement(insertUserSql);
                PreparedStatement renterAddresssStmt = conn.prepareStatement(insertRenterAddressSql)) {
                conn.setAutoCommit(false);

                renterStmt.setString(1, email);
                renterStmt.setString(2, name);
                renterStmt.setString(3, budget);
                renterStmt.setString(4, preferredLocation);
                renterStmt.setDate(5, moveInDate);
                renterStmt.setString(6, password);
                renterStmt.executeUpdate();

                userStmt.setString(1, name);
                userStmt.setString(2, email);
                userStmt.setString(3, password);
                userStmt.setString(4, "renter");
                userStmt.executeUpdate();
                
                renterAddresssStmt.setString(1,email);
                renterAddresssStmt.setString(2,address);
                renterAddresssStmt.executeUpdate();

                conn.commit();

                System.out.println("Renter registered successfully!");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public String loginUser(String emailAddress, String password) {
        String result = null;

        try (Connection connection = DatabaseConnector.getConnection()) {
            String agentQuery = "SELECT * FROM agent WHERE email = ? AND password = ?";
            PreparedStatement agentStatement = connection.prepareStatement(agentQuery);
            agentStatement.setString(1, emailAddress);
            agentStatement.setString(2, password);
            ResultSet agentResultSet = agentStatement.executeQuery();

            if (agentResultSet.next()) {
                result = "Agent";
            } else {
                String renterQuery = "SELECT * FROM perspective_renter WHERE email = ? AND password = ?";
                PreparedStatement renterStatement = connection.prepareStatement(renterQuery);
                renterStatement.setString(1, emailAddress);
                renterStatement.setString(2, password);
                ResultSet renterResultSet = renterStatement.executeQuery();

                if (renterResultSet.next()) {
                    result = "Renter";
                } else {
                    result = "Invalid";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void readingCommonAttributes(int propertyId, Scanner scanner, String type, int numberOfRooms,
                                            String emailAddress){
        System.out.print("Enter property description: ");
        String description = scanner.nextLine();

        System.out.print("Enter property square footage: ");
        Double squareFootage = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter property availability:(Yes/No) ");
        String availability = scanner.nextLine();

        System.out.print("Enter property price: ");
        String price = scanner.nextLine();

        System.out.print("Enter property location: ");
        String location = scanner.nextLine();

        System.out.print("Enter property city: ");
        String city = scanner.nextLine();

        System.out.print("Enter property state: ");
        String state = scanner.nextLine();

        System.out.print("Please enter the property's rental start date, format: yyyy/mm/dd ");
        String rentStart;
        java.util.Date rsDate = null;
        java.sql.Date rental_start_date;
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        //move_in_date = scanner.nextLine();
        while(rsDate == null)
        {
            try
            {
                rentStart = scanner.nextLine();
                rsDate =  df.parse(rentStart);
            }
            catch(ParseException e)
            {
                System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
            }
        }
        rental_start_date = new java.sql.Date(rsDate.getTime());

        System.out.print("Please enter the property's rental end date, format: yyyy/mm/dd ");
        String rentEnd;
        java.util.Date reDate = null;
        java.sql.Date rental_end_date;
        boolean isForSale = false;
        //move_in_date = scanner.nextLine();
        while(reDate == null)
        {
            try
            {
                rentEnd = scanner.nextLine();
                if(rentEnd.equals("")){
                    System.out.println("You have not entered any rental end date. Opening this property for sale.");
                    isForSale = true;
                    break;
                }
                reDate =  df.parse(rentEnd);
            }
            catch(ParseException e)
            {
                System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
            }
        }
        if(isForSale){
            rental_end_date = null;
        }
        else{
            rental_end_date = new java.sql.Date(reDate.getTime());
        }
        

        // Scan necessary items and enter into property relation
        PropertySIUD psuid = new PropertySIUD();
        if(type.equals("apartment")){
            System.out.print("Enter apartment building_type: ");
            String building_type = scanner.nextLine();
            Apartment apartment = new Apartment(propertyId, type, description, numberOfRooms, availability, price, 
                                        location, city, state, numberOfRooms, building_type, emailAddress, rental_start_date, rental_end_date);
            boolean insertStatus = psuid.insertApartment(apartment);
            System.out.println("Property Apartment: "+(insertStatus ?  "Inserted":"Failed Insert"));
        }
        else if(type.equals("commercial_building")){
            System.out.print("Enter apartment type_of_business: ");
            String typeOfBusiness = scanner.nextLine();
            CommercialBuilding commercialBuilding = new CommercialBuilding(propertyId, type, description, numberOfRooms, availability, price, location, 
                                                        city, state, typeOfBusiness, emailAddress, rental_start_date, rental_end_date);
            boolean insertStatus = psuid.insertCommercialBuilding(commercialBuilding);
            System.out.println("Property Commercial Building: "+(insertStatus ?  "Inserted":"Failed Insert"));
        }
        else if(type.equals("house")){
            House house = new House(propertyId, type, description, squareFootage, availability, price, location,
                                city, state, numberOfRooms, emailAddress, rental_start_date, rental_end_date);
            boolean insertStatus = psuid.insertHouseIntoDB(house);
            System.out.println("Property House:"+(insertStatus ?  "Inserted":"Failed Insert"));
        }
    }

    public void readingUpdateAttributes(Scanner scanner){
        while(true){
            System.out.println("Enter which property type you want to modify: \n 1. House \n 2. Apartment \n 3. Commercial Building \n 4. Exit");
            System.out.print("Enter your choice (1/2/3/4): ");
            int propertyTypeChoice = scanner.nextInt();
            scanner.nextLine();
            if(propertyTypeChoice == 4){
                System.out.println("Exiting...");
                break;
            }
            else if(propertyTypeChoice == 3){
                System.out.println("Modifying commercial building properties...");
                System.out.println("Enter property id you want to modify: ");
                int propertyId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Please enter the attribute you want to modify: "+"\n1.type, \n2.description, \n3.square_footage, \n4.availability, \n5.price, \n6.location, \n7.city,"+ "\n8.state, \n9.type_of_business, \n10.email, \n11.rental_start_date, \n12.rental_end_date\n");
                int modifyChoice = scanner.nextInt();
                scanner.nextLine();
                HashMap<Integer, String> map = new HashMap<Integer, String>();
                map.put(1, "type");
                map.put(2, "description");
                map.put(3, "square_footage");
                map.put(4, "availability");
                map.put(5, "price");
                map.put(6, "location");
                map.put(7, "city");
                map.put(8, "state");
                map.put(9, "type_of_business");
                map.put(10, "email");
                map.put(11, "rental_start_date");
                map.put(12, "rental_end_date");
                PropertySIUD psuid = new PropertySIUD();
                boolean modifyStatus = psuid.modifyCommercialBuilding(scanner, propertyId, modifyChoice, map);
                System.out.println("Property Commercial Building: "+(modifyStatus ?  "Updated":"Failed Update"));
            }
            else if(propertyTypeChoice == 2){
                System.out.println("Modifying apartment properties...");
                System.out.println("Enter property id you want to modify: ");
                int propertyId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Please enter the attribute you want to modify: "+"\n1.type, \n2.description, \n3.square_footage, \n4.availability, \n5.price, \n6.location, \n7.city,"+ "\n8.state, \n9.number_of_rooms, \n10.building_type \n11.email, \n12.rental_start_date, \n13.rental_end_date\n");
                int modifyChoice = scanner.nextInt();
                scanner.nextLine();
                HashMap<Integer, String> map = new HashMap<Integer, String>();
                map.put(1, "type");
                map.put(2, "description");
                map.put(3, "square_footage");
                map.put(4, "availability");
                map.put(5, "price");
                map.put(6, "location");
                map.put(7, "city");
                map.put(8, "state");
                map.put(9, "number_of_rooms");
                map.put(10, "building_type");
                map.put(11, "email");
                map.put(12, "rental_start_date");
                map.put(13, "rental_end_date");
                PropertySIUD psuid = new PropertySIUD();
                boolean modifyStatus = psuid.modifyApartment(scanner, propertyId, modifyChoice, map);
                System.out.println("Property Apartment: "+(modifyStatus ?  "Updated":"Failed Update"));
            }
            else if(propertyTypeChoice == 1){
                System.out.println("Modifying house properties...");
                System.out.println("Enter property id you want to modify: ");
                int propertyId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Please enter the attribute you want to modify: "+"\n1.type, \n2.description, \n3.square_footage, \n4.availability, \n5.price, \n6.location, \n7.city,"+ "\n8.state, \n9.number_of_rooms, \n10.email, \n11.rental_start_date, \n12.rental_end_date\n");
                int modifyChoice = scanner.nextInt();
                scanner.nextLine();
                HashMap<Integer, String> map = new HashMap<Integer, String>();
                map.put(1, "type");
                map.put(2, "description");
                map.put(3, "square_footage");
                map.put(4, "availability");
                map.put(5, "price");
                map.put(6, "location");
                map.put(7, "city");
                map.put(8, "state");
                map.put(9, "number_of_rooms");
                map.put(10, "email");
                map.put(11, "rental_start_date");
                map.put(12, "rental_end_date");
                PropertySIUD psuid = new PropertySIUD();
                boolean modifyStatus = psuid.modifyHouse(scanner, propertyId, modifyChoice, map);
                System.out.println("Property House: "+(modifyStatus ?  "Updated":"Failed Update"));
            }
        }
        
    }
    
    public void searchCriteria(Scanner scanner, int searchChoice)
    {
        PropertySIUD psuid = new PropertySIUD();
        if(searchChoice == 1){
            System.out.println("Enter which type of property table you want to view? \n1.House \n2.Apartment \n3.Commercial Building");
            int propType = scanner.nextInt();
            scanner.nextLine();
            if(propType == 1){
                List<House> houses = new ArrayList<House>();
                House house = new House();
                psuid.searchBasedOnType("house", houses, house);
                for (House home : houses) {
                    System.out.println(home);
                }
            }
            else if(propType == 2){
                List<Apartment> apartments = new ArrayList<Apartment>();
                Apartment apartment = new Apartment();
                psuid.searchBasedOnType("apartment", apartments, apartment);
                for (Apartment home : apartments) {
                    System.out.println(home);
                }
            }
            else if(propType == 3){
                List<CommercialBuilding> commercialBuildings = new ArrayList<CommercialBuilding>();
                CommercialBuilding commercialBuilding = new CommercialBuilding();
                psuid.searchBasedOnType("commercial_building", commercialBuildings, commercialBuilding);
                for (CommercialBuilding home : commercialBuildings) {
                    System.out.println(home);
                }
            }
            else{
                System.out.println("Invalid entry. ");
                return;
            }
        }
    }

    public void agentOperations(Scanner scanner, String emailAddress, String password){
        while(true){
            System.out.println("Please select an option:");
            System.out.println("1. Add a property.");
            System.out.println("2. Modify a property.");
            System.out.println("3. Search a property.");
            System.out.println("4. Delete a property");
            System.out.println("5. View Bookings");
            System.out.println("6. Cancel Bookings");
            System.out.println("7. Add Additional Properties ");

            System.out.println("8. Exit");
            System.out.print("Enter your choice (1/2/3/4/5/6/7/8): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // To clear the newline character

            if (choice == 8) {
                System.out.println("Exiting...");
                break;
            }
            
            int propertyId = 0;
            if(choice ==1 || choice ==4){
                System.out.print("Enter property id: ");
                propertyId = scanner.nextInt();
                scanner.nextLine();
            }

            if(choice == 1){
                // Insert a property
                System.out.println("Please select property type:");
                System.out.println("1. House");
                System.out.println("2. Commercial building");
                System.out.println("3. Apartment");
                int propChoice = scanner.nextInt();
                scanner.nextLine();
                if(propChoice == 1){

                    //insert house
                    String type = "house";
                    System.out.print("Enter number of rooms in the house : ");
                    int numberOfRooms = scanner.nextInt();
                    scanner.nextLine();
                    readingCommonAttributes(propertyId, scanner, type, numberOfRooms, emailAddress);

                    
                }
                if(propChoice == 2){
                    //insert commercial building
                    String type = "commercial_building";
                    readingCommonAttributes(propertyId, scanner, type, 0, emailAddress);
                }
                if(propChoice == 3){
                    //insert apartment
                    String type = "apartment";
                    System.out.print("Enter number of rooms in the apartment : ");
                    int numberOfRooms = scanner.nextInt();
                    scanner.nextLine();
                    readingCommonAttributes(propertyId, scanner, type, numberOfRooms, emailAddress);
                }
            }

            else if(choice == 2){
                // Modify a property
                readingUpdateAttributes(scanner);
                
            }

            else if(choice == 3){
                // Search a property
                System.out.println("Enter how you want to search the property?");
                System.out.println("1. Based on the type. This gives you the entire table of type House/Apartment/CommBuilding.");
                System.out.println("2. Based on the attributes in the property table.");
                int searchChoice = scanner.nextInt();
                scanner.nextLine();
                if(searchChoice != 1 && searchChoice !=2) continue;
                searchCriteria(scanner, searchChoice);
            }

            else if(choice == 4){
                // Delete a property
                System.out.print("Enter the ID of the property to delete: ");
                boolean propertyDeleted = false;
                PropertySIUD psuid = new PropertySIUD();
                propertyDeleted = psuid.deleteProperty(propertyId);
            
                if (propertyDeleted) {
                    System.out.println("Property deleted successfully.");
                } else {
                    System.out.println("Property with ID " + propertyId + " not found.");
                }
            }
            else if (choice == 5) {
                ViewBookingsForAgent(emailAddress, password);
            }
            else if (choice == 6) {
                CancelBookingsForAgent(emailAddress,password);
            }
            else if (choice == 7) {
                addAdditionalProperties(emailAddress,password);
            }
            else{
                System.out.println("Enter a valid choice. try again?");
                continue;
            }
        }
    }
    public void renterOperations(Scanner scanner, String email, String password){

        while(true){
            System.out.println("Please select an option:");
            System.out.println("1. Search a property.");
            System.out.println("2. View Bookings.");
            System.out.println("3. Cancel Bookings.");
            System.out.println("4. Delete address");
            System.out.println("5. Modify address");
            System.out.println("6. Delete credit card and associated address");
            System.out.println("7. Modify credit card and associated address");
            System.out.println("8. Join a reward program");

            System.out.println("9. Exit");
            System.out.print("Enter your choice (1/2/3/4/5/6/7/8/9): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // To clear the newline character

            if (choice == 9) {
                System.out.println("Exiting...");
                break;
            }
            
            

            if(choice == 1){
                
                System.out.println("Want to search property?(Yes/No)");
                String answer = scanner.nextLine();
                PropertySIUD psuid = new PropertySIUD();
                if(answer.equalsIgnoreCase("yes")){
                    psuid.propertySearch(email, password);
                }
                else if(answer.equalsIgnoreCase("no")){
                    System.out.println("ok!");
                }
                else{
                    System.out.println("Enter proper input. Exiting..");
                }
            continue;                    
            }
            

            else if(choice == 2){
                
                ViewBookingsForRenter(email,password);
                continue;
                
            }

            else if(choice == 3){
                CancelBookingsForRenter(email,password);
                continue;
            }

            else if(choice == 4){
                deleteAddress(email);
            }

            else if(choice == 5){
                modifyAddress(email);
            }

            else if(choice == 6){
                deleteCreditInfo(email);
            }

            else if(choice == 7){
                modifyCreditInfo(email);
            }

            else if(choice == 8){
                JoinRewardProgram(email, password);
            }

            else{
                System.out.println("Enter a valid choice. try again?");
                continue;
            }
        }


        return;






    }


    public void ViewBookingsForRenter(String emailAddress,String password){
        
        try(Connection connection = DatabaseConnector.getConnection()) {
                    String sql2 = "SELECT property_id,credit_card from books where email=?";
                    PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                    pstmt2.setString(1, emailAddress);
                    ResultSet resultSet2 = pstmt2.executeQuery();
                    System.out.println("Here are the bookings you have made.");
                    while (resultSet2.next()) {
                            String credit_card=resultSet2.getString("credit_card");
                            int p_id = resultSet2.getInt("property_id");
                            System.out.printf("Property_id: %d, Credit Card: %s%n",p_id,credit_card);
                        }

                        } catch (SQLException e) {
            e.printStackTrace();
        

         }
     return;
}




public void CancelBookingsForRenter(String emailAddress,String password){
    
        try(Connection connection = DatabaseConnector.getConnection()) {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the property id you want to cancel.");
            int pid=sc.nextInt();

            int price=0;
            String creditcard="";
                    String sql22 = "SELECT credit_card from books where email=? AND property_id=?";
                    PreparedStatement pstmt22 = connection.prepareStatement(sql22);
                    pstmt22.setString(1, emailAddress);
                    pstmt22.setInt(2, pid);
                    ResultSet resultSet22 = pstmt22.executeQuery();
                    if(resultSet22.next())
                    creditcard=resultSet22.getString("credit_card");


                    String sqlh = "SELECT price from house where property_id=?";
                    PreparedStatement pstmth = connection.prepareStatement(sqlh);
                    pstmth.setInt(1, pid);
                    ResultSet resultSeth = pstmth.executeQuery();
                    if(resultSeth.next())
                    price=resultSeth.getInt("price");

                    String sqla = "SELECT price from apartment where property_id=?";
                    PreparedStatement pstmta = connection.prepareStatement(sqla);
                    pstmta.setInt(1, pid);
                    ResultSet resultSeta = pstmta.executeQuery();
                    if(resultSeta.next())
                    price=resultSeta.getInt("price");

                    String sqlc = "SELECT price from commercial_building where property_id=?";
                    PreparedStatement pstmtc = connection.prepareStatement(sqlc);
                    pstmtc.setInt(1, pid);
                    ResultSet resultSetc = pstmtc.executeQuery();
                    if(resultSetc.next())
                    price=resultSetc.getInt("price");


                    String sql2 = "delete from books where property_id=? ";
                    PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                    pstmt2.setInt(1, pid);
                    pstmt2.executeUpdate();

                    String sql11= "update house set availability='yes' where property_id=?";
                        PreparedStatement pstmt11 = connection.prepareStatement(sql11);
                        pstmt11.setInt(1, pid);
                        pstmt11.executeUpdate(); 

                        String sql12= "update apartment set availability='yes' where property_id=?";
                        PreparedStatement pstmt12 = connection.prepareStatement(sql12);
                        pstmt12.setInt(1, pid);
                        pstmt12.executeUpdate();
                
                        String sql13= "update commercial_building set availability='yes' where property_id=?";
                        PreparedStatement pstmt13 = connection.prepareStatement(sql13);
                        pstmt13.setInt(1, pid);
                        pstmt13.executeUpdate();


                        System.out.printf("Your booking for property id: %d has been cancelled. $%d willl be refunded to your account %s",pid,price,creditcard);
                        return;

                        } catch (SQLException e) {
            e.printStackTrace();
        

         }
     return;
}



public void ViewBookingsForAgent(String emailAddress,String password){
try(Connection connection = DatabaseConnector.getConnection()) {
                    String sql22 = "with temp(agency) as (SELECT agency from agent where email=?)SELECT books.property_id, books.credit_card,books.email as Renter_email, apartment.* from books join apartment using(property_id) where apartment.email in (SELECT email from agent,temp where agent.agency=temp.agency)";
                    PreparedStatement pstmt22 = connection.prepareStatement(sql22);
                    pstmt22.setString(1, emailAddress);
                    ResultSet resultSet22 = pstmt22.executeQuery();
                   while(resultSet22.next())
                    {

                    String rentermail=resultSet22.getString("Renter_email");
                    int pid=resultSet22.getInt("property_id");
                    String creditcard=resultSet22.getString("credit_card");
                    String type=resultSet22.getString("type");
                    String description=resultSet22.getString("description");
                    String square_footage=resultSet22.getString("square_footage");
                    String availability=resultSet22.getString("availability");
                    String location=resultSet22.getString("location");
                    String city=resultSet22.getString("city");
                    String state=resultSet22.getString("state");
                    String bt=resultSet22.getString("building_type");
                    String rs=resultSet22.getString("rental_start_date");
                    String re=resultSet22.getString("rental_end_date");

                    int nr=resultSet22.getInt("number_of_rooms");
                    int price=resultSet22.getInt("price");
                    System.out.printf("Renter email address: %s , Property_id: %d, Payment details: %s%n, Property type: %s, Description:%s, Square_footage: %s, Availability:%s%n, Location: %s, City:%s, State:%s, Building Type:%s%n, Rent start date:%s, Rent end date:%s%n, Number of rooms:%d, Price:%d%n",rentermail,pid,creditcard,type,description,square_footage,availability,location,city,state,bt,rs,re,nr,price);
                    System.out.println();
                    }

                    String sql23 = "with temp(agency) as (SELECT agency from agent where email=?)SELECT books.property_id, books.credit_card,books.email as Renter_email, house.* from books join house using(property_id) where house.email in (SELECT email from agent,temp where agent.agency=temp.agency)";
                    PreparedStatement pstmt23 = connection.prepareStatement(sql23);
                    pstmt23.setString(1, emailAddress);
                    ResultSet resultSet23 = pstmt23.executeQuery();
                   while(resultSet23.next())
                    {

                    String rentermail=resultSet23.getString("Renter_email");
                    int pid=resultSet23.getInt("property_id");
                    String creditcard=resultSet23.getString("credit_card");
                    String type=resultSet23.getString("type");
                    String description=resultSet23.getString("description");
                    String square_footage=resultSet23.getString("square_footage");
                    String availability=resultSet23.getString("availability");
                    String location=resultSet23.getString("location");
                    String city=resultSet23.getString("city");
                    String state=resultSet23.getString("state");
                    String rs=resultSet23.getString("rental_start_date");
                    String re=resultSet23.getString("rental_end_date");

                    int nr=resultSet23.getInt("number_of_rooms");
                    int price=resultSet23.getInt("price");
                    System.out.printf("Renter email address: %s , Property_id: %d, Payment details: %s%n, Property type: %s, Description:%s, Square_footage: %s%n, Availability:%s, Location: %s, City:%s, State:%s%n, Rent start date:%s, Rent end date:%s%n, Number of rooms: %s, Price: %d%n",rentermail,pid,creditcard,type,description,square_footage,availability,location,city,state,rs,re,nr,price);
                     System.out.println();
                     }

                     String sql2 = "with temp(agency) as (SELECT agency from agent where email=?)SELECT books.property_id, books.credit_card,books.email as Renter_email, commercial_building.* from books join commercial_building using(property_id) where commercial_building.email in (SELECT email from agent,temp where agent.agency=temp.agency)";
                    PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                    pstmt2.setString(1, emailAddress);
                    ResultSet resultSet2 = pstmt2.executeQuery();
                   while(resultSet2.next())
                    {

                    String rentermail=resultSet2.getString("Renter_email");
                    int pid=resultSet2.getInt("property_id");
                    String creditcard=resultSet2.getString("credit_card");
                    String type=resultSet2.getString("type");
                    String description=resultSet2.getString("description");
                    String square_footage=resultSet2.getString("square_footage");
                    String availability=resultSet2.getString("availability");
                    String location=resultSet2.getString("location");
                    String city=resultSet2.getString("city");
                    String state=resultSet2.getString("state");
                    String tp=resultSet2.getString("type_of_business");
                    String rs=resultSet2.getString("rental_start_date");
                    String re=resultSet2.getString("rental_end_date");

                    
                    int price=resultSet2.getInt("price");
                    System.out.printf("Renter email address: %s , Property_id: %d, Payment details: %s%n, Property type: %s, Description:%s, Square_footage: %s%n, Availability:%s, Location: %s, City:%s, State:%s%n, Rent start date:%s, Rent end date:%s%n, Price: %d, Type of business:%s%n",rentermail,pid,creditcard,type,description,square_footage,availability,location,city,state,rs,re,price,tp);
                     System.out.println();
                     }
                       return;

                        } catch (SQLException e) {
            e.printStackTrace();
        

         }
     return;

}
public void CancelBookingsForAgent(String emailAddress,String password){



    try(Connection connection = DatabaseConnector.getConnection()) {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the property id you want to cancel.");
            int pid=sc.nextInt();

            int price=0;
            String creditcard="";
            String email="";
                    String sql22 = "SELECT credit_card,email from books where property_id=?";
                    PreparedStatement pstmt22 = connection.prepareStatement(sql22);
                    pstmt22.setInt(1, pid);
                    ResultSet resultSet22 = pstmt22.executeQuery();
                    if(resultSet22.next())
                    {creditcard=resultSet22.getString("credit_card");
                    email=resultSet22.getString("email");
                    }


                    String sqlh = "SELECT price from house where property_id=?";
                    PreparedStatement pstmth = connection.prepareStatement(sqlh);
                    pstmth.setInt(1, pid);
                    ResultSet resultSeth = pstmth.executeQuery();
                    if(resultSeth.next())
                    price=resultSeth.getInt("price");

                    String sqla = "SELECT price from apartment where property_id=?";
                    PreparedStatement pstmta = connection.prepareStatement(sqla);
                    pstmta.setInt(1, pid);
                    ResultSet resultSeta = pstmta.executeQuery();
                    if(resultSeta.next())
                    price=resultSeta.getInt("price");

                    String sqlc = "SELECT price from commercial_building where property_id=?";
                    PreparedStatement pstmtc = connection.prepareStatement(sqlc);
                    pstmtc.setInt(1, pid);
                    ResultSet resultSetc = pstmtc.executeQuery();
                    if(resultSetc.next())
                    price=resultSetc.getInt("price");


                    String sql2 = "delete from books where property_id=? ";
                    PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                    pstmt2.setInt(1, pid);
                    pstmt2.executeUpdate();

                    String sql11= "update house set availability='yes' where property_id=?";
                        PreparedStatement pstmt11 = connection.prepareStatement(sql11);
                        pstmt11.setInt(1, pid);
                        pstmt11.executeUpdate(); 

                        String sql12= "update apartment set availability='yes' where property_id=?";
                        PreparedStatement pstmt12 = connection.prepareStatement(sql12);
                        pstmt12.setInt(1, pid);
                        pstmt12.executeUpdate();
                
                        String sql13= "update commercial_building set availability='yes' where property_id=?";
                        PreparedStatement pstmt13 = connection.prepareStatement(sql13);
                        pstmt13.setInt(1, pid);
                        pstmt13.executeUpdate();


                        System.out.printf("The property id: %d has been cancelled. $%d will be refunded to the renter %s to his account: %s\n",pid,price,email,creditcard);
                        return;

                        } catch (SQLException e) {
            e.printStackTrace();
        

         }
     return;

}


public void deleteAddress(String email){
    try(Connection connection = DatabaseConnector.getConnection()) {
        Scanner sc=new Scanner(System.in);
        String address1="";
        String sql22 = "SELECT address from perspective_renter_address where email=?";
        PreparedStatement pstmt22 = connection.prepareStatement(sql22);
        pstmt22.setString(1, email);
        ResultSet resultSet22 = pstmt22.executeQuery();
        System.out.println("Address associated with this renter:");
        if(resultSet22.next()){
            address1=resultSet22.getString("address");
            System.out.println(address1);
        }
        System.out.println("Enter the address you want to delete:");
        String addressToDelete = sc.nextLine();
        String sql000 = "SELECT payment_address FROM renter_card_address WHERE email=?";
        PreparedStatement pstmt00 = connection.prepareStatement(sql000);
        pstmt00.setString(1, email);
        ResultSet resultSet00 = pstmt00.executeQuery();
        boolean check = false;
        if(resultSet00.next()){
            address1=resultSet00.getString("address");
            if(address1.equalsIgnoreCase(addressToDelete)){
                check = true;
                String sql220 = "DELETE from renter_card_address where payment_address=?";
                PreparedStatement pstmt220 = connection.prepareStatement(sql220);
                pstmt220.setString(1, addressToDelete);
                pstmt220.executeUpdate();
                String sql221 = "DELETE from perspective_renter_address where address=?";
                PreparedStatement pstmt221 = connection.prepareStatement(sql221);
                pstmt221.setString(1, addressToDelete);
                System.out.println(sql221);
                pstmt221.executeUpdate();
            }
        }

        if(!check){
            String sql222 = "DELETE from perspective_renter_address where address=?";
            PreparedStatement pstmt222 = connection.prepareStatement(sql222);
            pstmt222.setString(1, addressToDelete);
            pstmt222.executeUpdate();
            check = true;
        }
        if(check){
            System.out.println("The selected address has been deleted");
        }
        return;

        } catch (SQLException e) {
        e.printStackTrace();
    

     }
 return;
}



public void deleteCreditInfo(String email){
    try(Connection connection = DatabaseConnector.getConnection()) {
        Scanner sc=new Scanner(System.in);
        String address1="";
        String credit_card = "";
        String sql22 = "SELECT * from renter_card_address where email=?";
        PreparedStatement pstmt22 = connection.prepareStatement(sql22);
        pstmt22.setString(1, email);
        ResultSet resultSet22 = pstmt22.executeQuery();
        System.out.println("Credit information associated with this renter:");
        if(resultSet22.next()){
            credit_card=resultSet22.getString("credit_card");
            address1=resultSet22.getString("payment_address");
            System.out.print(credit_card+ "  ");
            System.out.println(address1);
        }
        System.out.println("Enter the payment card you want to delete:");
        String paymentToDelete = sc.nextLine();
        String addressToDelete = sc.nextLine();
        String sql223 = "DELETE from books where credit_card=? and email=?";
        PreparedStatement pstmt223 = connection.prepareStatement(sql223);
        pstmt223.setString(1, paymentToDelete);
        pstmt223.setString(2, email);
        pstmt223.executeUpdate();
        String sql222 = "DELETE from renter_card_address where credit_card=? and payment_address=?";
        PreparedStatement pstmt222 = connection.prepareStatement(sql222);
        pstmt222.setString(1, paymentToDelete);
        pstmt222.setString(2, addressToDelete);
        pstmt222.executeUpdate();
        
        System.out.println("The selected payment information is deleted");
        return;

        } catch (SQLException e) {
        e.printStackTrace();
    

     }
 return;
}

public void modifyAddress(String email){
    try(Connection connection = DatabaseConnector.getConnection()) {
        Scanner sc=new Scanner(System.in);
        String address1="";
        String sql22 = "SELECT address from perspective_renter_address where email=?";
        PreparedStatement pstmt22 = connection.prepareStatement(sql22);
        pstmt22.setString(1, email);
        ResultSet resultSet22 = pstmt22.executeQuery();
        System.out.println("Address associated with this renter:");
        if(resultSet22.next()){
            address1=resultSet22.getString("address");
            System.out.println(address1);
        }
        System.out.println("Enter the address you want to modify:");
        String addressToUpdate = sc.nextLine();
        System.out.println("Enter the new address");
        String addressUpdated = sc.nextLine();
        String sql000 = "SELECT payment_address FROM renter_card_address WHERE email=?";
        PreparedStatement pstmt00 = connection.prepareStatement(sql000);
        pstmt00.setString(1, email);
        ResultSet resultSet00 = pstmt00.executeQuery();
        boolean check = false;
        if(resultSet00.next()){
            address1=resultSet00.getString("payment_address");
            if(address1.equalsIgnoreCase(addressToUpdate)){
                check = true;
                String sql220 = "Update renter_card_address set payment_address=? where payment_address=?";
                PreparedStatement pstmt220 = connection.prepareStatement(sql220);
                pstmt220.setString(1, addressUpdated);
                pstmt220.setString(2, addressToUpdate);
                pstmt220.executeUpdate();
                String sql221 = "Update perspective_renter_address set address=? where address=?";
                PreparedStatement pstmt221 = connection.prepareStatement(sql221);
                pstmt221.setString(1, addressUpdated);
                pstmt221.setString(2, addressToUpdate);
                System.out.println(sql221);
                pstmt221.executeUpdate();
            }
        }

        if(!check){
            String sql222 = "Update perspective_renter_address set address=? where address=?";
            PreparedStatement pstmt222 = connection.prepareStatement(sql222);
            pstmt222.setString(1, addressUpdated);
            pstmt222.setString(2, addressToUpdate);
            pstmt222.executeUpdate();
            check = true;
        }
        if(check){
            System.out.println("The selected address has been updated");
        }
        return;

        } catch (SQLException e) {
        e.printStackTrace();
    

     }
 return;
}

public void modifyCreditInfo(String email){
    try(Connection connection = DatabaseConnector.getConnection()) {
        Scanner sc=new Scanner(System.in);
        String address1="";
        String credit_card="";
        String sql22 = "SELECT * from renter_card_address where email=?";
        PreparedStatement pstmt22 = connection.prepareStatement(sql22);
        pstmt22.setString(1, email);
        ResultSet resultSet22 = pstmt22.executeQuery();
        System.out.println("Payment information associated with this renter:");
        if(resultSet22.next()){
            address1=resultSet22.getString("payment_address");
            credit_card=resultSet22.getString("credit_card");
            System.out.print(address1+" ");
            System.out.println(credit_card);
        }
        System.out.println("Enter the credit card you want to modify");
        String creditToUpdate = sc.nextLine();
        System.out.println("Enter the new credit card");
        String creditUpdated = sc.nextLine();

        String sql220 = "insert into renter_card_address values (?,?,?)";
        PreparedStatement pstmt220 = connection.prepareStatement(sql220);
        pstmt220.setString(1, email);
        pstmt220.setString(2, creditUpdated.trim());
        pstmt220.setString(3, address1);
        pstmt220.executeUpdate();
        
        String sql2 = "update books set credit_card=? where credit_card=? and email=?";
        PreparedStatement pstmt2 = connection.prepareStatement(sql2);
        pstmt2.setString(1, creditUpdated.trim());
        pstmt2.setString(2, creditToUpdate);
        pstmt2.setString(3, email);
        pstmt2.executeUpdate();
        
        sql220 = "delete from renter_card_address where credit_card=? and email=?";
        pstmt220 = connection.prepareStatement(sql220);
        pstmt220.setString(1, creditToUpdate);
        pstmt220.setString(2, email);
        pstmt220.executeUpdate();

        


        

        // sql2 = "Update books set credit_card=?,email=? where credit_card=? and email=?";
        // PreparedStatement pstmt2 = connection.prepareStatement(sql2);
        // pstmt2.setString(1, creditUpdated);
        // pstmt2.setString(2, email);
        // pstmt2.setString(3, creditToUpdate);
        // pstmt2.setString(4, email);

        // pstmt2.executeUpdate();

        System.out.println("The selected payment information has been updated");
        return;

        } catch (SQLException e) {
        e.printStackTrace();
    

     }
 return;
}

public void addAdditionalProperties(String email,String password){
    try(Connection connection = DatabaseConnector.getConnection()) {
            Scanner scanner=new Scanner(System.in);
            int pd=0;
           while(true){
               System.out.println("Enter the property id:");
               pd=scanner.nextInt();
            String sql21 = "Select property_id from property where property_id=?";
                   PreparedStatement pstmt21 = connection.prepareStatement(sql21);
                   pstmt21.setInt(1, pd);
                   ResultSet resultSet21 = pstmt21.executeQuery();
                   int check=0;
                   if(resultSet21.next())
                   check=resultSet21.getInt("property_id");
                   if(check==pd)
                   break;
                   else{
                       System.out.println("The property id is invalid. Enter a valid id.");
                       continue;
                   }
           }
           
           System.out.println("Enter the crime rate percentage in the neighbourhood.");
           String crime=scanner.next();
           System.out.println("Enter the number of schools in the neighbourhood:");
           String sch=scanner.next();
            System.out.println("Enter the nearby land information of the neighbourhood:");
           String land=scanner.next();
           System.out.println("Enter the number of vacation homes in the neighbourhood:");
           String vac=scanner.next();

        String sql1 = "INSERT into additional_properties values(?,?,?)";
                   PreparedStatement pstmt1 = connection.prepareStatement(sql1);
                   pstmt1.setInt(1, pd);
                   pstmt1.setString(2, crime);
                   pstmt1.setString(3, sch);
                   pstmt1.executeUpdate();
               
                   String sql = "INSERT into neighbourhood values(?,?,?,?,?)";
                   PreparedStatement pstmt = connection.prepareStatement(sql);
                   pstmt.setInt(1, pd);
                   pstmt.setString(2, crime);
                   pstmt.setString(3, sch);
                   pstmt.setString(4, land);
                   pstmt.setString(5, vac);
                   pstmt.executeUpdate();
            System.out.println("Additional information about the property has been inserted.");
                  

           

       }catch(SQLException e){  e.printStackTrace();}
}

public void JoinRewardProgram(String emailAddress,String password){
                        try(Connection connection = DatabaseConnector.getConnection()) {
                           pgid++;
    String sql = "INSERT into reward_program values(?,?,?)";
                   PreparedStatement pstmt = connection.prepareStatement(sql);
                   pstmt.setString(1, emailAddress);
                   pstmt.setInt(2, pgid);
                   pstmt.setDouble(3, 0);
                   pstmt.executeUpdate();
            System.out.println("You have successfully enrolled in the reward program. Book properties to earn points.");
            }catch(SQLException e){  e.printStackTrace();}
                  
}

}
