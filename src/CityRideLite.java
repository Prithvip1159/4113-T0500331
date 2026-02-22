import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

final class CityRideDataset {

    private CityRideDataset() {}

    public static final int MIN_ZONE = 1;
    public static final int MAX_ZONE = 5;

    public enum TimeBand {
        PEAK,
        OFF_PEAK
    }



    public enum PassengerType {
        ADULT,
        STUDENT,
        CHILD,
        SENIOR_CITIZEN
    }

    public static final Map<PassengerType, BigDecimal> DISCOUNT_RATE = Map.of(
            PassengerType.ADULT, new BigDecimal("0.00"),
            PassengerType.STUDENT, new BigDecimal("0.25"),
            PassengerType.CHILD, new BigDecimal("0.50"),
            PassengerType.SENIOR_CITIZEN, new BigDecimal("0.30")
    );

    public static final Map<PassengerType, BigDecimal> DAILY_CAP = Map.of(
            PassengerType.ADULT, new BigDecimal("8.00"),
            PassengerType.STUDENT, new BigDecimal("6.00"),
            PassengerType.CHILD, new BigDecimal("4.00"),
            PassengerType.SENIOR_CITIZEN, new BigDecimal("7.00")
    );

    public static final Map<String, BigDecimal> BASE_FARE = buildBaseFare();

    public static BigDecimal getBaseFare(int fromZone, int toZone, TimeBand timeBand) {
        return BASE_FARE.get(key(fromZone, toZone, timeBand));
    }

    public static String key(int fromZone, int toZone, TimeBand timeBand) {
        return fromZone + "-" + toZone + "-" + timeBand.name();
    }

    private static BigDecimal money(String amount) {
        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
    }

    private static Map<String, BigDecimal> buildBaseFare() {
        Map<String, BigDecimal> m = new HashMap<>();

        // Peak fares
        put(m,1,1,TimeBand.PEAK,"2.50"); put(m,1,2,TimeBand.PEAK,"3.20");
        put(m,1,3,TimeBand.PEAK,"3.80"); put(m,1,4,TimeBand.PEAK,"4.40");
        put(m,1,5,TimeBand.PEAK,"5.00");
        put(m,2,1,TimeBand.PEAK,"3.20"); put(m,2,2,TimeBand.PEAK,"2.30");
        put(m,2,3,TimeBand.PEAK,"3.10"); put(m,2,4,TimeBand.PEAK,"3.80");
        put(m,2,5,TimeBand.PEAK,"4.50");
        put(m,3,1,TimeBand.PEAK,"3.80"); put(m,3,2,TimeBand.PEAK,"3.10");
        put(m,3,3,TimeBand.PEAK,"2.10"); put(m,3,4,TimeBand.PEAK,"3.00");
        put(m,3,5,TimeBand.PEAK,"3.70");
        put(m,4,1,TimeBand.PEAK,"4.40"); put(m,4,2,TimeBand.PEAK,"3.80");
        put(m,4,3,TimeBand.PEAK,"3.00"); put(m,4,4,TimeBand.PEAK,"2.00");
        put(m,4,5,TimeBand.PEAK,"2.90");
        put(m,5,1,TimeBand.PEAK,"5.00"); put(m,5,2,TimeBand.PEAK,"4.50");
        put(m,5,3,TimeBand.PEAK,"3.70"); put(m,5,4,TimeBand.PEAK,"2.90");
        put(m,5,5,TimeBand.PEAK,"1.90");

        // Off-peak fares
        put(m,1,1,TimeBand.OFF_PEAK,"2.00"); put(m,1,2,TimeBand.OFF_PEAK,"2.70");
        put(m,1,3,TimeBand.OFF_PEAK,"3.20"); put(m,1,4,TimeBand.OFF_PEAK,"3.70");
        put(m,1,5,TimeBand.OFF_PEAK,"4.20");
        put(m,2,1,TimeBand.OFF_PEAK,"2.70"); put(m,2,2,TimeBand.OFF_PEAK,"1.90");
        put(m,2,3,TimeBand.OFF_PEAK,"2.60"); put(m,2,4,TimeBand.OFF_PEAK,"3.20");
        put(m,2,5,TimeBand.OFF_PEAK,"3.80");
        put(m,3,1,TimeBand.OFF_PEAK,"3.20"); put(m,3,2,TimeBand.OFF_PEAK,"2.60");
        put(m,3,3,TimeBand.OFF_PEAK,"1.70"); put(m,3,4,TimeBand.OFF_PEAK,"2.50");
        put(m,3,5,TimeBand.OFF_PEAK,"3.10");
        put(m,4,1,TimeBand.OFF_PEAK,"3.70"); put(m,4,2,TimeBand.OFF_PEAK,"3.20");
        put(m,4,3,TimeBand.OFF_PEAK,"2.50"); put(m,4,4,TimeBand.OFF_PEAK,"1.60");
        put(m,4,5,TimeBand.OFF_PEAK,"2.40");
        put(m,5,1,TimeBand.OFF_PEAK,"4.20"); put(m,5,2,TimeBand.OFF_PEAK,"3.80");
        put(m,5,3,TimeBand.OFF_PEAK,"3.10"); put(m,5,4,TimeBand.OFF_PEAK,"2.40");
        put(m,5,5,TimeBand.OFF_PEAK,"1.50");

        return Map.copyOf(m);
    }
    private static void put(Map<String, BigDecimal> m, int from, int to,
                            TimeBand band, String amount) {
        m.put(key(from, to, band), money(amount));
    }
}

