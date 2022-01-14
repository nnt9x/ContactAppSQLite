package com.bkacad.nnt.contactappsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bkacad.nnt.contactappsqlite.adapter.MyAdapter;
import com.bkacad.nnt.contactappsqlite.database.DAO;
import com.bkacad.nnt.contactappsqlite.database.DBHelper;
import com.bkacad.nnt.contactappsqlite.dialog.NewContactDialog;
import com.bkacad.nnt.contactappsqlite.model.Contact;
import com.bkacad.nnt.contactappsqlite.model.ContactDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private EditText edtKeyword;
    private ListView lvContact;
    private MyAdapter myAdapter;
    private List<Contact> contactList;
    private NewContactDialog contactDialog = null;

    private DBHelper dbHelper;
    private ContactDAO contactDAO;

    private  int position = 0;
    private  boolean itemClickFromAdapter = false;

    private void initUI(){
        btnAdd = findViewById(R.id.btn_main_add);
        edtKeyword = findViewById(R.id.edt_main_keyword);
        lvContact = findViewById(R.id.lv_main_contacts);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        dbHelper = new DBHelper(this);
        contactDAO = new ContactDAO(dbHelper);
        //
        contactList = contactDAO.all();

        myAdapter = new MyAdapter(this, contactList) {
            @Override
            public void listenerItemMenuClick(int position) {
                itemClickFromAdapter = true;
                MainActivity.this.position = position;
                lvContact.showContextMenu();
            }
        };
        lvContact.setAdapter(myAdapter);

        registerForContextMenu(lvContact);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactDialog == null){
                    contactDialog = new NewContactDialog(MainActivity.this) {
                        @Override
                        public void sendDataFromDialog(Contact contact) {
                            // Them vao db truoc, thanh cong moi cap nhat len listview
                            long id = contactDAO.create(contact);
                            if(id == -1){
                                Toast.makeText(MainActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            contact.setId(id);
                            // Thêm vào database -> cập nhật vào listview
                            contactList.add(contact);
                            myAdapter.notifyDataSetChanged();
                        }
                    };
                }
                contactDialog.show();
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_list_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // Lấy vị trí item listview click
        if(!itemClickFromAdapter){
        position = contextMenuInfo.position;}

        switch (item.getItemId()){
            case R.id.context_list_menu_details:
                // Chuyển sang Details Activity
                break;
            case R.id.context_list_menu_edit:
                // Show dialog cho phép sửa các thông tin
                break;

            case R.id.context_list_menu_delete:
                // Xoá
                int rs = contactDAO.delete(contactList.get(position));
                if(rs == 1) {
                    contactList.remove(position);
                    myAdapter.notifyDataSetChanged();
                    // Tự thêm alert dialog để confirm xoá
                    Toast.makeText(MainActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        itemClickFromAdapter = false;
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}