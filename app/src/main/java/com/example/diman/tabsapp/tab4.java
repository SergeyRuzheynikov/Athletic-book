package com.example.diman.tabsapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class tab4 extends Fragment {
    final String LOG_TAG = "myLogs";
    private  final int CM_DELETE_ID = 9;
    private  final int CM_EDIT_ID = 10;



    Cursor mgroups,params;

    public EditText etNameExercise;
    public Spinner spMgroupChoose;
    public ListView lvParamsChoose;

    public Button btnAddEx;

    public ExpandableListView exlvExerciseslist;

    public  long selected_mgroup_id;
    //private ContextMenu.ContextMenuInfo menuInfo;

    public void ReloadLists()
    {  //Cursor
        mgroups = MainActivity.db.getMgroupsList();
        //exs=MainActivity.db.getExs;

        //startManagingCursor(cursor);
        // сопоставление данных и View для групп
        String[] groupFrom = { "Mgroups.name" };
        int[] groupTo = { android.R.id.text1 };
        // сопоставление данных и View для элементов
        String[] childFrom = { "Exercises.name"};
        int[] childTo = { android.R.id.text1 };

        // создаем адаптер и настраиваем список
        SimpleCursorTreeAdapter lstAdapter = new MyAdapter(MainActivity.db.con, mgroups,
                R.layout.expandable_list_item_parent, groupFrom,
                groupTo, R.layout.expandable_list_item_child, childFrom,
                childTo);
        exlvExerciseslist.setAdapter(lstAdapter);


        SpinnerAdapter spinAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                mgroups,
                new String[] {"name"},
                new int[] {android.R.id.text1});

        spMgroupChoose.setAdapter(spinAdapter);



        params=MainActivity.db.getParamsList();

        ListAdapter lst1Adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.item_cheklist, //android.R.layout.simple_list_item_multiple_choice,
                params,
                new String[] {"param"},
                new int[] {android.R.id.text1});
        lvParamsChoose.setAdapter(lst1Adapter);
      lvParamsChoose.setSelection(1);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View view=inflater.inflate(R.layout.tab4,container,false);

      //  lvExerciseslist = (ListView) view.findViewById(R.id.lvExercisesList);
       // lvExerciseslist.setAdapter(MainActivity.db.getExercisesList());
        btnAddEx=(Button) view.findViewById(R.id.btnAddEx);
        etNameExercise = (EditText) view.findViewById(R.id.etNameExercise);
        spMgroupChoose = (Spinner) view.findViewById(R.id.spMgroupChoose);
        lvParamsChoose = (ListView) view.findViewById(R.id.lvParamsChoose);
        exlvExerciseslist = (ExpandableListView) view.findViewById(R.id.exlvExercisesList);





     //
        /****************************************************/
        // создаем обработчик нажатия
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.db.addExercise(etNameExercise.getText().toString(), spMgroupChoose.getSelectedItemId());
                Log.d(LOG_TAG, "Added Exercise name : " + etNameExercise.getText().toString() + "  mgroup_id:" + spMgroupChoose.getSelectedItemId() + "   ");




                // btnAddParam.setText(etNameParam.getText());

                SparseBooleanArray checked=lvParamsChoose.getCheckedItemPositions();

                Toast toast = Toast.makeText(getContext(), "Количество выбранных "+lvParamsChoose.getCheckedItemCount(), Toast.LENGTH_SHORT);
                toast.show();
                lvParamsChoose = (ListView) view.findViewById(R.id.lvParamsChoose);
                ReloadLists();


            }
        };
        // присвоим обработчик кнопке OK (btnOk)
        btnAddEx.setOnClickListener(oclBtnOk);
        /****************************************************/
        ReloadLists();

        registerForContextMenu(exlvExerciseslist);
       // exlvExerciseslist.setAdapter(MainActivity.db.getExercisesList());


        spMgroupChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                selected_mgroup_id = selectedId;
                Log.d(LOG_TAG, "MGROUP SELECTED ITEM id: " + selectedId + "  child pos:" + selectedItemPosition + "   ");

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




