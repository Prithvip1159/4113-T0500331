# IY4113 Part 2 Milestone 3

| Assessment Details | Please Complete All Details                                      |
| ------------------ | ---------------------------------------------------------------- |
| Group              | B                                                                |
| Module Title       | Applied Software Engineering using Object-Orientated Programming |
| Assessment Type    | Stage 2                                                          |
| Module Tutor Name  | Jonathan Shore                                                   |
| Student ID Number  | P500331                                                          |
| Date of Submission | 06/04/2026                                                       |
| Word Count         | 2648                                                             |
| GitHub Link        |                                                                  |

- [x] *I confirm that this assignment is my own work. Where I have referred to academic sources, I have provided in-text citations and included the sources in
  the final reference list.*

- [x] *Where I have used AI, I have cited and referenced appropriately.

------------------------------------------------------------------------------------------------------------------------------

### Research (minimum of 2, at least 3)

---

**Title of research :**

Using JSON in Java (Saving and Loading Data)

**Reference (link)**: https://www.baeldung.com/java-json

**How does the research help with coding practise?:**

This research helped me understand how to save and load data using JSON files. It showed how to store user profiles so they can be used again later. It also explained how to convert objects into JSON and read them back into the program.

**Key coding ideas you could reuse in your program:**

-Converting objects into JSON format

-Saving rider profiles and settings

**Screenshot of research:**




![](C:\Users\Prithvi\AppData\Roaming\marktext\images\2026-04-06-23-19-23-image.png)

![](C:\Users\Prithvi\AppData\Roaming\marktext\images\2026-04-06-23-29-55-image.png)

------------------------------------------------------------------------------------------------------------------------------

**Title of research:**

Reading and Writing CSV Files in Java

**Reference (link):** https://www.geeksforgeeks.org/java/working-with-json-data-in-java/

**How does the research help with coding practise?:**

This research helped me understand how to read and write CSV files in Java. It is useful because the system needs to import journeys and export reports. It also showed how to use BufferedReader and FileWriter to handle CSV data.

**Key coding ideas you could reuse in your program:**

-Reading CSV files using BufferedReader

-Exporting reports and journey data

**Screenshot of research:**

![](C:\Users\Prithvi\AppData\Roaming\marktext\images\2026-04-06-23-23-47-image.png)

![](C:\Users\Prithvi\AppData\Roaming\marktext\images\2026-04-06-23-31-03-image.png)

------------------------------------------------------------------------------------------------------------------------------

**Title of research:**

Inheritance and Polymorphism in Java

**Reference (link):** https://www.oracle.com/java/technologies/oop.html

**How does the research help with coding practise?:**

This research helped me understand inheritance and polymorphism in Java. It showed how different classes can share common features but also have different behaviours. This helps make the program more organised and reduces repeated code.

**Key coding ideas you could reuse in your program:**

-Creating base and derived classes

-Reusing code through inheritance

**Screenshot of research:**

![](C:\Users\Prithvi\AppData\Roaming\marktext\images\2026-04-06-23-26-17-image.png)

![](C:\Users\Prithvi\AppData\Roaming\marktext\images\2026-04-06-23-31-58-image.png)

---

**Title of research:**

Exception Handling and Input Validation in Java

**Reference (link):** https://docs.oracle.com/javase/tutorial/essential/exceptions/definition.html

**How does the research help with coding practise?:**

This research helped me understand exception handling in Java. It showed how to use try-catch to handle errors and stop the program from crashing. This helped me validate user input and show clear error messages.

**Key coding ideas you could reuse in your program:**

-Using try-catch blocks to handle exceptions

-Displaying meaningful error messages to the user

**Screenshot of research:**

![](C:\Users\Prithvi\AppData\Roaming\marktext\images\2026-04-06-23-29-21-image.png)

---

### Program Code:

------------------------------------------------------------------------------------------------------------------------------

*Program code goes here:*

import java.util.ArrayList;  
import java.util.List;  
import java.util.Scanner;  
import java.math.BigDecimal;  
import java.math.RoundingMode;  
import java.util.HashMap;  
import java.util.Map;  
import java.io.Serializable;  
import java.io.*;  

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
    private int fromZone;  
    private int toZone;  
    private CityRideDataset.TimeBand timeBand;  
    private CityRideDataset.PassengerType passengerType;  
    private BigDecimal baseFare;  
    private BigDecimal chargedFare;  

    public Journey(int fromZone, int toZone, CityRideDataset.TimeBand timeBand,  
                   CityRideDataset.PassengerType passengerType, BigDecimal baseFare, BigDecimal chargedFare) {  
        this.fromZone = fromZone;  
        this.toZone = toZone;  
        this.timeBand = timeBand;  
        this.passengerType = passengerType;  
        this.baseFare = baseFare;  
        this.chargedFare = chargedFare;  
    }  
    
    // Getters  
    public int getfromZone() { return fromZone; }  
    public int gettoZone() { return toZone; }  
    public CityRideDataset.TimeBand getTimeBand() { return timeBand; }  
    public CityRideDataset.PassengerType getPassengerType() { return passengerType; }  
    public BigDecimal getBaseFare() { return baseFare; }  
    public BigDecimal getchargedFare() { return chargedFare; }  
    
    //  Setters (used by updateJourney in JourneyManager)  
    
    public void setfromZone(int fromZone) { this.fromZone = fromZone; }  
    public void settoZone(int toZone) { this.toZone = toZone; }  
    public void setTimeBand(CityRideDataset.TimeBand timeBand) { this.timeBand = timeBand; }  
    public void setPassengerType(CityRideDataset.PassengerType passengerType) { this.passengerType = passengerType; }  
    public void setBaseFare(BigDecimal baseFare) { this.baseFare = baseFare; }  
    public void setchargedFare(BigDecimal chargedFare) { this.chargedFare = chargedFare; }  

