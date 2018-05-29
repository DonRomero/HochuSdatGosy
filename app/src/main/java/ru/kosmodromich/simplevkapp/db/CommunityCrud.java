package ru.kosmodromich.simplevkapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.kosmodromich.simplevkapp.Entities.Community;

public class CommunityCrud {
    private SQLiteDatabase sqLiteDatabase;

    public CommunityCrud(Context context) {
        sqLiteDatabase = DBhelper.getInstance(context).getWritableDatabase();
    }

    public long create(Community community) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", community.getId());
        contentValues.put("name", community.getName());
        contentValues.put("photo", community.getPhoto());

        return sqLiteDatabase.insert(DBhelper.TABLE_COMMUNITY, null, contentValues);
    }

    public Community readById(int id) {
        Cursor cursor = sqLiteDatabase.query(
                DBhelper.TABLE_COMMUNITY,
                null,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int photoIndex = cursor.getColumnIndex("photo");

            String name = cursor.getString(nameIndex);
            String photo = cursor.getString(photoIndex);

            Community community = new Community();
            community.setId(id);
            community.setName(name);
            community.setPhoto(photo);
            cursor.close();
            return community;
        }
        cursor.close();
        return null;
    }

    public List<Community> readAllByName(String name) {
        ArrayList<Community> communities = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(
                DBhelper.TABLE_COMMUNITY,
                null,
                "name = ?",
                new String[]{name},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int photoIndex = cursor.getColumnIndex("photo");

                int id = cursor.getInt(idIndex);
//                String name = cursor.getString(nameIndex);
                String photo = cursor.getString(photoIndex);

                Community community = new Community();
                community.setId(id);
                community.setName(name);
                community.setPhoto(photo);

                communities.add(community);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return communities;
    }

    public List<Community> readAll() {
        ArrayList<Community> communities = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(
                DBhelper.TABLE_COMMUNITY,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int photoIndex = cursor.getColumnIndex("photo");

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String photo = cursor.getString(photoIndex);

                Community community = new Community();
                community.setId(id);
                community.setName(name);
                community.setPhoto(photo);

                communities.add(community);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return communities;
    }

    public void delete(int id) {
        sqLiteDatabase.delete(
                DBhelper.TABLE_COMMUNITY,
                "id = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public void deleteAll() {
        sqLiteDatabase.delete(DBhelper.TABLE_COMMUNITY, null, null);
    }
}
