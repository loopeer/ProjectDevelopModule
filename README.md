# ProjectDevelopModule
## Catalog
* [CompatInset](#compatinset)
* [BottomDialog](#bottomdialog)
* [AddressPicker](#addresspicker)
* [DevelopUtil](#developutil)

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
        , R.string.util_click_span_helper_content)
        .setHighlightColor(0)
        .setColor(R.color.colorAccent)
        .setSpanUnderLine(false)
        .setSpanBold(false)
        .addClickSpanParam(R.string.util_click_span_helper_01, widget -> {
            Toast.makeText(widget.getContext(), "Click 01", Toast.LENGTH_SHORT).show();
        })
        .addClickSpanParam(R.string.util_click_span_helper_02, widget -> {
            Toast.makeText(widget.getContext(), "Click 02", Toast.LENGTH_SHORT).show();
        })
        .addClickSpanParam(R.string.util_click_span_helper_03, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(widget.getContext(), "Click 03", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
                ds.setColor(ContextCompat.getColor(BaseDevelopUtilsActivity.this, R.color.colorPrimary));
            }
        }).build();
```


