package com.hieunt.ksoapapi3;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Array;
import java.util.ArrayList;

import config.Configuration;
import model.Contact;

public class MainActivity extends AppCompatActivity {
    Button btnLay;
    ProgressDialog progressDialog;
    ListView lvConTact;
    ArrayList<Contact> dsContact;
    ArrayAdapter<Contact> adapterConTact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLayDanhSach();
            }
        });
    }

    private void xuLyLayDanhSach() {
        ListContactTask task=new ListContactTask();
        task.execute();
    }
    class ListContactTask extends AsyncTask<Void,Void,ArrayList<Contact>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapterConTact.clear();
            progressDialog.dismiss();

        }

        @Override
        protected void onPostExecute(ArrayList<Contact> contacts) {
            super.onPostExecute(contacts);
            adapterConTact.clear();
            adapterConTact.addAll(contacts);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override

        protected ArrayList<Contact> doInBackground(Void... params) {
            ArrayList<Contact>ds=new ArrayList<>();
            try
            {
                SoapObject request=new SoapObject(Configuration.NAME_SPACE,Configuration.METHOD_GET_5CONTACT);
                SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransportSE=new HttpTransportSE(Configuration.SERVER_URL);
                httpTransportSE.call(Configuration.SOAP_ACTION_GET_DETAIL,envelope);

                SoapObject data= (SoapObject) envelope.getResponse();
                for(int i=0;i<data.getPropertyCount();i++)
                {
                    SoapObject soapObject= (SoapObject) data.getProperty(i);
                    Contact contact=new Contact();
                    if(soapObject.hasProperty("Ma"))
                        contact.setMa(Integer.parseInt(soapObject.getPropertyAsString("Ma")));
                    if(soapObject.hasProperty("Ten"))
                        contact.setTen(soapObject.getPropertyAsString("Ten"));
                    if(soapObject.hasProperty("Phone"))
                        contact.setPhone(soapObject.getPropertyAsString("Phone"));
                    ds.add(contact);
                }
            }
            catch (Exception ex)
            {
                Log.e("LOI",ex.toString());
            }
            return ds;
        }
        }

    private void addControls() {
        btnLay= (Button) findViewById(R.id.btnLayDanhSach);
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Đang tải danh sách Contact, vui lòng chờ...");
        progressDialog.setCanceledOnTouchOutside(false);

        lvConTact = (ListView) findViewById(R.id.lvContact);
        dsContact=new ArrayList<>();
        adapterConTact=new ArrayAdapter<Contact>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                dsContact);
        lvConTact.setAdapter(adapterConTact);
    }
}
