package es.hungryLion.mbowling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Esta clase muestra al usuario las pistas, se puede filtrar por libres o
 * ocupadas
 * 
 * @author Cristian
 * 
 */
public class VisualizarPistas extends Activity {

	// Declaramos los botones y las variables que usaremos en esta clase
	private Button Reservado;
	private Button reservar;
	private ListView lista;
	String[] pista;
	String texto;
	String Comprobante;
	String tmp;
	int ID;
	int x;
	boolean ejecutado;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Asociamos las variales a los botones
		Reservado = (Button) findViewById(R.id.Reservadas);
		reservar = (Button) findViewById(R.id.Reservar);
		lista = (ListView) findViewById(R.id.Listview);

		Reservado.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (ejecutado = true) {
					pista = null;
					lista.setAdapter(null);
				}
				leer();
				x = 2;
				colocarList();
				verReserva();
			}
		});

		reservar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent a = new Intent(VisualizarPistas.this,
						anadirReserva.class);
				startActivity(a);
			}
		});

	}

	/**
	 * Este metodo rellena la lista del Activity con los resultados que le
	 * devuelve el PHP cuando ejecuta la peticion a la URL
	 * 
	 * @param Le
	 *            pasamos x, que se le pasa al ejecutar alguno de los
	 *            setOnclicklistener.
	 * @return Devuelve y coloca los datos en el ListView
	 */
	private void colocarList() {
		String tokens[] = tmp.split(" ");
		String idUsuario = tokens[2];
		
		try {

			HttpGet peticion = new HttpGet(
					"http://www.decatarroja.es/edyed33/WSDone/Verpistas.php/json/?seleccion="
							+ x+"&id=" + idUsuario);
			peticion.setHeader("content-type", "application/json");

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse resp = httpClient.execute(peticion);
			String respStr = EntityUtils.toString(resp.getEntity());

			JSONArray resparray = new JSONArray(respStr);

			int n = resparray.length();

			pista = new String[n];

			for (int i = 0; i < resparray.length(); i++) {
				JSONObject respJSON = resparray.getJSONObject(i);

				String nombre = respJSON.getString("NOMBRE");

				pista[i] = nombre;
				Log.d(nombre, nombre);
			}

			// Rellenamos la lista con los resultados
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, pista);
			lista.setAdapter(adaptador);
			ejecutado = true;
		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
		}

	}

	public void verReserva() {
		lista.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int posicion, long arg3) {
				String pistaelejida;
				
				pistaelejida = pista[posicion].toString();
				
				HttpGet peticion = new HttpGet(	"http://www.decatarroja.es/edyed33/WSDone/VerReservas.php/json/?seleccion="	+ pistaelejida);
				peticion.setHeader("content-type", "application/json");
				
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse resp = httpClient.execute(peticion);
					String respStr = EntityUtils.toString(resp.getEntity());
					JSONArray resparray = new JSONArray(respStr);					
					JSONObject respJSON = resparray.getJSONObject(0);
					ID = respJSON.getInt("idRESERVAS");
					 texto = respJSON.getString("FECHA_HORA");	
					//Toast.makeText(VisualizarPistas.this, texto,Toast.LENGTH_LONG).show();
					aviso();
				} catch (Exception ex) {
					Log.e("ServicioRest", "Error!", ex);
				}

			}

			
		});
	}
	
	private void aviso() {
		//Creamos un objeto de la clase AlertDialog a través de la clase Builder:
   	 AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this); 
   	 //Configuramos el título del diálogo:
        dialogo1.setTitle("Informacion de la reserva");
        //Configuramos el mensaje del diálogo:
        dialogo1.setMessage("Reservada: " + texto);
        //Evitamos que el diálogo sea salteado por cualquier medio distinto a presionar alguno de los dos botones:
        dialogo1.setCancelable(false);  
        //Llamamos al método setPositiveButton indicando el texto a mostrar en el botón y la clase anónima 
        //que capturará el evento clic del botón:
        dialogo1.setPositiveButton("Borrar reserva", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	try {
            		HttpDelete peticion = new HttpDelete(	"http://www.decatarroja.es/edyed33/WSDone/abmReservas.php/json/?id="+ ID);
    				peticion.setHeader("content-type", "application/json");
    				HttpClient httpClient = new DefaultHttpClient();
					HttpResponse resp = httpClient.execute(peticion);
					String respStr = EntityUtils.toString(resp.getEntity());
					JSONArray resparray = new JSONArray(respStr);					
					JSONObject respJSON = resparray.getJSONObject(0);
					Comprobante = respJSON.getString("Resultado");
					
				} catch (Exception e) {
					
				}
            	
            	if(Comprobante == "ok" || Comprobante == "Ok" || Comprobante == "OK"){
            		Toast.makeText(VisualizarPistas.this, "Se ha borrado la reserva",Toast.LENGTH_LONG).show();
            	}
            	else{
            		Toast.makeText(VisualizarPistas.this, "No se a podido borrar la reserva",Toast.LENGTH_LONG).show();
            	}
            	
            	
            }

			 
        });
        //De forma similar procedemos con el botón de cancelar:
        dialogo1.setNegativeButton("Atras", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
                //cancelar();
            }  
        }); 
        //Mostramos el diálogo:
        dialogo1.show();        
    
		
	}
	
	public void leer() {
		FileInputStream fis = null;
		try {
			fis = openFileInput("login.txt");
			byte[] reader = new byte[fis.available()];
			while (fis.read(reader) != -1) {
			}
			tmp = new String(reader);
		} catch (FileNotFoundException e) {

		} catch (Exception e) {

		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {

				}
			}
		}
	}
}
