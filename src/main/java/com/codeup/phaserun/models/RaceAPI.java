package com.codeup.phaserun.models;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.LocalDate;
import org.joda.time.Years;
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

    public static List<Race> getRacesFromAPI(String radius, String zipcode, String minDistance, String maxDistance) throws UnirestException {
        List<Race> races = null;

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get("https://runsignup.com/rest/races?format=json&results_per_page=5&start_date=today&event_type=running_race&min_distance=5&max_distance=10&radius=20&zipcode=78223&distance_units=K")
                .header("api_key", apiKey)
                .queryString("results_per_page", "5")
                .asJson();

        return races;
    }

    public static int fitnessValueCalculation(){
        int fitnessScore = 0;
        //
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    public static void main(String[] args) {
        System.out.println(fitnessValueCalculation());
    }
}
