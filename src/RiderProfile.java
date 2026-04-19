import java.io.Serializable;

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
