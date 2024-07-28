package Hotel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code View} class handles user interaction and input/output operations for the Hotel Reservation System.
 */
public class View {

    // Constants for main menu options
    public static final char CREATE_HOTEL_OPTION = 'C',
                             VIEW_HOTEL_OPTION = 'V',
                             MANAGE_HOTEL_OPTION = 'M',
                             BOOK_RESERVATION_OPTION = 'B',
                             QUIT_OPTION = 'Q';

    // Constants for hotel information levels
    public static final char HIGH_LEVEL_OPTION = 'H',
                             LOW_LEVEL_OPTION = 'L';

    // Constants for selected options
    public static final char SELECTED_DATE_OPTION = '1',
                             SELECTED_ROOM_OPTION = '2',
                             SELECTED_RESERVATION_OPTION = '3';
    
    // System date constants
    public static final int  SYSTEM_MONTH = 7,
                             SYSTEM_YEAR = 2024;


     /**
     * Enum representing various manager states for managing hotels.
     */
    public enum MANAGER_STATE {
        MS_CHOSE_HOTEL(0),
        MS_OVERVIEW(1),
        MS_CHANGE_NAME(2),
        MS_ADD_ROOMS(3),
        MS_REMOVE_ROOMS(4),
        MS_UPDATE_PRICE(5),
        MS_REMOVE_RESERVATIONS(6),
        MS_REMOVE_HOTEL(7);

        private int numberID;

        private MANAGER_STATE(int numberID) {
            this.numberID = numberID;
        }

        public int getID() {
            return this.numberID;
        }

    }


    /**
     * Enum representing various states for simulating booking.
     */
    public enum SIMULATE_BOOKING {
        SB_HOTEL_SELECTION(0),
        SB_OVERVIEW(1),
        SB_DATE_SELECTION(2),
        SB_ROOM_SELECTION(3),
        SB_GUEST_SELECTION(4),
        SB_RESERVATION_CONFIRMATION(5);

        private int numberID;

        private SIMULATE_BOOKING(int numberID) {
            this.numberID = numberID;
        }

        public int getID() {
            return this.numberID;
        }

    } 

    private final Scanner scanner;


    /**
     * Constructs a View object and initializes the scanner.
     */
    public View() {
        this.scanner = new Scanner(System.in);
    }

    

    // ### 1. INPUT GETTERS
    /**
     * Prompts the user for a character input.
     * @param prompt the prompt message
     * @return the user's input character
     */
    public char getInputChar(String prompt) {
        String s;
        do {
            System.out.print(prompt + " ");
            s = scanner.nextLine();
            displayInvalidInputWarning(!s.isEmpty(), "Please provide a character response!");
        } while (s.isEmpty());
        return s.toUpperCase().charAt(0);
    }


    /**
     * Prompts the user for a string input.
     * @param prompt the prompt message
     * @return the user's input string
     */
    public String getInputStr(String prompt) {
        String s;
        do {
            System.out.print(prompt + " ");
            s = scanner.nextLine();
            displayInvalidInputWarning(!s.isEmpty(), "Please provide a string response!");
        } while (s.isEmpty());
        return s;
    }


    /**
     * Prompts the user for an integer input.
     * @param prompt the prompt message
     * @return the user's input integer
     */
    public int getInputInt(String prompt) {
        System.out.print(prompt + " ");
        do {
            displayInvalidInputWarning(scanner.hasNextInt(), "Please provide a numerical response!");
            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.print(prompt + " ");
            }
        } while (!scanner.hasNextInt());

