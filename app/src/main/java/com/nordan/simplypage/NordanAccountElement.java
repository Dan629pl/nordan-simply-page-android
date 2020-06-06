package com.nordan.simplypage;

import android.net.Uri;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class NordanAccountElement {

    @NonNull
    private String accountName;
    private String accountEmail;
    private Uri accountPhotoUrl;
}
