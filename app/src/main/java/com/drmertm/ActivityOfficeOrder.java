package com.drmertm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.Adapter.PeopleAdapter;
import com.drmertm.ModelClass.GrievencePOJO;
import com.drmertm.ModelClass.ListPopupItem;
import com.drmertm.ModelClass.MyGrievanceApplyLISTResponse;
import com.drmertm.ModelClass.ViewOfficeOrderData;
import com.drmertm.ModelClass.ViewOfficeOrderResponse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityOfficeOrder extends AppCompatActivity {

    ImageView img_back,img_filter;
    List<ViewOfficeOrderData> list;
    RecyclerView recyclerView;
//    GravinceAdapter gravinceAdapter;
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;

    AutoCompleteTextView auto_name;
//    PeopleAdapter adapter;
private ExampleAdapter adapter;
    EditText etSearch;

    private int REQUEST_CAMERA = 0;
    ListPopupWindow listPopupWindow;


    WebView webview_officeo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_order);
        sessionManager = new SessionManager(ActivityOfficeOrder.this);
        mAPIService = ApiUtils.getAPIService();
        InitView();
        Click();
        list = new ArrayList<>();
        list.clear();
//        getGrievanceList("3");


        //-----------------webview code ----------------------

        if (ContextCompat.checkSelfPermission(ActivityOfficeOrder.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityOfficeOrder.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
        } else {
            webview_officeo.setWebViewClient(new WebViewClient());
            webview_officeo.getSettings().setSupportZoom(true);
            webview_officeo.getSettings().setJavaScriptEnabled(true);
            webview_officeo.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                    request.setDescription("Download file...");
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();

                }

            });
            webview_officeo.loadUrl(Allurls.MainURL+"webview_office_order");
        }


    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createDialog(ActivityOfficeOrder.this);
            }
        });
    }

    private void InitView() {
        img_back = findViewById(R.id.img_back);
        img_filter = findViewById(R.id.img_filter);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        auto_name = findViewById(R.id.auto_name);
        etSearch = findViewById(R.id.etSearch);
        webview_officeo = findViewById(R.id.webview_officeo);
    }

    public void getGrievanceList(String filter_type) {
        Customprogress.showPopupProgressSpinner(ActivityOfficeOrder.this,true);
        mAPIService.getListofficeorder(filter_type).enqueue(new Callback<ViewOfficeOrderResponse>() {
            @Override
            public void onResponse(Call<ViewOfficeOrderResponse> call, Response<ViewOfficeOrderResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);
                Customprogress.showPopupProgressSpinner(ActivityOfficeOrder.this,false);
                if(response.isSuccessful()) {
                    System.out.println("Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();
                    list=new ArrayList<>();
                    if (status==true)
                    {
                        ViewOfficeOrderResponse res = response.body();
                        for (int i=0;i<res.getData().size();i++){

                            String id = res.getData().get(i).getId();
                            String empId= res.getData().get(i).getEmpId();
                            String departmentId= res.getData().get(i).getDepartmentId();
                            String tblHeadId= res.getData().get(i).getTblHeadId();
                            String subject= res.getData().get(i).getSubject();
                            String letterNo= res.getData().get(i).getLetterNo();
                            String letterDate= res.getData().get(i).getLetterDate();
                            String referenceNo= res.getData().get(i).getReferenceNo();
                            String referenceDate= res.getData().get(i).getReferenceDate();
                            String issueDate= res.getData().get(i).getIssueDate();
                            String document= res.getData().get(i).getDocument();
                            String ostatus= res.getData().get(i).getStatus();
                            String createdOn= res.getData().get(i).getCreatedOn();
                            String updatedOn= res.getData().get(i).getUpdatedOn();
                            String departmentName= res.getData().get(i).getDepartmentName();
                            String headName= res.getData().get(i).getHeadName();

                            list.add(new ViewOfficeOrderData(id,  empId,  departmentId,  tblHeadId, subject,  letterNo,  letterDate,  referenceNo, referenceDate,  issueDate,  document, ostatus, createdOn,  updatedOn,  departmentName, headName));

                        }
                        setAdapter();
                        etSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                adapter.getFilter().filter(s);
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(ActivityOfficeOrder.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ViewOfficeOrderResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivityOfficeOrder.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void setAdapter(){
//        gravinceAdapter=new GravinceAdapter(ActivityOfficeOrder.this);
        adapter = new ExampleAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityOfficeOrder.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


/*
    public class GravinceAdapter extends RecyclerView.Adapter<GravinceAdapter.MyViewHolder>{
        Context context;
        public GravinceAdapter(Context context){
            this.context=context;
        }

        @NonNull
        @Override
        public GravinceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_officeorder_list, parent,false);
            return new GravinceAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GravinceAdapter.MyViewHolder holder, int position) {

            holder.txt_title.setText(list.get(position).getDepartmentName());
            holder.txt_subtitle.setText(list.get(position).getHeadName());

            holder.layoutcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityOfficeOrder.this,ActivityOfficeOrderDetails.class);
                   intent.putExtra("data",list.get(position));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void filterList(ArrayList<ViewOfficeOrderData> filteredList) {
            list = filteredList;
            notifyDataSetChanged();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView txt_title,txt_subtitle;
            LinearLayout layoutcard;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_subtitle = (TextView) itemView.findViewById(R.id.txt_subtitle);
                txt_title = (TextView) itemView.findViewById(R.id.txt_title);
                layoutcard = (LinearLayout) itemView.findViewById(R.id.layoutcard);
            }
        }
    }
*/

    public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {
        private List<ViewOfficeOrderData> exampleList;
        private List<ViewOfficeOrderData> exampleListFull;
        class ExampleViewHolder extends RecyclerView.ViewHolder {
            TextView txt_title,txt_subtitle;
            LinearLayout layoutcard;
            ExampleViewHolder(View itemView) {
                super(itemView);
                txt_subtitle = (TextView) itemView.findViewById(R.id.txt_subtitle);
                txt_title = (TextView) itemView.findViewById(R.id.txt_title);
                layoutcard = (LinearLayout) itemView.findViewById(R.id.layoutcard);
            }
        }
        ExampleAdapter(List<ViewOfficeOrderData> exampleList) {
            this.exampleList = exampleList;
            exampleListFull = new ArrayList<>(exampleList);
        }
        @NonNull
        @Override
        public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_officeorder_list,
                    parent, false);
            return new ExampleViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
            ViewOfficeOrderData currentItem = exampleList.get(position);
            holder.txt_title.setText(list.get(position).getDepartmentName());
            holder.txt_subtitle.setText(list.get(position).getHeadName());

            holder.layoutcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityOfficeOrder.this,ActivityOfficeOrderDetails.class);
                    intent.putExtra("data",list.get(position));
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return exampleList.size();
        }
        @Override
        public Filter getFilter() {
            return exampleFilter;
        }
        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ViewOfficeOrderData> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(exampleListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ViewOfficeOrderData item : exampleListFull) {
                        if (item.getDepartmentName().toLowerCase().contains(filterPattern) || item.getHeadName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                exampleList.clear();
                exampleList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public  void createDialog(final Context context)
        {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
            dialog.setContentView(R.layout.custom_popup_menu);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
           RadioGroup radioSexGroup=(RadioGroup)dialog.findViewById(R.id.radioGroup);



            ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        int selectedId=radioSexGroup.getCheckedRadioButtonId();
                        RadioButton  radioSexButton=(RadioButton)dialog.findViewById(selectedId);
                        //Issue Date (Latest) = 3 and Department (A to Z) = 1 and  Head (A to Z) = 2 add in filter_type
                        String name = radioSexButton.getText().toString().trim();

                        if (name.isEmpty())
                        {
                            Toast.makeText(context, "Please select option !", Toast.LENGTH_SHORT).show();
                        }
                        else if (name.contains("Department (A to Z)"))
                        {
                            System.out.println("select value >>>>>>>>>>"+name);
                            list.clear();
                            getGrievanceList("1");
                            dialog.dismiss();
                        }
                        else if (name.contains("Head (A to Z)"))
                        {
                            System.out.println("select value >>>>>>>>>>"+name);
                            list.clear();
                            getGrievanceList("2");
                            dialog.dismiss();
                        }
                        else if (name.contains("Issue Date (Latest)"))
                        {
                            System.out.println("select value >>>>>>>>>>"+name);
                            list.clear();
                            getGrievanceList("3");
                            dialog.dismiss();
                        }

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }



                }
            });

            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        System.out.println("Request Code >>>>>>>"+requestCode);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    webview_officeo.setWebViewClient(new WebViewClient());
                    webview_officeo.getSettings().setSupportZoom(true);
                    webview_officeo.getSettings().setJavaScriptEnabled(true);
                    webview_officeo.setDownloadListener(new DownloadListener() {
                        @Override
                        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                            request.setDescription("Download file...");
                            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(request);
                            Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();

                        }

                    });
                    webview_officeo.loadUrl(Allurls.MainURL+"webview_office_order");
                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}