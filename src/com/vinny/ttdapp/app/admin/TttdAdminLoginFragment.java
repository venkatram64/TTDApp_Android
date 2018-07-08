package com.vinny.ttdapp.app.admin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vinny.ttdapp.R;
import com.vinny.ttdapp.admin.util.AlertDialogManager;
import com.vinny.ttdapp.admin.util.SessionManager;

public class TttdAdminLoginFragment extends Fragment implements OnClickListener{
	
	Context context;
	TtdPendingListHandler pendingListHandler;
	
	// Email, password edittext 
    EditText txtUsername, txtPassword; 
      
    // login button 
    Button btnLogin; 
      
    // Alert Dialog Manager 
    AlertDialogManager alert = new AlertDialogManager(); 
      
    // Session Manager Class 
    SessionManager session; 
    
    @Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			pendingListHandler = (TtdPendingListHandler) getActivity();
		}catch(ClassCastException ex){
			Log.d("TtdProcessFragment : " , getActivity().getClass().getSimpleName() + 
					" not implementd " + TtdPendingListHandler.class.getSimpleName());
		}
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.ttd_admin_login_fragment, container,false);
		 // Session Manager 
        session = new SessionManager(context);                 
          
        // Email, Password input text 
        txtUsername = (EditText) view.findViewById(R.id.txtUsername); 
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);  

     // Login button 
        btnLogin = (Button) view.findViewById(R.id.btnLogin); 

     // Login button click event 
        btnLogin.setOnClickListener(this); 
		return view;
		
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.btnLogin:
			// Get username, password from EditText 
            String username = txtUsername.getText().toString(); 
            String password = txtPassword.getText().toString(); 
              
            // Check if username, password is filled                 
            if(username.trim().length() > 0 && password.trim().length() > 0){ 
                // For testing puspose username, password is checked with sample data 
                // username = test 
                // password = test 
                if(username.equals("admin") && password.equals("admin")){ 
                      
                    // Creating user login session 
                    // For testing i am stroing name, email as follow 
                    // Use user real data 
                    session.createLoginSession("Android Hive", "anroidhive@gmail.com"); 
                    
                    pendingListHandler.onPendingListHandler();
                      
                }else{ 
                    // username / password doesn't match 
                    alert.showAlertDialog(context, "Login failed..", "Username/Password is incorrect", false); 
                }                
            }else{ 
                // user didn't entered username or password 
                // Show alert asking him to enter the details 
                alert.showAlertDialog(context, "Login failed..", "Please enter username and password", false); 
            }
            return;
		}
	}
	
	interface TtdPendingListHandler{
		void onPendingListHandler();
	}
	

}
