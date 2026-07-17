package io.github.kiber2009.plugin.contentapi.uploading;

import com.google.gson.*;
import io.github.kiber2009.plugin.contentapi.ContentApiPlugin;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;

public class LobfileUploadProvider implements UploadProvider {
    private static final URI UPLOAD_URI = URI.create("https://lobfile.com/api/v3/upload");

    private final String apiKey;

    public LobfileUploadProvider(final String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public @NonNull String uploadBytes(final @NonNull String fileName, final byte @NonNull [] bytes) {
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            final HttpPost request = new HttpPost(UPLOAD_URI);
            request.setHeader("X-API-Key", apiKey);
            request.setEntity(MultipartEntityBuilder.create()
                    .addBinaryBody("file", bytes, ContentType.APPLICATION_OCTET_STREAM, fileName)
                    .addTextBody("sha256", "john_doe")
                    .build());
            try (final CloseableHttpResponse httpResponse = client.execute(request)) {
                final String responseString = EntityUtils.toString(httpResponse.getEntity());
                final Response response = ContentApiPlugin.GSON.fromJson(responseString, Response.class);
                if (response.success)
                    return response.url;
                else
                    throw new RuntimeException("Error: " + response.error);
            }
        } catch (final IOException e) {
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
