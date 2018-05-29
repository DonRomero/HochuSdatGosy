package ru.kosmodromich.simplevkapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.kosmodromich.simplevkapp.Entities.Album;
import ru.kosmodromich.simplevkapp.Entities.Community;

public class AlbumCrud {
    private SQLiteDatabase sqLiteDatabase;

    public AlbumCrud(Context context) {
        sqLiteDatabase = DBhelper.getInstance(context).getWritableDatabase();
    }

    public long create(Album album) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", album.getId());
        contentValues.put("title", album.getTitle());
        contentValues.put("photo", album.getPhoto());
        contentValues.put("ownerId", album.getOwnerId());

        return sqLiteDatabase.insert(DBhelper.TABLE_ALBUM, null, contentValues);
    }

    public Album readById(int id) {
        Cursor cursor = sqLiteDatabase.query(
                DBhelper.TABLE_ALBUM,
                null,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("title");
            int photoIndex = cursor.getColumnIndex("photo");
            int ownerIdIndex = cursor.getColumnIndex("ownerId");

            String title = cursor.getString(titleIndex);
            String photo = cursor.getString(photoIndex);
            int ownerId = cursor.getInt(ownerIdIndex);

            Album album = new Album();
            album.setId(id);
            album.setTitle(title);
            album.setPhoto(photo);
            album.setOwnerId(ownerId);
            cursor.close();
            return album;
        }
        cursor.close();
        return null;
    }

    public List<Album> readAll() {
        ArrayList<Album> albums = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(
                DBhelper.TABLE_ALBUM,
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
                int titleIndex = cursor.getColumnIndex("title");
                int photoIndex = cursor.getColumnIndex("photo");
                int ownerIdIndex = cursor.getColumnIndex("ownerId");

                int id = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String photo = cursor.getString(photoIndex);
                int ownerId = cursor.getInt(ownerIdIndex);

                Album album = new Album();
                album.setId(id);
                album.setTitle(title);
                album.setPhoto(photo);
                album.setOwnerId(ownerId);

                albums.add(album);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return albums;
    }

    public void delete(int id) {
        sqLiteDatabase.delete(
                DBhelper.TABLE_ALBUM,
                "id = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public void deleteAll() {
        sqLiteDatabase.delete(DBhelper.TABLE_ALBUM, null, null);
    }
}
