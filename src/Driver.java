import java.io.Serializable;

abstract class Driver implements Serializable { //main abstract class

    private String driverName;
    private String driverLocation;
    private String driverTeam;

    public Driver(String driverName,String driverLocation, String driverTeam){ //constructor
        this.driverName = driverName;
        this.driverLocation = driverLocation;
        this.driverTeam = driverTeam;
    }

    public Driver(){ //constructor
    }

    public  String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

    public String getDriverTeam() {
        return driverTeam;
    }

    public void setDriverTeam(String driverTeam) {
        this.driverTeam = driverTeam;
    }

}