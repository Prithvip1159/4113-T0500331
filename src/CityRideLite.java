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
    private static void put(Map<String, BigDecimal> m, int from, int to, TimeBand band, String amount) {
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

    public int getStartZone() { return startZone; }
    public int getEndZone() { return endZone; }
    public CityRideDataset.TimeBand getTimeBand() { return timeBand; }
    public CityRideDataset.PassengerType getPassengerType() { return passengerType; }
    public BigDecimal getBaseFare() { return baseFare; }
    public BigDecimal getFinalFare() { return finalFare; }

    public void setStartZone(int startZone) { this.startZone = startZone; }
    public void setEndZone(int endZone) { this.endZone = endZone; }
    public void setTimeBand(CityRideDataset.TimeBand timeBand) { this.timeBand = timeBand; }
    public void setPassengerType(CityRideDataset.PassengerType passengerType) { this.passengerType = passengerType; }
    public void setBaseFare(BigDecimal baseFare) { this.baseFare = baseFare; }
    public void setFinalFare(BigDecimal finalFare) { this.finalFare = finalFare; }

    public void displayJourney() {
        System.out.println("Zone " + startZone + " -> Zone " + endZone
                + " | " + timeBand + " | " + passengerType
                + " | Base: £" + baseFare + " | Final: £" + finalFare);
    }
}

class JourneyManager {
    private List<Journey> journeyList = new ArrayList<>();
    private Map<CityRideDataset.PassengerType, BigDecimal> dailySpending = new HashMap<>();

    public JourneyManager() {
        for (CityRideDataset.PassengerType pt : CityRideDataset.PassengerType.values()) {
            dailySpending.put(pt, BigDecimal.ZERO);
        }
    }

    public BigDecimal calculateFare(int fromZone, int toZone,
                                    CityRideDataset.TimeBand timeBand,
                                    CityRideDataset.PassengerType passengerType) {
        BigDecimal baseFare = CityRideDataset.getBaseFare(fromZone, toZone, timeBand);
        if (baseFare == null) {
            System.out.println("Error: Invalid zone combination.");
            return null;
        }
        BigDecimal discountRate = CityRideDataset.DISCOUNT_RATE.get(passengerType);
        BigDecimal discountAmount = baseFare.multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal discountedFare = baseFare.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);

