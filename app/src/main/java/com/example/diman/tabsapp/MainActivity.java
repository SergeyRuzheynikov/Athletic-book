package com.example.diman.tabsapp;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static public MyDatabase db;
    // public static final String TAG = "MainActivity";
    public static final String LOG_TAG = "MainActivity";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MyDatabase(this);

        setContentView(R.layout.activity_main);
       // AppBarLayout appBarLayout=(AppBarLayout) findViewById(R.id.appbar);
        //appBarLayout.setLayoutMode(AppBarLayout.LAYOUT_MODE_OPTICAL_BOUNDS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
       // tabLayout.setBackgroundColor(Color.RED);// цвет панели вкладок
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //   tabLayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        tabLayout.setupWithViewPager(mViewPager);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        static  public  View rv1,rv2,rv3,rv4,rv5,rv6;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rv1 = inflater.inflate(R.layout.tab1,container,false);
             rv2 = inflater.inflate(R.layout.tab2,container,false);
            rv3 = inflater.inflate(R.layout.tab3,container,false);
            rv4 = inflater.inflate(R.layout.tab4,container,false);
            rv5 = inflater.inflate(R.layout.tab5,container,false);
            rv6 = inflater.inflate(R.layout.tab6,container,false);

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            LinearLayout LLpage=(LinearLayout) rootView.findViewById(R.id.llPage);
            Bundle args = getArguments();

            switch (args.getInt(ARG_SECTION_NUMBER)) {
                case 1: {
                    LLpage.addView(rv1);
                }  ;
                case 2: {
                    LLpage.addView(rv2);
                }  ;
                case 3: {
                    LLpage.addView(rv3);
                }  ;
                case 4: {
                    LLpage.addView(rv4);
                }  ;
                case 5: {
                    LLpage.addView(rv5);
                }  ;
                case 6: {
                    LLpage.addView(rv6);
                }  ;

            }



//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }



        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           // super.destroyItem(container, position, object);

        }

        @Override
        public Fragment getItem(int i) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (i) {
                case 0:
                    tab1 t1 = new tab1();
                    return t1;
               case 1:
                    tab2 t2 = new tab2();
                    return t2;
                case 2:
                    tab3 t3 = new tab3();
                    return t3;
                case 3:
                    tab4 t4 = new tab4();
                    return t4;
                case 4:
                    tab5 t5 = new tab5();
                    return t5;
                case 5:
                    tab6 t6 = new tab6();


                    return t6;

                 }
                 return null;

           // return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }




        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Тренировка";
                case 1:
                    return "Дневник";
                case 2:
                    return "Комплексы";
                case 3:
                    return "Упражнения";
                case 4:
                    return "Мышечные группы";
                case 5:
                    return "Параметры";
            }
            return null;
        }
    }
}
