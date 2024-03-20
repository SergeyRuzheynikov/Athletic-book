package com.example.diman.tabsapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

/**
 * Created by Dr.h3cker on 16/03/2015.
 */
public class tab3 extends Fragment {
    final String LOG_TAG = "myLogs";
   public ExpandableListView exlvGroupsExercisesList, exlvComplexsExs;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab3,container,false);

        exlvComplexsExs = (ExpandableListView) view.findViewById(R.id.exlvComplexsExs);

        exlvGroupsExercisesList = (ExpandableListView) view.findViewById(R.id.exlvGroupsExercisesList);

        ReloadLists();


       exlvGroupsExercisesList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
           @Override
           public boolean onChildClick(ExpandableListView parent, View v,
                                       int groupPosition, int childPosition, long id) {

               Log.d(LOG_TAG, "ITEM id: " + id + "  child pos:" + childPosition + "   ");

               return true;
           }
       });

        exlvGroupsExercisesList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                Log.d(LOG_TAG, "ITEM id: " + l + "  GROUP pos:" + i + "   ");
                return false;
            }


        });




        return view;
    }






    public void ReloadLists()
    {
        Cursor cursor = MainActivity.db.getComplexesList();
        //startManagingCursor(cursor);
        // сопоставление данных и View для групп
        String[] groupFrom = { "Complexes.name" };
        int[] groupTo = { android.R.id.text1 };
        // сопоставление данных и View для элементов
        String[] childFrom = { "Exercises.name"};
        int[] childTo = { android.R.id.text1 };

        // создаем адаптер и настраиваем список
        SimpleCursorTreeAdapter exlstAdapter = new ComplexesAdapter(MainActivity.db.con, cursor,
                R.layout.expandable_list_item_parent, groupFrom, //expandable_list_item_parent
                groupTo,R.layout.list_item_1, childFrom, //expandable_list_item_child
                childTo);

        exlvComplexsExs.setAdapter(exlstAdapter);


        /*************************************************************************************/
        Cursor cursor1 = MainActivity.db.getMgroupsList();
        //startManagingCursor(cursor);
        // сопоставление данных и View для групп
        String[] groupFrom1 = { "Mgroups.name" };
        int[] groupTo1 = { android.R.id.text1 };
        // сопоставление данных и View для элементов
        String[] childFrom1 = { "Exercises.name"};
        int[] childTo1 = { android.R.id.text1 };

        // создаем адаптер и настраиваем список
        SimpleCursorTreeAdapter lstAdapter = new MyAdapter(MainActivity.db.con, cursor1,
                R.layout.expandable_list_item_parent, groupFrom1, //expandable_list_item_parent
                groupTo1,R.layout.list_item_1, childFrom1, //expandable_list_item_child
                childTo1);

        exlvGroupsExercisesList.setAdapter(lstAdapter);








    }





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



    class ComplexesAdapter extends SimpleCursorTreeAdapter {

        public ComplexesAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
        }

        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // получаем курсор по элементам для конкретной группы
            int idColumn = groupCursor.getColumnIndex("Complexes._id");
            return MainActivity.db.getExsOfComplexes(groupCursor.getInt(idColumn));
        }


    }




}
