import java.util.Scanner;
import java.math.BigDecimal;

public class CityRideLite {
    private Scanner scanner;
    private JourneyManager journeyManager;


    public CityRideLite() {
        scanner = new Scanner(System.in);
        journeyManager = new JourneyManager();
    }


    private void handleAdminMenu() {
        AdminManager adminManager = new AdminManager();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();


        if (adminManager.authenticate(password)) {
            int choice;
            do {
                System.out.println("\n===== Admin Menu =====");
                System.out.println("1. View Configuration");
                System.out.println("2. Update Configuration");
                System.out.println("3. Exit Admin Menu");
                System.out.print("Choice: ");

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine();
                    choice = 0;
                }

                switch (choice) {
                    case 1:
                        adminManager.viewConfig();
                        break;
                    case 2:
                        adminManager.updateConfig();
                        break;
                    case 3:
                        System.out.println("Exiting Admin Menu.");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 3);
        } else {
            System.out.println("Invalid password. Access denied.");
        }
    }


    public void run() {
        int choice;
        do {
            System.out.println("\n===== CityRideLite Menu =====");
            System.out.println("1. Add Journey");
            System.out.println("2. List Journeys");
            System.out.println("3. Search Journey");
            System.out.println("4. Update Journey");
            System.out.println("5. Delete Journey");
            System.out.println("6. Calculate Fare");
            System.out.println("7. View Daily Spending");
            System.out.println("8. Admin Menu");
            System.out.println("9. Exit");
            System.out.print("Choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    handleAddJourney();
                    break;
                case 2:
                    journeyManager.showAllJourneys();
                    break;
                case 3:
                    handleSearchJourney();
                    break;
                case 4:
                    handleUpdateJourney();
                    break;
                case 5:
                    handleDeleteJourney();
                    break;
                case 6:
                    handleCalculateFare();
                    break;
                case 7:
                    journeyManager.showDailySpending();
                    break;
                case 8:
                    handleAdminMenu();
                    break;
                case 9:
                    System.out.println("Exiting CityRideLite. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 9);
    }

    private void handleAddJourney() {
        int fromZone = 0;
        int toZone = 0;
        CityRideDataset.TimeBand timeBand;
        CityRideDataset.PassengerType passengerType;

        try {
            System.out.print("Enter start zone: ");
            fromZone = scanner.nextInt();

            System.out.print("Enter end zone: ");
            toZone = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter time band (PEAK/OFF_PEAK): ");
            timeBand = CityRideDataset.TimeBand.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter passenger type (ADULT/STUDENT/CHILD/SENIOR_CITIZEN): ");
            passengerType = CityRideDataset.PassengerType.valueOf(scanner.nextLine().toUpperCase());

        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input: zones must be numbers.");
            scanner.nextLine();
            return;

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: check time band or passenger type values.");
            return;
        }

        BigDecimal baseFare = journeyManager.getBaseFare(fromZone, toZone, timeBand);
        BigDecimal chargedFare = journeyManager.calculateFare(fromZone, toZone, timeBand, passengerType);

        if (baseFare != null && chargedFare != null) {

            String id = "J" + String.format("%03d", journeyManager.getAllJourneys().size() + 1);
            String dateTime = java.time.LocalDateTime.now().toString();
            int zonesCrossed = Math.abs(fromZone - toZone);
            BigDecimal discount = baseFare.subtract(chargedFare);

            Journey journey = new Journey(
                    id,
                    dateTime,
                    fromZone,
                    toZone,
                    timeBand,
                    passengerType,
                    zonesCrossed,
                    baseFare,
                    discount,
                    chargedFare
            );

            journeyManager.addJourney(journey);
        } else {
            System.out.println("Journey could not be added.");
        }
    }


    // prompts for zones and assigns search to JourneyManager
    private void handleSearchJourney() {
        System.out.print("Enter start zone: ");
        int startZone = scanner.nextInt();
        System.out.print("Enter end zone: ");
        int endZone = scanner.nextInt();
        scanner.nextLine();

        Journey found = journeyManager.searchJourney(startZone, endZone);
        if (found != null) {
            found.displayJourney();
        } else {
            System.out.println("No journey found for those zones.");
        }
    }

    // shows all journeys and asks which to delete by number
        private void handleDeleteJourney() {
        journeyManager.showAllJourneys();
        System.out.print("Enter journey number to delete: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        journeyManager.deleteJourney(index);
    }
    private void handleUpdateJourney() {
        journeyManager.showAllJourneys();

        System.out.print("Enter journey number to update: ");

        int index;
        try {
            index = scanner.nextInt() - 1;
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid journey number.");
            scanner.nextLine();
            return;
        }

        try {
            System.out.print("Enter new start zone: ");
            int newStart = scanner.nextInt();

            System.out.print("Enter new end zone: ");
            int newEnd = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new time band (PEAK/OFF_PEAK): ");
            CityRideDataset.TimeBand newTimeBand =
                    CityRideDataset.TimeBand.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter new passenger type (ADULT/STUDENT/CHILD/SENIOR_CITIZEN): ");
            CityRideDataset.PassengerType newType =
                    CityRideDataset.PassengerType.valueOf(scanner.nextLine().toUpperCase());

            journeyManager.updateJourney(index, newStart, newEnd, newTimeBand, newType);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid time band or passenger type.");
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine();
        }
    }

    // calculates and displays fare without adding a journey
    private void handleCalculateFare() {
        try {
            System.out.print("Enter start zone: ");
            int fromZone = scanner.nextInt();

            System.out.print("Enter end zone: ");
            int toZone = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter time band (PEAK/OFF_PEAK): ");
            CityRideDataset.TimeBand timeBand =
                    CityRideDataset.TimeBand.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter passenger type (ADULT/STUDENT/CHILD/SENIOR_CITIZEN): ");
            CityRideDataset.PassengerType passengerType =
                    CityRideDataset.PassengerType.valueOf(scanner.nextLine().toUpperCase());

            BigDecimal fare = journeyManager.calculateFare(fromZone, toZone, timeBand, passengerType);

            if (fare != null) {
                System.out.println("Calculated fare: £" + fare);
            } else {
                System.out.println("Could not calculate fare.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid time band or passenger type.");
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter numbers correctly.");
            scanner.nextLine();
        }
    }


    // main()
    public static void main(String[] args) {
        CityRideLite app = new CityRideLite();
        app.run();
    }
}