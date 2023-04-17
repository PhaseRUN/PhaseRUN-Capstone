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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.ZoneId;
import java.util.*;

public class RaceAPI {
    @Value("${apiKey}")
    private static String apiKey;
    private final User.RunningExpEnum runningExpEnum;
    private final User.ActivityLvlEnum activityLvlEnum;

    public RaceAPI(User.RunningExpEnum runningExpEnum, User.ActivityLvlEnum activityLvlEnum) {
        this.runningExpEnum = runningExpEnum;
        this.activityLvlEnum = activityLvlEnum;
    }
    //Gets the Races information from the races API
    public static List<RaceInfo> getRacesFromAPI(String radius, String zipcode, String distance) throws ParseException {

        //This function will calculate the start date based on the user's fitness score
        Date startDate = getStartDateCalculation(distance);

        //Formats the date needed for the API
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(startDate);

        //Convert distance from String to double
        double doubleDistance = convertDistanceToDouble(distance);

        //API call gets races information with given filters
        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("https://runsignup.com/rest/races?format=json&event_type=running_race&distance_units=K")
                    .header("api_key", apiKey)
                    .queryString("results_per_page", "5")
                    .queryString("start_date", strDate)
                    .queryString("min_distance", doubleDistance)
                    .queryString("max_distance", doubleDistance)
                    .queryString("radius", radius)
                    .queryString("zipcode", zipcode)
                    .asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        //Displays the api result to the console
//        displayHTTPResponse(response);

        //Sets the information acquired from the Races API call
        List<RaceInfo> races = setRacesInfoFromAPI(response, startDate);

        return races;
    }

    //Sets the race information acquired from the Races API and returns a list of races
    private static List<RaceInfo> setRacesInfoFromAPI(HttpResponse<JsonNode> response, Date startDate) throws ParseException {
        List<RaceInfo> races = new ArrayList<>();

        //converts from a response to an object
        JSONObject myObj = response.getBody().getObject();
        //set results to the inner level "races" of myObj
        JSONArray results = myObj.getJSONArray("races");

        for(int i = 0; i < results.length(); i++)
        {
            RaceInfo raceInfo = new RaceInfo();
            // GETTING OBJECT INFORMATION
            JSONObject jsonObject = results.getJSONObject(i).getJSONObject("race");

            //Set Yellow Start date
            raceInfo.setYellowStartDate(startDate);

            //Convert to calendar and add 2 weeks to start date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            System.out.println(calendar.getTime() + "before math");
            calendar.add(Calendar.DATE, 14);
            System.out.println(calendar.getTime() + "after math");

            //Convert back to date
            Date greenDate = calendar.getTime();
            System.out.println(greenDate);

            raceInfo.setGreenStartDate(greenDate);

            // SETTING THE RACE ID FROM THE API
            raceInfo.setRaceId(jsonObject.getInt("race_id"));

            // SETTING THE RACE NAME FROM THE API
            raceInfo.setName(jsonObject.getString("name"));

            // LOGIC TO CHECK IF A DESCRIPTION WAS PROVIDED FOR THE API
            // SETTING IT IF IS AVAILABLE
            if(jsonObject.isNull("description"))
            {
                raceInfo.setDescription("Description not provided");
            }
            else
            {
                raceInfo.setDescription(jsonObject.getString("description"));
            }

            // SETTING THE DATE FROM THE API
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date formattedDate = formatter.parse(jsonObject.getString("next_date"));
            raceInfo.setRaceDate(formattedDate);

            // SETTING URL FOR THE ACTUAL RACE SITE FROM THE API
            raceInfo.setRaceURL(jsonObject.getString("url"));

            // CHECKING IF THERE IS A LOGO URL
            // SETTING IT FROM API IF IT DOES EXIST
            if(jsonObject.isNull("logo_url"))
            {
                raceInfo.setLogoUrl("logo not provided");
            }
            else
            {
                raceInfo.setLogoUrl(jsonObject.getString("logo_url"));
            }

            races.add(raceInfo);

        }
        return races;
    }

    //sets a single race from the API and returns it
//    public static void setRaceInfoFromAPI(Race race) throws UnirestException {
//
//        String raceId = race.getRaceId();
//        HttpResponse<JsonNode> raceSpecificResponse = Unirest.get(String.format("https://runsignup.com/rest/race/%s?format=json", raceId))
//                .asJson();
//
//        JSONObject raceObj = raceSpecificResponse.getBody().getObject();
//
//        //GETTING THE DISTANCE (FOR THE CARD DISPLAY) FROM THE API
//        race.setDistanceInKm(raceObj.getJSONObject("race").getJSONArray("events").getJSONObject(0).getString("distance"));
//
//        // GETTING THE PRICE(S) (FOR THE CARD DISPLAY) FROM THE API
//        JSONObject getToPrice = raceObj.getJSONObject("race").getJSONArray("events").getJSONObject(0).getJSONArray("registration_periods").getJSONObject(0);
//        double raceFee = Double.parseDouble(getToPrice.getString("race_fee").substring(1));
//        double processingFee = Double.parseDouble(getToPrice.getString("processing_fee").substring(1));
//        double finalRaceCost = raceFee + processingFee;
//
//        race.setCostInDollars(finalRaceCost);
//
//        //displays race api result to console
//        displayHTTPResponse(raceSpecificResponse);
//        System.out.println();
//
//        //displays object to console
//        System.out.println(race);
//        System.out.println();
//    }

    //Displays a response in JSON format to the console
    private static void displayHTTPResponse(HttpResponse<JsonNode> response){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }

