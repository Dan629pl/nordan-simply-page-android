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
                ._addSeparator()
                .addPhone("123456789", "Call to me")
                .addEmail("nordan.studio@gmail.com", "Write me an email")
                .addSms("123456789", "Send message to me", "Sms message")
                .addGroup("Check my socials", R.color.gray_font_color)
                .addFacebook("facebookId")
                .addInstagram("instagramId")
                .addGithub("Dan629pl")
                .addGooglePlayDeveloperPage("5301320783716798716")
                .addGooglePlayApplicationPage("com.nordan.unknown", "Unknown - Logic Game", R.drawable.unknown_logo)
                .addYoutube("channelId")
                .addWebsite("https://www.google.com", "Website")
                .addSkype("profileId")
                .addLinkedIn("daniel-owczarczyk-8b89a6150")
                .addTwitter("profileId")
                .addGroup(R.mipmap.ic_launcher_round, "Other groups (with left side image)")
                .addMinimalItem(BaseElement.builder().title("Minimal item (only text view)").build())
                .addEmptyItem()
                .addMinimalItem(
                        BaseElement.builder()
                                .title("Version " + BuildConfig.VERSION_NAME)
                                .gravity(Gravity.CENTER)
                                .build())
                .addCopyRightsItem()
                .create();
        setContentView(simplyPage);
    }
}
