package com.vuvannoi.ndapptracnghiem.Login_Activity.view.Fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.Constants;
import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.CustomToast;
import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.RequestInterface;
import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.ServerRequest;
import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.ServerResponse;
import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.Untils;
import com.vuvannoi.ndapptracnghiem.Login_Activity.presenter.User;
import com.vuvannoi.ndapptracnghiem.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 9/6/2017.
 */

public class Fragment_forgotpass extends Fragment implements View.OnClickListener {
    private static View view;
    private static EditText emailId;
  //  private static TextView submit, back;

    private AppCompatButton btn_reset;
    private EditText et_email,et_code,et_password;
    private TextView tv_timer;
    private ProgressBar progress;
    private boolean isResetInitiated = false;
    private String email;
    private CountDownTimer countDownTimer;


    public Fragment_forgotpass() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forgotpass, container,
                false);
        initViews();
    //    back.setOnClickListener(this);
     //   submit.setOnClickListener(this);
        return view;

    }

    private void initViews() {
       // emailId = (EditText) view.findViewById(R.id.registered_emailid);
    //    submit = (TextView) view.findViewById(R.id.forgot_button);
     //   back = (TextView) view.findViewById(R.id.backToLoginBtn);
//        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
//        try {
//            ColorStateList csl = ColorStateList.createFromXml(getResources(),
//                    xrp);
//
//            back.setTextColor(csl);
//            submit.setTextColor(csl);
//
//        } catch (Exception e) {
//        }

        btn_reset = (AppCompatButton)view.findViewById(R.id.btn_reset);
        tv_timer = (TextView)view.findViewById(R.id.timer);
        et_code = (EditText)view.findViewById(R.id.et_code);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);
        et_password.setVisibility(View.GONE);
        et_code.setVisibility(View.GONE);
        tv_timer.setVisibility(View.GONE);
        btn_reset.setOnClickListener(this);
        progress = (ProgressBar)view.findViewById(R.id.progress);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_reset:
                if (!isResetInitiated) {
                    email = et_email.getText().toString();
                    if (!email.isEmpty()) {
                        progress.setVisibility(View.VISIBLE);
                        initiateResetPasswordProcess(email);
                    } else {
                        Snackbar.make(getView(), "Fields are empty !",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    String code = et_code.getText().toString();
                    String password = et_password.getText().toString();
                    if (!code.isEmpty() && !password.isEmpty()) {
                        finishResetPasswordProcess(email, code, password);
                    } else {
                        Snackbar.make(getView(), "Fields are empty !",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    private void initiateResetPasswordProcess(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface =
                retrofit.create(RequestInterface.class);
        User user = new User();
        user.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_INITIATE);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call,
                                   Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(),
                        Snackbar.LENGTH_LONG).show();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    Snackbar.make(getView(), resp.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                    et_email.setVisibility(View.GONE);
                    et_code.setVisibility(View.VISIBLE);
                    et_password.setVisibility(View.VISIBLE);
                    tv_timer.setVisibility(View.VISIBLE);
                    btn_reset.setText("Change Password");
                    isResetInitiated = true;
                    startCountdownTimer();
                } else {
                    Snackbar.make(getView(), resp.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                }
                progress.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(),
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
    private void finishResetPasswordProcess(String email, String code, String
            password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface =
                retrofit.create(RequestInterface.class);
        User user = new User();
        user.setEmail(email);
        user.setCode(code);
        user.setPass(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_FINISH);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call,
                                   Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(),
                        Snackbar.LENGTH_LONG).show();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    Snackbar.make(getView(), resp.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                    countDownTimer.cancel();
                    isResetInitiated = false;
                    goToLogin();
                } else {
                    Snackbar.make(getView(), resp.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                }
                progress.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(),
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void startCountdownTimer(){
        countDownTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished /1000);
            }
            public void onFinish() {
                Snackbar.make(getView(), "Time Out ! Request again to reset password.", Snackbar.LENGTH_LONG).show();
                goToLogin();
            }
        }.start();
    }

    private void goToLogin(){
        Fragment login = new Fragment_login_main();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameContainer,login);
        ft.commit();
    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();
        Pattern p = Pattern.compile(Untils.regEx);
        Matcher m = p.matcher(getEmailId);
        if (getEmailId.equals("") || getEmailId.length() == 0)
            new CustomToast().Show_Toast(getActivity(), view,
                    "bạn chưa nhập email	");
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "email của bạn không hợp lệ");
        else {
//            Intent intent = new Intent(getActivity(), ResetPasswordFragment.class);
//            startActivity(intent);
            Toast.makeText(getActivity(), "Get Forgot Password.",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
