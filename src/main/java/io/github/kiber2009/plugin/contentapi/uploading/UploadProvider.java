package io.github.kiber2009.plugin.contentapi.uploading;

import org.jspecify.annotations.NonNull;

public interface UploadProvider {
    @NonNull String uploadBytes(final byte @NonNull [] bytes);
}
