public class CommercialBuilding extends Property{
    String description;
    double squareFootage;
    String availability;
    String price;
    String location;
    String city;
    String state;
    String typeOfBusiness;
    String email;
    java.sql.Date rental_start_date;
    java.sql.Date rental_end_date;

    CommercialBuilding(int propertyId, String type, String description, double squareFootage, String availability, String price,
            String location, String city, String state, String typeOfBusiness, String email, java.sql.Date rental_start_date, 
            java.sql.Date rental_end_date){
        super(propertyId, type);
        this.description = description;
        this.squareFootage = squareFootage;
        this.availability = availability;
        this.price = price;
        this.location = location;
        this.city = city;
        this.state = state;
        this.typeOfBusiness = typeOfBusiness;
        this.email = email;
        this.rental_start_date = rental_start_date;
        this.rental_end_date = rental_end_date;
    }
    
    public CommercialBuilding() {
        super(1901, "commercial_building");
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setSquareFootage(double squareFootage){
        this.squareFootage = squareFootage;
    }

    public double getSquareFootage(){
        return squareFootage;
    }

    public void setAvailability(String availability){
        this.availability = availability;
    }

    public String getAvailability(){
        return availability;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public void setTypeOfBusiness(String typeOfBusiness){
        this.typeOfBusiness = typeOfBusiness;
    }

    public String getTypeOfBusiness(){
        return this.typeOfBusiness;
    }

    public String getNumberOfRooms(){
        return typeOfBusiness;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setRentalStartDate(java.sql.Date rental_start_date){
        this.rental_start_date = rental_start_date;
    }

    public java.sql.Date getRentalStartDate(){
        return rental_start_date;
    }

    public void setRentalEndDate(java.sql.Date rental_end_date){
        this.rental_end_date = rental_end_date;
    }

    public java.sql.Date getRentalEndDate(){
        return rental_end_date;
    }

    @Override
    public String toString() {
        return "House{" +
                "propertyId=" + propertyId +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", squareFootage=" + squareFootage +
                ", availability='" + availability + '\'' +
                ", price='" + price + '\'' +
                ", location='" + location + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", numberOfRooms=" + typeOfBusiness +
                ", email='" + email + '\'' +
                ", rentalStartDate=" + rental_start_date +
                ", rentalEndDate=" + rental_end_date +
                '}';
    }
}