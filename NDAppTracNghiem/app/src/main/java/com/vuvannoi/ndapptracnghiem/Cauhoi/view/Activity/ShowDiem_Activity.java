package com.vuvannoi.ndapptracnghiem.Cauhoi.view.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vuvannoi.ndapptracnghiem.Cauhoi.model.question;
import com.vuvannoi.ndapptracnghiem.Login_Activity.model.url_sever;
import com.vuvannoi.ndapptracnghiem.Main_App.view.Activity.MainActivity;
import com.vuvannoi.ndapptracnghiem.Main_App.view.Fragment.Fragment_kiemtra;
import com.vuvannoi.ndapptracnghiem.R;
import com.vuvannoi.ndapptracnghiem.share.model.ScreenShot;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 9/6/2017.
 */

public class ShowDiem_Activity extends AppCompatActivity {

    private Fragment_kiemtra fragment_kiemtra;
    private Cauhoi_slidesmain_Activity activitys;

    ArrayList<question> arr_QuestBegin = new ArrayList<question>();
    int numNoAns = 0;
    private static int numTrue = 0;
    int numFalse = 0;
    private static int totalScore = 0;

    TextView tvTrue, tvTotalScore, tvxetloai, tvFlase, tvNotQuestion;
    Button btnSaveScore, btnAgain, btnShareMXH, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diem_);
        // ánh xạ

        innit();

        // checkkq
        checkResult();

        tvTrue.setText("" + numTrue);
        tvFlase.setText("" + numFalse);
        tvNotQuestion.setText("" + numNoAns);
        totalScore = numTrue * 10;
        tvTotalScore.setText("" + totalScore + "/100");

        if (totalScore >= 50) {
            tvxetloai.setTextColor(0xFF00FF5D);
            tvxetloai.setText("PASSED");
        } else if (totalScore < 50) {
            tvxetloai.setTextColor(0xFF690F16);
            tvxetloai.setText("FAILED");
        }

        // làm lại
//        btnAgain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                // startActivity(new Intent(ShowDiem_Activity.this,Fragment_kiemtra.class));
//                Intent intent2 = new Intent(ShowDiem_Activity.this, Fragment_kiemtra.class);
//
//                startActivity(intent2);
//                finish();
//            }
//        });

        // chia sẻ điểm
        btnSaveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareScore();

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ShowDiem_Activity.this, R.style.AlertDialogStyle);
                builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ShowDiem_Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        btnShareMXH.setOnClickListener(new View.OnClickListener() {
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

    }

    //=========================================
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
//==========================================

    @Override
    public void onBackPressed() {
        dialogExit();
    }


    // dialog lưu điểm


    private void ShareScore() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ShowDiem_Activity.this, R.style.AlertDialogStyle);
        LayoutInflater inflater = ShowDiem_Activity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.save_score_dialog, null);
        builder.setView(view);

        final EditText edtName = (EditText) view.findViewById(R.id.edtName);
        final EditText edtMail = (EditText) view.findViewById(R.id.edtmail);
        edtName.setText(MainActivity.arrbundelget.get(0).getUser());
        edtMail.setText(MainActivity.arrbundelget.get(0).getEmail());
        TextView tvScore = (TextView) view.findViewById(R.id.tvScore);

        tvScore.setText(totalScore + " điểm");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // lưu điểm vào database onl
                addscoretosever(url_sever.addscoretosevr);
                Toast.makeText(ShowDiem_Activity.this, "Lưu điểm thành công!", Toast.LENGTH_LONG).show();
                finish();
                dialog.dismiss();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog b = builder.create();
        b.show();
    }

    private void addscoretosever(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Lỗi lưu", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("lỗi thêm", error.toString());
                        Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();

                    }
                })
                //
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("nameuu", MainActivity.arrbundelget.get(0).getUser().trim());
                params.put("scoreu", String.valueOf(totalScore).trim());
                params.put("mail", MainActivity.arrbundelget.get(0).getEmail().trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    // confilm exit
    public void dialogExit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ShowDiem_Activity.this, R.style.AlertDialogStyle);
        builder.setIcon(R.drawable.exit);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn thoát hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }

    private void innit() {
        tvTrue = (TextView) findViewById(R.id.tvTrue);
        tvTotalScore = (TextView) findViewById(R.id.tvTotalPoint);
        tvxetloai = (TextView) findViewById(R.id.tvrotdau);
        tvFlase = (TextView) findViewById(R.id.tvFalse);
        tvNotQuestion = (TextView) findViewById(R.id.tvNotAnsQuest);



       // btnAgain = (Button) findViewById(R.id.btnAgain);
        btnSaveScore = (Button) findViewById(R.id.btnSaveScore);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnShareMXH = (Button) findViewById(R.id.btnShare);
    }


    //PT Check kết quả
    public void checkResult() {

        if (activitys.num_ex == 1 || activitys.num_ex == 3) {
            for (int i = 0; i < fragment_kiemtra.arr_ques1.size(); i++) {
                if (fragment_kiemtra.arr_ques1.get(i).getResult().equals(fragment_kiemtra.arr_ques1.get(i).getTraloi()) == true) {
                    numTrue++;
                } else if (fragment_kiemtra.arr_ques1.get(i).getTraloi().equals("") == true) {
                    numNoAns++;
                } else numFalse++;
            }
        } else if (activitys.num_ex == 2 || activitys.num_ex == 4) {
            for (int i = 0; i < fragment_kiemtra.arr_ques2.size(); i++) {
                if (fragment_kiemtra.arr_ques2.get(i).getResult().equals(fragment_kiemtra.arr_ques2.get(i).getTraloi()) == true) {
                    numTrue++;
                } else if (fragment_kiemtra.arr_ques1.get(i).getTraloi().equals("") == true) {
                    numNoAns++;
                } else numFalse++;
            }
        }

    }
}
