package com.nordan.sampleapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.nordan.simplypage.NordanSimplyPage;
import com.nordan.simplypage.dto.AccountElement;
import com.nordan.simplypage.dto.BaseElement;
import com.nordan.simplypage.dto.CheckBoxElement;
import com.nordan.simplypage.dto.EditableTextElement;
import com.nordan.simplypage.dto.PageElement;
import com.nordan.simplypage.dto.SeekBarElement;
import com.nordan.simplypage.dto.SingleChoiceElement;
import com.nordan.simplypage.dto.SwitchElement;
import java.util.Arrays;

public class SettingsSample extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View settingPage = new NordanSimplyPage(this)
                .addImageItem(R.drawable.nordan_logo, 300, 75)
                .addGroup(getString(R.string.account_group), R.drawable.account_icon, R.color.gray_font_color)
                .addAccountItem(createAccountElement())
                .addItem(createAccountConfirmedElement())
                .addMinimalItem(createSignOutElement())
                .addEmptyItem(30)
                .addGroup(R.drawable.settings_app_icon, "Application")
                .addSwitchItem(createThemeSwitcherElement())
                .addItem(createTimeRefreshElement())
                .addSingleRadioChoiceItem(createSingleChoiceElement())
                .addCheckBoxItem(createCheckBoxElement())
                .addCheckBoxItem(createCheckBoxExtendableElement())
                .addSeekBarItem(createSeekBarElement())
                .addEditableTextItem(createEditTextElement())
                .addEmptyItem(200)
                .create();
        setContentView(settingPage);
    }

    private EditableTextElement createEditTextElement() {
        String[] array = {"FIRST PARAM", "SECOND PARAM"};
        return EditableTextElement.builder()
                .title("Default message with params")
                .subText("Order number {0} ready for collection in {1} minutes.")
                .textParams(array)
                .onEditTextChangeValueListener(newValue -> Toast.makeText(this, newValue, Toast.LENGTH_LONG).show())
                .build();
    }

    private SeekBarElement createSeekBarElement() {
        return SeekBarElement.builder()
                .title("SeekBar Element")
                .subText(" Special subtext")
                .progress(20)
                .minValue(1)
                .maxValue(30)
                .onSeekBarChangeValueListener(
                        newValue -> Toast.makeText(SettingsSample.this, "New value: " + newValue, Toast.LENGTH_SHORT).show())
                .build();
    }

    private CheckBoxElement createCheckBoxExtendableElement() {
        return CheckBoxElement.builder()
                .title("Extendable CheckBox Element")
                .isChecked(false)
                .subText("Subtext is available here")
                .onCheckedChangeListener((buttonView, isChecked) -> {
                })
                .extendView(createExtendView())
                .build();
    }

    private View createExtendView() {
        return new NordanSimplyPage(this)
                .addGroup(R.color.gray_font_color, "Extended View")
                .addCheckBoxItem(createCheckBoxElement())
                .addPhone("123456789", "Call to me")
                .addSwitchItem(createThemeSwitcherElement())
                .addSingleRadioChoiceItem(createSingleChoiceElement())
                .create();
    }

    private CheckBoxElement createCheckBoxElement() {
        return CheckBoxElement.builder()
                .title("CheckBox Element")
                .isChecked(true)
                .subText("Subtext is available here")
                .onCheckedChangeListener((buttonView, isChecked) -> {
                })
                .build();
    }

    private SingleChoiceElement createSingleChoiceElement() {
        return SingleChoiceElement.builder()
                .title("Single choice element")
                .elements(Arrays.asList("Never", "Ever", "Give up"))
                .selectedValue("Ever")
                .onCheckedChangeListener(
                        (group, checkedId) -> Toast.makeText(this, "Select " + checkedId + " index element", Toast.LENGTH_SHORT).show())
                .build();
    }

    private PageElement createTimeRefreshElement() {
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.ic_twotone_access_time_24)
                .title("Background service refresh time")
                .subText("30 minutes")
                .build();
    }

    private PageElement createAccountConfirmedElement() {
        return PageElement.builder()
                .title("Account confirmed")
                .subText("Your account is confirmed")
                .rightSideIconDrawable(R.drawable.check_icon)
                .build();
    }

    private BaseElement createSignOutElement() {
        return BaseElement.builder()
                .onClickListener(click -> Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show())
                .title("Sign out")
                .build();
    }

    private SwitchElement createThemeSwitcherElement() {
        return SwitchElement.builder()
                .title("Dark mode")
                .isChecked(true)
                .subText("Switch to enable dark mode application.")
                .onCheckedChangeListener(
                        (buttonView, isChecked) -> Toast.makeText(this, "IS CHECKED: " + isChecked, Toast.LENGTH_SHORT).show())
                .build();
    }

    private AccountElement createAccountElement() {
        return AccountElement.builder()
                .accountName("Sample Name")
                .accountEmail("sample@email.com")
                .accountPhotoUrl(Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.sample_avatar))
                .build();
    }
}
