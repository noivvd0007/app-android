package com.vuvannoi.ndapptracnghiem.Main_App.view.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.vuvannoi.ndapptracnghiem.Login_Activity.model.url_sever;
import com.vuvannoi.ndapptracnghiem.Main_App.model.custom_list_topbxh;
import com.vuvannoi.ndapptracnghiem.Main_App.model.topbxh;
import com.vuvannoi.ndapptracnghiem.Main_App.view.Activity.MainActivity;
import com.vuvannoi.ndapptracnghiem.R;
import com.vuvannoi.ndapptracnghiem.share.model.ScreenShot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Admin on 9/6/2017.
 */

public class Fragment_topbxh extends Fragment {

    private static ListView listView;
    private static View view;

    private static ArrayList<topbxh> topbxhArrayList;
    private static custom_list_topbxh adapter;
    RequestQueue requestQueue;
    String data = "";

    private AdView mAdView;

    public Fragment_topbxh() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Bảng xếp hạng");
        view = inflater.inflate(R.layout.fragment_fragment_topbxh, container, false);
        // Inflate the layout for this fragment

        innit();


        // get top

        gettopfromsever(url_sever.getalltop);


        return view;
    }

    private void innit() {
        topbxhArrayList = new ArrayList<topbxh>();
        listView = view.findViewById(R.id.listv_bxh);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //chia sẻ bảng điểm
        ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.imgshareBXH);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chụp ảnh màn hình
                Bitmap bm = ScreenShot.takecreenshotOfRootView(view);
                //lưu hình ảnh
                File file = saveBitmap(bm, "mantis_image.png");
                // chia sẻ lên các phầm mềm mạng xã hội
                Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my app.");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Vũ Nội"));
            }
        });


        // chèn quảng cáo
        MobileAds.initialize(getActivity().getApplicationContext(), "ca-app-pub-1625024826789660~5274914936");

        mAdView = (AdView) getActivity().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    //** bắt đầu  lưu hình ảnh **
    //lưu hình ảnh vừa mới chụp
    private static File saveBitmap(Bitmap bm, String fileName) {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void gettopfromsever(String url) {
        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(getActivity());
        // Casts results into the TextView found within the main layout XML with id jsonData

        JsonObjectRequest obreg = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsarray = response.getJSONArray("topxh");


                            for (int i = 0; i < jsarray.length(); i++) {
                                JSONObject objitem = jsarray.getJSONObject(i);

                                String nameu = objitem.getString("nameu");
                                String score = objitem.getString("score");
                                String mail = objitem.getString("mail");
                                topbxhArrayList.add(new topbxh(nameu, score, mail));
                                data += topbxhArrayList.get(i).toString();
                            }
                            // show du lieu
                         //   Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                            adapter = new custom_list_topbxh(getActivity(), R.layout.custom_lisview_top, topbxhArrayList);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        requestQueue.add(obreg);

    }

}
