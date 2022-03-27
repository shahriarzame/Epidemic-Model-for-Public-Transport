package assignments.assignment5;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.io.IOException;

public class EpidemicRunner {

    static final int MATRICULATION = 8;
    static final int N_Cells = MATRICULATION + 10;
    static final int NUM_BUS_STATIONS = 4;
    static final double VACCINE_SHARE = 0.0;
    static final double TELEWORKER_SHARE = 0.0;

    //Setting Path for CSV output
    static String inputFilePathPassenger = "src/assignments/assignment5/passengers.csv";
    static String inputFilePathProbability = "src/assignments/assignment5/probability.csv";
    static String outputFilePath = "src/assignments/assignment5/Epidemic";
    static String outputFilePath2 = "src/assignments/assignment5/EpidemicCumulative";


    public static void main(String[] args) throws IOException {

        ActorWorld world = new ActorWorld();
        world.setGrid(new BoundedGrid<>(N_Cells,N_Cells));

        BusStation[] busStation = new BusStation[NUM_BUS_STATIONS];

        for (int i=0; i < NUM_BUS_STATIONS; i++) {
            busStation[i] = new BusStation();

            if (i == 0){busStation[i].setStationID("a"); busStation[i].setColor(Color.red);}
            if (i == 1){busStation[i].setStationID("b"); busStation[i].setColor(Color.green);}
            if (i == 2){busStation[i].setStationID("c"); busStation[i].setColor(Color.yellow);}
            if (i == 3){busStation[i].setStationID("d"); busStation[i].setColor(Color.blue);}
        }

        world.add(new Location(0,0), busStation[0]);
        world.add(new Location(0,17), busStation[1]);
        world.add(new Location(17,17), busStation[2]);
        world.add(new Location(17,0), busStation[3]);

        Bus[] bus = new Bus[NUM_BUS_STATIONS];

        for (int i=0; i < NUM_BUS_STATIONS; i++) {
            bus[i] = new Bus(i+1);
            bus[i].setDirection(-180 + 90*i);

            if(i<3){bus[i].startST = busStation[i]; bus[i].endSt = busStation[i+1];
                bus[i].initiationSt = busStation[i+1];bus[i].destinationSt = busStation[i];}
            else {bus[i].startST = busStation[i]; bus[i].endSt = busStation[0];
                bus[i].initiationSt = (busStation[0]);bus[i].destinationSt = busStation[i];}
        }

        world.add(new Location(0,1), bus[0]);
        world.add(new Location(1,17), bus[1]);
        world.add(new Location(17,16), bus[2]);
        world.add(new Location(16,0), bus[3]);

        //Reading in Passenger
        PassengerReader passengerReader = new PassengerReader();
        passengerReader.readData(inputFilePathPassenger);
        passengerReader.processPassengers();

        passengerReader.checking();

        //Reading in InfectionProbability
        InfectionProbabilityReader infectionProbabilityReader = new InfectionProbabilityReader();
        infectionProbabilityReader.readData(inputFilePathProbability);
        infectionProbabilityReader.checkingProbability();

        busStation[0].waitingPassengerSet.addAll(passengerReader.initiatePassengersAtHomeStation("a"));
        busStation[1].waitingPassengerSet.addAll(passengerReader.initiatePassengersAtHomeStation("b"));
        busStation[2].waitingPassengerSet.addAll(passengerReader.initiatePassengersAtHomeStation("c"));
        busStation[3].waitingPassengerSet.addAll(passengerReader.initiatePassengersAtHomeStation("d"));

        //Setting Probability map in HealthCenter.
        HealthCenter healthCenter = new HealthCenter();

        healthCenter.setPassengerMapH(passengerReader.getPassengerMap());

        //Write Out first data
        //HealthCenter.writeOutData(EpidemicRunner.outputFilePath);
        HealthCenter.writeOutCumulativeData(EpidemicRunner.outputFilePath2);

        //Calling Vaccine Update
        //HealthCenter.updateVaccination(VACCINE_SHARE);

        //Calling Mask Update
        //HealthCenter.updateMask();

        //Calling TeleWorking Scenario
        //HealthCenter.teleworkingScenario(TELEWORKER_SHARE);

        world.show();
    }

}
