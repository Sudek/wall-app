package ikko_ikki.wall_app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String analyticsAddress = "http://thewallmagazine.ru/category/analytics/";
    private static final String cultureAddress = "http://thewallmagazine.ru/category/culture/";
    private static final String interviewAddress = "http://thewallmagazine.ru/category/interview/";
    private static final String blogsAddress = "http://thewallmagazine.ru/category/blogs/";
    private static final String eventsAddress = "http://thewallmagazine.ru/category/events/";
    private ArrayList<String> titleAnalyticsList = new ArrayList<>();
    private ArrayList<String> linksAnalyticsList = new ArrayList<>();
    private ArrayList<String> titleCultureList = new ArrayList<>();
    private ArrayList<String> linksCultureList = new ArrayList<>();
    private ArrayList<String> titleInterviewList = new ArrayList<>();
    private ArrayList<String> linksInterviewList = new ArrayList<>();
    private ArrayList<String> titleBlogsList = new ArrayList<>();
    private ArrayList<String> linksBlogsList = new ArrayList<>();
    private ArrayList<String> titleEventList = new ArrayList<>();
    private ArrayList<String> linksEventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Parser().execute();
        initToolbar();
        initViewPagerAndTabs();
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(PartThreeFragment.createInstance(titleAnalyticsList, linksAnalyticsList), getString(R.string.tab_1));
        pagerAdapter.addFragment(PartThreeFragment.createInstance(titleCultureList, linksCultureList), getString(R.string.tab_2));
        pagerAdapter.addFragment(PartThreeFragment.createInstance(titleInterviewList, linksInterviewList), getString(R.string.tab_3));
        pagerAdapter.addFragment(PartThreeFragment.createInstance(titleBlogsList, linksBlogsList), getString(R.string.tab_4));
        pagerAdapter.addFragment(PartThreeFragment.createInstance(titleEventList, linksEventList), getString(R.string.tab_5));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    class Parser extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Document analyticsDoc = null;
            Document cultureDoc = null;
            Document interviewDoc = null;
            Document blogsDoc = null;
            Document eventDoc = null;
            try {
                analyticsDoc = Jsoup.connect(analyticsAddress).get();
                cultureDoc = Jsoup.connect(cultureAddress).get();
                interviewDoc = Jsoup.connect(interviewAddress).get();
                blogsDoc = Jsoup.connect(blogsAddress).get();
                eventDoc = Jsoup.connect(eventsAddress).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (analyticsDoc != null & cultureDoc != null & interviewDoc != null & blogsDoc != null & eventDoc != null) {

                titleAnalyticsList.clear();
                titleCultureList.clear();
                titleInterviewList.clear();
                titleBlogsList.clear();
                titleEventList.clear();

                linksAnalyticsList.clear();
                linksCultureList.clear();
                linksInterviewList.clear();
                linksBlogsList.clear();
                titleEventList.clear();

                Elements analyticsElements = analyticsDoc.getElementsByTag("h2");
                Elements cultureElements = cultureDoc.getElementsByTag("h2");
                Elements interviewElements = interviewDoc.getElementsByTag("h2");
                Elements blogsElements = blogsDoc.getElementsByTag("h2");
                Elements eventElements = eventDoc.getElementsByTag("h2");

                Elements links = analyticsElements.select("a[href]");
                Elements cultureLinks = cultureElements.select("a[href]");
                Elements interviewLinks = interviewElements.select("a[href]");
                Elements blogsLinks = blogsElements.select("a[href]");
                Elements eventLinks = eventElements.select("a[href]");

                for (Element link : links) {
                    Log.d("Analytics title - ", link.text());
                    Log.d("Analytics link - ", link.attr("href"));
                    titleAnalyticsList.add(link.text());
                    linksAnalyticsList.add(link.attr("href"));
                }
                for (Element link : cultureLinks) {
                    Log.d("Culture title - ", link.text());
                    Log.d("Culture link - ", link.attr("href"));
                    titleCultureList.add(link.text());
                    linksCultureList.add(link.attr("href"));
                }
                for (Element link : interviewLinks) {
                    Log.d("Interview title - ", link.text());
                    Log.d("Interview link - ", link.attr("href"));
                    titleInterviewList.add(link.text());
                    linksInterviewList.add(link.attr("href"));
                }
                for (Element link : blogsLinks) {
                    Log.d("Blogs title - ", link.text());
                    Log.d("Blogs link - ", link.attr("href"));
                    titleBlogsList.add(link.text());
                    linksBlogsList.add(link.attr("href"));
                }
                for (Element link : eventLinks) {
                    Log.d("Event title - ", link.text());
                    Log.d("Event link - ", link.attr("href"));
                    titleEventList.add(link.text());
                    linksEventList.add(link.attr("href"));
                }
            }
            return null;
        }
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

    }

}