        // Apply daily cap
        BigDecimal currentSpending = dailySpending.getOrDefault(passengerType, BigDecimal.ZERO);
        BigDecimal cap = CityRideDataset.DAILY_CAP.get(passengerType);
        BigDecimal remaining = cap.subtract(currentSpending);

        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Daily cap reached for " + passengerType + ". This journey is free!");
            return BigDecimal.ZERO;
        }
        if (discountedFare.compareTo(remaining) > 0) {
            System.out.println("Fare capped to remaining daily allowance: £" + remaining);
            return remaining;
        }
        return discountedFare;
    }

    public BigDecimal getBaseFare(int fromZone, int toZone, CityRideDataset.TimeBand timeBand) {
        return CityRideDataset.getBaseFare(fromZone, toZone, timeBand);
    }

    public void addJourney(Journey j) {
        journeyList.add(j);
        BigDecimal currentSpending = dailySpending.getOrDefault(j.getPassengerType(), BigDecimal.ZERO);
        dailySpending.put(j.getPassengerType(), currentSpending.add(j.getFinalFare()));
        System.out.println("Journey added successfully.");
    }

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

    public Journey searchJourney(int startZone, int endZone) {
        for (Journey j : journeyList) {
            if (j.getStartZone() == startZone && j.getEndZone() == endZone) {
                return j;
            }
        }
        return null;
    }

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

    public void updateJourney(int index, int newStart, int newEnd,
                              CityRideDataset.TimeBand newTimeBand,
                              CityRideDataset.PassengerType newPassengerType) {
        if (index >= 0 && index < journeyList.size()) {
            Journey j = journeyList.get(index);

            // Remove old fare from daily spending
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

    public void showDailySpending() {
        System.out.println("\n--- Daily Spending Summary ---");
        for (CityRideDataset.PassengerType pt : CityRideDataset.PassengerType.values()) {
            BigDecimal spent = dailySpending.getOrDefault(pt, BigDecimal.ZERO);
            BigDecimal cap = CityRideDataset.DAILY_CAP.get(pt);
            System.out.println(pt + ": £" + spent + " / £" + cap + " (daily cap)");
        }
    }
}


public class CityRideLite {

    private static int getValidZone(Scanner sc, String prompt) {
        int zone;
        while (true) {
            System.out.print(prompt);
            zone = sc.nextInt();
            sc.nextLine();
            if (zone >= CityRideDataset.MIN_ZONE && zone <= CityRideDataset.MAX_ZONE) {
                return zone;
            }
            System.out.println("Invalid zone. Please enter a zone between "
                    + CityRideDataset.MIN_ZONE + " and " + CityRideDataset.MAX_ZONE + ".");
        }
    }

    private static CityRideDataset.TimeBand getTimeBand(Scanner sc) {
        while (true) {
            System.out.println("Select time band:");
            System.out.println("1. PEAK");
            System.out.println("2. OFF_PEAK");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) return CityRideDataset.TimeBand.PEAK;
            if (choice == 2) return CityRideDataset.TimeBand.OFF_PEAK;
            System.out.println("Invalid choice. Please select 1 or 2.");
        }
    }

    private static CityRideDataset.PassengerType getPassengerType(Scanner sc) {
        while (true) {
            System.out.println("Select passenger type:");
            System.out.println("1. ADULT");
            System.out.println("2. STUDENT");
            System.out.println("3. CHILD");
            System.out.println("4. SENIOR_CITIZEN");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: return CityRideDataset.PassengerType.ADULT;
                case 2: return CityRideDataset.PassengerType.STUDENT;
                case 3: return CityRideDataset.PassengerType.CHILD;
                case 4: return CityRideDataset.PassengerType.SENIOR_CITIZEN;
                default: System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JourneyManager jm = new JourneyManager();
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

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    int start = getValidZone(sc, "Enter start zone (1-5): ");
                    int end = getValidZone(sc, "Enter end zone (1-5): ");
                    CityRideDataset.TimeBand timeBand = getTimeBand(sc);
                    CityRideDataset.PassengerType passengerType = getPassengerType(sc);

                    BigDecimal baseFare = jm.getBaseFare(start, end, timeBand);
                    BigDecimal finalFare = jm.calculateFare(start, end, timeBand, passengerType);

                    if (finalFare != null && baseFare != null) {
                        Journey journey = new Journey(start, end, timeBand, passengerType, baseFare, finalFare);
                        jm.addJourney(journey);
                        System.out.println("Base Fare: £" + baseFare
                                + " | Final Fare (after discount & cap): £" + finalFare);
                    }
                    break;

                case 2:
                    jm.showAllJourneys();
                    break;

                case 3:
                    int sStart = getValidZone(sc, "Enter start zone to search (1-5): ");
                    int sEnd = getValidZone(sc, "Enter end zone to search (1-5): ");
                    Journey found = jm.searchJourney(sStart, sEnd);
                    if (found != null) {
                        found.displayJourney();
                    } else {
                        System.out.println("Journey not found.");
                    }
                    break;

                case 4:
                    jm.showAllJourneys();
                    System.out.print("Enter journey number to update: ");
                    int updateIndex = sc.nextInt() - 1;
                    sc.nextLine();
                    int newStart = getValidZone(sc, "Enter new start zone (1-5): ");
                    int newEnd = getValidZone(sc, "Enter new end zone (1-5): ");
                    CityRideDataset.TimeBand newTimeBand = getTimeBand(sc);
                    CityRideDataset.PassengerType newPassengerType = getPassengerType(sc);
                    jm.updateJourney(updateIndex, newStart, newEnd, newTimeBand, newPassengerType);
                    break;

                case 5:
                    jm.showAllJourneys();
                    System.out.print("Enter journey number to delete: ");
                    int deleteIndex = sc.nextInt() - 1;
                    sc.nextLine();
                    jm.deleteJourney(deleteIndex);
                    break;

                case 6:
                    int fStart = getValidZone(sc, "Enter start zone (1-5): ");
                    int fEnd = getValidZone(sc, "Enter end zone (1-5): ");
                    CityRideDataset.TimeBand fTimeBand = getTimeBand(sc);
                    CityRideDataset.PassengerType fPassengerType = getPassengerType(sc);

                    BigDecimal calcBase = jm.getBaseFare(fStart, fEnd, fTimeBand);
                    BigDecimal calcFare = jm.calculateFare(fStart, fEnd, fTimeBand, fPassengerType);

                    if (calcFare != null && calcBase != null) {
                        System.out.println("Base Fare: £" + calcBase);
                        System.out.println("Discounted Fare (with daily cap): £" + calcFare);
                    }
                    break;

                case 7:
                    jm.showDailySpending();
                    break;

                case 8:
                    System.out.println("Exiting CityRideLite. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 8);

        sc.close();
    }
}
