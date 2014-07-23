package com.rmartinez.demointentservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btnEjecutar;
	private ProgressBar pbarProgreso;
	
	private ProgressDialog ringProgressDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnEjecutar = (Button)findViewById(R.id.btnEjecutar);
        pbarProgreso = (ProgressBar)findViewById(R.id.pbarProgreso);
        
        btnEjecutar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent mgIntent = new Intent(MainActivity.this, MiIntentService.class);
				mgIntent.putExtra("iteraciones", 10);
				startService(mgIntent);
				
				ringProgressDialog = ProgressDialog.show(
						MainActivity.this, 
						"Please wait ...", 
						"Downloading Image ...", 
						true);
			}
		});
        
        IntentFilter filter = new IntentFilter();
        filter.addAction(MiIntentService.ACTION_PROGRESO);
        filter.addAction(MiIntentService.ACTION_FIN);
        
        ProgressReceiver rcv = new ProgressReceiver();
        registerReceiver(rcv, filter);
    }
    
    public class ProgressReceiver extends BroadcastReceiver
    {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			
			if(intent.getAction().equals(MiIntentService.ACTION_PROGRESO))
			{
				int prog = intent.getIntExtra("progreso", 0);
				pbarProgreso.setProgress(prog);
			}
			else if(intent.getAction().equals(MiIntentService.ACTION_FIN))
			{
				Toast.makeText(MainActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
				ringProgressDialog.dismiss();
			}
		}
    }
}
