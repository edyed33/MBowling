package es.hungryLion.mbowling;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class anadirReserva extends Activity {

	private DatePicker dp;
	private String dia;
	private EditText pers;
	private int personas;
	private Button btn;
	private ListView list;

	private String fIni;
	private String fFin;

	private JSONArray respJSON;
	private String listahoras[];
	private int horasp1[] = { 1400, 1415, 1430, 1445, 1500, 1515, 1530, 1545,
			1600, 1615, 1630, 1645, 1700, 1715, 1730, 1745, 1800, 1815, 1830,
			1845, 1900, 1915, 1930, 1945, 2000, 2015, 2030, 2045, 2100, 2115,
			2130, 2145, 2200, 2215, 2230, 2245, 2300, 2315, 2330, 2345 };
	private int horasp2[] = new int[40];
	private int horasp3[] = new int[40];
	private int horasp4[] = new int[40];
	private int horasp5[] = new int[40];
	private int horasp6[] = new int[40];
	private int horasp7[] = new int[40];
	private int horasp8[] = new int[40];
	private int horasp9[] = new int[40];

	private String tmp = "";
	private String varDNI = "";
	private String varCli = "";
	private int varPista = 0;
	private String varTime = "";
	private String varTimeFinal = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reserva);

		dp = (DatePicker) findViewById(R.id.datePicker1);
		pers = (EditText) findViewById(R.id.editText1);
		btn = (Button) findViewById(R.id.button1);

		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (!pers.getText().toString().equalsIgnoreCase("")) {

					dia = dp.getYear() + "-" + (dp.getMonth() + 1) + "-"
							+ dp.getDayOfMonth();

					fIni = dia + ",13:00:00";
					fFin = dia + ",23:59:00";

					personas = Integer.valueOf(pers.getText().toString());
					if (personas <= 10 && personas >= 2) {
						DevuelveLibresJson();

						try {
							trataJson();
						} catch (JSONException e) {
							Log.e("Error trataJson", "Error", e);
						}
					} else {
						pers.setText("");
						toast("Introduce un número de personas menor que 10 y mayor que 2");
					}

				} else {
					pers.setText("");
					toast("Introduce un número de personas");
				}

			}
		});

	}

	/**
	 * Este método establece la conexión con el servidor, pasandole las
	 * variables de fecha y devolviendo un arrayJSON
	 */
	public void DevuelveLibresJson() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet peticion = new HttpGet(
				"http://www.edyed33.decatarroja.es/WSDone/abmReservas.php/json/?metodo=1&fechahora="
						+ fIni + "&fechahorafin=" + fFin);
		peticion.setHeader("content-type", "application/json");

		try {
			HttpResponse resp = httpClient.execute(peticion);
			String respStr = EntityUtils.toString(resp.getEntity());

			respJSON = new JSONArray(respStr);

		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
		}
	}

	/**
	 * Este método se encarga de meter ceros en las horasp1 en las cuales el
	 * usuario no puede reservas porque hay otras reservas hechas
	 * 
	 * @throws JSONException
	 *             Salta una excepción si falla la obtención de datos de JSON
	 */
	public void trataJson() throws JSONException {

		for (int i = 0; i < respJSON.length(); i++) {
			JSONObject jsonob = respJSON.getJSONObject(i);

			String pala = jsonob.getString("FECHA_HORA");
			String pala2 = pala.substring(11);
			String h = pala2.substring(0, 2);
			String m = pala2.substring(3, 5);
			int fch = Integer.parseInt(h + m);

			String palaFin = jsonob.getString("FECHA_HORA_FINAL");
			String pala2Fin = palaFin.substring(11);
			String hF = pala2Fin.substring(0, 2);
			String mF = pala2Fin.substring(3, 5);
			int fchFin = Integer.parseInt(hF + mF);

			if (jsonob.getString("COD_ARTICULO_RESERVA").equalsIgnoreCase("1")) {

				for (int e = 0; e < horasp1.length; e++) {
					int suma = horasp1[e];

					if (horasp1[e] >= fch && horasp1[e] < fchFin) {
						horasp2[e] = horasp1[e];
						horasp1[e] = 0;

					} else if (sumaHoras(suma) > fch && horasp1[e] < fch) {
						horasp2[e] = horasp1[e];
						horasp1[e] = 0;
					}

				}
			} else if (jsonob.getString("COD_ARTICULO_RESERVA")
					.equalsIgnoreCase("2")) {

				for (int e = 0; e < horasp2.length; e++) {
					if (horasp2[e] != 0) {
						int suma = horasp2[e];

						if (horasp2[e] >= fch && horasp2[e] < fchFin) {
							horasp3[e] = horasp2[e];
							horasp2[e] = 0;

						} else if (sumaHoras(suma) > fch && horasp2[e] < fch) {
							horasp3[e] = horasp2[e];
							horasp2[e] = 0;
						}
					}

				}

			} else if (jsonob.getString("COD_ARTICULO_RESERVA")
					.equalsIgnoreCase("3")) {

				for (int e = 0; e < horasp3.length; e++) {
					if (horasp3[e] != 0) {
						int suma = horasp3[e];

						if (horasp3[e] >= fch && horasp3[e] < fchFin) {
							horasp4[e] = horasp3[e];
							horasp3[e] = 0;

						} else if (sumaHoras(suma) > fch && horasp3[e] < fch) {
							horasp4[e] = horasp3[e];
							horasp3[e] = 0;
						}
					}

				}

			} else if (jsonob.getString("COD_ARTICULO_RESERVA")
					.equalsIgnoreCase("4")) {

				for (int e = 0; e < horasp4.length; e++) {
					if (horasp4[e] != 0) {
						int suma = horasp4[e];

						if (horasp4[e] >= fch && horasp4[e] < fchFin) {
							horasp5[e] = horasp4[e];
							horasp4[e] = 0;

						} else if (sumaHoras(suma) > fch && horasp4[e] < fch) {
							horasp5[e] = horasp4[e];
							horasp4[e] = 0;
						}
					}

				}

			} else if (jsonob.getString("COD_ARTICULO_RESERVA")
					.equalsIgnoreCase("5")) {

				for (int e = 0; e < horasp5.length; e++) {
					if (horasp5[e] != 0) {
						int suma = horasp5[e];

						if (horasp5[e] >= fch && horasp5[e] < fchFin) {
							horasp6[e] = horasp5[e];
							horasp5[e] = 0;

						} else if (sumaHoras(suma) > fch && horasp5[e] < fch) {
							horasp6[e] = horasp5[e];
							horasp5[e] = 0;
						}
					}

				}

			} else if (jsonob.getString("COD_ARTICULO_RESERVA")
					.equalsIgnoreCase("6")) {

				for (int e = 0; e < horasp6.length; e++) {
					if (horasp6[e] != 0) {
						int suma = horasp6[e];

						if (horasp6[e] >= fch && horasp6[e] < fchFin) {
							horasp7[e] = horasp6[e];
							horasp6[e] = 0;

						} else if (sumaHoras(suma) > fch && horasp6[e] < fch) {
							horasp7[e] = horasp6[e];
							horasp6[e] = 0;
						}
					}

				}
			} else if (jsonob.getString("COD_ARTICULO_RESERVA")
					.equalsIgnoreCase("7")) {

				for (int e = 0; e < horasp7.length; e++) {
					if (horasp7[e] != 0) {
						int suma = horasp7[e];

						if (horasp7[e] >= fch && horasp7[e] < fchFin) {
							horasp8[e] = horasp7[e];
							horasp7[e] = 0;

						} else if (sumaHoras(suma) > fch && horasp7[e] < fch) {
							horasp8[e] = horasp7[e];
							horasp7[e] = 0;
						}
					}

				}

			} else if (jsonob.getString("COD_ARTICULO_RESERVA")
					.equalsIgnoreCase("8")) {

				for (int e = 0; e < horasp8.length; e++) {
					if (horasp8[e] != 0) {
						int suma = horasp8[e];

						if (horasp8[e] >= fch && horasp8[e] < fchFin) {
							horasp9[e] = horasp8[e];
							horasp8[e] = 0;

						} else if (sumaHoras(suma) > fch && horasp8[e] < fch) {
							horasp9[e] = horasp8[e];
							horasp8[e] = 0;
						}
					}

				}

			} else if (jsonob.getString("COD_ARTICULO_RESERVA")
					.equalsIgnoreCase("9")) {

				for (int e = 0; e < horasp9.length; e++) {
					if (horasp9[e] != 0) {
						int suma = horasp9[e];

						if (horasp9[e] >= fch && horasp9[e] < fchFin) {
							horasp9[e] = 0;

						} else if (sumaHoras(suma) > fch && horasp9[e] < fch) {
							horasp9[e] = 0;
						}
					}

				}

			}
		}
		pasaLista();
	}

	/**
	 * Este método suma la hora inicial para que de como resultado la hora final
	 * de la reserva según las personas que juegan.
	 * 
	 * @param hora
	 *            - Le pasas la hora inicial que quieres empezar a sumar
	 * @return La hora resultante de sumarla según las personas que participan
	 */
	public int sumaHoras(int hora) {
		for (int sum = 0; sum < personas; sum++) {
			hora = hora + 15;
			if (hora == 1460) {
				hora = 1500;
			} else if (hora == 1560) {
				hora = 1600;
			} else if (hora == 1660) {
				hora = 1700;
			} else if (hora == 1760) {
				hora = 1800;
			} else if (hora == 1860) {
				hora = 1900;
			} else if (hora == 1960) {
				hora = 2000;
			} else if (hora == 2060) {
				hora = 2100;
			} else if (hora == 2160) {
				hora = 2200;
			} else if (hora == 2260) {
				hora = 2300;
			} else if (hora == 2360) {
				hora = 2400;
			}
		}
		return hora;
	}

	/**
	 * Este método hace una lista de las horasp1 libres en las cuales el usuario
	 * puede reservar
	 */
	public void pasaLista() {
		listaIntToString();

		setContentView(R.layout.listalibres);
		list = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listahoras);
		list.setAdapter(adaptador);

		haceReserva();
	}

	/**
	 * Este método crea una array con todos los horarios libres según la pista
	 */
	public void listaIntToString() {
		listahoras = new String[horasp1.length];
		for (int r = 0; r < listahoras.length; r++) {
			if (horasp1[r] != 0) {
				listahoras[r] = "Hora: " + horasp1[r] + "      --> pista 1";
			} else if (horasp2[r] != 0) {
				listahoras[r] = "Hora: " + horasp2[r] + "      --> pista 2";
			} else if (horasp3[r] != 0) {
				listahoras[r] = "Hora: " + horasp3[r] + "      --> pista 3";
			} else if (horasp4[r] != 0) {
				listahoras[r] = "Hora: " + horasp4[r] + "      --> pista 4";
			} else if (horasp5[r] != 0) {
				listahoras[r] = "Hora: " + horasp5[r] + "      --> pista 5";
			} else if (horasp6[r] != 0) {
				listahoras[r] = "Hora: " + horasp6[r] + "      --> pista 6";
			} else if (horasp7[r] != 0) {
				listahoras[r] = "Hora: " + horasp7[r] + "      --> pista 7";
			} else if (horasp8[r] != 0) {
				listahoras[r] = "Hora: " + horasp8[r] + "      --> pista 8";
			} else if (horasp9[r] != 0) {
				listahoras[r] = "Hora: " + horasp9[r] + "      --> pista 9";
			}
		}
	}

	/**
	 * Este método controla a que horario del listView clica el usuario
	 */
	public void haceReserva() {
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int posicion, long arg3) {
				try {
					leerArrayHoras(posicion);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Este método almacena las variables para realizar la reserva
	 * 
	 * @param pos
	 *            - Devuelve al posicion del array donde están almacenadas las
	 *            horas
	 * @throws UnsupportedEncodingException
	 */
	public void leerArrayHoras(int pos) throws UnsupportedEncodingException {
		varPista = Integer.parseInt(listahoras[pos].toString().substring(26));
		varTime = dia + "," + listahoras[pos].toString().substring(6, 8) + ":"
				+ listahoras[pos].toString().substring(8, 10) + ":00";

		String tempo = ""
				+ sumaHoras(Integer.parseInt(listahoras[pos].toString()
						.substring(6, 10)));
		varTimeFinal = dia + "," + tempo.substring(0, 2) + ":"
				+ tempo.substring(2, 4) + ":00";

		leer();
	}

	/**
	 * Este método realiza la reserva
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void intrReservaJson() throws UnsupportedEncodingException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost peticion = new HttpPost(
				"http://www.edyed33.decatarroja.es/WSDone/abmReservas.php/json");
		peticion.setHeader("content-type", "application/json");

		JSONObject obj = new JSONObject();

		try {
			obj.put("codcliente", varCli);
			obj.put("dni", varDNI);
			obj.put("fechahora", varTime);
			obj.put("fechahorafin", varTimeFinal);
			obj.put("codarticulo", varPista);

			StringEntity entity = new StringEntity(obj.toString());
			peticion.setEntity(entity);

		} catch (JSONException e) {
			Log.e("Error put", "Error!", e);
		}

		try {
			HttpResponse resp = httpClient.execute(peticion);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject respJSON = new JSONObject(respStr);
			String dato = respJSON.getString("Resultado");
			toast(dato);
		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
		}
	}

	/**
	 * lee el archivo de login.txt
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void leer() throws UnsupportedEncodingException {
		FileInputStream fis = null;
		try {
			fis = openFileInput("login.txt");
			byte[] reader = new byte[fis.available()];
			while (fis.read(reader) != -1) {
			}
			tmp = new String(reader);
		} catch (FileNotFoundException e) {
			toast("Inicia sesión para hacer una reserva");
		} catch (Exception e) {
			toast("Inicia sesión para hacer una reserva");
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {

				}
			}
		}

		String tokens[] = tmp.split(" "); // Divide las cadenas entre los
											// espacios que haya
		varDNI = tokens[0];
		varCli = tokens[2];

		intrReservaJson();
	}

	/**
	 * Este método crea un toast
	 * 
	 * @param txt
	 *            Recibe el texto que queremos mostrar en el toast
	 */
	public void toast(String txt) {
		Toast.makeText(anadirReserva.this, txt, Toast.LENGTH_LONG).show();
	}
}