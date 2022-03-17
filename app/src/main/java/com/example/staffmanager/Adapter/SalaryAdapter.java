package com.example.staffmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffmanager.Model.AttendanceModel;
import com.example.staffmanager.Model.SalaryModel;
import com.example.staffmanager.R;

import java.util.ArrayList;

public class SalaryAdapter extends RecyclerView.Adapter<SalaryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SalaryModel> salarylist;

    public SalaryAdapter(Context context, ArrayList<SalaryModel> salarylist){

        this.context=context;
        this.salarylist=salarylist;

    }


    @NonNull
    @Override
    public SalaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View salarylist=layoutInflater.inflate(R.layout.staff_salary_item,parent,false);
        SalaryAdapter.ViewHolder viewHolder=new SalaryAdapter.ViewHolder(salarylist);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SalaryAdapter.ViewHolder holder, int position) {

        holder.salarymonth.setText(salarylist.get(position).getSalarymonth());
        holder.salaryyear.setText(salarylist.get(position).getSalaryyear());
        holder.salaryfixed.setText("Fixed : "+context.getString(R.string.Rs)+" "+salarylist.get(position).getSalaryfixed());
        holder.salarycommision.setText("Commisions : "+context.getString(R.string.Rs)+" "+salarylist.get(position).getSalarycommision());
        holder.salaryvalue.setText(context.getString(R.string.Rs)+" "+salarylist.get(position).getTotalsalary());
        holder.salarystatus.setText(salarylist.get(position).getSalarystatus());



    }

    @Override
    public int getItemCount() {
        return salarylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView salarymonth,salaryyear,salaryfixed,salarycommision,salaryvalue,salarystatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            salarymonth=itemView.findViewById(R.id.salarymonth);
            salaryyear=itemView.findViewById(R.id.salaryyear);
            salaryfixed=itemView.findViewById(R.id.salaryfixed);
            salarycommision=itemView.findViewById(R.id.salarycommision);
            salaryvalue=itemView.findViewById(R.id.salaryvalue);
            salarystatus=itemView.findViewById(R.id.salarystatus);
        }
    }
}
