package com.tripsolver.backoffice.Adapters;

/**
 * Created by lenovo on 4/24/2019.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.model.PassengerDataResponse;

public class PassengerListAdapter extends RecyclerView.Adapter<PassengerListAdapter.MyViewHolder> {

    private List<PassengerDataResponse> passengerdatalist;
    Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView   passengertittlevalue,
                passengertittlefnamevalue,
        passengertittlemnamevalue,
                passengertittlelnamevalue,
        gendervalue,
                dobvalue,passengertypevalue;

        public MyViewHolder(View view) {
            super(view);
            passengertypevalue=(TextView)view.findViewById(R.id.passengerInfotittle);
            passengertittlevalue = (TextView) view.findViewById(R.id.passengertittlevalue);
            passengertittlefnamevalue = (TextView) view.findViewById(R.id.passengertittlefnamevalue);
            passengertittlemnamevalue = (TextView) view.findViewById(R.id.passengertittlemnamevalue);
            passengertittlelnamevalue = (TextView) view.findViewById(R.id.passengertittlelnamevalue);
            gendervalue = (TextView) view.findViewById(R.id.gendervalue);
            dobvalue = (TextView) view.findViewById(R.id.dobvalue);

        }
    }


    public PassengerListAdapter(List<PassengerDataResponse> passengerdatalist, Context ctx) {
        this.passengerdatalist = passengerdatalist;
        this.ctx=ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passengerdata, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            PassengerDataResponse passengerdataobj = passengerdatalist.get(position);

            String passengertype = "Adult";
            if (passengerdataobj.getPax_Type().equalsIgnoreCase("ADT")) {
                passengertype = "Adult";
            } else if (passengerdataobj.getPax_Type().equalsIgnoreCase("CHD")) {
                passengertype = "Child";
            } else if (passengerdataobj.getPax_Type().equalsIgnoreCase("INF")) {
                passengertype = "Infant";
            } else if (passengerdataobj.getPax_Type().equalsIgnoreCase("INL")) {
                passengertype = "InfantonLap";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
            SimpleDateFormat displayformat = new SimpleDateFormat("MM/dd/yyyy");

            Date dobvaluedt=sdf.parse(passengerdataobj.getDateofBirth());
           String dobvalue= displayformat.format(dobvaluedt);
            holder.passengertittlevalue.setText(passengerdataobj.getTitle());
            holder.passengertittlefnamevalue.setText(passengerdataobj.getFirstName());
            holder.passengertittlemnamevalue.setText(passengerdataobj.getMiddleName());
            holder.passengertittlelnamevalue.setText(passengerdataobj.getLastName());
            holder.gendervalue.setText(passengerdataobj.getGender());
            holder.dobvalue.setText(dobvalue);
            holder.passengertypevalue.setText("Passenger " + (position+1)+" (" + passengertype + ")");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return passengerdatalist.size();
    }
}
