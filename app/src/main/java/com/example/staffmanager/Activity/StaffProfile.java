package com.example.staffmanager.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Utils.BaseFragment;
import com.example.staffmanager.Adapter.ViewPagerAdapter;
import com.example.staffmanager.Model.StaffModel;
import com.example.staffmanager.Network.ApiPlaceHolder;
import com.example.staffmanager.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StaffProfile extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    View view;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    TextView stafffirstnamedetails, stafflastnamedetails,staffprofilelogo;
    ImageView deletestaffdetails, backstaffscreen, staffprofilewhatsapp, staffprofilecall, staffprofileshare, staffprofile;

//    StaffModel staffModel = (StaffModel) getArguments().getSerializable("model");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_staff_profile, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tab);
        deletestaffdetails = view.findViewById(R.id.deletestaffdetails);
        backstaffscreen = view.findViewById(R.id.backstaffscreen);
        staffprofilewhatsapp = view.findViewById(R.id.staffprofilewhatsapp);
        staffprofilecall = view.findViewById(R.id.staffprofilecall);
        staffprofileshare = view.findViewById(R.id.staffprofileshare);
        stafffirstnamedetails = view.findViewById(R.id.stafffirstnamedetails);
        stafflastnamedetails = view.findViewById(R.id.stafflastnamedetails);
        staffprofile = view.findViewById(R.id.staffprofile);
        staffprofilelogo=view.findViewById(R.id.staffprofilelogo);


        StaffModel staffModel = (StaffModel) getArguments().getSerializable("model");

        Fragment fragmentspd = new StaffProfileDetails();
        Bundle args = new Bundle();
        args.putSerializable("modelspd", staffModel);
        fragmentspd.setArguments(args);

        Fragment fragmentsa = new StaffAttendance();
        Bundle args1 = new Bundle();
        args1.putSerializable("modelsa", staffModel);
        fragmentsa.setArguments(args1);

        Fragment fragmentss = new StaffSalary();
        Bundle args2 = new Bundle();
        args2.putSerializable("modelss", staffModel);
        fragmentss.setArguments(args2);


        tabLayout.setupWithViewPager(viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.add(fragmentspd, "Profile");
        viewPagerAdapter.add(fragmentsa, "Attendance");
        viewPagerAdapter.add(fragmentss, "Salary");
        viewPager.setAdapter(viewPagerAdapter);


        stafffirstnamedetails.setText(staffModel.getFirstname());
        stafflastnamedetails.setText(staffModel.getLastname());
        staffprofilecall(staffModel.getContact());



        if (staffModel.getProfileimage().matches(""))
              {
                  staffprofilelogo.setVisibility(View.INVISIBLE);
                  String uri = staffModel.getProfileimage();
                  Toast.makeText(getContext(), uri+"", Toast.LENGTH_SHORT).show();
                  Picasso.get()
                          .load(uri)
                          .noFade()
                          .into(staffprofile);

        }
        else
        {
            staffprofilelogo.setVisibility(View.VISIBLE);
            staffprofilelogo.setText(staffModel.getFirstname().substring(0,1).toUpperCase());


        }



        staffprofileshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                String text="Name : "+staffModel.getFirstname()+" "+staffModel.getLastname();
                text+="\nContact : "+staffModel.getContact();
                intent.putExtra(Intent.EXTRA_TEXT,text);
                intent.setType("text/plain");
                intent=Intent.createChooser(intent,"Share By");
                startActivity(intent);
            }
        });



        deletestaffdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                deletestaffdata(staffModel.getId());
//
                deletedialog(staffModel.getId());
            }
        });

        backstaffscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();

                fragmentManager.popBackStack();
            }
        });

        return view;
    }



    private void deletedialog(String id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Delete Staff");
        alert.setMessage("Are you sure you want to delete?");


        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                deletestaffdata(id);
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // close dialog
                dialog.cancel();
            }
        });
        alert.show();
    }





    private void staffprofilecall(String contact) {
        staffprofilewhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="  + contact + "&text=" + "message"));

                try {
                    startActivity(intent);

                } catch (Exception e) {

                }

            }
        });

        staffprofilecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:" + contact));

                try {
                    startActivity(intent);

                } catch (Exception e) {

                }
            }
        });

    }


//    Delete Staff Data

    private void deletestaffdata(String id) {

        showLoading(requireContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pidu.in/mybiz/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiPlaceHolder retrofitAPI = retrofit.create(ApiPlaceHolder.class);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("apikey", "164310030161efb88d2d888");
        jsonObject.addProperty("userid", "1");
        jsonObject.addProperty("storeid", "158");
        jsonObject.addProperty("id", id);

        Call<ResponseBody> call = retrofitAPI.deletestaff(jsonObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {

                        try {
                            JSONObject json = new JSONObject(response.body().string());
//                            JSONObject json1 = new JSONObject(json.get("data").toString());
//                            JSONObject json2 = new JSONObject(json1.get("0").toString());

                            if (json.getString("status").equals("success")) {

//                                    PlansModel model1 = new PlansModel(id,editpackagename,editdays,editduration,editamount,editnotes);
//                                    list.set(pos, model1);
//                                    plansadapter.notifyDataSetChanged();


                                hideLoading();
                                showSnackBarGreen("Data Deleted Successfully");

                                FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();

                                fragmentManager.popBackStack();


                            } else {
                                hideLoading();
                                Toast.makeText(requireContext(), "Error1", Toast.LENGTH_SHORT).show();
                            }


                        } catch (IOException | JSONException e) {
                            hideLoading();
                            Toast.makeText(requireContext(), "Error2", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        hideLoading();
                        Toast.makeText(requireContext(), "Error3", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    hideLoading();
                    Toast.makeText(requireContext(), "Error4", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoading();
                Toast.makeText(requireContext(), "Error5", Toast.LENGTH_SHORT).show();


            }
        });
    }


}