package com.example.kartcontrolremote;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeKart extends Activity {
    private ArrayList<POJOKart> kartList = new ArrayList<>();
    private AdapterChangeKart mAdapter;

    /* renamed from: com.example.sourabh.kartgrid.ChangeKart$1 */
    class C03701 implements Listener<JSONArray> {
        C03701() {
        }

        public void onResponse(JSONArray response) {
            ChangeKart.this.kartList.clear();
            Log.e("ContentValuesResponse", response.toString());
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    POJOKart pojoKart = new POJOKart();
                    pojoKart.setKart_no(obj.getInt("kart_no"));
                    pojoKart.setEngineCut(false);
                    pojoKart.setActive(false);
                    ChangeKart.this.kartList.add(pojoKart);
                } catch (JSONException e) {
                    Log.e("e", e.getMessage());
                    e.printStackTrace();
                }
            }
            ChangeKart.this.mAdapter.notifyDataSetChanged();
            String stringBuilder = "" +
                    ChangeKart.this.kartList;
            Log.e("t", stringBuilder);
            if (ChangeKart.this.kartList.isEmpty()) {
                Log.e("g", "isempty");
            }
        }
    }

    /* renamed from: com.example.sourabh.kartgrid.ChangeKart$2 */
    class C03712 implements ErrorListener {
        C03712() {
        }

        public void onErrorResponse(VolleyError error) {
            Object[] objArr = new Object[1];
            String stringBuilder = "Error: " +
                    error.getMessage();
            objArr[0] = stringBuilder;
            VolleyLog.e("ContentValues Server Error", objArr);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_kart);
        init();
        updateInactiveKart();
    }

    private void init() {
        RecyclerView mRecyclerView = findViewById(R.id.rv_change_kart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.mAdapter = new AdapterChangeKart(this.kartList);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(this.mAdapter);
    }

    private void updateInactiveKart() {
        String url = "http://192.168.0.100/timer/exe/get_inactive_karts.php";
        MySingleton.getInstance(this).addToRequestQueue(new JsonArrayRequest(url, new C03701(), new C03712()));
    }
}
