package com.vuvannoi.ndapptracnghiem.Main_App.view.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuvannoi.ndapptracnghiem.Main_App.model.Video;
import com.vuvannoi.ndapptracnghiem.Main_App.presenter.YoutubeAdapter;
import com.vuvannoi.ndapptracnghiem.Main_App.view.Activity.MainActivity;
import com.vuvannoi.ndapptracnghiem.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Created by Admin on 9/6/2017.
 */

public class Fragment_baihoc extends Fragment {
    String API_URI = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=20&playlistId=";
    String PLAYLIST_ID = "PLCJKa_SIQmMWKZLFP3TVIZsJOwkOL2G9O";
    String KEY_BROWSE = "AIzaSyCB59pO7FTdpna3aHMICxqKLtIKjvNH1q8";

    //    String API_URI = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + PLAYLIST_ID + "&key=" + KEY_BROWSE + "&maxResults=50";
//    String PLAYLIST_ID = "PLCJKa_SIQmMWKZLFP3TVIZsJOwkOL2G9O";
//    String KEY_BROWSE = "AIzaSyCB59pO7FTdpna3aHMICxqKLtIKjvNH1q8";
    RecyclerView recyclerVideo;
    private static View view;




    public Fragment_baihoc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Video");
        view = inflater.inflate(R.layout.fragment_fragment_baihoc, container, false);
        recyclerVideo = (RecyclerView) view.findViewById(R.id.recycler_video);
        new ParseVideoYoutube().execute();

        // Inflate the layout for this fragment
        return view;
    }

    //Setup recyclerView
    private void setupRecyclerView(ArrayList<Video> listVideo) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerVideo.setHasFixedSize(true);
        recyclerVideo.setLayoutManager(manager);
        recyclerVideo.setItemAnimator(new DefaultItemAnimator());
        YoutubeAdapter adapter = new YoutubeAdapter(getActivity(),listVideo);
        recyclerVideo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    //AsyncTask parse Json
    private class ParseVideoYoutube extends AsyncTask<Void, Void, ArrayList<Video>> {
        @Override
        protected ArrayList<Video> doInBackground(Void... params) {
            ArrayList<Video> listVideo = new ArrayList<>();
            URL jSonUrl;
            URLConnection jSonConnect;
            try {
                jSonUrl = new URL(API_URI + PLAYLIST_ID + "&key=" + KEY_BROWSE);
                jSonConnect = jSonUrl.openConnection();
                InputStream inputstream = jSonConnect.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                inputstream.close();
                String jSontxt = stringBuilder.toString();

                JSONObject jsonobject = new JSONObject(jSontxt);
                JSONArray allItem = jsonobject.getJSONArray("items");
                for (int i = 0; i < allItem.length(); i++) {
                    JSONObject item = allItem.getJSONObject(i);
                    JSONObject snippet = item.getJSONObject("snippet");
                    String title = snippet.getString("title");              // Get Title Video
                    String decription = snippet.getString("description");   // Get Description
                    JSONObject thumbnails = snippet.getJSONObject("thumbnails");    //Get Url Thumnail
                    JSONObject thumnailsIMG = thumbnails.getJSONObject("medium");
                    String thumnailurl = thumnailsIMG.getString("url");

                    JSONObject resourceId = snippet.getJSONObject("resourceId");    //Get ID Video
                    String videoId = resourceId.getString("videoId");

                    Video video = new Video();
                    video.setTitle(title);
                    video.setThumnail(thumnailurl);
                    video.setDecription(decription);
                    video.setUrlVideo(videoId);
                    //Add video to List
                    listVideo.add(video);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listVideo;
        }

        @Override
        protected void onPostExecute(ArrayList<Video> videos) {
            super.onPostExecute(videos);
            setupRecyclerView(videos);
        }
    }

}
