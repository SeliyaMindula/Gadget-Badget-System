package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Delivery {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadget_badget", "root", "");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return con;
	}

	public String readDelivery() {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr>"
			 		+ "<th>Item ID</th> "
			 		+ "<th>Receiver Name</th>"
			 		+ "<th>Receiver Phone No</th>"
			 		+ "<th>Receiver Mail</th> "
			 		+ "<th>Update</th>"
			 		+ "<th>Remove</th></tr>"; 
			 String query = "select * from delivery"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String DeliveryID = Integer.toString(rs.getInt("DeliveryID")); 
				 String ItemID = rs.getString("ItemID");
				 String ReceiverName = rs.getString("ReceiverName");
				 String ReceiverPhoneNo = rs.getString("ReceiverPhoneNo"); 
				 String ReceiverMail = rs.getString("ReceiverMail"); 
				 
				 // Add into the html table
				 output += "<tr><td><input id='hidDeliveryIDUpdate' "
				 		+ "name='hidDeliveryIDUpdate' "
				 		+ "type='hidden' value='" + DeliveryID
				 		+ "'>" + ItemID + "</td>"; 
				 output += "<td>" + ReceiverName + "</td>"; 
				 output += "<td>" + ReceiverPhoneNo + "</td>"; 
				 output += "<td>" + ReceiverMail + "</td>"; 
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
						 + "class='btnUpdate btn btn-secondary' data-Deliveryid='" + DeliveryID + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove' "
						 + "class='btnRemove btn btn-danger' data-Deliveryid='" + DeliveryID + "'></td></tr>";
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the delivery."+e.getMessage(); ; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}

	public String insertDelivery(String ItemID, String ReceiverName, String ReceiverPhoneNo, String ReceiverMail) {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement
			String query = "insert into delivery (DeliveryID,ItemID,ReceiverName,ReceiverPhoneNo,ReceiverMail) values (?, ?, ?, ?, ?);"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, ItemID);
			preparedStmt.setString(3, ReceiverName); 
			preparedStmt.setString(4,ReceiverPhoneNo); 
			preparedStmt.setString(5, ReceiverMail); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newDelivery = readDelivery(); 
			output = "{\"status\":\"success\", \"data\": \""
					+ "" +newDelivery + "\"}"; 
			} 
			catch (Exception e) 
			{ 
				System.err.println("ERORR : "+e.getMessage());
				output = "{\"status\":\"error\", \"data\":"
					 + "\"Error while inserting the Delivery.\"}"; 
				 
			} 
			return output;
		 }

	public String updateDelivery(String DeliveryID, String ItemID, String ReceiverName, String ReceiverPhoneNo, String ReceiverMail) {
		String output = ""; 
		 try
		 { 
				 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for updating."; 
			 } 
			 // create a prepared statement
			 String query = "UPDATE delivery SET "
			 		+ "ItemID=?,ReceiverName=?,ReceiverPhoneNo=?,ReceiverMail=? WHERE DeliveryID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, ItemID);
			 preparedStmt.setString(2, ReceiverName); 
			 preparedStmt.setString(3, ReceiverPhoneNo); 
			 preparedStmt.setString(4, ReceiverMail); 
			 preparedStmt.setInt(5, Integer.parseInt(DeliveryID)); 
			// execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newDelivery = readDelivery(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newDelivery + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": "
			 		+ "\"Error while updating the item.\"}"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}

	public String deleteDelivery(String DeliveryID) {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from delivery where DeliveryID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(DeliveryID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newDelivery = readDelivery(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newDelivery + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": "
			 		+ "\"Error while deleting the Delivery.\"}"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}
}