    //Calculates the fitness score based on the user's activity level, running experience, and age
    //Return the fitness score
    private static int fitnessValueCalculation(){
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

        User user = new User("test","test@test.com","tester", User.RunningExpEnum.RECREATIONAL, User.ActivityLvlEnum.INTERMEDIATE,78223,formattedDate);

        User.ActivityLvlEnum activityLvl = user.getActivityLvl();
        User.RunningExpEnum runningExp = user.getRunningExp();

        switch (activityLvl) {
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

    //Calculates the start date based on how much the user needs to train
    private static Date getStartDateCalculation(String distance){
        SimpleDateFormat printDate = new SimpleDateFormat("MM/dd/yyyy");

        Calendar today = new GregorianCalendar();
        today.setTime(new Date());
        Date raceStartDate = null;

        int fitnessScore = fitnessValueCalculation();

        switch (distance.toUpperCase()) {
            // CHECKING FOR A 5K RACE
            case "5K" ->
            {
                if (fitnessScore >= 65 && fitnessScore <= 81)
                {
                    today.add(Calendar.DATE, (7 * 8)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 8 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 8-10 weeks to train");
                }
                else if (fitnessScore >= 50 && fitnessScore <= 64)
                {
                    today.add(Calendar.DATE, (7 * 6)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 6 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 6-8 weeks to train");
                }
                else if (fitnessScore >= 35 && fitnessScore <= 49)
                {
                    today.add(Calendar.DATE, (7 * 4)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 4 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 4-6 weeks to train");
                }
                else if (fitnessScore >= 20 && fitnessScore <= 34)
                {
                    today.add(Calendar.DATE, (7 * 3)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 3 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 3-5 weeks to train");
                }
                else if (fitnessScore >= 14 && fitnessScore <= 19)
                {
                    today.add(Calendar.DATE, (7 * 2)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 2 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 2-4 weeks to train");
                }
            }

            // CHECKING FOR A 10K RACE
            case "10K" ->
            {
                if (fitnessScore >= 65 && fitnessScore <= 81)
                {
                    today.add(Calendar.DATE, (7 * 12)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 12 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 12-14 weeks to train");
                }
                else if (fitnessScore >= 50 && fitnessScore <= 64)
                {
                    today.add(Calendar.DATE, (7 * 10)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 10 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 10-12 weeks to train");
                }
                else if (fitnessScore >= 35 && fitnessScore <= 49)
                {
                    today.add(Calendar.DATE, (7 * 8)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 8 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 8-10 weeks to train");
                }
                else if (fitnessScore >= 20 && fitnessScore <= 34)
                {
                    today.add(Calendar.DATE, (7 * 6)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 6 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 6-8 weeks to train");
                }
                else if (fitnessScore >= 14 && fitnessScore <= 19)
                {
                    today.add(Calendar.DATE, (7 * 4)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 4 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 4-6 weeks to train");
                }

            }

            // CHECKING FOR A HALF MARATHON
            case "HALF-MARATHON" ->
            {
                if (fitnessScore >= 65 && fitnessScore <= 81)
                {
                    today.add(Calendar.DATE, (7 * 16)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 16 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 16-18 weeks to train");
                }
                else if (fitnessScore >= 50 && fitnessScore <= 64)
                {
                    today.add(Calendar.DATE, (7 * 14)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 14 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 14-16 weeks to train");
                }
                else if (fitnessScore >= 35 && fitnessScore <= 49)
                {
                    today.add(Calendar.DATE, (7 * 12)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 12 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 12-14 weeks to train");
                }
                else if (fitnessScore >= 20 && fitnessScore <= 34)
                {
                    today.add(Calendar.DATE, (7 * 10)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 10 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 10-12 weeks to train");
                }
                else if (fitnessScore >= 14 && fitnessScore <= 19) {

                    today.add(Calendar.DATE, (7 * 8)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 8 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 8-10 weeks to train");
                }
            }

            // CHECKING FOR A MARATHON
            case "MARATHON" ->
            {
                if (fitnessScore >= 65 && fitnessScore <= 81)
                {
                    today.add(Calendar.DATE, (7 * 20)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 20 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 20-22 weeks to train");
                }
                else if (fitnessScore >= 50 && fitnessScore <= 64)
                {
                    today.add(Calendar.DATE, (7 * 18)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 18 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 18-20 weeks to train");
                }
                else if (fitnessScore >= 35 && fitnessScore <= 49)
                {
                    today.add(Calendar.DATE, (7 * 16)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 16 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 16-18 weeks to train");
                }
                else if (fitnessScore >= 20 && fitnessScore <= 34)
                {
                    today.add(Calendar.DATE, (7 * 14)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 14 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 14-16 weeks to train");
                }
                else if (fitnessScore >= 14 && fitnessScore <= 19)
                {
                    today.add(Calendar.DATE, (7 * 12)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 12 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
                    raceStartDate = today.getTime();
                    System.out.println("You need 12-14 weeks to train");
                }
            }
        }

        return raceStartDate;
    }

    //Converts the string distance to a numeric equivalent
    private static double convertDistanceToDouble(String raceDistance){

        double distanceInKm = 0;

        switch(raceDistance.toUpperCase())
        {
            case "5K" ->
            {
                distanceInKm = 5.0;
            }
            case "10K" ->
            {
                distanceInKm = 10.0;
            }
            case "HALF" ->
            {
                distanceInKm = 21.1;
            }
            case "FULL" ->
            {
                distanceInKm = 42.2;
            }
        }

        return distanceInKm;
    }

    //For testing purposes
    public static void main(String[] args) {
        System.out.println(fitnessValueCalculation());
    }
}
