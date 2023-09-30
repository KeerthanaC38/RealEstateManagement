import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

public class PropertySIUD {
    public boolean insertHouseIntoDB(House house){
        try (Connection conn = DatabaseConnector.getConnection()) {
            String insertHouseSql = "INSERT INTO house (property_id, type, description, square_footage, availability, price, location, city, state, number_of_rooms, email, rental_start_date, rental_end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String insertPropertySql = "INSERT INTO property (property_id) VALUES (?)";

            try (PreparedStatement houseStmnt = conn.prepareStatement(insertHouseSql);
                PreparedStatement propertyStmt = conn.prepareStatement(insertPropertySql)) {
                conn.setAutoCommit(false);

                houseStmnt.setInt(1, house.getPropertyId());
                houseStmnt.setString(2, house.getType());
                houseStmnt.setString(3, house.getDescription());
                houseStmnt.setDouble(4, house.getSquareFootage());
                houseStmnt.setString(5, house.getAvailability());
                houseStmnt.setString(6, house.getPrice());
                houseStmnt.setString(7, house.getLocation());
                houseStmnt.setString(8, house.getCity());
                houseStmnt.setString(9, house.getState());
                houseStmnt.setInt(10, house.getNumberOfRooms());
                houseStmnt.setString(11, house.getEmail());
                houseStmnt.setDate(12, house.getRentalStartDate());
                houseStmnt.setDate(13, house.getRentalEndDate());
                

                propertyStmt.setInt(1, house.getPropertyId());
            
                propertyStmt.executeUpdate();
                houseStmnt.executeUpdate();
                conn.commit();

                System.out.println("House inserted successfully!");
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
    public boolean insertApartment(Apartment apartment){
        try (Connection conn = DatabaseConnector.getConnection()) {
            String insertApartmentSql = "INSERT INTO apartment (property_id, type, description, square_footage, availability, price, location, city, state, number_of_rooms, building_type, email, rental_start_date, rental_end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String insertPropertySql = "INSERT INTO property (property_id) VALUES (?)";

            try (PreparedStatement apartmentStmnt = conn.prepareStatement(insertApartmentSql);
                PreparedStatement propertyStmt = conn.prepareStatement(insertPropertySql)) {
                conn.setAutoCommit(false);

                apartmentStmnt.setInt(1, apartment.getPropertyId());
                apartmentStmnt.setString(2, apartment.getType());
                apartmentStmnt.setString(3, apartment.getDescription());
                apartmentStmnt.setDouble(4, apartment.getSquareFootage());
                apartmentStmnt.setString(5, apartment.getAvailability());
                apartmentStmnt.setString(6, apartment.getPrice());
                apartmentStmnt.setString(7, apartment.getLocation());
                apartmentStmnt.setString(8, apartment.getCity());
                apartmentStmnt.setString(9, apartment.getState());
                apartmentStmnt.setInt(10, apartment.getNumberOfRooms());
                apartmentStmnt.setString(11, apartment.getBuildingType());
                apartmentStmnt.setString(12, apartment.getEmail());
                apartmentStmnt.setDate(13, apartment.getRentalStartDate());
                apartmentStmnt.setDate(14, apartment.getRentalEndDate());
                

                propertyStmt.setInt(1, apartment.getPropertyId());
            
                propertyStmt.executeUpdate();
                apartmentStmnt.executeUpdate();
                conn.commit();

                System.out.println("Apartment inserted successfully!");
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
    public boolean insertCommercialBuilding(CommercialBuilding commercialBuilding){
        try (Connection conn = DatabaseConnector.getConnection()) {
            String insertCBuildingSql = "INSERT INTO commercial_building (property_id, type, description, square_footage, availability, price, location, city, state, type_of_business, email, rental_start_date, rental_end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String insertPropertySql = "INSERT INTO property (property_id) VALUES (?)";

            try (PreparedStatement cBuildingStmnt = conn.prepareStatement(insertCBuildingSql);
                PreparedStatement propertyStmt = conn.prepareStatement(insertPropertySql)) {
                conn.setAutoCommit(false);

                cBuildingStmnt.setInt(1, commercialBuilding.getPropertyId());
                cBuildingStmnt.setString(2, commercialBuilding.getType());
                cBuildingStmnt.setString(3, commercialBuilding.getDescription());
                cBuildingStmnt.setDouble(4, commercialBuilding.getSquareFootage());
                cBuildingStmnt.setString(5, commercialBuilding.getAvailability());
                cBuildingStmnt.setString(6, commercialBuilding.getPrice());
                cBuildingStmnt.setString(7, commercialBuilding.getLocation());
                cBuildingStmnt.setString(8, commercialBuilding.getCity());
                cBuildingStmnt.setString(9, commercialBuilding.getState());
                cBuildingStmnt.setString(10, commercialBuilding.getTypeOfBusiness());
                cBuildingStmnt.setString(11, commercialBuilding.getEmail());
                cBuildingStmnt.setDate(12, commercialBuilding.getRentalStartDate());
                cBuildingStmnt.setDate(13, commercialBuilding.getRentalEndDate());
                

                propertyStmt.setInt(1, commercialBuilding.getPropertyId());
            
                propertyStmt.executeUpdate();
                cBuildingStmnt.executeUpdate();
                conn.commit();

                System.out.println("Commercial Building inserted successfully!");
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

    public boolean deleteProperty(int propertyID){
        try (Connection conn = DatabaseConnector.getConnection()) {
            String deleteFromHouse = "DELETE FROM house WHERE property_id = ?";
            String deleteFromApartment = "DELETE FROM apartment WHERE property_id = ?";
            String deleteFromCommercialBuilding = "DELETE FROM commercial_building WHERE property_id = ?";
            String deleteFromProperty = "DELETE FROM property WHERE property_id = ?";
            try (PreparedStatement propertyDelPreparedStatement = conn.prepareStatement(deleteFromProperty);
            PreparedStatement houseDelPreparedStatement = conn.prepareStatement(deleteFromHouse);
            PreparedStatement apartmentDelPreparedStatement = conn.prepareStatement(deleteFromApartment);
            PreparedStatement cBuildingDelPreparedStatement = conn.prepareStatement(deleteFromCommercialBuilding);) {
                conn.setAutoCommit(false);

                propertyDelPreparedStatement.setInt(1, propertyID);
                houseDelPreparedStatement.setInt(1, propertyID);
                apartmentDelPreparedStatement.setInt(1, propertyID);
                cBuildingDelPreparedStatement.setInt(1, propertyID);

                houseDelPreparedStatement.executeUpdate();
                apartmentDelPreparedStatement.executeUpdate();
                cBuildingDelPreparedStatement.executeUpdate();
                propertyDelPreparedStatement.executeUpdate();
                conn.commit();

                System.out.println("Property deleted successfully!");
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

    public boolean modifyHouse(Scanner scanner, int propertyId, int modifyChoice, HashMap<Integer, String> map){
        try (Connection conn = DatabaseConnector.getConnection()) {
    
            String attribute = map.get(modifyChoice);
            String updateHouseSql = "UPDATE house SET " + attribute + " = ? WHERE property_id = ?";
    
            try (PreparedStatement houseStmnt = conn.prepareStatement(updateHouseSql)) {
                conn.setAutoCommit(false);
    
                switch (attribute) {
                    case "type":
                        System.out.println("Enter new type:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "description":
                        System.out.println("Enter new description:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "square_footage":
                        System.out.println("Enter new square footage:");
                        houseStmnt.setDouble(1, scanner.nextDouble());
                        scanner.nextLine();
                        break;
                    // Add more cases for other attributes here
                    case "availability":
                        System.out.println("Enter new availability:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "price":
                        System.out.println("Enter new price:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "location":
                        System.out.println("Enter new location:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "city":
                        System.out.println("Enter new city:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "state":
                        System.out.println("Enter new state:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "number_of_rooms":
                        System.out.println("Enter new no. of rooms:");
                        houseStmnt.setInt(1, scanner.nextInt());
                        scanner.nextLine();
                        break;
                    case "email":
                        System.out.println("Enter new email:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "rental_end_date":
                        System.out.println("Enter new rental start date:");
                        String rentEnd;
                        java.util.Date reDate = null;
                        java.sql.Date rental_end_date;
                        //move_in_date = scanner.nextLine();
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        while(reDate == null)
                        {
                            try
                            {
                                rentEnd = scanner.nextLine();
                                reDate =  df.parse(rentEnd);
                            }
                            catch(ParseException e)
                            {
                                System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
                            }
                        }
                        rental_end_date = new java.sql.Date(reDate.getTime());
                        houseStmnt.setDate(1, rental_end_date);
                        break;
                    case "rental_start_date":
                        System.out.println("Enter new rental end date:");
                        String rentStart;
                        java.util.Date rsDate = null;
                        java.sql.Date rental_start_date;
                        DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                        //move_in_date = scanner.nextLine();
                        while(rsDate == null)
                        {
                            try
                            {
                                rentStart = scanner.nextLine();
                                rsDate =  df1.parse(rentStart);
                            }
                            catch(ParseException e)
                            {
                                System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
                            }
                        }
                        rental_start_date = new java.sql.Date(rsDate.getTime());
                        houseStmnt.setDate(1, rental_start_date);
                        break;
                    default:
                        System.out.println("Invalid attribute, no changes made.");
                        return false;
                }
    
                houseStmnt.setInt(2, propertyId);
                houseStmnt.executeUpdate();
                conn.commit();
    
                System.out.println("House updated successfully!");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean modifyApartment(Scanner scanner, int propertyId, int modifyChoice, HashMap<Integer, String> map){
        try (Connection conn = DatabaseConnector.getConnection()) {
    
            String attribute = map.get(modifyChoice);
            String updateApartmentSql = "UPDATE apartment SET " + attribute + " = ? WHERE property_id = ?";
    
            try (PreparedStatement houseStmnt = conn.prepareStatement(updateApartmentSql)) {
                conn.setAutoCommit(false);
    
                switch (attribute) {
                    case "type":
                        System.out.println("Enter new type:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "description":
                        System.out.println("Enter new description:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "square_footage":
                        System.out.println("Enter new square footage:");
                        houseStmnt.setDouble(1, scanner.nextDouble());
                        scanner.nextLine();
                        break;
                    // Add more cases for other attributes here
                    case "availability":
                        System.out.println("Enter new availability:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "price":
                        System.out.println("Enter new price:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "location":
                        System.out.println("Enter new location:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "city":
                        System.out.println("Enter new city:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "state":
                        System.out.println("Enter new state:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "number_of_rooms":
                        System.out.println("Enter new no. of rooms:");
                        houseStmnt.setInt(1, scanner.nextInt());
                        scanner.nextLine();
                        break;
                    case "building_type":
                        System.out.println("Enter new building type:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "email":
                        System.out.println("Enter new email:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "rental_end_date":
                        System.out.println("Enter new rental start date:");
                        String rentEnd;
                        java.util.Date reDate = null;
                        java.sql.Date rental_end_date;
                        //move_in_date = scanner.nextLine();
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        while(reDate == null)
                        {
                            try
                            {
                                rentEnd = scanner.nextLine();
                                reDate =  df.parse(rentEnd);
                            }
                            catch(ParseException e)
                            {
                                System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
                            }
                        }
                        rental_end_date = new java.sql.Date(reDate.getTime());
                        houseStmnt.setDate(1, rental_end_date);
                        break;
                    case "rental_start_date":
                        System.out.println("Enter new rental end date:");
                        String rentStart;
                        java.util.Date rsDate = null;
                        java.sql.Date rental_start_date;
                        DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                        //move_in_date = scanner.nextLine();
                        while(rsDate == null)
                        {
                            try
                            {
                                rentStart = scanner.nextLine();
                                rsDate =  df1.parse(rentStart);
                            }
                            catch(ParseException e)
                            {
                                System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
                            }
                        }
                        rental_start_date = new java.sql.Date(rsDate.getTime());
                        houseStmnt.setDate(1, rental_start_date);
                        break;
                    default:
                        System.out.println("Invalid attribute, no changes made.");
                        return false;
                }
    
                houseStmnt.setInt(2, propertyId);
                houseStmnt.executeUpdate();
                conn.commit();
    
                System.out.println("House updated successfully!");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean modifyCommercialBuilding(Scanner scanner, int propertyId, int modifyChoice, HashMap<Integer, String> map){
        try (Connection conn = DatabaseConnector.getConnection()) {
    
            String attribute = map.get(modifyChoice);
            String updateHouseSql = "UPDATE commercial_building SET " + attribute + " = ? WHERE property_id = ?";
    
            try (PreparedStatement houseStmnt = conn.prepareStatement(updateHouseSql)) {
                conn.setAutoCommit(false);
    
                switch (attribute) {
                    case "type":
                        System.out.println("Enter new type:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "description":
                        System.out.println("Enter new description:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "square_footage":
                        System.out.println("Enter new square footage:");
                        houseStmnt.setDouble(1, scanner.nextDouble());
                        scanner.nextLine();
                        break;
                    // Add more cases for other attributes here
                    case "availability":
                        System.out.println("Enter new availability:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "price":
                        System.out.println("Enter new price:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "location":
                        System.out.println("Enter new location:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "city":
                        System.out.println("Enter new city:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "state":
                        System.out.println("Enter new state:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "type_of_business":
                        System.out.println("Enter new no. of rooms:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "email":
                        System.out.println("Enter new email:");
                        houseStmnt.setString(1, scanner.nextLine());
                        break;
                    case "rental_end_date":
                        System.out.println("Enter new rental start date:");
                        String rentEnd;
                        java.util.Date reDate = null;
                        java.sql.Date rental_end_date;
                        //move_in_date = scanner.nextLine();
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        while(reDate == null)
                        {
                            try
                            {
                                rentEnd = scanner.nextLine();
                                reDate =  df.parse(rentEnd);
                            }
                            catch(ParseException e)
                            {
                                System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
                            }
                        }
                        rental_end_date = new java.sql.Date(reDate.getTime());
                        houseStmnt.setDate(1, rental_end_date);
                        break;
                    case "rental_start_date":
                        System.out.println("Enter new rental end date:");
                        String rentStart;
                        java.util.Date rsDate = null;
                        java.sql.Date rental_start_date;
                        DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                        //move_in_date = scanner.nextLine();
                        while(rsDate == null)
                        {
                            try
                            {
                                rentStart = scanner.nextLine();
                                rsDate =  df1.parse(rentStart);
                            }
                            catch(ParseException e)
                            {
                                System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
                            }
                        }
                        rental_start_date = new java.sql.Date(rsDate.getTime());
                        houseStmnt.setDate(1, rental_start_date);
                        break;
                    default:
                        System.out.println("Invalid attribute, no changes made.");
                        return false;
                }
    
                houseStmnt.setInt(2, propertyId);
                houseStmnt.executeUpdate();
                conn.commit();
    
                System.out.println("House updated successfully!");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<House> searchBasedOnType(String type, List<House> houses, House house){
        
        try (Connection conn = DatabaseConnector.getConnection()) {
            String searchPropertySql = "SELECT * FROM house WHERE type = ?";

            try (PreparedStatement searchStmnt = conn.prepareStatement(searchPropertySql)) {
                searchStmnt.setString(1, type);

                try (ResultSet resultSet = searchStmnt.executeQuery()) {
                    while (resultSet.next()) {
                        house = new House();
                        // Set house attributes from the resultSet
                        house.setPropertyId(resultSet.getInt("property_id"));
                        house.setType(resultSet.getString("type"));
                        house.setDescription(resultSet.getString("description"));
                        house.setSquareFootage(resultSet.getDouble("square_footage"));
                        house.setAvailability(resultSet.getString("availability"));
                        house.setPrice(resultSet.getString("price"));
                        house.setLocation(resultSet.getString("location"));
                        house.setCity(resultSet.getString("city"));
                        house.setState(resultSet.getString("state"));
                        house.setNumberOfRooms(resultSet.getInt("number_of_rooms"));
                        house.setEmail(resultSet.getString("email"));
                        house.setRentalStartDate(resultSet.getDate("rental_start_date"));
                        house.setRentalEndDate(resultSet.getDate("rental_end_date"));
                        houses.add(house);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return houses;
    }

    public List<Apartment> searchBasedOnType(String type, List<Apartment> apartments, Apartment apartment) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String searchPropertySql = "SELECT * FROM apartment WHERE type = ?";
    
            try (PreparedStatement searchStmnt = conn.prepareStatement(searchPropertySql)) {
                searchStmnt.setString(1, type);
    
                try (ResultSet resultSet = searchStmnt.executeQuery()) {
                    while (resultSet.next()) {
                        apartment = new Apartment();
                        // Set apartment attributes from the resultSet
                        apartment.setPropertyId(resultSet.getInt("property_id"));
                        apartment.setType(resultSet.getString("type"));
                        apartment.setDescription(resultSet.getString("description"));
                        apartment.setSquareFootage(resultSet.getDouble("square_footage"));
                        apartment.setAvailability(resultSet.getString("availability"));
                        apartment.setPrice(resultSet.getString("price"));
                        apartment.setLocation(resultSet.getString("location"));
                        apartment.setCity(resultSet.getString("city"));
                        apartment.setState(resultSet.getString("state"));
                        apartment.setNumberOfRooms(resultSet.getInt("number_of_rooms"));
                        apartment.setBuildingType(resultSet.getString("building_type"));
                        apartment.setEmail(resultSet.getString("email"));
                        apartment.setRentalStartDate(resultSet.getDate("rental_start_date"));
                        apartment.setRentalEndDate(resultSet.getDate("rental_end_date"));
                        apartments.add(apartment);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apartments;
    }

    public List<CommercialBuilding> searchBasedOnType(String type, List<CommercialBuilding> buildings, CommercialBuilding building) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String searchPropertySql = "SELECT * FROM commercial_building WHERE type = ?";
    
            try (PreparedStatement searchStmnt = conn.prepareStatement(searchPropertySql)) {
                searchStmnt.setString(1, type);
    
                try (ResultSet resultSet = searchStmnt.executeQuery()) {
                    while (resultSet.next()) {
                        building = new CommercialBuilding();
                        // Set commercial building attributes from the resultSet
                        building.setPropertyId(resultSet.getInt("property_id"));
                        building.setType(resultSet.getString("type"));
                        building.setDescription(resultSet.getString("description"));
                        building.setSquareFootage(resultSet.getDouble("square_footage"));
                        building.setAvailability(resultSet.getString("availability"));
                        building.setPrice(resultSet.getString("price"));
                        building.setLocation(resultSet.getString("location"));
                        building.setCity(resultSet.getString("city"));
                        building.setState(resultSet.getString("state"));
                        building.setTypeOfBusiness(resultSet.getString("type_of_business"));
                        building.setEmail(resultSet.getString("email"));
                        building.setRentalStartDate(resultSet.getDate("rental_start_date"));
                        building.setRentalEndDate(resultSet.getDate("rental_end_date"));
                        buildings.add(building);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildings;
    }

    // User property operations are carried out from here
    public void propertySearch(String emailAddress,String password){

        Scanner scanner = new Scanner(System.in);
        while(true){
            
            System.out.println("Enter the location:");
            String location = scanner.nextLine();
            if(location.equals(""))
            {System.out.println("Location is a required field. Please re-enter your location preference.");
            continue;}

            System.out.println("Enter your preferred date (YYYY/MM/DD):");
            String date = scanner.nextLine();
            if(date.equals("")){
                System.out.println("Date is a required field. Please re-enter your preferred move-in date.");
                continue;
            }
            System.out.println("Enter the property type (house/apartment/commercial_building):");
            String propertyType = scanner.nextLine();
            System.out.println("Are you looking for a rental or sale property?: 1)Rent 2)Sale");
            int rent_or_sale=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter the number of bedrooms limit:");
            int bedroomsLimit = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the minimum price:");
            int minPrice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the maximum price:");
            int maxPrice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Order results by (price/bedrooms):");
            String orderBy = scanner.nextLine();

            searchProperties(location, date, propertyType, bedroomsLimit, minPrice, maxPrice, orderBy,rent_or_sale);
            askForBooking(emailAddress,password);
            // UserDao user = new UserDao();
            // user.renterOperations(scanner, emailAddress, password);
            System.out.println("Do you want to look for more properties?");
            String r=scanner.next();
            
            if(r.toLowerCase().equals("yes"))
            continue;
            else {
                
                break;
            }
        }
    }

    public static void searchProperties(String location, String date, String propertyType, int bedroomsLimit,
                                        int minPrice, int maxPrice, String orderBy,int rent_or_sale) {

        try(Connection connection = DatabaseConnector.getConnection()) {
            String tableName = propertyType.equals("house") ? "House"
                    : propertyType.equals("apartment") ? "Apartment"
                    : "Commercial_building";
            String query="";
            if(rent_or_sale==2){
                        query = "SELECT * FROM " + tableName + " WHERE location = ? AND rental_start_date <= ?" +
                            " AND (rental_end_date = ? AND rental_end_date IS NULL)" +
                            " AND number_of_rooms <= ? AND CAST(price AS integer) >= ? AND CAST(price AS integer) <= ?" +
                            " ORDER BY " + (orderBy.equals("price") ? "CAST(price AS integer)" : "number_of_rooms");

            }
            else{
                query = "SELECT * FROM " + tableName + " WHERE location = ? AND rental_start_date <= ? AND rental_end_date >= ? " +
                            " AND number_of_rooms <= ? AND CAST(price AS integer) >= ? AND CAST(price AS integer) <= ?" +
                            " ORDER BY " + (orderBy.equals("price") ? "CAST(price AS integer)" : "number_of_rooms");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String rentStart;
            java.util.Date rsDate = null;
            java.sql.Date rent_date;
            DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
            //move_in_date = scanner.nextLine();
            while(rsDate == null)
            {
                try
                {
                    rentStart = date;
                    rsDate =  df1.parse(rentStart);
                }
                catch(ParseException e)
                {
                    System.out.println("Please enter a valid date! Format is yyyy/mm/dd");
                }
            }
            rent_date = new java.sql.Date(rsDate.getTime());
            preparedStatement.setString(1, location);
            preparedStatement.setDate(2, rent_date);
            preparedStatement.setDate(3, rent_date);
            preparedStatement.setInt(4, bedroomsLimit);
            preparedStatement.setInt(5, minPrice);
            preparedStatement.setInt(6, maxPrice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int propertyId = resultSet.getInt("property_id");
                int price = resultSet.getInt("price");
                int bedrooms = resultSet.getInt("number_of_rooms");
                String propertyTypeResult = resultSet.getString("type");
                String description = resultSet.getString("description");

                System.out.printf("Property_id: %d, Price: %d, Bedrooms: %d, Property Type: %s, Description: %s%n",
                        propertyId,price, bedrooms, propertyTypeResult, description);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e) {
                e.printStackTrace();
        }
    }


    public void askForBooking(String emailAddress,String password){
        System.out.println("Do you want to book a property?");
        Scanner sc=new Scanner(System.in);
        String response=sc.nextLine();
        while(true){
            if(response.toLowerCase().equals("yes")){
                System.out.println("Enter the property id of the property you are willing to book.");
                int p_id=sc.nextInt();
                sc.nextLine();
                try(Connection connection = DatabaseConnector.getConnection()) {
                String sql = "SELECT property_id FROM Property WHERE property_id = ?";
                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    pstmt.setInt(1, p_id);
                    ResultSet resultSet = pstmt.executeQuery();
                    String sql1 = "SELECT property_id FROM books WHERE property_id = ?";
                    PreparedStatement pstmt1 = connection.prepareStatement(sql1);
                    pstmt1.setInt(1, p_id);
                    ResultSet resultSet1 = pstmt1.executeQuery();
                    if(!resultSet.next())
                    {
                        System.out.println("There is no property with the given id. Please enter a valid property id");
                        continue;
                    }
                    else if(resultSet1.next())
                    {
                        System.out.println("This property is no longer available. Please enter another property id");
                        continue;
                    }
                    else{
                        System.out.println("Would you like to use a saved payment method?");
                        String resp=sc.nextLine();
                        if(resp.toLowerCase().equals("yes")){
                            String sql2 = "SELECT * from renter_card_address where email=?";
                            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                            pstmt2.setString(1, emailAddress);
                            ResultSet resultSet2 = pstmt2.executeQuery();
                            if(resultSet2.next())
                            {
                                System.out.println("These are your saved cards. Enter the credit_card number you want to use.");
                                String sql3 = "SELECT * from renter_card_address where email=?";
                                PreparedStatement pstmt3 = connection.prepareStatement(sql3);
                                pstmt3.setString(1, emailAddress);
                                ResultSet resultSet3= pstmt3.executeQuery(); 

                                while (resultSet3.next()) {
                                    String credit_card=resultSet3.getString("credit_card");
                                    String payment_address = resultSet3.getString("payment_address");
                                    System.out.printf("Credit Card: %s, Payment address: %s%n",
                                            credit_card,payment_address);
                                }
                                System.out.println("Enter the credit card number you want to choose.");
                                String card_entered=sc.nextLine();
                        
                                String sql4 = "insert into books values(?,?,?)";
                                PreparedStatement pstmt4 = connection.prepareStatement(sql4);
                                pstmt4.setString(1, emailAddress);
                                pstmt4.setString(3, card_entered);
                                pstmt4.setInt(2, p_id);
                                pstmt4.executeUpdate(); 

                                String sql01= "update house set availability='no' where property_id=?";
                                PreparedStatement pstmt01 = connection.prepareStatement(sql01);
                                pstmt01.setInt(1, p_id);
                                pstmt01.executeUpdate(); 

                                String sql102= "update apartment set availability='no' where property_id=?";
                                PreparedStatement pstmt102 = connection.prepareStatement(sql102);
                                pstmt102.setInt(1, p_id);
                                pstmt102.executeUpdate();
                        
                                String sql103= "update commercial_building set availability='no' where property_id=?";
                                PreparedStatement pstmt103 = connection.prepareStatement(sql103);
                                pstmt103.setInt(1, p_id);
                                pstmt103.executeUpdate();
                                int id=0,price=0;
                                java.sql.Date re=null,rs=null;
                                



                                String sql0= "Select property_id,price,rental_start_date,rental_end_date from house where property_id=?";
                            PreparedStatement pstmt0 = connection.prepareStatement(sql0);
                            pstmt0.setInt(1, p_id);
                            ResultSet resultSet0 = pstmt0.executeQuery();
                            while(resultSet0.next()){
                                id=resultSet0.getInt("property_id");
                                 rs=resultSet0.getDate("rental_start_date");
                                re=resultSet0.getDate("rental_end_date");
                                 price=resultSet0.getInt("price");
                            }
                             String sql00= "Select property_id,price,rental_start_date,rental_end_date from apartment where property_id=?";
                            PreparedStatement pstmt00 = connection.prepareStatement(sql00);
                            pstmt00.setInt(1, p_id);
                            ResultSet resultSet00 = pstmt00.executeQuery();
                            while(resultSet00.next()){
                                 id=resultSet00.getInt("property_id");
                                 rs=resultSet00.getDate("rental_start_date");
                              re=resultSet00.getDate("rental_end_date");
                                price=resultSet00.getInt("price");
                            }
                             String sql02= "Select property_id,price,rental_start_date,rental_end_date from commercial_building where property_id=?";
                            PreparedStatement pstmt02 = connection.prepareStatement(sql02);
                            pstmt02.setInt(1, p_id);
                            ResultSet resultSet02 = pstmt02.executeQuery();
                            while(resultSet02.next()){
                                 id=resultSet02.getInt("property_id");
                                rs=resultSet02.getDate("rental_start_date");
                                re=resultSet02.getDate("rental_end_date");
                                 price=resultSet02.getInt("price");
                            }


                            int programid=0;
                        double rewardpoints=0;
                        String sqll= "Select program_id,reward_point_count from reward_program where email=? ";
                            PreparedStatement pstmtl = connection.prepareStatement(sqll);
                            pstmtl.setString(1, emailAddress);
                            ResultSet resultSetl = pstmtl.executeQuery();
                            while(resultSetl.next()){
                                 programid=resultSetl.getInt("program_id");
                                 rewardpoints=resultSetl.getDouble("reward_point_count")+price;
                            }
                       
                               String sqlw= "update reward_program set reward_point_count= ? where program_id=?";
                            PreparedStatement pstmtw = connection.prepareStatement(sqlw);
                            
                            pstmtw.setDouble(1, rewardpoints);
                            pstmtw.setInt(2, programid);
                            
                                pstmtw.executeUpdate(); 

                            String sqlr= "insert into makes_points values(?,?,?,?,?)";
                            PreparedStatement pstmtr = connection.prepareStatement(sqlr);
                            
                            pstmtr.setInt(2, p_id);
                            pstmtr.setString(1, emailAddress);
                              pstmtr.setString(3, card_entered);
                               pstmtr.setInt(4, programid);
                                pstmtr.setDouble(5, rewardpoints);
                                pstmtr.executeUpdate();
                                 
                                
                            


                            System.out.printf("Your booking for property id %d has been confirmed using the credit card %s.The price of the property is %d, the rental start date is %s and the rental end date is %s.",id,card_entered,price,rs,re);














                        




                                return;

                            }

                            if(!resultSet2.next()){
                                System.out.println("You do not have any saved cards. Please save a card to proceed further!");
                                System.out.println("Enter your credit card number:");
                                String nc1=sc.nextLine();
                                System.out.println("Enter your billing/payment address:");
                                String na1=sc.nextLine();

                                String sql9= "insert into renter_card_address values(?,?,?)";
                                PreparedStatement pstmt9 = connection.prepareStatement(sql9);
                                pstmt9.setString(1, emailAddress);
                                pstmt9.setString(2, nc1);
                                pstmt9.setString(3, na1);
                                pstmt9.executeUpdate();
                                String sql10 = "insert into books values(?,?,?)";
                                PreparedStatement pstmt10 = connection.prepareStatement(sql10);
                                pstmt10.setString(1, emailAddress);
                                pstmt10.setString(3, nc1);
                                pstmt10.setInt(2, p_id);
                                pstmt10.executeUpdate(); 

                                String sql11= "update house set availability='no' where property_id=?";
                                PreparedStatement pstmt11 = connection.prepareStatement(sql11);
                                pstmt11.setInt(1, p_id);
                                pstmt11.executeUpdate(); 

                                String sql12= "update apartment set availability='no' where property_id=?";
                                PreparedStatement pstmt12 = connection.prepareStatement(sql12);
                                pstmt12.setInt(1, p_id);
                                pstmt12.executeUpdate();
                        
                                String sql13= "update commercial_building set availability='no' where property_id=?";
                                PreparedStatement pstmt13 = connection.prepareStatement(sql13);
                                pstmt13.setInt(1, p_id);
                                pstmt13.executeUpdate();

                                
                                int id=0,price=0;
                                java.sql.Date re=null,rs=null;
                                



                                String sql0= "Select property_id,price,rental_start_date,rental_end_date from house where property_id=?";
                            PreparedStatement pstmt0 = connection.prepareStatement(sql0);
                            pstmt0.setInt(1, p_id);
                            ResultSet resultSet0 = pstmt0.executeQuery();
                            while(resultSet0.next()){
                                id=resultSet0.getInt("property_id");
                                 rs=resultSet0.getDate("rental_start_date");
                                re=resultSet0.getDate("rental_end_date");
                                 price=resultSet0.getInt("price");
                            }
                             String sql00= "Select property_id,price,rental_start_date,rental_end_date from apartment where property_id=?";
                            PreparedStatement pstmt00 = connection.prepareStatement(sql00);
                            pstmt00.setInt(1, p_id);
                            ResultSet resultSet00 = pstmt00.executeQuery();
                            while(resultSet00.next()){
                                 id=resultSet00.getInt("property_id");
                                 rs=resultSet00.getDate("rental_start_date");
                              re=resultSet00.getDate("rental_end_date");
                                price=resultSet00.getInt("price");
                            }
                             String sql02= "Select property_id,price,rental_start_date,rental_end_date from commercial_building where property_id=?";
                            PreparedStatement pstmt02 = connection.prepareStatement(sql02);
                            pstmt02.setInt(1, p_id);
                            ResultSet resultSet02 = pstmt02.executeQuery();
                            while(resultSet02.next()){
                                 id=resultSet02.getInt("property_id");
                                rs=resultSet02.getDate("rental_start_date");
                                re=resultSet02.getDate("rental_end_date");
                                 price=resultSet02.getInt("price");
                            }


                            int programid=0;
                        double rewardpoints=0;
                        String sqll= "Select program_id,reward_point_count from reward_program where email=? ";
                            PreparedStatement pstmtl = connection.prepareStatement(sqll);
                            pstmtl.setString(1, emailAddress);
                            ResultSet resultSetl = pstmtl.executeQuery();
                            while(resultSetl.next()){
                                 programid=resultSetl.getInt("program_id");
                                 rewardpoints=resultSetl.getDouble("reward_point_count")+price;
                            }
                       
                               String sqlw= "update reward_program set reward_point_count= ? where program_id=?";
                            PreparedStatement pstmtw = connection.prepareStatement(sqlw);
                            
                            pstmtw.setDouble(1, rewardpoints);
                            pstmtw.setInt(2, programid);
                            
                                pstmtw.executeUpdate(); 

                            String sqlr= "insert into makes_points values(?,?,?,?,?)";
                            PreparedStatement pstmtr = connection.prepareStatement(sqlr);
                            
                            pstmtr.setInt(2, p_id);
                            pstmtr.setString(1, emailAddress);
                              pstmtr.setString(3, nc1);
                               pstmtr.setInt(4, programid);
                                pstmtr.setDouble(5, rewardpoints);
                                pstmtr.executeUpdate();
                                 
                                
                            


                            System.out.printf("Your booking for property id %d has been confirmed using the credit card %s.The price of the property is %d, the rental start date is %s and the rental end date is %s.",id,nc1,price,rs,re);





                                return;
                            }
                        }   
                    
                        
                        else{
                            System.out.println("Enter your credit card number:");
                            String nc=sc.nextLine();
                            System.out.println("Enter your billing/payment address:");
                            String na=sc.nextLine();

                            String sql8= "insert into renter_card_address values(?,?,?)";
                            PreparedStatement pstmt8 = connection.prepareStatement(sql8);
                            pstmt8.setString(1, emailAddress);
                            pstmt8.setString(2, nc);
                            pstmt8.setString(3, na);
                            pstmt8.executeUpdate();
                            String sql6 = "insert into books values(?,?,?)";
                            PreparedStatement pstmt6 = connection.prepareStatement(sql6);
                            pstmt6.setString(1, emailAddress);
                            pstmt6.setString(3, nc);
                            pstmt6.setInt(2, p_id);
                            pstmt6.executeUpdate(); 

                            String sql111= "update house set availability='no' where property_id=?";
                            PreparedStatement pstmt111 = connection.prepareStatement(sql111);
                            pstmt111.setInt(1, p_id);
                            pstmt111.executeUpdate(); 

                            String sql112= "update apartment set availability='no' where property_id=?";
                            PreparedStatement pstmt112 = connection.prepareStatement(sql112);
                            pstmt112.setInt(1, p_id);
                            pstmt112.executeUpdate();
                    
                            String sql113= "update commercial_building set availability='no' where property_id=?";
                            PreparedStatement pstmt113 = connection.prepareStatement(sql113);
                            pstmt113.setInt(1, p_id);
                            pstmt113.executeUpdate();
                    

                            int id=0,price=0;
                            java.sql.Date re=null,rs=null;
                            



                            String sql0= "Select property_id,price,rental_start_date,rental_end_date from house where property_id=?";
                        PreparedStatement pstmt0 = connection.prepareStatement(sql0);
                        pstmt0.setInt(1, p_id);
                        ResultSet resultSet0 = pstmt0.executeQuery();
                        while(resultSet0.next()){
                            id=resultSet0.getInt("property_id");
                             rs=resultSet0.getDate("rental_start_date");
                            re=resultSet0.getDate("rental_end_date");
                             price=resultSet0.getInt("price");
                        }
                         String sql00= "Select property_id,price,rental_start_date,rental_end_date from apartment where property_id=?";
                        PreparedStatement pstmt00 = connection.prepareStatement(sql00);
                        pstmt00.setInt(1, p_id);
                        ResultSet resultSet00 = pstmt00.executeQuery();
                        while(resultSet00.next()){
                             id=resultSet00.getInt("property_id");
                             rs=resultSet00.getDate("rental_start_date");
                          re=resultSet00.getDate("rental_end_date");
                            price=resultSet00.getInt("price");
                        }
                         String sql02= "Select property_id,price,rental_start_date,rental_end_date from commercial_building where property_id=?";
                        PreparedStatement pstmt02 = connection.prepareStatement(sql02);
                        pstmt02.setInt(1, p_id);
                        ResultSet resultSet02 = pstmt02.executeQuery();
                        while(resultSet02.next()){
                             id=resultSet02.getInt("property_id");
                            rs=resultSet02.getDate("rental_start_date");
                            re=resultSet02.getDate("rental_end_date");
                             price=resultSet02.getInt("price");
                        }


                        int programid=0;
                    double rewardpoints=0;
                    String sqll= "Select program_id,reward_point_count from reward_program where email=? ";
                        PreparedStatement pstmtl = connection.prepareStatement(sqll);
                        pstmtl.setString(1, emailAddress);
                        ResultSet resultSetl = pstmtl.executeQuery();
                        while(resultSetl.next()){
                             programid=resultSetl.getInt("program_id");
                             rewardpoints=resultSetl.getDouble("reward_point_count")+price;
                        }
                   
                           String sqlw= "update reward_program set reward_point_count= ? where program_id=?";
                        PreparedStatement pstmtw = connection.prepareStatement(sqlw);
                        
                        pstmtw.setDouble(1, rewardpoints);
                        pstmtw.setInt(2, programid);
                        
                            pstmtw.executeUpdate(); 

                        String sqlr= "insert into makes_points values(?,?,?,?,?)";
                        PreparedStatement pstmtr = connection.prepareStatement(sqlr);
                        
                        pstmtr.setInt(2, p_id);
                        pstmtr.setString(1, emailAddress);
                          pstmtr.setString(3, nc);
                           pstmtr.setInt(4, programid);
                            pstmtr.setDouble(5, rewardpoints);
                            pstmtr.executeUpdate();
                             
                            
                        


                        System.out.printf("Your booking for property id %d has been confirmed using the credit card %s.The price of the property is %d, the rental start date is %s and the rental end date is %s.",id,nc,price,rs,re);


                            return;


                        }
                    }

                    
                //  resultSet.close();
                //  preparedStatement.close();
                    connection.close();
                    sc.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Ok..! See you later in the booking section.");
                break;
            }
        }
    }
    
}
