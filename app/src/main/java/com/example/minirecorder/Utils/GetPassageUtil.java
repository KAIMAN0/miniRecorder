package com.example.minirecorder.Utils;

import org.json.JSONObject;

import java.io.InputStream;

public class GetPassageUtil {
    public static int NUMBER_OF_PASSAGES = 3;

    public static String getPassageByIndex(InputStream jsonStream, int index){
        String label = "";
        try {

            byte[] jsonData = new byte[jsonStream.available()];
            jsonStream.read(jsonData);
            jsonStream.close();

            String jsonString = new String(jsonData,"utf-8");

            JSONObject object = new JSONObject(jsonString);

            label = object.getString(String.valueOf(index));



        }
        catch (Exception e){


        }
        return label;
    }

}
