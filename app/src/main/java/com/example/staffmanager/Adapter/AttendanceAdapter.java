package com.example.staffmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffmanager.Model.AttendanceModel;
import com.example.staffmanager.Model.StaffModel;
import com.example.staffmanager.R;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AttendanceModel> attendancelist;

    public AttendanceAdapter(Context context, ArrayList<AttendanceModel> attendancelist){

        this.context=context;
        this.attendancelist=attendancelist;

    }



    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View attendancelist=layoutInflater.inflate(R.layout.staff_attendance_item,parent,false);
        AttendanceAdapter.ViewHolder viewHolder=new AttendanceAdapter.ViewHolder(attendancelist);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
        holder.attendancemonth.setText(attendancelist.get(position).getSalaryattendancemonth());
        holder.attendanceyear.setText(attendancelist.get(position).getSalaryattendanceyear());
        holder.attendancecheckin.setText(attendancelist.get(position).getSalarycheckin());
        holder.attendanceleave.setText(attendancelist.get(position).getSalaryleaves());

    }

    @Override
    public int getItemCount() {
        return attendancelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView attendancemonth,attendanceyear,attendancecheckin,attendanceleave;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attendancemonth=itemView.findViewById(R.id.attendancemonth);
            attendanceyear=itemView.findViewById(R.id.attendanceyear);
            attendancecheckin=itemView.findViewById(R.id.attendancecheckin);
            attendanceleave=itemView.findViewById(R.id.attendanceleave);
        }
    }
}
