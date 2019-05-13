package com.example.kartcontrolremote;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.example.kartcontrolremote.R.id.tv_check;

public class Grid extends Activity implements gridListener {

    private String TAG;
    final Handler handler = new Handler();
    private ArrayList<POJOKart> kartList = new ArrayList<>();
    private AdapterGrid mAdapter;
    RecyclerView mRecyclerView;
    boolean refreshBool = true;
    View tv_warning;
    private String url_active;
    private String url_trigger;

    /* renamed from: com.example.sourabh.kartgrid.Grid$8 */
    class C02518 implements OnCancelListener {
        C02518() {
        }

        public void onCancel(DialogInterface dialogInterface) {
            Log.e("asd", "canceled");
            Grid.this.refreshBool = true;
        }
    }

    /* renamed from: com.example.sourabh.kartgrid.Grid$3 */
    class C03733 implements ErrorListener {
        C03733() {
        }

        public void onErrorResponse(VolleyError error) {
            Object[] objArr = new Object[1];
            String stringBuilder = "Error: " +
                    error.getMessage();
            objArr[0] = stringBuilder;
            VolleyLog.e("Server Error", objArr);
        }
    }

    /* renamed from: com.example.sourabh.kartgrid.Grid$6 */
    class C03746 implements Listener<JSONArray> {
        C03746() {
        }

