# ProjectDevelopModule
## Catalog
* [CompatInset](#CompatInset)
* [BottomDialog](#BottomDialog)

### CompatInset  

One fasion in the library to solve make status bar transparent.Idea from CoordinatorLayout and CollapsingToolbarLayout. 

||Api16|Api19|Api23|
|---|---|---|---|
|Full transparency status bar|![](/screenshot/inset_image/inset_tool_16.png)|![](/screenshot/inset_image/inset_tool_19.png)|![Full transparency status bar](/screenshot/inset_image/inset_tool_23.png)|
|singleholder With translucent status bar in native system|![](/screenshot/inset_image/inset_image_16.png)|![](/screenshot/inset_image/inset_image_19.png)|![](/screenshot/inset_image/inset_image_23.png)|
|singleholder With translucent status bar in native system|same to top|same to top|![](/screenshot/inset_image/single_inset_image_23.png)|
|singleholder With translucent status bar in native system|same to top|same to top|![](/screenshot/inset_image/single_inset_tool_23.png)|  

####Usages
#####Singleholder With translucent status bar in native system
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
#####Full transparency status bar
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

####Screenshot
====
![](/screenshot/bottom_dialog/bottom_dialog_date_time.gif)
####Installation
====
```groovy
dependencies {
    compile 'com.loopeer.library:bottomdialog:0.0.1'
}
```