        int i = scanner.nextInt();
        scanner.nextLine();
        return i;
    }


    /**
     * Prompts the user for a double input.
     * @param prompt the prompt message
     * @return the user's input double
     */
    public double getInputDouble(String prompt) {
        System.out.print(prompt + " ");
        do {
            displayInvalidInputWarning(scanner.hasNextDouble(), "Please provide a numerical response!");
            if (!scanner.hasNextDouble()) {
                scanner.nextLine();
                System.out.print(prompt + " ");
            }
        } while (!scanner.hasNextDouble());

        double d = scanner.nextDouble();
        scanner.nextLine();
        return d;
    }


    public LocalDate getLocalDate(String prompt) {
        // Boolean isNotValidDate = true;
        int day = 0;
        int year = SYSTEM_YEAR;
        int month = SYSTEM_MONTH;
        do{
        System.out.println(prompt + " ");
        System.out.print("Day: ");
        day = scanner.nextInt();
        } while (day > 31 || day < 1);
        return LocalDate.of(year, month, day);
    }

    // ### 2. UTILITY FUNCTIONS
    /**
     * Displays a divider line.
     */
    public void displayDivider() {
        System.out.println("\n========================================");
    }


    /**
     * Displays a message to the user.
     * @param message the message to display
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }


    /**
     * Displays a result message to the user.
     * @param result the result message to display
     */
    public void displayResultMessage(String result) {
        displayDivider();
        System.out.println("\n" + result);
        pressEnterToContinue();
    }


    /**
     * Displays a general invalid input warning.
     */

    public void displayManageHotelPrompt(MANAGER_STATE displayState) {
        final String[][] promptManageHotel = {
            {
                "",
                "Welcome to Hotel Manager.",
                "Please input the name of the hotel.",
                ""
            },{ // 0 : Hotel
                "Hotel Manager", 
                "Please Choose the following Managing actions:",
                "",
                " [a] Change the name of the Hotel",
                " [b] Add Rooms",
                " [c] Remove Rooms",
                " [d] Update the Base Price for a Room",
                " [e] Remove Reservation",
                " [f] Remove Hotel",
                " [q] Quit",
                ""
            }, {
                "## Change Name of the Hotel",
                "Please provide a valid name of the hotel you want to change the name."
            }, {    // 1 : Change
                "## Add a Room(s)",
                "Adds a room to to the selected hotel.",
                "Please provide the correct name for the additional room."
            }, {
                "## Remove a Room(s)",
                "Please provide the room you want to remove.",
                "Make sure that it does not have a reservation"
            }, {
                "## Update the Base Price",
                "Please input the price you want to change. Change should be greater than P100."
            }, {
                "## Remove Reservation"
            }, {
                "## Remove Hotel"
            }
        };
        clearScreen();
        displayDivider();
        System.out.println("\n");
        for (String sentence : promptManageHotel[displayState.getID()]){
            System.out.println(sentence);
        }
        displayDivider();

    }

    public void displayInvalidInputWarning() {
        System.out.println("Invalid input! ");
    }

    public void displayInvalidInputWarning(boolean isValidInput, String warning) {
        if (isValidInput) return;
        System.out.println("Invalid input! " + warning);

    }


    /**
     * Clears the screen by printing several newline characters.
     */
    public void clearScreen(){
        if (Debug.CLEAR_CONSOLE)
            System.out.println("\033\143");
        else
            System.out.println("\n\n\n\n\n");
    }


    /**
     * Prompts the user to press 'Enter' to continue.
     */
    public void pressEnterToContinue() {
        System.out.print("Press 'Enter' to continue.");
        scanner.nextLine();
    }


    /**
     * Prompts the user to confirm their input.
     * @return true if the user inputs 'Y'; false if the user inputs 'N'
     */
    public boolean confirmUserAction(String action) {
        while (true) {
            System.out.print("\nDo you want to confirm your action of " + action + " (Y/N)? ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("Y"))
                return true;
            else if (userInput.equalsIgnoreCase("N"))
                return false;
        }
    }


    // ### 3. LIST PRINTERS
    /**
     * Displays the list of hotels for selection.
     * @param hotelList the list of hotels to display
     */
    public void displayHotelSelection(ArrayList<Hotel> hotelList) {
        if (hotelList.isEmpty())
            System.out.println("\nNo hotel currently exists.");
        else {
            System.out.println("\nList of hotels:");
            int i = 1;
            for (Hotel hotel : hotelList) {
                System.out.println(i + ".) " + hotel.getName());
                ++i;
            }
        }
        displayDivider();
    }


    /**
     * Displays the list of rooms for selection.
     * @param roomList the list of rooms to display
     */
    public void displayRoomSelection(String hotelName, ArrayList<Room> roomList) {
        if (roomList.isEmpty())
            System.out.println("\nNo room currently exists.");
        else {
            System.out.println("\nSelected hotel: " + hotelName);
            System.out.println("\nList of rooms:");
            int i = 1;
            for (Room room : roomList) {
                if (i % 5 == 1)
                    System.out.print("\n");
                System.out.print(room.getName());
                if (i % 5 != 0)
                    System.out.print(" | ");
                ++i;
            }
            System.out.print("\n");
        }
    }


    /**
     * Displays the list of reservations for selection.
     * @param reservationList the list of reservations to display
     */
    public void displayReservationSelection(String hotelName, String roomName, ArrayList<Reservation> reservationList) {

        System.out.println("\nSelected hotel: " + hotelName);
        System.out.println("\nSelected room: " + roomName);

        if (reservationList.isEmpty())
            System.out.println("\nNo reservation currently exists.");
        else {
            System.out.println("\nList of reservations:");
            int i = 1;
            for (Reservation reservation : reservationList) {
                System.out.println(i + ".)");
                System.out.println("Check-in date: " + reservation.getCheckInDate());
                System.out.println("Room: " + reservation.getRoom());
                ++i;
            }
        }

        displayDivider();
    }


    /**
     * Displays the list of rooms with their details.
     * @param roomList the list of rooms to display
     */
    public void displayRoomList(Hotel hotel, ArrayList<Room> roomList) {

        if(roomList.isEmpty()){
            displayDivider();
            System.out.println("No Rooms currently exist.");
        } else {
            int i = 0;
            for (Room room : roomList) {
                double basePricePerNight = hotel.getBasePricePerNight();
                double multiplier = switch(room) {
                    case StandardRoom r -> hotel.getStandardMultiplier();
                    case DeluxeRoom r -> hotel.getDeluxeMultiplier();
                    case ExecutiveRoom r -> hotel.getExecutiveMultiplier();
                    case null, default -> 0f;
                };
                displayDivider();
                System.out.println(i + 1 + ".)");
                assert room != null;
                System.out.println("Room: " + room.getName());
                System.out.println("Class: " + room.getClass());
                System.out.println("Base Price: ₱" + (basePricePerNight * multiplier));
                ++i;
            }
        }
    }


    // ### 4. FORMATTED INFORMATION PRINTERS
    /**
     * Displays the high-level hotel information.
     * @param hotel the hotel to display
     * @param estimatedEarnings the estimated earnings of the hotel
     */
    public void displayHighLevelHotelInfo(Hotel hotel, double estimatedEarnings) {
        if (hotel == null)
            System.out.println("Error! Hotel nonexistent!");
        displayDivider();
        System.out.println("\nHotel name: " + hotel.getName());
        System.out.println("Total number of rooms: " + hotel.getTotalRooms());
        System.out.println("Estimated earnings: " + estimatedEarnings);
    }


    /**
     * Displays the selected date information for a hotel.
     * @param hotelName the name of the hotel
     * @param availableRooms the number of available rooms
     * @param unavailableRooms the number of unavailable rooms
     */
    public void displaySelectedDateInfo(String hotelName, int availableRooms, int unavailableRooms) {
        displayDivider();
        System.out.println("\nHotel: " + hotelName);
        System.out.println("Available rooms: " + availableRooms);
        System.out.println("Unavailable rooms: " + unavailableRooms);
    }


    /**
     * Displays the selected room information.
     * @param roomName name of the room
     * @param roomType type of the room
     */
    public void displaySelectedRoomInfo(String roomName, String roomType) {
        displayDivider();
        System.out.printf("\nName: %s", roomName);
        System.out.printf("\nType: %s\n", roomType);
        System.out.println("\n" );
    }


    /**
     * Displays the selected reservation information.
     */
    public void displaySelectedReservationInfo(String guestName, int checkInDay, int checkOutDay, String priceBreakDown, double totalPrice) {
        displayDivider();
        System.out.println("\nGuest: " + guestName);
        System.out.println("Check-in: July " + checkInDay + ", 2024");
        System.out.println("Check-out: July " + checkOutDay + ", 2024");
        System.out.println("Price breakdown:" + priceBreakDown);
        System.out.println("Total price: " + totalPrice);
    }

    /**
     * Displays the reservation information for a list of reservations.
     * @param reservationList the list of reservations to display
     * 
    */
    public void displayReservationInformation(ArrayList<Reservation> reservationList) {
        if (reservationList.isEmpty()) {
            displayDivider();
            System.out.println("\nNo reservations currently exist.");
        } else {
            int i = 1;
            for (Reservation reservation : reservationList) {
                displayDivider();
                System.out.println(i + ".)");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room: " + reservation.getRoom().getName());
                System.out.println("Check-in: " + reservation.getCheckInDate());
                System.out.println("Check-out: " + reservation.getCheckOutDate());
                ++i;
            }
        }
    }

    // ### 5. PROMPT PRINTERS
    /**
     * Displays the main action prompt to the user.
     */
    public void displayMainActionPrompt() {
        displayDivider();
        System.out.println("\nWelcome to the Hotel Reservation System!");
        displayDivider();
        System.out.println("\n[C]reate Hotel");
        System.out.println("[V]iew Hotel");
        System.out.println("[M]anage Hotel");
        System.out.println("[B]ook Reservation");
        System.out.println("[Q]uit Program");
        displayDivider();
    }


    /**
     * Displays the create hotel prompt to the user.
     */
    public void displayCreateHotelPrompt() {
        displayDivider();
        System.out.println("\nYou are creating a hotel. Enter \"quit\" to exit the hotel creation page.");
        displayDivider();
    }


    /**
     * Displays the high-level hotel information prompt.
     * @param hasSelectedHotel true if a hotel is selected; false otherwise
     * @param hotelName the name of the selected hotel
     */
    public void displayHighLevelHotelInfoPrompt(boolean hasSelectedHotel, String hotelName) {
        if (hasSelectedHotel)
            System.out.println("\nSelected hotel: " + hotelName + "\n");
        System.out.println("[H]igh-level information");
        System.out.println("\t- Hotel name");
        System.out.println("\t- Total number of rooms");
        System.out.println("\t- Estimated earnings for the month");
    }


    /**
     * Displays the low-level hotel information prompt.
     * @param hasSelectedHotel true if a hotel is selected; false otherwise
     * @param hotelName the name of the selected hotel
     */
    public void displayLowLevelHotelInfoPrompt(boolean hasSelectedHotel, String hotelName) {
        if (hasSelectedHotel)
            System.out.println("\nSelected hotel: " + hotelName + "\n");
        System.out.println("[L]ow-level information");
        System.out.println("\t[1] Selected date hotel availability");
        System.out.println("\t\t- Total number of available rooms");
        System.out.println("\t\t- Total number of booked rooms");
        System.out.println("\t[2] Selected room information");
        System.out.println("\t\t- Room name");
        System.out.println("\t\t- Price per night");
        System.out.println("\t\t- Availability across the entire month");
        System.out.println("\t[3] Selected reservation information");
        System.out.println("\t\t- Guest information");
        System.out.println("\t\t- Check-in date");
        System.out.println("\t\t- Check-out date");
        System.out.println("\t\t- Breakdown");
        System.out.println("\t\t- Total price");
        displayDivider();
    }


    /**
     * Displays the hotel information prompt.
     * @param hotelName the name of the selected hotel
     */
    public void displayHotelInfoPrompt(String hotelName) {
        displayHighLevelHotelInfoPrompt(true, hotelName);
        displayLowLevelHotelInfoPrompt(false, "");
    }


    /**
     * Displays the view hotel prompt to the user.
     */
    public void displayViewHotelPrompt() {
        displayDivider();
        System.out.println("\nYou are viewing hotel information. Enter \"quit\" to exit the hotel information page.");
        displayDivider();
    }




    /**
     * Displays the book reservation prompt to the user based on the given booking state.
     * @param displayState the booking state to display
     */
    public void displayBookReservationPrompt(SIMULATE_BOOKING displayState) {
        displayDivider();
        final String[][] promptManageHotel = {
            {
                "",
                "Welcome to Booking a Reservation.",
                "Please input the name of the hotel.",
                ""
            }, {
                "To book a reservation, please do the following: ",
                " 1) Enter a Valid Check-In Date and a Check-out Date",
                " 2) Enter a Room to Select"
            }, {
                "Please Select a Date to select from",
                "Note: Check-in Date should be earlier than the Check-out Date."
            }, {
                "Please Select a Room to Reserve",
                "Note: The room must be available for the selected dates."
            }, {
                "Input your name Please",
            }, {
                "Reservation Succesful.",
            }
        };

        displayDivider();
        for (String sentence : promptManageHotel[displayState.getID()]){
            System.out.println(sentence);
        }
    }


    /**
     * Displays the program termination message.
     */
    public void displayProgramTerminationMessage() {
        displayDivider();
        System.out.println("\nThank you for trying our Hotel Reservation System!\n");
        System.out.println("Authors:");
        System.out.println("Christian Joseph C. Bunyi - @cjbnyi");
        System.out.println("Roan Cedric V. Campo - @ImaginaryLogs");
        displayDivider();
        scanner.close(); // close the scanner
    }
}