        public void onResponse(JSONArray response) {
            Grid.this.kartList.clear();
            String stringBuilder = Grid.this.TAG +
                    "Response";
            Log.e(stringBuilder, response.toString());
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    POJOKart pojoKart = new POJOKart();
                    pojoKart.setKart_no(obj.getInt("kart_no"));
                    pojoKart.setEngineCut(false);
                    if (obj.getBoolean("makeGreen")) {
                        pojoKart.setGreen(true);
                    } else {
                        pojoKart.setGreen(false);
                    }
                    if (obj.getBoolean("batt_active")) {
                        pojoKart.setBattActive(true);
                    } else {
                        pojoKart.setBattActive(false);
                    }
                    if (obj.getInt("active") == 1) {
                        pojoKart.setActive(true);
                    } else {
                        pojoKart.setActive(false);
                    }
                    Grid.this.kartList.add(pojoKart);
                } catch (JSONException e) {
                    Log.e("e", e.getMessage());
                    e.printStackTrace();
                }
            }
            Grid.this.mAdapter.notifyDataSetChanged();
        }
    }

    /* renamed from: com.example.sourabh.kartgrid.Grid$7 */
    class C03757 implements ErrorListener {
        C03757() {
        }

        public void onErrorResponse(VolleyError error) {
            if (Grid.this.tv_warning.getVisibility() == View.INVISIBLE) {
                Grid.this.tv_warning.setVisibility(View.VISIBLE);
//                Log.e("val",Grid.this.tv_warning.toString());

                Grid.this.mRecyclerView.setVisibility(View.INVISIBLE);
            }


            Grid.this.kartList.clear();
            Grid.this.mAdapter.notifyDataSetChanged();
            String stringBuilder2 = Grid.this.TAG +
                    " Server Error";
            Object[] objArr = new Object[1];
            String stringBuilder3 = "Error: " +
                    error.getMessage();
            objArr[0] = stringBuilder3;
            VolleyLog.e(stringBuilder2, objArr);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        init();
        updateData();
        final Handler updateHandler = new Handler();
        updateHandler.postDelayed(new Runnable() {
            public void run() {
                Grid.this.updateData();
                updateHandler.postDelayed(this, 2500);
            }
        }, 2500);
    }

    private void init() {
        this.TAG = getClass().getSimpleName();
        String url_base = getResources().getString(R.string.url_base);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url_base);
        stringBuilder.append("/exe/trigger.php");
        this.url_trigger = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(url_base);
        stringBuilder.append("/exe/set_active.php");
        this.url_active = stringBuilder.toString();
        this.tv_warning = findViewById(tv_check);
        this.mRecyclerView = findViewById(R.id.rv_grid);
        this.mRecyclerView.setHasFixedSize(true);
        gridListener mListener = this;
        this.mAdapter = new AdapterGrid(this.kartList, mListener);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        this.mRecyclerView.setAdapter(this.mAdapter);


    }

    private void sendObject(JSONObject object, String url, final int choice) {
        Log.e(this.TAG, url);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(new JsonObjectRequest(POST, url, object, new Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                Log.e(Grid.this.TAG, response.toString());
                if (choice == 1) {
                    Grid.this.updateData();
                }
            }
        }, new C03733()) {
            public Map getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("charset", "utf-8");
                return headers;
            }
        });
    }

    public void gridCutEngine(final View v, int position) {
        if (!this.kartList.get(position).isBattActive()) {
            return;
        }
        if (this.kartList.get(position).isEngineCut()) {
            Log.e(this.TAG, "kart is inactive");
            return;
        }
        String str = this.TAG;
        String stringBuilder = "" +
                position;
        Log.e(str, stringBuilder);
        v.setBackgroundColor(-16711936);
        this.kartList.get(position).setEngineCut(true);
        final int pos = position;
        this.handler.postDelayed(new Runnable() {
            public void run() {
                v.setBackgroundColor(-1);
                Grid.this.kartList.get(pos).setEngineCut(false);
                try {
                    new JSONObject().put("trigger", pos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 10000);
        Vibrator vibs = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibs.vibrate(50);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("trigger", this.kartList.get(position).getKart_no());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendObject(jsonObj, this.url_trigger, 2);
    }

    public void makeKartActive(View v, int position) {
        if (this.kartList.get(position).isBattActive()) {
            Vibrator vibs = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibs.vibrate(50);
            this.kartList.get(position).setActive(true);
            v.setBackgroundColor(-1);
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("i", this.kartList.get(position).getKart_no());
                jsonObj.put("active", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sendObject(jsonObj, this.url_active, 1);
        }
    }

    public boolean gridCheckActive(View v, int position) {
        return this.kartList.get(position).isActive();
    }

    public void changeKart(View v, int position) {
        startActivity(new Intent(this, ChangeKart.class));
    }

    public void dialog(int position) {
        if (this.kartList.get(position).isBattActive()) {
            this.refreshBool = false;
            showDialogue(position);
        }
    }

    protected void onResume() {
        super.onResume();
        if (!this.refreshBool) {
            this.refreshBool = true;
        }
        updateData();
    }

    protected void onPause() {
        super.onPause();
        this.refreshBool = false;
        Log.e("as", "paused");
    }

    private void updateData() {
        if (this.refreshBool) {
            if (this.mRecyclerView.getVisibility() == View.INVISIBLE) {
                this.tv_warning.setVisibility(View.INVISIBLE);
                this.mRecyclerView.setVisibility(View.VISIBLE);
                Log.e("toggled to grid", "yes ");
            }
            String url_base = getResources().getString(R.string.url_base);
            String url = url_base +
                    "/exe/get_kartlist.php";

            Log.e("url", "a " + url);
            MySingleton.getInstance(this).addToRequestQueue(new JsonArrayRequest(url, new C03746(), new C03757()));
        }
    }

    private void showDialogue(final int position) {
        Builder builder;
        if (VERSION.SDK_INT >= 21) {
            builder = new Builder(this, 16974374);
        } else {
            builder = new Builder(this);
        }
        Builder title = builder.setTitle("Kart Status");
        String stringBuilder = "Stop Kart " +
                this.kartList.get(position).getKart_no() +
                " ?";
        title.setMessage(stringBuilder).setPositiveButton("Stop", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Grid.this.refreshBool = true;
                Grid.this.stopKart(Grid.this.kartList.get(position).getKart_no());
            }
        }).setNegativeButton("Change", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Grid.this.refreshBool = false;
                Intent i = new Intent(Grid.this, ChangeKart.class);
                i.putExtra("kart_no", Grid.this.kartList.get(position).getKart_no());
                Grid.this.startActivity(i);
            }
        }).setOnCancelListener(new C02518()).show();
    }

    private void stopKart(int kart_no) {
        Vibrator vibs = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibs.vibrate(50);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("i", kart_no);
            jsonObj.put("active", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendObject(jsonObj, this.url_active, 1);
    }
}
