package asseco.dto;

public class Zahtjev {
	
	private String userId;
	private String naslov;
	private String tekst;
	private String jezik;
	private String tipKorisnika;
	private String tipPoruke;
	private String naslovPush;
	private String tekstPush;
	


	public Zahtjev(String userId, String naslov, String tekst, String jezik, String tipKorisnika, String tipPoruke,
			String naslovPush, String tekstPush) {
		super();
		this.userId = userId;
		this.naslov = naslov;
		this.tekst = tekst;
		this.jezik = jezik;
		this.tipKorisnika = tipKorisnika;
		this.tipPoruke = tipPoruke;
		this.naslovPush = naslovPush;
		this.tekstPush = tekstPush;
	}

	public Zahtjev() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public String getJezik() {
		return jezik;
	}

	public void setJezik(String jezik) {
		this.jezik = jezik;
	}

	public String getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(String tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}


	public String getTipPoruke() {
		return tipPoruke;
	}

	public void setTipPoruke(String tipPoruke) {
		this.tipPoruke = tipPoruke;
	}

	public String getNaslovPush() {
		return naslovPush;
	}

	public void setNaslovPush(String naslovPush) {
		this.naslovPush = naslovPush;
	}

	public String getTekstPush() {
		return tekstPush;
	}

	public void setTekstPush(String tekstPush) {
		this.tekstPush = tekstPush;
	}
	
	


}
