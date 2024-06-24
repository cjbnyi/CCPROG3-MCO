package Hotel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
    public static final char CREATE_HOTEL_OPTION = 'C',
                             VIEW_HOTEL_OPTION = 'V',
                             MANAGE_HOTEL_OPTION = 'M',
                             BOOK_RESERVATION_OPTION = 'B',
                             QUIT_OPTION = 'Q';

    public enum MANAGER_STATE {
        MS_OVERVIEW(0),
        MS_CHANGE_NAME(1),
        MS_ADD_ROOMS(2),
        MS_REMOVE_ROOMS(3),
        MS_UPDATE_PRICE(4),
        MS_REMOVE_RESERVATIONS(5),
        MS_REMOVE_HOTEL(6);

        private int numberID;


        private MANAGER_STATE(int numberID) {
            this.numberID = numberID;
        }

        public int getID() {
            return this.numberID;
        }

    }


    public enum SIMULATE_BOOKING {
        SB_OVERVIEW(1),
        SB_DATE_SELECTION(2),
        SB_ROOM_SELECTION(3);

        private int numberID;


        private SIMULATE_BOOKING(int numberID) {
            this.numberID = numberID;
        }

        public int getID() {
            return this.numberID;
        }

    } 

    private final Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }


    // TODO: Finalize the divider
    private void displayDivider() {
        System.out.println("========");
    }


    public void displayMessage(String message) {
        displayDivider();
        System.out.println(message);
    }


    public void displayActionPrompt() {
        displayDivider();
        System.out.println("Welcome to the Hotel Reservation System!\n");
        System.out.println("[C]reate Hotel");
        System.out.println("[V]iew Hotel");
        System.out.println("[M]anage Hotel");
        System.out.println("[B]ook Reservation");
    }


    public String getUserInput(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }

    public Double getUserInputDouble(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextDouble();
    }

    public LocalDate getLocalDate(String prompt) {
        System.out.println(prompt + " ");
        
        System.out.println("Year: ");
        int year = scanner.nextInt();
        System.out.println("Month: ");
        int month = scanner.nextInt();    
        System.out.println("Day: ");
        int day = scanner.nextInt();
        return LocalDate.of(year, month, day);
    }

    /**
     * Prompts the user to confirm their input.
     * @return true if the user inputs 'Y'; false if the user inputs 'N'
     */
    public boolean confirmUserInput() {
        displayDivider();
        System.out.print("Do you want to confirm your action (Y/N)? "); // placeholder and tentative
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("Y"))
            return true;
        else if (userInput.equalsIgnoreCase("N"))
            return false;
        return confirmUserInput(); // may cause some errors, idk
    }

    // TODO: Implement clearScreen()
    public void clearScreen() {

    }

    public void displayCreateHotelPrompt() {
        displayDivider();
        System.out.println("<insert Create Hotel prompt>");
    }

    public void displayViewHotelPrompt() {
        displayDivider();
        System.out.println("<insert View Hotel prompt>");
    }

    public void displayManageHotelPrompt(MANAGER_STATE displayState) {
        final String[][] promptManageHotel = {
            {       // 0 : Hotel
                "# Please Choose the following Actions",
                " [a] Change the name of the Hotel",
                " [b] Add Rooms",
                " [c] Remove Rooms",
                " [d] Update the Base Price for a Room",
                " [e] Remove Reservation",
                " [f] Remove Hotel",
                "Type \"quit\" to exit the program"
            }, {
                "## Change Name of the Hotel",
                "Please provide the correct name of the hotel you want to change the name."
            }, {    // 1 : Change
                "## Add a Room(s)",
                "Please provide the correct name of the hotel and the room you want to add."
            }, {
                "## Remove a Room(s)",
                "Please provide the correct name of the hotel and the room you want to remove."
            }, {
                "## Update the Base Price",
                "Please input the price you want to change. Change should be greater than P100."
            }, {
                "## Remove Reservation"
            }, {
                "## Remove Hotel"
            }
        };

        displayDivider();
        for (String sentence : promptManageHotel[displayState.getID()]){
            System.out.println(sentence);
        }

    }

    public void displayBookReservationPrompt(SIMULATE_BOOKING displayState) {
        displayDivider();
        final String[][] promptManageHotel = {
            {
                "To book a reservation, please do the following: ",
                " 1) Enter a Valid Hotel",
                " 2) Enter a Valid Check-In Date and a Check-out Date",
                " 3) Enter a Room to Select"
            }, {
                "Please Select a Date to select from",
                "Note: Check-in Date should be earlier than the Check-out Date."
            }
        };

        displayDivider();
        for (String sentence : promptManageHotel[displayState.getID()]){
            System.out.println(sentence);
        }
    }

    public void displayProgramTerminationMessage() {
        displayDivider();
        System.out.println("Thank you for trying out our Hotel Reservation System!");
        System.out.println("Authors:");
        System.out.println("\tChristian Joseph C. Bunyi - @cjbnyi");
        System.out.println("\tRoan Cedric V. Campo - @ImaginaryLogs");
    }

    public void displayHotels(ArrayList<Hotel> hotelList) {
        displayDivider();

        if (hotelList.isEmpty())
            System.out.println("No hotels currently exist.");
        else {
            int i = 0;
            for (Hotel hotel : hotelList) {
                displayDivider();
                System.out.println((i + 1) + ".)");
                System.out.println("Name: " + hotel.getName());
                System.out.println("List of rooms:");
                displayRoomList(hotel.getRoomList());
                // will continue this - cj
            }
        }
    }

    public void displayRoomList(ArrayList<Room> roomList) {
        
        if(roomList.isEmpty()){
            displayDivider();
            System.out.println("No Rooms currently exist.");
        } else {
            int i = 0;
            for (Room room : roomList){
                displayDivider();
                System.out.println(i + 1 + ".)");
                System.out.println("Room: " + room.getName());
                System.out.println("Class: " + room.getClass());
                System.out.println("Base Price: " + room.getBasePricePerNight());
                ++i;
            }
        }
    }

    public void displayInvalidInputWarning() {
        displayDivider();
        System.out.println("Please provide a valid response!");
    }
}