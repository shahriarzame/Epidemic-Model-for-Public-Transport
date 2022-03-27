package assignments.assignment5;

import java.io.*;
import java.util.*;

public class HealthCenter {

    private static int dayCount;
    private static final int daysInYear = 365;
    public static int yearCount;
    private static final double QUARANTINE_PROB = 0.4;
    static Random random = new Random(EpidemicRunner.MATRICULATION);


    private static final Map<Integer, Probability> probabilityMapH = new HashMap<>();
    private static Map<Integer, Passenger> passengerMapH = new HashMap<>();

    public Map<Integer, Probability> getProbabilityMapH() {
        return probabilityMapH;
    }

    public void setPassengerMapH(Map<Integer, Passenger> passengerMapH) {
        HealthCenter.passengerMapH = passengerMapH;
    }

   //End of day
    public static boolean endOfDay() {
        boolean endOfDay = false;
        int count = 0;
        for (Passenger passenger : passengerMapH.values()) {
            if (passenger.getTravelStatus() == TravelStatus.home) {
                count++;
            }}

        if (count == passengerMapH.size()){endOfDay = true;dayCount++;}
        if(dayCount > daysInYear){yearCount++;dayCount = 1;}

        return endOfDay;
    }

    //Update Passenger InfectionStatus
    public static void updateInfectionStatus(){
        for (Passenger passenger : passengerMapH.values()) {
            if (passenger.getInfectionStatus().equals(InfectionStatus.quarantine)) {
                if(passenger.getInfectionDay() == 7){
                    passenger.setInfectionStatus(InfectionStatus.recovered);
                    passenger.setInfectionDay(0);
                }
                else {passenger.setInfectionDay(passenger.getInfectionDay()+1);}
            }
            if (passenger.getInfectionStatus().equals(InfectionStatus.infected)) {
                if(passenger.getInfectionDay() == 14){
                    passenger.setInfectionStatus(InfectionStatus.recovered);
                    passenger.setInfectionDay(0);
                }
                else {passenger.setInfectionDay(passenger.getInfectionDay()+1);}
            }}}

    public static void transitionToQuarantine(){
        for (Passenger passenger : passengerMapH.values()) {
            if (passenger.getInfectionStatus() == InfectionStatus.infected){
                if (QUARANTINE_PROB > random.nextFloat())
                {passenger.setInfectionStatus(InfectionStatus.quarantine);
                    passenger.setInfectionDay(0);
                }}}}


    public static void updateTravelStatus(){

        for (Passenger passenger : passengerMapH.values()) {
            if (passenger.getInfectionStatus() != InfectionStatus.quarantine){
                passenger.setTravelStatus(TravelStatus.outbound);
            }}}

    public static void writeOutData(String filePath) throws IOException {

        FileWriter fw = new FileWriter(EpidemicRunner.outputFilePath + "_Year(" + yearCount + ")_QuaProb(" + QUARANTINE_PROB + ").csv",true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter writer = new PrintWriter(bw);

        if(dayCount == 0) {
            writer.print("day");
            writer.print(",");
            writer.print("passenger_id");
            writer.print(",");
            writer.print("infection_status");
            writer.print(",");
            writer.print("infection_day"); //if applies
            writer.print(",");
            writer.println("infection_location");//(bus id/station id) if applies
        }

        for(Passenger passenger : passengerMapH.values()){
            writer.print(dayCount);
            writer.print(",");
            writer.print(passenger.getPassengerId());
            writer.print(",");
            writer.print(passenger.getInfectionStatus());
            writer.print(",");
            writer.print(passenger.getInfectionDay());
            writer.print(",");
            writer.println(passenger.getInfectionLocation());
        }
        writer.close();
    }

    public static void writeOutCumulativeData(String filePath) throws IOException {

        FileWriter fw = new FileWriter(EpidemicRunner.outputFilePath + "_Cumulative_" + "Year(" + yearCount + ")_XXXXXXX" + ".csv",true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter writer = new PrintWriter(bw);

        if(dayCount == 0 || (yearCount>0 && dayCount == 1)) {
            writer.print("day");
            writer.print(",");
            writer.print("Cumulative_None");
            writer.print(",");
            writer.print("Cumulative_Infected");
            writer.print(",");
            writer.print("Cumulative_Quarantined");
            writer.print(",");
            writer.println("Cumulative_Recovered");
        }

        int cumulativeNone = 0;
        int cumulativeInfected = 0;
        int cumulativeQuarantined = 0;
        int cumulativeRecovered = 0;

        for (Passenger passenger : passengerMapH.values()) {
            if (passenger.getInfectionStatus() == InfectionStatus.none){cumulativeNone++;}
            if (passenger.getInfectionStatus() == InfectionStatus.infected){cumulativeInfected++;}
            if (passenger.getInfectionStatus() == InfectionStatus.quarantine){cumulativeQuarantined++;}
            if (passenger.getInfectionStatus() == InfectionStatus.recovered){cumulativeRecovered++;}
        }

        writer.print(dayCount);
        writer.print(",");
        writer.print(cumulativeNone);
        writer.print(",");
        writer.print(cumulativeInfected);
        writer.print(",");
        writer.print(cumulativeQuarantined);
        writer.print(",");
        writer.println(cumulativeRecovered);

        writer.close();
    }


    //Update Vaccination Status
    public static void updateVaccination(double vaccineShare) {


        Passenger[] arrayPassengers = passengerMapH.values().toArray(new Passenger[0]);
        Passenger[] toBeVaccinedPassengers = new Passenger[(int) (passengerMapH.size()* vaccineShare)];
        Set<Integer> randomNumberSet = new HashSet<>();
            //Pick Random Contacts in Container
            for (int i = 0; i < (int) (passengerMapH.size()* vaccineShare); i++) {
                int randomNumber = random.nextInt(passengerMapH.size());
                randomNumberSet.add(randomNumber);
                if(randomNumberSet.size() == i)
                {i--;}
                toBeVaccinedPassengers[i] = arrayPassengers[randomNumber];
        }

        for (Passenger toBeVaccinedPassenger: toBeVaccinedPassengers) {
            toBeVaccinedPassenger.setVaccinated(true);
        }
    }

    public static void updateMask() {
        for (Passenger passenger : passengerMapH.values()) {
            passenger.setMask(true);
        }
    }

    public static void teleworkingScenario(double teleworkingShare) {


        Passenger[] arrayPassengers = passengerMapH.values().toArray(new Passenger[0]);
        Passenger[] teleworkingPassengers = new Passenger[(int) (passengerMapH.size()* teleworkingShare)];
        Set<Integer> randomNumberSet = new HashSet<>();

        for (int i = 0; i < (int) (passengerMapH.size()* teleworkingShare); i++) {
            int randomNumber = random.nextInt(passengerMapH.size());

            randomNumberSet.add(randomNumber);
            if(randomNumberSet.size() == i)
            {i--;}
            teleworkingPassengers[i] = arrayPassengers[randomNumber];
        }

        for (Passenger teleworkingPassenger: teleworkingPassengers) {
            passengerMapH.remove(teleworkingPassenger);
        }

    }


}

