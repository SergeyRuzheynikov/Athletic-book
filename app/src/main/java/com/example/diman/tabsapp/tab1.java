package com.example.diman.tabsapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.Layout;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Wrapper;


public class tab1 extends Fragment {
    final String LOG_TAG = "myLogs";
    Chronometer cr;
    Spinner spComplex, spExsOfComplex;
    ListView lvExsOfComplex;
    TextView tvv;
    LinearLayout llSetParam, llSetsOfTrainingAndExs;
    Button btnNewTraining, btnEndTraining;
    ListAdapter lstAdapter, lst2adapter;
    Cursor paramsOfEx;
    Cursor ExsOfComplex;
    Cursor SetsOfTraining;
    Cursor resultsOfTrainingAndEx;
    LayoutInflater infl;
    long idNowTraining;

    Time today = new Time(Time.getCurrentTimezone());
    String date_tr, start_time, end_time;

    int k = 1;
    long id_exs = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        infl = inflater;
        spComplex = (Spinner) view.findViewById(R.id.spComplex);
        // lvExsOfComplex=(ListView) view.findViewById(R.id.lvExsOfComplex);
        spExsOfComplex = (Spinner) view.findViewById(R.id.spExsOfComplex);


        btnNewTraining = (Button) view.findViewById(R.id.btnNewTraining);
        btnEndTraining = (Button) view.findViewById(R.id.btnEndTraining);


        tvv = (TextView) view.findViewById(R.id.tvExNow);
        llSetParam = (LinearLayout) view.findViewById(R.id.llSetParam);





        llSetsOfTrainingAndExs = (LinearLayout) view.findViewById(R.id.llSetsOfTrainingAndExs);
/*
       int ii=0,jj=0;
        for (ii=0;ii<30;ii++)
        {   View set_item = inflater.inflate(R.layout.set_item, null);
            LinearLayout ll_set_item=(LinearLayout) set_item.findViewById(R.id.ll_set_item);

            for (jj=0;jj<50;jj++)
            {View table_sets_textview = inflater.inflate(R.layout.table_sets_textview, null);
              TextView tv_table_sets=(TextView) table_sets_textview.findViewById(R.id.tv_table_sets);


                if ((ii==0)&&(jj==0))
                {tv_table_sets.setText("№" + ii);
                    tv_table_sets.setTextColor(Color.BLUE);

                }
                else
                   if ((ii==0)&&(jj>0))
                {   tv_table_sets.setText("ves" + ii);
                    tv_table_sets.setTextColor(Color.RED);
                }
                else

                if ((ii>0)&&(jj==0))
                {
                    tv_table_sets.setText("" + ii);
                    tv_table_sets.setTextColor(Color.GREEN);
                }
                else
                {tv_table_sets.setText(""+jj);
                    //tv_table_sets.setWidth(30);
                }

            ll_set_item.addView(table_sets_textview);
            }

            llSetsOfTrainingAndExs.addView(set_item);
        }
*/

        btnNewTraining.setEnabled(true);
        btnEndTraining.setEnabled(false);



        spComplex.setEnabled(false);
        spExsOfComplex.setEnabled(false);
        cr = (Chronometer) view.findViewById(R.id.chronometer);

        btnNewTraining.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  //  cr.setBase(SystemClock.elapsedRealtime());
                                                  cr.setBase(SystemClock.elapsedRealtime());
                                                  cr.start();
                                                  today.setToNow();
                                                  date_tr = today.format("%d.%m.%Y");
                                                  start_time = today.format("%k:%M:%S");
                                                  MainActivity.db.addNewTraining(date_tr, start_time);

                                                  Cursor Trainings = MainActivity.db.getTrainings();
                                                  idNowTraining = Trainings.getCount();
                                                 // btnEndTraining.setText("" + (Trainings.getCount()));

