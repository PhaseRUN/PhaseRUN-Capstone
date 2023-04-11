import com.codeup.phaserun.Race;
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
                .asJson();
////        System.out.println(httpResponse.getHeaders().get("Content-Type"));
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(httpResponse.getBody().toString());
//        String prettyJsonString = gson.toJson(je);
////        System.out.println(httpResponse[1]);
//        System.out.println(prettyJsonString);  //84148 126294,

//        HttpResponse<JsonNode> httpResponse = Unirest.get("https://runsignup.com/rest/race/126294?format=json")
//                .asJson();
//        System.out.println(httpResponse.getHeaders().get("Content-Type"));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(httpResponse.getBody().toString());
        String prettyJsonString = gson.toJson(je);
//        System.out.println(httpResponse[1]);
        System.out.println(prettyJsonString);


        JSONObject myObj = httpResponse.getBody().getObject();

//        System.out.println(myObj);

//        String msg = myObj.getString("race");

//        System.out.println(myObj.getJSONObject("race").getInt("race_id"));
        JSONArray results = myObj.getJSONArray("races");

//        System.out.println(results.getJSONObject(0).getJSONObject("race").getInt("race_id")); //.getJSONObject("race").getInt("race_id")

        List<Race> races = new ArrayList<>();

        Race race = new Race();

        for(int i = 0; i < results.length(); i++)
        {

            JSONObject jsonObject = results.getJSONObject(i).getJSONObject("race");
            race.setRace_id(Integer.toString(jsonObject.getInt("race_id")));
            race.setRace_name(jsonObject.getString("name"));
            race.setDescription(jsonObject.getString("description"));
            race.setState(jsonObject.getJSONObject("address").getString("state"));

//            System.out.println(results.getJSONObject(i).getJSONObject("race").getInt("race_id"));
        }

//        List<JSONObject> races = new ArrayList<>();

    }



}