class Journey {
    private int startZone;
    private int endZone;
    private CityRideDataset.TimeBand timeBand;
    private CityRideDataset.PassengerType passengerType;
    private BigDecimal baseFare;
    private BigDecimal finalFare;

    public Journey(int startZone, int endZone, CityRideDataset.TimeBand timeBand,
                   CityRideDataset.PassengerType passengerType, BigDecimal baseFare, BigDecimal finalFare) {
        this.startZone = startZone;
        this.endZone = endZone;
        this.timeBand = timeBand;
        this.passengerType = passengerType;
        this.baseFare = baseFare;
        this.finalFare = finalFare;
    }

    // --- Getters ---
    public int getStartZone() { return startZone; }
    public int getEndZone() { return endZone; }
    public CityRideDataset.TimeBand getTimeBand() { return timeBand; }
    public CityRideDataset.PassengerType getPassengerType() { return passengerType; }
    public BigDecimal getBaseFare() { return baseFare; }
    public BigDecimal getFinalFare() { return finalFare; }

    // --- Setters (used by updateJourney in JourneyManager) ---

    public void setStartZone(int startZone) { this.startZone = startZone; }
    public void setEndZone(int endZone) { this.endZone = endZone; }
    public void setTimeBand(CityRideDataset.TimeBand timeBand) { this.timeBand = timeBand; }
    public void setPassengerType(CityRideDataset.PassengerType passengerType) { this.passengerType = passengerType; }
    public void setBaseFare(BigDecimal baseFare) { this.baseFare = baseFare; }
    public void setFinalFare(BigDecimal finalFare) { this.finalFare = finalFare; }

    /* Prints a formatted single-line summary of this journey */
    public void displayJourney() {
        System.out.println("Zone " + startZone + " -> Zone " + endZone
                + " | " + timeBand + " | " + passengerType
                + " | Base: £" + baseFare + " | Final: £" + finalFare);
    }
}


// =============================================================================
// JourneyManager — Handles CRUD operations, fare calculation, and daily caps
// =============================================================================

class JourneyManager {
    private List<Journey> journeyList;

    private Map<CityRideDataset.PassengerType, BigDecimal> dailySpending;

