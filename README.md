# ProjectDevelopModule
## Catalog
* [CompatInset](#compatinset)
* [BottomDialog](#bottomdialog)
* [AddressPicker](#addresspicker)
* [DevelopUtil](#developutil)
    * [ClickSpanHelper](#clickspanhelper)
    * [DoubleClickHelper](#doubleclickhelper)
    * [BankNoSpaceWatcher](#banknospacewatcher)
* [ImageSwitcher](#imageswitcher)

### CompatInset
===
One fasion in the library to solve make status bar transparent.Idea from CoordinatorLayout and CollapsingToolbarLayout.

||Api16|Api19|Api23|
|---|---|---|---|
|Full transparency status bar|![](/screenshot/inset_image/inset_tool_16.png)|![](/screenshot/inset_image/inset_tool_19.png)|![Full transparency status bar](/screenshot/inset_image/inset_tool_23.png)|
|singleholder With translucent status bar in native system|![](/screenshot/inset_image/inset_image_16.png)|![](/screenshot/inset_image/inset_image_19.png)|![](/screenshot/inset_image/inset_image_23.png)|
|singleholder With translucent status bar in native system|same to top|same to top|![](/screenshot/inset_image/single_inset_image_23.png)|
|singleholder With translucent status bar in native system|same to top|same to top|![](/screenshot/inset_image/single_inset_tool_23.png)|  

#### Usages
##### Singleholder With translucent status bar in native system
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <loopeer.com.compatinset.SingleInsetHolderView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:insetStatusBarColor="@android:color/transparent"/>

    <ImageView
        android:layout_width="24dp"
        android:layout_height="24dp"
        android:layout_alignParentRight="true"
        android:layout_below="@+id/inset_holder"
        android:layout_gravity="right"
        android:layout_margin="10dp"
        android:src="@android:drawable/ic_dialog_email"/>
</LinearLayout>
```
If you want support edittext softinput pop to push layout top. You should add **android:windowSoftInputMode="adjustPan"**
```xml
<activity
    android:name=".CompatSingleInsetToolbarActivity"
    android:theme="@style/AppTheme.SingleNoActionBar"
    android:windowSoftInputMode="adjustPan">
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value=".MainActivity"/>
</activity>
```
##### Full transparency status bar
The parents wrap **InsetHolderView** must be support the inset just as InsetLinearLayout in library.You can new create use the InsetHelper. Don't forget add fitsSystemWindows.
```xml
<loopeer.com.compatinset.InsetLinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:fitsSystemWindows="@bool/fits_system_windows"
    android:orientation="vertical">

    <loopeer.com.compatinset.InsetHolderView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:insetStatusBarColor="@android:color/transparent"
        android:fitsSystemWindows="@bool/fits_system_windows"/>

    <ImageView
        android:layout_width="24dp"
        android:layout_height="24dp"
        android:layout_alignParentRight="true"
        android:layout_below="@+id/inset_holder"
        android:layout_gravity="right"
        android:layout_margin="10dp"
        android:src="@android:drawable/ic_dialog_email"/>
</loopeer.com.compatinset.InsetLinearLayout>
```
Then set up **AppTheme.NoActionBar** theme
values-v19:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="AppTheme.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
        <item name="android:windowTranslucentStatus">true</item>
    </style>
</resources>
```
values-v21:
```xml
<resources>
    <style name="AppTheme.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
        <item name="android:windowDrawsSystemBarBackgrounds">true</item>
        <item name="android:statusBarColor">@android:color/transparent</item>
    </style>
</resources>

```   

### BottomDialog
===
#### Screenshot
![](/screenshot/bottom_dialog/bottom_dialog_date_time.gif)
#### Installation
```groovy
dependencies {
    compile 'com.loopeer.library:bottomdialog:0.0.2'
}
```
#### Usages
```java
new BottomDateTimeDialog.Builder(this)
        .updateDateTime(Calendar.getInstance().getTimeInMillis())
        .setDateSelectListener(
                new BottomDateTimeDialog.OnDateSelectListener() {
                    @Override
                    public void onDateSelected(Calendar calendar) {
                        updateDateTimeText(calendar);
                    }
                })
        .setDateTimeMode(mDateTimeMode)
        .show();
```

### AddressPicker
===
#### Screenshot
![](/screenshot/address_picker/address_picker_dialog.gif)
#### Installation
```groovy
dependencies {
    compile 'com.loopeer.library:addresspicker:0.0.2'
}
```
#### Usages
```java
new AddressPickerDialog.Builder(this)
            .setTitle(title)
            //bottom or alert dialog
            .setDialogType(mType)
            //show province | city | district
            .setAddressMode(mMode)
            //if you want to show the last address you have picked,just record the params and set them
            .setProvinceIndex(provinceIndex)
            .setCityIndex(cityIndex)
            .setDistrictIndex(districtIndex)
            .setOnPickerListener(new OnAddressPickListener() {
                @Override public void onConfirm(AddressPickerView pickerView) {
                    mTextAddress.setText(getString(
                        R.string.address_format,pickerView.getProvince(), pickerView.getCity(),
                        pickerView.getDistrict()));
                    //record params
                    provinceIndex = pickerView.getProvinceIndex();
                    cityIndex = pickerView.getCityIndex();
                    districtIndex = pickerView.getDistrictIndex();
                }


                @Override public void onCancel() {

                }
            })
            .show();
```

### DevelopUtil
===
#### Installation
```groovy
dependencies {
    compile 'com.loopeer.library:developutil:x.x.x'
}
```
#### Usages
##### ClickSpanHelper
![](/screenshot/develop_util/util_click_span_helper.png)

```java
new ClickSpanHelper.Builder(mBinding.textTestClickSpan
        , R.string.contentString)
        .setHighlightColor(0)
        .setColor(R.color.colorAccent)
        .setSpanUnderLine(false)
        .setSpanBold(false)
        .addClickSpanParam(R.string.first_click_string, widget -> method1())
        .addClickSpanParam(R.string.second_click_string, widget -> method2())
        .addClickSpanParam(R.string.third_click_string, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                method3();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
                ds.setColor(ContextCompat.getColor(BaseDevelopUtilsActivity.this, R.color.colorPrimary));
            }
        }).build();
```

##### DoubleClickHelper
Add Double click event for view with single click.
```java
new DoubleClickHelper.Builder(targetView)
        .setDoubleClickListener(e -> doubleClickEvent())
        .setSingleClickListener(event -> singleClickEvent())
        .build();
```

##### BankNoSpaceWatcher
One TextWatcher to format the bank card no to add space auto.  
![](/screenshot/develop_util/util_bank_no_space_watcher.gif)

```java
new BankNoSpaceWatcher().applyTo(edittext);
```

##### CaptchaHelper
Wraping CountDownTimer and TextView to let add captcha easy.

![](/screenshot/develop_util/util_captcha_helper.gif)

```java
mCaptchaHelper = new CaptchaHelper.Builder(mBinding.textPhoneCaptcha)
        .setTimeFuture(10)
        .build();

mBinding.textPhoneCaptcha.setOnClickListener(v -> {
    mCaptchaHelper.start();// assume the this is one request callback.Then to start count down
});
```
When go out of activity(onDestroy) or fragment(onDestroyView) you should cancel the CountDownTimer.
```java
mCaptchaHelper.cancel();
```

### ImageSwitcher
===
<img src="/screenshot/image_switcher/image_switcher.gif" width = "310"/>

#### Usages
```java
NavigatorImage.startImageSwitchActivity(this,  Arrays.asList(sImages), 3, R.mipmap.ic_image_default);
```
