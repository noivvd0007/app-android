package com.vuvannoi.ndapptracnghiem.Cauhoi.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vuvannoi.ndapptracnghiem.Cauhoi.model.question;
import com.vuvannoi.ndapptracnghiem.R;

import java.util.ArrayList;

/**
 * Created by Admin on 9/6/2017.
 */

public class checkAnswerAdapter extends BaseAdapter {
    ArrayList lsData;
    LayoutInflater inflater;


    public checkAnswerAdapter(ArrayList lsData, Context context) {
        this.lsData = lsData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lsData.size();
    }

    @Override
    public Object getItem(int position) {
        return lsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        question data = (question) getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gridview_list_answer, null);

            //tùy chỉnh viewholder từ tvNumAnswer <= tvNumAns và tvYourAns <= tvAnswer
            holder.tvNumAnswer = (TextView) convertView.findViewById(R.id.tvNumAns);
            holder.tvYourAns = (TextView) convertView.findViewById(R.id.tvAnswer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // gán giá trị i vào position (vào GirdView)
        int i = position + 1;
        holder.tvNumAnswer.setText("Câu" + i + ": ");
        //lấy câu trả lời bằng cách getTraloi (bên Question)
        holder.tvYourAns.setText(data.getTraloi());
        return convertView;
    }

    // câu hỏi thứ bao nhiêu và đáp án mình đã chọn
    private static class ViewHolder {
        TextView tvNumAnswer, tvYourAns;
    }
}
