import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import Model.DbConnection;

public class AboneGiris {

	public JFrame frmAboneGirii;
	public JButton btnNewButton;
	public JTextField textField;
	public JTextField textField_1;
	public JPasswordField passwordField;
	private JLabel lblNewLabel_1;
	public static int id;

	//Connect to database
	public static void connect() {
		try{
			DbConnection connection=new DbConnection();
			connection.DBConnection();
			System.out.println("Bağlandı!");
		}
		catch(Exception ex) {
			System.out.println("Bağlanılamadı!");
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboneGiris window = new AboneGiris();
					window.frmAboneGirii.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				connect();
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public AboneGiris() {
		initialize();
	}
	
	private void username() {
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(140, 143, 180, 35);
		frmAboneGirii.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Kullanici Adi");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(60, 152, 68, 16);
		frmAboneGirii.getContentPane().add(lblNewLabel);
		
	}
	
	private void password() {
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setColumns(10);
		passwordField.setBounds(140, 191, 180, 35);
		frmAboneGirii.getContentPane().add(passwordField);
		
		JLabel label = new JLabel("Parola");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(91, 200, 37, 16);
		frmAboneGirii.getContentPane().add(label);
		
	}
	
	private void title() {
		frmAboneGirii.getContentPane().setLayout(null);
		lblNewLabel_1 = new JLabel("Abone Girisi");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(91, 51, 251, 45);
		lblNewLabel_1.setForeground(Color.blue);
		
		frmAboneGirii.getContentPane().add(lblNewLabel_1);
	}
	
	private void loginButton() {
		btnNewButton = new JButton("Giris Yap");
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Hikmet\\eclipse-workspace\\gazete-dergi\\icon\\icons8-login-30.png"));
		btnNewButton.setBounds(140, 286, 180, 40);

		frmAboneGirii.getContentPane().add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AboneGiris obj=new AboneGiris();
				String username=textField.getText();
				String password=obj._getPassword(passwordField.getPassword());
				
				obj.handle_login_button(obj,username,password);
			}
		});
		
	}
	
	//Converting password from char[] to String
	public String _getPassword(char[] password) {
		
		String strPassword="";
		for(int i=0;i<password.length;i++) {
			strPassword+=password[i];
		}
		return strPassword;
 	}
	
	public boolean handle_login_button(AboneGiris obj,String username,String password) {
		
		if(textFieldsControl(username,password) && checkUser(username, password)) {
			AbonePaneli _obj=new AbonePaneli();
			obj.frmAboneGirii.dispose();
			_obj.frmAbonepaneli.setVisible(true);
			return true;
		}
		else {
			JOptionPane.showMessageDialog(frmAboneGirii, "Lütfen tüm alanları doldurduğunuzdan ve bilgilerinizi doğru girdiğinizden emin olun!");
		}
		return false;
	}
	
	public boolean textFieldsControl(String username,String password) {
		
		if(!username.equals("") || !password.equals("")) {
			return true;
		}
		
		return false;
	}
	
	
	public boolean checkUser(String username,String password) {
		
		String query="select KullaniciTipi from kullanicitipi where idKullaniciTipi=("
				+ "select KullaniciTipID from kullanici where KullaniciAdi='"+username+"'"
						+ "and Parola='"+password+"')";
		String query2="select idKullanici from kullanici where KullaniciAdi='"+username+"'"
				+ "and Parola='"+password+"'";
		
		try {
			
		    //Sending query and getting results
			DbConnection object=new DbConnection();
			ResultSet rs=object.Read(query);
			ResultSet rs2=object.Read(query2);
			
			//Getting id of the user
			while(rs2.next()) {
				
				id=rs2.getInt("idKullanici");
			}
				
			while(rs.next()) {
				
				String usertype=rs.getString(1);
				//Checking results
				if(usertype.equals("Kullanici")) {
					return true;
				}
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void forgetPassword() {
			
			JButton btnNewButton_1 = new JButton("Parolamı Unuttum");
			
			btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Hikmet\\eclipse-workspace\\gazete-dergi\\icon\\icons8-forgot-password-26.png"));
			btnNewButton_1.setBounds(140, 339, 180, 40);
			btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));			
			
			frmAboneGirii.getContentPane().add(btnNewButton_1);
			
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					AboneGiris window1 = new AboneGiris();
					SifreUnuttum window3=new SifreUnuttum();
					window1.frmAboneGirii.dispose();
					window3.previousWindow=1;
					window3.frmifremiUnuttum.setVisible(true);
					
				}
			});
		}
		

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmAboneGirii = new JFrame();
		frmAboneGirii.setTitle("Abone Giri\u015Fi");
		frmAboneGirii.setBounds(100, 100,  450, 500);
		frmAboneGirii.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.title();
		this.loginButton();
		this.forgetPassword();
		this.username();
		this.password();
	}

}
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import Model.DbConnection;

