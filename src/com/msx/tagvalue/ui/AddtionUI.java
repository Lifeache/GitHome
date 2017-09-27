package com.msx.tagvalue.ui;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.msx.lifeache.ui.SearchableBox;

import msx.names.NameFactory;

public class AddtionUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8970920715591945303L;
	
	JTable table;
	JButton button;
	SearchableBox input;
	JButton load;
	JLabel label;
	JButton newList;
	int listNumber = 0;
	boolean haveACellInEditing = false;
	String oldValue;
	String newValue;
	public AddtionUI() {
		setTitle("添加值");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initUI();
		setVisible(true);
	}
	private void initUI(){
		setBounds(100, 100, 300, 500);
		setLayout(null);
		button = new JButton("保存");
		button.addActionListener(e -> {
			Vector<?> v = ((DefaultTableModel)(table.getModel())).getDataVector();
			Dao dao = new Dao();
			if (dao.checkNumber(listNumber)){
				dao.update(v, listNumber);
			} else {
				dao.save(v, listNumber);
			}
			System.out.println(dao.lastNumber());
		});
		button.setBounds(20, 20, 60, 30);
		add(button);
		Vector<String> library = new Vector<>();
		NameFactory nameFactory = new NameFactory("E:\\JavaHome\\我的游戏\\");
		ArrayList<String> array = nameFactory.getArray(NameFactory.What_Uint);
		for (String string : array) {
			library.add(string);
		}
		input = new SearchableBox(library);
		input.setBounds(90, 20, 120, 30);
		add(input);
		load = new JButton("装载");
		load.addActionListener(e -> {
			int n = Integer.valueOf(input.getText());
			System.out.println("装载表" + n);
			loadList(n);
		});
		load.setBounds(220, 20, 60, 30);
		add(load);
		newList = new JButton("新建");
		newList.addActionListener(e -> {
			
		});
		newList.setBounds(220, 60, 60, 30);
		add(newList);
		Dao dao = new Dao();
		listNumber = dao.lastNumber() + 1;
		label = new JLabel("列表号：" + listNumber);
		label.setBounds(20, 100, 60, 20);
		add(label);
		DefaultTableModel defaultTableModel = new DefaultTableModel(new String[][]{{"领域"},{"域名"},{"昵称"},{"用户名"},{"密码"}}, new String[]{"First","Second"});
		table = new JTable(defaultTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 120, 260, 460);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.RIGHT);
		table.getColumnModel().getColumn(0).setCellRenderer(render);
		JTextField field = new JTextField();
		
		field.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				newValue = field.getText();
				System.out.println("lost=" + field.getText());
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				oldValue = field.getText();
				System.out.println("gain=" + field.getText());
			}
		});
		field.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				System.out.println("daf");
				if ( e.getKeyCode() == KeyEvent.VK_ENTER){
					System.out.println("enter");
				}
			}
		});
		table.getColumnModel().getColumn(1).setCellEditor(new Renderer(field));
		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()){
			/**
			 * 
			 */
			private static final long serialVersionUID = -685054912918879490L;

			@Override
			public boolean isCellEditable(EventObject anEvent) {
				// TODO 自动生成的方法存根
				return false;
			}
		});
		add(scrollPane);
	}
	
	public void loadList(int n){
		Dao dao = new Dao();
		Vector<Vector<String>> list = dao.getList(n);
		Vector<String> head = new Vector<>();
		head.add("First");
		head.add("Second");
		table.setModel(new DefaultTableModel(list,head));
		listNumber = n;
		label.setText("列表号：" + listNumber);
	}
	
	public static void main(String[] args) {
		new AddtionUI();
	}
}
class Renderer extends DefaultCellEditor{

	public Renderer(JTextField textField) {
		super(textField);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5568333714588784804L;
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Component component = super.getTableCellEditorComponent(table, value, isSelected, row, column);
		TableCellRenderer renderer = table.getCellRenderer(row, column);
		Component c = renderer.getTableCellRendererComponent(table, value,
                isSelected, true, row, column);
		 if (c instanceof JComponent) {
			((JComponent)component).setBorder(((JComponent)c).getBorder());
         }
		return component;
	}
}