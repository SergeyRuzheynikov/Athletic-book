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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class tab6 extends Fragment {
    final String LOG_TAG = "myLogs";

    private   int CM_DELETE_ID = 1;
    private   int CM_EDIT_ID = 2;

    private ListView lvParamList;
    private Button btnAddParam;

    Cursor params;
    private EditText etNameParam, etTypeParam;
    private Spinner spTypeParam,spNameParam;


   // private View v;




    public void ReloadLists()
{    params= MainActivity.db.getParamsList();


    ListAdapter lstAdapter = new SimpleCursorAdapter(getActivity(),
        R.layout.list_item_1,
        params,
        new String[] {"param","type_par"},
        new int[] {android.R.id.text1,android.R.id.text2});
      lvParamList.setAdapter(lstAdapter);


    String[] param_names =
            {"длительность","дистанция","вес","повторения", "повторения (прав)","повторения (лев)"};
    // создаем адаптер
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1, param_names);

    spNameParam.setAdapter(adapter);



    String[] meri =
            { "кг.",
            "анг.фунты",
            "сек.",
            "мин.",
            "часы",
            "км.",
            "мили",
            "раз(а)"};
    // создаем адаптер
    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1, meri);

    spTypeParam.setAdapter(adapter1);


}



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.tab6, container,false);
      /*final*/// View  view=ContentFragment.rv6;
       // view.setAlpha(MainActivity.alpha_bg);
        Log.d(MainActivity.LOG_TAG, "createview");
        //v=view;
        lvParamList = (ListView) view.findViewById(R.id.lvParamList);
        registerForContextMenu(lvParamList);

       // etNameParam=(EditText) view.findViewById(R.id.etNameParam);
      //  etTypeParam=(EditText) view.findViewById(R.id.etTypeParam);
        spNameParam=(Spinner) view.findViewById(R.id.spNameParam);
        spTypeParam=(Spinner) view.findViewById(R.id.spTypeParam);

        btnAddParam=(Button) view.findViewById(R.id.btnAddParam);



        /****************************************************/
        // создаем обработчик нажатия
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "type id: " + spTypeParam.getSelectedItem() + "   ");


                MainActivity.db.addParam(spNameParam.getSelectedItem().toString(), spTypeParam.getSelectedItem().toString());
               ReloadLists();
               // btnAddParam.setText(etNameParam.getText());
            }
        };
        // присвоим обработчик кнопке OK (btnOk)
        btnAddParam.setOnClickListener(oclBtnOk);
        /****************************************************/



      //  spTypeParam.setAdapter(MainActivity.db.getParamsList());
       ReloadLists();

    /*    lvParamList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> a, View v, int position, long id)
                    {
                        Log.d(LOG_TAG, "ITEM id: " + id + "   pos:" + position + "   ");

                    }
                }

        );*/


        return  view;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().onCreateContextMenu(menu, v, menuInfo);


        params.moveToPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);

        Log.d(LOG_TAG, "--- MENU from PARAMS:" + params.getString(params.getColumnIndex("param")) + " --position-" + ((AdapterView.AdapterContextMenuInfo) menuInfo).position + "  id:" + ((AdapterView.AdapterContextMenuInfo) menuInfo).id);

        menu.setHeaderTitle(params.getString(params.getColumnIndex("param")));
     //   menu.add(0, CM_DELETE_ID, 0, "Удалить параметр");
      //  menu.add(0, CM_EDIT_ID, 0, "Изменить параметр");

        menu.add(0, CM_DELETE_ID, 0, "Удалить параметр");
        menu.add(0, CM_EDIT_ID, 0, "Изменить параметр");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            // получаем инфу о пункте списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // удаляем Map из коллекции, используя позицию пункта в списке
           // MainActivity.db.addParam("ffffffff","fdfdf");
            Log.d(LOG_TAG, "--- Delete from PARAMS: --id-" + acmi.id + "  pos:" + acmi.position);
            MainActivity.db.delParam(acmi.id);

           ReloadLists();
            return true;
        } else

            if (item.getItemId() == 2) {
                // получаем инфу о пункте списка
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                // удаляем Map из коллекции, используя позицию пункта в списке
                // MainActivity.db.addParam("ffffffff","fdfdf");
                Log.d(LOG_TAG, "--- EDIT --id-" + acmi.id + "  pos:" + acmi.position);
             //   MainActivity.db.delParam(acmi.id);


                ReloadLists();
                return true;
            }
       return super.onContextItemSelected(item);
    }


    @Nullable
    @Override
    public View getView() {
        Log.d(MainActivity.LOG_TAG, "getview6");
        Log.d(MainActivity.LOG_TAG, super.getView().toString());
        return super.getView();

    }
}
