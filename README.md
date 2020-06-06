# Nordan Simply Page
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=plastic)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)


## Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {
	...
	implementation 'com.github.Dan629pl:NordanSimplyPage:1.0.2'
}
```
<h1>Nordan Simply Pages</h1>

<h3>About Us Page Style</h3>

```diff
        View simplyPage = new NordanSimplyPage(this)
                        .addImageItem(R.drawable.nordan_logo)
                        .addDescriptionItem(R.string.lorem_ipsum)
                        .addSeparator()
                        .addGroup("Contact", R.color.gray_font_color)
                        .addPhone("123456789", "Call to me")
                        .addEmail("email address","Write me an email")
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
                        .addGroup(R.drawable.androidicon, "Other groups (with left side image)")
                        .addMinimalItem(NordanBasePageElement.builder().title("Minimal item (only text view)").build())
                        .addEmptyItem()
                        .addMinimalItem(
                                NordanBasePageElement.builder()
                                        .title("Version " + BuildConfig.VERSION_NAME)
                                        .gravity(Gravity.CENTER_HORIZONTAL)
                                        .build())
                        .addCopyRightsItem()
                        .create();
                setContentView(simplyPage);
```
## Screenshots

![Gif About_Us](https://github.com/Dan629pl/NordanSimplyPage/blob/master/img/page_gif.gif)



![Screenshots](https://github.com/Dan629pl/NordanSimplyPage/blob/master/img/screenshot_side_hr.png)

<h3>Settings Page Style</h3>

```diff
               ...
               View settingPage = new NordanSimplyPage(this)
                            .addGroup(getString(R.string.account), R.drawable.account_icon, R.color.rec)
                            .addAccountItem(createAccountElement())
                            .addItem(createSignElement())
                            .addItem(createResetProgressElement())
                            .addGroup(getString(R.string.language_title), R.drawable.langugage_icon, R.color.rec)
                            .addItem(createLanguageElement())
                            .create();
                    setContentView(settingPage);
               ...

    private NordanPageElement createLanguageElement() {
           return NordanPageElement.builder()
                  .title(/* PUT HERE YPUR STRING */)
                  .onClickListener(click -> {/* DO SOMETHING HERE*/})
                  .build();
       }
   
       private NordanPageElement createResetProgressElement() {
           return NordanPageElement.builder()
                   .title(/* PUT HERE YPUR STRING */)
                   .onClickListener(click -> {/* DO SOMETHING HERE*/})
                   .build();
       }
   
       private NordanPageElement createSignElement() {
           return NordanPageElement.builder()
                   .title(/* PUT HERE YPUR STRING */)
                   .onClickListener(click -> {/* DO SOMETHING HERE*/})
                   .build();
       }
   
       private NordanAccountElement createAccountElement() {
           return NordanAccountElement.builder()
                             .accountEmail(googleSignInAccount.getEmail())
                             .accountPhotoUrl(googleSignInAccount.getPhotoUrl())
                             .accountName(googleSignInAccount.getDisplayName())
                             .build();
       }
```
## Screenshots

![Gif Settings_Page](https://github.com/Dan629pl/NordanSimplyPage/blob/master/img/setting_page.gif)



![Screenshots](https://github.com/Dan629pl/NordanSimplyPage/blob/master/img/settings_hr.png)

## Donation
If this library  help you reduce time to develop, you can buy me a coffee! :) 

<a href="https://www.buymeacoffee.com/Dan629"><img src="https://www.buymeacoffee.com/assets/img/bmc-meta-new/apple-icon-72x72.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

```
Copyright 2020 Daniel Owczarczyk

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.