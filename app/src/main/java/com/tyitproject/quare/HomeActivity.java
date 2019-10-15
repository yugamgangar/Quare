package com.tyitproject.quare;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener{

    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    ViewPager viewPager;
    int[] homeLeftlist_imgid = {R.drawable.chickenpox, R.drawable.acne, R.drawable.diabetes, R.drawable.constipation};
    int[] homeRightlist_imgid = {R.drawable.fever, R.drawable.cough, R.drawable.dehydration, R.drawable.ringworm};
    private String[] homeLeftlist_imageName = {"Chickenpox","Acne","Diabetes","Constipation"};
    private String[] homeRightlist_imageName = {"Fever","Cough","Dehydration","Ringworm"};
    private String disease_name_op, symptoms_op="", medicines_op="", precautions_op="";

    EditText editText;
    private ArrayList<HomeImageList> homelist;
    private ArrayList<DiseaseSearchData> searchOP_list;
    ListView listview;
    ImageView imageview;
    private BottomNavigationView mBtmView;
    private int mMenuId, NUM_PAGES = 4, aboutImgId, currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_info);
        mBtmView = findViewById(R.id.bottom_navigation_view);
        mBtmView.setSelectedItemId(R.id.navigation_home);
        mBtmView.setOnNavigationItemSelectedListener(this);

        //========================================= HOME ACTIVITY =============================================
        listview = findViewById(R.id.list);

//        com.tyitproject.quare.HomeImageList list1 = new com.tyitproject.quare.HomeImageList(homeLeftlist_imageName[0], homeLeftlist_imgid[0], homeRightlist_imageName[0], homeRightlist_imgid[0]);
//        com.tyitproject.quare.HomeImageList list2 = new com.tyitproject.quare.HomeImageList(homeLeftlist_imageName[1], homeLeftlist_imgid[1], homeRightlist_imageName[1], homeRightlist_imgid[1]);
//        com.tyitproject.quare.HomeImageList list3 = new com.tyitproject.quare.HomeImageList(homeLeftlist_imageName[2], homeLeftlist_imgid[2], homeRightlist_imageName[2], homeRightlist_imgid[2]);
//        com.tyitproject.quare.HomeImageList list4 = new com.tyitproject.quare.HomeImageList(homeLeftlist_imageName[3], homeLeftlist_imgid[3], homeRightlist_imageName[3], homeRightlist_imgid[3]);
//        homelist = new ArrayList<>();
//        homelist.add(list1);
//        homelist.add(list2);
//        homelist.add(list3);
//        homelist.add(list4);
//        com.tyitproject.quare.HomeListAdapter homeListView = new com.tyitproject.quare.HomeListAdapter(com.tyitproject.quare.HomeActivity.this, R.layout.home_listview, homelist);
//        listview.setAdapter(homeListView);

//        PagerAdapter pager_adapter = new com.tyitproject.quare.BannerAdapter(com.tyitproject.quare.HomeActivity.this,imageId);
//        viewPager = (ViewPager) findViewById(R.id.banner_pager);
//        viewPager.setAdapter(pager_adapter);
//
//        /*After setting the adapter use the timer */
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES-1) {
//                    currentPage = 0;
//                }
//                viewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//        timer = new Timer(); // This will create a new Thread
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, DELAY_MS, PERIOD_MS);

