package com.rmartinez.demointentservice;

import android.app.IntentService;
import android.content.Intent;

public class MiIntentService extends IntentService{

	public static final String ACTION_PROGRESO = "com.rmartinez.intent.action.PROGRESO";
	public static final String ACTION_FIN = "com.rmartinez.intent.action.FIN";
	 
	    public MiIntentService() {
	            super("MiIntentService");
	        }
	
	public MiIntentService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		int iter = intent.getIntExtra("iteraciones", 0);
		
		for(int i=0; i<=iter; i++)
		{
			tareaLarga();
			
			Intent bcIntent = new Intent();
			bcIntent.setAction(ACTION_PROGRESO);
			bcIntent.putExtra("progreso", i*10);
			sendBroadcast(bcIntent);
		}
		
		Intent bcIntent = new Intent();
		bcIntent.setAction(ACTION_FIN);
		sendBroadcast(bcIntent);
	}
	
	private void tareaLarga()
    {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }

}
