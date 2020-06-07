package com.nordan.simplypage.dto;

import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class CheckBoxElement {

    @NonNull
    private String title;
    private String subText;
    private int rightSideIconDrawable;
    @NonNull
    private OnCheckedChangeListener onCheckedChangeListener;
    private boolean isChecked;
    private View extendView;
}
