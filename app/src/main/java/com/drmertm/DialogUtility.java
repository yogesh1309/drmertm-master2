package com.drmertm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


public class DialogUtility {
    private static ProgressDialog mProgressDialog;


  /*  public static Dialog showProgressDialog(Context context, boolean isCancelable, String message) {
        mProgressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        mProgressDialog.setMessage(message);

        DoubleBounce doubleBounce = new DoubleBounce();
        mProgressDialog.setIndeterminateDrawable(doubleBounce);

        mProgressDialog.show();
        mProgressDialog.setCancelable(isCancelable);

        return mProgressDialog;
    }*/

   /* public static Dialog createProgressDialog(Context context, int msgResId) {
        ProgressLoder dialog = new ProgressLoder(context*//*, msgResId*//*);
        dialog.setCanceledOnTouchOutside(false);
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("loading");

        return dialog;
    }*/

    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }


    }

    public static boolean CheckNetwork(Context context) {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else
            connected = false;

        return connected;
    }


    public static void showToast(Context context, String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    /* public static void connectDisconnectDialog(final Context context, String key, View.OnClickListener onClickListener) {
        final Dialog dialog = new Dialog(context);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_error);

        TextView tv_message = dialog.findViewById(R.id.message);
        TextView tv_error = dialog.findViewById(R.id.tv_error);
        TextView okBtn = dialog.findViewById(R.id.tv_ok);
        okBtn.setText("Submit");

        if (key.equals("connect")) {
            tv_message.setText(context.getResources().getString(R.string.connect));
            tv_error.setText("Connect with agent");
        } else {
            tv_message.setText(context.getResources().getString(R.string.disconnect));
            tv_error.setText("Disconnect with agent");
        }

        okBtn.setTag(dialog);
        okBtn.setOnClickListener(onClickListener);
        dialog.show();
    }

    public static void askCommunityDialog(final Context context, AskDialogClick askDialogClick) {
        final Dialog dialog = new Dialog(context);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_ask_question);

        TextView tv_submit = dialog.findViewById(R.id.tv_submit);
        EditText et_comment = dialog.findViewById(R.id.et_comment);
        EditText et_title = dialog.findViewById(R.id.et_title);
        ImageView iv_cancel = dialog.findViewById(R.id.iv_cancel);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_comment.getText().toString())) {
                    Toast.makeText(context, "Please Enter Comment", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(et_title.getText().toString())) {
                    Toast.makeText(context, "Please Enter Title", Toast.LENGTH_SHORT).show();
                } else {
                    askDialogClick.adkDialogClick(et_comment.getText().toString(), et_title.getText().toString());
                    dialog.dismiss();
                }
            }
        });


        dialog.show();
    }


    public static void downloadDialog(final Context context, List<RplyAttactment> rplyAttactment) {
        final Dialog dialog = new Dialog(context);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_insurance_provider);

        RecyclerView recyclerView = dialog.findViewById(R.id.rv_insurance_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DownloadAdapter downloadAdapter=new DownloadAdapter(context,dialog);
        downloadAdapter.setData(rplyAttactment);
        recyclerView.setAdapter(downloadAdapter);
        dialog.show();




       *//* Button okBtn = dialog.findViewById(R.id.btn_ok);
        okBtn.setTag(dialog);
        okBtn.setOnClickListener(onClickListener);
       *//*
    }

    public static void showLogout(final Context context, View.OnClickListener onClickListener) {
        final Dialog dialog = new Dialog(context);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_logout);

        TextView tv_no = dialog.findViewById(R.id.tv_no);
        TextView tv_yes = dialog.findViewById(R.id.tv_yes);

        tv_yes.setTag(dialog);
        tv_yes.setOnClickListener(onClickListener);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void setItem(Context context, String mainTitle, String[] some_array, int level, CustomItemSelected customItemSelected) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.partially_dialog_button, null, false);
        final TextView tv_no = formElementsView.findViewById(R.id.tv_no);
        final TextView tv_yes = formElementsView.findViewById(R.id.tv_yes);
        final String[] itemName = {null};

        TextView title = new TextView(context);
        title.setText(mainTitle);
        title.setPadding(0, 30, 0, 0);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setTextSize(18);

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, android.R.layout.simple_list_item_single_choice, some_array);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(formElementsView);
        mBuilder.setCustomTitle(title);
        mBuilder.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemName[0] = some_array[i];

            }
        });

        AlertDialog mDialog = mBuilder.create();
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemName[0] != null) {
                    customItemSelected.customItemSelected(itemName[0], level);
                    mDialog.dismiss();
                } else
                    Toast.makeText(context, "Please Select Any One", Toast.LENGTH_SHORT).show();
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();


    }

    public static void showYearDialog(Context context, int level, CustomItemSelected customItemSelected) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        final Dialog mDialog = new Dialog(context);
        mDialog.setTitle("Year Picker");
        mDialog.setContentView(R.layout.dialog_year_picker);
        TextView tv_no = mDialog.findViewById(R.id.tv_no);
        TextView tv_yes = mDialog.findViewById(R.id.tv_yes);
        final NumberPicker nopicker = mDialog.findViewById(R.id.numberPicker1);
        Utils.setDividerColor(nopicker);
        nopicker.setMaxValue(year + 0);
        nopicker.setMinValue(year - 100);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemSelected.customItemSelected("" + nopicker.getValue(), level);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }


    public static void datePicker(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                android.app.AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, year, month, day);
        colorizeDatePicker(datePickerDialog.getDatePicker());

        TextView tv = new TextView(context);
        tv.setPadding(15, 15, 15, 15);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        tv.setText("Select Date of Birth");
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        datePickerDialog.setCustomTitle(tv);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTypeface(datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).getTypeface(), Typeface.BOLD);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTypeface(datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).getTypeface(), Typeface.BOLD);

    }


    public static void customDialog(Context context, String mainTitle, String[] some_array, int level, CustomItemSelected customItemSelected) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.partially_dialog_button, null, false);
        final TextView tv_no = formElementsView.findViewById(R.id.tv_no);
        final TextView tv_yes = formElementsView.findViewById(R.id.tv_yes);
        final String[] itemName = {null};

        TextView title = new TextView(context);
        title.setText(mainTitle);
        title.setPadding(0, 30, 0, 0);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setTextSize(18);

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, android.R.layout.simple_list_item_single_choice, some_array);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(formElementsView);
        mBuilder.setCustomTitle(title);
        mBuilder.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemName[0] = some_array[i];

            }
        });

        AlertDialog mDialog = mBuilder.create();
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemName[0] != null) {
                    customItemSelected.customItemSelected(itemName[0], level);
                    mDialog.dismiss();
                } else
                    Toast.makeText(context, "Please Select Any One", Toast.LENGTH_SHORT).show();
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();


    }*/


}
