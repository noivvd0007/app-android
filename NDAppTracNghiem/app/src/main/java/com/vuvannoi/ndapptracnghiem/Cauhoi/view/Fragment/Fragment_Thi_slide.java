package com.vuvannoi.ndapptracnghiem.Cauhoi.view.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.vuvannoi.ndapptracnghiem.Cauhoi.model.checkketqua;
import com.vuvannoi.ndapptracnghiem.Cauhoi.model.question;
import com.vuvannoi.ndapptracnghiem.Cauhoi.view.Activity.Cauhoi_slidesmain_Activity;
import com.vuvannoi.ndapptracnghiem.Main_App.view.Fragment.Fragment_kiemtra;
import com.vuvannoi.ndapptracnghiem.R;

import java.util.ArrayList;

/**
 * Created by Admin on 9/6/2017.
 */
public class Fragment_Thi_slide extends Fragment {

    private View view;
    private Fragment_kiemtra fragment_kiemtra;
    public static final String KEY_PAGE = "page";
    public static final String KEY_CHECKANSWER = "checkAnswer";

    private static int checkAns;   // biến kiểm tra ...
    private static int numpage;

    private static Cauhoi_slidesmain_Activity activitys;
    private static String ketquacau;
    public static ArrayList<checkketqua> arr_ketqualam;
    private AdView mAdView;

    // textview , radbutton
    TextView tvNum, tvQuestion;
    RadioGroup radioGroup;
    RadioButton radA, radB, radC, radD;

    // int dem ket qua dung
    private static int demkq = 0;


    public Fragment_Thi_slide() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment__thi_slide, container, false);
        // Inflate the layout for this fragment


        // getnumpage
        final Cauhoi_slidesmain_Activity activitys = (Cauhoi_slidesmain_Activity) getActivity();
        numpage = getArguments().getInt(KEY_PAGE);
        checkAns = getArguments().getInt(KEY_CHECKANSWER);


        inits();

        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
//                // This will get the radiobutton that has changed in its check state
//               if(checkedId == 2131558555)
//               {
//                   ketquacau = "A";
//                 //  Toast.makeText(getActivity(), ketquacau, Toast.LENGTH_SHORT).show();
//               }
//               else if(checkedId == 2131558556)
//               {
//                   ketquacau = "B";
//                  // Toast.makeText(getActivity(), ketquacau, Toast.LENGTH_SHORT).show();
//               }
//               else if(checkedId == 2131558557)
//               {
//                   ketquacau = "C";
//                  // Toast.makeText(getActivity(), ketquacau, Toast.LENGTH_SHORT).show();
//               }

                if(activitys.num_ex ==1 || activitys.num_ex == 3)
                {
                    fragment_kiemtra.arr_ques1.get(numpage).choiceID = checkedId;
                    fragment_kiemtra.arr_ques1.get(numpage).setTraloi(getChoiceFromID(checkedId));

                }
                else
                if(activitys.num_ex ==2 || activitys.num_ex == 4)
                {
                    fragment_kiemtra.arr_ques2.get(numpage).choiceID = checkedId;
                    fragment_kiemtra.arr_ques2.get(numpage).setTraloi(getChoiceFromID(checkedId));

                }

            }

        });



        return view;
    }

    // anhs xa
    private void inits()
    {
        // new Arr ket qua
        arr_ketqualam = new ArrayList<checkketqua>();

        tvNum = (TextView) view.findViewById(R.id.tvNum);
        tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        radA = (RadioButton) view.findViewById(R.id.radA);
        radB = (RadioButton) view.findViewById(R.id.radB);
        radC = (RadioButton) view.findViewById(R.id.radC);
        radD = (RadioButton) view.findViewById(R.id.radD);
        radioGroup = (RadioGroup) view.findViewById(R.id.radGroup);

    }


//    public question getItem1(int posotion){
//        return fragment_kiemtra.arr_ques1.get(posotion);
//    }
//    public question getItem2(int posotion){
//        return fragment_kiemtra.arr_ques1.get(posotion);
//    }





    public static Fragment_Thi_slide create(int numPage, int checkAnswer)
    {
        Fragment_Thi_slide fragment = new Fragment_Thi_slide();
        Bundle args = new Bundle();
        args.putInt(KEY_PAGE, numPage);
        args.putInt(KEY_CHECKANSWER,checkAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(activitys.num_ex ==1 || activitys.num_ex==3)
        {
            tvNum.setText("Đề "+activitys.num_ex+"-Câu " + (numpage + 1));
            tvQuestion.setText(getItem1(numpage).getQuestion());

            radA.setText(getItem1(numpage).getAnsA());
            radB.setText(getItem1(numpage).getAnsB());
            radC.setText(getItem1(numpage).getAnsC());
            radD.setText(getItem1(numpage).getAnsD());



        }
        else
        if(activitys.num_ex ==2 || activitys.num_ex==4)
        {
            tvNum.setText("Đề "+activitys.num_ex+"-Câu " + (numpage + 1));
            tvQuestion.setText(fragment_kiemtra.arr_ques2.get(numpage).getQuestion());

            radA.setText(fragment_kiemtra.arr_ques2.get(numpage).getAnsA());
            radB.setText(fragment_kiemtra.arr_ques2.get(numpage).getAnsB());
            radC.setText(fragment_kiemtra.arr_ques2.get(numpage).getAnsC());
            radD.setText(fragment_kiemtra.arr_ques2.get(numpage).getAnsD());
        }

// check đáp án
//đóng băng dáp án
        if(checkAns!=0){
            radA.setClickable(false);
            radB.setClickable(false);
            radC.setClickable(false);
            radD.setClickable(false);
            getCheckAns(getItem1(numpage).getResult().toString());
        }

        MobileAds.initialize(getActivity().getApplicationContext(),"ca-app-pub-1625024826789660~5274914936ư");

        mAdView = (AdView) getActivity().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    public question getItem1(int posotion){
        return fragment_kiemtra.arr_ques1.get(posotion);
    }
    //Lấy giá trị (vị trí) radiogroup chuyển thành đáp án A/B/C/D
    private String getChoiceFromID(int ID) {
        if (ID == R.id.radA) {
            return "A";
        } else if (ID == R.id.radB) {
            return "B";
        } else if (ID == R.id.radC) {
            return "C";
        } else if (ID == R.id.radD) {
            return "D";
        } else return "";
    }

    //Hàm kiểm tra câu đúng, nếu câu đúng thì đổi màu background radiobutton tương ứng
    private void getCheckAns(String ans){
        if(ans.equals("A")==true){
            radA.setBackgroundColor(Color.GREEN);
        }else if(ans.equals("B")==true){
            radB.setBackgroundColor(Color.GREEN);
        }else if(ans.equals("C")==true){
            radC.setBackgroundColor(Color.GREEN);
        }else if(ans.equals("D")==true){
            radD.setBackgroundColor(Color.GREEN);
        }else ;
    }
}

