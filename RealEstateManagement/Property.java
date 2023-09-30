public class Property {
    public int propertyId;
    public String type;
    
    Property(int propertyId, String type){
        this.propertyId = propertyId;
        this.type = type;
    }

    public void setPropertyId(int propertyId){
        this.propertyId = propertyId;
    }

    public int getPropertyId(){
        return this.propertyId;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