                                                  btnNewTraining.setEnabled(false);
                                                  btnEndTraining.setEnabled(true);
                                                  spComplex.setEnabled(true);
                                                  spExsOfComplex.setEnabled(true);

                                                  if ((ExsOfComplex.getCount() >= 1)) {
                                                      ReloadListSetsOfTraining(idNowTraining, spExsOfComplex.getSelectedItemId());

                                                  } else {

                                                  }

                                              }
                                          }
        );


        btnEndTraining.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  cr.stop();
                                                  today.setToNow();
                                                  end_time = today.format("%k:%M:%S");
                                                  MainActivity.db.endTraining(date_tr, start_time, end_time);



                                                 /* Calendar c = Calendar.getInstance();

                                                //  int seconds = c.get(Calendar.SECOND);
                                                  int dfw=c.get(Calendar.DAY_OF_WEEK);
                                                  int day=c.get(Calendar.DAY_OF_MONTH);
                                                  int month=c.get(Calendar.MONTH);
                                                  int year=c.get(Calendar.YEAR);
                                                  int minutes= c.get(Calendar.MINUTE);
                                                  int hours= c.get(Calendar.HOUR_OF_DAY);
                                                  btnEndTraining.setText(dfw+"  "+ day+"."+month+"."+year+"   "+hours+":"+minutes);
                                                 */

                                                  //btnEndTraining.setText(date_tr + "  " + end_time);


                                                  btnNewTraining.setEnabled(true);
                                                  btnEndTraining.setEnabled(false);
                                                  spComplex.setEnabled(false);
                                                  spExsOfComplex.setEnabled(false);

                                              }
                                          }
        );

        spComplex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                // selected_mgroup_id = selectedId;
                Log.d(LOG_TAG, "SELECTED ITEM id: " + selectedId + "  child pos:" + selectedItemPosition + "   ");
                ReloadListExs(selectedId);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spExsOfComplex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   tvv.setText(""+id);
                ExsOfComplex.moveToPosition(position);
                tvv.setText("" + ExsOfComplex.getString(ExsOfComplex.getColumnIndex("name")));


                ReloadParamsofEx(id);
                id_exs = id;

                Log.d(LOG_TAG, " ITEM id: " + id + "  child pos:" + position + "   ");