//     Prints a formatted single-line summary of this journey  
    public void displayJourney() {  
        System.out.println("Zone " + fromZone + " -> Zone " + toZone  
                + " | " + timeBand + " | " + passengerType  
                + " | Base: £" + baseFare + " | Final: £" + chargedFare);  
    }  
}  

class RiderProfile implements Serializable {  
    private String name;  
    private CityRideDataset.PassengerType passengerType;  
    private String paymentOption;  

    public RiderProfile(String name, CityRideDataset.PassengerType passengerType, String paymentOption) {  
        this.name = name;  
        this.passengerType = passengerType;  
        this.paymentOption = paymentOption;  
    }  
    
    // Getters and Setters  
    public String getName() { return name; }  
    public void setName(String name) { this.name = name; }  
    
    public CityRideDataset.PassengerType getPassengerType() { return passengerType; }  
    public void setPassengerType(CityRideDataset.PassengerType passengerType) { this.passengerType = passengerType; }  
    
    public String getPaymentOption() { return paymentOption; }  
    public void setPaymentOption(String paymentOption) { this.paymentOption = paymentOption; }  
    
    @Override  
    public String toString() {  
        return "Name: " + name + "\nPassenger Type: " + passengerType + "\nPayment Option: " + paymentOption;  
    }  

}  

class FileManager {  
    private static final ObjectMapper objectMapper = new ObjectMapper()  
            .enable(SerializationFeature.INDENT_OUTPUT);  

    // Save rider profile to JSON  
    public static void saveProfile(RiderProfile profile, String filePath) {  
        try {  
            objectMapper.writeValue(new File(filePath), profile);  
            System.out.println("Profile saved successfully to " + filePath);  
        } catch (IOException e) {  
            System.out.println("Error saving profile: " + e.getMessage());  
        }  
    }  
    
    // Load rider profile from JSON  
    public static RiderProfile loadProfile(String filePath) {  
        try {  
            return objectMapper.readValue(new File(filePath), RiderProfile.class);  
        } catch (IOException e) {  
            System.out.println("Error loading profile: " + e.getMessage());  
            return null;  
        }  
    }  
    
