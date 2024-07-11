package Hotel;

public class Result {
    
    public enum COMMON_ERRORS{
        ER_SUCCESSFUL               (0, "Success."),
        ER_NO_HOTEL                 (1, "Hotel does not exist."),
        ER_NO_ROOM                  (2, "Room does not exist."),
        ER_NO_RESERVATION           (3, "Reservation does not exist."),
        ER_EMPTY_RESERVATION_LIST   (4, "Reservation list is empty."),
        ER_ROOM_HAS_RESERVATION     (5, "Room has a reservation."),
        ER_HOTEL_HAS_RESERVATION    (6, "Hotel has a reservation."),
        ER_LOWER_THAN_BASEPRICE     (7, "Price is lower than 100."),
        ER_EXISTING_OLD_NAME        (8, "Existing old name exists."),
        ER_NOT_UNIQUE_GIVENNAME     (9, "Given name is not unique within the list."),
        ER_MAX_CAPACITY             (10, "Hotel is at Max."),
        ER_EXISTING_DISCOUNT        (11, "Reservation already has an existing discount."),
        ER_STAY4_GET1_INVALID       (12, "Invalid code, reservation must be at least 5 days long."),
        ER_PAYDAY_INVALID           (13, "Invalid code, reservation must cover at least the 15th or the 30th day of the month (excluding check-out)."),
        ER_INVALID_DAY              (14, "Invalid day of the month."),
        ER_INVALID_PRICE_RATE       (15, "Invalid price rate.");

        private int errorID;
        private String errorMessage;

        private COMMON_ERRORS(int initNumberID, String initErrorMessage) {
            this.errorID = initNumberID;
            this.errorMessage = initErrorMessage;
        }

        public int getErrorID() {
            return errorID;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

    }

    private Boolean isSucessful;
    private String stateString;
    private COMMON_ERRORS commonError;

    Result(){
        this.isSucessful = true;
        this.stateString = new String("");
        this.commonError = COMMON_ERRORS.ER_SUCCESSFUL;
    }
    
    Result(COMMON_ERRORS error){
        this.stateString = error.getErrorMessage();
        this.commonError = error;
        this.isSucessful = error.getErrorMessage().equals(COMMON_ERRORS.ER_SUCCESSFUL.getErrorMessage());
    }

    Result(String errorString){
        if (errorString.equals("")){
            this.isSucessful = true;
            this.stateString = new String("");
        } else {
            this.isSucessful = false;
            this.stateString = new String(errorString);
        }
    }

    Result(String message, Boolean isSuccess){
        this.isSucessful = isSuccess;
        this.stateString = new String(message);
    }    

    public Boolean isSuccesful(){
        return this.isSucessful;
    }

    public String getMessage(){
        return this.stateString;
    }

    public COMMON_ERRORS getCommonError() {
        return commonError;
    }
}
