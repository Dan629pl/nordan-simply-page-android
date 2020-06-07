package com.nordan.simplypage.dto;

import android.widget.RadioGroup.OnCheckedChangeListener;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class SingleChoiceElement {

    @NonNull
    private String title;
    private int rightSideIconDrawable;
    private int leftSideIconDrawable;
    @NonNull
    private OnCheckedChangeListener onCheckedChangeListener;
    @NonNull
    private List<String> elements;
    private String selectedValue;
}
