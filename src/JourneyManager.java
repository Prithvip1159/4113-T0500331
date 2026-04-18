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

        for (CityRideDataset.PassengerType passengerType : CityRideDataset.PassengerType.values()) {
            dailySpending.put(passengerType, BigDecimal.ZERO);
        }
    }

    public BigDecimal calculateFare(int fromZone, int toZone,
                                    CityRideDataset.TimeBand timeBand,
                                    CityRideDataset.PassengerType passengerType) {

        BigDecimal result = null;
        BigDecimal baseFare = CityRideDataset.getBaseFare(fromZone, toZone, timeBand);

        if (baseFare == null) {
            System.out.println("Error: Invalid zone combination.");
        } else {
            BigDecimal discountRate = CityRideDataset.DISCOUNT_RATE.get(passengerType);
            BigDecimal discountAmount = baseFare.multiply(discountRate)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal discountedFare = baseFare.subtract(discountAmount)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal currentSpending = dailySpending.getOrDefault(passengerType, BigDecimal.ZERO);
            BigDecimal cap = CityRideDataset.DAILY_CAP.get(passengerType);
            BigDecimal remaining = cap.subtract(currentSpending);

            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Daily cap reached for " + passengerType + ". Free journey.");
                result = BigDecimal.ZERO;
            } else if (discountedFare.compareTo(remaining) > 0) {
                System.out.println("Fare capped: £" + remaining);
                result = remaining;
            } else {
                result = discountedFare;
            }
        }
        return result;
    }


    public BigDecimal getBaseFare(int fromZone, int toZone, CityRideDataset.TimeBand timeBand) {
        return CityRideDataset.getBaseFare(fromZone, toZone, timeBand);
    }

    public void addJourney(Journey j) {
        journeyList.add(j);

        if (j.getChargedFare() != null) {
            BigDecimal current = dailySpending.getOrDefault(j.getPassengerType(), BigDecimal.ZERO);
            dailySpending.put(j.getPassengerType(), current.add(j.getChargedFare()));
        }

        System.out.println("Journey added successfully.");
    }

    public void showAllJourneys() {
        if (journeyList.isEmpty()) {
            System.out.println("No journeys recorded yet.");
        } else {
            for (int i = 0; i < journeyList.size(); i++) {
                System.out.println((i + 1) + ". " + journeyList.get(i).toSummaryLine());
            }
        }
    }

    public Journey searchJourney(int fromZone, int toZone) {
        for (Journey j : journeyList) {
            if (j.getFromZone() == fromZone && j.getToZone() == toZone) {
                return j;
            }
        }
        return null;
    }

    public void deleteJourney(int index) {
        if (index >= 0 && index < journeyList.size()) {
            Journey removed = journeyList.remove(index);

            BigDecimal current = dailySpending.get(removed.getPassengerType());
            dailySpending.put(removed.getPassengerType(),
                    current.subtract(removed.getChargedFare()));

            System.out.println("Journey deleted.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    public void updateJourney(int index, int newStart, int newEnd,
                              CityRideDataset.TimeBand newTimeBand,
                              CityRideDataset.PassengerType newPassengerType) {

        if (index >= 0 && index < journeyList.size()) {
            Journey j = journeyList.get(index);

            BigDecimal old = dailySpending.get(j.getPassengerType());
            dailySpending.put(j.getPassengerType(),
                    old.subtract(j.getChargedFare()));

            BigDecimal baseFare = CityRideDataset.getBaseFare(newStart, newEnd, newTimeBand);
            BigDecimal chargedFare = calculateFare(newStart, newEnd, newTimeBand, newPassengerType);

            if (baseFare != null && chargedFare != null) {
                j.setFromZone(newStart);
                j.setToZone(newEnd);
                j.setTimeBand(newTimeBand);
                j.setPassengerType(newPassengerType);
                j.setBaseFare(baseFare);
                j.setChargedFare(chargedFare);

                BigDecimal updated = dailySpending.getOrDefault(newPassengerType, BigDecimal.ZERO);
                dailySpending.put(newPassengerType, updated.add(chargedFare));

                System.out.println("Journey updated.");
            }
        }
    }

    public void showDailySpending() {
        System.out.println("\n--- Daily Spending ---");
        for (CityRideDataset.PassengerType pt : CityRideDataset.PassengerType.values()) {
            BigDecimal spent = dailySpending.getOrDefault(pt, BigDecimal.ZERO);
            BigDecimal cap = CityRideDataset.DAILY_CAP.get(pt);
            System.out.println(pt + ": £" + spent + " / £" + cap);
        }
    }

    public List<Journey> getAllJourneys() {
        return journeyList;
    }

    public void setJourneys(List<Journey> journeys) {
        // Prevents null issues
        this.journeyList = (journeys != null) ? journeys : new ArrayList<>();

        // Resets daily spending
        dailySpending.clear();
        for (CityRideDataset.PassengerType pt : CityRideDataset.PassengerType.values()) {
            dailySpending.put(pt, BigDecimal.ZERO);
        }

        // Recalculates spending safely
        for (Journey j : journeyList) {
            if (j != null && j.getPassengerType() != null && j.getChargedFare() != null) {
                BigDecimal current = dailySpending.get(j.getPassengerType());
                dailySpending.put(
                        j.getPassengerType(),
                        current.add(j.getChargedFare())
                );
            }
        }
    }
}