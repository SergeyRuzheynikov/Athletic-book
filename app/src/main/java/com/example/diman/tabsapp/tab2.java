package com.example.diman.tabsapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class tab2 extends Fragment {
    final String LOG_TAG = "myLogs";
    ListView lvSets;
    LinearLayout llSetsParamsHeader, llSetsListOfExs, llExsAndSetsOfTraining;
    Spinner spTrainings /*spExsOfTraining*/;
    Cursor Sets, ExsOfTrainings, Trainings, paramsOfEx, Results, resultsOfTrainingAndEx;
    SimpleCursorAdapter lstAdapter;
    LayoutInflater infl;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        infl = inflater;

        //   lvSets=(ListView) view.findViewById(R.id.lvSets);
        spTrainings = (Spinner) view.findViewById(R.id.spTrainings);
       /* spExsOfTraining = (Spinner) view.findViewById(R.id.spExsOfTraining);*/

        llSetsParamsHeader = (LinearLayout) view.findViewById(R.id.llSetsParamsHeader);
        llSetsListOfExs = (LinearLayout) view.findViewById(R.id.llSetsListOfExs);

        llExsAndSetsOfTraining = (LinearLayout) view.findViewById(R.id.llExsAndSetsOfTraining);


        spTrainings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                // selected_mgroup_id = selectedId;
                //   Log.d(LOG_TAG, "SELECTED ITEM id: " + selectedId + "  child pos:" + selectedItemPosition + "   ");
                ReloadListExsOfTraining(selectedId);


                ReloadTableTraining(selectedId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*spExsOfTraining.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {


                int i;

                //Формирование заголовков столбцов
                paramsOfEx = MainActivity.db.getParamsOfEx(selectedId);
                llSetsParamsHeader.removeAllViews();
                for (i = 0; i < paramsOfEx.getCount(); i++) {

                    TextView tvParam = new TextView(getActivity());
                    tvParam.setId(i);
                    paramsOfEx.moveToPosition(i);
                    String s = paramsOfEx.getString(paramsOfEx.getColumnIndex("param")) + "\r\n (" + paramsOfEx.getString(paramsOfEx.getColumnIndex("type_par")) + ")";
                    tvParam.setText(s);
                    tvParam.setTextSize(20);
                    int widthrow = llSetsParamsHeader.getWidth() / paramsOfEx.getCount();
                    tvParam.setWidth(widthrow);
                    tvParam.setGravity(Gravity.CENTER_HORIZONTAL);
                    // llsetsxxx.addView(tvParam);
                    llSetsParamsHeader.addView(tvParam);
                }
                //...формирование заголовков столбцов

                int k;
                Sets = MainActivity.db.getSetsOfTraining(spTrainings.getSelectedItemId(), spExsOfTraining.getSelectedItemId());

                Log.d(LOG_TAG, "---TRAINING-id-" + spTrainings.getSelectedItemId() + "  EXS ID:" + spExsOfTraining.getSelectedItemId());
                Log.d(LOG_TAG, "---COUNT SETS:-" + Sets.getCount());
                llSetsListOfExs.removeAllViews();
                for (k = 0; k < Sets.getCount(); k++) {

                    LinearLayout llxSet = new LinearLayout(getActivity());
                    llxSet.setOrientation(LinearLayout.HORIZONTAL);
                    llxSet.setId(i + 2500);
                    LinearLayout.LayoutParams Lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    llxSet.setLayoutParams(Lparams);
                    //llxSet.setBackgroundColor(getResources().getColor(R.color.translucent_orange));

                    TextView tvSet = new TextView(getActivity());
                    tvSet.setId(k + 1500);
                    Sets.moveToPosition(k);
                    String st = (k + 1) + " " + Sets.getString(Sets.getColumnIndex("_id"));
                    tvSet.setText(st);
                    tvSet.setTextSize(25);
                    tvSet.setWidth(100);

                    llxSet.addView(tvSet);

                    int p;
                    for (p = 0; p < paramsOfEx.getCount(); p++) {
                        paramsOfEx.moveToPosition(p);
                        Results = MainActivity.db.getResultsOfSetAndParam(Long.valueOf(Sets.getString(Sets.getColumnIndex("_id"))), Long.valueOf(paramsOfEx.getString(paramsOfEx.getColumnIndex("_id"))));
                        //  Results = MainActivity.db.getResultsOfSetAndParam(4,0);
                        Results.moveToPosition(0);

                        TextView tvRes = new TextView(getActivity());
                        tvRes.setId(p + 7500);

                        Log.d(LOG_TAG, "---RESULT:-" + Results.getCount());//Results.getString(Results.getColumnIndex("Results._id")));
                        if (Results.getCount() > 0) {
                            String sres = "     " + Results.getString(Results.getColumnIndex("Results.res"));
                            tvRes.setText(sres);
                        }

                        tvRes.setTextSize(25);
                        Log.d(LOG_TAG, "---width:-" + (llxSet.getWidth()) / paramsOfEx.getCount());
                        tvRes.setWidth((llSetsListOfExs.getWidth()) / paramsOfEx.getCount());
                        tvRes.setGravity(Gravity.CENTER_HORIZONTAL);

                        llxSet.addView(tvRes);

                    }

                    //   int widthrow= llSetsParamsHeader.getWidth()/paramsOfEx.getCount();
                    // tvSet.setWidth(300);
                    //  tvSet.setGravity(Gravity.CENTER_HORIZONTAL);
                    // llsetsxxx.addView(tvParam);
                    llSetsListOfExs.addView(llxSet);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        //ReloadListSets();
        ReloadListTrainings();


        return view;
    }

    public void ReloadListTrainings() {
        Trainings = MainActivity.db.getTrainings();

        lstAdapter = new SimpleCursorAdapter(getActivity(),
                //  lstAdapter = new CatAdapter(getActivity(),
                R.layout.simple_list_item_3,
                Trainings,
                new String[]{"_id", "date_tr", "start_time_training"},
                new int[]{R.id.text1, R.id.text2, R.id.text3}, 0);


        // lvSets.setAdapter(lstAdapter);
        spTrainings.setAdapter(lstAdapter);

    }


    public void ReloadTableTraining(long id_training) {
        llExsAndSetsOfTraining.removeAllViews();


        int aa = 0;
        int ii = 0;
        int jj = 0;
        int exsCount = ExsOfTrainings.getCount();

        for (aa = 0; aa < exsCount; aa++) {

            ExsOfTrainings.moveToPosition(aa);

            final View exs_item = infl.inflate(R.layout.row_parent, null);
            LinearLayout ll_parent = (LinearLayout) exs_item.findViewById(R.id.llParent);

            LinearLayout ll_exs_item = (LinearLayout) exs_item.findViewById(R.id.llHeadTable);
            TextView tv_table_exs = (TextView) ll_exs_item.findViewById(R.id.nameEx);

            tv_table_exs.setText((aa+1)+") "+ExsOfTrainings.getString(ExsOfTrainings.getColumnIndex("name")));

            ReloadListSetsOfTraining(id_training, Long.valueOf(ExsOfTrainings.getString(ExsOfTrainings.getColumnIndex("_id"))));
//            View row_exs_textview = infl.inflate(R.layout.table_sets_textview, null);
//            TextView tv_table_exs = (TextView) table_exs_textview.findViewById(R.id.tv_table_sets);


            int setsCount = Sets.getCount();
            paramsOfEx = MainActivity.db.getParamsOfEx(Long.valueOf(ExsOfTrainings.getString(ExsOfTrainings.getColumnIndex("_id"))));
            int paramsCount = paramsOfEx.getCount();
            int[] rowwidth = new int[20];
            Log.d(LOG_TAG, "КОЛИЧЕСТВО ПОДХОДОВ " + setsCount + "   ПАРАМЕТРОВ " + paramsCount);


            for (ii = -1; ii < setsCount; ii++) {

                final View set_item = infl.inflate(R.layout.set_item, null);
                LinearLayout ll_set_item = (LinearLayout) set_item.findViewById(R.id.ll_set_item);

                final int k = ii;
                ll_set_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(LOG_TAG, "КЛИКНУТ № " + k);
                    }
                });

                Log.d(LOG_TAG, "ii=" + ii + " jj=" + jj);

                if (ii == -1) {
                    set_item.setBackgroundColor(Color.BLUE);
                    ll_set_item.setBackgroundColor(Color.GRAY);
                    for (jj = -1; jj < paramsCount; jj++) {

                      final  View table_sets_textview = infl.inflate(R.layout.table_sets_textview, null);
                        TextView tv_table_sets = (TextView) table_sets_textview.findViewById(R.id.tv_table_sets);
                        // tv_table_sets.setBackgroundColor(Color.RED);

                        if (jj == -1) {
                            tv_table_sets.setText("№");
                            tv_table_sets.setTextColor(Color.BLACK);
                            // tv_table_sets.setWidth(30);

                        } else {
                            paramsOfEx.moveToPosition(jj);


                            Log.d(LOG_TAG, "ii=" + ii + " jj=" + jj + " param:" + paramsOfEx.getString(paramsOfEx.getColumnIndex("param")));

                            tv_table_sets.setText("" + paramsOfEx.getString(paramsOfEx.getColumnIndex("param")) + " (" + paramsOfEx.getString(paramsOfEx.getColumnIndex("type_par")) + ")");
                            tv_table_sets.setTextColor(Color.BLACK);
                            //  tv_table_sets.setWidth(160);
                        }

                        ll_set_item.addView(table_sets_textview);

                        // tv_table_sets.setWidth(30);
                        rowwidth[jj + 1] = spTrainings.getWidth();
                        Log.d(LOG_TAG, "rowwidth[jj+1]=" + rowwidth[jj + 1]);

                        table_sets_textview.setId((ii + 2) * 100000 + (jj + 2) * 1000);
                        tv_table_sets.setId((ii + 2) * 10000 + (jj + 2) * 1000);
                    }
                } else {
                    Sets.moveToPosition(ii);
                    long set_id = Long.valueOf(Sets.getString(Sets.getColumnIndex("_id")));

                    for (jj = -1; jj < paramsCount; jj++) {

                        View table_sets_textview = infl.inflate(R.layout.table_sets_textview, null);
                        TextView tv_table_sets = (TextView) table_sets_textview.findViewById(R.id.tv_table_sets);


                        if (jj == -1) {
                            tv_table_sets.setText("" + (ii + 1));
                            tv_table_sets.setTextColor(Color.BLUE);
                            //  tv_table_sets.setWidth(30);


                        } else

                        {
                            paramsOfEx.moveToPosition(jj);
                            long param_id = Long.valueOf(paramsOfEx.getString(paramsOfEx.getColumnIndex("_id")));


                            resultsOfTrainingAndEx = MainActivity.db.getResultsOfSetAndParam(set_id, param_id);
                            resultsOfTrainingAndEx.moveToFirst();

                       /*     Log.d(LOG_TAG, "******************************************************");
                            Log.d(LOG_TAG, "!!!  set_id=" + set_id + " param_id=" + param_id + "SetsOfTraining.id" + Long.valueOf(Sets.getString(Sets.getColumnIndex("_id"))) + "  getcount" + Sets.getCount());
                            Log.d(LOG_TAG, " РЕЗУЛЬТАТ " + resultsOfTrainingAndEx.getString(resultsOfTrainingAndEx.getColumnIndex("res")));

                            Log.d(LOG_TAG, "!!!  ii=" + ii + " jj=" + jj + " param:" + paramsOfEx.getString(paramsOfEx.getColumnIndex("param")));
                            Log.d(LOG_TAG, "******************************************************");*/

                            tv_table_sets.setText(resultsOfTrainingAndEx.getString(resultsOfTrainingAndEx.getColumnIndex("res")));
                            // tv_table_sets.setWidth(15);
                            //  tv_table_sets.setWidth(160);

                        }
                        //  tv_table_sets.setWidth(rowwidth[jj+1]);
                        //  tv_table_sets.setWidth(30);
                        ll_set_item.addView(table_sets_textview);

                        table_sets_textview.setId((ii + 2) * 100000 + (jj + 2) * 1000);
                        tv_table_sets.setId((ii + 2) * 10000 + (jj + 2) * 1000);

                    }
                }
                ll_parent.addView(set_item);
                set_item.setId((ii + 2) * 1000000 + (jj + 2) * 1000);
                ll_set_item.setId((ii + 2) * 100000000);

            }
                //ll_parent.addView(set_item);
                 // ll_parent.addView(set_);
                llExsAndSetsOfTraining.addView(exs_item);

                //  llExsAndSetsOfTraining.addView(set_item);

//                set_item.setId((ii + 2) * 1000000 + (jj + 2) * 1000);
//                ll_set_item.setId((ii + 2) * 100000000);
            }




/*            ii = -1;
            {
                View set_item = (View) llExsAndSetsOfTraining.findViewById((ii + 2) * 1000000 + (jj + 2) * 1000);
                LinearLayout ll_set_item = (LinearLayout) set_item.findViewById((ii + 2) * 100000000);

                for (jj = -1; jj < paramsCount; jj++) {
                    View table_sets_textview = ll_set_item.findViewById((ii + 2) * 100000 + (jj + 2) * 1000);//infl.inflate(R.layout.table_sets_textview, null);
                    TextView ttt = (TextView) table_sets_textview.findViewById((ii + 2) * 10000 + (jj + 2) * 1000);

//                LinearLayout.LayoutParams pars= (LinearLayout.LayoutParams) ttt.getLayoutParams();

                    rowwidth[jj + 1] = ttt.length() * 10;
                    ttt.setWidth(rowwidth[jj + 1]);
                    ttt.setWidth(((spExsOfTraining.getWidth()) / 4) - 5);
                    Log.d(LOG_TAG, "*************" + rowwidth[jj + 1] + "*****" + spExsOfTraining.getWidth() + "**************" + set_item.getWidth() + "**********************");


                }
            }


            for (ii = 0; ii < setsCount; ii++) {
                View set_item = getActivity().findViewById((ii + 2) * 1000000 + (jj + 2) * 1000);
                LinearLayout ll_set_item = (LinearLayout) set_item.findViewById((ii + 2) * 100000000);

                for (jj = -1; jj < paramsCount; jj++) {
                    View table_sets_textview = ll_set_item.findViewById((ii + 2) * 100000 + (jj + 2) * 1000);
                    TextView ttt = (TextView) table_sets_textview.findViewById((ii + 2) * 10000 + (jj + 2) * 1000);
                    ttt.setWidth(rowwidth[jj + 1]);
                    ttt.setWidth(((spExsOfTraining.getWidth()) / 4) - 5);


                }
            }*/

        }




    public void ReloadListExsOfTraining(long id_training) {
        ExsOfTrainings = MainActivity.db.getExsOfTrainings(id_training);

        lstAdapter = new SimpleCursorAdapter(getActivity(),

                R.layout.simple_list_item_3,
                ExsOfTrainings,
                new String[]{"Exercises._id", "Exercises.name"},
                new int[]{R.id.text1, R.id.text2});


       /* spExsOfTraining.setAdapter(lstAdapter);*/

    }


    public void ReloadListSetsOfTraining(long id_training, long id_exs) {
        Sets = MainActivity.db.getSetsOfTraining(id_training, id_exs);

        lstAdapter = new SimpleCursorAdapter(getActivity(),

                android.R.layout.simple_list_item_1,
                Sets,
                new String[]{"num"},
                new int[]{android.R.id.text1});


        //  lvSets.setAdapter(lstAdapter);

    }

}
