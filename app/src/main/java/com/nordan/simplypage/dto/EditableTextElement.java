package com.nordan.simplypage.dto;

import com.nordan.simplypage.OnEditTextChangeValueListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class EditableTextElement {

    @NonNull
    private String title;
    @NonNull
    private String subText;
    @NonNull
    private OnEditTextChangeValueListener onEditTextChangeValueListener;
    private String[] textParams;
    private String helperTextParams;
    private int rightSideIconDrawable;
    private int leftSideIconDrawable;
}