/********************************************************************************************//////
                llSetParam.removeAllViews();
                final int n;
                int i;
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                final View addedView = layoutInflater.inflate(R.layout.row, null);
              //  TextView textOut = (TextView) addedView.findViewById(R.id.textout);

                //  Lparams.setMargins(10, 10, 0, 30);
             //   textOut.setText("ПОДХОД №" + k);

                final LinearLayout llsetsxxx = (LinearLayout) addedView.findViewById(R.id.llsets);


                n = paramsOfEx.getCount();

                for (i = 0; i < n; i++) {
                    /***************************/

                    View row_param = layoutInflater.inflate(R.layout.row_param, null);
                    TextView tv_name_param = (TextView) row_param.findViewById(R.id.tv_name_param);
                    EditText et_value_param = (EditText) row_param.findViewById(R.id.et_value_param);

                    NumberPicker np = (NumberPicker) row_param.findViewById(R.id.numberPicker1);
                    np.setOrientation(LinearLayout.HORIZONTAL);
                    np.setMaxValue(500);
                    np.setMinValue(1);

                    llsetsxxx.addView(row_param);


                    tv_name_param.setId(i);
                    paramsOfEx.moveToPosition(i);
                    String s = paramsOfEx.getString(paramsOfEx.getColumnIndex("param")) + " (" + paramsOfEx.getString(paramsOfEx.getColumnIndex("type_par")) + ")";
                    tv_name_param.setText(s);

                    et_value_param.setId(i + 100);
                    et_value_param.setInputType(InputType.TYPE_CLASS_NUMBER);
                    et_value_param.setBackgroundColor(getResources().getColor(R.color.edit));
                    /***************************/

                  /*  LinearLayout stroka = new LinearLayout(getActivity());

                    stroka.setOrientation(LinearLayout.HORIZONTAL);
                    stroka.setId(i + 500);
                    LinearLayout.LayoutParams Lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    if (i == 0) {

                        Lparams.setMargins(10, 30, 0, 0);
                    } else {
                        Lparams.setMargins(10, 10, 0, 0);
                    }

                    stroka.setBackgroundColor(getResources().getColor(R.color.translucent_orange));
                    stroka.setLayoutParams(Lparams);


                    llsetsxxx.addView(stroka);


                    TextView tvParam = new TextView(getActivity());

                    tvParam.setId(i);
                    paramsOfEx.moveToPosition(i);
                    String s = paramsOfEx.getString(paramsOfEx.getColumnIndex("param")) + " (" + paramsOfEx.getString(paramsOfEx.getColumnIndex("type_par")) + ")";
                    tvParam.setText(s);
                    tvParam.setTextSize(16);
                    tvParam.setWidth(200);
                    // llsetsxxx.addView(tvParam);
                    stroka.addView(tvParam);


                    EditText eParam = new EditText(getActivity());
                    eParam.setId(i + 100);
                    eParam.setInputType(InputType.TYPE_CLASS_NUMBER);
                    eParam.setWidth(250);
                    eParam.setBackgroundColor(getResources().getColor(R.color.edit));
                    // llsetsxxx.addView(eParam);
                    stroka.addView(eParam);
                    //tvv.setText("K:" +k+"   I:"+i+"  id:");// + tvSet.getId());*/
                }

                if (btnNewTraining.isEnabled() == false) {
                    ReloadListSetsOfTraining(idNowTraining, spExsOfComplex.getSelectedItemId());
                }
                llSetParam.addView(addedView);

                Button btnAdd = (Button) addedView.findViewById(R.id.btnRec);

                if (btnEndTraining.isEnabled()) {
                    btnAdd.setEnabled(true);
                } else {
                    btnAdd.setEnabled(false);
                }


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvv.setText("");
                        int i, countSets;
                        boolean isNulls=true;
                        ReloadListSetsOfTraining(idNowTraining, spExsOfComplex.getSelectedItemId());
                        countSets = SetsOfTraining.getCount();

                        for (i = 0; i < n; i++) {
                            EditText eParam = (EditText) addedView.findViewById(i + 100);
                            // tvv.setText(tvv.getText() + " " + eParam.getText() + "  " + spExsOfComplex.getSelectedItemId());
                            paramsOfEx.moveToPosition(eParam.getId() - 100);

                            if (eParam.getText().toString().equals(""))
                            {Log.d(LOG_TAG, "pustaya stroka^^"+eParam.getText().toString().equals(""));
                                Log.d(LOG_TAG, "stroka["+eParam.getText().toString()+"]");
                                Toast toast = Toast.makeText(getContext(),"Введите все значения результатов!", Toast.LENGTH_SHORT);;
                                toast.show();
                                isNulls=true;
                               break;
                            }
                            else
                            {   Log.d(LOG_TAG, "nepustaya stroka^^"+eParam.getText().toString().equals(""));
                                Log.d(LOG_TAG, "stroka[" + eParam.getText().toString() + "]");
                                isNulls=false;
                            }

                        }

                        Log.d(LOG_TAG, "isNulls^^"+isNulls);



                    if (!isNulls)

                    {
                        MainActivity.db.addSet(idNowTraining, countSets + 1, spExsOfComplex.getSelectedItemId());
                        ReloadListSetsOfTraining(idNowTraining, spExsOfComplex.getSelectedItemId());
                        SetsOfTraining.moveToLast();

                        for (i = 0; i < n; i++) {
                                EditText eParam = (EditText) addedView.findViewById(i + 100);
                                // tvv.setText(tvv.getText() + " " + eParam.getText() + "  " + spExsOfComplex.getSelectedItemId());
                                paramsOfEx.moveToPosition(eParam.getId() - 100);


                                    MainActivity.db.addResultOfSet(Long.valueOf(SetsOfTraining.getString(SetsOfTraining.getColumnIndex("_id"))), Long.valueOf(paramsOfEx.getString(paramsOfEx.getColumnIndex("_id"))), Long.parseLong(eParam.getText().toString()));
                                    Log.d(LOG_TAG, "id last set: " + SetsOfTraining.getString(SetsOfTraining.getColumnIndex("_id")) + "  id_param:  " + (paramsOfEx.getString(paramsOfEx.getColumnIndex("_id"))) + "  res: " + Long.parseLong(eParam.getText().toString()) + "   ");
                                    eParam.setText("");

                        }

                        Toast toast = Toast.makeText(getContext(),"Результаты подхода записаны!", Toast.LENGTH_SHORT);
                        toast.show();

                        ReloadSets();

                    }




                        // ((LinearLayout) addedView.getParent()).removeView(addedView);
                        // k--;

                        //ReloadListSetsOfTraining(idNowTraining, spExsOfComplex.getSelectedItemId());




                    }
                });


