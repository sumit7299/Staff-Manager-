package com.example.staffmanager.Activity;

import static android.Manifest.permission.READ_CONTACTS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Utils.BaseFragment;
import com.example.Utils.NetworkUtils;
import com.example.staffmanager.Adapter.StaffAdapter;
import com.example.staffmanager.Model.StaffModel;
import com.example.staffmanager.Network.APIClient;
import com.example.staffmanager.Network.ApiPlaceHolder;
import com.example.staffmanager.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaffList extends BaseFragment implements StaffAdapter.Showstaffdata {

    private static final int PICK_CONTACT_REQUEST_CODE=1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View v;
    Button addstaff;
    RecyclerView staffrecyclerView;
    ArrayList<StaffModel> stafflist = new ArrayList<StaffModel>();
    StaffAdapter staffAdapter;
    ImageView emptystaffdetails,searchbutton,closebutton;
    Group searchgroup,closegroup;
    EditText searchbox;
    SwipeRefreshLayout swipeRefresh;

    static final int PICK_CONTACT=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_staff_list, container, false);

        staffrecyclerView = v.findViewById(R.id.staffrecyclerView);
        addstaff = v.findViewById(R.id.addstaff);

        emptystaffdetails=v.findViewById(R.id.emptystaffdetails);
        searchgroup=v.findViewById(R.id.searchgroup);
        closegroup=v.findViewById(R.id.closegroup);
        searchbutton=v.findViewById(R.id.searchbutton);
        closebutton=v.findViewById(R.id.closebutton);
        searchbox=v.findViewById(R.id.searchbox);
        swipeRefresh=v.findViewById(R.id.swipeRefresh);


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getstaffdata();
            }
        });

        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        getstaffdata();

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closegroup.setVisibility(View.VISIBLE);
                searchgroup.setVisibility(View.INVISIBLE);
                 }
        });

        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closegroup.setVisibility(View.INVISIBLE);
                searchgroup.setVisibility(View.VISIBLE);
                searchbox.setText("");
            }
        });


        StaffAdapter staffAdapter = new StaffAdapter(requireContext(), stafflist, StaffList.this);
        staffrecyclerView.setHasFixedSize(true);
        staffrecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        staffrecyclerView.setAdapter(staffAdapter);


        addstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                staffbottomsheet();


            }
        });



        return v;

    }






    private void filter(String text) {
        ArrayList<StaffModel> filteredlist = new ArrayList<>();
        for (StaffModel item : stafflist) {
            if (item.getFirstname().toLowerCase().contains(text.toLowerCase()) ||
                    item.getLastname().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {

            emptystaffdetails.setVisibility(View.VISIBLE);
            staffrecyclerView.setVisibility(View.INVISIBLE);

        }
        else
        {
            staffAdapter.filterList(filteredlist);
            emptystaffdetails.setVisibility(View.INVISIBLE);
            staffrecyclerView.setVisibility(View.VISIBLE);
        }
    }





    String[] courses = {"Full time", "Part time","Hourly"};

    BottomSheetDialog bottomSheetDialogaddstaff;
    EditText addstafffirstname, addstafflastname, addstaffcontact, addstaffaddress, addstaffemergencycontact, addstaffemergencycontactname, addstaffdesignation,addstaffemail, addstaffnote;
    CountryCodePicker countryCodePicker;

    String a= "+91";
    int i=0;

    private void staffbottomsheet() {

        bottomSheetDialogaddstaff = new BottomSheetDialog(requireContext());
        bottomSheetDialogaddstaff.setContentView(R.layout.addstaff_bottomsheet);

         Button addstaffbutton;
        TextView importcontact;
        Spinner addstafftype;

        countryCodePicker=bottomSheetDialogaddstaff.findViewById(R.id.countryCodePicker);
        addstafffirstname = bottomSheetDialogaddstaff.findViewById(R.id.addstafffirstname1);
        addstafflastname = bottomSheetDialogaddstaff.findViewById(R.id.addstafflastname1);
        addstaffcontact = bottomSheetDialogaddstaff.findViewById(R.id.addstaffcontact1);
        addstaffaddress = bottomSheetDialogaddstaff.findViewById(R.id.addstaffaddress1);
        addstaffemergencycontact = bottomSheetDialogaddstaff.findViewById(R.id.addstaffemergencycontact1);
        addstaffemergencycontactname = bottomSheetDialogaddstaff.findViewById(R.id.addstaffemergencyname1);
        addstaffemail = bottomSheetDialogaddstaff.findViewById(R.id.addstaffemail1);
        addstaffnote = bottomSheetDialogaddstaff.findViewById(R.id.addstaffnote1);
        addstaffbutton = bottomSheetDialogaddstaff.findViewById(R.id.addstaffbutton1);
        importcontact = bottomSheetDialogaddstaff.findViewById(R.id.importcontact);
        addstaffdesignation=bottomSheetDialogaddstaff.findViewById(R.id.addstaffdesignation1);
        addstafftype = bottomSheetDialogaddstaff.findViewById(R.id.addstafftype1);


        ArrayAdapter ad = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, courses);

        addstafftype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addstafftype.setAdapter(ad);



        importcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               checkpermission();

            }
        });

        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {

                a="+"+countryCodePicker.getSelectedCountryCode().toString();
//                Toast.makeText(getContext(), "Selected : " + countryCodePicker.getSelectedCountryName(), Toast.LENGTH_SHORT).show();

            }
        });





        addstaffbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addstafffirstname.getText().toString().isEmpty()) {
                    addstafffirstname.setError("Enter First Name");
                }  if (addstafflastname.getText().toString().isEmpty()) {
                    addstafflastname.setError("Enter Last Name");
                }  if (addstaffcontact.getText().toString().isEmpty()) {
                    addstaffcontact.setError("Enter Contact Number");
                }

                if (
                       !addstafffirstname.getText().toString().isEmpty() &&
                                !addstafflastname.getText().toString().isEmpty()  &&
                                !addstaffcontact.getText().toString().isEmpty()

                ) {
                    if (NetworkUtils.isNetworkConnected(requireContext())) {
//                        Toast.makeText(getContext(), a+addstaffcontact.getText().toString(), Toast.LENGTH_SHORT).show();
                        insertstaffdata(
                                addstafffirstname.getText().toString() + "",
                                addstafflastname.getText().toString() + "",
                                addstaffaddress.getText().toString() + "",
                                i==0?
                                        a+addstaffcontact.getText().toString() + ""
                                        :addstaffcontact.getText().toString() + "",
                                addstaffemergencycontact.getText().toString() + "",
                                addstaffemergencycontactname.getText().toString() + "",
                                addstaffemail.getText().toString() + "",
                                addstaffdesignation.getText().toString() + "",
                                addstafftype.getSelectedItem().toString() + "",
                                addstaffnote.getText().toString() + ""
                        );
                    }
                }
            }
        });

        bottomSheetDialogaddstaff.show();
    }


    private void pickContact() {
     }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST_CODE && data != null) {

             Uri uri1 =data.getData();
            String[] projection = {
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            };

            Cursor cursor = requireContext().getApplicationContext().getContentResolver().query(uri1, projection,
                    null, null, null);
            cursor.moveToFirst();

            int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = cursor.getString(numberColumnIndex);

            int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String name = cursor.getString(nameColumnIndex);

            addstaffcontact.setText(number);
            addstafffirstname.setText(name);
            i=1;
            cursor.close();

        }
    }

    public void checkpermission() {
        Dexter.withContext(getContext())
                .withPermission(READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                        Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                        startActivityForResult(pickContact, PICK_CONTACT_REQUEST_CODE);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        showSettingsDialog();

                     }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();

                    }
                }).onSameThread().check();


    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Contact Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");

        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);

                intent.setData(uri);

                startActivityForResult(intent, 101);

            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }

        });
        builder.show();

    }
    //    insert staff data
    private void insertstaffdata(String addstafffirstname, String addstafflastname, String addstaffaddress, String addstaffcontact, String addstaffemergencycontact, String addstaffemergencycontactname, String addstaffemail,String addstaffdesignation,String addstafftype, String addstaffnote) {


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
        jsonObject.addProperty("firstname", addstafffirstname);
        jsonObject.addProperty("lastname", addstafflastname);
        jsonObject.addProperty("address", addstaffaddress);
        jsonObject.addProperty("phone", addstaffcontact);
        jsonObject.addProperty("emergencycontact", addstaffemergencycontact);
        jsonObject.addProperty("emergencycontactname", addstaffemergencycontactname);
        jsonObject.addProperty("email", addstaffemail);
        jsonObject.addProperty("designation",addstaffdesignation);
        jsonObject.addProperty("note", addstaffnote);
        jsonObject.addProperty("type",addstafftype);

        Call<ResponseBody> call = retrofitAPI.addstaff(jsonObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {

                        try {

                            JSONObject jsonObject1 = new JSONObject(response.body().string());
                            showSnackBarGreen(jsonObject1.getString("status").toString());
                            hideLoading();
                            getstaffdata();
                            bottomSheetDialogaddstaff.dismiss();



                        } catch (Exception e) {
                            showSnackBarRed("Catch");
                        }
                    } else {
                        showSnackBarRed("Something Error");
                        hideLoading();
                    }
                } else {
                    showSnackBarRed("Something Error");
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showSnackBarRed("Something Error");
                hideLoading();
            }
        });
    }


