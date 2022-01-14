package com.bkacad.nnt.contactappsqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkacad.nnt.contactappsqlite.R;
import com.bkacad.nnt.contactappsqlite.model.Contact;

import java.util.List;

public abstract class MyAdapter extends BaseAdapter {

    public abstract void listenerItemMenuClick(int position);

    private Context context;
    private List<Contact> contactList;

    public MyAdapter(Context context , List<Contact> contactList){
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        }
        // Bind id
        TextView tvName, tvPhone;
        tvName = convertView.findViewById(R.id.tv_item_contact_name);
        tvPhone = convertView.findViewById(R.id.tv_item_contact_phone);

        // Đổ dữ liệu vào view
        tvName.setText(contactList.get(position).getName());
        tvPhone.setText(contactList.get(position).getPhone());

        // Sự kiện khi click vào menu
        ImageView imgMenu = convertView.findViewById(R.id.img_item_contact_menu);
        // Sự kiện khi click vào imgMenu (Xu ly sau)
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerItemMenuClick(position);
            }
        });

        return convertView;
    }
}
