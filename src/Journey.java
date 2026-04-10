import java.math.BigDecimal;

public class Journey {
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

    // Getters
    public int getStartZone() { return startZone; }
    public int getEndZone() { return endZone; }
    public CityRideDataset.TimeBand getTimeBand() { return timeBand; }
    public CityRideDataset.PassengerType getPassengerType() { return passengerType; }
    public BigDecimal getBaseFare() { return baseFare; }
    public BigDecimal getFinalFare() { return finalFare; }

    //  Setters (used by updateJourney in JourneyManager)

    public void setStartZone(int startZone) { this.startZone = startZone; }
    public void setEndZone(int endZone) { this.endZone = endZone; }
    public void setTimeBand(CityRideDataset.TimeBand timeBand) { this.timeBand = timeBand; }
    public void setPassengerType(CityRideDataset.PassengerType passengerType) { this.passengerType = passengerType; }
    public void setBaseFare(BigDecimal baseFare) { this.baseFare = baseFare; }
    public void setFinalFare(BigDecimal finalFare) { this.finalFare = finalFare; }

    //     Prints a formatted single-line summary of this journey
    public void displayJourney() {
        System.out.println("Zone " + startZone + " -> Zone " + endZone
                + " | " + timeBand + " | " + passengerType
                + " | Base: £" + baseFare + " | Final: £" + finalFare);
    }
}