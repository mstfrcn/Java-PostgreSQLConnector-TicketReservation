package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import DBConnector.connector;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class form extends JFrame {
	private static JPanel contentPane;
	static JButton[] buton = new JButton[20];
	private JTextField txtIsim;
	private JLabel lblSoysim;
	private JTextField txtSoyisim;
	private JLabel lblCinsiyet;
	private JLabel lblNumara;
	private JTextField txtNumara;
	public ButtonGroup bg = new ButtonGroup();
	public ActionListener al;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					form frame = new form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public form() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblsim = new JLabel("Isim:");
		lblsim.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblsim.setBounds(373, 133, 100, 25);
		contentPane.add(lblsim);

		txtIsim = new JTextField();
		txtIsim.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtIsim.setBounds(483, 135, 120, 25);
		contentPane.add(txtIsim);
		txtIsim.setColumns(10);

		lblSoysim = new JLabel("SoyIsim:");
		lblSoysim.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSoysim.setBounds(373, 183, 100, 25);
		contentPane.add(lblSoysim);

		txtSoyisim = new JTextField();
		txtSoyisim.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtSoyisim.setColumns(10);
		txtSoyisim.setBounds(483, 185, 120, 25);
		contentPane.add(txtSoyisim);

		lblCinsiyet = new JLabel("Cinsiyet:");
		lblCinsiyet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCinsiyet.setBounds(373, 238, 100, 25);
		contentPane.add(lblCinsiyet);

		lblNumara = new JLabel("Numara:");
		lblNumara.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNumara.setBounds(373, 286, 100, 25);
		contentPane.add(lblNumara);

		txtNumara = new JTextField();
		txtNumara.setEnabled(false);
		txtNumara.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtNumara.setColumns(10);
		txtNumara.setBounds(483, 290, 120, 25);
		contentPane.add(txtNumara);
		JButton butonKayit = new JButton("KAYDET");

		butonKayit.setBounds(483, 336, 120, 31);
		contentPane.add(butonKayit);

		JRadioButton radioButonKadin = new JRadioButton("Kadin");
		radioButonKadin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radioButonKadin.setBounds(481, 241, 61, 23);
		contentPane.add(radioButonKadin);

		JRadioButton radioButonErkek = new JRadioButton("Erkek");
		radioButonErkek.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radioButonErkek.setBounds(544, 241, 61, 23);
		contentPane.add(radioButonErkek);
		bg.add(radioButonErkek);
		bg.add(radioButonKadin);
		connector.connect();
		boya();
		
		al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 20; i++) {
					if (e.getSource() == buton[i]) {
						txtNumara.setText(buton[i].getText());
					}
				}
			}
		};

		for (int i = 0; i < 20; i++) {
			buton[i].addActionListener(al);
		}
		
		actionAta();

		butonKayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIsim.getText().length() == 0 || txtSoyisim.getText().length() == 0
						|| (radioButonErkek.isSelected() == false && radioButonKadin.isSelected() == false)) {
					System.out.println("hata");
				} else {
					String id = txtNumara.getText();
					String isim = txtIsim.getText();
					String soyIsim = txtSoyisim.getText();
					Boolean cinsiyet = false;
					if (radioButonKadin.isSelected())
						cinsiyet = true;
					
					String sorgu = "update rezerve set " + 
					"ad= '"+isim+"',"  +
					"soyad= '"+soyIsim+"'," +
					"dolu= true ,"+
					"cinsiyet= "+ cinsiyet +" "+ 
					"where id = "+id;
					
					connector.guncelle(sorgu);
					temizle();
					boya();
					actionAta();
					
				}

			}
		});

	}
	void temizle() {
		for(int i=0; i<20; i++) {
			contentPane.remove(buton[i]);
			contentPane.repaint();
			contentPane.revalidate();
			txtIsim.setText(null);
			txtSoyisim.setText(null);
			txtNumara.setText(null);
		}
	}	
	void actionAta() {
		al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 20; i++) {
					if (e.getSource() == buton[i]) {
						txtNumara.setText(buton[i].getText());
					}
				}
			}
		};

		for (int i = 0; i < 20; i++) {
			buton[i].addActionListener(al);
		}
	}

	static void boya() {

		for (int i = 0; i < 10; i++) {
			buton[i] = new JButton();
			buton[i].setBounds(20, i * 55 + 50, 50, 50);
			contentPane.add(buton[i]);
			buton[i].setText(Integer.toString(i + 1));

			String sorgu = "select dolu,cinsiyet from rezerve where id =" + Integer.toString(i + 1);
			ResultSet rs = connector.listele(sorgu);

			try {
				while (rs.next()) {
					try {
						if (rs.getBoolean("dolu") == false) {
							buton[i].setBackground(Color.GREEN);
						} else {
							if (rs.getBoolean("cinsiyet") == true) {
								buton[i].setBackground(Color.PINK);
								buton[i].setEnabled(false);
							}
							if (rs.getBoolean("cinsiyet") == false) {
								buton[i].setBackground(Color.BLUE);
								buton[i].setEnabled(false);
							}

						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 10; i < 20; i++) {
			buton[i] = new JButton();
			buton[i].setBounds(80, (i - 10) * 55 + 50, 50, 50);
			contentPane.add(buton[i]);
			buton[i].setText(Integer.toString(i + 1));

			String sorgu = "select dolu,cinsiyet from rezerve where id =" + Integer.toString(i + 1);
			ResultSet rs = connector.listele(sorgu);

			try {
				while (rs.next()) {
					try {
						if (rs.getBoolean("dolu") == false) {
							buton[i].setBackground(Color.GREEN);
						} else {
							if (rs.getBoolean("cinsiyet") == true) {
								buton[i].setBackground(Color.PINK);
								buton[i].setEnabled(false);
							}
							if (rs.getBoolean("cinsiyet") == false) {
								buton[i].setBackground(Color.BLUE);
								buton[i].setEnabled(false);
							}

						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
