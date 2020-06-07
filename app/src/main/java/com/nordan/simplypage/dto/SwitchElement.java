package com.nordan.simplypage.dto;

import android.widget.CompoundButton.OnCheckedChangeListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class SwitchElement {

    @NonNull
    private String title;
    private String subText;
    private boolean isChecked;
    @NonNull
    private OnCheckedChangeListener onCheckedChangeListener;
}
