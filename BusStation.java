package assignments.assignment5;

import info.gridworld.actor.Rock;

import java.util.HashSet;
import java.util.Set;

public class BusStation extends Rock {

    String stationID;

    public String getStationID() {return stationID;}

    public void setStationID(String stationID) {this.stationID = stationID;}

    Set<Passenger> waitingPassengerSet = new HashSet<>();

    public Set<Passenger> getWaitingPassengerSet() {return waitingPassengerSet;}

}
