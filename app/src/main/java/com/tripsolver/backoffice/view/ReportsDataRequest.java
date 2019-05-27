package com.tripsolver.backoffice.view;

import android.app.AlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.model.Validations;

/**
 * Created by lenovo on 5/2/2019.
 */

public class ReportsDataRequest extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    String srcstr,fromdatestr,todatestr,statusstr;

  EditText srctype,fromdate,todate;
  EditText   statustype;
    String selectedstatusvalue="All";
    String selectedsourcevalue="All";
    int selectedstatus;
    int fromYear, fromMonth, fromDay ;
    int toYear, toMonth, toDay ;
    int currentyear,currentmonth,currentday;

    int selectedsource;
    DatePickerDialog datePickerDialog;
    Button searchrecordsbt;
    boolean fromselect=false;
    Calendar calendar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportsdatarequest);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(R.string.reports);

        srctype=(EditText)findViewById(R.id.srctype);
        fromdate=(EditText)findViewById(R.id.fromdate);
        todate=(EditText)findViewById(R.id.todate);
        searchrecordsbt=(Button)findViewById(R.id.searchrecords);
        statustype=(EditText)findViewById(R.id.statustype);
        calendar = Calendar.getInstance();
        srctype.setFocusable(false);
        fromdate.setFocusable(false);
        todate.setFocusable(false);
        statustype.setFocusable(false);
        currentyear = calendar.get(Calendar.YEAR);
        currentmonth = calendar.get(Calendar.MONTH)+1;
        currentday = calendar.get(Calendar.DAY_OF_MONTH);
        toYear = calendar.get(Calendar.YEAR);
        toMonth = calendar.get(Calendar.MONTH)+1;
        toDay = calendar.get(Calendar.DAY_OF_MONTH);

        fromYear = calendar.get(Calendar.YEAR);
        fromMonth = calendar.get((Calendar.MONTH));
        fromDay = calendar.get(Calendar.DAY_OF_MONTH);

        statustype.setText("All");
        srctype.setText("All");
        fromdatestr=  fromYear + "-" +checkDigit(fromMonth)+ "-" +  checkDigit(fromDay);