    public JourneyManager() {
        journeyList = new ArrayList<>();
        dailySpending = new HashMap<>();

        for (CityRideDataset.PassengerType PassangerType : CityRideDataset.PassengerType.values()) {
            dailySpending.put(PassangerType, BigDecimal.ZERO);
        }
    }
    /**
     * Calculates final fare by applying passenger discount then enforcing the
     * daily spending cap. Uses a single result variable to satisfy the NTIC
     * single-return guideline.
     */

    public BigDecimal calculateFare(int fromZone, int toZone,
                                    CityRideDataset.TimeBand timeBand,
                                    CityRideDataset.PassengerType passengerType) {
        BigDecimal result = null;
        BigDecimal baseFare = CityRideDataset.getBaseFare(fromZone, toZone, timeBand);

        if (baseFare == null) {
            System.out.println("Error: Invalid zone combination.");
        }
        else {
            /* Apply percentage discount based on passenger type */
            BigDecimal discountRate = CityRideDataset.DISCOUNT_RATE.get(passengerType);
            BigDecimal discountAmount = baseFare.multiply(discountRate)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal discountedFare = baseFare.subtract(discountAmount)
                    .setScale(2, RoundingMode.HALF_UP);

            /* Enforce daily cap so total spend does not exceed the limit */
            BigDecimal currentSpending = dailySpending.getOrDefault(
                    passengerType, BigDecimal.ZERO);
            BigDecimal cap = CityRideDataset.DAILY_CAP.get(passengerType);
            BigDecimal remaining = cap.subtract(currentSpending);

            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Daily cap reached for " + passengerType
                        + ". This journey is free!");
                result = BigDecimal.ZERO;
            } else if (discountedFare.compareTo(remaining) > 0) {
                System.out.println("Fare capped to remaining daily allowance: £"
                        + remaining);
                result = remaining;
            } else {
                result = discountedFare;
            }
        }
        return result;
    }

    /* Delegates to the dataset to retrieve the base fare for a zone pair */
    public BigDecimal getBaseFare(int fromZone, int toZone, CityRideDataset.TimeBand timeBand) {
        return CityRideDataset.getBaseFare(fromZone, toZone, timeBand);
    }

    /* Records a new journey and updates the daily spending tracker */
    public void addJourney(Journey j) {
        journeyList.add(j);
        BigDecimal currentSpending = dailySpending.getOrDefault(j.getPassengerType(), BigDecimal.ZERO);
        dailySpending.put(j.getPassengerType(), currentSpending.add(j.getFinalFare()));
        System.out.println("Journey added successfully.");
    }

    /* Lists every recorded journey, or a message if the list is empty */
    public void showAllJourneys() {
        if (journeyList.isEmpty()) {
            System.out.println("No journeys recorded yet.");
        } else {
            for (int i = 0; i < journeyList.size(); i++) {
                System.out.print((i + 1) + ". ");
                journeyList.get(i).displayJourney();
            }
        }
    }

    /* Returns the first journey matching both zones, or null if not found */
    public Journey searchJourney(int startZone, int endZone) {
        Journey result = null;
        for (Journey current : journeyList) {
            if (current.getStartZone() == startZone
                    && current.getEndZone() == endZone) {
                result = current;
                break; // stop at first match
            }
        }
        return result;
    }


    /* Removes a journey by list index and reverses its spending impact */
    public void deleteJourney(int index) {
        if (index >= 0 && index < journeyList.size()) {
            Journey removed = journeyList.remove(index);
            BigDecimal currentSpending = dailySpending.get(removed.getPassengerType());
            dailySpending.put(removed.getPassengerType(), currentSpending.subtract(removed.getFinalFare()));
            System.out.println("Journey deleted successfully.");
        } else {
            System.out.println("Invalid journey number.");
        }
    }

    /*
     * Replaces an existing journey's details with new values.
     * Old fare is subtracted before the new fare is added so the cap
     * remains accurate across updates.
     */
    public void updateJourney(int index, int newStart, int newEnd,
                              CityRideDataset.TimeBand newTimeBand,
                              CityRideDataset.PassengerType newPassengerType) {
        if (index >= 0 && index < journeyList.size()) {
            Journey j = journeyList.get(index);

            /* Remove old fare from spending before recalculating */
            BigDecimal oldSpending = dailySpending.get(j.getPassengerType());
            dailySpending.put(j.getPassengerType(), oldSpending.subtract(j.getFinalFare()));

            BigDecimal baseFare = CityRideDataset.getBaseFare(newStart, newEnd, newTimeBand);
            BigDecimal finalFare = calculateFare(newStart, newEnd, newTimeBand, newPassengerType);

            if (finalFare != null && baseFare != null) {
                j.setStartZone(newStart);
                j.setEndZone(newEnd);
                j.setTimeBand(newTimeBand);
                j.setPassengerType(newPassengerType);
                j.setBaseFare(baseFare);
                j.setFinalFare(finalFare);

                BigDecimal newSpending = dailySpending.getOrDefault(newPassengerType, BigDecimal.ZERO);
                dailySpending.put(newPassengerType, newSpending.add(finalFare));
                System.out.println("Journey updated successfully.");
            }
        } else {
            System.out.println("Invalid journey number.");
        }
    }

    /* Prints each passenger type's total spend alongside its daily cap */
    public void showDailySpending() {
        System.out.println("\n--- Daily Spending Summary ---");
        for (CityRideDataset.PassengerType pt : CityRideDataset.PassengerType.values()) {
            BigDecimal spent = dailySpending.getOrDefault(pt, BigDecimal.ZERO);
            BigDecimal cap = CityRideDataset.DAILY_CAP.get(pt);
            System.out.println(pt + ": £" + spent + " / £" + cap + " (daily cap)");
        }
    }
}

