package com.nordan.sampleapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.nordan.simplypage.NordanSimplyPage;
import com.nordan.simplypage.dto.BaseElement;

public class AboutUsSample extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View simplyPage = new NordanSimplyPage(this)
                .addImageItem(R.drawable.nordan_logo)
                .addDescriptionItem(R.string.lorem_ipsum)
                .addSeparator()
                .addGroup("Contact", R.color.gray_font_color)
                .addPhone("123456789", "Call to me")
                .addEmail("email address", "Write me an email")
                .addSms("123456789", "Send message to me", "Sms message")
                .addGroup("Check my socials", R.color.gray_font_color)
                .addFacebook("facebookId")
                .addInstagram("instagramId")
                .addGithub("githubId")
                .addGooglePlayStore("com.google.android.googlequicksearchbox")
                .addYoutube("channelId")
                .addWebsite("https://www.google.com", "Website")
                .addSkype("profileId")
                .addTwitter("profileId")
                .addGroup(R.mipmap.ic_launcher_round, "Other groups (with left side image)")
                .addMinimalItem(BaseElement.builder().title("Minimal item (only text view)").build())
                .addEmptyItem()
                .addMinimalItem(
                        BaseElement.builder()
                                .title("Version " + BuildConfig.VERSION_NAME)
                                .gravity(Gravity.CENTER_HORIZONTAL)
                                .build())
                .addCopyRightsItem()
                .create();
        setContentView(simplyPage);
    }
}
