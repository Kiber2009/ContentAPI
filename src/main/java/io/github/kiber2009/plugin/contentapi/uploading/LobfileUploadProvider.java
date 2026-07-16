package io.github.kiber2009.plugin.contentapi.uploading;

import com.google.gson.*;
import io.github.kiber2009.plugin.contentapi.ContentApiPlugin;
import io.github.kiber2009.plugin.contentapi.utils.HttpUtils;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class LobfileUploadProvider implements UploadProvider {
    private static final URI UPLOAD_URI = URI.create("https://lobfile.com/api/v3/upload");
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private final String apiKey;

    public LobfileUploadProvider(final String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public @NonNull String uploadBytes(final byte @NonNull [] bytes) {
        final String boundary = UUID.randomUUID().toString();
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(UPLOAD_URI)
                    .header("X-API-Key", apiKey)
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(HttpUtils.createMultipartBody(boundary,
                            new HttpUtils.MultipartEntry("file", bytes))))
                    .build();
            final HttpResponse<String> httpResponse = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            final Response response = ContentApiPlugin.GSON.fromJson(httpResponse.body(), Response.class);
            if (response.success)
                return response.url;
            else
                throw new RuntimeException("Error: " + response.error);
        } catch (final IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public record Response(boolean success, String url, String error) {
        public static final class Deserializer implements JsonDeserializer<Response> {
            public static final Deserializer INSTANCE = new Deserializer();

            private Deserializer() {
            }

            @Override
            public Response deserialize(final JsonElement json, final Type typeOfT,
                                        final JsonDeserializationContext context) throws JsonParseException {
                final JsonObject object = json.getAsJsonObject();

                final JsonElement url = object.get("url");
                final JsonElement error = object.get("error");

                return new Response(object.get("success").getAsBoolean(),
                        url != null ? url.getAsString() : null,
                        error != null ? error.getAsString() : null);
            }
        }
    }
}
