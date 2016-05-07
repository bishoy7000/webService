package rest_web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource; 


@Path("/product")
public class Product {
	  @Path("/select_products")
	  @Produces("application/json")
	  public JSONArray products_select() throws JSONException, SQLException, IOException {
		  	JSONArray jarray = new JSONArray();
		  	MysqlDataSource ds = null; 
	        Connection connect = null; 
	        Statement query =null;
		  try {
			  ds = new MysqlDataSource(); 
	          ds.setUrl("jdbc:mysql://localhost:3306/products_db"); 
	  	      ds.setUser("root"); 
	          ds.setPassword(""); 
	          connect = ds.getConnection(); 
   		      query = connect.createStatement();
			  ResultSet list = query.executeQuery("select * from products");
				  while (list.next()) {
					  String  name_json= 	   list.getString("Name");
					  String discription_json= list.getString("Description");
					  int price_json = 		   list.getInt("Price");
					  int id_json = 		   list.getInt("ID");
					    JSONObject jobj = new JSONObject();
					    jobj.put("Name", 		name_json);
					    jobj.put("Description", discription_json);
					    jobj.put("Price", 		price_json);
					    jobj.put("ID", 			id_json);
					    jarray.put(jobj);
					}
			}
			catch(Exception ex) {
			   ex.printStackTrace();
			}

		    try { query.close(); } catch (SQLException e) { e.printStackTrace(); } 
            try { connect.close(); } catch (SQLException e) { e.printStackTrace(); } 
            System.out.println("Invoked");
		  return jarray;
	  }
	  @POST
	  @Path("/addProduct")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public void product_add(InputStream incomingData) {
		  
		  StringBuilder product_details = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					product_details.append(line);
				}
			} catch (Exception e) {
				System.out.println("Error Parsing: - ");
			}
			System.out.println("Data Received: " + product_details.toString());
			JSONObject f = new JSONObject(product_details.toString());
			
			MysqlDataSource ds = null; 
	        Connection connect = null; 
	        PreparedStatement query =null;
			 try {
				  ds = new MysqlDataSource(); 
		          ds.setUrl("jdbc:mysql://localhost:3306/products_db"); 
		  	      ds.setUser("root"); 
		          ds.setPassword(""); 
		          connect = ds.getConnection(); 
	  		      query = connect.prepareStatement("INSERT INTO products (Name, Description, Price)VALUES (?,?,?)");
	  		      query.setString(1, f.getString("Name"));
	  		      query.setString(2, f.getString("Description"));
	  		      query.setInt	 (3, f.getInt("Price"));
	  		      query.executeUpdate();
			 }
			 catch(Exception ex) {
				   ex.printStackTrace();
				}
			 try { query.close(); } catch (SQLException e) { e.printStackTrace(); } 
	         try { connect.close(); } catch (SQLException e) { e.printStackTrace(); }
	}
	  @POST
	  @Path("/editProduct")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public void product_update(InputStream incomingData) {
		  StringBuilder product_details = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
				product_details.append(line);
				}
			} catch (Exception e) {
				System.out.println("Error Parsing: - ");
			}
			System.out.println("Data Received: " + product_details.toString());
			JSONObject f = new JSONObject(product_details.toString());
			
			MysqlDataSource ds = null; 
	        Connection connect = null; 
	        PreparedStatement query =null;
			 try {
				  ds = new MysqlDataSource(); 
		          ds.setUrl("jdbc:mysql://localhost:3306/products_db"); 
		  	      ds.setUser("root"); 
		          ds.setPassword(""); 
		          connect = ds.getConnection(); 
	  		      query = connect.prepareStatement("UPDATE products SET Name= ?, Description= ?, Price= ? WHERE ID= ?");
	  		      query.setString(1, f.getString("Name"));
	  		      query.setString(2, f.getString("Description"));
	  		      query.setInt	 (3, f.getInt("Price"));
	  		      query.setInt	 (4, f.getInt("ID"));
	  		      query.executeUpdate();
			 }
			 catch(Exception ex) {
				   ex.printStackTrace();
				}
			 try { query.close(); } catch (SQLException e) { e.printStackTrace(); } 
	         try { connect.close(); } catch (SQLException e) { e.printStackTrace(); }
	}
	  @GET
	  @Path("/deleteProduct/{id}")
	  public Response  product_delete(@PathParam("id") int id ) {
		  	MysqlDataSource ds = 		null; 
	        Connection connect = 		null; 
	        PreparedStatement query =	null;
			 try {
				  ds = new MysqlDataSource(); 
		          ds.setUrl("jdbc:mysql://localhost:3306/products_db"); 
		  	      ds.setUser("root"); 
		          ds.setPassword(""); 
		          connect = ds.getConnection(); 
	  		      query = connect.prepareStatement("DELETE FROM products WHERE ID= ?");
	  		      query.setInt(1, id);
	  		      query.executeUpdate();
			 }catch(Exception ex) {
				   ex.printStackTrace();
				}
			 try { query.close(); } catch (SQLException e) { e.printStackTrace(); } 
	         try { connect.close(); } catch (SQLException e) { e.printStackTrace(); }
	         return Response.status(200).build();
	}
}

