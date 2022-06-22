import api.Response;
import api.Result;
import com.google.gson.Gson;
import example.City;
import example.User;
import example.newUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Supplier<Response> supplier = Main::getUser;
        Response response0 = supplier.get();
        List<Result> results = response0.getResults();
        Function<Result, newUser> function = r -> new newUser(r.getRegistered().getDate(), r.getRegistered().getAge(),
                r.getName().getFirst(), r.getName().getLast(), r.getGender(),
                new City(r.getLocation().getCoordinates().getLatitude(), r.getLocation().getCoordinates().getLongitude(),
                        r.getLocation().getCity(), r.getLocation().getPostcode()));

        Function<Result, City> function1 = r -> {
            City city = new City(r.getLocation().getCoordinates().getLatitude(),
                    r.getLocation().getCoordinates().getLongitude(), r.getLocation().getCity(), r.getLocation().getPostcode());
            results.stream().map(function).forEach(newUser -> {
                if (newUser.getCity().getCity().equals(city.getCity())) {
                    city.addUser(newUser);
                }
            });
            return city;
        };
        // Task 1:
        Stream<Result> stream = results.stream();
        Stream<newUser> userStream = stream.map(function);
        Predicate<newUser> predicate = user -> user.getAge() > 18 && user.getAge() < 62;
        Comparator<String> dateComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = 0;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date1 = LocalDate.parse(o1.substring(0, 10), dtf);
                LocalDate date2 = LocalDate.parse(o2.substring(0, 10), dtf);
                if (date1.getYear() == date2.getYear()) {
                    result = date1.getDayOfYear() - date2.getDayOfYear();
                } else {
                    result = date1.getYear() - date2.getYear();
                }
                return result;
            }
        };

        userStream.filter(predicate)
                .sorted(Comparator.comparing(newUser::getGender))
                .sorted((o1, o2) -> {
                    int result = 0;
                    if (o1.getGender().equals(o2.getGender())) {
                        result = dateComparator.compare(o1.getDate(), o2.getDate());
                    }
                    return result;
                })
                .sorted((o1, o2) -> {
                    int result = 0;
                    if (o1.getDate().substring(0, 10).equals(o2.getDate().substring(0, 10))) {
                        result = o1.getFirstName().compareTo(o2.getFirstName());
                    }
                    return result;
                })
                .sorted((o1, o2) -> {
                    int result = 0;
                    if (o1.getDate().substring(0, 10).equals(o2.getDate().substring(0, 10)) && o1.getFirstName().equals(o2.getFirstName())) {
                        result = o1.getLastName().compareTo(o2.getLastName());
                    }
                    return result;
                })
                .forEach(System.out::println);

        //Task2
        results.stream().map(function)
                .sorted(Comparator.comparing(newUser::getGender))
                .sorted((o1, o2) -> {
                    int result = 0;
                    if (o1.getGender().equals(o2.getGender())) {
                        double r = Double.parseDouble(o1.getCity().getLatitude()) - Double.parseDouble(o2.getCity().getLatitude());
                        if (r > 0) {
                            result = 1;
                        } else if (r < 0) {
                            result = -1;
                        }
                    }
                    return result;
                })
                .sorted((o1, o2) -> {
                    int result = 0;
                    if (o1.getCity().getLatitude().equals(o2.getCity().getLongitude())) {
                        double r = Double.parseDouble(o1.getCity().getLongitude()) - Double.parseDouble(o2.getCity().getLongitude());
                        if (r > 0) {
                            result = 1;
                        } else if (r < 0) {
                            result = -1;
                        }
                    }
                    return result;
                })
                .sorted((o1, o2) -> {
                    int result = 0;
                    if (o1.getCity().getLatitude().equals(o2.getCity().getLongitude())
                            && o1.getCity().getLatitude().equals(o2.getCity().getLongitude())) {
                        result = o1.getCity().getCity().compareTo(o2.getCity().getCity());
                    }
                    return result;
                })
                .forEach(System.out::println);


//        Function<Result, User> function = r -> new User(r.getName().getFirst(), r.getRegistered().getAge(), r.getGender());
//
//        Stream<Result> stream = results.stream();
//        Stream<User> userStream = stream.map(function);
//
//
//        Predicate<User> predicate = user -> user.getAge()<10 & user.getGender().equals("male");
//        Stream<User> userStreamNew = userStream.filter(predicate);
//
//
//        Consumer<User> consumer = user -> System.out.println(user);
//
//        userStreamNew.forEach(consumer);


    }


    public static Response getUser() {
        // Task 1:
        // String uri = "https://randomuser.me/api/?results=1000&nat=gb";

        //Task 2:
        String uri = "https://randomuser.me/api/?results=1000&nat=gb";
        //TODO make different methods
        //String uri = "?results=10&noinfo";
        //String uri = "?gender=female";

        //String uri = "?format=csv";
        //JSON (default), PrettyJSON or pretty, CSV, YAML, XML
        //String uri = "?nat=gb(,fi)"
        //v1.3: AU, BR, CA, CH, DE, DK, ES, FI, FR, GB, IE, IR, NO, NL, NZ, TR, US
        //String uri = "?results=5&inc=name,gender,nat& noinfo";
        //String uri = "?inc=gender(,name)";
        //String uri = "?exc=login";
        String get = "GET";
        URL url;
        HttpURLConnection con;
        BufferedReader in;
        StringBuilder content = new StringBuilder();
        Gson gson = new Gson();
        Response response;
        try {
            url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(get);
            con.getResponseCode();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            response = gson.fromJson(content.toString(), Response.class);
            in.close();
            con.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
