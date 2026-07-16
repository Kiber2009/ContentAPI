package io.github.kiber2009.plugin.contentapi.utils;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class HttpUtils {
    private HttpUtils() {
    }

    @Contract(pure = true)
    public static byte @NonNull [] createMultipartBody(final @NonNull String boundary,
                                                       final @NonNull MultipartEntry @NonNull ... entries) throws IOException {
        final ByteArrayOutputStream builder = new ByteArrayOutputStream();
        final byte[] separator = ("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8);

        builder.write(separator);

        for (final MultipartEntry entry : entries) {
            builder.write(entry.getBytes());
            builder.write(separator);
        }

        builder.write("--\r\n".getBytes(StandardCharsets.UTF_8));

        return builder.toByteArray();
    }

    public record MultipartEntry(@Contract(pure = true) @NonNull String name,
                                 @Contract(pure = true) byte @NonNull [] value,
                                 @Contract(pure = true) @Nullable String fileName,
                                 @Contract(pure = true) @Nullable String contentType) {
        public MultipartEntry(final @NonNull String name, final byte @NonNull [] value) {
            this(name, value, null, null);
        }

        public MultipartEntry(final @NonNull String name, final @NonNull String value) {
            this(name, value.getBytes(StandardCharsets.UTF_8), null, null);
        }

        @Contract(pure = true)
        public byte[] getBytes() {
            final StringBuilder header = new StringBuilder("Content-Disposition: form-data; name=\"" + name + "\"");
            if (fileName != null)
                header.append("; filename=\"").append(fileName).append("\"");
            header.append("\r\n");
            if (contentType != null)
                header.append("Content-Type: ").append(contentType).append("\r\n");

            final byte[] headerBytes = header.toString().getBytes(StandardCharsets.UTF_8);

            final byte[] res = new byte[headerBytes.length + value.length];
            System.arraycopy(headerBytes, 0, res, 0, headerBytes.length);
            System.arraycopy(value, 0, res, headerBytes.length, value.length);

            return res;
        }
    }
}
