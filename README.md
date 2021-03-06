# ProjectDevelopModule
## Catalog
* [CompatInset](#compatinset)
* [BottomDialog](#bottomdialog)
* [AddressPicker](#addresspicker)
* [DevelopUtil](#developutil)
    * [ClickSpanHelper](#clickspanhelper)
    * [DoubleClickHelper](#doubleclickhelper)
    * [BankNoSpaceWatcher](#banknospacewatcher)
    * [CaptchaHelper](#captchahelper)
* [ImageSwitcher](#imageswitcher)
* [AppUpdate](#appupdate)

### CompatInset
===
One fasion in the library to solve make status bar transparent.Idea from CoordinatorLayout and CollapsingToolbarLayout.
#### Installation
```groovy
dependencies {
    compile 'com.loopeer.library:compatinset:0.0.5-beta36'
}
```
||Api16|Api19|Api23|
|---|---|---|---|
|Full transparency status bar|![](/screenshot/inset_image/inset_tool_16.png)|![](/screenshot/inset_image/inset_tool_19.png)|![Full transparency status bar](/screenshot/inset_image/inset_tool_23.png)|
|Full transparency status bar|![](/screenshot/inset_image/inset_image_16.png)|![](/screenshot/inset_image/inset_image_19.png)|![](/screenshot/inset_image/inset_image_23.png)|
|singleholder With translucent status bar in native system|same to top|same to top|![](/screenshot/inset_image/single_inset_image_23.png)|
|singleholder With translucent status bar in native system|same to top|same to top|![](/screenshot/inset_image/single_inset_tool_23.png)|  

#### Usages
##### Singleholder With translucent status bar in native system
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <com.loopeer.compatinset.SingleInsetHolderView
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
If you want support edittext softinput pop to push layout top. You should add **android:windowSoftInputMode="adjustResize"**. Add for the EditText parent such as Relative,Frame,Coord Layout **app:softCompat="true"**. For the LinearLayout,add that in the linearlayout's parent **ScrollView**
```xml
<activity
    android:name=".CompatSingleInsetToolbarActivity"
    android:theme="@style/AppTheme.SingleNoActionBar"
    android:windowSoftInputMode="adjustResize">
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value=".MainActivity"/>
</activity>
```
##### Full transparency status bar
The parents wrap **InsetHolderView** must be support the inset just as InsetLinearLayout in library.You can new create use the InsetHelper.
```xml
<com.loopeer.compatinset.InsetLinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <com.loopeer.compatinset.InsetHolderView
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
</com.loopeer.compatinset.InsetLinearLayout>
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
    compile 'com.loopeer.library:bottomdialog:0.0.5-beta36'
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
    compile 'com.loopeer.library:addresspicker:0.0.5-beta36'
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
    compile 'com.loopeer.library:developutil:0.0.5-beta36'
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
new BankNoSpaceWatcher().apply(edittext);
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
Builder method:
* setSendText(has default)
* setTimeRemainText(string res with one integer param,has default)
* setTimeFuture(seconds default 60)

When go out of activity(onDestroy) or fragment(onDestroyView) you should cancel the CountDownTimer.
```java
mCaptchaHelper.cancel();
```

### ImageSwitcher
===

```groovy
dependencies {
    compile 'com.loopeer.library:imageswitcher:0.0.5-beta36'
}
```
<img src="/screenshot/image_switcher/image_switcher.gif" width = "310"/>

#### Usages
```java
NavigatorImage.startImageSwitchActivity(this,  Arrays.asList(sImages), 3, R.mipmap.ic_image_default);
```

### AppUpdate
===
Apk Update helper with apk version check and download apk service.
```groovy
dependencies {
    compile 'com.loopeer.library:appupdate:0.0.5-beta36'
}
```

#### Usages
```java
AppUpdate.apply(this
    , title
    , description
    , url
    , getString(R.string.app_name)
    , R.drawable.ic_launcher);
```


### SpringViewGroup
===

<img src="/screenshot/spring_viewgroup/more_item_anim.gif" width = "310"/>
<img src="/screenshot/spring_viewgroup/less_item_anim.gif" width = "310"/>

#### Usages
```SpringRecyclerView```   ```SpringScrollView```

### Databindpack and FormItemView
===
#### Databindpack
Databindpack provide a fast way to build large form,which is inspired by the two-way binding.    
**Feature**:    
1.since the ui and data are bind in two-way. there is no need to use setXXX and getXXX Method of TextView.And also the TextWatcher is no need to set.     
2.a easy way to check whether the data is modified.    
#### FormItemView
FormItemView contains two easy custom view —— FormEditItem / FormTextItem to help us build form more efficiently. both view provide databinding adpater.    
for more detail,please see the FormValidatorActivity and FormItemViewActivity.
