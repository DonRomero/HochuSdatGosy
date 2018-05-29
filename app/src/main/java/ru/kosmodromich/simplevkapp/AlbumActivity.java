package ru.kosmodromich.simplevkapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.kosmodromich.simplevkapp.Adapters.AlbumAdapter;
import ru.kosmodromich.simplevkapp.Adapters.CommunityAdapter;
import ru.kosmodromich.simplevkapp.Entities.Album;
import ru.kosmodromich.simplevkapp.Entities.Community;
import ru.kosmodromich.simplevkapp.Entities.RequestCommunityData;
import ru.kosmodromich.simplevkapp.db.AlbumCrud;

public class AlbumActivity extends AppCompatActivity {
    List<Album> groupAlbums;
    AlbumAdapter albumAdapter;
    AlbumCrud albumCrud;
    int ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        albumCrud = new AlbumCrud(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.community_list);
        ownerId = getIntent().getIntExtra("ownerId", 0);
        groupAlbums = new ArrayList<>();
        albumAdapter = new AlbumAdapter(groupAlbums);
        recyclerView.setAdapter(albumAdapter);
        GetAlbumsAsyncTask task = new GetAlbumsAsyncTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class GetAlbumsAsyncTask extends AsyncTask<Void, Void, List<Album>> {

        private ProgressDialog dialog;

        @Override
        protected List<Album> doInBackground(Void... voids) {
            String url = "https://api.vk.com/method/photos.getAlbums?"
                    + "owner_id=-" + ownerId
                    + "&count=20" + "&access_token=" + Constants.TOKEN + "&v=5,78";
            String inputLine = "";
            String result = "";
            try {
                URL myUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                result = stringBuilder.toString();
                Log.d("res", result);
                JSONArray jsonArray = new JSONObject(result)
                        .getJSONObject("response")
                        .getJSONArray("items");
                List<Album> albums = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String photo = "";
                    if (obj.has("photo_50")) {
                        photo = obj.getString("photo_50");
                    }
                    Album album = new Album();
                    album.setId(obj.getInt("id"));
                    album.setTitle(obj.getString("title"));
                    album.setPhoto(photo);
                    album.setOwnerId(ownerId);
                    albums.add(album);
                }
                return albums;
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AlbumActivity.this);
            dialog.setMessage("Ожидайте");
            dialog.setTitle("Загрузка");
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<Album> albums) {
            super.onPostExecute(albums);
            dialog.dismiss();
            try {
                groupAlbums.clear();
                groupAlbums.addAll(albums);
                albumAdapter.notifyDataSetChanged();
                dialog.dismiss();
                Log.d("Complete", "complete");
            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
        }
    }
}
