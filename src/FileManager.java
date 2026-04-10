import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // Save rider profile to JSON
    public static void saveProfile(RiderProfile profile, String filePath) {
        try {
            gson.toJson(profile, new FileWriter(filePath));
            System.out.println("Profile saved successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving profile: " + e.getMessage());
        }
    }

    // Load rider profile from JSON
    public static RiderProfile loadProfile(String filePath) {
        try {
            return gson.fromJson(new FileReader(filePath), RiderProfile.class);
        } catch (IOException e) {
            System.out.println("Error loading profile: " + e.getMessage());
            return null;
        }
    }

    // Export journeys to CSV
    public static void exportJourneysToCSV(List<Journey> journeys, String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            writer.println("StartZone,EndZone,TimeBand,PassengerType,BaseFare,FinalFare");
            for (Journey journey : journeys) {
                writer.printf("%d,%d,%s,%s,%.2f,%.2f%n",
                        journey.getStartZone(),
                        journey.getEndZone(),
                        journey.getTimeBand(),
                        journey.getPassengerType(),
                        journey.getBaseFare(),
                        journey.getFinalFare());
            }
            System.out.println("Journeys exported successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting journeys: " + e.getMessage());
        }
    }

    // Import journeys from CSV
    public static List<Journey> importJourneysFromCSV(String filePath) {
        List<Journey> journeys = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int startZone = Integer.parseInt(parts[0]);
                int endZone = Integer.parseInt(parts[1]);
                CityRideDataset.TimeBand timeBand = CityRideDataset.TimeBand.valueOf(parts[2]);
                CityRideDataset.PassengerType passengerType = CityRideDataset.PassengerType.valueOf(parts[3]);
                BigDecimal baseFare = new BigDecimal(parts[4]);
                BigDecimal finalFare = new BigDecimal(parts[5]);

                journeys.add(new Journey(startZone, endZone, timeBand, passengerType, baseFare, finalFare));
            }
            System.out.println("Journeys imported successfully from " + filePath);
        } catch (IOException e) {
            System.out.println("Error importing journeys: " + e.getMessage());
        }
        return journeys;
    }
}