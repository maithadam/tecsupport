package com.nanobite.nanobite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 5/10/17.
 */

public class NanoSupportFragment extends Fragment{

    View supportview;
    String subject_str;
    String name_str;
    String number_str;
    String email_str;
    String issue_str;
    EditText subject_edt,name_edt,number_edt,email_edt,issue_edt;
    Button send_btn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



            supportview = inflater.inflate(R.layout.fragment_support, container, false);
            subject_edt = (EditText) supportview.findViewById(R.id.subject_editText);
            name_edt = (EditText) supportview.findViewById(R.id.name_editText);
            number_edt = (EditText) supportview.findViewById(R.id.number_editText);
            email_edt = (EditText) supportview.findViewById(R.id.email_editText);
            issue_edt = (EditText) supportview.findViewById(R.id.issue_editText);
            send_btn = (Button) supportview.findViewById(R.id.sent_button);
            send_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Internet_Connections.checkConnection(getActivity().getApplicationContext())) {

                        subject_str = subject_edt.getText().toString();
                        name_str = name_edt.getText().toString();
                        number_str = number_edt.getText().toString();
                        email_str = email_edt.getText().toString();
                        issue_str = issue_edt.getText().toString();
                        new SendSupport().execute();
                    }

                    else{

                    Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        return supportview;



    }


    //insert into support table on the server
    //Asynchronous task
    class SendSupport extends AsyncTask<Void, Void, Void>
    {

        ProgressDialog loading;
        boolean success;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getActivity(), "Sending...", null, true, true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loading.dismiss();

            if(success) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Dialog).create();
                alertDialog.setTitle("successful");
                alertDialog.setMessage("Support Request Successful,Will Contact you soon");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                subject_edt.setText("");
                                name_edt.setText("");
                                number_edt.setText("");
                                email_edt.setText("");
                                issue_edt.setText("");

                            }
                        });
                alertDialog.show();
            }
            else{

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Dialog).create();
                alertDialog.setTitle("Un successful");
                alertDialog.setMessage("Support Request un Successful,Try Again");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        }



        @Override

        protected Void doInBackground(Void... params){

            String urls = "http://www.nanobitetec.com/nanosupport/supports.php";
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("subject",subject_str ));
            param.add(new BasicNameValuePair("name",name_str));
            param.add(new BasicNameValuePair("number",number_str));
            param.add(new BasicNameValuePair("email",email_str));
            param.add(new BasicNameValuePair("issue",issue_str));
            ServiceHandler serviceClient = new ServiceHandler();
            String json = serviceClient.makeServiceCall(urls, ServiceHandler.POST, param);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);

                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {
                        // new category created successfully

                        success=true;


                    } else {
                        Log.e("Create Category Error: ", "> " + jsonObj.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else {

                Log.e("JSON Data", "Didn't receive any data from server!");
            }


            return null;
        }

    }

}
