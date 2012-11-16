package es.hungryLion.mbowling;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Esta clase muestra las mayores puntuaciones obtenidas en la bolera por el socio, 
 * mostrando el d�a y la hora en las que fueron obtenidas. 
 * 
 * @author Hungry Lion
 *  
 */
public class RankingPersonal extends Activity {		
	private ListView lstPuntuaciones;   //Referencia a la lista de puntuaciones que mostraremos por pantalla
	private String tmp;					// String en el que almacenamos los datos obtenidos del 
										//fichero login.txt. De aqu� obtenemos el c�digo del 
										//cliente para realizar la b�squeda.
	private Button bSalirRanking;		//Referencia al bot�n volver del activity
	//private ManejoFichero fichero;		//Referencia un objeto ManejoFichero, para poder utilizar 
										//el m�todo leer "fichero"
	private ManejoFichero fichero;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking_personal);
		
		
	
		bSalirRanking= (Button) findViewById(R.id.bExit);
		//Cambia el color del bot�n volver
		
		
		//Listener del bot�n volver, que sirve para volver al activity anterior
		bSalirRanking.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		 finish();
	        	}
	    });
	     
		// Recupera del fichero login.txt el cod_cliente asociado al DNI del usuario,
		//para poder enviarlos al servicio como par�metro de entrada
		fichero=new ManejoFichero(this);
		tmp = fichero.leer();
		
		//Devuelve en un array las palabras separadas por un espacio
		String tokens[] = tmp.split(" ");
		String codCliente = tokens[2];
		consultar(codCliente);

		
	}
	
	/**
	 * Se accede al servicio web ranking personal, utilizando cadenas json enviadas por REST.
	 * En este caso s�lo utilizamos Get, ya que s�lo se consultan datos.
	 * 
	 * @param codCliente Le pasamos un String con el c�digo del cliente para que el servicio 
	 * pueda realizar la consulta
	 * 
	 * @return void
	 */
	private void consultar(String codCliente) {
		/*
		 * Android incluye todo lo necesario para realizar la conexi�n y llamada
		 * a los m�todos del servicio, y tratamiento de resultados en formato
		 * JSON.
		 */

		// Crea nuevo objeto HttpClient, que ser� el encargado de realizar la
		// comunicaci�n HTTP con el
		// servidor a partir de los datos que nosotros le proporcionemos.
		HttpClient httpClient = new DefaultHttpClient();

		
		/*
		 * Te ha pasado que est�s desarrollando una aplicaci�n android que debe
		 * comunicarse con otra aplicaci�n en un web server, pero tanto la
		 * aplicaci�n android y la web est� en tu misma m�quina porque ambas se
		 * encuentran en desarrollo?
		 * 
		 * Bueno, a m� s� en algunas ocasiones y siempre me sucede lo mismo.
		 * �C�mo le hago para acceder a localhost de mi PC desde el emulador de
		 * Android? Y por esa raz�n he decidido escribirlo para que ya no se me
		 * olvide.
		 * 
		 * Si desde tu c�digo android pones localhost entonces nunca llegar�s a
		 * tu aplicaci�n que se encuentra en tu m�quina bajo el dominio
		 * localhost. La raz�n es porque cuando haces referencia a localhost
		 * est�s haciendo referencia al emulador mismo y entonces busca tu
		 * aplicaci�n web dentro del mismo emulador!
		 * 
		 * La soluci�n es muy sencilla, en lugar de poner localhost para tener
		 * acceso al web server de tu PC debes poner la direcci�n IP de tu
		 * ordenador, en mi caso: 192.168.1.8
		 */

		/*
		 * Creamos la petici�n GET creando un nuevo objeto HttpGet e indicando
		 * la URL de llamada al servicio. Modificaremos mediante setHeader() el
		 * atributo http content-type para indicar que el formato de los datos
		 * que utilizaremos en la comunicaci�n, que ser� JSON (cuyo MIME-Type
		 * correspondiente es �application/json�).
		 */

		HttpGet peticion = new HttpGet("http://www.edyed33.decatarroja.es/WSDone/ranking_personal.php/json/?cod_cliente="+codCliente);

		peticion.setHeader("content-type", "application/json");

		try {

			/*
			 * Una vez creada nuestra petici�n HTTP y asociado el dato de
			 * entrada, tan s�lo nos queda realizar la llamada al servicio
			 * mediante el m�todo execute() del objeto HttpClient y recuperar el
			 * resultado mediante getEntity(). Este resultado lo recibimos en
			 * forma de objeto HttpEntity, pero lo podemos convertir f�cilmente
			 * en una cadena de texto mediante el m�todo est�tico
			 * EntityUtils.toString().
			 */

			HttpResponse resp = httpClient.execute(peticion);
			String respStr = EntityUtils.toString(resp.getEntity());			
			/*
			 * Creamos un nuevo objeto JSONObject a partir del resultado textual
			 * de getEntity(). Hecho esto, podremos acceder a los atributos del
			 * objeto utilizando para ello los m�todos get() correspondientes,
			 * seg�n el tipo de cada atributo (getInt(), getString(), etc). Tras
			 * esto mostraremos los datos del cliente recuperado
			 */

			JSONArray respJSON = new JSONArray(respStr);			
			
			int n=respJSON.length();			
			
			String[] puntuaciones = new String[n];				
			
			
			for (int i = 0; i < respJSON.length(); i++) {				
				
				JSONObject obj = respJSON.getJSONObject(i);

				String fecha = obj.getString("fecha");
				int puntuacion = obj.getInt("puntuacion");

				puntuaciones[i] = "" + puntuacion + "		  " + fecha;			
			}

			// Rellenamos la lista con los resultados
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, puntuaciones);
			ListView lstPuntuaciones = (ListView)findViewById(R.id.listView1);
			lstPuntuaciones.setAdapter(adaptador);

		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
		}

	}
}
