package com.project.group2.attendancetool.activity.teacher;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.group2.attendancetool.R;
import com.project.group2.attendancetool.helper.Base64Coverter;
import com.project.group2.attendancetool.model.StudentAttendance;
import com.project.group2.attendancetool.utils.Constants;

import java.util.List;

/**
 * Created by User on 3/6/2018.
 */

public class CustomStudentsAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<StudentAttendance> listData;

    public CustomStudentsAdapter(Context context, List<StudentAttendance> listData){
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        ImageView ivAvatar;
        TextView tvName;
        TextView tvStatus;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.list_student_layout,null);
            holder = new ViewHolder();
            holder.ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
            holder.tvName = (TextView) view.findViewById(R.id.tvName);
            holder.tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        StudentAttendance studentAttendance = this.listData.get(i);
        holder.ivAvatar.setImageBitmap(Base64Coverter.toImageBitmap(studentAttendance.getImage()));
        holder.tvName.setText(studentAttendance.getFullName());
        holder.tvStatus.setText(studentAttendance.getAttendanceStatus());
        if(studentAttendance.getAttendanceStatus().equalsIgnoreCase(Constants.AttendanceStatus.PRESENTED)){
            holder.tvStatus.setTextColor(Color.parseColor("#42f46f"));  //set color green for Present Status
        } else if(studentAttendance.getAttendanceStatus().equalsIgnoreCase(Constants.AttendanceStatus.ABSENT)){
            holder.tvStatus.setTextColor(Color.parseColor("#F44336"));  //set color red for Absent Status
        }
        return view;
    }
}
