import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Journey {
    private String startZone;
    private String endZone;
    private double fare;

    public Journey(String startZone, String endZone, double fare) {
        this.startZone = startZone;
        this.endZone = endZone;
        this.fare = fare;
    }

    public String getStartZone() { return startZone; }
    public String getEndZone() { return endZone; }
    public double getFare() { return fare; }

    public void setStartZone(String startZone) { this.startZone = startZone; }
    public void setEndZone(String endZone) { this.endZone = endZone; }
    public void setFare(double fare) { this.fare = fare; }

    public void displayJourney() {
        System.out.println("From: " + startZone + " -> To: " + endZone + " | Fare: £" + fare);
    }
}

class JourneyManager {
    private List<Journey> journeyList = new ArrayList<>();

    public void addJourney(Journey j) {
        journeyList.add(j);
        System.out.println("Journey added successfully.");
    }

    public void showAllJourneys() {
        if (journeyList.isEmpty()) {
            System.out.println("No journeys recorded yet.");
        } else {
            for (Journey j : journeyList) {
                j.displayJourney();
            }
        }
    }
}

public class CityRideLite {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        JourneyManager jm = new JourneyManager();
        int choice;

        do {
            System.out.println("\nCityRideLite Menu");
            System.out.println("1. Add Journey");
            System.out.println("2. List Journeys");
            System.out.println("3. Search Journey");
            System.out.println("4. Update Journey");
            System.out.println("5. Delete Journey");
            System.out.println("6. Calculate Fare");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear newline

            switch(choice) {

                case 1:
                    System.out.println("Enter start zone:");
                    String start = sc.nextLine();

                    System.out.println("Enter end zone:");
                    String end = sc.nextLine();

                    // simple fare placeholder (you can improve later)
                    double fare = start.equalsIgnoreCase(end) ? 1.50 : 2.50;

                    jm.addJourney(new Journey(start, end, fare));
                    break;


            }

        } while(choice != 7);

        sc.close();
    }
}
