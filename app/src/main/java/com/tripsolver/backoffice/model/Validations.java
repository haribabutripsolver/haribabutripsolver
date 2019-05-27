package com.tripsolver.backoffice.model;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 11/16/2017.
 */

public class Validations {
    public static boolean isEmpty(String value, TextInputLayout editText, String name)
    {
        if(value.length()>1)
        {
            editText.setErrorEnabled(false);
            return true;

        }
        editText.setErrorEnabled(false);

        editText.setError("Please enter " +name.toLowerCase());
        editText.requestFocus();
        return false;

    }
    public static boolean isIdentityEmpty(String value, TextInputLayout editText, String name)
    {
        if(value.length()>1)
        {
            editText.setErrorEnabled(false);
            return true;

        }
        editText.setError("Please enter " +name.toLowerCase());

        return false;

    }
    public static boolean isLastnameEmpty(String value, TextInputLayout editText, String name)
    {
        if(value.length()>0)
        {
            editText.setErrorEnabled(false);
            return true;

        }
        editText.setError("Please enter " +name.toLowerCase());
        editText.requestFocus();
        return false;

    }
    public static boolean isLastnameEmpty(String value, EditText editText, String name)
    {
        if(value.length()>0)
        {

            return true;

        }
        editText.setError("Please enter " +name.toLowerCase());
        editText.requestFocus();
        return false;

    }

    public static boolean isCvvEmpty(String value, TextInputLayout editText, String name, String cardtype) {
        if (value.length() > 2) {
if(cardtype.length()>0) {
    switch (cardtype) {

        case "visa":
            if (value.length() == 3)
                return true;
            break;
        case "dinnersclub":
            if (value.length() == 3)
                return true;
            break;
        case "mastercard":
            if (value.length() == 3)
                return true;
            break;
        case "americanexpress":
            if (value.length() == 4)
                return true;
            break;
        case "discover":
            if (value.length() == 3)
                return true;
            break;
        case "jcb":
            if (value.length() == 3)
                return true;

            break;
        default:
            editText.setError("invalid " + name.toLowerCase());
            editText.requestFocus();
            return false;

    }
}
        }
            editText.setError("invalid " + name.toLowerCase());
            editText.requestFocus();
            return false;


    }
    public static boolean isGenderMatched(String tittlename,String gender, TextInputLayout editText)
    {
        boolean gendermatch = true;
        switch (tittlename) {
            case "Mr":
                if (!gender.equals("Male")) {
                    gendermatch = false;
                }

                break;
            case "Mrs":
                if (!gender.equals("Female")) {
                    gendermatch = false;
                }
                break;
            case "Miss":
                if (!gender.equals("Female")) {
                    gendermatch = false;
                }
                break;
            case "Ms":
                if (!gender.equals("Female")) {
                    gendermatch = false;
                }
                break;
        }

        if(gendermatch)
        {
            editText.setErrorEnabled(false);
            return true;

        }
        editText.setError("Invalid Passenger Title and Gender Combination");
        editText.requestFocus();
        return false;

    }

    public static boolean isEmpty(String value, EditText editText, String name)
    {
        if(value.length()>0)
        {
            editText.setError(null);
            return true;

        }
  editText.setError("Please enter the " +name.toLowerCase());
        editText.requestFocus();
        return false;

    }
    public static boolean isIdentityEmpty(String value, EditText editText, String name)
    {
        if(value.length()>0)
        {

            return true;

        }
        editText.setError("Please enter " +name.toLowerCase());
        return false;

    }

