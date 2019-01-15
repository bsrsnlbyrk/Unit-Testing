import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; 

public class AboneGirisTest {
	
	private String username;
	private String password;
	AboneGiris obj;
	
	@Before
	public void setUp() throws Exception{

		obj=new AboneGiris();
		obj.initialize();
	}
	
	@Test
	public void getPasswordTest() {
		char[] psswd= {'a','b','c','d'};
		assertEquals("abcd",obj._getPassword(psswd));
	}
	
	@Test
	public void textFieldsControlTest() {
		obj.textField.setText("a");
		obj.passwordField.setText("b");
		username=obj.textField.getText();
		password=obj._getPassword(obj.passwordField.getPassword());
		assertEquals(true,obj.textFieldsControl(username,password));
	}
	
	@Test
	public void textFieldsControlNullTest() {
		username="";
		password="";
		assertEquals(false,obj.textFieldsControl(username,password));
	}
	
	@Test
	
	public void checkUserTest() {
		username="adsoyad";
		password="parola";
		assertEquals(true,obj.checkUser(username, password));
	}
	
	@Test
	public void handle_login_buttonTest() {
		obj.textField.setText("adsoyad");
		obj.passwordField.setText("parola");
		username=obj.textField.getText();
		password=obj._getPassword(obj.passwordField.getPassword());
		
		assertEquals(true,obj.handle_login_button(obj,username,password));
	}
	
		
	@After
	public void tearDown() {

		username=null;
		password=null;
		obj=null;
	}
}