//======================================= SEARCH ==============================================

        editText = findViewById(R.id.search_bar);
     //   arrayList = new ArrayList<>(Arrays.asList(items));

        editText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                        String search_text = editText.getText().toString();
                        searchClick(search_text);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

    public void onBackPressed() {
        HomeActivity.this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clicked = homelist.get(position).toString();
        Log.d("ListView","Clicked outside onCreate position:"+clicked);
        //startActivity(new Intent(this, clicked));
    }


    private void searchClick(String search_input) {
        String data = "";
        String mLine;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("disease_db.csv")));
            //do reading, usually loop until end of file reading
            while ((mLine = reader.readLine()) != null) {

                if(search_input.equalsIgnoreCase(getSubjectName(mLine, ",", ","))) {
                    data = mLine;
                    Log.d("LOG:~",""+data);
                    break;
                }
                else if(!search_input.equalsIgnoreCase(getSubjectName(mLine, ",", ","))) {
                    data = "Data not found!";
                    Log.d("LOG:~",""+data);
                }
            }
        } catch (IOException e) {
            Log.d("FILE READING","EXCEPTION - "+e);//log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d("READER CLOSE","EXCEPTION - "+e);//log the exception
                }
            }
        }

        Log.d("DATA","DATA when disease present -"+data);

        StringBuilder sb1 = getStringBuilder(data);

        Log.d("LOG","diesese name- "+disease_name_op);


        switch(disease_name_op){
            case "Fever":   aboutImgId = R.drawable.about_fever; break;
            case "Amoebiasis":   aboutImgId = R.drawable.about_amoebiasis; break;
            case "Chicken Pox":   aboutImgId = R.drawable.about_chickenpox; break;
            case "Constipation":   aboutImgId = R.drawable.about_constipation; break;
            case "Convulsion":   aboutImgId = R.drawable.about_convulsion; break;
            case "Cough":   aboutImgId = R.drawable.about_cough; break;
            case "Dehydration":   aboutImgId = R.drawable.about_dehydration; break;
            case "Diabetes":   aboutImgId = R.drawable.about_diabetes; break;
            case "Gingivitis":   aboutImgId = R.drawable.about_gingivitis; break;
            case "Hyperpyrexia":   aboutImgId = R.drawable.about_hyperpyrexia; break;
            case "Hypoglycemia":   aboutImgId = R.drawable.about_hypoglycemia; break;
            case "Periodontitis":   aboutImgId = R.drawable.about_periodontitis; break;
            case "Ringworm":   aboutImgId = R.drawable.about_ringworm; break;
            case "Stomatitis":   aboutImgId = R.drawable.about_stomatitis; break;
            case "Typhoid":   aboutImgId = R.drawable.about_typhoid; break;
            default: aboutImgId = R.drawable.imagenotfound; break;
        }

        DiseaseSearchData searchOP = new DiseaseSearchData(aboutImgId,disease_name_op,symptoms_op,medicines_op,precautions_op);
        searchOP_list = new ArrayList<>();
        searchOP_list.add(searchOP);

        DiseaseSearchListAdapter diseaseSearchListAdapter = new DiseaseSearchListAdapter(HomeActivity.this, R.layout.disease_listview, searchOP_list);
        listview.setAdapter(diseaseSearchListAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String listView_string= String.valueOf((listview.getItemAtPosition(position)));
                Log.d("LoG::","listview item position: "+listView_string);
                inti_dia(listView_string);
            }});
    }

    private String getSubjectName(String input, String startChar, String endChar) {
        try {
            int start = input.indexOf(startChar);
            if (start != -1) {
                int end = input.indexOf(endChar, start + startChar.length());
                if (end != -1) {
                    return input.substring(start + startChar.length(), end);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input; // return null; || return "" ;
    }

    private void inti_dia(String listView_string) {
        final Dialog dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.pop_up_layout);
        dialog3.setCancelable(true);
        dialog3.show();
        TextView tx= dialog3.findViewById(R.id.text_dis);
        tx.setText(listView_string, TextView.BufferType.SPANNABLE);
        ImageView cancel= dialog3.findViewById(R.id.close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.dismiss();
            }
        });
    }

    private StringBuilder getStringBuilder(String dis_data) {
        StringBuilder sb = new StringBuilder();
        sb.append("");

        int data_length = dis_data.length();
        dis_data = dis_data.substring(4,data_length);
        String ddata[] = dis_data.split(",");
        Log.d("ddata value", ": "+ddata);
        for (int n = 0; n < ddata.length; n++) {
            String temp_[] = ddata[n].split(";");
            Log.d("length", "= "+temp_.length);
            for (int p = 0; p < temp_.length; p++) {
                    if (p == temp_.length - 1)
                        sb.append("" + temp_[p] + " ");
                    else
                        sb.append("" + temp_[p] + "\n ");

                if(n==0)
                {   disease_name_op = temp_[p];
                    break; }
                if(n==1) {
                    if (p == temp_.length - 1)
                        symptoms_op = symptoms_op + temp_[p] + " ";
                    else
                        symptoms_op = symptoms_op + temp_[p] + "\n";
                }
                if(n==2) {
                    if (p == temp_.length - 1)
                        medicines_op = medicines_op + temp_[p] + " ";
                    else
                        medicines_op = medicines_op + temp_[p] + "\n";
                }
                if(n==3) {
                    if (p == temp_.length - 1)
                        precautions_op = precautions_op + temp_[p] + " ";
                    else
                        precautions_op = precautions_op + temp_[p] + ".\n";
                }
            }
        }
        return sb;
    }

    //======================================= NAVIGATION ===========================================

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        mMenuId = item.getItemId();
        for (int i = 0; i < mBtmView.getMenu().size(); i++) {
            MenuItem menuItem = mBtmView.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);

            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    HomeActivity.this.finish();
                    break;
                }
                case R.id.navigation_healthdata: {
                    Intent intent = new Intent(HomeActivity.this, HealthDataActivity.class);
                    startActivity(intent);
                    HomeActivity.this.finish();
                    break;
                }
                case R.id.navigation_account: {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    HomeActivity.this.finish();
                    break;
                }
            }
            return true;
        }
        return false;
    }

}
