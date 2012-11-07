package es.hungryLion.mbowling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ClasificacionGlobal extends Activity {
	
	/*    
	 ----------------------------------------------------   
	|													|
	|     DECLARACIÓN OBJETOS LAYOUT PRINCIPAL			|
	|      												|
	 ----------------------------------------------------
	*/
    TextView txt1;
    Button btn;
    RadioButton name;
    RadioButton sex;
    RadioButton age;
    RadioButton all;
    RadioGroup radiofiltro;
    /*    
    ----------------------------------------------------   
   |													|
   |     DECLARACION OBJETOS LAYOUT "BUSCAAGE"			|
   |      												|
    ----------------------------------------------------
   */
    
    TextView agebetween;
    TextView de;
    TextView a;
    EditText intervalo1;
    EditText intervalo2;
    Button buscaAge;
    
    /*    
    ----------------------------------------------------   
   |													|
   |     DECLARACIÓN OBJETOS LAYOUT "BUSCANAME"			|
   |      												|
    ----------------------------------------------------
   */
    
    Button findName;
    EditText searchName;
    EditText searchSurname;
    TextView textName;
    TextView textSurname;
    
    /*    
    ----------------------------------------------------   
   |													|
   |     DECLARACIÓN OBJETOS LAYOUT "BUSCASEX"			|
   |      												|
    ----------------------------------------------------
   */
    
    RadioGroup gruposex;
    RadioButton male;
    RadioButton female;
    TextView sextext;
    Button findsex;
    
    /*    
    ----------------------------------------------------   
   |													|
   |     DECLARACIÓN BOTON BACK (MULTILAYOUT)			|
   |      												|
    ----------------------------------------------------
   */
    
    Button atras;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion_global);

        
        /*    
        ----------------------------------------------------   
       |													|
       |     INICIALIZACIÓN OBJETOS LAYOUT PRINCIPAL		|
       |      												|
        ----------------------------------------------------
       */         
        txt1 = (TextView) findViewById(R.id.textView1);
        btn = (Button) findViewById(R.id.button1);
        radiofiltro = (RadioGroup) findViewById(R.id.radiofiltro);
        name = (RadioButton) findViewById(R.id.radioname);
        sex = (RadioButton) findViewById(R.id.radiosex);
        age = (RadioButton) findViewById(R.id.radioage);
        all = (RadioButton) findViewById(R.id.radioall);
                
        /*    
        ----------------------------------------------------   
       |													|
       |     INICIALIZACIÓN OBJETOS LAYOUT BUSCAAGE			|
       |      												|
        ----------------------------------------------------
       */  
        
        agebetween =(TextView) findViewById(R.id.textView1);
        de =(TextView) findViewById(R.id.de);
        a = (TextView) findViewById(R.id.a);
        intervalo1 = (EditText) findViewById(R.id.intervalo1);
        intervalo2 = (EditText) findViewById(R.id.intervalo2);
        
          
        /*    
        ----------------------------------------------------   
       |													|
       |     INICIALIZACIÓN OBJETOS LAYOUT BUSCANAME		|
       |      												|
        ----------------------------------------------------
       */  
        

        searchName = (EditText) findViewById(R.id.searchname);
        searchSurname = (EditText) findViewById(R.id.searchsurname);
        textName = (TextView) findViewById(R.id.textname);
        textSurname = (TextView) findViewById(R.id.textsurname);
             
        /*    
        ----------------------------------------------------   
       |													|
       |     INICIALIZACIÓN OBJETOS LAYOUT BUSCASEX			|
       |      												|
        ----------------------------------------------------
       */

        gruposex = (RadioGroup) findViewById(R.id.radiogsex);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        sextext = (TextView) findViewById(R.id.sextext);

              
             
        
        /*    
        ----------------------------------------------------   
       |													|
       |    				MÉTODOS							|
       |      												|
        ----------------------------------------------------
       */  
        
        
      cambioLayout(); //CAMBIA EL Layout
      

        	
    }

    public void cambioLayout()
    {
        btn.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (name.isChecked()== true)
				{
					
					setContentView(R.layout.buscaname);
			        findName = (Button) findViewById(R.id.botonname);
					
				}
				if (sex.isChecked()== true)
				{
			        
					setContentView(R.layout.buscasex);
					findsex = (Button) findViewById(R.id.findsex);
					
				}
				if (age.isChecked()== true)
				{
			        
					setContentView(R.layout.buscaage);
					buscaAge = (Button) findViewById(R.id.findage);
					
				}
				if (all.isChecked()== true)
				{
					
					System.out.println("no implementado");
					
				}
				atras(); //Cambia al primer Layout
			}
		});
    	
    }
    
    public void atras()
    {
        /*    
        ----------------------------------------------------   
       |													|
       |     INICIALIZACION BOTON BACK (MULTILAYOUT)		|
       |      												|
        ----------------------------------------------------
       */
    	atras = (Button) findViewById(R.id.back);
    	atras.setOnClickListener(new OnClickListener() {
			

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (ClasificacionGlobal.this, ClasificacionGlobal.class);
				startActivity(intent);
				
			}
		});
    	
    }
    
}
