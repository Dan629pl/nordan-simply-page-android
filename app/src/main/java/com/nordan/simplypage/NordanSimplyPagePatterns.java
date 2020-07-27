package com.nordan.simplypage;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;

import com.nordan.simplypage.dto.PageElement;

import java.util.Collections;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NordanSimplyPagePatterns {

    private final Activity activity;

    boolean isAppInstalled(String appName) {
        return Optional.ofNullable(activity)
                .map(ContextWrapper::getPackageManager)
                .map(packageManager -> packageManager.getInstalledPackages(0))
                .orElse(Collections.emptyList())
                .stream()
                .filter(packageInfo -> packageInfo.applicationInfo.enabled)
                .map(packageInfo -> packageInfo.packageName)
                .anyMatch(appName::equals);
    }

    PageElement createEmailElement(String email, String title) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.email_icon)
                .title(title)
                .intent(emailIntent)
                .build();
    }

    PageElement createFacebookElement(String facebookProfileId) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        if (isAppInstalled("com.facebook.katana")) {
            facebookIntent.setPackage("com.facebook.katana");
            Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/" + facebookProfileId);
            facebookIntent.setData(uri);
        } else {
            facebookIntent.addCategory(Intent.CATEGORY_BROWSABLE);
            facebookIntent.setData(Uri.parse("http://m.facebook.com/" + facebookProfileId));
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.facebook_icon)
                .title("Facebook")
                .intent(facebookIntent)
                .build();
    }

    PageElement createTwitterElement(String twitterProfileId) {
        Intent twitterIntent = new Intent();
        twitterIntent.setAction(Intent.ACTION_VIEW);
        twitterIntent.addCategory(Intent.CATEGORY_BROWSABLE);

        if (isAppInstalled("com.twitter.android")) {
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

    PageElement createGooglePlayDeveloperPageElement(String developerId) {
        Uri uri = Uri.parse("https://play.google.com/store/apps/dev?id=" + developerId);
        Intent googlePlayStoreIntent = new Intent(Intent.ACTION_VIEW, uri);
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.google_play_icon)
                .title("Google Play")
                .intent(googlePlayStoreIntent)
                .build();
    }

    PageElement createGooglePlayApplicationPageElement(String applicationId, String tittle) {
        return createGooglePlayApplicationPageElement(applicationId, tittle, R.drawable.google_play_icon);
    }

    PageElement createGooglePlayApplicationPageElement(String applicationId, String tittle, int iconRes) {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + applicationId);
        Intent googlePlayStoreIntent = new Intent(Intent.ACTION_VIEW, uri);
        return PageElement.builder()
                .leftSideIconDrawable(iconRes)
                .title(tittle)
                .intent(googlePlayStoreIntent)
                .build();
    }

    PageElement createYoutubeElement(String youtubeChannelId) {
        Intent youTubeIntent = new Intent(Intent.ACTION_VIEW);
        youTubeIntent.setData(Uri.parse(String.format("http://youtube.com/channel/%s", youtubeChannelId)));
        if (isAppInstalled("com.google.android.youtube")) {
            youTubeIntent.setPackage("com.google.android.youtube");
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.youtube_icon)
                .title("YouTube")
                .intent(youTubeIntent)
                .build();
    }

    PageElement createInstagramElement(String instagramProfileId) {
        Intent instagramIntent = new Intent();
        instagramIntent.setAction(Intent.ACTION_VIEW);
        instagramIntent.setData(Uri.parse("http://instagram.com/_u/" + instagramProfileId));

        if (isAppInstalled("com.instagram.android")) {
            instagramIntent.setPackage("com.instagram.android");
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.instagram_icon)
                .title("Instagram")
                .intent(instagramIntent)
                .build();
    }

    PageElement createGithubElement(String githubProfileId) {
        Intent githubIntent = new Intent();
        githubIntent.setAction(Intent.ACTION_VIEW);
        githubIntent.setData(Uri.parse(String.format("https://github.com/%s", githubProfileId)));
        if (isAppInstalled("com.github.android")) {
            githubIntent.setPackage("com.github.android");
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.github_icon)
                .title("Github")
                .intent(githubIntent)
                .build();
    }

    PageElement createLinkedInElement(String linkedInProfileId) {
        Intent linkedInIntent = new Intent();
        linkedInIntent.setAction(Intent.ACTION_VIEW);
        linkedInIntent.setData(Uri.parse(String.format("https://www.linkedin.com/in/%s", linkedInProfileId)));
        if (isAppInstalled("com.linkedin.android")) {
            linkedInIntent.setPackage("com.linkedin.android");
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.linkedin_icon)
                .title("Linkedin")
                .intent(linkedInIntent)
                .build();
    }

    PageElement createWebsiteElement(String url, String tittle) {
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

    PageElement createSkypeElement(String skypeProfileId) {
        Intent skypeIntent = new Intent(Intent.ACTION_VIEW);
        if (isAppInstalled("com.skype.raider")) {
            skypeIntent.setData(Uri.parse("skype:" + skypeProfileId + "?chat"));
            skypeIntent.setPackage("com.skype.raider");
        } else {
            skypeIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.skype.raider"));
        }
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.skype_icon)
                .title("Skype")
                .intent(skypeIntent)
                .build();
    }

    PageElement createPhoneItem(String phoneNumber, String title) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.call_icon)
                .title(title)
                .intent(callIntent)
                .build();
    }

    PageElement createMessageItem(String phoneNumber, String title, String smsText) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null));
        smsIntent.putExtra("sms_body", smsText);
        return PageElement.builder()
                .leftSideIconDrawable(R.drawable.message_icon)
                .title(title)
                .intent(smsIntent)
                .build();
    }
}
