package rest_web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class test_client {
	public static void main(String[] args) {
		try { 
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Name", "Product Three");
			jsonObject.put("Description", "The third product in the DB add from client");
			jsonObject.put("Price", 2016);
			jsonObject.put("ID", 3);

			try {
				URL url = new URL("http://localhost:8080/rest_web/dss/product/addProduct");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				while (in.readLine() != null) {
				}
				
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			}
 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
