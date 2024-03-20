package com.example.diman.tabsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class tab5 extends Fragment {

    private  final int CM_DELETE_ID = 3;
    private  final int CM_EDIT_ID = 4;

    final String LOG_TAG = "myLogs";
    private ListView lvMgroupslist;
    public EditText etNameNewMgroup;
    public Button btnAddMgroup;

    private Cursor mgroups;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.tab5,container,false);



        lvMgroupslist = (ListView) view.findViewById(R.id.lvMgroupsList);
        etNameNewMgroup=(EditText) view.findViewById(R.id.etNameNewMgroup);
        btnAddMgroup=(Button) view.findViewById(R.id.btnAddMgroup);
        ReloadMgroups();

        registerForContextMenu(lvMgroupslist);

        etNameNewMgroup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etNameNewMgroup.setText("");
            }
        });


        btnAddMgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etNameNewMgroup.getText().toString().equals("")) {
                    Log.d(LOG_TAG, "pustaya stroka^^" + etNameNewMgroup.getText().toString().equals(""));
                    Log.d(LOG_TAG, "stroka[" + etNameNewMgroup.getText().toString() + "]");
                    Toast toast = Toast.makeText(getContext(), "Введите название мышечной группы!", Toast.LENGTH_SHORT);
                    toast.show();


                } else {
                    Log.d(LOG_TAG, "nepustaya stroka^^" + etNameNewMgroup.getText().toString().equals(""));
                    Log.d(LOG_TAG, "stroka[" + etNameNewMgroup.getText().toString() + "]");

                    Toast toast = Toast.makeText(getContext(), "Мышечная группа "
                            + etNameNewMgroup.getText().toString()
                            + " добавлена", Toast.LENGTH_SHORT);
                    toast.show();
                    MainActivity.db.addMgroup(etNameNewMgroup.getText().toString());

                    ReloadMgroups();
                }

            }


        });

        return  view;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);


        mgroups.moveToPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);
        Log.d(LOG_TAG, "...!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d(LOG_TAG, "--- MENU from Mgroups:" + mgroups.getString(mgroups.getColumnIndex("name")) + " --position-" + ((AdapterView.AdapterContextMenuInfo) menuInfo).position + "  id:" + ((AdapterView.AdapterContextMenuInfo) menuInfo).id);

        menu.setHeaderTitle(mgroups.getString(mgroups.getColumnIndex("name")));
        menu.add(0, CM_DELETE_ID, 0, "Удалить мышечную группу");
        menu.add(0, CM_EDIT_ID, 0, "Изменить мышечную группу");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "...xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx!");
      AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
      //  ContextMenu.ContextMenuInfo info=(ContextMenu.ContextMenuInfo) item.getMenuInfo();

        mgroups.moveToPosition(info.position);

        if (item.getItemId() == 3) {

            MainActivity.db.delMgroup(info.id);

             Log.d(LOG_TAG, "...Удалена мышечная группа " +mgroups.getString(mgroups.getColumnIndex("name")));
            Toast toast = Toast.makeText(getContext(), "Удалена мышечная группа: \""+mgroups.getString(mgroups.getColumnIndex("name"))+"\"", Toast.LENGTH_SHORT); //" + mgroups.getString(mgroups.getColumnIndex("name"))+"\"", Toast.LENGTH_SHORT);
           toast.show();
           ReloadMgroups();

            return true;
        } else

        if (item.getItemId() == 4) {
            // MainActivity.db.addParam("ffffffff","fdfdf");
        //    Log.d(LOG_TAG, "--- EDIT EXERCISE --id-" + idItem + " GROUP?" + isGroup);
            //   MainActivity.db.delParam(acmi.id);
         //     Log.d(LOG_TAG, "...изменено упражнение: " + etNameExercise.getText().toString() + "  id:" + info.id + "   ");
            ;
        //    ReloadLists();

            //  exlvExerciseslist.expandGroup((int) info.packedPosition, false); //разворот групп
         //   Log.d(LOG_TAG, "...изменено упражнение: " + etNameExercise.getText().toString() + "  id:" + info.id + "   ");*//*
            return true;
        }


        return super.onContextItemSelected(item);
        }







    public void ReloadMgroups()
    {
         mgroups= MainActivity.db.getMgroupsList();

        ListAdapter lstAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_1,
                mgroups,
                new String[] {"name"},
                new int[] {android.R.id.text1});
        lvMgroupslist.setAdapter(lstAdapter);
    }

}
