package com.nordan.simplypage;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NordanPageElement extends NordanBasePageElement {

    private String subText;
    private int rightSideIconDrawable;
    private int leftSideIconDrawable;
}
