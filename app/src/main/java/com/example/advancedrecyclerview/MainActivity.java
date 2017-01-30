package com.example.advancedrecyclerview;

import android.content.ComponentName;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private Adapter adapter;
    private RecyclerView recyclerView;
    private List<ListItem> data;

    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;

    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new Adapter();

        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int randomNum = new Random().nextInt(100);
            if (i % 5 == 0) {
                data.add(new BasicItem());
                data.add(new BasicItem());
            } else if (randomNum < 50) {
                data.add(new BasicItem());
                data.add(new LittleItem());
                data.add(new LittleItem());
            } else {
                data.add(new LittleItem());
                data.add(new BasicItem());
                data.add(new LittleItem());
            }
        }

        adapter.setData(data);


        final int spanCount = 4;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                ListItem listItem = adapter.getItem(position);
                return listItem.getSpanSize(spanCount, position);
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscription = adapter.getPositionClicks().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                openInTabs();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
        subscription = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient= customTabsClient;
                mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCustomTabsClient= null;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void openInTabs() {
        Uri uri = Uri.parse("http://librarius.hu/2017/01/24/nezd-meg-az-oscar-jelolt-magyar-rovidfilmet/");

        // create an intent builder

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder(mCustomTabsSession);

        // Begin customizing
        // set toolbar colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        // set start and exit animations
        intentBuilder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = intentBuilder
                .setShowTitle(true)
                .addDefaultShareMenuItem()
                .setCloseButtonIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_arrow_back_white_24dp))
                .build();

        // launch the url
        mCustomTabsIntent.launchUrl(this, uri);
    }
}
