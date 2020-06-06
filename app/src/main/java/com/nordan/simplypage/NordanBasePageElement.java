package com.nordan.simplypage;

import android.content.Intent;
import android.view.View;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NordanBasePageElement {

    @NonNull
    private String title;
    private Intent intent;
    private int gravity;
    private View.OnClickListener onClickListener;
}
