package com.example.staffmanager.Activity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Utils.BaseFragment;
import com.example.Utils.NetworkUtils;
import com.example.staffmanager.Model.StaffModel;
import com.example.staffmanager.Network.APIClient;
import com.example.staffmanager.Network.ApiPlaceHolder;
import com.example.staffmanager.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StaffProfileDetails extends BaseFragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    View v;
    TextView staffcontactdetail,staffemaildetail,staffaddressdetail,staffdesignationdetaail,stafftypedetail,staffemergencycontactdetail,staffemergencynamedetail,staffnotedetail;

    Button editstaffdetails;
//    StaffModel staffModel=new StaffModel("","","","","","","","","","","");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_staff_profile_details, container, false);




        staffcontactdetail=v.findViewById(R.id.staffcontactdetail);
        staffemaildetail=v.findViewById(R.id.staffemaildetail);
        staffaddressdetail=v.findViewById(R.id.staffaddressdetail);
        staffdesignationdetaail=v.findViewById(R.id.staffdesignationdetaail);
        stafftypedetail=v.findViewById(R.id.stafftypedetail);
        staffemergencycontactdetail=v.findViewById(R.id.staffemergencycontactdetail);
        staffemergencynamedetail=v.findViewById(R.id.staffemergencynamedetail);
        staffnotedetail=v.findViewById(R.id.staffnotedetail);
        editstaffdetails=v.findViewById(R.id.editstaffdetails);


        StaffModel staffModel=(StaffModel) getArguments().getSerializable("modelspd");


        editstaffdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editstaffbottomsheet(staffModel);

            }
        });

        getstaffprofiledata(staffModel.getId());

        return v;
    }



    private void getstaffprofiledata(String id) {


        showLoading(requireContext());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("apikey", "164310030161efb88d2d888");
        jsonObject.addProperty("userid", "1");
        jsonObject.addProperty("storeid", "158");
        jsonObject.addProperty("id",id);

        Call<ResponseBody> call = APIClient.getInstance().create(ApiPlaceHolder.class).liststaffprofile(jsonObject);


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
                                JSONObject json2 = new JSONObject(json1.get("0").toString());


                                staffcontactdetail.setText(json2.getString("contact"));


                                if (json2.getString("email").matches("")){
                                    staffemaildetail.setText("--");
                                }
                                else{
                                    staffemaildetail.setText(json2.getString("email"));
                                }


                                if (json2.getString("address").matches("")){
                                    staffaddressdetail.setText("--");
                                }
                                else{
                                    staffaddressdetail.setText(json2.getString("address"));
                                }

                                if (json2.getString("designation").matches("")){
                                    staffdesignationdetaail.setText("--");
                                }
                                else{
                                    staffdesignationdetaail.setText(json2.getString("designation"));
                                }

                                if (json2.getString("emergencycontact").matches("")){
                                    staffemergencycontactdetail.setText("--");
                                }
                                else{
                                    staffemergencycontactdetail.setText(json2.getString("emergencycontact"));
                                }

                                if (json2.getString("emergencyname").matches("")){
                                    staffemergencynamedetail.setText("--");
                                }
                                else{
                                    staffemergencynamedetail.setText(json2.getString("emergencyname"));
                                }

                                if (json2.getString("note").matches("")){
                                    staffnotedetail.setText("--");
                                }
                                else{
                                    staffnotedetail.setText(json2.getString("note"));
                                }



//                                staffemaildetail.setText(json2.getString("email"));
//                                staffaddressdetail.setText(json2.getString("address"));
                                stafftypedetail.setText(json2.getString("type"));
//                                staffdesignationdetaail.setText(json2.getString("designation"));
//                                staffemergencycontactdetail.setText(json2.getString("emergencycontact"));
//                                staffemergencynamedetail.setText(json2.getString("emergencyname"));
//                                staffnotedetail.setText(json2.getString("note"));


                                hideLoading();
                            } else {

                                hideLoading();

                            }
                        } catch (JSONException e) {

                            hideLoading();

                            e.printStackTrace();

                        } catch (IOException e) {
                            hideLoading();

                            e.printStackTrace();
                        }

                    }
                } else {
                    hideLoading();

                    showSnackBarRed("Something Error!!");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoading();

                showSnackBarRed("Something Error!!");

            }
        });

    }

    String[] courses = {"Full time", "Part time","Hourly"};

    BottomSheetDialog bottomSheetDialogeditstaff;
    private void editstaffbottomsheet(StaffModel itemviewmodel) {

        bottomSheetDialogeditstaff = new BottomSheetDialog(requireContext());
        bottomSheetDialogeditstaff.setContentView(R.layout.editstaff_bottomsheet);

        EditText editstafffirstname, editstafflastname, editstaffcontact, editstaffaddress, editstaffemergencycontact, editstaffemergencyname, editstaffemail,editstaffdesignation, editstaffnote;
        Button editstaffbutton;
        Spinner editstafftype;


        editstafffirstname=bottomSheetDialogeditstaff.findViewById(R.id.editstafffirstname1);
        editstafflastname=bottomSheetDialogeditstaff.findViewById(R.id.editstafflastname1);
        editstaffcontact=bottomSheetDialogeditstaff.findViewById(R.id.editstaffcontact1);
        editstaffaddress=bottomSheetDialogeditstaff.findViewById(R.id.editstaffaddress1);
        editstaffemergencycontact=bottomSheetDialogeditstaff.findViewById(R.id.editstaffemergencynumber1);
        editstaffemergencyname=bottomSheetDialogeditstaff.findViewById(R.id.editstaffemergencyname1);
        editstaffemail=bottomSheetDialogeditstaff.findViewById(R.id.editstaffemail1);
        editstaffnote=bottomSheetDialogeditstaff.findViewById(R.id.editstaffnote1);
        editstaffdesignation=bottomSheetDialogeditstaff.findViewById(R.id.editstaffdesignation1);
        editstaffbutton=bottomSheetDialogeditstaff.findViewById(R.id.editstaffbutton);
        editstafftype=bottomSheetDialogeditstaff.findViewById(R.id.editstafftype1);

        editstafffirstname.setText(itemviewmodel.getFirstname());
        editstafflastname.setText(itemviewmodel.getLastname());
        editstaffcontact.setText(itemviewmodel.getContact());
        editstaffaddress.setText(itemviewmodel.getAddress());
        editstaffemergencycontact.setText(itemviewmodel.getEmergencycontact());
        editstaffemergencyname.setText(itemviewmodel.getEmergencyname());
        editstaffemail.setText(itemviewmodel.getEmailid());
        editstaffdesignation.setText(itemviewmodel.getDesignation());
        editstaffnote.setText(itemviewmodel.getNote());


        ArrayAdapter ad = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, courses);

        editstafftype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        editstafftype.setAdapter(ad);

        if(itemviewmodel.getStafftype().equals("Part time")){
            editstafftype.setSelection(1);
        }
        else if(itemviewmodel.getStafftype().equals("Hourly")){
            editstafftype.setSelection(2);
        }




        editstaffbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editstafffirstname.getText().toString().isEmpty()) {
                    editstafffirstname.setError("Enter First Name");
                } else if (editstafflastname.getText().toString().isEmpty()) {
                    editstafflastname.setError("Enter Last Name");
                } else if (editstaffcontact.getText().toString().isEmpty()) {
                    editstaffcontact.setError("Enter Contact Number");
                }
                else {
                    if (NetworkUtils.isNetworkConnected(requireContext())) {

                        updatestaffdata(itemviewmodel.getId()+"",
                                editstafffirstname.getText().toString() + "",
                                editstafflastname.getText().toString() + "",
                                editstaffaddress.getText().toString() + "",
                                editstaffcontact.getText().toString() + "",
                                editstaffemergencycontact.getText().toString() + "",
                                editstaffemergencyname.getText().toString() + "",
                                editstaffemail.getText().toString() + "",
                                editstaffnote.getText().toString() + "",
                                editstaffdesignation.getText().toString()+"",
                                editstafftype.getSelectedItem().toString()+""
                        );
                    }
                }


            }
        });




        bottomSheetDialogeditstaff.show();
    }


    private void updatestaffdata(String id,String editstafffirstname, String editstafflastname, String editstaffaddress, String editstaffcontact, String editstaffemergencycontact, String editstaffemergencyname, String editstaffemail, String editstaffnote,String editstaffdesignation,String editstafftype) {

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
        jsonObject.addProperty("firstname", editstafffirstname);
        jsonObject.addProperty("lastname", editstafflastname);
        jsonObject.addProperty("address", editstaffaddress);
        jsonObject.addProperty("phone", editstaffcontact);
        jsonObject.addProperty("emergencycontact", editstaffemergencycontact);
        jsonObject.addProperty("emergencycontactname", editstaffemergencyname);
        jsonObject.addProperty("email", editstaffemail);
        jsonObject.addProperty("note", editstaffnote);
        jsonObject.addProperty("designation",editstaffdesignation);
        jsonObject.addProperty("type",editstafftype);


        Call<ResponseBody> call = retrofitAPI.updatestaff(jsonObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {

                        try {
                            JSONObject json = new JSONObject(response.body().string());

                            if (json.getString("status").equals("success")) {

                                hideLoading();
                                bottomSheetDialogeditstaff.dismiss();

                                 showSnackBarGreen("Data Updated Successfully");
//                                 showSnackBarGreen(json.getString("message"));
                                getstaffprofiledata(id);



                            } else {
                                showSnackBarRed("Error1");

                                hideLoading();
                            }


                        } catch (IOException | JSONException e) {
                            showSnackBarRed("Error2");
                            hideLoading();

                        }
                    } else {
                        showSnackBarRed("Error3");
                        hideLoading();
                    }
                } else {
                    showSnackBarRed("Error4");
                    hideLoading();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showSnackBarRed("Error5");
                hideLoading();

            }
        });

    }



}