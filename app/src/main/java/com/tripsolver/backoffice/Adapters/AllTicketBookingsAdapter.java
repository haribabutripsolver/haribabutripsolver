package com.tripsolver.backoffice.Adapters;

/**
 * Created by lenovo on 4/24/2019.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.model.TicketBookingsResponse;
import com.tripsolver.backoffice.view.TicketDetails;

public class AllTicketBookingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<TicketBookingsResponse> tiketbookinglist;
    private List<TicketBookingsResponse> itemsFilteredtiketbookinglist;
    boolean noresultsfound=false;int VIEW_TYPE_NORESULT=2;
    Context ctx;
    int loadlimit;
    String filtertype="";
    int viewid;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    String bookingtype;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filtervalue = charSequence.toString();

                List<TicketBookingsResponse> filtered = new ArrayList<>();
                if(filtertype!=null)  {
                    if (filtervalue.isEmpty()||filtervalue.equalsIgnoreCase("")) {
                        filtered = tiketbookinglist;
                    } else {
                        switch (filtertype) {
                            case "PNR":
                                String pnrvalue;
                                for (TicketBookingsResponse movie : tiketbookinglist) {
                                    if (movie.getPnrvalue().toLowerCase().equalsIgnoreCase(filtervalue.toLowerCase())) {
                                        filtered.add(movie);
                                    }
                                }
                                break;
                            case "Confirmation":
                                for (TicketBookingsResponse movie : tiketbookinglist) {
                                    if (movie.getConfirmationid().toLowerCase().equalsIgnoreCase(filtervalue.toLowerCase())) {
                                        filtered.add(movie);
                                    }
                                }
                                break;
                            case "Booking Id":
                                for (TicketBookingsResponse movie : tiketbookinglist) {
                                    if (movie.getBookingid().toLowerCase().equalsIgnoreCase(filtervalue.toLowerCase())) {
                                        filtered.add(movie);
                                    }
                                }
                                break;
                            case "First Name":
                                for (TicketBookingsResponse movie : tiketbookinglist) {
                                    if (movie.getFirstname().toLowerCase().equalsIgnoreCase(filtervalue.toLowerCase())) {
                                        filtered.add(movie);
                                    }
                                }
                                break;
                            case "Last Name":
                                for (TicketBookingsResponse movie : tiketbookinglist) {
                                    if (movie.getLastname().toLowerCase().equalsIgnoreCase(filtervalue.toLowerCase())) {
                                        filtered.add(movie);
                                    }
                                }
                                break;
                            case "Phone":
                                for (TicketBookingsResponse movie : tiketbookinglist) {
                                    if (movie.getPhonevalue().toLowerCase().equalsIgnoreCase(filtervalue.toLowerCase())) {
                                        filtered.add(movie);
                                    }
                                }
                                break;
                            case "Email_Id":
                                for (TicketBookingsResponse movie : tiketbookinglist) {
                                    if (movie.getEmailvalue().toLowerCase().equalsIgnoreCase(filtervalue.toLowerCase())) {
                                        filtered.add(movie);
                                    }
                                }
                                break;

                            case "seven day":

                                break;
                            case "thirty day":
                                break;
                            case "sixty day":
                                break;
                        }

                    }
                }


                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                itemsFilteredtiketbookinglist = (List<TicketBookingsResponse>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        TextView  citieslable,triptype,status,confirmationno,bookingconfirmationno,custdetails,createdate,pnrvalue,phonenovalue;
        CardView  cardview;
        TextView sourcetype,statusvalue;


        public MyViewHolder(View view) {
            super(view);
            citieslable = (TextView) view.findViewById(R.id.citieslable);
            triptype = (TextView) view.findViewById(R.id.triptype);
            status = (TextView) view.findViewById(R.id.status);
            confirmationno = (TextView) view.findViewById(R.id.confirmationno);
            bookingconfirmationno = (TextView) view.findViewById(R.id.bookingconfirmationno);
            custdetails = (TextView) view.findViewById(R.id.custdetails);
            createdate=(TextView)view.findViewById(R.id.createdate);
            cardview=(CardView)view.findViewById(R.id.innercardview);
            sourcetype=(TextView)view.findViewById(R.id.source);
            pnrvalue=(TextView) view.findViewById(R.id.pnrvalue);
            phonenovalue=(TextView)view.findViewById(R.id.phoneno);
            statusvalue=(TextView)view.findViewById(R.id.statusvalue);
        }
    }



    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private class NoResultViewHolder extends RecyclerView.ViewHolder {


        public NoResultViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }




    public AllTicketBookingsAdapter(List<TicketBookingsResponse> tiketbookinglist,Context ctx,String bookingtype,int viewid,int loadlimit) {
        this.tiketbookinglist = tiketbookinglist;
        this.itemsFilteredtiketbookinglist=tiketbookinglist;
        this.ctx=ctx;
        this.loadlimit=loadlimit;
        this.viewid=viewid;
        this.bookingtype=bookingtype;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(viewid, parent, false);
            return new MyViewHolder(itemView);
        } else if(viewType == VIEW_TYPE_LOADING){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemloading, parent, false);
            return new LoadingViewHolder(itemView);

        }
        else
        {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.noresultsfound, parent, false);
            return new NoResultViewHolder(itemView);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            TicketBookingsResponse  ticketbookingobj = itemsFilteredtiketbookinglist.get(position);
            holder.itemView.setTag(position);
            ((MyViewHolder) holder).citieslable.setText(ticketbookingobj.getFromcity()+" - "+ticketbookingobj.getTocity());
            String triptypevalue="";
            String sourcetype="";
            String pnrvaluestr="";
            pnrvaluestr= ticketbookingobj.getPnrvalue();
            if(pnrvaluestr.equals(""))
            {
                pnrvaluestr="NA";
            }
            if(ticketbookingobj.getTriptype().equalsIgnoreCase("2"))
            {
                triptypevalue="Round Trip";
            }
            else if(ticketbookingobj.getTriptype().equalsIgnoreCase("1"))
            {
                triptypevalue="One Way";
            }
            else {
                triptypevalue="Multicity";
            }
            String bookingcreatedstr=  ticketbookingobj.getCreated();
            sourcetype=ticketbookingobj.getSourcetype();
            if(sourcetype=="null")
            {
                sourcetype="";
            }

            ((MyViewHolder) holder).triptype.setText(triptypevalue);


            ((MyViewHolder) holder).sourcetype.setText("Source:"+sourcetype);
            ((MyViewHolder) holder).phonenovalue.setText("Phno:"+ticketbookingobj.getPhonevalue());
            ((MyViewHolder) holder).statusvalue.setText("status: "+ticketbookingobj.getBookingstatus());


            ((MyViewHolder) holder).status.setText("Booking Status: "+ticketbookingobj.getBookingstatus());
            ((MyViewHolder) holder).cardview.setCardBackgroundColor(Color.parseColor(ticketbookingobj.getColorvalue()));


/*
            ((MyViewHolder) holder).cardview.setCardBackgroundColor(ticketbookingobj.getColorvalue());
*/





            ((MyViewHolder) holder).confirmationno.setText("Conf#: "+ticketbookingobj.getConfirmationid());

            ((MyViewHolder) holder).bookingconfirmationno.setText("Bk#: "+ticketbookingobj.getFbid());

            ((MyViewHolder) holder).custdetails.setText("Name: "+ticketbookingobj.getFirstname()+" "+ticketbookingobj.getLastname()
                    +" Emailid: "+ticketbookingobj.getEmailvalue());

            ((MyViewHolder) holder).createdate.setText("Created: "+bookingcreatedstr);
            ((MyViewHolder) holder).pnrvalue.setText("PNR: "+pnrvaluestr);
            if(!(ticketbookingobj.getSourcetype().equalsIgnoreCase("TGDS-Process"))) {

                ((MyViewHolder) holder).pnrvalue.setTextColor(Color.parseColor("#265094"));
                ((MyViewHolder) holder).bookingconfirmationno.setTextColor(Color.parseColor("#265094"));

                ((MyViewHolder) holder).confirmationno.setTextColor(Color.parseColor("#265094"));
                ((MyViewHolder) holder).statusvalue.setTextColor(Color.parseColor("#265094"));
            }
            else
            {
                ((MyViewHolder) holder).pnrvalue.setTextColor(Color.parseColor("#ffffff"));
                ((MyViewHolder) holder).bookingconfirmationno.setTextColor(Color.parseColor("#ffffff"));

                ((MyViewHolder) holder).confirmationno.setTextColor(Color.parseColor("#ffffff"));
                ((MyViewHolder) holder).statusvalue.setTextColor(Color.parseColor("#ffffff"));
            }

            ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();

                    TicketBookingsResponse  ticketbookingobj = itemsFilteredtiketbookinglist.get(position);
                    Intent i=new Intent(ctx, TicketDetails.class);
                    Bundle b=new Bundle();
                    b.putString("itinselid",ticketbookingobj.getItinselid().toString());
                    b.putString("bookingid",ticketbookingobj.getBookingid().toString());
                    b.putString("triptype",ticketbookingobj.getTriptype().toString());

                    i.putExtras(b);
                    ctx.startActivity(i);
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(itemsFilteredtiketbookinglist.size()>0&&itemsFilteredtiketbookinglist.size()>position&&itemsFilteredtiketbookinglist.size()>0
        )
        {
            return itemsFilteredtiketbookinglist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }
        else if(noresultsfound){

            return VIEW_TYPE_NORESULT;
        }
        else
        {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        noresultsfound=false;
        if(itemsFilteredtiketbookinglist.size()<loadlimit&&itemsFilteredtiketbookinglist.size()>=1)
        {
            return itemsFilteredtiketbookinglist.size();
        }
        else if(itemsFilteredtiketbookinglist.size()<1)
        {
            noresultsfound=true;
            return 1;
        }
        return loadlimit;
    }
    public String getCreatedHours(String datevalue){
        String bookingcreatedstr="";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
            Date createddate=   sdf.parse(datevalue);
            String todaydatestr=  sdf.format(new Date());
            Date todaydate=sdf.parse(todaydatestr);
            long diff =todaydate.getTime()-createddate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
            if(diffDays>0)
            {
                if(diffDays>=365)
                {
                    Long l=new Long(diffDays);
                    int diffdayint=l.intValue();
                    int noofyears=diffdayint/365;
                    bookingcreatedstr=noofyears + " year ago";

                }
                else if(diffDays>=30)
                {
                    Long l=new Long(diffDays);
                    int diffdayint=l.intValue();
                    int noofmonths=diffdayint/30;
                    bookingcreatedstr=noofmonths + " months ago";

                }
                else if(diffDays>=7)
                {
                    Long l=new Long(diffDays);
                    int diffdayint=l.intValue();
                    int noofweek=diffdayint/7;
                    bookingcreatedstr=noofweek + " weeks ago";

                }
                else
                {
                    bookingcreatedstr=diffDays + " days ago";

                }

            }
            else if(diffHours>0)
            {
                bookingcreatedstr=diffHours + " hours ago";

            }
            else if(diffMinutes>0)
            {
                bookingcreatedstr=diffMinutes + " minutes ago";

            }
            else
            {
                bookingcreatedstr="just now";

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return bookingcreatedstr;
    }


    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }
    public void setLoadlimit(int loadlimit)
    {
        this.loadlimit=loadlimit;
    }
    public void setFiltertype(String filtertype)
    {
        this.filtertype=filtertype;
    }
}
