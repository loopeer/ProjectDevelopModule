package com.loopeer.projectdevelopmodule.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.loopeer.imageswitcher.NavigatorImage;
import com.loopeer.projectdevelopmodule.R;

import java.util.Arrays;

public class ImageSwitcherTestActivity extends AppCompatActivity {

    private static final String[] sImages = new String[]{
            "http://ww2.sinaimg.cn/large/610dc034gw1f9zjk8iaz2j20u011hgsc.jpg",
            "http://ww2.sinaimg.cn/large/610dc034jw1f9vyl2fqi0j20u011habc.jpg",
            "http://ww2.sinaimg.cn/large/610dc034jw1f9us52puzsj20u00u0jtd.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f9tmhxq87lj20u011htae.jpg",
            "http://ww3.sinaimg.cn/large/610dc034gw1f9shm1cajkj20u00jy408.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f9rc3qcfm1j20u011hmyk.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f9nuk0nvrdj20u011haci.jpg"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigatorImage.startImageSwitchActivity(this,  Arrays.asList(sImages), 3, R.mipmap.ic_image_default);
    }
}
