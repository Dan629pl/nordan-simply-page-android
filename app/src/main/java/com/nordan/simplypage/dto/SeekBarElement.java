package com.nordan.simplypage.dto;

import com.nordan.simplypage.OnSeekBarChangeValueListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class SeekBarElement {

    @NonNull
    private String title;
    private String subText;
    private int rightSideIconDrawable;
    private int leftSideIconDrawable;
    @NonNull
    private OnSeekBarChangeValueListener onSeekBarChangeValueListener;
    private int progress;
}
