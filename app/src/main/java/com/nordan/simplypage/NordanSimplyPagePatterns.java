package com.nordan.simplypage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.nordan.simplypage.dto.PageElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class NordanSimplyPagePatterns {

    static PageElement createEmailElement(String email, String title) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.email_icon)
                .title(title)
                .intent(emailIntent)
                .build();
    }

    static PageElement createFacebookElement(String facebookProfileId) {
        Intent facebookIntent = new Intent();
        facebookIntent.setAction(Intent.ACTION_VIEW);
        facebookIntent.addCategory(Intent.CATEGORY_BROWSABLE);

        if (NordanSimplyPage.isAppInstalled("com.facebook.katana")) {
            facebookIntent.setPackage("com.facebook.katana");
            int versionCode = 0;
            try {
                versionCode = NordanSimplyPage.activity.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (versionCode >= 3002850) {
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + "http://m.facebook.com/" + facebookProfileId);
                facebookIntent.setData(uri);
            } else {
                Uri uri = Uri.parse("fb://page/" + facebookProfileId);
                facebookIntent.setData(uri);
            }
        } else {
            facebookIntent.setData(Uri.parse("http://m.facebook.com/" + facebookProfileId));
        }

        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.facebook_icon)
                .title("Facebook")
                .intent(facebookIntent)
                .build();
    }

    static PageElement createTwitterElement(String twitterProfileId) {
        Intent twitterIntent = new Intent();
        twitterIntent.setAction(Intent.ACTION_VIEW);
        twitterIntent.addCategory(Intent.CATEGORY_BROWSABLE);

        if (NordanSimplyPage.isAppInstalled("com.twitter.android")) {
            twitterIntent.setPackage("com.twitter.android");
            twitterIntent.setData(Uri.parse(String.format("twitter://user?screen_name=%s", twitterProfileId)));
        } else {
            twitterIntent.setData(Uri.parse(String.format("http://twitter.com/twitterIntent/user?screen_name=%s", twitterProfileId)));
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.twitter_icon)
                .title("Twitter")
                .intent(twitterIntent)
                .build();
    }

    static PageElement createGooglePlayStoreElement(String googlePlayStoreId) {
        Uri uri = Uri.parse("market://details?id=" + googlePlayStoreId);
        Intent googlePlayStoreIntent = new Intent(Intent.ACTION_VIEW, uri);
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.google_play_icon)
                .title("Google Play Store")
                .intent(googlePlayStoreIntent)
                .build();
    }

    static PageElement createYoutubeElement(String youtubeChannelId) {
        Intent youTubeIntent = new Intent();
        youTubeIntent.setAction(Intent.ACTION_VIEW);
        youTubeIntent.setData(Uri.parse(String.format("http://youtube.com/channel/%s", youtubeChannelId)));

        if (NordanSimplyPage.isAppInstalled("com.google.android.youtube")) {
            youTubeIntent.setPackage("com.google.android.youtube");
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.youtube_icon)
                .title("YouTube")
                .intent(youTubeIntent)
                .build();
    }

    static PageElement createInstagramElement(String instagramProfileId) {
        Intent instagramIntent = new Intent();
        instagramIntent.setAction(Intent.ACTION_VIEW);
        instagramIntent.setData(Uri.parse("http://instagram.com/_u/" + instagramProfileId));

        if (NordanSimplyPage.isAppInstalled("com.instagram.android")) {
            instagramIntent.setPackage("com.instagram.android");
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.instagram_icon)
                .title("Instagram")
                .intent(instagramIntent)
                .build();
    }

    static PageElement createGithubElement(String githubProfileId) {
        Intent githubIntent = new Intent();
        githubIntent.setAction(Intent.ACTION_VIEW);
        githubIntent.setData(Uri.parse(String.format("https://github.com/%s", githubProfileId)));
        if (NordanSimplyPage.isAppInstalled("com.github.android")) {
            githubIntent.setPackage("com.github.android");
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.github_icon)
                .title("Github")
                .intent(githubIntent)
                .build();
    }

    static PageElement createWebsiteElement(String url, String tittle) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Uri uri = Uri.parse(url);
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.world_icon)
                .title(tittle)
                .intent(websiteIntent)
                .build();
    }

    static PageElement createSkypeElement(String skypeProfileId) {
        Intent skypeIntent = new Intent();
        skypeIntent.setAction(Intent.ACTION_VIEW);
        skypeIntent.setData(Uri.parse("skype:" + skypeProfileId));
        if (NordanSimplyPage.isAppInstalled("com.skype.raider")) {
            skypeIntent.setPackage("com.skype.raider");
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.skype_icon)
                .title("Skype")
                .intent(skypeIntent)
                .build();
    }

    static PageElement createPhoneItem(String phoneNumber, String title) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.call_icon)
                .title(title)
                .intent(callIntent)
                .build();
    }

    static PageElement createMessageItem(String phoneNumber, String title, String smsText) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null));
        smsIntent.putExtra("sms_body", smsText);
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.message_icon)
                .title(title)
                .intent(smsIntent)
                .build();
    }
}
