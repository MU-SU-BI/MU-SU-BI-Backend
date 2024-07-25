package com.musubi.domain.location.process;

import com.musubi.global.exception.GlobalExceptionHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;

public class LocationMaker { // 쓰레기 코드 1
    @Value("${naver.client.id}")
    private static String client_id;

    @Value("${naver.client.secret}")
    private static String client_secret;
    public static String makeDistrict(String coordinate) { //128.8442, 35.9011

        try {
            String coord = URLEncoder.encode(coordinate, "UTF-8");
            String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords="+coord+"&output=json";

            URL url = new URL(reqUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);

            BufferedReader br;
            int responseCode = con.getResponseCode();

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String line;
            StringBuffer response = new StringBuffer();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener);

            JSONObject tmp = (JSONObject) object.getJSONArray("results").get(0);
            String result = "";
            for (int i = 1; i < 4; i++) {
                result += tmp.getJSONObject("region").getJSONObject("area" + Integer.toString(i)).get("name");
            }
            return result;

        } catch (Exception e) {
            return null;
        }
    }
}
