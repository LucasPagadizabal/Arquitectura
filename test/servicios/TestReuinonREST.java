package servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestReuinonREST {

	public final String BASE_URL="http://localhost:8080/Practico-Jersey/rest";
	public final HttpClient client = HttpClientBuilder.create().build();
	String token;

	@Test
	public void testUsuarioREST() throws ClientProtocolException, IOException {
		token = this.getToken();
		crearReuniones();
		getReunion();
		listarReuniones();
		getReunionesEntreFechas();
		getReunionesByUserAndDay();
		getReunionesSuperpuestas();
		updateReunion();
		deleteReunion();
	}

	private String getResultContent(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if(entity!=null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		}else {
			return "";
		}
	}
	public String getToken() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/autenticacion";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nickName", "Juan");
		jsonObject.put("password", "123456");
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		return resultContent;
	}
	public void crearReuniones() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/reuniones";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 01);
		jsonObject.put("dia", 01);
		jsonObject.put("horaI", 11);
		jsonObject.put("horaF", 13);
		jsonObject.put("idSala", 21);
		jsonObject.put("idCalendario", 2);
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 01);
		jsonObject.put("dia", 01);
		jsonObject.put("horaI", 14);
		jsonObject.put("horaF", 18);
		jsonObject.put("idSala", 21);
		jsonObject.put("idCalendario", 2);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 01);
		jsonObject.put("dia", 01);
		jsonObject.put("horaI", 19);
		jsonObject.put("horaF", 20);
		jsonObject.put("idSala", 21);
		jsonObject.put("idCalendario", 2);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		//Test mismo horario-mismo calendario -distinto dia
		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 01);
		jsonObject.put("dia", 02);
		jsonObject.put("horaI", 19);
		jsonObject.put("horaF", 20);
		jsonObject.put("idSala", 21);
		jsonObject.put("idCalendario", 2);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);	

		//Test mismo dia - mismo horario -distinito calendario (superposicion)
		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 01);
		jsonObject.put("dia", 01);
		jsonObject.put("horaI", 19);
		jsonObject.put("horaF", 20);
		jsonObject.put("idSala", 21);
		jsonObject.put("idCalendario", 6);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);	

		//Superposicion de horario en el mismo calendario(superposicion)
		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 01);
		jsonObject.put("dia", 01);
		jsonObject.put("horaI", 14);
		jsonObject.put("horaF", 18);
		jsonObject.put("idSala", 22);
		jsonObject.put("idCalendario", 2);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);	

		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 02);
		jsonObject.put("dia", 01);
		jsonObject.put("horaI", 14);
		jsonObject.put("horaF", 20);
		jsonObject.put("idSala", 22);
		jsonObject.put("idCalendario", 2);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		//usuario Gomez

		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 02);
		jsonObject.put("dia", 02);
		jsonObject.put("horaI", 8);
		jsonObject.put("horaF", 10);
		jsonObject.put("idSala", 22);
		jsonObject.put("idCalendario", 6);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 02);
		jsonObject.put("dia", 02);
		jsonObject.put("horaI", 11);
		jsonObject.put("horaF", 15);
		jsonObject.put("idSala", 22);
		jsonObject.put("idCalendario", 6);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 02);
		jsonObject.put("dia", 02);
		jsonObject.put("horaI", 18);
		jsonObject.put("horaF", 22);
		jsonObject.put("idSala", 22);
		jsonObject.put("idCalendario", 6);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("anio", 2017);
		jsonObject.put("mes", 01);
		jsonObject.put("dia", 01);
		jsonObject.put("horaI", 8);
		jsonObject.put("horaF", 10);
		jsonObject.put("idSala", 22);
		jsonObject.put("idCalendario", 6);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

	}

	public void getReunion() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/reuniones/1";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void listarReuniones() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/reuniones";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void getReunionesEntreFechas() throws ClientProtocolException, IOException{
		String url = BASE_URL + "/reuniones/getReunionesEntreFechas?day1=2017-10-17&&day2=2017-15-17";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void getReunionesByUserAndDay() throws ClientProtocolException, IOException{
		String url = BASE_URL + "/reuniones/getReunionesByUserAndDay?iduser=1&&day=2017-10-15";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void getReunionesSuperpuestas() throws ClientProtocolException, IOException{
		String url = BASE_URL + "/reuniones/getReunionesSuperpuestas?iduser=1&&fechaInicio=2017-10-1710:00:00&&fechaFin=2017-10-1713:00:00";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		request.addHeader("Authorization", "Bearer-"+token+"");	
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void deleteReunion() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/reuniones/28";

		HttpDelete request = new HttpDelete(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		HttpResponse response = client.execute(request);

		System.out.println("\nDELETE "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void updateReunion() throws ClientProtocolException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Test");
		jsonObject.put("apellido", "JUnit");
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/reuniones/30";
		HttpPut request = new HttpPut(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(request);

		System.out.println("\nPUT "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
}


