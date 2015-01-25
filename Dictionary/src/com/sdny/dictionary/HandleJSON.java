package com.sdny.dictionary;

/**
 * Created by Neha Surawar on 1/26/2015.
 */

        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

        import org.json.JSONObject;
        import org.json.JSONArray;
        import android.annotation.SuppressLint;

public class HandleJSON {
    private String textMeaning = "meaning";

    public String getTextMeaning(){
        return textMeaning;
    }
    public volatile boolean parsingComplete = true;
    private String urlString = null;
    public HandleJSON(String url){
        this.urlString = url;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject reader = new JSONObject(in);

            JSONArray meanings  = reader.getJSONArray("meanings");
            for (int i = 0; i < meanings.length(); i++) {
                JSONObject c = meanings.getJSONObject(i);
                textMeaning += c.getString("text");
            }
            parsingComplete = false;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void fetchJSON(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data);
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}