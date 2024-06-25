package Hotel;

public class HotelState {
    private String hotelName;
    private Boolean hasDelete;

    HotelState(String newInput) {
        this.hotelName = new String(newInput);
        this.hasDelete = false;
    }

    public String getHotelName() {
        return this.hotelName;
    }   

    public Boolean getHasDelete() {
        return hasDelete;
    }

    public void setHotelName(String newInput) {
        this.hotelName = newInput;
    }

    public void setHasDelete(Boolean hasDelete) {
        this.hasDelete = hasDelete;
    }
}