/*

        exlvExerciseslist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d(LOG_TAG, "ITEM id: " + id + "  child pos:" + childPosition + "   ");


            return true;
            }
        });
*/




        return  view;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        ExpandableListView.ExpandableListContextMenuInfo info= ( ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        Log.d(LOG_TAG, "------ Тип выделенного элемента (1-дочерний, 0-группа):" + info.id + " GROUP?" + ExpandableListView.getPackedPositionType(info.packedPosition));// + acmi.id + "  pos:" + acmi.position);
      if (ExpandableListView.getPackedPositionType(info.packedPosition)!=0)//если элемент НЕ родительский
      {
          super.onCreateContextMenu(menu, v, menuInfo);
      //    this.menuInfo = menuInfo;


          //String keyword = o.toString();

         // exs.moveToPosition(0);//исправить курсоры
          Cursor exs=MainActivity.db.getExsOfId(info.id);
          exs.moveToFirst();

          menu.setHeaderTitle(exs.getString(exs.getColumnIndex("name")));
          menu.add(0, CM_DELETE_ID, 0, "Удалить упражнение");
          menu.add(0, CM_EDIT_ID, 0, "Изменить упражнение");
          menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
              @Override
              public boolean onMenuItemClick(MenuItem item) {
                  ExpandableListView.ExpandableListContextMenuInfo info= (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

                  Integer idItem = (int) info.id;
                  Boolean isGroup = ExpandableListView.getPackedPositionType(info.packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_GROUP;
                  Log.d(LOG_TAG, "--- Тип выделенного элемента (true-группа, false-дочерний): --id-" + idItem + " GROUP?" + isGroup);// + acmi.id + "  pos:" + acmi.position);

                      Cursor exs= MainActivity.db.getExsOfId(info.id);
                      exs.moveToFirst();


                      MainActivity.db.delExercise(info.id);

                      Log.d(LOG_TAG, "...Удалено упражнение: " + (exs.getString(exs.getColumnIndex("name"))) + "  id:" + info.id + "   ");
                      Toast toast = Toast.makeText(getContext(), "Удалено упражнение: \"" + (exs.getString(exs.getColumnIndex("name")))+"\"", Toast.LENGTH_SHORT);
                      toast.show();
                      ReloadLists();


                      return true;





              }
          });

          Log.d(LOG_TAG, "MGROUP CONTEXT MENU ITEM id: " + info.id + "  child pos:" + info.packedPosition + "   "+exs.getString(exs.getColumnIndex("name")));
      }

    }



/*   @Override
    public boolean onContextItemSelected(MenuItem item) {

        // получаем инфу о пункте списка
       // AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

       ExpandableListView.ExpandableListContextMenuInfo info= (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

        Integer idItem = (int) info.id;
        Boolean isGroup = ExpandableListView.getPackedPositionType(info.packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_GROUP;
        Log.d(LOG_TAG, "--- Тип выделенного элемента (true-группа, false-дочерний): --id-" + idItem + " GROUP?" + isGroup);// + acmi.id + "  pos:" + acmi.position);


        if (item.getItemId() == 9) {

           Cursor exs= MainActivity.db.getExsOfId(info.id);
            exs.moveToFirst();


            MainActivity.db.delExercise(info.id);

            Log.d(LOG_TAG, "...Удалено упражнение: " + (exs.getString(exs.getColumnIndex("name"))) + "  id:" + info.id + "   ");
            Toast toast = Toast.makeText(getContext(), "Удалено упражнение: \"" + (exs.getString(exs.getColumnIndex("name")))+"\"", Toast.LENGTH_SHORT);
            toast.show();
            ReloadLists();


            return true;
        } else

        if (item.getItemId() == 10) {
           // MainActivity.db.addParam("ffffffff","fdfdf");
            Log.d(LOG_TAG, "--- EDIT EXERCISE --id-" + idItem + " GROUP?" + isGroup);
           //   MainActivity.db.delParam(acmi.id);
            Log.d(LOG_TAG, "...изменено упражнение: " + etNameExercise.getText().toString() + "  id:" + info.id + "   ");
            ;
            ReloadLists();

          //  exlvExerciseslist.expandGroup((int) info.packedPosition, false); //разворот групп
            Log.d(LOG_TAG, "...изменено упражнение: " + etNameExercise.getText().toString() + "  id:" + info.id + "   ");
            return true;
        }
        return super.onContextItemSelected(item);
    }*/



    class MyAdapter extends SimpleCursorTreeAdapter {

        public MyAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
        }

        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // получаем курсор по элементам для конкретной группы
            int idColumn = groupCursor.getColumnIndex("Mgroups._id");

            return MainActivity.db.getExsOfMgroups(groupCursor.getInt(idColumn));
        }


    }










}
