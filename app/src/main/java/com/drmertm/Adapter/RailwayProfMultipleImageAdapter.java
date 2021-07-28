package com.drmertm.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.drmertm.Allurls;
import com.drmertm.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.DOWNLOAD_SERVICE;

public class RailwayProfMultipleImageAdapter extends RecyclerView.Adapter<RailwayProfMultipleImageAdapter.ViewHolder> {
    private List<String> stList;
    private Context context;

    public RailwayProfMultipleImageAdapter(Context context, List<String> students) {
        this.stList = students;
        this.context = context;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
    }
    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_railway_images, parent,false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        String file=stList.get(position);
        int dotposition= stList.get(position).lastIndexOf(".");
        String ext = file.substring(dotposition + 1, file.length());
        System.out.println("extention >>>>>>>>>"+ext);
        if (ext.equals("pdf"))
        {
            viewHolder.img_empsign.setBackground(context.getResources().getDrawable(R.drawable.ic_pdf_file));
           String url = Allurls.ImageURL+stList.get(position);

           viewHolder.img_empsign.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                   request.setDescription("Download file...");
                   request.setTitle(URLUtil.guessFileName(url, "", ""));
                   request.allowScanningByMediaScanner();
                   request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                   request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, "", ""));
                   DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                   dm.enqueue(request);
                   Toast.makeText(context, "Downloading File", Toast.LENGTH_LONG).show();
               }
           });


        }
        else
            {
            String s_image = Allurls.ImageURL+stList.get(position);
            if (s_image!=null&&!s_image.equalsIgnoreCase("")) {
                s_image = s_image.replaceAll("\\\\", "");
                ImageLoader.getInstance().displayImage(s_image, viewHolder.img_empsign);
            }
        }


    }

    @Override
    public int getItemCount() {
//        return size;
        return stList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_empsign;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            img_empsign = itemLayoutView.findViewById(R.id.img_empsign);
        }
    }

    public List<String> getStudentist() {
        return stList;
    }
}

