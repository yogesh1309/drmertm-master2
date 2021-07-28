package com.drmertm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;

import com.drmertm.ModelClass.LogoutResponse;
import com.drmertm.ModelClass.UserDetailResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDashboard extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ImageView action_menu;
    LinearLayout lldashboard, llidcheckstatus,llidcardstatus,llservicerec, llofford, llconrep, llform16, llgrievance, llleavesection, lllogout, llyoutube, llprofile, llqb;
    LinearLayout llsubgrievance, llsubleave, llidcard, layout_apar;
    TextView txtappleave, txtmyleave, txtlodgri, txtmygri, txt_empnamedash, txt_designationdash, txt_stationdash, txt_hrmsiddash;

    ImageView imggir, imgleave;

    Boolean isgri = true;
    Boolean isleave = true;
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;
    TextView txt_empno, txt_empname, text1;
    ImageView img_profile;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(ActivityDashboard.this);
        mAPIService = ApiUtils.getAPIService();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ActivityDashboard.this));

        InitView();
        Click();
        draweritemonclick();

        if (Utils.isInternetConnected(ActivityDashboard.this)) {
            sendUserDetail(sessionManager.getSavedEmpno()); // Last step. Logout function
        } else {
            CustomAlertdialog.createDialog(ActivityDashboard.this, getString(R.string.no_internet));
        }

    }

    private void Click() {

        action_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {

                    mDrawerLayout.closeDrawer(Gravity.LEFT);


                } else {

                    mDrawerLayout.openDrawer(Gravity.LEFT);

                }
            }
        });


        llgrievance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDashboard.this, ActivityGrievance.class);
                startActivity(intent);
            }
        });

        llleavesection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(ActivityDashboard.this, "Coming Soon.\nजल्द आ रहा है।");
//                Toast.makeText(ActivityDashboard.this, "Coming Soon.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ActivityDashboard.this,ActivityLeave.class);
//                startActivity(intent);
            }
        });

        /*
        txtlodgri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityDashboard.this,ActivityLodgeGri.class);
                startActivity(intent);
            }
        });

        txtmygri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDashboard.this,ActivityMyGrievance.class);
                startActivity(intent);
            }
        });


        txtappleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityDashboard.this,ActivityApplyForLeave.class);
                startActivity(intent);
            }
        });

        txtmyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDashboard.this,ActivityMyLeaveReport.class);
                startActivity(intent);
            }
        });

         */

        llform16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                createDialog(ActivityDashboard.this, "Coming Soon.\nजल्द आ रहा है।");
//                Toast.makeText(ActivityDashboard.this, "Coming Soon.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityDashboard.this, ActivityForm16.class);
                startActivity(intent);
            }
        });
        llidcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(ActivityDashboard.this)) {
                    sendUserGetServiceStatus(); // Last step. Logout function
                } else {
                    CustomAlertdialog.createDialog(ActivityDashboard.this, getString(R.string.no_internet));
                }

                //createDialog(ActivityDashboard.this, "Coming Soon.\nजल्द आ रहा है।");
//                Toast.makeText(ActivityDashboard.this, "Coming Soon.", Toast.LENGTH_SHORT).show();
            }
        });

        /*
         llconrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityDashboard.this, "Coming Soon.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ActivityDashboard.this,ActivityConfidentialReport.class);
//                startActivity(intent);
            }
        });
         */


        llservicerec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                createDialog(ActivityDashboard.this, "Coming Soon.\nजल्द आ रहा है।");
//                Toast.makeText(ActivityDashboard.this, "Coming Soon.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ActivityDashboard.this,ActivityServiceRecord.class);
//                startActivity(intent);
                try {
//                  Intent launchIntent = getPackageManager().getLaunchIntentForPackage("https://play.google.com/store/apps/details?id=cris.org.in.ress");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=in.co.org.cris.hrmsMobileApplication.free"));
//                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=cris.org.in.ress"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        lldashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityDashboard.this, ActivityDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        llidcheckstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDashboard.this, IdCardStatusActivity.class);
                startActivity(intent);
            }
        });
        /*
         llqb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ActivityDashboard.this, "Coming Soon.", Toast.LENGTH_SHORT).show();
            }
        });


         */

        llyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createDialog(ActivityDashboard.this, "Coming Soon.\nजल्द आ रहा है।");
                try {
//                  Intent launchIntent = getPackageManager().getLaunchIntentForPackage("https://play.google.com/store/apps/details?id=cris.org.in.ress");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCXNe5nQHLcMUAHbAvbkOJlg"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        layout_apar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(ActivityDashboard.this, "Coming Soon.", Toast.LENGTH_SHORT).show();
//                createDialog(ActivityDashboard.this, "Coming Soon.\nजल्द आ रहा है।");
                Intent intent = new Intent(ActivityDashboard.this, ActivityConfidentialReport.class);
                startActivity(intent);
            }
        });

        llofford.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(ActivityDashboard.this, "Coming Soon.", Toast.LENGTH_SHORT).show();
