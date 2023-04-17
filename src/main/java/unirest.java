import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.RaceInfo;
import com.mashape.unirest.http.Unirest;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.processor.comment.ICommentProcessor;

public class unirest
{
    @Value("${apiKey}")
    public static String apiKey;
//    URLConnection connection = new URL("https://pokeapi.co/api/v2/evolution-chain/1").openConnection();
//    connection.setRequestProperty("header1", header1);
//    connection.setRequestProperty("header2", header2);
//    //Get Response
//    InputStream is = connection.getInputStream();
//    System.out.println(connection.getContentType());

    public static void main(String[] args) throws Exception
    {
//        HttpResponse<JsonNode> httpResponse = Unirest.get("https://runsignup.com/rest/races?format=json&max_distance=5&distance_units=K")
//                .header("api_key", apiKey)
//                .queryString("results_per_page", "5")
//                .asJson();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(httpResponse.getBody().toString());
//        String prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);


//        JSONObject myObj = httpResponse.getBody().getObject();

//        JSONArray results = myObj.getJSONArray("races");
//
//        List<Race> races = new ArrayList<>();


//        for(int i = 0; i < results.length(); i++)
//        {
//            Race race = new Race();
//            // GETTING OBJECT INFORMATION
//            JSONObject jsonObject = results.getJSONObject(i).getJSONObject("race");
//
//            // SETTING THE RACE ID FROM THE API
//            race.setRaceId(Integer.toString(jsonObject.getInt("race_id")));
//
//            // SETTING THE RACE NAME FROM THE API
//            race.setRaceName(jsonObject.getString("name"));
//
//            // LOGIC TO CHECK IF A DESCRIPTION WAS PROVIDED FOR THE API
//            // SETTING IT IF IS AVAILABLE
//            if(jsonObject.isNull("description"))
//            {
//                race.setDescription("Description not provided");
//            }
//            else
//            {
//                race.setDescription(jsonObject.getString("description"));
//            }
//
//            // SETTING THE STATE (IN THE UNITED STATES) FROM THE API
//            race.setState(jsonObject.getJSONObject("address").getString("state"));
//
//            // CHECKING IF A ZIPCODE WAS PROVIDED IN THE API
//            // SETTING IT IF IT WAS PROVIDED
//            if(jsonObject.getJSONObject("address").isNull("zipcode"))
//            {
//                race.setZipcode(0000);
//            }
//            else
//            {
//                race.setZipcode(Integer.parseInt(jsonObject.getJSONObject("address").getString("zipcode")));
//            }
//
//            // SETTING THE CITY (IN THE UNITED STATES) FROM THE API
//            race.setCity(jsonObject.getJSONObject("address").getString("city"));
//
//            // SETTING THE DATE FROM THE API
//            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
//            SimpleDateFormat print = new SimpleDateFormat("MM/dd/yyyy");
//            Date formattedDate = formatter.parse(jsonObject.getString("next_date"));
//            System.out.println(print.format(formattedDate)); // turning a Date to a formatted String
//            race.setRaceStart(formattedDate);
//
//            // SETTING URL FOR THE ACTUAL RACE SITE FROM THE API
//            race.setUrl(jsonObject.getString("url"));
//
//            // CHECKING IF THERE IS A LOGO URL
//            // SETTING IT FROM API IF IT DOES EXIST
//            if(jsonObject.isNull("logo_url"))
//            {
//                race.setLogoUrl("logo not provided");
//            }
//            else
//            {
//                race.setLogoUrl(jsonObject.getString("logo_url"));
//            }
//
//            races.add(race);
//
//        }
//
//        for(int i = 0; i < races.size(); i++)
//        {
//            System.out.println();
//            System.out.println(races.get(i));
//            System.out.println();
//        }

//        for(int i = 0; i < races.size(); i++)
//        {
//            Race individualRace = races.get(i);
//           String raceId = races.get(i).getRaceId();
//            HttpResponse<JsonNode> raceSpecificResponse = Unirest.get(String.format("https://runsignup.com/rest/race/%s?format=json", raceId))
//                .asJson();
//
//            JSONObject raceObj = raceSpecificResponse.getBody().getObject();
//
//            //GETTING THE DISTANCE (FOR THE CARD DISPLAY) FROM THE API
//            individualRace.setDistanceInKm(raceObj.getJSONObject("race").getJSONArray("events").getJSONObject(0).getString("distance"));
//
//            // GETTING THE PRICE(S) (FOR THE CARD DISPLAY) FROM THE API
//            JSONObject getToPrice = raceObj.getJSONObject("race").getJSONArray("events").getJSONObject(0).getJSONArray("registration_periods").getJSONObject(0);
//            double raceFee = Double.parseDouble(getToPrice.getString("race_fee").substring(1));
//            double processingFee = Double.parseDouble(getToPrice.getString("processing_fee").substring(1));
//            double finalRaceCost = raceFee + processingFee;
//
//            individualRace.setCostInDollars(finalRaceCost);
//
//            // PRETTIFYING THE JAVA JSON RESPONSE
//            Gson raceGson = new GsonBuilder().setPrettyPrinting().create();
//            JsonParser raceJp = new JsonParser();
//            JsonElement raceJe = raceJp.parse(raceSpecificResponse.getBody().toString());
//            String racePrettyJsonString = gson.toJson(raceJe);
//
//            System.out.println();
//            System.out.println(racePrettyJsonString);
////            System.out.println(raceObj.getJSONObject("race"));
//            System.out.println(individualRace);
//            System.out.println();
//
//        }





    ///////// STARTING LOGIC TO DETERMINE THE NUMBER OF WEEKS A USER WILL NEED TO TRAIN FOR AN INDIVIDUAL RACE /////////////

        /*
            5K Race:
            Low: Scores of 65-81 correspond to a training period of 8-10 weeks.
            Below Average: Scores of 50-64 correspond to a training period of 6-8 weeks.
            Average: Scores of 35-49 correspond to a training period of 4-6 weeks.
            Above Average: Scores of 20-34 correspond to a training period of 3-6 weeks.
            High: Scores of 14-19 correspond to a training period of 2-4 weeks.

            10K Race:
            Low: Scores of 65-81 correspond to a training period of 12-14 weeks.
            Below Average: Scores of 50-64 correspond to a training period of 10-12 weeks.
            Average: Scores of 35-49 correspond to a training period of 8-10 weeks.
            Above Average: Scores of 20-34 correspond to a training period of 6-8 weeks.
            High: Scores of 14-19 correspond to a training period of 4-6 weeks.

            Half-Marathon:
            Low: Scores of 65-81 correspond to a training period of 16-18 weeks.
            Below Average: Scores of 50-64 correspond to a training period of 14-16 weeks.
            Average: Scores of 35-49 correspond to a training period of 12-14 weeks.
            Above Average: Scores of 20-34 correspond to a training period of 10-12 weeks.
            High: Scores of 14-19 correspond to a training period of 8-10 weeks.

            Full Marathon:
            Low: Scores of 65-81 correspond to a training period of 20-22 weeks.
            Below Average: Scores of 50-64 correspond to a training period of 18-20 weeks.
            Average: Scores of 35-49 correspond to a training period of 16-18 weeks.
            Above Average: Scores of 20-34 correspond to a training period of 14-16 weeks.
            High: Scores of 14-19 correspond to a training period of 12-14 weeks.
         */


//        SimpleDateFormat printDate = new SimpleDateFormat("MM/dd/yyyy");
//
//        Calendar today = new GregorianCalendar();
//        today.setTime(new Date());
//        Date raceStartDate = null;
//
//        Date todayDate = today.getTime();
//
//        String raceDistance = "10k";
//        int fitnessScore = 40;
//
//        System.out.println(printDate.format(todayDate));
//
//        switch (raceDistance.toUpperCase()) {
//            // CHECKING FOR A 5K RACE
//            case "5K" ->
//            {
//                if (fitnessScore >= 65 && fitnessScore <= 81)
//                {
//                    today.add(Calendar.DATE, (7 * 8)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 8 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 8-10 weeks to train");
//                }
//                else if (fitnessScore >= 50 && fitnessScore <= 64)
//                {
//                    today.add(Calendar.DATE, (7 * 6)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 6 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 6-8 weeks to train");
//                }
//                else if (fitnessScore >= 35 && fitnessScore <= 49)
//                {
//                    today.add(Calendar.DATE, (7 * 4)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 4 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 4-6 weeks to train");
//                }
//                else if (fitnessScore >= 20 && fitnessScore <= 34)
//                {
//                    today.add(Calendar.DATE, (7 * 3)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 3 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 3-6 weeks to train");
//                }
//                else if (fitnessScore >= 14 && fitnessScore <= 19)
//                {
//                    today.add(Calendar.DATE, (7 * 2)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 2 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 2-4 weeks to train");
//                }
//            }
//
//            // CHECKING FOR A 10K RACE
//            case "10K" ->
//            {
//                if (fitnessScore >= 65 && fitnessScore <= 81)
//                {
//                    today.add(Calendar.DATE, (7 * 12)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 12 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 12-14 weeks to train");
//                }
//                else if (fitnessScore >= 50 && fitnessScore <= 64)
//                {
//                    today.add(Calendar.DATE, (7 * 10)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 10 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 10-12 weeks to train");
//                }
//                else if (fitnessScore >= 35 && fitnessScore <= 49)
//                {
//                    today.add(Calendar.DATE, (7 * 8)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 8 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 8-10 weeks to train");
//                }
//                else if (fitnessScore >= 20 && fitnessScore <= 34)
//                {
//                    today.add(Calendar.DATE, (7 * 6)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 6 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 6-8 weeks to train");
//                }
//                else if (fitnessScore >= 14 && fitnessScore <= 19)
//                {
//                    today.add(Calendar.DATE, (7 * 4)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 4 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 4-6 weeks to train");
//                }
//
//            }
//
//            // CHECKING FOR A HALF MARATHON
//            case "HALF-MARATHON" ->
//            {
//                if (fitnessScore >= 65 && fitnessScore <= 81)
//                {
//                    today.add(Calendar.DATE, (7 * 16)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 16 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 16-18 weeks to train");
//                }
//                else if (fitnessScore >= 50 && fitnessScore <= 64)
//                {
//                    today.add(Calendar.DATE, (7 * 14)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 14 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 14-16 weeks to train");
//                }
//                else if (fitnessScore >= 35 && fitnessScore <= 49)
//                {
//                    today.add(Calendar.DATE, (7 * 12)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 12 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 12-14 weeks to train");
//                }
//                else if (fitnessScore >= 20 && fitnessScore <= 34)
//                {
//                    today.add(Calendar.DATE, (7 * 10)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 10 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 10-12 weeks to train");
//                }
//                else if (fitnessScore >= 14 && fitnessScore <= 19) {
//
//                    today.add(Calendar.DATE, (7 * 8)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 8 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 8-10 weeks to train");
//                }
//            }
//
//            // CHECKING FOR A MARATHON
//            case "MARATHON" ->
//            {
//                if (fitnessScore >= 65 && fitnessScore <= 81)
//                {
//                    today.add(Calendar.DATE, (7 * 20)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 20 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 20-22 weeks to train");
//                }
//                else if (fitnessScore >= 50 && fitnessScore <= 64)
//                {
//                    today.add(Calendar.DATE, (7 * 18)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 18 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 18-20 weeks to train");
//                }
//                else if (fitnessScore >= 35 && fitnessScore <= 49)
//                {
//                    today.add(Calendar.DATE, (7 * 16)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 16 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 16-18 weeks to train");
//                }
//                else if (fitnessScore >= 20 && fitnessScore <= 34)
//                {
//                    today.add(Calendar.DATE, (7 * 14)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 14 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 14-16 weeks to train");
//                }
//                else if (fitnessScore >= 14 && fitnessScore <= 19)
//                {
//                    today.add(Calendar.DATE, (7 * 12)); // 7 IS THE NUMBER OF DAYS IN A WEEK, 12 IS THE MINIMUM NUMBER OF WEEKS REQUIRED
//                    raceStartDate = today.getTime();
//                    System.out.println("You need 12-14 weeks to train");
//                }
//            }
//        }
//
//        System.out.println(printDate.format(raceStartDate));


        /*
            CHECKING THE INT EQUIVALENT OF THE STRING DISTANCE


            STRING "5K" WILL BE EQUAL TO THE DOUBLE "5.0"
            STRING "10K" WILL BE EQUAL TO THE DOUBLE "10.0"
            STRING "HALF-MARATHON" WILL BE EQUAL TO THE DOUBLE "21.1"
            STRING "MARATHON" WILL BE EQUAL TO THE DOUBLE "42.2"

         */

//        double distanceInKm = 0;
//
//        switch(raceDistance.toUpperCase())
//        {
//            case "5K" ->
//            {
//                distanceInKm = 5.0;
//            }
//            case "10K" ->
//            {
//                distanceInKm = 10.0;
//            }
//            case "HALF" ->
//            {
//                distanceInKm = 21.1;
//            }
//            case "FULL" ->
//            {
//                distanceInKm = 42.2;
//            }
//        }
//
//        System.out.println(distanceInKm);
//
//
//



//        // GETTING THE SEPARATE EVENTS FROM A SINGLE RACE FROM THE API
//        HttpResponse<JsonNode> raceSpecificResponse = Unirest.get("https://runsignup.com/rest/race/84148?format=json")
//                .asJson();
//
//        JSONObject raceObj = raceSpecificResponse.getBody().getObject().getJSONObject("race");
//
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(raceSpecificResponse.getBody().toString());
//        String prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);

//        JSONArray raceEvents = raceObj.getJSONArray("events");
//
//
//        double totalFee = 0;
//
//
//        for(int i = 0; i < raceEvents.length(); i++)
//        {
//            System.out.println();
//            System.out.println(raceEvents.getJSONObject(i).getString("distance"));
//
//            JSONObject getToPrice = raceEvents.getJSONObject(i).getJSONArray("registration_periods").getJSONObject(0);
//            double raceFee = Double.parseDouble(getToPrice.getString("race_fee").substring(1));
//            double processingFee = Double.parseDouble(getToPrice.getString("processing_fee").substring(1));
//            double finalRaceCost = raceFee + processingFee;
//            System.out.println("$" + finalRaceCost);
//
////            System.out.println("Race fee: " + raceEvents.getJSONObject(i).getJSONArray("registration_periods").getJSONObject(0).getString("race_fee"));
////            System.out.println("Processing fee: " + raceEvents.getJSONObject(i).getJSONArray("registration_periods").getJSONObject(0).getString("processing_fee"));
//            System.out.println();
//        }

        //////////// GREEN YELLOW RED CALCULATION /////////////////

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date d1 = formatter.parse("07/11/2023");
        Date d2 = formatter.parse("07/31/2023");
        Date compareDate = formatter.parse("08/01/2023");

        System.out.println("The date 1 is: " + formatter.format(d1));
        System.out.println("The date 2 is: " + formatter.format(d2));
        System.out.println("The date to compare is: " + formatter.format(compareDate));

        if(compareDate.compareTo(d1) > 0 && compareDate.compareTo(d2) < 0)//if the date we're comparing is in between the two dates
        {
            System.out.println("Yellow");
        }
        else if(compareDate.compareTo(d1) < 0)//if the date we're comparing is before the first (earliest) date
        {
            System.out.println("Red");
        }
        else if(compareDate.compareTo(d2) > 0)//if the date we're comparing is after the second (latest) date (2 weeks after first date)
        {
            System.out.println("Green");
        }

        RaceInfo.redYellowGreen(compareDate, d1, d2);

    }
}
