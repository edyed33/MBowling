package es.hungryLion.mbowling;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.VideoView;

/**
 * Clase que sirve para crear una pantalla de inicio de una aplicación, que
 * estará activa durante 5 segundos, y acontinuación inicia a la pantalla de
 * login
 * 
 * @author Hungry Lion
 * 
 */
public class Splash extends Activity {

	// Nos indica la duración del splash en ms
	private final int DURACION_SPLASH = 5000;

	/**
	 * El método onCreate() es invocado cuando la actividad se inicia.
	 * 
	 * @param savedInstanceState El método onCreate() recibe como parámetro un objeto de la 
	 * clase Bundle, que contiene el estado anterior de la actividad en caso de que haya sido
	 * suspendida. Según el ciclo de vida de una actividad, después de que iniciamos un 
	 * actividad podemos pausarla o detenerla de forma momentánea si el usuario está realizando
	 * otras funciones en el teléfono. Si esto ocurre, la actividad deberá iniciarse nuevamente
	 * y es entonces cuando la información de este Bundle nos resulta de utilidad.
	 * 
	 */
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Quita la barra de titulo del activity
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		
		//Cargamos el video de inicio de la aplicación para mostrarlo en la pantalla de splash
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		// Uri path=Uri.parse("android.resource://es.hungryLion.mbowling/" + R.raw.bolos);
		Uri path = Uri.parse("android.resource://" + getPackageName() + "/"	+ R.raw.videobolos);

		videoView.setVideoURI(path);		
		videoView.start();		
		//Aplica un retraso (postDelayed) de 5 seg(DURCION_SPLASH) a un manejador (handler)
		 esperarYCerrar(DURACION_SPLASH);		
	}
	 
	 	/**
		 * Método que inicia la actividad de Login y destruye la actividad de Splash 5 milisegundos
		 */
		public void esperarYCerrar(int DURACION_SPLASH) {
	        Handler handler = new Handler();
	        handler.postDelayed(new Runnable() {

			/**
			 * Método que inicia la actividad de Login y destruye la actividad de Splash
			 * En este método están las acciones que se ejecutan tras los 5 milisegundos
			 */
			public void run() {
				// Inicia la activity de Login
				Intent intent = new Intent(Splash.this, Login.class);
				startActivity(intent);
				// Destruye la activity Splash
				finish();
			};
		}, DURACION_SPLASH);
		}
}
