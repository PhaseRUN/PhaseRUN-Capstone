package com.codeup.phaserun.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

public class RaceAPI {
    @Value("${apiKey}")
    private static String apiKey;

    private final User.RunningExpEnum runningExpEnum;
    private final User.FitnessLvlEnum fitnessLvlEnum;

    public RaceAPI(User.RunningExpEnum runningExpEnum, User.FitnessLvlEnum fitnessLvlEnum) {
        this.runningExpEnum = runningExpEnum;
        this.fitnessLvlEnum = fitnessLvlEnum;
    }

    public static List<Race> getRacesFromAPI(String radius, String zipcode, String distance) throws UnirestException {

        /*Start Date Calculation
        This function will calculate the start date based on their fitness score
        Fitness score function will be called inside this function
        startDateCalculation(distance)
         */

        //API call gets races information with given filters
        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get("https://runsignup.com/rest/races?format=json&results_per_page=5&start_date=today&event_type=running_race&min_distance=5&max_distance=10&radius=20&zipcode=78223&distance_units=K")
                .header("api_key", apiKey)
                .queryString("results_per_page", "5")
                .asJson();

        //Displays the api result to the console
        displayHTTPResponse(response);

        //Sets the information acquired from the Races API call
        List<Race> races = setRaceInformationFromAPI(response);

        //Sets the individual race information into our race objects
        races = getRaceFromAPI(races);

        return races;
    }
    //Sets the race information acquired from the Races API and returns a list of races
    public static List<Race> setRaceInformationFromAPI(HttpResponse<JsonNode> response){
        List<Race> races = new ArrayList<>();

        //converts from a response to an object
        JSONObject myObj = response.getBody().getObject();
        //set results to the inner level "races" of myObj
        JSONArray results = myObj.getJSONArray("races");

        for(int i = 0; i < results.length(); i++)
        {
            Race race = new Race();
            // GETTING OBJECT INFORMATION
            JSONObject jsonObject = results.getJSONObject(i).getJSONObject("race");

            // SETTING THE RACE ID FROM THE API
            race.setRaceId(Integer.toString(jsonObject.getInt("race_id")));

            // SETTING THE RACE NAME FROM THE API
            race.setRaceName(jsonObject.getString("name"));

            // LOGIC TO CHECK IF A DESCRIPTION WAS PROVIDED FOR THE API
            // SETTING IT IF IS AVAILABLE
            if(jsonObject.isNull("description"))
            {
                race.setDescription("Description not provided");
            }
            else
            {
                race.setDescription(jsonObject.getString("description"));
            }

            // SETTING THE STATE (IN THE UNITED STATES) FROM THE API
            race.setState(jsonObject.getJSONObject("address").getString("state"));

            // CHECKING IF A ZIPCODE WAS PROVIDED IN THE API
            // SETTING IT IF IT WAS PROVIDED
            if(jsonObject.getJSONObject("address").isNull("zipcode"))
            {
                race.setZipcode(0000);
            }
            else
            {
                race.setZipcode(Integer.parseInt(jsonObject.getJSONObject("address").getString("zipcode")));
            }

            // SETTING THE CITY (IN THE UNITED STATES) FROM THE API
            race.setCity(jsonObject.getJSONObject("address").getString("city"));

            // SETTING THE DATE FROM THE API
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            SimpleDateFormat print = new SimpleDateFormat("MM/dd/yyyy");
            Date formattedDate = formatter.parse(jsonObject.getString("next_date"));
            System.out.println(print.format(formattedDate)); // turning a Date to a formatted String
            race.setRaceStart(formattedDate);

            // SETTING URL FOR THE ACTUAL RACE SITE FROM THE API
            race.setUrl(jsonObject.getString("url"));

            // CHECKING IF THERE IS A LOGO URL
            // SETTING IT FROM API IF IT DOES EXIST
            if(jsonObject.isNull("logo_url"))
            {
                race.setLogoUrl("logo not provided");
            }
            else
            {
                race.setLogoUrl(jsonObject.getString("logo_url"));
            }

            races.add(race);

        }
    }

