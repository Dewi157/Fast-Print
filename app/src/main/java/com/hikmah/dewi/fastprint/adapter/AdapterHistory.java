package com.hikmah.dewi.fastprint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.model.History;

import java.util.ArrayList;

/**
 * Created by dewi on 10/31/2017.
 */

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    private ArrayList<History> history;
    private Context mContext;

    public AdapterHistory(ArrayList<History> history, Context mContext) {
        this.history = history;
        this.mContext = mContext;
    }

    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_history, parent, false));
}

    @Override
    public void onBindViewHolder(AdapterHistory.ViewHolder holder, int position) {
        History currentHistory = history.get(position);
        holder.bindTo(currentHistory);
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //Member Variables for the TextViews
        private TextView id_order_list;
        private TextView tanggal_rp;
        private TextView harga_rp;
        private TextView status_rp;
        private History currentHistory;
        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file
         */
        ViewHolder(View itemView) {
            super(itemView);
            //Initialize the views
            id_order_list = (TextView) itemView.findViewById(R.id.id_order_list);
            tanggal_rp = (TextView) itemView.findViewById(R.id.hari_rp);
            harga_rp = (TextView) itemView.findViewById(R.id.harga_rp);
            status_rp = (TextView) itemView.findViewById(R.id.status_rp);
        }

        void bindTo(History currentHistory) {
            //Populate the textviews with data
            id_order_list.setText(currentHistory.getNama_rp());
            tanggal_rp.setText(currentHistory.getTanggal_rp());
            harga_rp.setText("Rp."+currentHistory.getBayar_rp());
            status_rp.setText(currentHistory.getStatus_rp());
            //Get the current sport
            this.currentHistory = currentHistory;
        }
    }
}
