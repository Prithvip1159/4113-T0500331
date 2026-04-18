import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Handles saving and loading data using JSON and CSV files

public class FileManager {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // json save journeys
    public static void saveJourneysToJSON(List<Journey> journeys, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(journeys, writer);
            System.out.println("Journeys saved to JSON.");
        } catch (IOException e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }
    // json load journeys
    public static List<Journey> loadJourneysFromJSON(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Journey[] arr = gson.fromJson(reader, Journey[].class);
            return new ArrayList<>(java.util.Arrays.asList(arr));
        } catch (IOException e) {
            System.out.println("Error loading JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    // csv export

    public static void exportJourneysToCSV(List<Journey> journeys, String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            writer.println(Journey.csvHeader());

            for (Journey j : journeys) {
                writer.println(j.toCsvLine());
            }

            System.out.println("Journeys exported to CSV: " + filePath);

        } catch (IOException e) {
            System.out.println("Error exporting journeys: " + e.getMessage());
        }
    }
    // csv import
    public static List<Journey> importJourneysFromCSV(String filePath) {
        List<Journey> journeys = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String journeyId = parts[0];
                String dateTime = parts[1];
                int fromZone = Integer.parseInt(parts[2]);
                int toZone = Integer.parseInt(parts[3]);

                CityRideDataset.TimeBand timeBand =
                        CityRideDataset.TimeBand.valueOf(parts[4]);

                CityRideDataset.PassengerType passengerType =
                        CityRideDataset.PassengerType.valueOf(parts[5]);

                int zonesCrossed = Integer.parseInt(parts[6]);
                BigDecimal baseFare = new BigDecimal(parts[7]);
                BigDecimal discount = new BigDecimal(parts[8]);
                BigDecimal chargedFare = new BigDecimal(parts[9]);

                Journey journey = new Journey(
                        journeyId,
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

                journeys.add(journey);
            }

            System.out.println("Journeys imported from CSV: " + filePath);

        } catch (IOException e) {
            System.out.println("Error importing journeys: " + e.getMessage());
        }

        return journeys;
    }
}