public class AboneGiris {

	public JFrame frmAboneGirii;
	public JButton btnNewButton;
	public JTextField textField;
	public JTextField textField_1;
	public JPasswordField passwordField;
	private JLabel lblNewLabel_1;
	public static int id;

	//Connect to database
	public static void connect() {
		try{
			DbConnection connection=new DbConnection();
			connection.DBConnection();
			System.out.println("Bağlandı!");
		}
		catch(Exception ex) {
			System.out.println("Bağlanılamadı!");
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboneGiris window = new AboneGiris();
					window.frmAboneGirii.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				connect();
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public AboneGiris() {
		initialize();
	}
	
	private void username() {
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(140, 143, 180, 35);
		frmAboneGirii.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Kullanici Adi");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(60, 152, 68, 16);
		frmAboneGirii.getContentPane().add(lblNewLabel);
		
	}
	
	private void password() {
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setColumns(10);
		passwordField.setBounds(140, 191, 180, 35);
		frmAboneGirii.getContentPane().add(passwordField);
		
		JLabel label = new JLabel("Parola");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(91, 200, 37, 16);
		frmAboneGirii.getContentPane().add(label);
		
	}
	
	private void title() {
		frmAboneGirii.getContentPane().setLayout(null);
		lblNewLabel_1 = new JLabel("Abone Girisi");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(91, 51, 251, 45);
		lblNewLabel_1.setForeground(Color.blue);
		
		frmAboneGirii.getContentPane().add(lblNewLabel_1);
	}
	
	private void loginButton() {
		btnNewButton = new JButton("Giris Yap");
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Hikmet\\eclipse-workspace\\gazete-dergi\\icon\\icons8-login-30.png"));
		btnNewButton.setBounds(140, 286, 180, 40);

		frmAboneGirii.getContentPane().add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AboneGiris obj=new AboneGiris();
				String username=textField.getText();
				String password=obj._getPassword(passwordField.getPassword());
				
				obj.handle_login_button(obj,username,password);
			}
		});
		
	}
	
	//Converting password from char[] to String
	public String _getPassword(char[] password) {
		
		String strPassword="";
		for(int i=0;i<password.length;i++) {
			strPassword+=password[i];
		}
		return strPassword;
 	}
	
	public boolean handle_login_button(AboneGiris obj,String username,String password) {
		
		if(textFieldsControl(username,password) && checkUser(username, password)) {
			AbonePaneli _obj=new AbonePaneli();
			obj.frmAboneGirii.dispose();
			_obj.frmAbonepaneli.setVisible(true);
			return true;
		}
		else {
			JOptionPane.showMessageDialog(frmAboneGirii, "Lütfen tüm alanları doldurduğunuzdan ve bilgilerinizi doğru girdiğinizden emin olun!");
		}
		return false;
	}
	
	public boolean textFieldsControl(String username,String password) {
		
		if(!username.equals("") || !password.equals("")) {
			return true;
		}
		
		return false;
	}
	
	
	public boolean checkUser(String username,String password) {
		
		String query="select KullaniciTipi from kullanicitipi where idKullaniciTipi=("
				+ "select KullaniciTipID from kullanici where KullaniciAdi='"+username+"'"
						+ "and Parola='"+password+"')";
		String query2="select idKullanici from kullanici where KullaniciAdi='"+username+"'"
				+ "and Parola='"+password+"'";
		
		try {
			
		    //Sending query and getting results
			DbConnection object=new DbConnection();
			ResultSet rs=object.Read(query);
			ResultSet rs2=object.Read(query2);
			
			//Getting id of the user
			while(rs2.next()) {
				
				id=rs2.getInt("idKullanici");
			}
				
			while(rs.next()) {
				
				String usertype=rs.getString(1);
				//Checking results
				if(usertype.equals("Kullanici")) {
					return true;
				}
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void forgetPassword() {
			
			JButton btnNewButton_1 = new JButton("Parolamı Unuttum");
			
			btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Hikmet\\eclipse-workspace\\gazete-dergi\\icon\\icons8-forgot-password-26.png"));
			btnNewButton_1.setBounds(140, 339, 180, 40);
			btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));			
			
			frmAboneGirii.getContentPane().add(btnNewButton_1);
			
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					AboneGiris window1 = new AboneGiris();
					SifreUnuttum window3=new SifreUnuttum();
					window1.frmAboneGirii.dispose();
					window3.previousWindow=1;
					window3.frmifremiUnuttum.setVisible(true);
					
				}
			});
		}
		

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmAboneGirii = new JFrame();
		frmAboneGirii.setTitle("Abone Giri\u015Fi");
		frmAboneGirii.setBounds(100, 100,  450, 500);
		frmAboneGirii.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.title();
		this.loginButton();
		this.forgetPassword();
		this.username();
		this.password();
	}

}
