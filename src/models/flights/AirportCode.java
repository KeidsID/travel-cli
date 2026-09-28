package models.flights;

public enum AirportCode {
    CGK("CGK", "Soekarno-Hatta International Airport", "Jakarta - Indonesia"),
    UPG("UPG", "Sultan Hasanuddin International Airport", "Makassar - Indonesia"),
    DJJ("DJJ", "Sentani International Airport", "Jayapura - Indonesia");

    private final String code;
    private final String name;
    private final String location;

    AirportCode(String code, String name, String location) {
        this.code = code;
        this.name = name;
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
