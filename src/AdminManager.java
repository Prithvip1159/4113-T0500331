import java.math.BigDecimal;
import java.util.Scanner;

// Handles admin authentication and configuration management

public class AdminManager {
    private static final String ADMIN_PASSWORD = "admin123";

    public boolean authenticate(String password) {
        return ADMIN_PASSWORD.equals(password);
    }

    public void viewConfig() {
        System.out.println("\n--- Current Configuration ---");
        System.out.println("Base Fares: " + CityRideDataset.BASE_FARE);
        System.out.println("Discount Rates: " + CityRideDataset.DISCOUNT_RATE);
        System.out.println("Daily Caps: " + CityRideDataset.DAILY_CAP);
    }

    public void updateConfig() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start zone: ");
        int fromZone = scanner.nextInt();
        System.out.print("Enter end zone: ");
        int toZone = scanner.nextInt();
        System.out.print("Enter time band (PEAK/OFF_PEAK): ");
        String timeBandStr = scanner.next();
        System.out.print("Enter new base fare: ");
        BigDecimal newFare = scanner.nextBigDecimal();

        CityRideDataset.TimeBand timeBand = CityRideDataset.TimeBand.valueOf(timeBandStr.toUpperCase());
        String key = CityRideDataset.key(fromZone, toZone, timeBand);
        CityRideDataset.BASE_FARE.put(key, newFare);

        System.out.println("Base fare updated successfully.");
    }
}