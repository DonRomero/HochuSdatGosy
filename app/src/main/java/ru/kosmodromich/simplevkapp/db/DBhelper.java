package ru.kosmodromich.simplevkapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "vkDB";
    private static DBhelper instance = null;

    public static final String TABLE_COMMUNITY = "Community",
            TABLE_ALBUM = "Album";

    public DBhelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static DBhelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBhelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_COMMUNITY + "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "photo TEXT NOT NULL" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ALBUM + "(" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT NOT NULL," +
                "photo TEXT," +
                "ownerId INTEGER NOT NULL," +
                "FOREIGN KEY(ownerId) REFERENCES " + TABLE_COMMUNITY + "(id)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Community;");
        onCreate(sqLiteDatabase);
    }
}
