package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.IdCardStatusResponse;
import com.drmertm.ModelClass.LoginResponse;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdCardStatusActivity extends AppCompatActivity {

    ImageView img_back;
    SessionManager sessionManager;
    Context context;
    private JsonPlaceHolderApi mAPIService;
    ImageView dot1,dot2,dot3,img1,img2,img3;
    View view1,view2;
    TextView txt1,txt2,txt3,txt_html,txt_viewidcard,txt_rejectedreason;
    LinearLayout layout_rejectedres;
    String cardId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card_status);
        InitView();
        Click();
        if(Utils.isInternetConnected(context)) {

            sendIdCardStatus();
        }
        else {
            CustomAlertdialog.createDialog(context,getString(R.string.no_internet));
        }
    }

    private void Click()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_viewidcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    byte[] data = cardId.getBytes("UTF-8");
                    String cardIdbase64 = Base64.encodeToString(data, Base64.DEFAULT);

                    byte[] data2 = sessionManager.getSavedEmpno().getBytes("UTF-8");
                    String Empnobase64 = Base64.encodeToString(data2, Base64.DEFAULT);

                    String url = Allurls.MainURL+"webview_idcard?idcard_id="+cardIdbase64+"&empno="+Empnobase64;
                    System.out.println("IdCardStatusActivity url >>>>>>>>>"+url);
               /* Intent intent = new Intent(context,WVIdCardActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);*/
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void InitView() {
        context = this;
        sessionManager = new SessionManager(context);
        mAPIService = ApiUtils.getAPIService();
        img_back = findViewById(R.id.img_back);
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt_html = findViewById(R.id.txt_html);
        txt_viewidcard = findViewById(R.id.txt_viewidcard);
        txt_rejectedreason = findViewById(R.id.txt_rejectedreason);
        layout_rejectedres = findViewById(R.id.layout_rejectedres);
    }

    public void sendIdCardStatus() {
        Customprogress.showPopupProgressSpinner(context,true);
        mAPIService.idcardstatus(sessionManager.getSavedEmpno()).enqueue(new Callback<IdCardStatusResponse>() {
            @Override
            public void onResponse(Call<IdCardStatusResponse> call, Response<IdCardStatusResponse> response) {

                Customprogress.showPopupProgressSpinner(context,false);

                if(response.isSuccessful()) {

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {
                        cardId = response.body().getData().getId();
                        txt_html.setText(Html.fromHtml(response.body().getData().getStatusMessage()));
                        String submitstatus = response.body().getData().getSubmitStatus();
                        //submitstatus = 0,1 means green and submitstatus = other means grey
                        if (submitstatus.equals("0")||submitstatus.equals("1"))
                        {
                            txt1.setTextColor(getResources().getColor(R.color.darkbluecolour));
                            view1.setBackgroundColor(getResources().getColor(R.color.darkbluecolour));
                        }
                        else {

                            txt1.setTextColor(getResources().getColor(R.color.light_grey));
                            view1.setBackgroundColor(getResources().getColor(R.color.light_grey));
                        }

                        String clerk_status = response.body().getData().getClerkStatus();
                        //clerk_status = 1 means green and clerk_status = 0 means grey
                        if (clerk_status.equals("1"))
                        {
                            txt2.setTextColor(getResources().getColor(R.color.darkbluecolour));
                            view2.setBackgroundColor(getResources().getColor(R.color.darkbluecolour));
                        }
                        else {
                            view2.setBackgroundColor(getResources().getColor(R.color.light_grey));
                            txt2.setTextColor(getResources().getColor(R.color.light_grey));
                        }

                        String id_status = response.body().getData().getIdStatus();
                        //id_status = 1 means green and id_status = 0 means grey
                        if (id_status.equals("1"))
                        {
                            txt3.setTextColor(getResources().getColor(R.color.darkbluecolour));
                            txt_viewidcard.setVisibility(View.VISIBLE);
                        }
                        else {

                            txt3.setTextColor(getResources().getColor(R.color.light_grey));
                            txt_viewidcard.setVisibility(View.GONE);
                        }

                        String rejectedreason = response.body().getData().getRejectReason();
                        if (rejectedreason.equals(""))
                        {
                            layout_rejectedres.setVisibility(View.GONE);
                        }
                        else {
                            layout_rejectedres.setVisibility(View.VISIBLE);
                            txt_rejectedreason.setText(rejectedreason);
                        }

                    }
                    else
                    {
                        Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<IdCardStatusResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

}