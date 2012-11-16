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
 * mostrando el día y la hora en las que fueron obtenidas. 
 * 
 * @author Hungry Lion
 *  
 */
public class RankingPersonal extends Activity {		
	private ListView lstPuntuaciones;   //Referencia a la lista de puntuaciones que mostraremos por pantalla
	private String tmp;					// String en el que almacenamos los datos obtenidos del 
										//fichero login.txt. De aquí obtenemos el código del 
										//cliente para realizar la búsqueda.
	private Button bSalirRanking;		//Referencia al botón volver del activity
	//private ManejoFichero fichero;		//Referencia un objeto ManejoFichero, para poder utilizar 
										//el método leer "fichero"
	private ManejoFichero fichero;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking_personal);
		
		
	
		bSalirRanking= (Button) findViewById(R.id.bExit);
		//Cambia el color del botón volver
		
		
		//Listener del botón volver, que sirve para volver al activity anterior
		bSalirRanking.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		 finish();
	        	}
	    });
	     
		// Recupera del fichero login.txt el cod_cliente asociado al DNI del usuario,
		//para poder enviarlos al servicio como parámetro de entrada
		fichero=new ManejoFichero(this);
		tmp = fichero.leer();
		
		//Devuelve en un array las palabras separadas por un espacio
		String tokens[] = tmp.split(" ");
		String codCliente = tokens[2];
		consultar(codCliente);

		
	}
	
	/**
	 * Se accede al servicio web ranking personal, utilizando cadenas json enviadas por REST.
	 * En este caso sólo utilizamos Get, ya que sólo se consultan datos.
	 * 
	 * @param codCliente Le pasamos un String con el código del cliente para que el servicio 
	 * pueda realizar la consulta
	 * 
	 * @return void
	 */
	private void consultar(String codCliente) {
		/*
		 * Android incluye todo lo necesario para realizar la conexión y llamada
		 * a los métodos del servicio, y tratamiento de resultados en formato
		 * JSON.
		 */

		// Crea nuevo objeto HttpClient, que será el encargado de realizar la
		// comunicación HTTP con el
		// servidor a partir de los datos que nosotros le proporcionemos.
		HttpClient httpClient = new DefaultHttpClient();

		
		/*
		 * Te ha pasado que estás desarrollando una aplicación android que debe
		 * comunicarse con otra aplicación en un web server, pero tanto la
		 * aplicación android y la web está en tu misma máquina porque ambas se
		 * encuentran en desarrollo?
		 * 
		 * Bueno, a mí sí en algunas ocasiones y siempre me sucede lo mismo.
		 * ¿Cómo le hago para acceder a localhost de mi PC desde el emulador de
		 * Android? Y por esa razón he decidido escribirlo para que ya no se me
		 * olvide.
		 * 
		 * Si desde tu código android pones localhost entonces nunca llegarás a
		 * tu aplicación que se encuentra en tu máquina bajo el dominio
		 * localhost. La razón es porque cuando haces referencia a localhost
		 * estás haciendo referencia al emulador mismo y entonces busca tu
		 * aplicación web dentro del mismo emulador!
		 * 
		 * La solución es muy sencilla, en lugar de poner localhost para tener
		 * acceso al web server de tu PC debes poner la dirección IP de tu
		 * ordenador, en mi caso: 192.168.1.8
		 */

		/*
		 * Creamos la petición GET creando un nuevo objeto HttpGet e indicando
		 * la URL de llamada al servicio. Modificaremos mediante setHeader() el
		 * atributo http content-type para indicar que el formato de los datos
		 * que utilizaremos en la comunicación, que será JSON (cuyo MIME-Type
		 * correspondiente es “application/json“).
		 */

		HttpGet peticion = new HttpGet("http://www.edyed33.decatarroja.es/WSDone/ranking_personal.php/json/?cod_cliente="+codCliente);

		peticion.setHeader("content-type", "application/json");

		try {

			/*
			 * Una vez creada nuestra petición HTTP y asociado el dato de
			 * entrada, tan sólo nos queda realizar la llamada al servicio
			 * mediante el método execute() del objeto HttpClient y recuperar el
			 * resultado mediante getEntity(). Este resultado lo recibimos en
			 * forma de objeto HttpEntity, pero lo podemos convertir fácilmente
			 * en una cadena de texto mediante el método estático
			 * EntityUtils.toString().
			 */

			HttpResponse resp = httpClient.execute(peticion);
			String respStr = EntityUtils.toString(resp.getEntity());			
			/*
			 * Creamos un nuevo objeto JSONObject a partir del resultado textual
			 * de getEntity(). Hecho esto, podremos acceder a los atributos del
			 * objeto utilizando para ello los métodos get() correspondientes,
			 * según el tipo de cada atributo (getInt(), getString(), etc). Tras
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
