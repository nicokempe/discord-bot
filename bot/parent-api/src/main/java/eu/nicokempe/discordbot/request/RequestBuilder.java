package eu.nicokempe.discordbot.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import eu.nicokempe.discordbot.IDiscordBot;
import lombok.Builder;
import lombok.SneakyThrows;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.function.Consumer;

@Builder
public class RequestBuilder {

    @Builder.Default
    private final String url = "http://127.0.0.1:8081/api/";
    private final String route;
    private final FormBody.Builder body;
    private final Consumer<Response> response;
    private final IDiscordBot.AuthKey authKey;

    public void post() {
        if (authKey != null) body.add("authKey", authKey.getKey());
        Request request = new Request.Builder()
                .url(url + route)
                .post(body.build())
                .build();
        request(request);
    }

    public void put() {
        if (authKey != null) body.add("authKey", authKey.getKey());
        Request request = new Request.Builder()
                .url(url + route)
                .put(body.build())
                .build();
        request(request);
    }

    public void delete() {
        if (authKey != null) body.add("authKey", authKey.getKey());
        Request request = new Request.Builder()
                .url(url + route)
                .delete(body.build())
                .build();
        request(request);
    }

    @SneakyThrows
    private void request(Request request) {
        OkHttpClient httpClient = new OkHttpClient();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            if (this.response != null) this.response.accept(response);
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout");
        }
    }

    public JsonElement get() {
        URL url;
        try {
            url = new URL(this.url + route);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return JsonParser.parseReader(reader);
    }

}
