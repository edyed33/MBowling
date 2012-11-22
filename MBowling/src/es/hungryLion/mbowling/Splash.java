package es.hungryLion.mbowling;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.VideoView;

/**
 * Clase que sirve para crear una pantalla de inicio de una aplicaci�n, que
 * estar� activa durante 5 segundos, y acontinuaci�n inicia a la pantalla de
 * login
 * 
 * @author Hungry Lion
 * 
 */
public class Splash extends Activity {

	// Nos indica la duraci�n del splash en ms
	private final int DURACION_SPLASH = 5000;

	/**
	 * El m�todo onCreate() es invocado cuando la actividad se inicia.
	 * 
	 * @param savedInstanceState El m�todo onCreate() recibe como par�metro un objeto de la 
	 * clase Bundle, que contiene el estado anterior de la actividad en caso de que haya sido
	 * suspendida. Seg�n el ciclo de vida de una actividad, despu�s de que iniciamos un 
	 * actividad podemos pausarla o detenerla de forma moment�nea si el usuario est� realizando
	 * otras funciones en el tel�fono. Si esto ocurre, la actividad deber� iniciarse nuevamente
	 * y es entonces cuando la informaci�n de este Bundle nos resulta de utilidad.
	 * 
	 */
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Quita la barra de titulo del activity
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		
		//Cargamos el video de inicio de la aplicaci�n para mostrarlo en la pantalla de splash
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		// Uri path=Uri.parse("android.resource://es.hungryLion.mbowling/" + R.raw.bolos);
		Uri path = Uri.parse("android.resource://" + getPackageName() + "/"	+ R.raw.videobolos);

		videoView.setVideoURI(path);		
		videoView.start();		
		//Aplica un retraso (postDelayed) de 5 seg(DURCION_SPLASH) a un manejador (handler)
		 esperarYCerrar(DURACION_SPLASH);		
	}
	 
	 	/**
		 * M�todo que inicia la actividad de Login y destruye la actividad de Splash 5 milisegundos
		 */
		public void esperarYCerrar(int DURACION_SPLASH) {
	        Handler handler = new Handler();
	        handler.postDelayed(new Runnable() {

			/**
			 * M�todo que inicia la actividad de Login y destruye la actividad de Splash
			 * En este m�todo est�n las acciones que se ejecutan tras los 5 milisegundos
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