    public static boolean isEmail(EditText emailidedit) {
        // TODO Auto-generated method stub
        String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String em = emailidedit.getText().toString();
        em=em.replaceAll("\\s+","");
        if (em.matches(EMAIL_REGEX)&&!em.contains(" ")) {

            return true;
        } else {
            emailidedit.setError("Enter valid email");
            emailidedit.requestFocus();
            return false;
        }
    }
    public static boolean isEmail(EditText emailidedit,TextInputLayout textInputLayout) {
        // TODO Auto-generated method stub
        String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String em = emailidedit.getText().toString();
        em=em.replaceAll("\\s+","");
        if (em.matches(EMAIL_REGEX)&&!em.contains(" ")) {
            textInputLayout.setErrorEnabled(false);
            return true;
        } else {
            textInputLayout.setError("Enter valid email");
            emailidedit.requestFocus();
            return false;
        }
    }
    public static boolean isIdentityEmail(EditText emailidedit,TextInputLayout textInputLayout) {
        // TODO Auto-generated method stub
        String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String em = emailidedit.getText().toString();
        em=em.replaceAll("\\s+","");
        if (em.matches(EMAIL_REGEX)) {
            textInputLayout.setErrorEnabled(false);
            return true;
        } else {
            textInputLayout.setError("Enter valid email");
            emailidedit.requestFocus();

            return false;
        }
    }
    public static boolean isMobile(EditText mobile) {
        String text = mobile.getText().toString().trim();

        int len = text.length();
        boolean flag = false;
        if(len<=0)
        {
            mobile.setError("Enter mobile number");
            mobile.requestFocus();
            return false;
        }
        else if(len>11||len<10) {
            mobile.setError("Mobile number is invalid");
            mobile.requestFocus();
            return false;
        }


        return true;


    }
    public static boolean isGetmaxPin(EditText mobile) {
        String text = mobile.getText().toString().trim();

        int len = text.length();
        boolean flag = false;
        if(len<=0)
        {
            mobile.setError("Enter Getmax PIN");
            mobile.requestFocus();
            return false;
        }
       else  if (len <6) {
            mobile.setError("Enter valid Getmax PIN");
            mobile.requestFocus();
            return false;
        }
        return true;


    }
    public static boolean isGetmaxPin(EditText mobile,TextInputLayout textInputLayout) {
        String text = mobile.getText().toString().trim();

        int len = text.length();
        boolean flag = false;
        if(len<=0)
        {
            textInputLayout.setError("Enter  Getmax PIN");
            textInputLayout.requestFocus();
            return false;
        }
        else  if (len <6) {
            textInputLayout.setError("Enter valid Getmax PIN");
            textInputLayout.requestFocus();
            return false;
        }

        textInputLayout.setErrorEnabled(false);
        return true;


    }
    public static boolean isMobile(EditText mobile,TextInputLayout textInputLayout) {
        String text = mobile.getText().toString();

        int len = text.length();

        if(len<=0)
        {
            textInputLayout.setError("Enter mobile number");
            mobile.requestFocus();
            return false;
        }
        else if(len>11||len<10) {
            textInputLayout.setError("Mobile number is invalid");
            mobile.requestFocus();
            return false;
        }
      /*  if(len>11||len<10||text.contains(" ")) {
            textInputLayout.setError("Mobile number is invalid");
            textInputLayout.requestFocus();
            return false;
        }*/
        textInputLayout.setErrorEnabled(false);
        return true;


    }
    public static boolean isIdentityMobile(EditText mobile,TextInputLayout textInputLayout) {
        String text = mobile.getText().toString().trim();

        int len = text.length();
        boolean flag = false;


        if(len<=0)
        {
            textInputLayout.setError("Enter mobile number");
            mobile.requestFocus();
            return false;
        }
        else if(len>11||len<10) {
            textInputLayout.setError("Mobile number is invalid");
            mobile.requestFocus();
            return false;
        }
       /* if(len>11||len<10||text.contains(" ")) {
            textInputLayout.setError("Mobile number is invalid");
            return false;
        }*/
        textInputLayout.setErrorEnabled(false);
        return true;


    }
    public static  boolean Checkequal(String firststr,String secondstr)
    {
        if(firststr.equals(secondstr))
        {
            return true;
        }
        return false;
    }
    public static  boolean isFnameLnameEqual(String firststr,String secondstr,TextInputLayout lastnamelayout)
    {
        if(firststr.equals(secondstr))
        {
            lastnamelayout.setError("");
            return true;
        }
        lastnamelayout.setErrorEnabled(false);
        return false;
    }
    public  static boolean Checkcount(int count)
    {
        if(count<=9)
        {
        return  true;
        }
    return false;
    }
    public static boolean Countcompare(int firstdatestr,int secondcount)
    {
        if(firstdatestr==secondcount)
        {
            return true;
        }
        return false;
    }
    public static boolean Checkgreaterdate(String fristdatestr,String seconddatestr)
    {
        try {
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");

            Date firstdate= sdf.parse(fristdatestr);
            Date seconddate= sdf.parse(seconddatestr);
            if(firstdate.before(seconddate))
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        return  false;
    }



    public static boolean greaterDate(String expdate, TextInputLayout datelayout, String message)
    {
        Date date1=new Date();
        Date date2=new Date();
        try {
            Date date=new Date(); // your date
            SimpleDateFormat dy = new SimpleDateFormat("yyyy");
            String year=dy.format(date);
            SimpleDateFormat dm = new SimpleDateFormat("MM");
            String month=dm.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            date1 = sdf.parse(expdate);
            date2 = sdf.parse(Integer.parseInt(month)+"/"+year);



            if(date1.compareTo(date2)>=0)
            {
                datelayout.setErrorEnabled(false);
                return true;
            }
            else
            {
                datelayout.setError("please enter valid "+message);
                datelayout.requestFocus();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            datelayout.setError("please enter valid "+message);
        }

        return  false;
    }
    public static boolean greaterDob(String expdate, TextInputLayout datelayout, String message)
    {
        Date date1=new Date();
        Date date2=new Date();
        try {
            Date date=new Date(); // your date
            SimpleDateFormat dateFormattemp=new SimpleDateFormat("dd/MM/yyyy");
            String todaydate=dateFormattemp.format(date);



            date1 = dateFormattemp.parse(expdate);
            date2 = dateFormattemp.parse(todaydate);



            if(date1.compareTo(date2)<0)
            {
                datelayout.setErrorEnabled(false);
                return true;
            }
            else
            {
                datelayout.setError("Please enter valid "+message);
                datelayout.requestFocus();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            datelayout.setError("Please enter valid "+message);
        }

        return  false;
    }

    public static boolean isValidDate(String expdate, TextInputLayout datelayout, String message)
    {
        boolean returnvalue=true;
        Date date1=new Date();
        Date date2=new Date();
        try {
            Date date=new Date(); // your date
            SimpleDateFormat dateFormattemp=new SimpleDateFormat("dd/MM/yyyy");
          String todaydate=dateFormattemp.format(date);
           /*   SimpleDateFormat dy = new SimpleDateFormat("yyyy");
            String year=dy.format(date);
            SimpleDateFormat dm = new SimpleDateFormat("MM");

            SimpleDateFormat dd = new SimpleDateFormat("dd");*/

        String    datevalue =expdate.substring(0,2);
       String    monthvalue  = expdate.substring(3,5);
        String    yearvalue = expdate.substring(6,expdate.length());
            if (monthvalue.equals("02"))
            {
                if (isLeap(Integer.parseInt(yearvalue)))
                    returnvalue= (Integer.parseInt(datevalue) <= 29);
                else
                    returnvalue= (Integer.parseInt(datevalue) <= 28);
            }

            date1 = dateFormattemp.parse(expdate);
            date2 = dateFormattemp.parse(todaydate);



            if(date1.compareTo(date2)<0)
            {
                if(returnvalue)
                {
                    datelayout.setErrorEnabled(false);
return true;
                }
                else
                {
                    datelayout.setError("Please enter valid "+message);
                    datelayout.requestFocus();
                    return false;
                }

            }
            else
            {
                datelayout.setError("Please enter valid "+message);
                datelayout.requestFocus();
                return  false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            datelayout.setError("Please enter valid "+message);
        }

        return  false;
    }
    public  static boolean Checkdateequal(String firstdatestr,String seconddatestr)
    {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");

            Date firstdate= sdf.parse(firstdatestr);
            Date seconddate= sdf.parse(seconddatestr);
            if(firstdate.equals(seconddate))
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  false;
    }
    public static  boolean isLeap(int year)
    {
        // Return true if year is
        // a multiple of 4 and not
        // multiple of 100.
        // OR year is multiple of 400.
        return (((year % 4 == 0) &&
                (year % 100 != 0)) ||
                (year % 400 == 0));
    }
}
