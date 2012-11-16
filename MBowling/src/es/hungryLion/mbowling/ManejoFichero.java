package es.hungryLion.mbowling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;

public class ManejoFichero {	
	Context c;
	
	public ManejoFichero(Context cont){
		c=cont;
	}
	
	
	public String leer() {
		String tmp="";
		FileInputStream fis = null;
		try {
			fis = c.openFileInput("login.txt");
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
		return tmp;
	}

}