    // Export journeys to CSV  
    public static void exportJourneysToCSV(List<Journey> journeys, String filePath) {  
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {  
            writer.println("fromZone,toZone,TimeBand,PassengerType,BaseFare,chargedFare");  
            for (Journey journey : journeys) {  
                writer.printf("%d,%d,%s,%s,%.2f,%.2f%n",  
                        journey.getfromZone(),  
                        journey.gettoZone(),  
                        journey.getTimeBand(),  
                        journey.getPassengerType(),  
                        journey.getBaseFare(),  
                        journey.getchargedFare());  
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
                int fromZone = Integer.parseInt(parts[0]);  
                int toZone = Integer.parseInt(parts[1]);  
                CityRideDataset.TimeBand timeBand = CityRideDataset.TimeBand.valueOf(parts[2]);  
                CityRideDataset.PassengerType passengerType = CityRideDataset.PassengerType.valueOf(parts[3]);  
                BigDecimal baseFare = new BigDecimal(parts[4]);  
                BigDecimal chargedFare = new BigDecimal(parts[5]);  
    
                journeys.add(new Journey(fromZone, toZone, timeBand, passengerType, baseFare, chargedFare));  
            }  
            System.out.println("Journeys imported successfully from " + filePath);  
        } catch (IOException e) {  
            System.out.println("Error importing journeys: " + e.getMessage());  
        }  
        return journeys;  
    }  

}  
class AdminManager {  
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
     * Calculates final fare by applying passenger discount then enforcing the     * daily spending cap. Uses a single result variable to satisfy the NTIC     * single-return guideline.     */  
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
        dailySpending.put(j.getPassengerType(), currentSpending.add(j.getchargedFare()));  
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
    public Journey searchJourney(int fromZone, int toZone) {  
        Journey result = null;  
        for (Journey current : journeyList) {  
            if (current.getfromZone() == fromZone  
                    && current.gettoZone() == toZone) {  
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
            dailySpending.put(removed.getPassengerType(), currentSpending.subtract(removed.getchargedFare()));  
            System.out.println("Journey deleted successfully.");  
        } else {  
            System.out.println("Invalid journey number.");  
        }  
    }  
    
    /*  
     * Replaces an existing journey's details with new values.     * Old fare is subtracted before the new fare is added so the cap     * remains accurate across updates.     */    public void updateJourney(int index, int newStart, int newEnd,  
                              CityRideDataset.TimeBand newTimeBand,  
                              CityRideDataset.PassengerType newPassengerType) {  
        if (index >= 0 && index < journeyList.size()) {  
            Journey j = journeyList.get(index);  
    
            /* Remove old fare from spending before recalculating */  
            BigDecimal oldSpending = dailySpending.get(j.getPassengerType());  
            dailySpending.put(j.getPassengerType(), oldSpending.subtract(j.getchargedFare()));  
    
            BigDecimal baseFare = CityRideDataset.getBaseFare(newStart, newEnd, newTimeBand);  
            BigDecimal chargedFare = calculateFare(newStart, newEnd, newTimeBand, newPassengerType);  
    
            if (chargedFare != null && baseFare != null) {  
                j.setfromZone(newStart);  
                j.settoZone(newEnd);  
                j.setTimeBand(newTimeBand);  
                j.setPassengerType(newPassengerType);  
                j.setBaseFare(baseFare);  
                j.setchargedFare(chargedFare);  
    
                BigDecimal newSpending = dailySpending.getOrDefault(newPassengerType, BigDecimal.ZERO);  
                dailySpending.put(newPassengerType, newSpending.add(chargedFare));  
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
public class CityRideLite {  
    private Scanner scanner;  
    private JourneyManager journeyManager;  
    private RiderProfile riderProfile;  

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
                    case 1: adminManager.viewConfig(); break;  
                    case 2: adminManager.updateConfig(); break;  
                    case 3: System.out.println("Exiting Admin Menu."); break;  
                    default: System.out.println("Invalid choice. Try again.");  
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
                case 1: handleAddJourney(); break;  
                case 2: journeyManager.showAllJourneys(); break;  
                case 3: handleSearchJourney(); break;  
                case 4: handleUpdateJourney(); break;  
                case 5: handleDeleteJourney(); break;  
                case 6: handleCalculateFare(); break;  
                case 7: journeyManager.showDailySpending(); break;  
                case 8: handleAdminMenu(); break;  
                case 9: System.out.println("Exiting CityRideLite. Goodbye!"); break;  
                default: System.out.println("Invalid choice. Try again.");  
            }  
        } while (choice != 9);  
    }  
    
    public static void main(String[] args) {  
        CityRideLite app = new CityRideLite();  
        app.run();  
    }  

}

------------------------------------------------------------------------------------------------------------------------------

### Updated Gantt Chart

------------------------------------------------------------------------------------------------------------------------------

![Screenshot 2026-04-06 225328.png](C:\Users\Prithvi\OneDrive\Pictures\Screenshots\Screenshot%202026-04-06%20225328.png)

------------------------------------------------------------------------------------------------------------------------------

### Diary Entries (at least 4)

------------------------------------------------------------------------------------------------------------------------------

### 30/03/2026

Today I focused on researching how to use JSON in Java. This was important because the system needs to save and load rider profiles and configuration data. From the research, I understood how data can be stored in JSON format and reused later instead of being lost when the program closes.

I also learned how objects can be converted into JSON and then read back into the program. This helped me understand how to implement persistent storage, which is a key requirement in Part 2.

At first, I found it confusing to understand how data is converted between objects and JSON format. However, after reading examples, I understood the basic idea. This research helped me plan how I will save user profiles and system settings in my program.

------------------------------------------------------------------------------------------------------------------------------

### 31/03/2026

In this session, I researched how to read and write CSV files in Java. This is important because the system needs to import journey data and export reports.

I learned how to use BufferedReader to read data from a file and FileWriter to write data into a CSV file. This helped me understand how journey data can be stored and later used for reports.

One issue I faced was understanding how to split CSV data correctly using commas. At first, I was not sure how the data is separated, but after testing examples, I understood how it works.

This research helped me prepare for implementing features like importing journeys and exporting summaries in my program.

---

### 01/04/2026

Today I researched inheritance and polymorphism in Java. This helped me understand how to design my program in a structured way.

I learned that inheritance allows one class to reuse features from another class, and polymorphism allows different classes to behave differently using the same method. This is useful for my program because different roles like Rider and Admin can share common features but also have different functions.

At first, I found polymorphism slightly confusing, especially how methods behave differently. After reviewing examples, I understood how it works.

This research helped me improve the organisation of my program and reduce repeated code.

---

### 02/04/2026

In this session, I researched exception handling in Java. This is important because the system must validate user input and handle errors properly.

I learned how to use try-catch blocks to handle errors and prevent the program from crashing. This helped me understand how to manage invalid inputs such as incorrect IDs or wrong values.

One challenge I faced was understanding when to use try-catch and how to display useful error messages. After practising examples, I improved my understanding.

This research helped me make my program more reliable and user-friendly by ensuring errors are handled correctly.

---
