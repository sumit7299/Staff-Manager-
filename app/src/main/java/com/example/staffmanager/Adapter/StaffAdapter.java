package com.example.staffmanager.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffmanager.Model.StaffModel;
import com.example.staffmanager.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.ListIterator;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {


    private Context context;
    private ArrayList<StaffModel> stafflist;
    private Showstaffdata staffdata;

    public StaffAdapter(Context context,ArrayList<StaffModel>stafflist,Showstaffdata staffdata){

        this.context=context;
        this.stafflist=stafflist;
        this.staffdata=staffdata;
    }

    public void filterList(ArrayList<StaffModel> filteredlist) {
        stafflist=filteredlist;
        notifyDataSetChanged();
    }

    public interface Showstaffdata{

        void showdata(StaffModel itemviewmodel, int pos);

        void showprofiledata(StaffModel itemviewmodel,int pos);

        void showwhatsapp(StaffModel itemviewmodel,int pos);

        void showcontact(StaffModel itemviewmodel,int pos);


    }


    @NonNull
    @Override
    public StaffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View stafflist=layoutInflater.inflate(R.layout.staff_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(stafflist);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StaffAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (!stafflist.get(position).getProfileimage().isEmpty()
                && stafflist.get(position).getProfileimage() != null
                && !stafflist.get(position).getProfileimage().equals("")
                && !stafflist.get(position).getProfileimage().toString().equals("null")) {


            //textview invisible
            holder.profilelogo.setVisibility(View.INVISIBLE);
            //   Log.e("img",list.get(position).getCouponimage());
            String uri = stafflist.get(position).getProfileimage();

            Toast.makeText(context, uri+"", Toast.LENGTH_SHORT).show();
//             Picasso.get()
//                    .load(uri)
//                    .noFade().error()
//                    .into(holder.staffprofileimage);
        }
        else{
            //textview visible
            holder.profilelogo.setVisibility(View.VISIBLE);
            holder.profilelogo.setText(stafflist.get(position).getFirstname().substring(0,1).toUpperCase());


        }

        holder.stafffirstname.setText(stafflist.get(position).getFirstname());
        holder.stafflastname.setText(stafflist.get(position).getLastname());
        holder.staffcontact.setText(stafflist.get(position).getContact());
        holder.staffdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                staffdata.showdata(stafflist.get(position),position);
            }
        });

        holder.staffprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                staffdata.showprofiledata(stafflist.get(position),position);

            }
        });

        holder.staffwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                staffdata.showwhatsapp(stafflist.get(position),position);

            }
        });

        holder.staffcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                staffdata.showcontact(stafflist.get(position),position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return stafflist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stafffirstname,stafflastname,staffcontact,profilelogo;
        ConstraintLayout staffdata;
        ImageView staffprofileimage,staffwhatsapp,staffcall;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stafffirstname=itemView.findViewById(R.id.stafffirstname);
            stafflastname=itemView.findViewById(R.id.stafflastname);
            staffcontact=itemView.findViewById(R.id.staffmonumber);
            staffdata=itemView.findViewById(R.id.staffdata);
            staffprofileimage=itemView.findViewById(R.id.staffprofileimage);
            staffwhatsapp=itemView.findViewById(R.id.staffwhatsapp);
            staffcall=itemView.findViewById(R.id.staffcall);
            profilelogo=itemView.findViewById(R.id.profilelogo);
        }
    }
}
