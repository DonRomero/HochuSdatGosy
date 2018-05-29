package ru.kosmodromich.simplevkapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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

import ru.kosmodromich.simplevkapp.Adapters.CommunityAdapter;
import ru.kosmodromich.simplevkapp.Entities.Community;
import ru.kosmodromich.simplevkapp.Entities.RequestCommunityData;
import ru.kosmodromich.simplevkapp.db.CommunityCrud;

public class CommunityActivity extends AppCompatActivity {

    List<Community> groups;
    CommunityAdapter communityAdapter;
    CommunityCrud communityCrud;
    RequestCommunityData requestCommunityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        communityCrud = new CommunityCrud(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.community_list);
        requestCommunityData = getIntent().getParcelableExtra("data");
        groups = new ArrayList<>();
        communityAdapter = new CommunityAdapter(this, groups);
        recyclerView.setAdapter(communityAdapter);
        CommunityActivity.GetCommunitiesAsyncTask task = new CommunityActivity.GetCommunitiesAsyncTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class GetCommunitiesAsyncTask extends AsyncTask<Void, Void, List<Community>> {

        private ProgressDialog dialog;

        @Override
        protected List<Community> doInBackground(Void... voids) {
            String url = "https://api.vk.com/method/groups.search?"
                    + "q=" + requestCommunityData.getName()
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
                List<Community> communities = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String photo = "";
                    if (obj.has("photo_50")) {
                        photo = obj.getString("photo_50");
                    }
                    Community community = new Community();
                    community.setId(obj.getInt("id"));
                    community.setName(obj.getString("name"));
                    community.setPhoto(photo);
                    communities.add(community);

                    communityCrud.create(community);
                }
                return communities;
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                return communityCrud.readAllByName(requestCommunityData.getName());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CommunityActivity.this);
            dialog.setMessage("Ожидайте");
            dialog.setTitle("Загрузка");
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<Community> communities) {
            super.onPostExecute(communities);
            dialog.dismiss();
            try {
                groups.clear();
                groups.addAll(communities);
                communityAdapter.notifyDataSetChanged();
                dialog.dismiss();
                Log.d("Complete", "complete");
            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
        }
    }
}
