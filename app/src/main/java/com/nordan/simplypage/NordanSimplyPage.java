package com.nordan.simplypage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import lombok.NonNull;

public class NordanSimplyPage {

    @SuppressLint("StaticFieldLeak")
    static Activity activity;
    private final LayoutInflater layoutInflater;
    private final View pageView;

    public NordanSimplyPage(Activity activity) {
        NordanSimplyPage.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        this.pageView = layoutInflater.inflate(R.layout.nordan_page_activity, null);
    }

    static boolean isAppInstalled(String appName) {
        return Optional.ofNullable(activity)
                .map(ContextWrapper::getPackageManager)
                .map(packageManager -> packageManager.getInstalledPackages(0))
                .orElse(Collections.emptyList())
                .stream()
                .map(packageInfo -> packageInfo.packageName)
                .anyMatch(appName::equals);
    }

    public NordanSimplyPage addEmptyItem() {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, activity.getResources().getDisplayMetrics());
        RelativeLayout view = new RelativeLayout(activity);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, px);
        view.setLayoutParams(layoutParams);
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        return this;
    }

    public NordanSimplyPage addCopyRightsItem() {
        String copyrights = String.format(activity.getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        NordanBasePageElement copyRightsElement = NordanBasePageElement.builder()
                .title(copyrights)
                .gravity(Gravity.CENTER_HORIZONTAL)
                .build();
        return addSingleBottomItem(copyRightsElement);
    }

    public NordanSimplyPage addSingleBottomItem(NordanBasePageElement basePageElement) {
        LinearLayout bottomLinear = pageView.findViewById(R.id.single_bottom_element);
        bottomLinear.setVisibility(View.VISIBLE);
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.minimal_item_view, null);
        MaterialTextView textItem = view.findViewById(R.id.item_text);
        textItem.setText(basePageElement.getTitle());
        if (basePageElement.getGravity() != 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
            view.setGravity(basePageElement.getGravity());
            textItem.setLayoutParams(params);
        }
        if (bottomLinear.getChildCount() > 0) {
            bottomLinear.removeAllViews();
        }
        bottomLinear.addView(view);
        return this;
    }

    public NordanSimplyPage addMinimalItem(NordanBasePageElement basePageElement) {
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.minimal_item_view, null);
        MaterialTextView textItem = view.findViewById(R.id.item_text);
        textItem.setText(basePageElement.getTitle());
        if (basePageElement.getGravity() != 0) {
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, activity.getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, px);
            RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(viewParams);
            view.setGravity(basePageElement.getGravity());
            textItem.setLayoutParams(textParams);
        }
        TypedValue outValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.selectableItemBackground, outValue, true);
        view.setBackgroundResource(outValue.resourceId);
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        return this;
    }

    public NordanSimplyPage addItem(NordanPageElement pageElement) {
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.standard_item_view, null);
        ImageView imageLeftSide = view.findViewById(R.id.image_left);
        ImageView imageRightSide = view.findViewById(R.id.image_right);
        MaterialTextView textItem = view.findViewById(R.id.item_text);
        MaterialTextView subTextItem = view.findViewById(R.id.item_subtext);
        TypedValue outValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.selectableItemBackground, outValue, true);
        view.setBackgroundResource(outValue.resourceId);
        if (pageElement.getOnClickListener() != null) {
            view.setOnClickListener(pageElement.getOnClickListener());
        } else if (pageElement.getIntent() != null) {
            view.setOnClickListener(click -> activity.startActivity(pageElement.getIntent()));
        }
        Optional.of(pageElement.getLeftSideIconDrawable())
                .filter(value -> value != 0)
                .ifPresent(resId -> {
                    Glide.with(activity).load(resId).into(imageLeftSide);
                    imageLeftSide.setVisibility(View.VISIBLE);
                });
        Optional.of(pageElement.getRightSideIconDrawable())
                .filter(value -> value != 0)
                .ifPresent(resId -> {
                    Glide.with(activity).load(resId).into(imageRightSide);
                    imageRightSide.setVisibility(View.VISIBLE);
                });
        textItem.setText(pageElement.getTitle());
        Optional.ofNullable(pageElement.getSubText())
                .filter(subText -> !subText.isEmpty())
                .ifPresent(subText -> {
                    subTextItem.setText(subText);
                    subTextItem.setVisibility(View.VISIBLE);
                });
        if (pageElement.getGravity() != 0) {/*TODO*/}
        LinearLayout wrapper = pageView.findViewById(R.id.page_provider);
        wrapper.addView(view);
        addSeparator();
        return this;
    }

    public NordanSimplyPage addAccountItem(NordanAccountElement accountElement) {
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.account_item_view, null);
        ImageView accountPhoto = view.findViewById(R.id.account_photo);
        MaterialTextView textAccountName = view.findViewById(R.id.account_name);
        MaterialTextView textAccountEmail = view.findViewById(R.id.account_email);
        Optional.ofNullable(accountElement.getAccountPhotoUrl()).ifPresent(uri -> {
            Glide.with(activity).load(uri).into(accountPhoto);
            accountPhoto.setVisibility(View.VISIBLE);
        });
        textAccountName.setText(accountElement.getAccountName());
        textAccountEmail.setText(accountElement.getAccountEmail());
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        addSeparator();
        return this;
    }

    public NordanSimplyPage addImageItem(int resourceId) {
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.image_item_view, null);
        ImageView image = view.findViewById(R.id.image);
        Glide.with(activity).load(resourceId).into(image);
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        return this;
    }

    public NordanSimplyPage addDescriptionItem(int resourceId) {
        return addDescriptionItem(activity.getString(resourceId));
    }

    public NordanSimplyPage addDescriptionItem(String message) {
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.description_item_view, null);
        MaterialTextView textDescription = view.findViewById(R.id.item_description);
        textDescription.setText(message);
        if (textDescription.getParent() != null) {
            ((ViewGroup) textDescription.getParent()).removeView(textDescription);
        }
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(textDescription);
        return this;
    }

    public NordanSimplyPage addGroup(int leftSideImageResourceId, String name) {
        return addGroup(name, leftSideImageResourceId, 0);
    }

    public NordanSimplyPage addGroup(String name, int colorResourceId) {
        return addGroup(name, 0, colorResourceId);
    }

    public NordanSimplyPage addGroup(String name) {
        return addGroup(name, 0, 0);
    }

    public NordanSimplyPage addGroup(@NonNull String name, int leftSideImageResourceId, int colorResourceId) {
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.group_idem, null);
        ImageView imageLeftSide = view.findViewById(R.id.image_group);
        MaterialTextView groupTitle = view.findViewById(R.id.group_title);
        if (leftSideImageResourceId != 0) {
            Glide.with(activity).load(leftSideImageResourceId).into(imageLeftSide);
        } else {
            imageLeftSide.setVisibility(View.INVISIBLE);
        }
        if (colorResourceId != 0) {
            groupTitle.setTextColor(activity.getColor(colorResourceId));
        }
        groupTitle.setText(name);
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        return this;
    }

    public NordanSimplyPage addSeparator() {
        int dimensionPixelSize = activity.getResources().getDimensionPixelSize(R.dimen.nordan_simply_page_separator_height);
        ((LinearLayout) pageView.findViewById(R.id.page_provider))
                .addView(getSeparator(), new LayoutParams(LayoutParams.MATCH_PARENT, dimensionPixelSize));
        return this;
    }

    public NordanSimplyPage addFacebook(String id) {
        addItem(NordanSimplyPagePatterns.createFacebookElement(id));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addGooglePlayStore(String id) {
        addItem(NordanSimplyPagePatterns.createGooglePlayStoreElement(id));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addGithub(String id) {
        addItem(NordanSimplyPagePatterns.createGithubElement(id));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addInstagram(String id) {
        addItem(NordanSimplyPagePatterns.createInstagramElement(id));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addEmail(String email, String title) {
        addItem(NordanSimplyPagePatterns.createEmailElement(email, title));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addEmail(String email) {
        return addEmail(email, activity.getString(R.string.email));
    }

    public NordanSimplyPage addPhone(String phoneNumber, String title) {
        addItem(NordanSimplyPagePatterns.createPhoneItem(phoneNumber, title));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addSms(String phoneNumber, String title, String messageText) {
        addItem(NordanSimplyPagePatterns.createMessageItem(phoneNumber, title, messageText));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addWebsite(String url, String title) {
        addItem(NordanSimplyPagePatterns.createWebsiteElement(url, title));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addSkype(String id) {
        addItem(NordanSimplyPagePatterns.createSkypeElement(id));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addYoutube(String id) {
        addItem(NordanSimplyPagePatterns.createYoutubeElement(id));
        addSeparator();
        return this;
    }

    public NordanSimplyPage addTwitter(String id) {
        addItem(NordanSimplyPagePatterns.createTwitterElement(id));
        addSeparator();
        return this;
    }

    public View create() {
        return pageView;
    }

    private View getSeparator() {
        return activity.getLayoutInflater().inflate(R.layout.separator_item, null);
    }
}
