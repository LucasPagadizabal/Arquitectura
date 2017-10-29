package servicios;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

public class TestUsuarioREST {
	
	public final String BASE_URL="http://localhost:8080/Practico-Jersey/rest";
	public final HttpClient client = HttpClientBuilder.create().build();
	String token;
	@Test
	public void testUsuarioREST() throws ClientProtocolException, IOException {
		token = getToken();
		crearUsuarios();
		getUsuario();
		listarUsuarios();
		updateUsuario();
		deleteUsuario();
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

	public void crearUsuarios() throws ClientProtocolException, IOException {
		
		String url = BASE_URL + "/usuarios";
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Juan");
		jsonObject.put("apellido", "Perez");
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
	
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Jose");
		jsonObject.put("apellido", "Flores");
		jsonObject.put("nickName", "Jose");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Pepe");
		jsonObject.put("apellido", "Gomez");
		jsonObject.put("nickName", "Pepe");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Idella");
		jsonObject.put("apellido", "Morrow");
		jsonObject.put("nickName", "Idella");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Abdul");
		jsonObject.put("apellido", "Blanchard");
		jsonObject.put("nickName", "Abdul");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Helena");
		jsonObject.put("apellido", "Mclaughlin");
		jsonObject.put("nickName", "Helena");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Ammie");
		jsonObject.put("apellido", "Fraser");
		jsonObject.put("nickName", "Ammie");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Billye");
		jsonObject.put("apellido", "Bird");
		jsonObject.put("nickName", "Billye");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Lavonia");
		jsonObject.put("apellido", "Mcculloch");
		jsonObject.put("nickName", "Lavonia");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Arcelia");
		jsonObject.put("apellido", "Atkinson");
		jsonObject.put("nickName", "Arcelia");
		jsonObject.put("password", "123456");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
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
	public void getUsuario() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuarios/1";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void listarUsuarios() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuarios";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void updateUsuario() throws ClientProtocolException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Test");
		jsonObject.put("apellido", "JUnit");
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/usuarios/1";
		HttpPut request = new HttpPut(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(request);

		System.out.println("\nPUT "+url);
		
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void deleteUsuario() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuarios/3";
		
		HttpDelete request = new HttpDelete(url);
		request.addHeader("Authorization", "Bearer-"+token+"");
		HttpResponse response = client.execute(request);
		
		System.out.println("\nDELETE "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
}