//    get(List) Staff Data

    private void getstaffdata() {


        showLoading(requireContext());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("apikey", "164310030161efb88d2d888");
        jsonObject.addProperty("userid", "1");
        jsonObject.addProperty("storeid", "158");

        Call<ResponseBody> call = APIClient.getInstance().create(ApiPlaceHolder.class).liststaff(jsonObject);


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

                                stafflist.clear();
                                while (x.hasNext()) {
                                    String key = (String) x.next();

                                    JSONObject json2 = new JSONObject(json1.get(key).toString());
                                    stafflist.add(new StaffModel
                                            (
                                                    json2.get("id") + "",
                                                    json2.get("profileimage")+"",
                                                    json2.get("firstname") + "",
                                                    json2.get("lastname") + "",
                                                    json2.get("contact") + "",
                                                    json2.get("address") + "",
                                                    json2.get("emergencycontact") + "",
                                                    json2.get("emergencyname") + "",
                                                    json2.get("designation")+"",
                                                    json2.get("email")+"",
                                                    json2.get("note")+"",
                                                    json2.get("type")+""

                                            ));
                                }


                                swipeRefresh.setRefreshing(false);
                                hideLoading();
                                emptystaffdetails.setVisibility(View.INVISIBLE);
                                staffrecyclerView.setVisibility(View.VISIBLE);

                                staffAdapter = new StaffAdapter(requireContext(), stafflist,StaffList.this);
                                staffrecyclerView.setAdapter(staffAdapter);

                            } else {

                                hideLoading();
                                swipeRefresh.setRefreshing(false);
                                emptystaffdetails.setVisibility(View.VISIBLE);
                                staffrecyclerView.setVisibility(View.INVISIBLE);



                            }
                        } catch (JSONException e) {

                            hideLoading();
                            emptystaffdetails.setVisibility(View.VISIBLE);
                            staffrecyclerView.setVisibility(View.INVISIBLE);
                            swipeRefresh.setRefreshing(false);

                            e.printStackTrace();

                        } catch (IOException e) {
                            hideLoading();
                            emptystaffdetails.setVisibility(View.VISIBLE);
                            staffrecyclerView.setVisibility(View.INVISIBLE);
                            swipeRefresh.setRefreshing(false);

                            e.printStackTrace();
                        }

                    }
                } else {
                    hideLoading();
                    swipeRefresh.setRefreshing(false);

                    showSnackBarRed("Something Error!!");

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoading();
                swipeRefresh.setRefreshing(false);

                showSnackBarRed("Something Error!!");

            }
        });

    }




    @Override
    public void showdata(StaffModel itemviewmodel, int pos) {

//        editstaffbottomsheet(itemviewmodel);
        loadFragment(itemviewmodel,new StaffProfile());

    }

    @Override
    public void showprofiledata(StaffModel itemviewmodel, int pos) {

        loadFragment(itemviewmodel,new StaffProfile());

    }

    @Override
    public void showwhatsapp(StaffModel itemviewmodel, int pos) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "+91" + itemviewmodel.getContact() + "&text=" + "message"));

        try {
            startActivity(intent);

        }catch (Exception e){

        }

    }

    @Override
    public void showcontact(StaffModel itemviewmodel, int pos) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+itemviewmodel.getContact()));

        try {
            startActivity(intent);

        }catch (Exception e){

        }

    }



    public void loadFragment(StaffModel staffModel,Fragment fragment) {

        if (getContext() != null) {

            Bundle args = new Bundle();
            args.putSerializable("model", staffModel);
            fragment.setArguments(args);


            FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frm, fragment);
             transaction.addToBackStack(null);
            transaction.commit();

        }
    }


}