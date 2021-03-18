package asseco.helper;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("TEST!!");
		Soap soap = new Soap();
		soap.login();
		//soap.updateNotificationMessages("retail", "bs", "tekst test", "naslov test");
		soap.notifyUser("retail", "en", "test.nlb@ibank");
		//soap.pushProxy("retail", "test.nlb@ibank","naslov","test");
		System.out.println(soap);
		System.out.println("KRAJ TESTA!");

	}

}
