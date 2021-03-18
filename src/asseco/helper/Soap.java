package asseco.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import asseco.dao.HelperDAO;

public class Soap {
	
	private String poruka;
	private int statusCode;
	private String sessionID;
	

	public void login () {
		 ResourceBundle bundle =
			      PropertyResourceBundle.getBundle("asseco.dao.Values");
		 String username = bundle.getString("adminUsername");
		 String pass = HelperDAO.getPassword();
		 
		 String xmlInput =
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.api.provisioning.admin.access.platform.smap.mobile.assecosee.hr/\">\r\n" + 
			"   <soapenv:Header/>\r\n" + 
			"   <soapenv:Body>\r\n" + 
			"      <ws:login>\r\n" + 
			"         <!--Zero or more repetitions:-->\r\n" + 
			"         <authData>\r\n" + 
			"            <!--Optional:-->\r\n" + 
			"            <key>" + username + "</key>\r\n" + 
			"            <!--Optional:-->\r\n" + 
			"            <value>" + pass +"</value>\r\n" + 
			"         </authData>\r\n" + 
			"         <!--Optional:-->\r\n" + 
			"         <domainID>Main</domainID>\r\n" + 
			"      </ws:login>\r\n" + 
			"   </soapenv:Body>\r\n" + 
			"</soapenv:Envelope>";
		
		String wsURL = bundle.getString("adresaServisa");		 
		URL url = null;
		URLConnection connection = null;
		HttpURLConnection httpConn = null;
		String responseString = null;
		String outputString="";
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		 try
	        {
	            url = new URL(wsURL);
	            connection = url.openConnection();
	            httpConn = (HttpURLConnection) connection;
	 
	            byte[] buffer = new byte[xmlInput.length()];
	            buffer = xmlInput.getBytes();
	 
	            String SOAPAction = "";
	            httpConn.setRequestProperty("Content-Length", String.valueOf(buffer.length));
	            httpConn.setRequestProperty("Content-Type","text/xml; charset=utf-8");
	            httpConn.setRequestProperty("SOAPAction", SOAPAction);
	            httpConn.setRequestMethod("POST");
	            httpConn.setDoOutput(true);
	            httpConn.setDoInput(true);
	            out = httpConn.getOutputStream();
	            out.write(buffer);
	            out.close();
	             
	            // Read the response and write it to standard out.
	            isr = new InputStreamReader(httpConn.getInputStream());
	            in = new BufferedReader(isr);	             
	            while ((responseString = in.readLine()) != null) 
	            {
	                outputString = outputString + responseString;
	            }
	            
	            Document document = parseXmlFile(outputString);
	            
	            this.statusCode = Integer.parseInt(document.getElementsByTagName("statusCode").item(0).getTextContent());
	            if (this.statusCode == 0){
	            	this.poruka = "";
	            	this.sessionID = document.getElementsByTagName("sessionID").item(0).getTextContent();
	            }
	            else {
	            	this.sessionID = "";
	            	this.poruka = document.getElementsByTagName("statusMessage").item(0).getTextContent();
	            }
	              
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
		 
		
	}
	
	
	public void updateNotificationMessages(String tip, String jezik, String tekst, String naslov) {
		ResourceBundle bundle =
			      PropertyResourceBundle.getBundle("asseco.dao.Values");
		 String notificationScenarioId = bundle.getString("notificationScenarioID_" + tip + "_" + jezik);
		 if (notificationScenarioId == null)
		 {
			 this.poruka = "NotificationScenario does not exist";
			 return;
		 }
		 String id = HelperDAO.getScenarionId(notificationScenarioId);
		 String scenarioTypeId = HelperDAO.getScenarioTypeId();
		 
		 String xmlInput = 
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.api.provisioning.admin.access.platform.smap.mobile.assecosee.hr/\">\r\n" + 
		 		"   <soapenv:Header/>\r\n" + 
		 		"   <soapenv:Body>\r\n" + 
		 		"      <ws:updateNotificationMessages>\r\n" + 
		 		"         <!--Optional:-->\r\n" + 
		 		"         <sessionID>"+ this.sessionID+"</sessionID>\r\n" + 
		 		"         <!--Zero or more repetitions:-->\r\n" + 
		 		"         <notificationMessages>\r\n" + 
		 		"            <!--Optional:-->\r\n" + 
		 		"            <delay>0</delay>\r\n" + 
		 		"            <!--Optional:-->\r\n" + 
		 		"            <id>"+id+"</id>\r\n" + 
		 		"            <!--Optional:-->\r\n" + 
		 		"            <messageIndex>0</messageIndex>\r\n" + 
		 		"            <!--Optional:-->\r\n" + 
		 		"            <notificationScenarioID>"+notificationScenarioId+"</notificationScenarioID>\r\n" +  
		 		"            <!--Optional:-->\r\n" + 
		 		"            <notificationScenarioTypeId>"+ scenarioTypeId+"</notificationScenarioTypeId>\r\n" + 
		 		"            <!--Optional:-->\r\n" + 
		 		"            <retryAttempts>0</retryAttempts>\r\n" + 
		 		"            <!--Optional:-->\r\n" + 
		 		"            <sourceAddress>?</sourceAddress>\r\n" + 
		 		"            <!--Optional:-->\r\n" + 
		 		"            <text>"+tekst+"</text>\r\n" + 
		 		"            <!--Optional:-->\r\n" + 
		 		"            <title>"+naslov+"</title>\r\n" + 
		 		"         </notificationMessages>\r\n" + 
		 		"         <!--Optional:-->\r\n" + 
		 		"         <domainID>Main</domainID>\r\n" + 
		 		"      </ws:updateNotificationMessages>\r\n" + 
		 		"   </soapenv:Body>\r\n" + 
		 		"</soapenv:Envelope>";
		
		String wsURL = bundle.getString("adresaServisa");		 
		URL url = null;
		URLConnection connection = null;
		HttpURLConnection httpConn = null;
		String responseString = null;
		String outputString="";
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		 try
	        {
	            url = new URL(wsURL);
	            connection = url.openConnection();
	            httpConn = (HttpURLConnection) connection;
	 
	            byte[] buffer = new byte[xmlInput.length()];
	            buffer = xmlInput.getBytes();
	 
	            String SOAPAction = "";
	            httpConn.setRequestProperty("Content-Length", String.valueOf(buffer.length));
	            httpConn.setRequestProperty("Content-Type","text/xml; charset=utf-8");
	            httpConn.setRequestProperty("SOAPAction", SOAPAction);
	            httpConn.setRequestMethod("POST");
	            httpConn.setDoOutput(true);
	            httpConn.setDoInput(true);
	            out = httpConn.getOutputStream();
	            out.write(buffer);
	            out.close();
	             
	            // Read the response and write it to standard out.
	            isr = new InputStreamReader(httpConn.getInputStream());
	            in = new BufferedReader(isr);	             
	            while ((responseString = in.readLine()) != null) 
	            {
	                outputString = outputString + responseString;
	            }
	            
	            Document document = parseXmlFile(outputString);
	            
	            this.statusCode = Integer.parseInt(document.getElementsByTagName("statusCode").item(0).getTextContent());
	            if (this.statusCode == 0){
	            	this.poruka = "";
	            }
	            else {
	            	this.poruka = document.getElementsByTagName("statusMessage").item(0).getTextContent();
	            }
	              
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	}
	
	
	public void notifyUser(String tip, String jezik, String user) {
		ResourceBundle bundle =
			      PropertyResourceBundle.getBundle("asseco.dao.Values");
		 String notificationScenarioId = bundle.getString("notificationScenarioID_" + tip + "_" + jezik);
		 if (notificationScenarioId == null)
		 {
			 this.poruka = "NotificationScenario does not exist";
			 return;
		 }
		 String id = HelperDAO.getUserId(user);
		 String servis = "";
		 if ("retail".equals(tip))
			 servis = "JimbaNgRetail";
		 else
			 servis = "JimbaNgCorporate";
		 String serviceID = HelperDAO.getServiceId(servis);
		 ArrayList<String> uredjaji = HelperDAO.getUredjaji(user);
		 
		 for (String uredjaj : uredjaji) {
			 
			 String xmlInput = 
						"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.api.provisioning.admin.access.platform.smap.mobile.assecosee.hr/\">\r\n" + 
						"   <soapenv:Header/>\r\n" + 
						"   <soapenv:Body>\r\n" + 
						"      <ws:notifyUser>\r\n" + 
						"         <!--Optional:-->\r\n" + 
						"         <sessionID>" + this.sessionID + "</sessionID>\r\n" + 
						"         <!--Optional:-->\r\n" + 
						"         <userID>"+ id +"</userID>\r\n" + 
						"         <!--Optional:-->\r\n" + 
						"         <userDeviceID>"+ uredjaj +"</userDeviceID>\r\n" + 
						"         <!--Optional:-->\r\n" + 
						"         <serviceID>"+ serviceID +"</serviceID>\r\n" + 
						"         <!--Optional:-->\r\n" + 
						"         <notificationScenarioID>"+ notificationScenarioId +"</notificationScenarioID>\r\n" + 
						"         <!--Optional:-->\r\n" + 
						"         <domainID>Main</domainID>\r\n" + 
						"      </ws:notifyUser>\r\n" + 
						"   </soapenv:Body>\r\n" + 
						"</soapenv:Envelope>";
				
				String wsURL = bundle.getString("adresaServisa");		 
				URL url = null;
				URLConnection connection = null;
				HttpURLConnection httpConn = null;
				String responseString = null;
				String outputString="";
				OutputStream out = null;
				InputStreamReader isr = null;
				BufferedReader in = null;
				 try
			        {
			            url = new URL(wsURL);
			            connection = url.openConnection();
			            httpConn = (HttpURLConnection) connection;
			 
			            byte[] buffer = new byte[xmlInput.length()];
			            buffer = xmlInput.getBytes();
			 
			            String SOAPAction = "";
			            httpConn.setRequestProperty("Content-Length", String.valueOf(buffer.length));
			            httpConn.setRequestProperty("Content-Type","text/xml; charset=utf-8");
			            httpConn.setRequestProperty("SOAPAction", SOAPAction);
			            httpConn.setRequestMethod("POST");
			            httpConn.setDoOutput(true);
			            httpConn.setDoInput(true);
			            out = httpConn.getOutputStream();
			            out.write(buffer);
			            out.close();
			             
			            // Read the response and write it to standard out.
			            isr = new InputStreamReader(httpConn.getInputStream());
			            in = new BufferedReader(isr);	             
			            while ((responseString = in.readLine()) != null) 
			            {
			                outputString = outputString + responseString;
			            }
			            
			            Document document = parseXmlFile(outputString);
			            
			            this.statusCode = Integer.parseInt(document.getElementsByTagName("statusCode").item(0).getTextContent());
			            if (this.statusCode == 0){
			            	this.poruka = "";
			            }
			            else {
			            	this.poruka = document.getElementsByTagName("statusMessage").item(0).getTextContent();
			            }
			              
			        } 
			        catch (Exception e) 
			        {
			            e.printStackTrace();
			        }
			 
		 }
		 
		 
	}
	
	
	public void pushProxy(String tip, String user,String naslov, String tekst) {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("asseco.dao.Values");
		
		String base_url = bundle.getString("SMAP_BASE_URL");
		try {
			URL url = new URL (base_url + "/AP/PushProxy/push/user/"+ user );
			
			String servis = "";
			 if ("retail".equals(tip))
				 servis = "JimbaNgRetail";
			 else
				 servis = "JimbaNgCorporate";
			String ttl = bundle.getString("ttl");
			
			String body = "{\r\n" + 
					"	\"domain\": \"Main\",\r\n" + 
					"	\"serviceId\": \"" + servis +"\",\r\n" + 
					"	\"title\": \""+ naslov + "\",\r\n" + 
					"	\"content\": \""+tekst+"\",\r\n" + 
					"	\"metadata\": { \"someKey\": \"someData\" }, \r\n" + 
					"	\"settings\": { \r\n" + 
					"                       \"badge\": \"1\", \r\n" + 
					"                       \"priority\": \"1\",\r\n" + 
					"                       \"ttl\": \""+ ttl +"\" \r\n" + 
					"				}\r\n" + 
					"	}";
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
		
			OutputStream os = con.getOutputStream();
			byte [] input = body.getBytes("utf-8");
			os.write(input);
			
			if (con.getResponseCode() != 200) {
				this.statusCode = con.getResponseCode();
				this.poruka = con.getResponseMessage();
			}
			else {
				this.poruka = "";
				this.statusCode = 0;
			
//				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
//				StringBuilder response = new StringBuilder();
//				String linija = "";
//				while ((linija = br.readLine()) != null) {
//					response.append(linija +"\n");
//				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
             InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


	
	@Override
	public String toString() {
		return "Soap [poruka=" + poruka + ", statusCode=" + statusCode + ", sessionID=" + sessionID + "]";
	}

	public String getPoruka() {
		return poruka;
	}


	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
}
