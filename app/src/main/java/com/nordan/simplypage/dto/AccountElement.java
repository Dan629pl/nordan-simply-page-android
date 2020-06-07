package com.nordan.simplypage.dto;

import android.net.Uri;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class AccountElement {

    @NonNull
    private String accountName;
    private String accountEmail;
    private Uri accountPhotoUrl;
}
