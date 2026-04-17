import java.math.BigDecimal;


/*
 This represents a single transit journey taken by a rider.
 This class only holds journey data , all the calculations are done in JourneyManager
 */
public class Journey {

    // attributes for the journey class include
    private String journeyId;
    private String journeyDateTime;
    private int fromZone;
    private int toZone;
    private CityRideDataset.TimeBand timeBand;
    private CityRideDataset.PassengerType passengerType;
    private int zonesCrossed;
    private BigDecimal baseFare;
    private BigDecimal discountApplied;
    private BigDecimal chargedFare;


    // Constructor
    public Journey(String journeyId,
                   String journeyDateTime,
                   int fromZone,
                   int toZone,
                   CityRideDataset.TimeBand timeBand,
                   CityRideDataset.PassengerType passengerType,
                   int zonesCrossed,
                   BigDecimal baseFare,
                   BigDecimal discountApplied,
                   BigDecimal chargedFare) {

        this.journeyId = journeyId;
        this.journeyDateTime = journeyDateTime;
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.timeBand = timeBand;
        this.passengerType = passengerType;
        this.zonesCrossed = zonesCrossed;
        this.baseFare = baseFare;
        this.discountApplied = discountApplied;
        this.chargedFare = chargedFare;
    }


    // Getters

    public String getJourneyId() {
        return journeyId;
    }

    public String getJourneyDateTime() {
        return journeyDateTime;
    }

    public int getFromZone() {
        return fromZone;
    }

    public int getToZone() {
        return toZone;
    }

    public CityRideDataset.TimeBand getTimeBand() {
        return timeBand;
    }

    public CityRideDataset.PassengerType getPassengerType() {
        return passengerType;
    }

    public int getZonesCrossed() {
        return zonesCrossed;
    }

    public BigDecimal getBaseFare() {
        return baseFare;
    }

    public BigDecimal getDiscountApplied() {
        return discountApplied;
    }

    public BigDecimal getChargedFare() {
        return chargedFare;
    }


    // Setters - this is used by updateJourney in JourneyManager

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public void setJourneyDateTime(String journeyDateTime) {
        this.journeyDateTime = journeyDateTime;
    }

    public void setFromZone(int fromZone) {
        this.fromZone = fromZone;
    }

    public void setToZone(int toZone) {
        this.toZone = toZone;
    }

    public void setTimeBand(CityRideDataset.TimeBand timeBand) {
        this.timeBand = timeBand;
    }

    public void setPassengerType(CityRideDataset.PassengerType passengerType) {
        this.passengerType = passengerType;
    }

    public void setZonesCrossed(int zonesCrossed) {
        this.zonesCrossed = zonesCrossed;
    }

    public void setBaseFare(BigDecimal baseFare) {
        this.baseFare = baseFare;
    }

    public void setDiscountApplied(BigDecimal discountApplied) {
        this.discountApplied = discountApplied;
    }

    public void setChargedFare(BigDecimal chargedFare) {
        this.chargedFare = chargedFare;
    }



    // Prints a quick one-line summary directly to console
    public void displayJourney() {
        System.out.println(toSummaryLine());
    }

    // Returns a one-line summary string
    public String toSummaryLine() {
        return String.format("[%s] %s | Zone %d → %d | %s | %s | Zones: %d | Base: £%s | Discount: £%s | Charged: £%s",
                journeyId,
                journeyDateTime,
                fromZone,
                toZone,
                timeBand,
                passengerType,
                zonesCrossed,
                baseFare.toPlainString(),
                discountApplied.toPlainString(),
                chargedFare.toPlainString());
    }

    // Returns a detailed multi-line string and is used when searching for a specific journey
    public String toDetailString() {
        return String.format(
                "Journey ID     : %s%n" +
                        "Date/Time      : %s%n" +
                        "From Zone      : %d%n" +
                        "To Zone        : %d%n" +
                        "Zones Crossed  : %d%n" +
                        "Time Band      : %s%n" +
                        "Passenger Type : %s%n" +
                        "Base Fare      : £%s%n" +
                        "Discount       : £%s%n" +
                        "Charged Fare   : £%s",
                journeyId,
                journeyDateTime,
                fromZone,
                toZone,
                zonesCrossed,
                timeBand,
                passengerType,
                baseFare.toPlainString(),
                discountApplied.toPlainString(),
                chargedFare.toPlainString());
    }

    // Returns journey data as a single CSV line and is used by FileManager for export
    public String toCsvLine() {
        return String.join(",",
                journeyId,
                journeyDateTime,
                String.valueOf(fromZone),
                String.valueOf(toZone),
                timeBand.name(),
                passengerType.name(),
                String.valueOf(zonesCrossed),
                baseFare.toPlainString(),
                discountApplied.toPlainString(),
                chargedFare.toPlainString());
    }

    // Returns the CSV header line static because it belongs to the class not one object
    public static String csvHeader() {
        return "journeyId,dateTime,fromZone,toZone,timeBand," +
                "passengerType,zonesCrossed,baseFare,discountApplied,chargedFare";
    }

    // Default toString returns the detailed view  useful for debugging with System.out.println(journey)
    @Override
    public String toString() {
        return toDetailString();
    }
}