//                Button buttonRemove = (Button) addedView.findViewById(R.id.remove);
//
//                buttonRemove.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int i;
//                        for (i = 0; i < n; i++) {
//                            EditText eParam = (EditText) addedView.findViewById(i + 100);
//                            eParam.setText("");
//                        }
//                        //k--;
//                    }
//                });

                /*******************************************************************************************/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ReloadComplexesLists();


        return view;
    }

    public void ReloadComplexesLists() {
        Cursor cursor = MainActivity.db.getComplexesList();

        SpinnerAdapter spinAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                cursor,
                new String[]{"name"},
                new int[]{android.R.id.text1});

        spComplex.setAdapter(spinAdapter);



      /*  Cursor mgroups=MainActivity.db.getExsOfComplexes(spComplex.getSelectedItemId());

        ListAdapter lstAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_1,
                mgroups,
                new String[] {"name"},
                new int[] {android.R.id.text1});


        lvExsOfComplex.setAdapter(lstAdapter);


*/
    }

    public void ReloadParamsofEx(long idEx) {
        paramsOfEx = MainActivity.db.getParamsOfEx(idEx);

        lst2adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                paramsOfEx,
                new String[]{"param"},
                new int[]{android.R.id.text1});


        //lvExsOfComplex.setAdapter(lst2adapter);

    }

    public void ReloadListExs(long id_Complex) {
        ExsOfComplex = MainActivity.db.getExsOfComplexes(id_Complex);

        SpinnerAdapter spinAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                ExsOfComplex,
                new String[]{"name"},
                new int[]{android.R.id.text1});


        // lvExsOfComplex.setAdapter(lstAdapter);
        spExsOfComplex.setAdapter(spinAdapter);

        if ((ExsOfComplex.getCount() >= 1) && (btnNewTraining.isEnabled() == false)) {


            ExsOfComplex.moveToPosition(0);
            tvv.setText("" + ExsOfComplex.getString(ExsOfComplex.getColumnIndex("name")));

            id_exs = Integer.parseInt(ExsOfComplex.getString(ExsOfComplex.getColumnIndex("_id")));
            ReloadParamsofEx(id_exs);
        } else {

            llSetParam.removeAllViews();
        }
    }


    public void ReloadSets() {
        ReloadListSetsOfTraining(idNowTraining, spExsOfComplex.getSelectedItemId());
        ReloadParamsofEx(spExsOfComplex.getSelectedItemId());

        llSetsOfTrainingAndExs.removeAllViews();
        int ii = 0;
        int setsCount = SetsOfTraining.getCount();
        int jj = 0;
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

                    View table_sets_textview = infl.inflate(R.layout.table_sets_textview, null);
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
                    rowwidth[jj + 1] = spExsOfComplex.getWidth();
                    Log.d(LOG_TAG, "rowwidth[jj+1]=" + rowwidth[jj + 1]);

                    table_sets_textview.setId((ii + 2) * 100000 + (jj + 2) * 1000);
                    tv_table_sets.setId((ii + 2) * 10000 + (jj + 2) * 1000);
                }
            } else {
                SetsOfTraining.moveToPosition(ii);
                long set_id = Long.valueOf(SetsOfTraining.getString(SetsOfTraining.getColumnIndex("_id")));

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

                        Log.d(LOG_TAG, "******************************************************");
                        Log.d(LOG_TAG, "!!!  set_id=" + set_id + " param_id=" + param_id + "SetsOfTraining.id" + Long.valueOf(SetsOfTraining.getString(SetsOfTraining.getColumnIndex("_id"))) + "  getcount" + SetsOfTraining.getCount());
                        Log.d(LOG_TAG, " РЕЗУЛЬТАТ " + resultsOfTrainingAndEx.getString(resultsOfTrainingAndEx.getColumnIndex("res")));

                        Log.d(LOG_TAG, "!!!  ii=" + ii + " jj=" + jj + " param:" + paramsOfEx.getString(paramsOfEx.getColumnIndex("param")));
                        Log.d(LOG_TAG, "******************************************************");

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

            llSetsOfTrainingAndExs.addView(set_item);

            set_item.setId((ii + 2) * 1000000 + (jj + 2) * 1000);
            ll_set_item.setId((ii + 2) * 100000000);
        }

