import java.time.LocalDate;

class Formula1Driver extends Driver{ //extended abstract class

    private int driverRaceCount;
    private int driverCurrentPoints;
    private int driverPoints;
    private int driverRacePositions;
    private int firstPos;
    private int secondPos;
    private int thirdPos;
    private int startingPos;
    private int finishPos;
    private int winProbability;
    private LocalDate driverRaceDate;

    public Formula1Driver(String driverName, String driverLocation, String driverTeam, int driverRaceCount, int driverCurrentPoints,
                          int driverPoints, int firstPos, int secondPos, int thirdPos, LocalDate driverRaceDate) {    //constructor
        super(driverName, driverLocation, driverTeam);

        this.driverRaceCount = driverRaceCount;
        this.driverCurrentPoints = driverCurrentPoints;
        this.driverPoints = driverPoints;
        this.firstPos = firstPos;
        this.secondPos = secondPos;
        this.thirdPos  = thirdPos;
        this.driverRaceDate = driverRaceDate;
    }

    public Formula1Driver(){ //constructor
    }

    public int getDriverRaceCount() {
        return driverRaceCount;
    }

    public void setDriverRaceCount(int driverRaceCounts) {
        this.driverRaceCount = driverRaceCounts;
    }

    public int getDriverCurrentPoints(){
        return driverCurrentPoints;
    }

    public void setDriverCurrentPoints(int driverCurrentPoints){ this.driverCurrentPoints = driverCurrentPoints; }

    public int getDriverPoints() {
        return driverPoints;
    }

    public void setDriverPoints(int driverPoints) {
        this.driverPoints = driverPoints;
    }

    public int getDriverRacePositions() {
        return driverRacePositions;
    }

    public void setDriverRacePositions(int driverRacePositions) {
        this.driverRacePositions = driverRacePositions;
    }

    public int getFirstPos() {
        return firstPos;
    }

    public void setFirstPos(int firstPos) {
        this.firstPos = firstPos;
    }

    public int getSecondPos() {
        return secondPos;
    }

    public void setSecondPos(int secondPos) {
        this.secondPos = secondPos;
    }

    public int getThirdPos() {
        return thirdPos;
    }

    public void setThirdPos(int thirdPos) {
        this.thirdPos = thirdPos;
    }

    public LocalDate getDriverRaceDate() {
        return driverRaceDate;
    }

    public void setDriverRaceDate(LocalDate driverRaceDate) {
        this.driverRaceDate = driverRaceDate;
    }


    public int getStartingPos() {
        return startingPos;
    }

    public void setStartingPos(int startingPos) {
        this.startingPos = startingPos;
    }

    public int getFinishPos() {
        return finishPos;
    }

    public void setFinishPos(int finishPos) {
        this.finishPos = finishPos;
    }

    public int getWinProbability() {
        return winProbability;
    }

    public void setWinProbability(int winProbability) {
        this.winProbability = winProbability;
    }
}