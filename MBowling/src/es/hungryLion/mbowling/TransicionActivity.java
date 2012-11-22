package es.hungryLion.mbowling;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Antonio
 * 
 */
public class TransicionActivity extends Activity {
	/** Called when the activity is first created. */
	
	private AlertDialog.Builder dialogo1;
	private AlertDialog.Builder dialogo2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layouttransicion);
		// File fichero = new File("login.txt");

		ImageView rankingImg = (ImageView) findViewById(R.id.rankingImg);
		ImageView pistas = (ImageView) findViewById(R.id.pistasImg);
		ImageView logout = (ImageView) findViewById(R.id.logoutImg);
		ImageView clasif = (ImageView) findViewById(R.id.clasifImg);

		clasif.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				  Intent i = new Intent
				  (getApplicationContext(),ClasificacionGlobal.class);
				  startActivity(i);
				 
			}
		});

		rankingImg.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				 Intent i = new Intent(getApplicationContext(),RankingPersonal.class);
				 startActivity(i);
			}
		});

		pistas.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						VisualizarPistas.class);
				startActivity(i);
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				lanzarSalir(v);
			}
		});

	}

	public void lanzarSalir(View view) {
		// Creamos un objeto de la clase AlertDialog a través de la clase
		// Builder:
		dialogo1 = new AlertDialog.Builder(this);
		// Configuramos el título del diálogo:
		dialogo1.setTitle("CIERRE DE LA APLICACION");
		// Configuramos el mensaje del diálogo:
		dialogo1.setMessage("ÀEsta seguro que quiere cerrar la aplicaci—n?");
		// Evitamos que el diálogo sea salteado por cualquier medio distinto a
		// presionar alguno de los dos botones:
		dialogo1.setCancelable(false);
		// Llamamos al método setPositiveButton indicando el texto a mostrar en
		// el botón y la clase anónima
		// que capturará el evento clic del botón:
		dialogo1.setPositiveButton("Confirmar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogo1, int id) {
						aceptar();
					}
				});
		// De forma similar procedemos con el botón de cancelar:
		dialogo1.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogo1, int id) {
						//
					}
				});
		
		// Mostramos el diálogo:
		dialogo1.show();
	}

	// Definimos los métodos del Activity que se llamaran desde las clases
	// anónimas:
	public void aceptar() {
		dialogo2 = new AlertDialog.Builder(this);
		dialogo2.setTitle("Recordar usuario");
		dialogo2.setMessage("ÀQuiere recordar el usuario?");
		dialogo2.setPositiveButton("Si",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogo1, int id) {
						finish();
					}
				});
		dialogo2.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogo1, int id) {
						Eliminar();
						finish();
					}
				});
		dialogo2.show();
	}

	public void cancelar() {

	}

	/**
	 * 
	 * Este metodo elimina el archivo de login que almacena el usuario y la
	 * contraseña
	 * 
	 * @autor Antonio
	 * @param nombre
	 *            - (listamos y explicamos los parametros)
	 * @return nombre - (especificamos y explicamos si el metodo devuelve algo)
	 * @throws nombre
	 *             - (describimos si se lanza alguna excepción)
	 */
	public void Eliminar() {
		File fichero = new File(
				"data/data/es.hungryLion.mbowling/files/login.txt");

		if (fichero.delete()) {
			
		} else {
			Toast.makeText(TransicionActivity.this, "no se puede borrar",
					Toast.LENGTH_SHORT);
		}

	}
}