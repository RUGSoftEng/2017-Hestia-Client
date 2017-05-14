package com.rugged.application.hestia;

        import android.support.test.runner.AndroidJUnit4;
        import android.util.Log;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import com.google.gson.JsonDeserializationContext;
        import com.google.gson.JsonElement;
        import com.google.gson.JsonObject;
        import com.google.gson.JsonParseException;
        import com.google.gson.JsonParser;
        import com.google.gson.reflect.TypeToken;

        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;

        import java.io.BufferedReader;
        import java.io.FileNotFoundException;
        import java.io.FileReader;
        import java.io.IOException;
        import java.lang.reflect.Type;

        import static org.junit.Assert.*;

        import hestia.backend.Activator;
        import hestia.backend.ActivatorDeserializer;
        import hestia.backend.ActivatorState;

@RunWith(AndroidJUnit4.class)
public class DeserializerTest {

    private static final String testBoolJSON =
            "{\"state\": false,\"type\": \"bool\",\"name\": \"On/Off\",\"activatorId\": \"591853a1094c1d2bdcbe9f21\",\"rank\": 0}";


    private static String TAG = "DESERIALT";
    private static final String currentPath = "app/src/androidTest/java/com/rugged/application/hestia";

    @Test
    public void deserializeBoolActivatorTest(){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Activator.class, new ActivatorDeserializer());
        Gson gson = gsonBuilder.create();

        JsonElement testBool = new JsonParser().parse(testBoolJSON);
        Activator deserializedBool = gson.fromJson(testBool, Activator.class);

        Log.d(TAG,"deserializedBool :: " + deserializedBool);

        JsonObject testBoolJson = (JsonObject) testBool;
        assertTrue(deserializedBool.getId().equals(testBoolJson.get("activatorId").getAsString()));

    }

    public String readJSONFile(String target){
        String everything = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(target));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return everything;
    }

}
