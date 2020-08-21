package com.nordan.simplypage.dto;

import android.view.View;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ExtendableElement {
    @NonNull
    private String title;
    private String subText;
    private int rightSideIconDrawable;
    private int leftSideIconDrawable;
    @NonNull
    private View extendView;
    private int gravity;
}
