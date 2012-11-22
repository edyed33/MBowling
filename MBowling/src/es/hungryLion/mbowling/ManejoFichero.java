package es.hungryLion.mbowling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

/**
 * Esta clase se utiliza a nivel interno para escribir y leer el fichero de login.txt, en el
 * que se almacenan los datod DNI, contrase�a y c�digo de cliente.
 * 
 * @author Hungry Lion
 *
 */
public class ManejoFichero {	
	Context c; //Variable que almacena el contexto del activity que est� utilizando el objeto
			   //instanciado de esta clase
	
	/**
	 * 
	 * Contructor de la clase MenejoFichero 
	 * 
	 * @param cont Necesitamos pasarle el contexto desde donde es creado el objeto ManejoFichero
	 * para que reconozca los m�todos openFileOutput y openFileInput que necesitamos utilizar
	 * 
	 */
	public ManejoFichero(Context cont){
		c=cont;  //almacenamos el contexto
	}
	
	/**
	 * 
	 * M�todo que escribe un String, con los datos que queremos almacenar separados entre 
	 * espacios, en el fichero interno privado login.txt.
	 * 
	 * @param tx String que queremos almacenar en el fichero
	 * @throws IOException
	 */
	public void escribe(String tx) throws IOException { // m�todo escribe
		//Declaramos un flujo de salida que inicializamos a null 
		FileOutputStream fos = null;
		try {
			/*	Escribir ficheros en la memoria interna es muy sencillo. Android proporciona 
			 *  para ello el m�todo openFileOutput(), que recibe como par�metros el nombre del 
			 *  fichero y el modo de acceso con el que queremos abrir el fichero. Este modo de 
			 *  acceso puede variar entre MODE_PRIVATE (por defecto) para acceso privado desde
			 *  nuestra aplicaci�n, MODE_APPEND para a�adir datos a un fichero ya existente, 
 			 * 	MODE_WORLD_READABLE para permitir a otras aplicaciones leer el fichero, o 
 			 *  MODE_WORLD_WRITABLE para permitir a otras aplicaciones escribir sobre el fichero.
			 *  Este m�todo devuelve una referencia al stream de salida asociado al fichero 
			 *  (en forma de objeto FileOutputStream), a partir del cual ya podremos utilizar
			 *  los m�todos de manipulaci�n de ficheros tradicionales del lenguaje java (api java.io).
			 *  
			 *  Android almacena por defecto los ficheros creados en una ruta determinada, que
			 *   en este caso seguir� el siguiente patr�n: /data/data/paquete_java/files/nombre_fichero			
			 */
			fos = c.openFileOutput("login.txt", Context.MODE_PRIVATE);
			//Escribimos una cadena de texto en el fichero login.txt. 
			//getBytes Codifica la cadena en una secuencia de bytes mediante el juego de 
			//caracteres dado, almacena el resultado en una array de bytes nuevo.
			fos.write(tx.getBytes());
		} catch (FileNotFoundException e) {
			// No se encuentra archivo
			Log.e("MBowling ", e.getMessage(), e);
		} catch (Exception e) {
			// Cualquier otro tipo de excepci�n.
			Log.e("MBowling ", e.getMessage(), e);
		} finally {
			if (fos != null) {
				try {
					//Vac�a el flujo de salida y fuerza que los bytes almacenados en el 
					//b�fer de salida se escriban. 
					fos.flush();
					//Cierra el flujo de salida del archivo y libera todos los recursos del
					//sistema asociados con este flujo.
					fos.close();
				} catch (IOException e) {
					//Captura las excepciones de entrada/salida
					Log.e("MBowling ", e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * M�todo que lee los datos almacenados en el fichero login.txt
	 * 
	 * @return String Nos devuelve los datos del fichero como una cadena String
	 */
	public String leer() {
		//Inicializamos el String tmp a vacio. Esta variable es utilizada para almacenar la
		//cadena leida del fichero login.txt que queremos leer
		String tmp="";
		
		
		//Declaramos un flujo de entrada que inicializamos a null 
		FileInputStream fis = null;
		try {
			//Utilizamos el m�todo openFileInput() para abrir el fichero, y los m�todos de 
			//lectura de java.io para leer el contenido.
			fis = c.openFileInput("login.txt");
			//Creamos un array de bytes
			//fis.avalaible() nos devuelve el n�mero de bytes del fichero
			byte[] reader = new byte[fis.available()];
			//Leemos bytes, mientras queden datos en el fichero
			while (fis.read(reader) != -1) {
			}
			//Construye una nueva cadena mediante la decodificaci�n del array de bytes  reader
			//utilizando el juego de caracteres por defecto.
			tmp = new String(reader);			
		} catch (FileNotFoundException e) {
			// No se encuentra archivo
			Log.e("MBowling ", e.getMessage(), e);
		} catch (Exception e) {
			// Cualquier otro tipo de excepci�n.
			Log.e("MBowling ", e.getMessage(), e);
		} finally {			
			if (fis != null) {
				try {
					//Cierra el flujo de salida del archivo y libera todos los recursos del
					//sistema asociados con este flujo.
					fis.close();
				} catch (Exception e) {
					//Captura las excepciones de entrada/salida
					Log.e("MBowling ", e.getMessage(), e);
				}
			}
		}
		//Devuelve los datos almacenados en el fichero como un String
		return tmp;
	}

}
