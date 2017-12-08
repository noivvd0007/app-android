package com.vuvannoi.ndapptracnghiem.Login_Activity.view.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.vuvannoi.ndapptracnghiem.Login_Activity.model.checkAcc_model;
import com.vuvannoi.ndapptracnghiem.Login_Activity.model.url_sever;
import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.CustomToast;
import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.Untils;
import com.vuvannoi.ndapptracnghiem.Login_Activity.view.Activity.Login_Activity;
import com.vuvannoi.ndapptracnghiem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 9/6/2017.
 */

public class Fragment_signup extends Fragment implements View.OnClickListener {
    private static View view;
    private static EditText fullName, emailId, mobileNumber,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    ArrayList<checkAcc_model> checkAcc_array;


    // khai báo database


    public Fragment_signup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        //ánh xạ
        initViews();

        //xự kiện click
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
        return view;
    }

    //ánh xạ các thành phần trong Fragment
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpBtn:
                checkValidation();
                break;
            case R.id.already_user:
                new Login_Activity().replaceLoginFragment();
                break;
        }
    }

    private void checkValidation() {
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();
        Pattern p = Pattern.compile(Untils.regEx);

        Matcher m = p.matcher(getEmailId);
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)
            new CustomToast().Show_Toast(getActivity(), view,
                    "bạn chưa nhập đủ");
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "sai email");
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "nhập lại mật khẩu không đúng.");
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "vui lòng tích vô đồng ý cái điều khoản và điều kiện!");
//        else if (getFullName!=null || getFullName==null){
//
//
//            for (int i = 0; i < checkAcc_array.size(); i++) {
//
//                if (getFullName.equals(checkAcc_array.get(i).getUsername())) {
//
//                        Toast.makeText(getActivity(),"ok" + checkAcc_array, Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//
//        }
        else {
            // đăng kí thành công add vô DB

            addRegester(url_sever.Signup_acc);

            //  Toast.makeText(getActivity(), "Đăng kí thành công.", Toast.LENGTH_SHORT).show();

//            fullName.setText("");
//            emailId.setText("");
//            mobileNumber.setText("");
//            password.setText("");
//            confirmPassword.setText("");
        }
    }

    private void addRegester(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(getActivity(), "Đăng kí tài khoản thành công!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Lỗi tài khoản hoặc email bạn đã bị trùng", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("lỗi thêm" , error.toString());
                        Toast.makeText(getActivity(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("userlg",fullName.getText().toString().trim());
                params.put("emaillg",emailId.getText().toString().trim());
                params.put("phonelg",mobileNumber.getText().toString().trim());
                params.put("passlg",password.getText().toString().trim());

                return params;
            }
        };

        requestQueue.add(stringRequest);

       // new Login_Activity().replaceLoginFragment();


    }
}
