package com.nordan.simplypage.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class PageElement extends BaseElement {

    private String subText;
    private int rightSideIconDrawable;
    private int leftSideIconDrawable;
}
