import com.codeup.phaserun.models.Race;
import com.mashape.unirest.http.Unirest;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
        HttpResponse<JsonNode> httpResponse = Unirest.get("https://runsignup.com/rest/races?format=json&max_distance=5&distance_units=K")
                .header("api_key", apiKey)
                .queryString("results_per_page", "5")
                .asJson();

//        HttpResponse<JsonNode> httpResponse = Unirest.get("https://runsignup.com/rest/race/124241?format=json")
//                .asJson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(httpResponse.getBody().toString());
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);


        JSONObject myObj = httpResponse.getBody().getObject();

        JSONArray results = myObj.getJSONArray("races");

        List<Race> races = new ArrayList<>();


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

        for(int i = 0; i < races.size(); i++)
        {
            System.out.println();
            System.out.println(races.get(i));
            System.out.println();
        }

        for(int i = 0; i < races.size(); i++)
        {
            Race individualRace = races.get(i);
           String raceId = races.get(i).getRaceId();
            HttpResponse<JsonNode> raceSpecificResponse = Unirest.get(String.format("https://runsignup.com/rest/race/%s?format=json", raceId))
                .asJson();

            JSONObject raceObj = raceSpecificResponse.getBody().getObject();

            //GETTING THE DISTANCE (FOR THE CARD DISPLAY) FROM THE API
            raceObj.getJSONObject("race");


            Gson raceGson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser raceJp = new JsonParser();
            JsonElement raceJe = raceJp.parse(raceSpecificResponse.getBody().toString());
            String racePrettyJsonString = gson.toJson(raceJe);

            System.out.println();
            System.out.println(racePrettyJsonString);
//            System.out.println(raceObj.getJSONObject("race"));
            System.out.println();

        }


//        List<JSONObject> races = new ArrayList<>();

    }



}
