package com.example.staffmanager.Activity;

import static java.security.AccessController.getContext;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.Utils.BaseActivity;
import com.example.Utils.NetworkUtils;
import com.example.staffmanager.Adapter.StaffAdapter;
import com.example.staffmanager.Model.StaffModel;
import com.example.staffmanager.Network.APIClient;
import com.example.staffmanager.Network.ApiPlaceHolder;
import com.example.staffmanager.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity  {

    Button addstaff;
    RecyclerView staffrecyclerView;
    ArrayList<StaffModel> stafflist = new ArrayList<StaffModel>();
    StaffAdapter staffAdapter;

    FrameLayout frm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frm=findViewById(R.id.frm);
        loadFragment(new StaffList());




    }



    public void loadFragment(Fragment fragment) {

        if (getContext() != null) {


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frm, fragment);
            transaction.commit();

        }
    }
}