    //Gets a single race from the API and returns it
    public static List<Race> getRaceFromAPI(List<Race> races){

        for(int i = 0; i < races.size(); i++)
        {
            Race individualRace = races.get(i);
            String raceId = races.get(i).getRaceId();
            HttpResponse<JsonNode> raceSpecificResponse = Unirest.get(String.format("https://runsignup.com/rest/race/%s?format=json", raceId))
                    .asJson();

            JSONObject raceObj = raceSpecificResponse.getBody().getObject();

            //GETTING THE DISTANCE (FOR THE CARD DISPLAY) FROM THE API
            individualRace.setDistanceInKm(raceObj.getJSONObject("race").getJSONArray("events").getJSONObject(0).getString("distance"));

            // GETTING THE PRICE(S) (FOR THE CARD DISPLAY) FROM THE API
            JSONObject getToPrice = raceObj.getJSONObject("race").getJSONArray("events").getJSONObject(0).getJSONArray("registration_periods").getJSONObject(0);
            double raceFee = Double.parseDouble(getToPrice.getString("race_fee").substring(1));
            double processingFee = Double.parseDouble(getToPrice.getString("processing_fee").substring(1));
            double finalRaceCost = raceFee + processingFee;

            individualRace.setCostInDollars(finalRaceCost);

            //displays race api result to console
            displayHTTPResponse(raceSpecificResponse);
            System.out.println();

            //displays object to console
            System.out.println(individualRace);
            System.out.println();

        }
    }
    //Displays a response in JSON format to the console
    public static void displayHTTPResponse(HttpResponse<JsonNode> response){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }

    //Calculates the fitness score based on the user's activity level, running experience, and age
    //Return the fitness score
    public static int fitnessValueCalculation(){
        int fitnessScore = 0;

        /* Need the below statement to switch out with the hardcoded user once security is implemented
           User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         */

        // SETTING THE DATE FROM THE API
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date formattedDate = null;
        try {
            formattedDate = formatter.parse("01/30/2000");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        User user = new User("test","test@test.com","tester", User.RunningExpEnum.RECREATIONAL, User.FitnessLvlEnum.INTERMEDIATE,78223,formattedDate);

        User.FitnessLvlEnum fitnessLevel = user.getFitnessLvl();
        User.RunningExpEnum runningExp = user.getRunningExp();

        switch (fitnessLevel) {
            case NONE : fitnessScore+=25; break;
            case BEGINNER : fitnessScore+=20; break;
            case INTERMEDIATE : fitnessScore+=15; break;
            case EXPERT : fitnessScore+=10; break;
            case ELITE : fitnessScore+=5; break;
        }

        switch (runningExp) {
            case NONE : fitnessScore+=25; break;
            case BEGINNER : fitnessScore+=20; break;
            case RECREATIONAL : fitnessScore+=15; break;
            case INTERMEDIATE : fitnessScore+=10; break;
            case EXPERT : fitnessScore+=5; break;
        }

        java.time.LocalDate localDate = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
        int day   = localDate.getDayOfMonth();

        Calendar today = new GregorianCalendar();
        today.setTime(new Date());

        LocalDate jamesBirthDay = new LocalDate(year, month, day);
        LocalDate now = new LocalDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));

        int age = Years.yearsBetween(jamesBirthDay, now).getYears();

        if (age <= 14) {
            fitnessScore += 10;
        }else if(age >= 15 && age <= 17 ){
            fitnessScore += 8;
        }else if(age >= 18 && age <= 29 ){
            fitnessScore += 5;
        }else if(age >= 30 && age <= 39 ){
            fitnessScore += 10;
        }else if(age >= 40 && age <= 49 ){
            fitnessScore += 15;
        }else if(age >= 50 && age <= 59 ){
            fitnessScore += 20;
        }else if(age >= 60 && age <= 69 ){
            fitnessScore += 25;
        }else if(age >= 70 ){
            fitnessScore += 30;
        }

        return fitnessScore;
    }

    //For testing purposes
    public static void main(String[] args) {
        System.out.println(fitnessValueCalculation());
    }
}
