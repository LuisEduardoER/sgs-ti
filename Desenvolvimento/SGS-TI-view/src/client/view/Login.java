package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import common.entity.Usuario;
import common.util.Utils;

import client.controller.ClientController;
import client.util.SpringUtilities;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblPassword;
	private JLabel lblUser;
	private JPasswordField password;
	private JTextField user;
	public Login() {
		super("Login");
		JFrame.setDefaultLookAndFeelDecorated(true);
			
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources/imgs/window_icon.png"));
		setSize(new Dimension(380,450));
		setLocation(getScreenCenter(this));
		inicializaComponentes();
	}
	
	private Point getScreenCenter(Component panel){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension panelSize = panel.getSize();
		
		int x = (screenSize.width - panelSize.width)/2; 
		int y = (screenSize.height - panelSize.height)/2;
		
		return new Point(x,y);
	}
	
	private void inicializaComponentes(){
		// painel geral
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(5,5));
		content.setBackground(Color.white);
		
		// painel da imagem
		Image img = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage("resources/imgs/LOGO_01.png")).getImage();
		ImagePanel logo = new ImagePanel(img);


		// painel do form
		JPanel form = criarForm();
		
		content.add(logo, BorderLayout.NORTH);
		content.add(form, BorderLayout.CENTER);
		add(content);
	}
	
	private JPanel criarForm(){
		JPanel form = new JPanel(new SpringLayout());
		
		// campo user
		lblUser = new JLabel("Usuário:",JLabel.TRAILING);
		user = new JTextField(15);
		
		lblUser.setLabelFor(user);
		

		// campo senha
		lblPassword = new JLabel("Senha:",JLabel.TRAILING);
		password = new JPasswordField(15);

		lblPassword.setLabelFor(password);
		// TODO: Temporario, login pronto(preguica detected)
		this.user.setText("admin");
		this.password.setText("mimimi");
		JButton btEnter = new JButton("Entrar");
		btEnter.addActionListener(new LoginAction());
		
		form.add(lblUser);
		form.add(user);
		form.add(lblPassword);
		form.add(password);
		form.add(new JLabel());
		form.add(btEnter);
		
		
		SpringUtilities.makeCompactGrid(form,
            3, 2,		//rows, cols
            6, 6,     	//initX, initY
            6, 6);      //xPad, yPad
		
		return form;
	}
	
	class LoginAction implements ActionListener{
	
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO 01 - fazer controller para verificar login
			String loginUser = user.getText();
			String loginPass = password.getText();
			if(Utils.isNullOrEmpty(loginUser))
				JOptionPane.showMessageDialog(null, "Digite um usuário.");
			else if(Utils.isNullOrEmpty(loginPass))
				JOptionPane.showMessageDialog(null, "Digite uma senha.");
			else
			{
				Usuario login = new Usuario(loginUser,loginPass);
				
				boolean acesso = ClientController.getInstance().autenticar(login);
				if(!acesso){
					JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos!");
				}else{
					Login.this.setVisible(false);
					Login.this.dispose();
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							MainView.getInstance().setVisible(true);
						}
					});
				}
			}
		}
	}
}
