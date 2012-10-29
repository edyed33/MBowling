package es.hungryLion.mbowling;

import java.io.File;

import android.app.Activity;
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
				/*
				 * Intent i = new Intent
				 * (getApplicationContext(),CLASECLASIFICACIONESActivity.class);
				 * startActivity(i);
				 */
			}
		});

		rankingImg.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				/*
				 * Intent i = new Intent
				 * (getApplicationContext(),CLASERANKINGActivity.class);
				 * startActivity(i);
				 */
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

			public void onClick(View arg0) {
				Eliminar();
				Intent i = new Intent(getApplicationContext(), Login.class);
				startActivity(i);
			}
		});
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
		File fichero = new File("data/data/es.hungryLion.mbowling/files/login.txt");
		
		if (fichero.delete()) {
			System.out.println("El fichero ha sido borrado satisfactoriamente");
		} else {
			Toast.makeText(TransicionActivity.this, "no se puede borrar",Toast.LENGTH_SHORT);
			System.out.println("El fichero no puede ser borrado");
		}

	}
}