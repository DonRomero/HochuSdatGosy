//package ru.kosmodromich.simplevkapp;
//
//import android.app.ProgressDialog;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.provider.ContactsContract;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import ru.kosmodromich.simplevkapp.Adapters.PeoplesAdapter;
//import ru.kosmodromich.simplevkapp.Entities.RequestData;
//import ru.kosmodromich.simplevkapp.Entities.User;
//
//public class PeoplesActivity extends AppCompatActivity {
//    private RequestData data;
//    private List<User> peoples;
//    private PeoplesAdapter peoplesAdapter;
//    private static String token = Constants.TOKEN;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_peoples);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        RecyclerView recyclerView = findViewById(R.id.peoples_list);
//        data = (RequestData) getIntent().getSerializableExtra("data");
//        peoples = new ArrayList<>();
//        peoplesAdapter = new PeoplesAdapter(peoples);
//        recyclerView.setAdapter(peoplesAdapter);
//        GetUserAsyncTask task = new GetUserAsyncTask();
//        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
//    }
//
//    private class GetUserAsyncTask extends AsyncTask<RequestData, Void, List<User>> {
//
//        private ProgressDialog dialog;
//
//        @Override
//        protected List<User> doInBackground(RequestData... requestData) {
//            String url = "https://api.vk.com/method/users.search?"
//                    + "q=" + data.getUserName() + "&fields=photo"
//                    + "&count=200" + "&hometown=" + data.getUserCity()
//                    + "&age_from=" + data.getLeftAgeValue() + "&age_to=" + data.getRightAgeValue()
//                    + "&online=" + (data.isOnlyOnline() ? "1" : "0")
//                    + "&access_token=" + token + "&v=5,78";
//            String inputLine = "";
//            String result = "";
//            try {
//                URL myUrl = new URL(url);
//                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
//                connection.setRequestMethod("GET");
//                connection.connect();
//                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
//                BufferedReader reader = new BufferedReader(streamReader);
//                StringBuilder stringBuilder = new StringBuilder();
//                while ((inputLine = reader.readLine()) != null) {
//                    stringBuilder.append(inputLine);
//                }
//                reader.close();
//                streamReader.close();
//                result = stringBuilder.toString();
//                Log.d("res", result);
//                JSONArray jsonArray = new JSONObject(result)
//                        .getJSONObject("response")
//                        .getJSONArray("items");
//                List<User> users = new ArrayList<>();
//                for (int i = 0; i < jsonArray.length(); ++i) {
//                    JSONObject obj = jsonArray.getJSONObject(i);
//                    String photo = "";
//                    if (obj.has("photo")) {
//                        photo = obj.getString("photo");
//                    }
//                    users.add(new User(obj.getLong("id"), obj.getString("first_name"),
//                            obj.getString("last_name"), photo));
//                }
//                return users;
//            } catch (Exception e) {
//                Log.e("Exception", e.toString());
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(PeoplesActivity.this);
//            dialog.setMessage("Ожидайте");
//            dialog.setTitle("Загрузка");
//            dialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(List<User> users) {
//            super.onPostExecute(users);
//            dialog.dismiss();
//            try {
//                peoples.clear();
//                peoples.addAll(users);
//                peoplesAdapter.notifyDataSetChanged();
//                dialog.dismiss();
//                Log.d("Complete", "complete");
//            } catch (Exception e) {
//                Log.d("Error", e.toString());
//            }
//        }
//    }
//}
