package org.example;
import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QuoteOfTheDay {
    private final String jsonFilePath;

    public QuoteOfTheDay(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    public void printQuoteOfTheDay() throws IOException {
        File file = new File(jsonFilePath);

/*this code block reads the quote of the day from a previously stored JSON file,
deserializes it into a Java object, and prints it to the console.*/
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                QuotesApiResponse response = gson.fromJson(reader, QuotesApiResponse.class);

                String quote = response.contents.quotes[0].quote;
                System.out.println("Quote of the day: " + quote);
            }
        } else
        //if the specified file does not exist, saves the response as a JSON file, deserializes the JSON data into a Java object
            {
            OkHttpClient client = new OkHttpClient();
            //construct the URL for the API endpoint by setting the scheme, host, and path segments.
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("https")
                    .host("quotes.rest")
                    .addPathSegment("qod.json")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                // Use Gson to parse the JSON response into a QuotesApiResponse object
                Gson gson = new Gson();
                QuotesApiResponse quotesApiResponse = gson.fromJson(response.body().charStream(), QuotesApiResponse.class);
                // Extract the quote of the day from the response

                String quote = quotesApiResponse.contents.quotes[0].quote;
                System.out.println("Quote of the day: " + quote);
                // Write the JSON response to the file

                FileManager.writeToJsonFile(file, gson.toJson(quotesApiResponse));
            }
        }
    }
//inner class to represent the JSON response from the API
    static class QuotesApiResponse {
        Contents contents;

        static class Contents {
            Quote[] quotes;

            static class Quote {
                String quote;
            }
        }
    }

    static class FileManager {
        static void writeToJsonFile(File file, String json) throws IOException {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(json);
            }
        }
    }
}