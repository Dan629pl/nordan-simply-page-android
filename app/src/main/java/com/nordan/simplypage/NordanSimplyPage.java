package com.nordan.simplypage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;
import com.nordan.simplypage.dto.AccountElement;
import com.nordan.simplypage.dto.BaseElement;
import com.nordan.simplypage.dto.PageElement;
import com.nordan.simplypage.dto.SingleChoiceElement;
import com.nordan.simplypage.dto.SwitchElement;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
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
        return addEmptyItem(64);
    }

    public NordanSimplyPage addEmptyItem(int height) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, activity.getResources().getDisplayMetrics());
        RelativeLayout view = new RelativeLayout(activity);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, px);
        view.setLayoutParams(layoutParams);
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        return this;
    }

    public NordanSimplyPage addCopyRightsItem() {
        String copyrights = String.format(activity.getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        BaseElement copyRightsElement = BaseElement.builder()
                .title(copyrights)
                .gravity(Gravity.CENTER_HORIZONTAL)
                .build();
        return addSingleBottomItem(copyRightsElement);
    }

    public NordanSimplyPage addSingleRadioChoiceItem(SingleChoiceElement element) {
        final boolean[] isResize = {false};
        LinearLayout view = (LinearLayout) layoutInflater.inflate(R.layout.radio_choice_view, null);
        RelativeLayout headerView = view.findViewById(R.id.head_item);
        ImageView headerImageLeftSide = headerView.findViewById(R.id.image_left);
        ImageView headerImageRightSide = headerView.findViewById(R.id.image_right);
        MaterialTextView headerTextItem = headerView.findViewById(R.id.item_text);
        MaterialTextView headerSubTextItem = headerView.findViewById(R.id.item_subtext);
        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        for (int i = 0; i < element.getElements().size(); i++) {
            RadioButton radioButton = (RadioButton) layoutInflater.inflate(R.layout.radio_button, null);
            radioButton.setId(i);
            radioButton.setText(element.getElements().get(i));
            radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                headerSubTextItem.setText(radioButton.getText());
                headerSubTextItem.setVisibility(View.VISIBLE);
            });
            radioGroup.addView(radioButton);
        }
        radioGroup.setOnCheckedChangeListener(element.getOnCheckedChangeListener());
        Optional.of(element.getRightSideIconDrawable())
                .filter(value -> value != 0)
                .ifPresent(resId -> {
                    Glide.with(activity).load(resId).into(headerImageRightSide);
                    headerImageRightSide.setVisibility(View.VISIBLE);
                });
        Optional.of(element.getLeftSideIconDrawable())
                .filter(value -> value != 0)
                .map(resId -> {
                    headerImageLeftSide.setVisibility(View.VISIBLE);
                    return Glide.with(activity).load(resId).into(headerImageLeftSide);
                }).orElseGet(() -> Glide.with(activity).load(R.drawable.arrow_down).into(headerImageLeftSide));
        headerTextItem.setText(element.getTitle());
        headerView.setOnClickListener(v -> {
            if (!isResize[0]) {
                isResize[0] = true;
                Glide.with(activity).load(R.drawable.arrow_up).into(headerImageLeftSide);
                TransitionManager.beginDelayedTransition(pageView.findViewById(R.id.page_provider), new ChangeBounds());
                for (int i = 1; i < view.getChildCount(); i++) {
                    view.getChildAt(i).setVisibility(View.VISIBLE);
                }
            } else {
                isResize[0] = false;
                Glide.with(activity).load(R.drawable.arrow_down).into(headerImageLeftSide);
                TransitionManager.beginDelayedTransition(pageView.findViewById(R.id.page_provider));
                for (int i = 1; i < view.getChildCount(); i++) {
                    View childAt = view.getChildAt(i);
                    TransitionManager.beginDelayedTransition((ViewGroup) childAt);
                    childAt.setVisibility(View.GONE);
                }
            }
        });

        TypedValue outValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.selectableItemBackground, outValue, true);
        headerView.setBackgroundResource(outValue.resourceId);
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        addSeparator();
        return this;
    }

    public NordanSimplyPage addSingleBottomItem(BaseElement basePageElement) {
        if (Objects.isNull(basePageElement)) {
            return this;
        }
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

    public NordanSimplyPage addCustomItem(View view) {
        return addCustomItem(view, 0);
    }

    public NordanSimplyPage addCustomItem(View customView, int gravity) {
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.custom_item_view, null);
        LinearLayout customLinear = view.findViewById(R.id.custom_linear);
        if (gravity != 0) {
            view.setGravity(gravity);
        }
        customLinear.addView(customView);
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        return this;
    }

    public NordanSimplyPage addMinimalItem(BaseElement basePageElement) {
        if (Objects.isNull(basePageElement)) {
            return this;
        }
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

    public NordanSimplyPage addSwitchItem(SwitchElement switchElement) {
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.switch_item_view, null);
        MaterialTextView textItem = view.findViewById(R.id.item_text);
        MaterialTextView subTextItem = view.findViewById(R.id.item_subtext);
        SwitchMaterial switchItem = (SwitchMaterial) view.findViewById(R.id.switch_item);
        switchItem.setOnCheckedChangeListener(switchElement.getOnCheckedChangeListener());
        textItem.setText(switchElement.getTitle());
        Optional.ofNullable(switchElement.getSubText())
                .filter(subText -> !subText.isEmpty())
                .ifPresent(subText -> {
                    subTextItem.setText(subText);
                    subTextItem.setVisibility(View.VISIBLE);
                });
        ((LinearLayout) pageView.findViewById(R.id.page_provider)).addView(view);
        addSeparator();
        return this;
    }

    public NordanSimplyPage addItem(PageElement pageElement) {
        if (Objects.isNull(pageElement)) {
            return this;
        }
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

    public NordanSimplyPage addAccountItem(AccountElement accountElement) {
        if (Objects.isNull(accountElement)) {
            return this;
        }
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
        return addImageItem(resourceId, 200, 200);
    }

    public NordanSimplyPage addImageItem(int resourceId, int width, int height) {
        int pxHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, activity.getResources().getDisplayMetrics());
        int pxWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, activity.getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(pxWidth, pxHeight);
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.image_item_view, null);
        ImageView image = view.findViewById(R.id.image);
        Glide.with(activity).load(resourceId).into(image);
        image.setLayoutParams(imageParams);
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