//                createDialog(ActivityDashboard.this, "Coming Soon.\nजल्द आ रहा है।");

                Intent intent = new Intent(ActivityDashboard.this, ActivityOfficeOrder.class);
                startActivity(intent);
            }
        });


        llprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDashboard.this, ActivityProfile.class);
                startActivity(intent);
            }
        });

        llidcardstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDashboard.this, IdCardStatusActivity.class);
                startActivity(intent);
            }
        });

    }

    private void InitView() {


        txt = findViewById(R.id.text);
        txt.setSelected(true);

        txt_empno = (TextView) findViewById(R.id.txt_empno);

        text1 = (TextView) findViewById(R.id.text1);
        text1.setSelected(true);

        txt_empname = (TextView) findViewById(R.id.txt_empname);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        action_menu = (ImageView) findViewById(R.id.action_menu);
        lldashboard = (LinearLayout) findViewById(R.id.lldashboard);
        llprofile = (LinearLayout) findViewById(R.id.llprofile);
//        llqb=(LinearLayout) findViewById(R.id.llqb);
        llservicerec = (LinearLayout) findViewById(R.id.llservicerec);
        llofford = (LinearLayout) findViewById(R.id.llofford);
//        llconrep=(LinearLayout) findViewById(R.id.llconrep);
        llform16 = (LinearLayout) findViewById(R.id.llform16);
        llgrievance = (LinearLayout) findViewById(R.id.llgrievance);
        llleavesection = (LinearLayout) findViewById(R.id.llleavesection);
        llsubleave = (LinearLayout) findViewById(R.id.llsubleave);
        llsubgrievance = (LinearLayout) findViewById(R.id.llsubgrievance);
        lllogout = (LinearLayout) findViewById(R.id.lllogout);
        llyoutube = (LinearLayout) findViewById(R.id.layout_youtube);
        llidcard = (LinearLayout) findViewById(R.id.llidcard);
        layout_apar = (LinearLayout) findViewById(R.id.layout_apar);
        img_profile = (ImageView) findViewById(R.id.img_profile);
        llidcheckstatus = findViewById(R.id.llidcheckstatus);

        txtmyleave = (TextView) findViewById(R.id.txtmyleave);
        txtappleave = (TextView) findViewById(R.id.txtappleave);
        txtlodgri = (TextView) findViewById(R.id.txtlodgri);
        txtmygri = (TextView) findViewById(R.id.txtmygri);
        txt_stationdash = (TextView) findViewById(R.id.txt_stationdash);
        txt_hrmsiddash = (TextView) findViewById(R.id.txt_hrmsiddash);
        txt_empnamedash = (TextView) findViewById(R.id.txt_empnamedash);
        txt_designationdash = (TextView) findViewById(R.id.txt_designationdash);
        imggir = (ImageView) findViewById(R.id.imggir);
        imgleave = (ImageView) findViewById(R.id.imgleave);
        llidcardstatus =  findViewById(R.id.llidcardstatus);

    }

    private void draweritemonclick() {

        llsubgrievance.setVisibility(View.GONE);
        llsubleave.setVisibility(View.GONE);

        /*
        llgrievance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isgri==true){
                    isgri=false;
                    imggir.setImageDrawable(getResources().getDrawable(R.mipmap.downarrow));
                    llsubgrievance.setVisibility(View.VISIBLE);


                }else {
                    isgri=true;
                    imggir.setImageDrawable(getResources().getDrawable(R.mipmap.rightarrow));
                    llsubgrievance.setVisibility(View.GONE);
                }

            }
        });

        llleavesection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isleave==true){
                    isleave=false;
                    imgleave.setImageDrawable(getResources().getDrawable(R.mipmap.downarrow));
                    llsubleave.setVisibility(View.VISIBLE);


                }else {
                    isleave=true;
                    imgleave.setImageDrawable(getResources().getDrawable(R.mipmap.rightarrow));
                    llsubleave.setVisibility(View.GONE);
                }

            }
        });

         */


        lllogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Intent intent = new Intent(ActivityDashboard.this, ActivityLogin.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
                showPopup();
            }
        });

    }

    // first step helper function
    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityDashboard.this);

        alert.setMessage("Are you sure?").setPositiveButton(Html.fromHtml("<font color='#000000'>Yes</font>"), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                if (Utils.isInternetConnected(ActivityDashboard.this)) {
                    sendPost(sessionManager.getSavedFcmTokenId()); // Last step. Logout function
                } else {
                    CustomAlertdialog.createDialog(ActivityDashboard.this, getString(R.string.no_internet));
                }


            }
        }).setNegativeButton(Html.fromHtml("<font color='#000000'>No</font>"), null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    public void sendPost(Integer fcmtokenid) {
        mAPIService.logout(fcmtokenid).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {

                if (response.isSuccessful()) {
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
//                    response.body().getMessage();
//                    response.body().getStatus();
//                    response.body().getData();
                    System.out.println("Status >>>>>>>>>>>>" + response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>" + response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status == true) {

                        sessionManager.clearSession();
                        Intent intent = new Intent(ActivityDashboard.this, ActivityLogin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(ActivityDashboard.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendUserDetail(String fcmtokenid) {

        try {
            mAPIService.userdetail(fcmtokenid).enqueue(new Callback<UserDetailResponse>() {
                @Override
                public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response) {

                    if (response.isSuccessful()) {
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
//                    response.body().getMessage();
//                    response.body().getStatus();
//                    response.body().getData();
                        System.out.println("Status >>>>>>>>>>>>" + response.body().getStatus());
                        System.out.println("Message >>>>>>>>>>>>" + response.body().getMessage());

                        boolean status = response.body().getStatus();

                        if (status == true) {

                            String empname = response.body().getData().getEMPNAME();
                            String empno = response.body().getData().getEMPNO();
                            String empdesig = response.body().getData().getDesignationName();
                            String empstation = response.body().getData().getSTATIONCODE();
                            String image = response.body().getData().getImage();
                            String HRMSiD = response.body().getData().getHRMSID();
                            String marq1 = response.body().getData().getMarqueText1();
                            String marq2 = response.body().getData().getMarqueText2();

                            txt_empname.setText(empname);
                            txt_empno.setText(empno);
                            txt_empnamedash.setText(empname);
                            txt_designationdash.setText(empdesig);
                            txt_stationdash.setText(empstation);
                            txt_hrmsiddash.setText(HRMSiD);
                            text1.setText(marq1);
                            txt.setText(marq2);

                            String s_image = Allurls.ImageURL + image;

                            if (s_image != null && !s_image.equalsIgnoreCase("")) {
                                s_image = s_image.replaceAll("\\\\", "");

                                System.out.println("imageUrlimageUrlimageUrl" + s_image);
                                ImageLoader.getInstance().displayImage(s_image, img_profile);
                            }
                        } else {
                            Toast.makeText(ActivityDashboard.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<UserDetailResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void createDialog(final Context context, final String message) {
        final Dialog dialog = new Dialog(context);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView text = (TextView) dialog.findViewById(R.id.content);
        text.setText(message);

        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    public void sendUserGetServiceStatus() {

        try {
            Customprogress.showPopupProgressSpinner(ActivityDashboard.this,true);

            mAPIService.getServicestatus().enqueue(new Callback<LogoutResponse>() {
                @Override
                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                    Customprogress.showPopupProgressSpinner(ActivityDashboard.this,false);

                    if (response.isSuccessful()) {
                        boolean status = response.body().getStatus();

                        if (status == true)
                        {
                            Intent intent = new Intent(ActivityDashboard.this, IDCardActivity.class);
                            startActivity(intent);
                        } else {
                            createDialog(ActivityDashboard.this, "Under Maintenance, Try After Sometime.");
//                            Toast.makeText(ActivityDashboard.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                    Customprogress.showPopupProgressSpinner(ActivityDashboard.this,false);

//                Log.e(TAG, "Unable to submit post to API.");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
