package tkachuk.com.weatherapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tkachuk.com.weatherapp.R;
import tkachuk.com.weatherapp.model.Day;
import tkachuk.com.weatherapp.model.DayList;
import tkachuk.com.weatherapp.ui.fragment.NextDaysFragment;
import tkachuk.com.weatherapp.ui.fragment.TodayFragment;
import tkachuk.com.weatherapp.ui.fragment.TomorrowFragment;
import tkachuk.com.weatherapp.util.DateManager;

public class MainActivity extends FragmentActivity implements IMainView {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    //refresh
    SwipeRefreshLayout swipeRefreshLayout;

    private MainPresenter mainPresenter;

    private EditText editText;
    private TextView textView;
    private TextView timeOfLastUpdate;

    private AutoCompleteTextView actv;


    private ArrayList<String> fragmentTags=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListeners();
        initPresenter();

        //viewPager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);

        String[] cities = getResources().getStringArray(R.array.cities);

        List<String> cytiesList = Arrays.asList(cities);

        //autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this,R.layout.auto_complete_textview, cytiesList);
        actv.setAdapter(adapter);

        loadData();
    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        editText = (EditText) findViewById(R.id.search_ed);
        textView = (TextView) findViewById(R.id.cityName_tv);
        timeOfLastUpdate = findViewById(R.id.lastUpdate);

        actv =  findViewById(R.id.search_ed);
    }

    private void initListeners() {

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    loadData();
                    hideKeyboard();
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
        mainPresenter = new MainPresenter(this, this);
    }

    private String getCity(){
        if(!TextUtils.isEmpty(editText.getText())){
            return editText.getText().toString();
        }
        else{
            return mainPresenter.getLoadedCity();
        }
    }

    private void loadData(){
        mainPresenter.loadWeather(getCity());
        editText.clearFocus();
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
    public void setLastUpdateToSP() {
        timeOfLastUpdate.setText("last update: "+DateManager.getCurrentDateTime());
        mainPresenter.setLastUpdate(timeOfLastUpdate.getText().toString());
    }

    public void setLastUpdateToTV(String l) {
        timeOfLastUpdate.setText(l);
    }

    @Override
    public void setToday(Day day) {
        Fragment fragment;
        if((fragment=getFragment(0))!=null) ((TodayFragment)fragment).setData(day);
    }

    @Override
    public void setNextDays(DayList dayList) {
        Fragment fragment;
        if((fragment=getFragment(1))!=null) ((TomorrowFragment)fragment).setData(dayList);
        if((fragment=getFragment(2))!=null) ((NextDaysFragment)fragment).setData(dayList);
    }

    @Override
    public void setCity(String city) {
        textView.setText(city);
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

    @Override
    public void showNotInternetConnectionIsCache() {Toast.makeText(this, "Not internet connection. " +
            "Pull down to load cashe", Toast.LENGTH_LONG).show();}

    private Fragment getFragment(int pos) {
        if(pos<fragmentTags.size()) {
            return getSupportFragmentManager().findFragmentByTag(fragmentTags.get(pos));
        }
        return null;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            if(position<fragmentTags.size()) fragmentTags.set(position,fragment.getTag());
            else fragmentTags.add(fragment.getTag());
            return fragment;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position) {
                case 0:
                    fragment= Fragment.instantiate(MainActivity.this, TodayFragment.class.getName());
                    break;
                case 1:
                    fragment= Fragment.instantiate(MainActivity.this, TomorrowFragment.class.getName());
                    break;
                case 2:
                    fragment= Fragment.instantiate(MainActivity.this, NextDaysFragment.class.getName());
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Today";
                case 1:
                    return "Tomorrow";
                case 2:
                    return "Next days";
            }
            return super.getPageTitle(position);
        }
    }
}
