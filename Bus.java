package assignments.assignment5;

import info.gridworld.actor.Actor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Bus extends Actor {

    private final int BusID;
    public static final int CAPACITY = 50;
    private static final int NUM_INF_CONTACT_BUS = 10;
    private static final int NUM_INF_CONTACT_BUS_STATION = 3;

    BusStation startST = new BusStation();
    BusStation endSt = new BusStation();
    BusStation initiationSt = new BusStation();
    BusStation destinationSt = new BusStation();
    Random random = new Random(EpidemicRunner.MATRICULATION);

    public Bus(int busID) {BusID = busID;}

    public void setStartST(BusStation startST) {
        this.startST = startST;
    }

    public void setEndSt(BusStation endSt) {this.endSt = endSt;}

    public void setInitiationSt(BusStation initiationSt) {
        this.initiationSt = initiationSt;
    }

    public void setDestinationSt(BusStation destinationSt) {
        this.destinationSt = destinationSt;
    }

    Set<Passenger> onboardPassengerSet = new HashSet<>();


    public void droppingOff() {

        for (Passenger passenger : onboardPassengerSet) {
            infectionContact(passenger, onboardPassengerSet,NUM_INF_CONTACT_BUS, this);
            switch (passenger.getTravelStatus()) {
                case outbound -> passenger.setTravelStatus(TravelStatus.inbound);
                case inbound -> passenger.setTravelStatus(TravelStatus.home);
            }
            destinationSt.waitingPassengerSet.add(passenger);
        }
        onboardPassengerSet.clear();
    }

    public void onBoarding() {

        this.setDirection(this.getDirection()+180);

        if(destinationSt.equals(endSt)){setDestinationSt(startST);setInitiationSt(endSt);}
        else {setDestinationSt(endSt);setInitiationSt(startST);}
        int count = 0;

        for (Passenger passenger: initiationSt.getWaitingPassengerSet()) {
            if (passenger.getTravelStatus().equals(TravelStatus.outbound)
                    && passenger.getWorkStationId().equals(destinationSt.getStationID())) {
                this.onboardPassengerSet.add(passenger);
                infectionContact(passenger, initiationSt.getWaitingPassengerSet(), NUM_INF_CONTACT_BUS_STATION, initiationSt);
                count++;
                if (count == CAPACITY) break;
            }
        }

        if(count < CAPACITY) {
            for (Passenger passenger : initiationSt.getWaitingPassengerSet()) {
                if (passenger.getTravelStatus().equals(TravelStatus.inbound)
                        && passenger.getHomeStationId().equals(destinationSt.getStationID())) {
                    this.onboardPassengerSet.add(passenger);

                    infectionContact(passenger, initiationSt.getWaitingPassengerSet(), NUM_INF_CONTACT_BUS_STATION, initiationSt);

                    count++;
                    if (count == CAPACITY) break;
                }}}

        initiationSt.waitingPassengerSet.removeAll(this.onboardPassengerSet);

    }

    public void infectionContact(Passenger passenger, Set<Passenger> PassengerSetInfectionContact, int NUM_INF_CONTACT, Object Container ){

        if(passenger.getInfectionStatus() != InfectionStatus.infected){ {

            Passenger[] arrayPassengers = PassengerSetInfectionContact.toArray(new Passenger[0]);
            Passenger[] contactPassengers = new Passenger[NUM_INF_CONTACT];

            for (int i = 0; i < NUM_INF_CONTACT; i++){
                int randomNumber = random.nextInt(PassengerSetInfectionContact.size());
                contactPassengers[i] = arrayPassengers[randomNumber];
            }

            float probabilityReductionFactor = 3.0F;
            for (Passenger contactPassenger: contactPassengers) {
                if(passenger.getInfectionStatus() == InfectionStatus.infected) {break;}
                if(contactPassenger.getInfectionStatus() == InfectionStatus.infected){
                    for(Probability probability: InfectionProbabilityReader.getProbabilityMap().values()) {
                        float infectionProbability;
                        if ((contactPassenger.getAgeGroup() == probability.getAgeGroup()) && (contactPassenger.isVaccinated() == probability.isVaccinated()) && (contactPassenger.isMasked() == probability.isMasked())) {
                            if(passenger.getInfectionStatus() != InfectionStatus.recovered){probabilityReductionFactor = 1.0F;}
                            infectionProbability = (probability.getProbability()/probabilityReductionFactor);
                            if (infectionProbability
                                    > random.nextFloat()) {
                                passenger.setInfectionStatus(InfectionStatus.infected);
                                if(Container.equals(this)){passenger.setInfectionLocation("BUS: " + String.valueOf(this.BusID));}
                                 else {passenger.setInfectionLocation("BUS STATION: " + initiationSt.stationID);}
                            }
                            break;
                        }}}}}}}


    int count;
    @Override
    public void act() {

        this.droppingOff();
        this.onBoarding();

        //this.move();

        if(HealthCenter.endOfDay()){
            HealthCenter.updateInfectionStatus();
            HealthCenter.transitionToQuarantine();
            HealthCenter.updateTravelStatus();

            if(HealthCenter.yearCount == 1){System.exit(0);}

            //Individual Passenger Data CSV output
            try {HealthCenter.writeOutData(EpidemicRunner.outputFilePath);} catch (IOException e) {e.printStackTrace();}

            //Cumulative Infection Data CSV output
            try {HealthCenter.writeOutCumulativeData(EpidemicRunner.outputFilePath2);} catch (IOException e) {e.printStackTrace();}
        }
    }
}
