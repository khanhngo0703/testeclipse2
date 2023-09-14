package org.aptech;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindown {

	private JFrame frame;
	private JTextField txtid;
	private JTextField txtname;
	private JTextField txtage;
	private JTextField txtemail;
	private JTextField txtaddress;
	private JTextField txtgpa;
	private JTable table;
	private int currentPage = 1;
	private int recordsPerPage = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindown window = new MainWindown();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public MainWindown() {
		initialize();
		Connect();
		table_load(currentPage);
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/javaswing", "root", "");
		} catch (ClassNotFoundException ex) {

		} catch (SQLException ex) {

		}
	}

	public void table_load(int page) {
		try {
			
			int offset = (page - 1) * recordsPerPage;
			pst = con.prepareStatement("SELECT * FROM student LIMIT ? OFFSET ?");
			pst.setInt(1, recordsPerPage);
			pst.setInt(2, offset);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 513);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setBounds(10, 11, 49, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setBounds(10, 43, 49, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Age");
		lblNewLabel_2.setBounds(10, 75, 49, 14);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Address");
		lblNewLabel_3.setBounds(10, 133, 49, 14);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("GPA");
		lblNewLabel_4.setBounds(10, 253, 49, 14);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Email");
		lblNewLabel_5.setBounds(10, 100, 49, 14);
		frame.getContentPane().add(lblNewLabel_5);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name, age, email, address, gpa;

				name = txtname.getText();
				age = txtage.getText();
				email = txtemail.getText();
				address = txtaddress.getText();
				gpa = txtgpa.getText();

				try {
					pst = con.prepareStatement("INSERT INTO student(name,age,address,gpa,email) VALUES(?,?,?,?,?)");

					pst.setString(1, name);
					pst.setString(2, age);
					pst.setString(3, address);
					pst.setString(4, gpa);
					pst.setString(5, email);
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Record Addeddd!!");
					table_load(currentPage);

					txtname.setText("");
					txtage.setText("");
					txtaddress.setText("");
					txtgpa.setText("");
					txtemail.setText("");

					txtname.requestFocus();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				if (!validForm()) {
					return;
				}

			}
		});
		btnNewButton.setBounds(19, 278, 59, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name, age, email, address, gpa, id;

				name = txtname.getText();
				age = txtage.getText();
				email = txtemail.getText();
				address = txtaddress.getText();
				gpa = txtgpa.getText();
				id = txtid.getText();

				if (!validForm()) {
					return;
				}

				try {
					pst = con.prepareStatement(
							"UPDATE student SET name = ?, age = ?, email = ?, address = ?, gpa = ? WHERE id = ?");

					pst.setString(1, name);
					pst.setString(2, age);
					pst.setString(3, email);
					pst.setString(4, address);
					pst.setString(5, gpa);
					pst.setString(6, id);
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Record Updatedd!!");
					table_load(currentPage);

					txtname.setText("");
					txtage.setText("");
					txtemail.setText("");
					txtaddress.setText("");
					txtgpa.setText("");

					txtname.requestFocus();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnNewButton_1.setBounds(88, 278, 59, 23);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id;

				id = txtid.getText();

				try {
					pst = con.prepareStatement("DELETE FROM student WHERE id = ?");

					pst.setString(1, id);
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Record Deletedd!!");
					table_load(currentPage);

					txtname.setText("");
					txtage.setText("");
					txtemail.setText("");
					txtaddress.setText("");
					txtgpa.setText("");

					txtname.requestFocus();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnNewButton_2.setBounds(154, 278, 65, 23);
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Clear");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtname.setText("");
				txtage.setText("");
				txtemail.setText("");
				txtaddress.setText("");
				txtgpa.setText("");

				txtname.requestFocus();

				table_load(currentPage);

			}
		});
		btnNewButton_3.setBounds(229, 278, 59, 23);
		frame.getContentPane().add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Previous");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPage > 1) {
					currentPage--;
					table_load(currentPage);
				}
			}
		});
		btnNewButton_4.setBounds(299, 417, 104, 23);
		frame.getContentPane().add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Next");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int totalRecords = getTotalRecords();
				int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
				if (currentPage < totalPages) {
					currentPage++;
					table_load(currentPage);
				}
			}
		});
		btnNewButton_5.setBounds(413, 417, 99, 23);
		frame.getContentPane().add(btnNewButton_5);

		txtid = new JTextField();
		txtid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {
					String id = txtid.getText();

					pst = con.prepareStatement("SELECT name, age, email, address, gpa FROM student WHERE id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();

					if (rs.next() == true) {
						String name = rs.getString(1);
						String age = rs.getString(2);
						String email = rs.getString(3);
						String address = rs.getString(4);
						String gpa = rs.getString(5);

						txtname.setText(name);
						txtage.setText(age);
						txtemail.setText(email);
						txtaddress.setText(address);
						txtgpa.setText(gpa);

					} else {
						txtname.setText("");
						txtage.setText("");
						txtemail.setText("");
						txtaddress.setText("");
						txtgpa.setText("");

					}
				} catch (SQLException ex) {

				}

			}
		});
		txtid.setBounds(88, 8, 96, 20);
		frame.getContentPane().add(txtid);
		txtid.setColumns(10);

		txtname = new JTextField();
		txtname.setBounds(88, 40, 155, 20);
		frame.getContentPane().add(txtname);
		txtname.setColumns(10);

		txtage = new JTextField();
		txtage.setBounds(88, 72, 96, 20);
		frame.getContentPane().add(txtage);
		txtage.setColumns(10);

		txtemail = new JTextField();
		txtemail.setBounds(88, 97, 155, 20);
		frame.getContentPane().add(txtemail);
		txtemail.setColumns(10);

		txtaddress = new JTextField();
		txtaddress.setBounds(88, 129, 155, 110);
		frame.getContentPane().add(txtaddress);
		txtaddress.setColumns(10);

		txtgpa = new JTextField();
		txtgpa.setBounds(88, 250, 96, 20);
		frame.getContentPane().add(txtgpa);
		txtgpa.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(298, 11, 358, 382);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

	}

	private int getTotalRecords() {
		int total = 0;
		try {
			pst = con.prepareStatement("SELECT COUNT(*) FROM student");
			rs = pst.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	private boolean validForm() {
		if (Validation.isEmpty(txtname, "Ten khong duoc de trong!")) {
			return false;
		}

		if (Validation.isEmpty(txtage, "Tuoi khong duoc de trong!")) {
			return false;
		}

		if (Validation.isEmpty(txtemail, "Email khong duoc de trong!")) {
			return false;
		}

		if (!Validation.isEmail(txtemail, "Email khong dung dinh dang!")) {
			return false;
		}

		if (Validation.isEmpty(txtaddress, "Dia chi khong duoc de trong!")) {
			return false;
		}

		if (!Validation.isAddress(txtaddress, "Dia chi khong dung dinh dang!")) {
			return false;
		}

		if (Validation.isEmpty(txtgpa, "GPA khong duoc de trong!")) {
			return false;
		}

		return true;

	}
}
