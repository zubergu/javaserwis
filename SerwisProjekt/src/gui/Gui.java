package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import obsluga.Orders;
import obsluga.Products;
import obsluga.UserRole;
import obsluga.Users;
import akcje.ActionZaloguj;
import akcje.PrzelaczNaPanelAdmin;
import akcje.PrzelaczNaPanelLogowanie;
import akcje.PrzelaczNaPanelSerwis;
import akcje.PrzelaczNaPanelSprzedawca;
import akcje.Wyloguj;
import akcje.ZalogujAdmin;
import akcje.ZalogujClerk;
import bazadanych.AddProductToTable;
import bazadanych.AddUserToTable;
import bazadanych.OrdersToTable;
import bazadanych.ProductsToTable;
import bazadanych.RemoveProductFromTable;
import bazadanych.RemoveUserFromTable;
import bazadanych.UpdateProductInTable;
import bazadanych.UsersToTable;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class Gui implements WindowListener{

	private JFrame frmSerwis;
	private JTextField textLogin;
	private JPasswordField passwordField;
	private Users users;
	private Products products;
	private Orders orders;
	private JTable tableUsers;
	private JTable tableProducts;
	private JTable tableOrders;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frmSerwis.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		this.users = new Users();
		this.products = new Products();
		this.orders = new Orders();
		initialize();
		try {
			UsersToTable.populateTableUsersFromDatabase(users, (DefaultTableModel) tableUsers.getModel(), users.getConnection());
			ProductsToTable.populateTableProductsFromDatabase(products, (DefaultTableModel)tableProducts.getModel(), products.getConnection());
			OrdersToTable.populateTableOrdersFromDatabase(orders, (DefaultTableModel)tableOrders.getModel(), orders.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSerwis = new JFrame();
		frmSerwis.setResizable(false);
		frmSerwis.setTitle("Serwis");
		frmSerwis.setBounds(100, 100, 592, 398);
		frmSerwis.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSerwis.addWindowListener(this);
		frmSerwis.getContentPane().setLayout(null);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBounds(12, 12, 566, 33);
		frmSerwis.getContentPane().add(menuPanel);
		menuPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		
		JPanel cardPanel = new JPanel();
		cardPanel.setBounds(12, 56, 566, 274);
		frmSerwis.getContentPane().add(cardPanel);
		CardLayout cardLayout = new CardLayout(0, 0);
		cardPanel.setLayout(cardLayout);
		
		JPanel panelLogowania = new JPanel();
		cardPanel.add(panelLogowania, "panelLogowania");
		panelLogowania.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("Wyloguj");
		btnNewButton.addActionListener(new Wyloguj(users,frmSerwis));
		panelLogowania.add(btnNewButton, BorderLayout.SOUTH);
		
		textLogin = new JTextField();
		panelLogowania.add(textLogin, BorderLayout.WEST);
		textLogin.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(15);
		panelLogowania.add(passwordField, BorderLayout.CENTER);
		
		JButton btnZaloguj = new JButton("Zaloguj");
		
		panelLogowania.add(btnZaloguj, BorderLayout.EAST);
		
		JPanel panelSprzedawcaZ = new JPanel();
		cardPanel.add(panelSprzedawcaZ, "panelSprzedawcaZ");
		panelSprzedawcaZ.setLayout(null);
		
		JButton btnNierozliczoneZlecenia = new JButton("Nierozliczone zlecenia");
		btnNierozliczoneZlecenia.setBounds(48, 5, 193, 25);
		panelSprzedawcaZ.add(btnNierozliczoneZlecenia);
		
		JButton btnStanZlecenia = new JButton("Stan zlecenia");
		btnStanZlecenia.setBounds(246, 5, 130, 25);
		panelSprzedawcaZ.add(btnStanZlecenia);
		
		JButton btnNoweZlecenie = new JButton("Nowe zlecenie");
		btnNoweZlecenie.setBounds(381, 5, 137, 25);
		btnNoweZlecenie.setHorizontalAlignment(SwingConstants.RIGHT);
		panelSprzedawcaZ.add(btnNoweZlecenie);
		
		JScrollPane scrollPaneOrders = new JScrollPane();
		scrollPaneOrders.setBounds(12, 40, 424, 208);
		panelSprzedawcaZ.add(scrollPaneOrders);
		
		tableOrders = new JTable();
		DefaultTableModel ordersTableModel = new DefaultTableModel();
		String[] headersOrdersTable ={"order_id",
				"customer",
				"order_type",
				"description",
				"order_status",
				"order_handler",
				"display",
				"keyboard",
				"mouse",
				"os",
				"processor",
				"mainboard", 
				"speakers",
				"graphics",
				"GPS",
				"bluetooth",
				"hdd" ,
				"dvd_rom",
				"ram", 
				"other"};
		ordersTableModel.setColumnIdentifiers(headersOrdersTable);
		tableOrders.setModel(ordersTableModel);
		tableOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableOrders.setFillsViewportHeight(true);
		tableOrders.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneOrders.setViewportView(tableOrders);
		
		JPanel panelSprzedawcaNz = new JPanel();
		cardPanel.add(panelSprzedawcaNz, "panelSprzedawcaNz");
		
		JTextArea txtrNieJestesZalogowany = new JTextArea();
		txtrNieJestesZalogowany.setText("Nie jestes zalogowany jako sprzedawca.");
		panelSprzedawcaNz.add(txtrNieJestesZalogowany);
		
		JPanel panelSerwisZ = new JPanel();
		cardPanel.add(panelSerwisZ, "panelSerwisZ");
		GridBagLayout gbl_panelSerwisZ = new GridBagLayout();
		gbl_panelSerwisZ.columnWidths = new int[]{119, 185, 0};
		gbl_panelSerwisZ.rowHeights = new int[]{25, 0, 0, 0, 0};
		gbl_panelSerwisZ.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelSerwisZ.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelSerwisZ.setLayout(gbl_panelSerwisZ);
		
		JButton btnSerwisantbuttontest = new JButton("Wez zlecenie");
		GridBagConstraints gbc_btnSerwisantbuttontest = new GridBagConstraints();
		gbc_btnSerwisantbuttontest.insets = new Insets(0, 0, 5, 0);
		gbc_btnSerwisantbuttontest.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSerwisantbuttontest.gridx = 1;
		gbc_btnSerwisantbuttontest.gridy = 0;
		panelSerwisZ.add(btnSerwisantbuttontest, gbc_btnSerwisantbuttontest);
		
		JButton btnUstawZlecenieNa = new JButton("Ustaw zlecenie na zakonczone");
		GridBagConstraints gbc_btnUstawZlecenieNa = new GridBagConstraints();
		gbc_btnUstawZlecenieNa.insets = new Insets(0, 0, 5, 0);
		gbc_btnUstawZlecenieNa.gridx = 1;
		gbc_btnUstawZlecenieNa.gridy = 1;
		panelSerwisZ.add(btnUstawZlecenieNa, gbc_btnUstawZlecenieNa);
		
		JButton btnZlecenieNieDo = new JButton("Zlecenie nie do wykonania.");
		GridBagConstraints gbc_btnZlecenieNieDo = new GridBagConstraints();
		gbc_btnZlecenieNieDo.insets = new Insets(0, 0, 5, 0);
		gbc_btnZlecenieNieDo.gridx = 1;
		gbc_btnZlecenieNieDo.gridy = 2;
		panelSerwisZ.add(btnZlecenieNieDo, gbc_btnZlecenieNieDo);
		
		JButton btnZwolnijZlecenie = new JButton("Zwolnij zlecenie");
		GridBagConstraints gbc_btnZwolnijZlecenie = new GridBagConstraints();
		gbc_btnZwolnijZlecenie.gridx = 1;
		gbc_btnZwolnijZlecenie.gridy = 3;
		panelSerwisZ.add(btnZwolnijZlecenie, gbc_btnZwolnijZlecenie);
		
		JPanel panelSerwisNz = new JPanel();
		cardPanel.add(panelSerwisNz, "panelSerwisNz");
		
		JTextArea txtrNieJestesZalogowany_1 = new JTextArea();
		txtrNieJestesZalogowany_1.setText("Nie jestes zalogowany jako serwisant.");
		panelSerwisNz.add(txtrNieJestesZalogowany_1);
		
		JPanel panelAdminZ = new JPanel();
		cardPanel.add(panelAdminZ, "panelAdminZ");
		
		JButton btnUsunUzytkownika = new JButton("Usun uzytkownika");
		btnUsunUzytkownika.setBounds(278, 12, 276, 25);
		
		JButton btnDodajUzytkownika = new JButton("Dodaj uzytkownika");
		btnDodajUzytkownika.setBounds(0, 12, 256, 25);
		
		panelAdminZ.setLayout(null);
		panelAdminZ.add(btnUsunUzytkownika);
		panelAdminZ.add(btnDodajUzytkownika);
		
		JScrollPane scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setBounds(10, 49, 256, 176);
		panelAdminZ.add(scrollPaneUsers);
		
		tableUsers = new JTable();
		DefaultTableModel usersTableModel = new DefaultTableModel();
		String[] headersUsersTable ={"User id","login","password","Role"};
		usersTableModel.setColumnIdentifiers(headersUsersTable);
		tableUsers.setModel(usersTableModel);
		tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableUsers.setFillsViewportHeight(true);

		btnDodajUzytkownika.addActionListener(new AddUserToTable(users, frmSerwis,tableUsers));
		btnUsunUzytkownika.addActionListener(new RemoveUserFromTable(users,frmSerwis,tableUsers));
		tableUsers.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneUsers.setViewportView(tableUsers);
		
		JScrollPane scrollPaneStorage = new JScrollPane();
		scrollPaneStorage.setBounds(288, 50, 266, 175);
		panelAdminZ.add(scrollPaneStorage);
		
		btnZaloguj.addActionListener(new ActionZaloguj(users,frmSerwis,tableUsers,textLogin,passwordField));
		
		tableProducts = new JTable();
		DefaultTableModel productsTableModel = new DefaultTableModel();
		String[] headersProductsTable ={"Product id","name","quantity","price"};
		productsTableModel.setColumnIdentifiers(headersProductsTable);
		tableProducts.setModel(productsTableModel);
		tableProducts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableProducts.setFillsViewportHeight(true);
		tableProducts.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneStorage.setViewportView(tableProducts);
		
		JButton btnUsunProdukt = new JButton("Usun");
		btnUsunProdukt.setBounds(384, 237, 75, 25);
		btnUsunProdukt.addActionListener(new RemoveProductFromTable(products, frmSerwis,tableProducts));
		panelAdminZ.add(btnUsunProdukt);
		
		JButton btnZmienProdukt = new JButton("Zmien");
		btnZmienProdukt.setBounds(479, 237, 75, 25);
		btnZmienProdukt.addActionListener(new UpdateProductInTable(products, frmSerwis,tableProducts));
		panelAdminZ.add(btnZmienProdukt);
		
		JButton btnDodajProdukt = new JButton("Dodaj produkt");
		btnDodajProdukt.addActionListener(new AddProductToTable(products,frmSerwis,tableProducts));
		btnDodajProdukt.setHorizontalAlignment(SwingConstants.RIGHT);
		btnDodajProdukt.setBounds(283, 237, 89, 25);
		panelAdminZ.add(btnDodajProdukt);
		
		
		JPanel panelAdminNz = new JPanel();
		cardPanel.add(panelAdminNz, "panelAdminNz");
		
		JTextArea txtrNieJestesZalogowany_2 = new JTextArea();
		txtrNieJestesZalogowany_2.setText("Nie jestes zalogowany jako administrator.");
		panelAdminNz.add(txtrNieJestesZalogowany_2);
		
		
		
		JButton btnLogowanie = new JButton("Logowanie");
		btnLogowanie.addActionListener(new PrzelaczNaPanelLogowanie(cardLayout,cardPanel));
		btnLogowanie.setFont(new Font("Dialog", Font.BOLD, 12));
		menuPanel.add(btnLogowanie);
		
		JButton btnSprzedawca = new JButton("Sprzedawca");
		btnSprzedawca.addActionListener(new PrzelaczNaPanelSprzedawca(users,cardLayout,cardPanel));
		menuPanel.add(btnSprzedawca);
		
		JButton btnNewButton_1 = new JButton("Serwisant");
		btnNewButton_1.addActionListener(new PrzelaczNaPanelSerwis(users,cardLayout, cardPanel));
		menuPanel.add(btnNewButton_1);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.addActionListener(new PrzelaczNaPanelAdmin(users,cardLayout,cardPanel));
		menuPanel.add(btnAdmin);
		
		
		
	}

	/* 
	 * (non-Javadoc)
	 * Methods that implement WindowListener for our class Gui.
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	/**
	 * Cleanup when closing application window.
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		this.users.closeConnection();
		this.products.closeConnection();
		this.orders.closeConnection();
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
