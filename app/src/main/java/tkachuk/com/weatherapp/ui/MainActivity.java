package tkachuk.com.weatherapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tkachuk.com.weatherapp.R;
import tkachuk.com.weatherapp.model.Day;
import tkachuk.com.weatherapp.model.DayList;
import tkachuk.com.weatherapp.ui.fragment.NextDaysFragment;
import tkachuk.com.weatherapp.ui.fragment.TodayFragment;
import tkachuk.com.weatherapp.ui.fragment.TomorrowFragment;

public class MainActivity extends AppCompatActivity implements IMainView,
        NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    //refresh
    SwipeRefreshLayout swipeRefreshLayout;

    //navigationDrawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    private MainPresenter mainPresenter;

    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListeners();
        initPresenter();

        //DrawerToggle
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        //viewPager
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        textView.setFocusable(false);

        loadData();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        editText = (EditText) findViewById(R.id.search_ed);
        textView = (TextView) findViewById(R.id.cityName_tv);
    }

    private void initListeners() {
        mNavigationView.setNavigationItemSelectedListener(this);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    loadData();
                    hideKeyboard();
                    textView.setText(editText.getText());
                    handled = true;
                }
                return handled;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initPresenter() {
        mainPresenter = new MainPresenter(getApplicationContext(), this);
    }

    private String getCity(){
        if(!TextUtils.isEmpty(editText.getText()))
            return editText.getText().toString();
        else{
            return textView.getText().toString();
        }
    }

    private void loadData(){
        //today
        if(viewPager.getCurrentItem() == 0){
            mainPresenter.getWeather(getCity());
        }
        //other days
        else
            mainPresenter.getForecast (getCity());
    }

    private void hideKeyboard(){
        InputMethodManager inputManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void incorrectCity(){
        Toast.makeText(this, "Error, incorrect city", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setToday(Day day) {
        Toast.makeText(this,"Set Today, city - "+day.getClouds(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNextDays(DayList dayList) {
        Toast.makeText(this,"set NextDays - "+dayList.getItems().get(1).getSys().getSunset(), Toast.LENGTH_SHORT).show();
    }

    private void showProgressLoaderWithBackground (boolean visibility, String text) {
        if (text == null)
            text = "";
        ((TextView) findViewById(R.id.progress_bar_text)).setText(text);

        if(visibility){
            findViewById(R.id.container_progress_bar).setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else
        {
            findViewById(R.id.container_progress_bar).setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void showProgress() {
        showProgressLoaderWithBackground(true, "load data");
    }

    @Override
    public void hideProgress() {
        showProgressLoaderWithBackground(false, "load data");
    }

    @Override
    public void hideRefreshing() {
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNotInternetConnection() {Toast.makeText(this, "Not internet connection", Toast.LENGTH_SHORT).show();}

    //NavigationDrawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerLayout.closeDrawers();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Add click to Home button
        if(mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    //View
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TodayFragment(), "Today");
        adapter.addFragment(new TomorrowFragment(), "Tomorrow");
        adapter.addFragment(new NextDaysFragment(), "NextDays");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
