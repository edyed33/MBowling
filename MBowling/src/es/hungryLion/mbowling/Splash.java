package es.hungryLion.mbowling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

	// Nos indica la duración del splash en ms
			private final int DURACION_SPLASH=2000;

			/** Called when the activity is first created. */
			    
			    public void onCreate(Bundle savedInstanceState) {
			        super.onCreate(savedInstanceState);
			        setContentView(R.layout.splash);
			        
			        new Handler().postDelayed(new Runnable(){
			        	
			        	public void run(){
			        		// Pasados los 5 segundos inicia la activity
			        		Intent intent=new Intent(Splash.this, Login.class);
			        		startActivity(intent);
			        		// Destruye esta activity
			        		finish();
			        	};
			        }, DURACION_SPLASH);	
			        
			    }
	}
