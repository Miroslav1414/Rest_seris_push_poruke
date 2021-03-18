package asseco.servis;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import asseco.dto.Zahtjev;
import asseco.helper.Soap;



@Path("/Notification")
public class NotificationService {
	


	@POST
	@Path("/SendMessage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLink(Zahtjev zahtjev) {
		
		String user = zahtjev.getUserId();
		String naslov = zahtjev.getNaslov();
		String tekst = zahtjev.getTekst();
		String jezik = zahtjev.getJezik();
		String tip = zahtjev.getTipKorisnika();
		String tipPoruke = zahtjev.getTipPoruke();
		String naslovPush = zahtjev.getNaslovPush();
		String tekstPush = zahtjev.getTekstPush();
		
		
		if("1".equals(tipPoruke)) {
			//System.out.println("tip poruke 1.");
			Soap soap = new Soap();
			soap.login();
			//System.out.println("soap login: " + soap.toString());
			if (soap.getStatusCode() != 0)
				return Response.status(soap.getStatusCode()).entity(soap.getPoruka()).build();
			soap.updateNotificationMessages(tip, jezik, tekst, naslov);
			//System.out.println("soap updateNotificationMessages: " + soap.toString());
			if (soap.getStatusCode() != 0)
				return Response.status(soap.getStatusCode()).entity(soap.getPoruka()).build();
			soap.notifyUser(tip, jezik, user);
			//System.out.println("soap notifyUser: " + soap.toString());
			if (soap.getStatusCode() != 0)
				return Response.status(soap.getStatusCode()).entity(soap.getPoruka()).build();
			else 
				return Response.status(200).build();
		}
		else if("2".equals(tipPoruke)) {
			//System.out.println("tip poruke 2.");
			Soap soap = new Soap();
			soap.pushProxy(tip, user, naslovPush, tekstPush);
			//System.out.println("soap pushProxy: " + soap.toString());
			if (soap.getStatusCode() != 0)
				return Response.status(soap.getStatusCode()).entity(soap.getPoruka()).build();
			else 
				return Response.status(200).build();
		}
		else if("3".equals(tipPoruke)) {
			//System.out.println("tip poruke 3.");
			Soap soap = new Soap();
			soap.login();
			//System.out.println("soap login: " + soap.toString());
			if (soap.getStatusCode() != 0)
				return Response.status(soap.getStatusCode()).entity(soap.getPoruka()).build();
			soap.updateNotificationMessages(tip, jezik, tekst, naslov);
			//System.out.println("soap updateNotificationMessages: " + soap.toString());
			if (soap.getStatusCode() != 0)
				return Response.status(soap.getStatusCode()).entity(soap.getPoruka()).build();
			soap.notifyUser(tip, jezik, user);
			//System.out.println("soap notifyUser: " + soap.toString());
			soap.pushProxy(tip, user, naslovPush, tekstPush);
			//System.out.println("soap pushProxy: " + soap.toString());
			if (soap.getStatusCode() != 0)
				return Response.status(soap.getStatusCode()).entity(soap.getPoruka()).build();
			else 
				return Response.status(200).build();
			
		}
		else
			return Response.status(500).entity("Bad request Error!").build();
	}
	
}
