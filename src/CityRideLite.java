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

                choice = scanner.nextInt();
                scanner.nextLine();

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
        System.out.print("Enter start zone: ");
        int startZone = scanner.nextInt();

        System.out.print("Enter end zone: ");
        int endZone = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter time band (PEAK/OFF_PEAK): ");
        String timeBandInput = scanner.nextLine().toUpperCase();

        System.out.print("Enter passenger type (ADULT/STUDENT/CHILD/SENIOR_CITIZEN): ");
        String passengerTypeInput = scanner.nextLine().toUpperCase();

        CityRideDataset.TimeBand timeBand = CityRideDataset.TimeBand.valueOf(timeBandInput);
        CityRideDataset.PassengerType passengerType =
                CityRideDataset.PassengerType.valueOf(passengerTypeInput);

        BigDecimal baseFare = journeyManager.getBaseFare(startZone, endZone, timeBand);
        BigDecimal finalFare = journeyManager.calculateFare(startZone, endZone, timeBand, passengerType);

        if (baseFare != null && finalFare != null) {
            Journey journey = new Journey(startZone, endZone, timeBand, passengerType, baseFare, finalFare);
            journeyManager.addJourney(journey);
        } else {
            System.out.println("Journey could not be added.");
        }
    }


    private void handleSearchJourney() {
        System.out.println("Search Journey selected.");
    }

    private void handleUpdateJourney() {
        System.out.println("Update Journey selected.");
    }

    private void handleDeleteJourney() {
        System.out.println("Delete Journey selected.");
    }

    private void handleCalculateFare() {
        System.out.println("Calculate Fare selected.");
    }


    // main()
    public static void main(String[] args) {
        CityRideLite app = new CityRideLite();
        app.run();
    }
}