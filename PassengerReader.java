package assignments.assignment5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PassengerReader {

    private Map<Integer, Passenger> passengerMap = new HashMap<>();
    public Map<Integer, Passenger> getPassengerMap() {
        return passengerMap;
    }

    public void readData(String s) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(s));
            String newline;

            //read header
            String[] str = reader.readLine().split(",");
            int indexPassengerId = findIndexInArray("passengerId", str);
            int indexHomeStationId = findIndexInArray("homeStationId", str);
            int indexWorkStationId = findIndexInArray("workStationId", str);
            int indexBusId = findIndexInArray("busId", str);
            int indexAge = findIndexInArray("age", str);
            int indexVaccinated = findIndexInArray("vaccinated", str);
            int indexMask = findIndexInArray("mask", str);
            int indexInfectionStatus = findIndexInArray("infectionStatus", str);

            //read body
            int count = 1;
            while ((newline = reader.readLine()) != null) {
                String[] strArray = newline.split(",");
                Passenger passenger = new Passenger(count);

                passenger.setPassengerId(Integer.parseInt(strArray[indexPassengerId]));
                passenger.setHomeStationId(strArray[indexHomeStationId]);
                passenger.setWorkStationId(strArray[indexWorkStationId]);
                passenger.setBusId(Integer.parseInt(strArray[indexBusId]));
                passenger.setAge(Integer.parseInt(strArray[indexAge])); // NEED TO STANDARDIZE
                passenger.setVaccinated(Boolean.parseBoolean(strArray[indexVaccinated]));
                passenger.setMask(Boolean.parseBoolean(strArray[indexMask]));
                passenger.setInfectionStatus(InfectionStatus.valueOf(strArray[indexInfectionStatus]));

                passengerMap.put(count, passenger);
                count++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Wrong path: " + s);
        } catch (IOException e) {
            System.out.println("IOException. Please check input file: " + s);
        }
    }

    private int findIndexInArray(String colname, String[] str) {
        int position = -1;
        for (int a = 0; a < str.length; a++) {
            if (str[a].equalsIgnoreCase(colname)) {
                position = a;
            }
        }
        if (position == -1) throw new RuntimeException("Could not find column called: " + colname);
        return position;
    }


    public void processPassengers() {
        for (Passenger passenger : passengerMap.values()) {
            //convert age to ageGroup
            int age = passenger.getAge();
            if (age >= 18 && age <= 29) {passenger.setAgeGroup(1);}
            else if (age >= 30 && age <= 39){passenger.setAgeGroup(2);}
            else if (age >= 40 && age <= 49){passenger.setAgeGroup(3);}
            else if (age >= 50 && age <= 59){passenger.setAgeGroup(4);}
            else if (age >= 60 && age <= 69){passenger.setAgeGroup(5);}
        }
    }

    public void checking() {for (Passenger passenger : passengerMap.values()) {
        System.out.println(passenger.getPassengerId());
        System.out.println(passenger.getAge());
        System.out.println(passenger.getAgeGroup());
        System.out.println("NEW");
    }}

    public Set<Passenger> initiatePassengersAtHomeStation(String e) {
        Set<Passenger> set = new HashSet<>();
        for (Passenger passenger : passengerMap.values()) {
            if (passenger.getHomeStationId().equalsIgnoreCase(e) ) {
                set.add(passenger);
            }}
        return set;
    }}