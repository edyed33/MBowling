package es.hungryLion.mbowling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	public EditText ed;
	public EditText ed2;
	public Button btn;
	public Button bExit;
	
	String dato;
	String dni;
	String cod;
	String tmp;
	boolean rec = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		ed = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);
		btn = (Button) findViewById(R.id.btnlogin);
		bExit = (Button)findViewById(R.id.bSalir);
		leer();

		if (tmp == null || tmp == "") { // si leer es null quiere decir que es
										// la primera vez que entra
			btn.setOnClickListener(new OnClickListener() {

				
				public void onClick(View v) {
					login(ed.getText().toString(), ed2.getText().toString()); // metodo
																				// JSON
																				// que
																				// conecta
																				// con
																				// la
																				// base
																				// de
																				// datos

					
					compruebaResultado();
				}
			});
			
			bExit.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					lanzarSalir(v);
				}
			});
			
		} else { // Lo que hace lo siguiente es dividir una cadena en dos ;)
			String tempo = tmp;
			String tokens[] = tempo.split(" "); // Divide las cadenas entre los
												// espacios que haya
			String varDNI = tokens[0];
			String varContra = tokens[1];

			rec = true;

			login(varDNI, varContra);
			compruebaResultado();
		}

	}
	
	 public void lanzarSalir(View view){
    	 //Creamos un objeto de la clase AlertDialog a través de la clase Builder:
    	 AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this); 
    	 //Configuramos el título del diálogo:
         dialogo1.setTitle("CIERRE DE LA APLICACION");
         //Configuramos el mensaje del diálogo:
         dialogo1.setMessage("ÀEsta seguro que quiere cerrar la aplicaci—n?");
         //Evitamos que el diálogo sea salteado por cualquier medio distinto a presionar alguno de los dos botones:
         dialogo1.setCancelable(false);  
         //Llamamos al método setPositiveButton indicando el texto a mostrar en el botón y la clase anónima 
         //que capturará el evento clic del botón:
         dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  
             public void onClick(DialogInterface dialogo1, int id) {  
                 aceptar();  
             }  
         });
         //De forma similar procedemos con el botón de cancelar:
         dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
             public void onClick(DialogInterface dialogo1, int id) {  
                 //cancelar();
             }  
         }); 
         //Mostramos el diálogo:
         dialogo1.show();        
     }
	 
	//Definimos los métodos del Activity que se llamaran desde las clases anónimas:
     public void aceptar() {
    	 finish();
     }
     
     public void cancelar() {
    	       
     }    	
	

	public void login(String deni, String contra) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet peticion = new HttpGet(
				"http://www.edyed33.decatarroja.es/WSDone/loginJSON.php/json/?dni="
						+ deni + "&contrasenna=" + contra);
		peticion.setHeader("content-type", "application/json");

		try {
			HttpResponse resp = httpClient.execute(peticion);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject respJSON = new JSONObject(respStr);
			dato = respJSON.getString("Resultado");
			dni = respJSON.getString("DNI");
			cod = respJSON.getString("idClientes");

		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
		}

	}

	public void compruebaResultado() { // comprueba si los datos coinciden
		// if(rec == false){
		if (dato.contains("no ok")) {

			toast("no coinciden los datos");
			ed.setText("");
			ed2.setText("");
			// No coinciden los datos
			// que pase a la siguiente Activity
		} else {
			if (rec == false) {
				try { // escribe en un archivo el DNI y la contraseña
					escribe(ed.getText().toString() + " "
							+ ed2.getText().toString() + " " + cod);
				} catch (IOException e) {
					Log.e("Error", "Error en escribe", e);
				}
			}

			Intent a = new Intent(Login.this, TransicionActivity.class);
			startActivity(a);
			finish();

		}
		
	}

	public void escribe(String tx) throws IOException { // metodo escribe
		FileOutputStream fos = null;
		try {
			fos = openFileOutput("login.txt", Context.MODE_PRIVATE);
			fos.write(tx.getBytes());
		} catch (FileNotFoundException e) {
			// No se encuentra archivo
		} catch (Exception e) {

		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {

				}
			}
		}
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

	public void toast(String tx) {
		Toast.makeText(this, tx, Toast.LENGTH_SHORT).show();
	}
}
