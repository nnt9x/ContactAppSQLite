package com.bkacad.nnt.contactappsqlite.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.bkacad.nnt.contactappsqlite.R;
import com.bkacad.nnt.contactappsqlite.model.Contact;

public abstract class NewContactDialog extends Dialog {

    public NewContactDialog(@NonNull Context context) {
        super(context);
    }

    public abstract void sendDataFromDialog(Contact contact);

    private EditText edtName, edtPhone, edtAddress;
    private Button btnSave, btnCancel;

    private void initUI(){
        edtAddress = findViewById(R.id.edt_dialog_address);
        edtName = findViewById(R.id.edt_dialog_name);
        edtPhone = findViewById(R.id.edt_dialog_phone);
        btnCancel = findViewById(R.id.btn_dialog_cancel);
        btnSave = findViewById(R.id.btn_dialog_save);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_contact);
        setCancelable(false);
        initUI();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                if(name.isEmpty()){
                    edtName.setError("Hãy nhập tên");
                    return;
                }
                String phone = edtPhone.getText().toString();
                if(phone.isEmpty()){
                    edtName.setError("Hãy nhập sdt");
                    return;
                }
                String address = edtName.getText().toString();

                Contact contact = new Contact();
                contact.setName(name);
                contact.setPhone(phone);
                contact.setAddress(address);

                sendDataFromDialog(contact);
                dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