// =============================================================================
// CityRideLite — Main entry point and user interaction
// =============================================================================

public class CityRideLite {
    private Scanner scanner;

    private JourneyManager journeyManager;

    public CityRideLite() {
        scanner = new Scanner(System.in);
        journeyManager = new JourneyManager();
    }

    /*
     * Repeatedly prompts until a valid zone (1-5) is entered.
     * Uses a boolean controller instead of while(true).
     */
    private int getValidZone(String prompt) {
        int zone = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            zone = scanner.nextInt();
            scanner.nextLine();
            if (zone >= CityRideDataset.MIN_ZONE
                    && zone <= CityRideDataset.MAX_ZONE) {
                valid = true;
            } else {
                System.out.println("Invalid zone. Please enter a zone between "
                        + CityRideDataset.MIN_ZONE + " and "
                        + CityRideDataset.MAX_ZONE + ".");
            }
        }
        return zone;
    }
    /* Prompts for a time band selection using a loop controller variable */
    private CityRideDataset.TimeBand getTimeBand() {
        CityRideDataset.TimeBand result = null;
        boolean valid = false;

        while (!valid) {
            System.out.println("Select time band:");
            System.out.println("1. PEAK");
            System.out.println("2. OFF_PEAK");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: result = CityRideDataset.TimeBand.PEAK;     valid = true; break;
                case 2: result = CityRideDataset.TimeBand.OFF_PEAK; valid = true; break;
                default: System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
        return result;
    }
    /* Prompts for a passenger type selection using a loop controller variable */
    private CityRideDataset.PassengerType getPassengerType() {
        CityRideDataset.PassengerType result = null;
        boolean valid = false;

        while (!valid) {
            System.out.println("Select passenger type:");
            System.out.println("1. ADULT");
            System.out.println("2. STUDENT");
            System.out.println("3. CHILD");
            System.out.println("4. SENIOR_CITIZEN");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: result = CityRideDataset.PassengerType.ADULT;          valid = true; break;
                case 2: result = CityRideDataset.PassengerType.STUDENT;        valid = true; break;
                case 3: result = CityRideDataset.PassengerType.CHILD;          valid = true; break;
                case 4: result = CityRideDataset.PassengerType.SENIOR_CITIZEN; valid = true; break;
                default: System.out.println("Invalid choice. Please select 1-4.");
            }
        }
        return result;
    }



    /* Collects journey details, calculates fare, and stores the journey */
    private void handleAddJourney() {
        int start = getValidZone("Enter start zone (1-5): ");
        int end = getValidZone("Enter end zone (1-5): ");
        CityRideDataset.TimeBand timeBand = getTimeBand();
        CityRideDataset.PassengerType passengerType = getPassengerType();

        BigDecimal baseFare = journeyManager.getBaseFare(start, end, timeBand);
        BigDecimal finalFare = journeyManager.calculateFare(
                start, end, timeBand, passengerType);

        if (finalFare != null && baseFare != null) {
            Journey journey = new Journey(start, end, timeBand,
                    passengerType, baseFare, finalFare);
            journeyManager.addJourney(journey);
            System.out.println("Base Fare: £" + baseFare
                    + " | Final Fare (after discount & cap): £" + finalFare);
        }
    }

    /* Prompts for zone pair and displays the matching journey if found */
    private void handleSearchJourney() {
        int startZone = getValidZone("Enter start zone to search (1-5): ");
        int endZone = getValidZone("Enter end zone to search (1-5): ");
        Journey found = journeyManager.searchJourney(startZone, endZone);

        if (found != null) {
            found.displayJourney();
        } else {
            System.out.println("Journey not found.");
        }
    }

    /* Shows current journeys, then lets the user pick one to update */
    private void handleUpdateJourney() {
        journeyManager.showAllJourneys();
        System.out.print("Enter journey number to update: ");
        int updateIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        int newStart = getValidZone("Enter new start zone (1-5): ");
        int newEnd = getValidZone("Enter new end zone (1-5): ");
        CityRideDataset.TimeBand newTimeBand = getTimeBand();
        CityRideDataset.PassengerType newPassengerType = getPassengerType();
        journeyManager.updateJourney(updateIndex, newStart, newEnd,
                newTimeBand, newPassengerType);
    }

    /* Shows current journeys, then lets the user pick one to delete */
    private void handleDeleteJourney() {
        journeyManager.showAllJourneys();
        System.out.print("Enter journey number to delete: ");
        int deleteIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        journeyManager.deleteJourney(deleteIndex);
    }

    /* Calculates and displays the fare without recording a journey */
    private void handleCalculateFare() {
        int startZone = getValidZone("Enter start zone (1-5): ");
        int endZone = getValidZone("Enter end zone (1-5): ");
        CityRideDataset.TimeBand timeBand = getTimeBand();
        CityRideDataset.PassengerType passengerType = getPassengerType();

        BigDecimal calcBase = journeyManager.getBaseFare(
                startZone, endZone, timeBand);
        BigDecimal calcFare = journeyManager.calculateFare(
                startZone, endZone, timeBand, passengerType);

        if (calcFare != null && calcBase != null) {
            System.out.println("Base Fare: £" + calcBase);
            System.out.println("Discounted Fare (with daily cap): £" + calcFare);
        }
    }

    /* Main application loop — displays menu and delegates to handler methods */
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
            System.out.println("8. Exit");
            System.out.print("Choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: handleAddJourney();                  break;
                case 2: journeyManager.showAllJourneys();    break;
                case 3: handleSearchJourney();               break;
                case 4: handleUpdateJourney();               break;
                case 5: handleDeleteJourney();               break;
                case 6: handleCalculateFare();               break;
                case 7: journeyManager.showDailySpending();  break;
                case 8: System.out.println("Exiting CityRideLite. Goodbye!"); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 8);

        scanner.close();
    }

    /* Entry point — creates an instance and starts the application */
    public static void main(String[] args) {
        CityRideLite app = new CityRideLite();
        app.run();
    }
}

// As per the tutor suggestions, I have created separate functions and I am calling each functions from my main menu.