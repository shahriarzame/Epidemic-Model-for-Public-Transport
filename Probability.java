package assignments.assignment5;

public class Probability {

    private int ageGroup;
    private String description;
    private boolean vaccinated;
    private boolean mask;
    private float probability;

    public Probability() {}

    public int getAgeGroup() {return ageGroup;}

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    public boolean isMasked() {
        return mask;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }

    public float getProbability() {return probability;}

    public void setProbability(float probability) {this.probability = probability;}
}
