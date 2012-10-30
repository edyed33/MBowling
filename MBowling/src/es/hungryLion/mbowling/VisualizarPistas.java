package es.hungryLion.mbowling;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
//import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Esta clase muestra al usuario las pistas, se puede filtrar por libres o ocupadas
 * @author Cristian
 *
 */
public class VisualizarPistas extends Activity {
	
	//Declaramos los botones y las variables que usaremos en esta clase
	private Button Ver;
	private Button Reservado;
	private Button Libres;
	private ListView lista;
	String[] pista;
	int x;
	boolean ejecutado;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Asociamos las variales a los botones
		Ver = (Button) findViewById(R.id.TODAS);
		Reservado = (Button) findViewById(R.id.Reservadas);
		Libres = (Button) findViewById(R.id.Libres);
		lista = (ListView)findViewById(R.id.Listview);
		
		
		//Estos setOnclickListener que ponen en null el Listview si esta lleno, y si no se cambia la variable X para pasar X parametro
		Ver.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {				
				if (ejecutado = true){
					pista = null;
					lista.setAdapter(null);
				}
				x=1;
				colocarList();
			}
		});	
	
	Reservado.setOnClickListener(new OnClickListener() {
		public void onClick(View view) {				
			if (ejecutado = true){
				pista = null;
				lista.setAdapter(null);
			}
			x=2;
			colocarList();
		}
	});	

	Libres.setOnClickListener(new OnClickListener() {
		public void onClick(View view) {				
			if (ejecutado = true){
				pista = null;
				lista.setAdapter(null);
			}
			x=3;
			colocarList();
		}
	});
	
}
	
	
	/**
	 * Este metodo rellena la lista del Activity con los resultados que le devuelve el PHP cuando ejecuta la peticion a la URL
	 * @param Le pasamos x, que se le pasa al ejecutar alguno de los setOnclicklistener.
	 * @return Devuelve y coloca los datos en el ListView 
	 */
private void colocarList(){
	try {
		
		HttpGet peticion = new HttpGet("http://www.decatarroja.es/edyed33/WSDone/Verpistas.php/json/?seleccion=" + x);
		peticion.setHeader("content-type", "application/json");	
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse resp = httpClient.execute(peticion);
		String respStr = EntityUtils.toString(resp.getEntity());
		
		JSONArray resparray = new JSONArray(respStr);
					
		
		int n=resparray.length();
		
		pista = new String[n];			
		
		for (int i = 0; i < resparray.length(); i++) {
			JSONObject respJSON = resparray.getJSONObject(i);

			String nombre = respJSON.getString("NOMBRE");
				
			pista[i] =nombre;
			Log.d(nombre,nombre);
		}
		
		// Rellenamos la lista con los resultados
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, pista);
		lista.setAdapter(adaptador);
		ejecutado = true;
	} catch (Exception ex) {
		Log.e("ServicioRest", "Error!", ex);}
	
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/
}