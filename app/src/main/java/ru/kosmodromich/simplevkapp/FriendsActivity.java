package ru.kosmodromich.simplevkapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import ru.kosmodromich.simplevkapp.Adapters.FriendsAdapter;
import ru.kosmodromich.simplevkapp.Entities.User;

public class FriendsActivity extends AppCompatActivity {

    List<User> friends;
    String data;
    private FriendsAdapter friendsAdapter;
    private static String token = Constants.TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        getSupportActionBar().setHomeButtonEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.friends_list);
        data = getIntent().getStringExtra("userId");
        friends = new ArrayList<>();
        friendsAdapter = new FriendsAdapter(friends);
        recyclerView.setAdapter(friendsAdapter);
        GetFriendsAsyncTask task = new GetFriendsAsyncTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
    }

    private class GetFriendsAsyncTask extends AsyncTask<String, Void, List<User>> {

        private ProgressDialog dialog;

        @Override
        protected List<User> doInBackground(String... data) {
            String url = "https://api.vk.com/method/friends.search?"
                    + "user_id=" + data[0] + "&fields=photo_50"
                    + "&count=200" + "&access_token=" + token + "&v=5,78";
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
                List<User> users = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String photo = "";
                    if (obj.has("photo_50")) {
                        photo = obj.getString("photo_50");
                    }
                    users.add(new User(obj.getLong("id"), obj.getString("first_name"),
                            obj.getString("last_name"), photo));
                }
                return users;
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FriendsActivity.this);
            dialog.setMessage("Ожидайте");
            dialog.setTitle("Загрузка");
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            dialog.dismiss();
            try {
                friends.clear();
                friends.addAll(users);
                friendsAdapter.notifyDataSetChanged();
                dialog.dismiss();
                Log.d("Complete", "complete");
            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
        }
    }
}
