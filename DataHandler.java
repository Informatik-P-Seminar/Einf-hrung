package com.example.wdgquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataHandler {

    private DataBaseHelper dbHelper;
    private Context ctx;
    private SQLiteDatabase db;

    public static final String DATABASE_NAME = "quizDB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "quizTable";
    public static final String FRAGE = "frage";
    public static final String A = "a";
    public static final String B = "b";
    public static final String C = "c";
    public static final String D = "d";

    public DataHandler(Context ctx) {
        this.ctx = ctx;
        dbHelper = new DataBaseHelper(ctx);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists quizTable(frage text not null, a text not null," +
                    " b text not null, c text not null, d text not null)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public void openWrite() {
        db = dbHelper.getWritableDatabase();
    }

    public void openRead() {
        db = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertData(String frage, String a, String b, String c, String d) {
        openWrite();
        db.execSQL("insert into quizTable(frage, a, b, c, d) values ('" + frage + "', '" + a + "','" + b + "','" + c + "','" + d + "')");
        close();
    }

    public String[] zufaelligeFrage() {
        openRead();

        Cursor c =  db.rawQuery("select * from quizTable order by random limit 1", null);

        String[] datensatz = new String[5];

        if(c.moveToFirst()) {
            datensatz[0] = c.getString(0);
            datensatz[1] = c.getString(1);
            datensatz[2]= c.getString(2);
            datensatz[3]= c.getString(3);
            datensatz[4] = c.getString(4);
        } else {
            System.out.println(007);
        }

        close();
        return datensatz;
    }

}


