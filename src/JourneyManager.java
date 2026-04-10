import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JourneyManager {
    private List<Journey> journeyList;

    private Map<CityRideDataset.PassengerType, BigDecimal> dailySpending;

    public JourneyManager() {
        journeyList = new ArrayList<>();
        dailySpending = new HashMap<>();

        for (CityRideDataset.PassengerType PassengerType : CityRideDataset.PassengerType.values()) {
            dailySpending.put(PassengerType, BigDecimal.ZERO);
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