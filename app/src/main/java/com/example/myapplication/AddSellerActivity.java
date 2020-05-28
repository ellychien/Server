package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.SendDataToServer.Seller;
import com.example.myapplication.SendDataToServer.ApiClient;
import com.example.myapplication.SendDataToServer.ApiInterface;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.Constants.BaseUrlAdd;

public class AddSellerActivity extends BaseActivity {

    @BindView(R.id.userregis)
    EditText userregis;

    @BindView(R.id.passregis)
    EditText passregis;

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.sdt)
    EditText sdt;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.place_seller)
    EditText placeseller;

    @BindView(R.id.registrationuser)
    Button regis;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_add_seller;
    }

    @Override
    protected void setupListener() {
        Regis();
    }

    @Override
    protected void populateData() {
    HideTitle();
    }

    private void Regis()
    {
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisSeller();
            }
        });
    }

    private void RegisSeller() {

        String username = userregis.getText().toString();
        String password = passregis.getText().toString();
        String na = name.getText().toString();
        int phone = Integer.parseInt(sdt.getText().toString());
        String mail = email.getText().toString();
        String place = placeseller.getText().toString();

        ApiInterface apiInterface = ApiClient.getApiClient(BaseUrlAdd).create(ApiInterface.class);
        Call<Seller> call = apiInterface.addseller( username,password,na,phone,mail,place);

        call.enqueue(new Callback<Seller>() {
            @Override
            public void onResponse(Call<Seller> call, Response<Seller> response) {

                Seller seller = response.body();
                Log.d("Server Response",""+ seller.getResponse());
                if(seller.getResponse().equals("Successfully"))
                {

                    Toast.makeText(AddSellerActivity.this,"Đăng kí thàng công",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(AddSellerActivity.this,"Tài khoản này đã tồn tại",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Seller> call, Throwable t) {
                Log.d("Server Response",""+t.toString());
            }
        });

        userregis.setText("");
        passregis.setText("");
        name.setText("");
        sdt.setText("");
        email.setText("");
        placeseller.setText("");

    }
}
