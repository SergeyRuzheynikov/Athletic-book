package com.example.diman.tabsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class MyDatabase extends SQLiteAssetHelper {
    final String LOG_TAG = "myLogs";

    static public Cursor params;

   // static public ListAdapter lstAdaper;
   public SQLiteDatabase dbb ;

    public Context con;

    private static final String DATABASE_NAME = "113.db";
    private static final int DATABASE_VERSION = 1;


    public static final String COLUMN_ID = "id_mgroup";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        dbb = getReadableDatabase();
        con=context;

        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);


    }
    /*======создание тренировки==========*/
    public void addNewTraining(String date_tr, String start_time)
    {ContentValues cv = new ContentValues();
        cv.put("date_tr",date_tr);
        cv.put("start_time_training",start_time);
        dbb.insert("Training", null, cv);//создание записи об упражнении
        // Ex_Params связь упражнения с параметрами
    }

    public void endTraining(String date_tr,String start_time, String end_time)
    {ContentValues cv = new ContentValues();
        cv.put("end_time_training",end_time);
       // dbb.insert("Exercises", null, cv);//создание записи об упражнении
       ////   String where ="((date_tr" + "=" + date_tr+")and(start_time_training="+start_time+"))";
        // Обновите строку с указанным индексом, используя новые значения.
       //// dbb.update("Training", cv, where, null);
        dbb.update("Training",cv, "date_tr = ? AND start_time_training=?",
                new String[] {date_tr,start_time});
    }



    public void addSet(long id_training,long number, long id_exs)
    {ContentValues cv = new ContentValues();

        String comments="nocomments";
        cv.put("id_training",id_training);
        cv.put("id_exs",id_exs);
        cv.put("num",number);
        cv.put("comments",comments);
        dbb.insert("Sets_tr", null, cv);
    }



    public void addResultOfSet(long id_set,long id_param,long res)
    {ContentValues cv = new ContentValues();
        cv.put("id_set",id_set);
        cv.put("id_param",id_param);
        cv.put("res",res);
        dbb.insert("Results", null, cv);
    }


    public Cursor getResultsOfSetAndParam(long id_set, long id_param) {
        Log.d(LOG_TAG, "---RESULT DBB:-" + id_set + " " + id_param + "  ");
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Results._id","Results.res","Results.id_set","Results.id_param"};



        Cursor c;
        c= dbb.query("Results",sqlSelect , "(id_set" + " = "+ id_set+")AND(id_param"+" = "+id_param+")", null, null, null, "Results._id");
        Log.d(LOG_TAG, "---RESULT DBB GOOD:-" + id_set + " " + id_param + "  ");
        c.moveToFirst();

        return c;

    }

    public Cursor getSetsOfTraining(long id_training, long id_exs) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Sets_tr._id","Sets_tr.num","Sets_tr.comments","Sets_tr.id_exs","Sets_tr.id_training"};



        Cursor c;
        c= dbb.query("Sets_tr",sqlSelect , "(Sets_tr.id_training" + " = "+ id_training+")AND(Sets_tr.id_exs"+" = "+id_exs+")", null, null, null, "Sets_tr._id");
        c.moveToFirst();

        return c;

    }
    /*===========конец тренировки===============*/


    public Cursor getTrainings() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"_id","date_tr","start_time_training","end_time_training","comments"};
        String sqlTables = "Training";

        qb.setTables(sqlTables);

        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();

        return c;

    }


    //=================работа со списком параметров...============
    //запрос списка всех параметров
    public Cursor getParamsList() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"_id","param","type_par"};
        String sqlTables = "Params";

        qb.setTables(sqlTables);

        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();

        return c;

    }
    //добавление нового параметра
    public void addParam(String np, String tp)
    {ContentValues cv = new ContentValues();

        cv.put("param",np);
        cv.put("type_par",tp);
        dbb.insert("Params", null, cv);
    }


    //удаление параметра упражнения
    public void delParam(long id)
    {   int delCount = dbb.delete("Params", "_id = " + id, null);
        Log.d("mylogs", "--- Удален параметр с id: " + id);
    }

    //=================...работа со списком параметров============

    //=================работа с упражнениями...============

    // данные по упражнениям конкретной группы
    public Cursor getExsOfMgroups(long id_mgroup) {
        String[] sqlSelect = {"_id","name","id_mgroup"};
        return dbb.query("Exercises",sqlSelect , "id_mgroup" + " = "+ id_mgroup, null, null, null, "_id");
    }

    public Cursor getExsOfId(long id_ex ) {
        String[] sqlSelect = {"_id","name","id_mgroup"};
        return dbb.query("Exercises",sqlSelect , "_id" + " = "+ id_ex, null, null, null, "_id");
    }


    //добавление нового упражнения
    public void addExercise(String name, long id_mgroup)
    {ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("id_mgroup", id_mgroup);
        dbb.insert("Exercises", null, cv);//создание записи об упражнении
        // Ex_Params связь упражнения с параметрами
    }





    //удаление  упражнения
    public void delExercise(long id)
    {   int delCount = dbb.delete("Exercises", "_id = " + id, null);
        Log.d("mylogs", "--- Удалено упражнение с id: " + id);
    }



    public Cursor getExsOfTrainings(long id_training) {
        String[] sqlSelect = {"Exercises._id","Exercises.name","COUNT(*)"};

        return dbb.query("Exercises, Sets_tr",sqlSelect , "(Sets_tr.id_training" + " = "+ id_training+")AND(Sets_tr.id_exs=Exercises._id)",null,"Exercises._id" , null, "Sets_tr._id");
    }
  //====================...работа с упражнениями===============




    //запрос списка всех комплексов
    public Cursor getComplexesList() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"_id","name"};
        String sqlTables = "Complexes";
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        c.moveToFirst();
        return c;

    }

    public Cursor getExsOfComplexes(long complexID) {
        String[] sqlSelect = {"Exercises._id","Exercises.name"};
        //return dbb.query("Exercises, Ex_Complex",sqlSelect , "(Ex_Complex.id_complex" + " = "+ complexID+")AND(id_exs=Exercises._id)"+" ", null, null, null, "Exercises._id");/*AND(id_exs=Exercises._id)*/
        return dbb.query("Exercises, Ex_Complex",sqlSelect , "(Ex_Complex.id_complex" + " = "+ complexID+")AND(id_exs=Exercises._id)"+" ", null, null, null, "Exercises._id");/*AND(id_exs=Exercises._id)*/
    }

    public Cursor getParamsOfEx(long exID) {
        String[] sqlSelect = {"Params._id","Params.param","Params.type_par"};
        //return dbb.query("Exercises, Ex_Complex",sqlSelect , "(Ex_Complex.id_complex" + " = "+ complexID+")AND(id_exs=Exercises._id)"+" ", null, null, null, "Exercises._id");/*AND(id_exs=Exercises._id)*/
        return dbb.query("Params, Ex_Params",sqlSelect , "(Ex_Params.id_exs" + " = "+ exID+")AND(id_param=Params._id)"+" ", null, null, null, "Params._id");/*AND(id_exs=Exercises._id)*/
    }
    /**************************************************************/
    //запрос списка всех мышечных групп
    public Cursor getMgroupsList() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"_id","name"};
        String sqlTables = "Mgroups";
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        c.moveToFirst();
        return c;

    }



    public void delMgroup(long id)
    {   int delCount = dbb.delete("Mgroups", "_id = " + id, null);
        Log.d("mylogs", "--- Удалена группа с id: " + id);
    }


    //добавление нового упражнения
    public void addMgroup(String name)
    {ContentValues cv = new ContentValues();
        cv.put("name",name);
        dbb.insert("Mgroups", null, cv);//создание записи об упражнении
        // Ex_Params связь упражнения с параметрами
    }













}

