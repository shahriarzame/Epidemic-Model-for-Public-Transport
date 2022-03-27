package assignments.assignment5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InfectionProbabilityReader {

    private static final Map<Integer, Probability> probabilityMap = new HashMap<>();

    public static Map<Integer, Probability> getProbabilityMap() {return probabilityMap;}

    public void readData(String s) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(s));
            String newline;

            //read header
            String[] str = reader.readLine().split(",");
            int indexAgeGroup = findIndexInArray("ageGroup",str);
            int indexDescription = findIndexInArray("description",str);
            int indexVaccinated = findIndexInArray("vaccinated",str);
            int indexMask = findIndexInArray("mask",str);
            int indexProbability = findIndexInArray("probability",str);

            //read body
            int count = 1;
            while((newline = reader.readLine()) != null){
                String[] strArray = newline.split(",");

                Probability probability = new Probability();
                probability.setAgeGroup(Integer.parseInt(strArray[indexAgeGroup]));
                probability.setDescription(strArray[indexDescription]);
                probability.setVaccinated(Boolean.parseBoolean(strArray[indexVaccinated]));
                probability.setMask(Boolean.parseBoolean(strArray[indexMask]));
                probability.setProbability(Float.parseFloat(strArray[indexProbability]));

                probabilityMap.put(count,probability);
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
        for (int a =0; a<str.length;a++){
            if(str[a].equalsIgnoreCase(colname)){
                position = a;
            }
        }

        if (position == -1)throw new RuntimeException("Could not find column called: " + colname);
        return position;
    }

    public void checkingProbability() {
        for (Probability probability : probabilityMap.values()) {
            System.out.println(probability.getAgeGroup());
            System.out.println(probability.getDescription());
            System.out.println(probability.isMasked());
            System.out.println(probability.isVaccinated());
            System.out.println(probability.getProbability());
            System.out.println("NEW");
        }}}
