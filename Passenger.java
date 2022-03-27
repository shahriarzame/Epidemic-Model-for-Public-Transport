package assignments.assignment5;

public class Passenger {

    private int passengerId;
    private String homeStationId;
    private String workStationId;
    private int busId;
    private int age;
    private boolean vaccinated;
    private boolean mask;

    private InfectionStatus infectionStatus;
    private TravelStatus travelStatus = TravelStatus.outbound;

    private int ageGroup;
    private int infectionDay;
    private String infectionLocation;

    public Passenger() {}

    public Passenger(int passengerId){this.passengerId = passengerId;}

    public int getPassengerId() {return passengerId;}

    public void setPassengerId(int passengerId) {this.passengerId = passengerId;}

    public String getHomeStationId() {return homeStationId;}

    public void setHomeStationId(String homeStationId) {this.homeStationId = homeStationId;}

    public String getWorkStationId() {return workStationId;}

    public void setWorkStationId(String workStationId) {this.workStationId = workStationId;}

    public int getBusId() {return busId;}

    public void setBusId(int busId) {this.busId = busId;}

    public int getAge() { return age; }

    public void setAge(int age) {this.age = age;}

    public boolean isVaccinated() {return vaccinated;}

    public void setVaccinated(boolean vaccinated) { this.vaccinated = vaccinated; }

    public boolean isMasked() {return mask;}

    public void setMask(boolean mask) {this.mask = mask;}

    public int getAgeGroup() {return ageGroup;}

    public void setAgeGroup(int ageGroup) {this.ageGroup = ageGroup;}

    public TravelStatus getTravelStatus() {return travelStatus;}

    public void setTravelStatus(TravelStatus travelStatus) {this.travelStatus = travelStatus;}

    public InfectionStatus getInfectionStatus() {return infectionStatus;}

    public void setInfectionStatus(InfectionStatus infectionStatus) {this.infectionStatus = infectionStatus;}

    public int getInfectionDay() {
        return infectionDay;
    }

    public void setInfectionDay(int infectionDay) {this.infectionDay = infectionDay;}

    public String getInfectionLocation() {return infectionLocation;}

    public void setInfectionLocation(String infectionLocation) {this.infectionLocation = infectionLocation;}
}
