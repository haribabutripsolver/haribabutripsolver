package com.tripsolver.backoffice.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lenovo on 11/28/2017.
 */

public class Session {
    Context ctx;
    SharedPreferences prefs;
String agencyId="ABC10280";


    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }



    String bofname;
    public Session(Context ctx)
    {
        this.ctx=ctx;
        prefs= PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    public void setISLogin(boolean islogin)
    {
        prefs.edit().putBoolean("islogin",islogin).commit();;
    }
    public Boolean getIsLogin()
    {
      Boolean islogin=  prefs.getBoolean("islogin",false);
        return  islogin;

    }
    public void setMinFareValue(int minfarevalue)
    {
        prefs.edit().putInt("minfarevalue",minfarevalue).commit();;
    }
    public Integer getMinFareValue()
    {
        Integer minfarevalue=  prefs.getInt("minfarevalue",39);
        return  minfarevalue;

    }
    public void setISPinVerified(boolean pinverify)
    {
        prefs.edit().putBoolean("pinverify",pinverify).commit();;
    }
    public Boolean getIsPinVerified()
    {
        Boolean islogin=  prefs.getBoolean("pinverify",false);
        return  islogin;

    }


    public void setUpdateLater(boolean updatelater)
    {
        prefs.edit().putBoolean("updatelater",updatelater).commit();
    }
    public Boolean getUpdateLater()
    {
        Boolean updatelater=  prefs.getBoolean("updatelater",false);
        return  updatelater;

    }

    public void setGetMaxLogin(boolean getmaxlogin)
    {
        prefs.edit().putBoolean("isgetmaxlogin",getmaxlogin).commit();;
    }
    public Boolean getIsGetMaxLogin()
    {
        Boolean islogin=  prefs.getBoolean("isgetmaxlogin",false);
        return  islogin;

    }

    public void setAgencyId(String agencyId)
    {
        prefs.edit().putString("agencyId",agencyId).commit();
    }
    public String getAgencyId()
    {
        String vname=  prefs.getString("agencyId","");
        return  vname;

    }
    public void setBofname(String bofname)
    {
        prefs.edit().putString("bofname",bofname).commit();
    }
    public String getBofname()
    {
        String vname=  prefs.getString("bofname","");
        return  vname;

    }



    public void setLogoUrl(String logourl)
    {
        prefs.edit().putString("logourl",logourl).commit();
    }
    public String getLogoUrl()
    {
        String logourl=  prefs.getString("logourl","");
        return  logourl;

    }


    public void setAgentId(String agencyId)
    {
        prefs.edit().putString("agentId",agencyId).commit();
    }
    public String getAgentId()
    {
        String vname=  prefs.getString("agentId","");
        return  vname;

    }
    public void setVersionName(String vname)
    {
        prefs.edit().putString("vname",vname).commit();
    }
    public String getVersionName()
    {
        String vname=  prefs.getString("vname","");
        return  vname;

    }


    public void setFname(String fname)
    {
        prefs.edit().putString("fname",fname).commit();
    }
    public String getFname()
    {
        String fname=  prefs.getString("fname","");
        return  fname;

    }

    public void setGetmaxPinValue(String getmaxpin)
    {
        prefs.edit().putString("getmaxpin",getmaxpin).commit();
    }
    public String getGetmaxPinValue()
    {
        String fname=  prefs.getString("getmaxpin","");
        return  fname;

    }
    public void setLname(String lname)
    {
        prefs.edit().putString("lname",lname).commit();
    }
    public String getLname()
    {
        String lname=  prefs.getString("lname","");
        return  lname;

    }


    public void setEmail(String email)
    {
        prefs.edit().putString("email",email).commit();
    }
    public String getEmail()
    {
        String email=  prefs.getString("email","");
        return  email;

    }


    public void setPhoneno(String phoneno)
    {
        prefs.edit().putString("phoneno",phoneno).commit();
    }
    public String getPhoneno()
    {
        String phoneno=  prefs.getString("phoneno","");
        return  phoneno;

    }

    public void setCustomerId(String custid)
    {
        prefs.edit().putString("custid",custid).commit();
    }
    public String getCustomerId()
    {
        String CustomerId=  prefs.getString("custid","");
        return  CustomerId;

    }
    public void setCustomerPin(String custpin)
    {
        prefs.edit().putString("custpin",custpin).commit();
    }
    public String getCustomerPin()
    {
        String custpin=  prefs.getString("custpin","");
        return  custpin;

    }


    public void setVersion(String version)
    {
        prefs.edit().putString("version",version).commit();
    }
    public String getVersion()
    {
        String version=  prefs.getString("version","");
        return  version;

    }

    public void setIsRepricing(boolean version)
    {
        prefs.edit().putBoolean("isrepricing",version).commit();
    }
    public boolean getIsRepricing()
    {
        boolean version=  prefs.getBoolean("isrepricing",false);
        return  version;

    }


    public void setAddress(String address)
    {
        prefs.edit().putString("address",address).commit();
    }
    public String getAddress()
    {
        String address=  prefs.getString("address","");
        return  address;

    }


    public void setFromcityCode(String address)
    {
        prefs.edit().putString("fromcitycode",address).commit();
    }
    public String getFromcityCode()
    {
        String address=  prefs.getString("fromcitycode","");
        return  address;

    }


    public void setFromcitystring(String address)
    {
        prefs.edit().putString("fromcitystring",address).commit();
    }
    public String getFromcitystring()
    {
        String address=  prefs.getString("fromcitystring","");
        return  address;

    }


    public void setTocityCode(String address)
    {
        prefs.edit().putString("tocitycode",address).commit();
    }
    public String getTocityCode()
    {
        String address=  prefs.getString("tocitycode","");
        return  address;

    }


    public void setTocitystring(String address)
    {
        prefs.edit().putString("tocitystring",address).commit();
    }
    public String getTocitystring()
    {
        String address=  prefs.getString("tocitystring","");
        return  address;

    }



    public void setCity(String city)
    {
        prefs.edit().putString("city",city).commit();
    }
    public String getCity()
    {
        String city=  prefs.getString("city","");
        return  city;

    }

    public void setState(String state)
    {
        prefs.edit().putString("state",state).commit();
    }
    public String getState()
    {
        String state=  prefs.getString("state","");
        return  state;

    }

    public void setZipcode(String zipcode)
    {
        prefs.edit().putString("zipcode",zipcode).commit();
    }
    public String getZipcode()
    {
        String zipcode=  prefs.getString("zipcode","");
        return  zipcode;

    }

    public void setCountry(String country)
    {
        prefs.edit().putString("country",country).commit();
    }
    public String getCountry()
    {
        String country=  prefs.getString("country","");
        return  country;

    }


    public void setDeviceid(String deviceid)
    {
        prefs.edit().putString("deviceid",deviceid).commit();;
    }
    public String getDeviceid()
    {
        String deviceid=  prefs.getString("deviceid","");
        return  deviceid;

    }

    public void setDeviceToken(String devicetoken)
    {
        prefs.edit().putString("devicetoken",devicetoken).commit();;
    }
    public String getDeviceToken()
    {
        String devicetoken=  prefs.getString("devicetoken","");
        return  devicetoken;

    }


    public void setDeviceType(String devicetype)
    {
        prefs.edit().putString("devicetype",devicetype).commit();;
    }
    public String getDeviceType()
    {
        String devicetype=  prefs.getString("devicetype","");
        return  devicetype;

    }




}