todatestr= currentyear + "-" +checkDigit(currentmonth)+ "-" +  checkDigit(currentday);
        String currenctdate=  "" + checkDigit(currentday) + "/" + checkDigit(currentmonth)+ "/" + currentyear;
        fromdate.setText( "" + checkDigit(fromDay) + "/" + checkDigit(fromMonth)+ "/" + fromYear);
        todate.setText( "" + checkDigit(currentday) + "/" + checkDigit(currentmonth)+ "/" + currentyear);
    }

    public void generateReports(View v)
    {
        if(checkValidations()) {
            Bundle b = new Bundle();

            srcstr = srctype.getText().toString();
            if (srctype.getText().toString().equals("All")) {
                srcstr = "";
            }
            statusstr = statustype.getText().toString();
            if (statustype.getText().toString().equals("All")) {
                statusstr = "";
            }

/*
        fromdatestr=fromdate.getText().toString();
*/
/*
                todatestr=todate.getText().toString();
*/
            b.putString("srcstr", srcstr);
            b.putString("fromdatestr", fromdatestr);
            b.putString("todatestr", todatestr);
            b.putString("statusstr", statusstr);
/*        Fragment f = new ReportsData();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, f);
        transaction.commit();*/
            Intent i = new Intent(ReportsDataRequest.this, ReportsData.class);
            i.putExtras(b);
            startActivity(i);
    /*       Fragment fobj=new ReportsData();
           fobj.setArguments(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fobj);

        ft.commit();*/

        }

    }
    public void Fromselectdate(View v)
    {
        fromselect=true;


        datePickerDialog = DatePickerDialog.newInstance(ReportsDataRequest.this, fromYear, fromMonth-1, fromDay);


        Calendar minDate = Calendar.getInstance();
        minDate.set(1800,5,3);
    /*    minDate.set(Calendar.DAY_OF_MONTH, fromDay);
        minDate.set(Calendar.MONTH, fromMonth);
        minDate.set(Calendar.YEAR, fromYear);*/


        datePickerDialog.setThemeDark(false);
        datePickerDialog.setMinDate(minDate);

        datePickerDialog.showYearPickerFirst(false);

        datePickerDialog.setAccentColor(Color.parseColor("#1C416A"));

        datePickerDialog.setTitle("Select From Date");

        // datePickerDialog.setMaxDate(Calendar.DAY_OF_YEAR.getTimeInMillis());
        // datePickerDialog.setMinDate(new Date(Calendar.currentTimeMillis() + 2*24*60*60*1000));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }



    public void Toselectdate(View v)
    {
        datePickerDialog = DatePickerDialog.newInstance(ReportsDataRequest.this, toYear, toMonth-1, toDay);


        Calendar minDate = Calendar.getInstance();
        minDate.set(Calendar.DAY_OF_MONTH, fromDay);
        minDate.set(Calendar.MONTH, fromMonth-1);
        minDate.set(Calendar.YEAR, fromYear);


        datePickerDialog.setThemeDark(false);
        datePickerDialog.setMinDate(minDate);


        datePickerDialog.showYearPickerFirst(false);

        datePickerDialog.setAccentColor(Color.parseColor("#1C416A"));

        datePickerDialog.setTitle("Select To Date");

        // datePickerDialog.setMaxDate(Calendar.DAY_OF_YEAR.getTimeInMillis());
        // datePickerDialog.setMinDate(new Date(Calendar.currentTimeMillis() + 2*24*60*60*1000));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }


    public void statusPopUp(View v)
    {


            AlertDialog.Builder builder = new AlertDialog.Builder(ReportsDataRequest.this);


            // String array for alert dialog multi choice items
            final String[] items = new String[]{
                    "All",
                    "Confirmed",
                    "Ticketed",
                    "Cancelled"

            };

            // Boolean array for initial selected items
            final boolean[] checkedColors = new boolean[]{
                    false, // Red
                    true

            };
            if(selectedstatusvalue.equals("All"))
            {
                selectedstatus=0;
            }
            else if(selectedstatusvalue.equals("Confirmed"))
            {
                selectedstatus=1;
            }
            else if(selectedstatusvalue.equals("Ticketed"))
            {
                selectedstatus=2;
            }
            else if(selectedstatusvalue.equals("Cancelled"))
        {
            selectedstatus=3;
        }

            final List<String> itemList = Arrays.asList(items);


            builder.setSingleChoiceItems(items, selectedstatus, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface d, int n) {
                    selectedstatusvalue=items[n];
                }

            });


            // Specify the dialog is not cancelable
            builder.setCancelable(false);

            // Set a title for alert dialog
            builder.setTitle("Select Status");

            // Set the positive/yes button click listener
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do something when click positive button
/*
                    dateinfotype.setText(selectedstringvalue);
*/
                    statustype.setText(selectedstatusvalue);

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    // Do something when click the negative button
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();



    }

    public void sourcePopUp(View v)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(ReportsDataRequest.this);


        // String array for alert dialog multi choice items
        final String[] items = new String[]{
"All",
                "B2C",
                "SkyScanner",
                "TGDS"


        };

        // Boolean array for initial selected items
        final boolean[] checkedColors = new boolean[]{
                false, // Red
                true

        };
        if(selectedsourcevalue.equals("All"))
        {
            selectedsource=0;
        }
        else if(selectedsourcevalue.equals("B2C"))
        {
            selectedsource=1;
        }
        else if(selectedsourcevalue.equals("SkyScanner"))
        {
            selectedsource=2;
        }
        else if(selectedsourcevalue.equals("TGDS"))
        {
            selectedsource=3;
        }

        final List<String> itemList = Arrays.asList(items);


        builder.setSingleChoiceItems(items, selectedsource, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface d, int n) {
                selectedsourcevalue=items[n];
            }

        });


        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Select Source");

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
               /* if(selectedsourcevalue.equals("All"))
                {
                    selectedsourcevalue="";
                }*/
                srctype.setText(selectedsourcevalue);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                // Do something when click the negative button
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();



    }


    public void populateSetDate(int year, int month, int day) {


        // String datevalue= String.format("%02d%02d%02d", yy/ mm/ dd);
        String date =   checkDigit(day)+ "/" + checkDigit(month)+ "/" +year ;


        // datePickerDialog.getActivity().setMinDate(System.currentTimeMillis() - 1000);
        if(fromselect)
        {
            fromDay=day;
            fromYear=year;
            fromMonth=month;
            fromselect=false;
            fromdate.setText(date);
            fromdatestr=  year + "-" +checkDigit(month)+ "-" +  checkDigit(day);

        }
        else
        {
todate.setText(date);
            toDay=day;
            toYear=year;
            toMonth=month;
            todatestr=  year + "-" + checkDigit(month)+ "-" + checkDigit(day);
        }
    }

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        populateSetDate(year,monthOfYear + 1, dayOfMonth);

    }
    public void setfromdateValue()
    {
    }

    public void settodateValue()
    {
        datePickerDialog = DatePickerDialog.newInstance(ReportsDataRequest.this, fromYear, fromMonth, fromDay+1);


        Calendar minDate = Calendar.getInstance();
        minDate.set(Calendar.DAY_OF_MONTH, fromDay);
        minDate.set(Calendar.MONTH, fromMonth);
        minDate.set(Calendar.YEAR, fromYear);


        datePickerDialog.setThemeDark(false);
        datePickerDialog.setMinDate(minDate);


        datePickerDialog.showYearPickerFirst(false);

        datePickerDialog.setAccentColor(Color.parseColor("#1C416A"));

        datePickerDialog.setTitle("Select Date");
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            // Android home
            case android.R.id.home:
/*
                drawerLayout.openDrawer(GravityCompat.START);
*/
finish();
                return true;
            // manage other entries if you have it ...
        }
        return true;
    }

    public boolean checkValidations()
    {

        if (Validations.isEmpty(srctype.getText().toString(), srctype, "Source type")) {
        }
        if (Validations.isEmpty(statustype.getText().toString(), statustype, "Status type")) {
        }
        if (Validations.isEmpty(fromdate.getText().toString(),fromdate , "From Date")) {

        }

        if (Validations.isEmpty(todate.getText().toString(),todate , "To Date")) {

        }
        boolean value=Validations.isEmpty(srctype.getText().toString(),srctype , "Source type")&&
                Validations.isEmpty(statustype.getText().toString(), statustype, "Status type")&&
                Validations.isEmpty(fromdate.getText().toString(), fromdate, "From Date")&&
                Validations.isEmpty(fromdate.getText().toString(), fromdate, "To Date");

        return value;
    }
}
