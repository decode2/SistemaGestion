package com.mycompany.sistemagestion.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ListModel;

import java.awt.TextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.mycompany.sistemagestion.dao.ClienteDao;
import com.mycompany.sistemagestion.models.Cliente;
import com.mysql.cj.util.StringUtils;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Formulario extends JFrame {

	private JPanel contentPane;
	private JButton btnGuardar;
	private JTextField txtNombre;
	private JLabel lblNewLabel;
	private JList listClientes;
	private JButton btnEliminar;
	private JTextField txtApellido;
	private JTextField txtEmail;
	private JTextField txtTelefono;
	
	private List<Cliente> list = new ArrayList<>(); 
	private JButton btnEditar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Formulario frame = new Formulario();
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
	public Formulario() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				
				actualizarLista();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 492);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		listClientes = new JList();
		listClientes.setBounds(31, 34, 271, 373);
		listClientes.setBorder(UIManager.getBorder("Tree.editorBorder"));
		listClientes.setValueIsAdjusting(true);
		listClientes.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		
		
		txtNombre = new JTextField();
		txtNombre.setBounds(427, 94, 170, 23);
		txtNombre.setColumns(10);
		
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = listClientes.getSelectedIndex();
				
				
				//list.remove(index);
				Cliente client = list.get(index);
				
				ClienteDao dao = new ClienteDao();
				dao.delete(client.getId());
				
				actualizarLista();
				
				JOptionPane.showMessageDialog(rootPane, "Cliente eliminado correctamente");
			}
		});
		btnEliminar.setActionCommand("Eliminar");
		btnEliminar.setBounds(213, 408, 89, 23);
		contentPane.add(btnEliminar);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(365, 128, 52, 14);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(427, 124, 170, 23);
		contentPane.add(txtApellido);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(365, 157, 52, 14);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(427, 153, 170, 23);
		contentPane.add(txtEmail);
		
		JLabel lblTelefono = new JLabel("Tel\u00E9fono");
		lblTelefono.setBounds(365, 186, 52, 14);
		contentPane.add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(427, 182, 170, 23);
		contentPane.add(txtTelefono);
		
		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setBounds(365, 73, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		final JLabel lblID = new JLabel("");
		lblID.setBounds(427, 73, 46, 14);
		contentPane.add(lblID);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = listClientes.getSelectedIndex();
				
				
				//list.remove(index);
				Cliente client = list.get(index);
				
				txtNombre.setText(client.getNombre());
				txtApellido.setText(client.getApellido());
				txtTelefono.setText(client.getTelefono());
				txtEmail.setText(client.getEmail());
				
				lblID.setText(client.getId());
			}
		});
		btnEditar.setBounds(31, 408, 89, 23);
		contentPane.add(btnEditar);
		
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(459, 216, 89, 23);
		btnGuardar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String strNombre = txtNombre.getText();
				String strApellido = txtApellido.getText();
				String strEmail = txtEmail.getText();
				String strTelefono = txtTelefono.getText();
				
				if (StringUtils.isEmptyOrWhitespaceOnly(strNombre) || StringUtils.isEmptyOrWhitespaceOnly(strApellido) || StringUtils.isEmptyOrWhitespaceOnly(strEmail) || StringUtils.isEmptyOrWhitespaceOnly(strTelefono)) {
					JOptionPane.showMessageDialog(rootPane, "El cliente debe tener los datos completos");
					return;
				}
				
				Cliente client = new Cliente();
				client.setNombre(strNombre);
				client.setApellido(strApellido);
				client.setEmail(strEmail);
				client.setTelefono(strTelefono);
				
				if (!StringUtils.isEmptyOrWhitespaceOnly(lblID.getText())) {
					client.setId(lblID.getText());
				}
				
				ClienteDao dao = new ClienteDao();
				//dao.add(client);
				
				dao.save(client);
				
				cleanTextBoxes();
				lblID.setText(null);
				
				actualizarLista();
				JOptionPane.showMessageDialog(rootPane, "El cliente se guardó correctamente");
			}
			
		});
		
		lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(365, 98, 52, 14);
		contentPane.setLayout(null);
		contentPane.add(txtNombre);
		contentPane.add(listClientes);
		contentPane.add(lblNewLabel);
		contentPane.add(btnGuardar);
		
	}
	
	private void actualizarLista(){
		
		DefaultListModel datos = new DefaultListModel();
		
		ClienteDao dao = new ClienteDao();
		list = dao.list();
		
		Cliente client = new Cliente();
		
		for (int i = 0; i < list.size(); i++) {
			client = list.get(i);
			datos.addElement(client.getNombreCompleto());
		}
		
		listClientes.setModel(datos);
	}
	
	private void cleanTextBoxes() {
		
		txtNombre.setText(null);
		txtApellido.setText(null);
		txtEmail.setText(null);
		txtTelefono.setText(null);
	}
}
