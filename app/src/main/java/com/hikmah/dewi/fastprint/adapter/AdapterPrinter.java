package com.hikmah.dewi.fastprint.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.model.Jarak;

import java.util.List;

/**
 * Created by dewi on 9/25/2017.
 */

public class AdapterPrinter extends BaseAdapter {
    private Fragment activity;
    private LayoutInflater inflater;
    private List<Jarak> items;
    public AdapterPrinter(Fragment activity, List<Jarak> items) {
        this.activity = activity;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (inflater == null)
        inflater = (LayoutInflater)activity.getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_printer, null);
        TextView id = (TextView)convertView.findViewById(R.id.id_rp_list);
        TextView nama = (TextView) convertView.findViewById(R.id.nama_list);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat_list);
        TextView jarak = (TextView) convertView.findViewById(R.id.jarak_list);
        Jarak data = items.get(position);
        id.setText(data.getId_rp());
        nama.setText(data.getNama());
        alamat.setText(data.getAlamat());
        jarak.setText(data.getJarak()+" KM");
        return convertView;
    }
}
