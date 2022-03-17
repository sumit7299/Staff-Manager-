package com.example.staffmanager.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Utils.BaseFragment;
import com.example.staffmanager.Adapter.AttendanceAdapter;
import com.example.staffmanager.Adapter.SalaryAdapter;
import com.example.staffmanager.Model.AttendanceModel;
import com.example.staffmanager.Model.SalaryModel;
import com.example.staffmanager.Model.StaffModel;
import com.example.staffmanager.Network.APIClient;
import com.example.staffmanager.Network.ApiPlaceHolder;
import com.example.staffmanager.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StaffAttendance extends BaseFragment {

    public static StaffAttendance newInstance(String param1, String param2) {
        StaffAttendance fragment = new StaffAttendance();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    View v;
    ArrayList<AttendanceModel> attendancelist = new ArrayList<AttendanceModel>();
    RecyclerView attendancerecyclerview;
    AttendanceAdapter attendanceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_staff_attendance, container, false);
        attendancerecyclerview=v.findViewById(R.id.attendancerecyclerview);


//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));
//        attendancelist.add(new AttendanceModel("Feb","2022","23","2"));

        StaffModel staffModel=(StaffModel) getArguments().getSerializable("modelsa");


        getstaffattendancedata(staffModel.getId());


        AttendanceAdapter attendanceAdapter=new AttendanceAdapter(requireContext(),attendancelist);
        attendancerecyclerview.setHasFixedSize(true);
        attendancerecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        attendancerecyclerview.setAdapter(attendanceAdapter);




        return v;
    }

    private void getstaffattendancedata(String id) {


        showLoading(requireContext());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("apikey", "164310030161efb88d2d888");
        jsonObject.addProperty("userid", "1");
        jsonObject.addProperty("storeid", "158");
        jsonObject.addProperty("id",id);

        Call<ResponseBody> call = APIClient.getInstance().create(ApiPlaceHolder.class).liststaffattendance(jsonObject);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        // json = null;
                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            if (json.getString("status").equals("success")) {


                                JSONObject json1 = new JSONObject(json.get("data").toString());
                                Iterator x = json1.keys();
                                //  GetStoreModel[] storeModels = new GetStoreModel[json1.length()];

                                attendancelist.clear();
                                while (x.hasNext()) {
                                    String key = (String) x.next();

                                    JSONObject json2 = new JSONObject(json1.get(key).toString());
                                    attendancelist.add(new AttendanceModel

                                            (
                                                    json2.get("month") + "",
                                                    json2.get("salarymonth") + "",
                                                    json2.get("salaryyear") + "",
                                                    json2.get("checkins") + "",
                                                    json2.get("leaves") + ""
                                            ));
                                }
//                                swiperefresh.setRefreshing(false);
//                                emptyplanimage.setVisibility(View.INVISIBLE);
//                                recyclerView.setVisibility(View.VISIBLE);
                                hideLoading();

                                attendanceAdapter = new AttendanceAdapter(requireContext(), attendancelist);
                                attendancerecyclerview.setAdapter(attendanceAdapter);

                            } else {


//                                emptyplanimage.setVisibility(View.VISIBLE);
//                                recyclerView.setVisibility(View.INVISIBLE);

                                hideLoading();
//                                swiperefresh.setRefreshing(false);

                            }
                        } catch (JSONException e) {
                            hideLoading();
//                            emptyplanimage.setVisibility(View.VISIBLE);
//                            recyclerView.setVisibility(View.INVISIBLE);
//                            swiperefresh.setRefreshing(false);

                            e.printStackTrace();

                        } catch (IOException e) {
                            hideLoading();
//                            emptyplanimage.setVisibility(View.VISIBLE);
//                            recyclerView.setVisibility(View.INVISIBLE);
//                            swiperefresh.setRefreshing(false);

                            e.printStackTrace();
                        }

                    }
                } else {
                    hideLoading();
//                    swiperefresh.setRefreshing(false);

                    showSnackBarRed("Something Error!!");

//                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoading();
//                swiperefresh.setRefreshing(false);

                showSnackBarRed("Something Error!!");

//                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}