package com.codeup.phaserun.models;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

public class RaceAPI {

    public static List<Race> getRacesFromAPI(int radius, String zipcode, float minDistance, float maxDistance) throws UnirestException {
        List<Race> races;

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get("https://runsignup.com/rest/races?format=json&results_per_page=5&start_date=today&event_type=running_race&min_distance=5&max_distance=10&radius=20&zipcode=78223&distance_units=K")
                .header("api_key", "1b5511344c1892462a5fdff8780b7e98ae45e645")
                .queryString("results_per_page", "5")
                .asJson();

        return races;
    }
}
