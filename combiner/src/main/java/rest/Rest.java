package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rest {

	private static final String URI = "http://localhost:8080/profiles/";

	public static void main(String[] args) throws Exception {
		String x = find("asd", "{\"name\":{\"first\":\"john\"}}");
		System.out.println(x);
	}

	public static String getAll(String collection) {
		try {
			String urlStr = Rest.URI + collection;
			System.out.println("rest get all " + urlStr);
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder result = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return result.toString();
		} catch (Exception e) {
			throw new RestException();
		}
	}

	public static String insert(String collection, String json) {
		try {
			URL url = new URL(Rest.URI + collection);
			System.out.println("rest insert " + url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
//		connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestMethod("PUT");
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
			osw.write(json);
			osw.flush();
			osw.close();
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return result.toString();
		} catch (Exception e) {
			throw new RestException();
		}
	}

	public static String find(String collection, String json) {
		try {
			URL url = new URL(Rest.URI + collection);
			System.out.println("rest find " + url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
//			connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestMethod("POST");
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
			osw.write(json);
			osw.flush();
			osw.close();
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return result.toString();
		} catch (Exception e) {
			throw new RestException();
		}
	}
}