//        for (ii = -1; ii < setsCount; ii++)


        ii = -1;
        {
            View set_item = (View) llSetsOfTrainingAndExs.findViewById((ii + 2) * 1000000 + (jj + 2) * 1000);
            LinearLayout ll_set_item = (LinearLayout) set_item.findViewById((ii + 2) * 100000000);

            for (jj = -1; jj < paramsCount; jj++) {
                View table_sets_textview = ll_set_item.findViewById((ii + 2) * 100000 + (jj + 2) * 1000);//infl.inflate(R.layout.table_sets_textview, null);
                TextView ttt = (TextView) table_sets_textview.findViewById((ii + 2) * 10000 + (jj + 2) * 1000);

//                LinearLayout.LayoutParams pars= (LinearLayout.LayoutParams) ttt.getLayoutParams();

                rowwidth[jj + 1] = ttt.length() * 10;
                ttt.setWidth(rowwidth[jj + 1]);
                ttt.setWidth(((llSetParam.getWidth()) / 4) - 5);
                Log.d(LOG_TAG, "*************" + rowwidth[jj + 1] + "*****" + llSetParam.getWidth() + "**************" + set_item.getWidth() + "**********************");


            }
        }


        for (ii = 0; ii < setsCount; ii++) {
            View set_item = getActivity().findViewById((ii + 2) * 1000000 + (jj + 2) * 1000);
            LinearLayout ll_set_item = (LinearLayout) set_item.findViewById((ii + 2) * 100000000);

            for (jj = -1; jj < paramsCount; jj++) {
                View table_sets_textview = ll_set_item.findViewById((ii + 2) * 100000 + (jj + 2) * 1000);
                TextView ttt = (TextView) table_sets_textview.findViewById((ii + 2) * 10000 + (jj + 2) * 1000);
                ttt.setWidth(rowwidth[jj + 1]);
                ttt.setWidth(((llSetParam.getWidth()) / 4) - 5);


            }
        }


    }


    public Cursor ReloadListSetsOfTraining(long id_training, long id_exs) {
        SetsOfTraining = MainActivity.db.getSetsOfTraining(id_training, id_exs);

//        lstAdapter = new SimpleCursorAdapter(getActivity(),
//
//                android.R.layout.simple_list_item_1,
//                SetsOfTraining,
//                new String[]{"Sets_tr.num"},
//                new int[]{android.R.id.text1});
//
//
//



        return SetsOfTraining;
    }


}
