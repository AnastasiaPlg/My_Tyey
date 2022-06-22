package example;

import java.util.ArrayList;
import java.util.List;

public class City {
    private String latitude;
    private String longitude;
    private String city;
    private String postcode;

    private List<newUser> users = new ArrayList<>();

    public City(String latitude, String longitude, String city, String postcode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.postcode = postcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public List<newUser> getUsers() {
        return users;
    }

    public void setUsers(List<newUser> users) {
        this.users = users;
    }

    public void addUser(newUser user) {
        users.add(user);
    }

    @Override
    public String toString() {
        return "City{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
