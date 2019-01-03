package com.orange.mall.app.modules.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.orange.mall.app.Application;
import com.orange.mall.app.R;
import com.orange.mall.app.adapters.ViewPagerFragmentAdapter;
import com.orange.mall.app.constants.Api;
import com.orange.mall.app.constants.Storage;
import com.orange.mall.app.utils.ActivityUtils;
import com.orange.mall.app.widgets.SwipeViewPager;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
  implements CellsShowroomFragment.OnCellsShowroomFragmentInteractionListener,
MallFragment.OnMallFragmentInteractionListener,
NewsFragment.OnNewsFragmentInteractionListener,
UserCenterFragment.OnUserCenterFragmentInteractionListener {
  private final String TAG = MainActivity.class.getSimpleName();

  private List<Fragment> mFragmentList = null;
  private ViewPagerFragmentAdapter mViewPagerFragmentAdapter = null;
  private FragmentManager mFragmentManager = null;
  private ViewPager mViewPager = null;
  private BottomNavigationView mNavigation = null;

  private Fragment mCurentFragment = null;

  private boolean mHasInited = false;

  // 点击了底部的tab
  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
    = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      MainActivity.this.setViewPagerCurrItem(item);
      return true;
    }
  };
  private AppUpdater mAppUpdater;
  private String mApkFilePath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
    mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    this.disableShiftMode(mNavigation);

    this.init();

  }

  private void setViewPagerCurrItem(MenuItem menuItem) {
    for (int i = 0; i < mNavigation.getMenu().size(); i++) {
      if (menuItem == mNavigation.getMenu().getItem(i)) {
        mViewPager.setCurrentItem(i);
        break;
      }
    }
  }

  private void init() {
    if (mHasInited) {
      return;
    }
    // 计算Navigation 高度
    saveNavigationHeight();

    this.initFragments();
    this.initViewPager();

    this.checkAppUpdate();

    // ActivityUtils.requestPermissions(this);



    mHasInited = true;
  }

  public static int px2dip(Context context, float pxValue) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }


  private void saveNavigationHeight () {

    int navigationBarHeight = 0;
    int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
    if (resourceId > 0) {
      navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
    }
    int dp = px2dip(this, navigationBarHeight);
    Log.i(TAG, String.format("NavigationHeight2: %d", dp));
    Hawk.put(Storage.KEY_NAVIGATION_HEIGHT, dp);
  }


  private void initViewPager() {
    mViewPager = (SwipeViewPager) findViewById(R.id.view_pager);
    ((SwipeViewPager) mViewPager).setSwipeable(false);
    mViewPager.setAdapter(mViewPagerFragmentAdapter);
    mViewPager.setCurrentItem(0);
    mViewPager.setOffscreenPageLimit(4);
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i(TAG, String.format("onPageScrolled position: %d; positionOffset: %f; positionOffsetPixels: %d;",
          position, positionOffset, positionOffsetPixels));
      }

      @Override
      public void onPageSelected(int position) {
        Log.i(TAG, String.format("onPageSelected position %d;", position));
        MenuItem mi = mNavigation.getMenu().getItem(position);
        mNavigation.setSelectedItemId(mi.getItemId());
        mCurentFragment = mViewPagerFragmentAdapter.getItem(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {
        Log.i(TAG, String.format("onPageScrollStateChanged state: %d;", state));
      }
    });
  }


  private void initFragments() {

    mFragmentList = new ArrayList<>();
    if (!Hawk.isBuilt()) {
      Hawk.init(Application.getAppContext()).build();
    }

    String token = Hawk.get(Storage.KEY_TOKEN);

    mFragmentList.add(NewsFragment.newInstance(token, ""));
    mFragmentList.add(CellsShowroomFragment.newInstance(token, ""));
    mFragmentList.add(MallFragment.newInstance(token, ""));
    mFragmentList.add(UserCenterFragment.newInstance(token, ""));

    mFragmentManager = getSupportFragmentManager();
    mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(mFragmentManager, mFragmentList);

  }

  /**
   * 检查App是否可以更新
   */
  private void checkAppUpdate() {
    String url = Api.apiDomain(Api.UPDATE_PATH);
    mAppUpdater = new AppUpdater(this)
      .setUpdateFrom(UpdateFrom.JSON)
      .setUpdateJSON(url)
      .setTitleOnUpdateAvailable("卡西慕商城可以更新啦！")
      .setContentOnUpdateAvailable("")
      .setButtonUpdate("现在更新")
      .setButtonUpdateClickListener(new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          // 执行更新
          Log.i(TAG, "点击了更新");
          String url = Api.apiDomain(Api.UPDATE_PATH);

          new AppUpdaterUtils(MainActivity.this).setUpdateFrom(UpdateFrom.JSON).setUpdateJSON(url)
            .withListener(new AppUpdaterUtils.UpdateListener() {
              @Override
              public void onSuccess(Update update, Boolean aBoolean) {
                Log.d("Latest Version", update.getLatestVersion());
                Log.d("Latest Version Code", String.format("%d", update.getLatestVersionCode()));
                Log.d("Release notes", update.getReleaseNotes());
                Log.d("URL", update.getUrlToDownload().toString());
                Log.d("get release notes: ", update.getReleaseNotes());
                // 下载apk
                String downloadUrl = update.getUrlToDownload().toString();
                ActivityUtils.openBrowser(downloadUrl, MainActivity.this);

              }

              @Override
              public void onFailed(AppUpdaterError appUpdaterError) {

              }
            }).start();
          // NetUtils.download();
        }
      })
      .setButtonDoNotShowAgain(null)
      .setContentOnUpdateNotAvailable(null)
      .setIcon(R.mipmap.ic_launcher_round)
      .setCancelable(false)
      .showAppUpdated(false);

    mAppUpdater.start();
  }


  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @SuppressLint("RestrictedApi")
  public static void disableShiftMode(BottomNavigationView navigationView) {

    BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
    try {
      // 利用反射，改变 item 中 mShiftingMode 的值 ,从而改变 BottomNavigationView 默认的效果
      Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
      shiftingMode.setAccessible(true);
      shiftingMode.setBoolean(menuView, false);
      shiftingMode.setAccessible(false);

      for (int i = 0; i < menuView.getChildCount(); i++) {
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
        itemView.setShiftingMode(false);
        itemView.setChecked(itemView.getItemData().isChecked());
      }

    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void OnCellsShowroomFragmentInteractionListener(Uri uri) {

  }

  @Override
  public void OnMallFragmentInteractionListener(Uri uri) {

  }

  @Override
  public void OnNewsFragmentInteractionListener(Uri uri) {

  }

  @Override
  public void OnUserCenterFragmentInteractionListener(Uri uri) {

  }

  @Override
  protected void onResume() {
    super.onResume();
    saveNavigationHeight();
  }
}
