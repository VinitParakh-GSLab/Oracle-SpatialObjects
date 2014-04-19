import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.border.LineBorder;

import java.awt.event.MouseAdapter;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class AlterUI extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	//Query1
	public List<ArrayList<Integer>> xPolybuildings = new ArrayList<ArrayList<Integer>>();
	public List<ArrayList<Integer>> yPolybuildings = new ArrayList<ArrayList<Integer>>();
	public List<Integer> xPointsstudents = new ArrayList<Integer>();
	public List<Integer> yPointsstudents = new ArrayList<Integer>();
	public List<Integer> asCords = new ArrayList<Integer>();
	
	//Query 2
	public List<ArrayList<Integer>> xPolybuildings_PointQuery = new ArrayList<ArrayList<Integer>>();
	public List<ArrayList<Integer>> yPolybuildings_PointQuery = new ArrayList<ArrayList<Integer>>();
	public List<Integer> xPointsstudents_PointQuery = new ArrayList<Integer>();
	public List<Integer> yPointsstudents_PointQuery = new ArrayList<Integer>();
	public List<Integer> asCords_PointQuery = new ArrayList<Integer>();
	int xclicked = -9999, yclicked = -9999;
	
	//Query 3
	public List<Integer> polygonPointsX = new ArrayList<Integer>();
	public List<Integer> polygonPointsY = new ArrayList<Integer>();
	public List<ArrayList<Integer>> xPolybuildings_RangeQuery = new ArrayList<ArrayList<Integer>>();
	public List<ArrayList<Integer>> yPolybuildings_RangeQuery = new ArrayList<ArrayList<Integer>>();
	public List<Integer> xPointsstudents_RangeQuery = new ArrayList<Integer>();
	public List<Integer> yPointsstudents_RangeQuery = new ArrayList<Integer>();
	public List<Integer> asCords_RangeQuery = new ArrayList<Integer>();
	
	//Query 4
	int xclicked_surrounding = -9999, yclicked_surrounding = -9999;
	int surroundingquery_nearestX = -9999, surroundingquery_nearestY = -9999, surroundingquery_nearestR = -9999;
	public List<Integer> xPointsstudents_SurroundingQuery = new ArrayList<Integer>();
	public List<Integer> yPointsstudents_SurroundingQuery = new ArrayList<Integer>();
	
	//Query 5
	int xclicked_emergency = -9999, yclicked_emergency = -9999;
	int emergencyquery_nearestX = -9999, emergencyquery_nearestY = -9999, emergencyquery_nearestR = -9999;
	String emergencyquery_announcementname;
	public List<Integer> xPointsstudents_EmergencyQuery = new ArrayList<Integer>();
	public List<Integer> yPointsstudents_EmergencyQuery = new ArrayList<Integer>();
	HashMap<String, emergencySystem> students_in_AS = new HashMap<String, emergencySystem>();
	
	public boolean isStudentschecked = false, isAschecked = false, isBuildingcheked = false, isWholeregion = false, isPointquery = false, isRangequery = false, isSurroundingquery = false, isEmergencyquery = false, isSubmitClicked =  false;
	
	public HashMap<String, Color> color_codes = new HashMap<String, Color>();
	
	public AlterUI()
	{
	color_codes.put("a1psa", Color.magenta);
	color_codes.put("a2ohe", Color.yellow);
	color_codes.put("a3sgm", Color.green);
	color_codes.put("a4hnb", Color.blue);
	color_codes.put("a5vhe", Color.orange);
	color_codes.put("a6ssc", Color.white);
	color_codes.put("a7helen", Color.cyan);
	}
	
	
			
	public void paint(Graphics g)
	{
		super.paint(g);
		if (isWholeregion)
		{		
			if (isBuildingcheked)
			{
				draw_buildings(g);
			}
			if (isStudentschecked)
			{
				draw_students(g);
			}
			if (isAschecked)
			{
				draw_announcement_systems(g);
			}
			isWholeregion = false;
		}
		
		if(isPointquery)
		{
			if(xclicked >= 0 && yclicked >= 0)
			{
				draw_point(g);
				if(isSubmitClicked)
				{
					if (isBuildingcheked)
					{
						draw_buildings_pointquery(g);
					}
			
					if (isStudentschecked)
					{
						draw_students_pointquery(g);
					}
			
					if (isAschecked)
					{
						draw_announcement_systems_pointquery(g);
					}
				
					isSubmitClicked = false;
					isPointquery = false;
				}
			}
		}
		
		if(isRangequery)
		{
			if(polygonPointsX.size() >= 3)
			{
				draw_polygon(g);
			
				if (isSubmitClicked)
				{
					if (isBuildingcheked)
					{
						draw_buildings_rangequery(g);
					}
					
					if (isStudentschecked)
					{
						draw_students_rangequery(g);
					}
					
					if (isAschecked)
					{
						draw_announcement_systems_rangequery(g);
					}
					
					isSubmitClicked = false;
					isRangequery = false;				
				}
			}
			
			
		}
		
		if(isSurroundingquery)
		{
			if(xclicked_surrounding >= 0 && yclicked_surrounding >= 0)
			{
				draw_point_surroundingquery(g);
				if (isSubmitClicked)
				{
				draw_students_surroundingquery(g);
				isSubmitClicked = false;
				isSurroundingquery = false;
				}
			}
		}
		
		if(isEmergencyquery)
		{
			if(xclicked_emergency >=0 && yclicked_emergency >=0)
			{
				if(isSubmitClicked)
				{
				Iterator<String> it = students_in_AS.keySet().iterator();
				while(it.hasNext())
				{
					String key = it.next().toString();
					draw_emergencyquery(key, students_in_AS.get(key), g);
				
				}
				isSubmitClicked = false;
				isSurroundingquery = false;
				students_in_AS.clear();
				}
				else
				{
				draw_point_emergencyquery(g);
				}
			}
		}
	}
	
	public void draw_emergencyquery(String key, emergencySystem obj, Graphics g)
	{
		g.setColor(color_codes.get(key));
		g.fillRect(obj.xcord-7, obj.ycord-7, 15, 15);
		g.drawOval(obj.xcord - obj.radius, obj.ycord - obj.radius, obj.radius*2, obj.radius*2);
		
		for (int i=0;  i < obj.Students.size(); i = i+2)
		{	
				g.fillRect(obj.Students.get(i)-5, obj.Students.get(i+1)-5, 10, 10);
		}
		
		xPointsstudents_EmergencyQuery.clear();
		yPointsstudents_EmergencyQuery.clear();
	}
	
	public void draw_point_emergencyquery(Graphics g)
	{
		g.setColor(Color.green);
		g.drawLine(xclicked_emergency, yclicked_emergency, xclicked_emergency, yclicked_emergency);
		g.setColor(Color.red);
		g.fillRect(emergencyquery_nearestX-7, emergencyquery_nearestY-7, 15, 15);
		g.drawOval(emergencyquery_nearestX - emergencyquery_nearestR, emergencyquery_nearestY - emergencyquery_nearestR, emergencyquery_nearestR*2, emergencyquery_nearestR*2);
	}
	
	public void draw_students_surroundingquery(Graphics g)
	{
		for (int i=0;  i < xPointsstudents_SurroundingQuery.size(); i++)
		{	
				g.setColor(Color.green);
				g.fillRect(xPointsstudents_SurroundingQuery.get(i)-5, yPointsstudents_SurroundingQuery.get(i)-5, 10, 10);
		}
		xPointsstudents_SurroundingQuery.clear();
		yPointsstudents_SurroundingQuery.clear();
	}
	
	public void draw_point_surroundingquery(Graphics g)
	{
		g.setColor(Color.green);
		g.drawLine(xclicked_surrounding, yclicked_surrounding, xclicked_surrounding, yclicked_surrounding);
		g.setColor(Color.red);
		g.fillRect(surroundingquery_nearestX-7, surroundingquery_nearestY-7, 15, 15);
		g.drawOval(surroundingquery_nearestX - surroundingquery_nearestR, surroundingquery_nearestY - surroundingquery_nearestR, surroundingquery_nearestR*2, surroundingquery_nearestR*2);
	}
	
	public void draw_announcement_systems_rangequery(Graphics g)
	{
		for (int i=0; i < asCords_RangeQuery.size(); i=i+3)
		{	
			g.setColor(Color.red);
			g.fillRect(asCords_RangeQuery.get(i)-7, asCords_RangeQuery.get(i+1)-7, 15, 15);
			g.drawOval(asCords_RangeQuery.get(i)-asCords_RangeQuery.get(i+2), asCords_RangeQuery.get(i+1)-asCords_RangeQuery.get(i+2), asCords_RangeQuery.get(i+2)*2, asCords_RangeQuery.get(i+2)*2);
		}
		asCords_RangeQuery.clear();
	}
	
	
	public void draw_students_rangequery(Graphics g)
	{
		for (int i=0; i<xPointsstudents_RangeQuery.size(); i++)
		{	
			g.setColor(Color.green);
			g.fillRect(xPointsstudents_RangeQuery.get(i)-5, yPointsstudents_RangeQuery.get(i)-5, 10, 10);
		}
		xPointsstudents_RangeQuery.clear();
		yPointsstudents_RangeQuery.clear();
	}
	
	public void draw_buildings_rangequery(Graphics g)
	{
		for (int i=0; i < xPolybuildings_RangeQuery.size(); i++)
		{	
			g.setColor(Color.yellow);
			g.drawPolygon(convertIntegers(xPolybuildings_RangeQuery.get(i)), convertIntegers(yPolybuildings_RangeQuery.get(i)), xPolybuildings_RangeQuery.get(i).size());	
		}
		xPolybuildings_RangeQuery.clear();
		yPolybuildings_RangeQuery.clear();
	}
	
	public void draw_polygon(Graphics g)
	{
		g.setColor(Color.red);
		g.drawPolygon(convertIntegers(polygonPointsX), convertIntegers(polygonPointsY), polygonPointsX.size());	
	}
	
	public void draw_point (Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(xclicked-3, yclicked-3, 5, 5);
		g.drawOval(xclicked-50, yclicked-50, 100, 100);
	}
	
	public void draw_announcement_systems(Graphics g)
	{
		g.setColor(Color.red);
		for (int i=0; i < asCords.size(); i=i+3)
		{	
			g.fillRect(asCords.get(i)-7, asCords.get(i+1)-7, 15, 15);
			g.drawOval(asCords.get(i)-asCords.get(i+2), asCords.get(i+1)-asCords.get(i+2), asCords.get(i+2)*2, asCords.get(i+2)*2);
		}
		asCords.clear();
	}
	
	public void draw_students(Graphics g)
	{
		g.setColor(Color.green);
		for (int i=0; i<xPointsstudents.size(); i++)
		{	
			g.fillRect(xPointsstudents.get(i)-5, yPointsstudents.get(i)-5, 10, 10);
		}
		xPointsstudents.clear();
	}
	
	public void draw_buildings(Graphics g)
	{
		
		g.setColor(Color.yellow);		
		for (int i=0; i < xPolybuildings.size(); i++)
		{	
			g.drawPolygon(convertIntegers(xPolybuildings.get(i)), convertIntegers(yPolybuildings.get(i)), xPolybuildings.get(i).size());	
		}
		xPolybuildings.clear();
	}
	
	public void draw_announcement_systems_pointquery(Graphics g)
	{
		
		for (int i=0; i < asCords_PointQuery.size(); i=i+3)
		{	
			if (i == 0)
				g.setColor(Color.yellow);
			else
				g.setColor(Color.green);
			
			g.fillRect(asCords_PointQuery.get(i)-7, asCords_PointQuery.get(i+1)-7, 15, 15);
			g.drawOval(asCords_PointQuery.get(i)-asCords_PointQuery.get(i+2), asCords_PointQuery.get(i+1)-asCords_PointQuery.get(i+2), asCords_PointQuery.get(i+2)*2, asCords_PointQuery.get(i+2)*2);
		}
		asCords_PointQuery.clear();
	}
	
	public void draw_students_pointquery(Graphics g)
	{
		for (int i=0; i<xPointsstudents_PointQuery.size(); i++)
		{	
			if(i==0)
				g.setColor(Color.yellow);
			else
				g.setColor(Color.green);
			
			g.fillRect(xPointsstudents_PointQuery.get(i)-5, yPointsstudents_PointQuery.get(i)-5, 10, 10);
		}
		xPointsstudents_PointQuery.clear();
		yPointsstudents_PointQuery.clear();
	}
	
	public void draw_buildings_pointquery(Graphics g)
	{
				
		for (int i=0; i < xPolybuildings_PointQuery.size(); i++)
		{	
			if(i==0)
				g.setColor(Color.yellow);
			else
				g.setColor(Color.green);
			
			g.drawPolygon(convertIntegers(xPolybuildings_PointQuery.get(i)), convertIntegers(yPolybuildings_PointQuery.get(i)), xPolybuildings_PointQuery.get(i).size());	
		}
		xPolybuildings_PointQuery.clear();
		yPolybuildings_PointQuery.clear();
	}
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
}

public class hw2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	int x, y, query_counter=1;
	JRadioButton Whole_Region = new JRadioButton("Whole Region", true);
	JRadioButton Point_Query = new JRadioButton("Point Query");
	JRadioButton Range_Query = new JRadioButton("Range Query");
	JRadioButton Surrounding_Student = new JRadioButton("Surrounding Student");
	JRadioButton Emergency_Query = new JRadioButton("Emergency Query");
	JTextArea textArea = new JTextArea();
	
	//temp variables to store the polygon co-ordinates
	public List<Integer> temppolygonPointsX = new ArrayList<Integer>();
	public List<Integer> temppolygonPointsY = new ArrayList<Integer>();
    
	//label alter ui
	AlterUI panel = new AlterUI();
	
	JCheckBox AS = new JCheckBox("AS");
	JCheckBox Building = new JCheckBox("Building");
	JCheckBox Students = new JCheckBox("Students");
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hw2 frame = new hw2();
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
	public hw2() {
		
		setResizable(false);
		setTitle("Vinit Rajendra Parakh 9283509659");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 684);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel.setBorder(UIManager.getBorder("EditorPane.border"));
		panel.setBounds(0, 0, 820, 580);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel.isSubmitClicked = false;
				
				if(Point_Query.isSelected())
				{
					//When click different point clear the initial buildings
					panel.isPointquery = true;
					panel.xclicked = arg0.getX();
					panel.yclicked = arg0.getY();
					panel.repaint();
				}
				
				if(Range_Query.isSelected())
				{
					panel.isRangequery = true;
					if(arg0.getButton() == 1)
					{
						temppolygonPointsX.add(arg0.getX());
						temppolygonPointsY.add(arg0.getY());
					}
					
					if(arg0.getButton() == 3)
					{	
						if(panel.polygonPointsX.size() != 0)
						{
							panel.polygonPointsX.clear();
							panel.polygonPointsY.clear();
						}
					
						panel.polygonPointsX.addAll(temppolygonPointsX);
						panel.polygonPointsY.addAll(temppolygonPointsY);
						temppolygonPointsX.clear();
						temppolygonPointsY.clear();
						panel.repaint();
					}
				}
				
				if(Surrounding_Student.isSelected())
				{
					panel.isSurroundingquery = true;
					panel.xclicked_surrounding = arg0.getX();
					panel.yclicked_surrounding = arg0.getY();
					Connection connection = null;
					try 
					{
						connection = dbConnection.ConnectToDB();
						ResultSet ASvertices = null;
						Statement mainStatement = connection.createStatement();
					
						String query1 = "SELECT xcenter, ycenter, announcement_radius from vrp_announcement "
		    				+ "WHERE SDO_NN(as_point_shape,"
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE(" + panel.xclicked_surrounding + "," + panel.yclicked_surrounding + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'sdo_num_res=1', 0) = 'TRUE'";
						
						ASvertices = mainStatement.executeQuery(query1); 
						
						textArea.append(query_counter + "." +  query1 + "\n");
						query_counter++;
						
						
						while(ASvertices.next())
						{
							panel.surroundingquery_nearestX = ASvertices.getInt("xcenter");
							panel.surroundingquery_nearestY = ASvertices.getInt("ycenter");
							panel.surroundingquery_nearestR = ASvertices.getInt("announcement_radius");
						}
						panel.repaint();
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					finally{
						dbConnection.closeDB(connection);;
					}
				}
				
				if(Emergency_Query.isSelected())
				{
					panel.isEmergencyquery = true;
					panel.xclicked_emergency = arg0.getX();
					panel.yclicked_emergency = arg0.getY();
					Connection connection = null;
					try 
					{
						connection = dbConnection.ConnectToDB();
						ResultSet ASvertices = null;
						Statement mainStatement = connection.createStatement();
					
						String query1 = "SELECT xcenter, ycenter, announcement_radius, announcement_name from vrp_announcement "
		    				+ "WHERE SDO_NN(shape,"
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE(" + panel.xclicked_emergency + "," + panel.yclicked_emergency + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'sdo_num_res=1', 0) = 'TRUE'";
						
						textArea.append(query_counter + "." +  query1 + "\n");
						query_counter++;
						
						ASvertices = mainStatement.executeQuery(query1); 
						while(ASvertices.next())
						{
							panel.emergencyquery_nearestX = ASvertices.getInt("xcenter");
							panel.emergencyquery_nearestY = ASvertices.getInt("ycenter");
							panel.emergencyquery_nearestR = ASvertices.getInt("announcement_radius");
							panel.emergencyquery_announcementname = ASvertices.getString("announcement_name");
						}
						panel.repaint();
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					finally{
						dbConnection.closeDB(connection);;
					}
				}
			}
		});
		
		
		lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("map.jpg")));
		lblNewLabel.setBounds(0, 0, 820, 580);
		panel.add(lblNewLabel);	
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(830, 0, 214, 106);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Active Feature Type");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 11, 105, 14);
		panel_1.add(lblNewLabel_1);
				
		AS.setBounds(10, 36, 168, 23);
		panel_1.add(AS);
		
		Building.setBounds(10, 62, 97, 37);
		panel_1.add(Building);
			
		Students.setBounds(109, 62, 99, 37);
		panel_1.add(Students);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(830, 117, 214, 463);
		contentPane.add(panel_2);
		
		JLabel lblNewLabel_2 = new JLabel("Query");
		lblNewLabel_2.setBounds(10, 11, 72, 27);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 12));
		panel_2.setLayout(null);
		panel_2.add(lblNewLabel_2);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(4, 49, 200, 264);
		panel_2.add(panel_5);
		panel_5.setLayout(null);
		
		
		//Adding item listener on each radio button
		Whole_Region.addItemListener(new RadioButtonToggle(panel, this));
		Point_Query.addItemListener(new RadioButtonToggle(panel, this));
		Range_Query.addItemListener(new RadioButtonToggle(panel, this));
		Surrounding_Student.addItemListener(new RadioButtonToggle(panel, this));
		Emergency_Query.addItemListener(new RadioButtonToggle(panel, this));
		
		
		Whole_Region.setBounds(6, 24, 125, 23);
		panel_5.add(Whole_Region);
		buttonGroup.add(Whole_Region);
		
		Point_Query.setBounds(6, 71, 125, 23);
		panel_5.add(Point_Query);
		buttonGroup.add(Point_Query);
		
		Range_Query.setBounds(6, 118, 125, 23);
		panel_5.add(Range_Query);
		buttonGroup.add(Range_Query);
		
		Surrounding_Student.setBounds(6, 165, 188, 23);
		panel_5.add(Surrounding_Student);
		buttonGroup.add(Surrounding_Student);
		
		Emergency_Query.setBounds(6, 212, 188, 23);
		panel_5.add(Emergency_Query);
		buttonGroup.add(Emergency_Query);
		
		JButton Submit = new JButton("Submit Query");
		Submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel.isSubmitClicked = true;
				display_regions();
			}
		});
		
		Submit.setBounds(10, 324, 194, 59);
		panel_2.add(Submit);	
		
		JLabel lblCurrentxY = new JLabel("Current (x, y)");
		lblCurrentxY.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblCurrentxY.setBounds(10, 394, 72, 14);
		panel_2.add(lblCurrentxY);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel_4.setBounds(10, 411, 194, 41);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		final JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(10, 11, 174, 19);
		panel_4.add(lblNewLabel_3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 582, 1044, 73);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1044, 73);
		panel_3.add(scrollPane);
		
		textArea.setColumns(100);
		scrollPane.setViewportView(textArea);
		lblNewLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				lblNewLabel_3.setText(Integer.toString(arg0.getX()) + ',' + Integer.toString(arg0.getY()));
			}
		});
	}
	
	public void display_regions()
	{
		Connection connection = null;
		try 
		{
			connection = dbConnection.ConnectToDB();
			ResultSet ASvertices = null;
			Statement mainStatement = connection.createStatement();
		    
			//Query 1
		    if (Whole_Region.isSelected())
		    {	
		    	panel.isWholeregion = true;
		    	if(Building.isSelected())
		    	{
		    		panel.isBuildingcheked = true;
		    		String query = "select v1.shape.GET_WKT() from vrp_buildings v1";
		    		
		    		ASvertices = mainStatement.executeQuery(query);
		    		
		    		textArea.append(query_counter + "." +  query + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
			    		ArrayList<Integer> xcord = new ArrayList<Integer>();
			    		ArrayList<Integer> ycord = new ArrayList<Integer>();
		    			String []polygon = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POLYGON ", "").split(", ");
		    		
		    			for(int i=0; i < polygon.length; i++)
		    			{
		    				String s = 	polygon[i].split(" ")[0];
		    				String s1 = polygon[i].split(" ")[1];
		    				xcord.add(Integer.parseInt(s.substring(0, s.indexOf("."))));
		    				ycord.add(Integer.parseInt(s1.substring(0, s1.indexOf("."))));
		    				
		    			}
		    			panel.xPolybuildings.add(xcord);
		    			panel.yPolybuildings.add(ycord);
		    		}
		    	}
		    	else
		    	{
		    		panel.isBuildingcheked = false;
		    	}
		    	
		    	if(Students.isSelected())	
		    	{
		    		panel.isStudentschecked = true;
		    		
		    		String query = "select v1.shape.GET_WKT() from vrp_students v1";
		    		ASvertices = mainStatement.executeQuery(query);
		    		
		    		textArea.append(query_counter + "." +  query + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
		    			String xcord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[0];
		    			String ycord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[1];
		    			panel.xPointsstudents.add(Integer.parseInt(xcord.substring(0, xcord.indexOf("."))));
		    			panel.yPointsstudents.add(Integer.parseInt(ycord.substring(0, ycord.indexOf("."))));
		    		}
		    	}
		    	else
		    	{
		    		panel.isStudentschecked = false;
		    	}
		    	
		    	if(AS.isSelected())	
		    	{
		    		panel.isAschecked = true;
		    		
		    		String query = "select xcenter, ycenter, announcement_radius from vrp_announcement";
		    		ASvertices = mainStatement.executeQuery(query); 

		    		textArea.append(query_counter + "." +  query + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
		    			panel.asCords.add(ASvertices.getInt("xcenter"));
		    			panel.asCords.add(ASvertices.getInt("ycenter"));
		    			panel.asCords.add(ASvertices.getInt("announcement_radius"));
		    		}
		    	}
		    	else
		    	{
		    		panel.isAschecked = false;
		    	}
		    	
		    	panel.repaint();
		    }
		    
		    //Query2
		    if(Point_Query.isSelected() && panel.xclicked >= 0 && panel.yclicked >= 0)
		    {
		    	panel.isPointquery = true;
		    	if(Building.isSelected())
		    	{
		    		panel.isBuildingcheked = true;
		    		String query1 = "SELECT v1.shape.GET_WKT(), v1.building_id from vrp_buildings v1 "
		    				+ "WHERE SDO_NN(v1.shape, "
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE("+ panel.xclicked + "," + panel.yclicked + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'sdo_num_res=1', 1) = 'TRUE'"
		    				+ "AND SDO_NN_DISTANCE(1) < 50 ";
		    		
		    		String building_id= "";
		    		
		    		ASvertices = mainStatement.executeQuery(query1);
		    		
		    		textArea.append(query_counter + "." +  query1 + "\n");
					query_counter++;
		    		
		    		while(ASvertices.next())
		    		{
			    		ArrayList<Integer> xcord = new ArrayList<Integer>();
			    		ArrayList<Integer> ycord = new ArrayList<Integer>();
		    			String []polygon = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POLYGON ", "").split(", ");
		    			building_id = ASvertices.getString(2);
		    			
		    			for(int i=0; i <polygon.length; i++)
		    			{
		    				String s = 	polygon[i].split(" ")[0];
		    				String s1 = polygon[i].split(" ")[1];
		    				xcord.add(Integer.parseInt(s.substring(0, s.indexOf("."))));
		    				ycord.add(Integer.parseInt(s1.substring(0, s1.indexOf("."))));
		    				
		    			}
		    			
		    			panel.xPolybuildings_PointQuery.add(xcord);
		    			panel.yPolybuildings_PointQuery.add(ycord);
		    		}
		    		
		    		String query = "SELECT v1.shape.GET_WKT() from vrp_buildings v1 "
		    				+ "WHERE SDO_WITHIN_DISTANCE(v1.shape,"
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE("+ panel.xclicked + "," + panel.yclicked + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'distance=50') = 'TRUE' AND v1.building_id != '" + building_id +"'" ; 
		    		
		    		ASvertices = mainStatement.executeQuery(query);
		    		
		    		textArea.append(query_counter + "." +  query + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
			    		ArrayList<Integer> xcord = new ArrayList<Integer>();
			    		ArrayList<Integer> ycord = new ArrayList<Integer>();
		    			String []polygon = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POLYGON ", "").split(", ");
		    			
		    			for(int i=0; i <polygon.length; i++)
		    			{
		    				String s = 	polygon[i].split(" ")[0];
		    				String s1 = polygon[i].split(" ")[1];
		    				xcord.add(Integer.parseInt(s.substring(0, s.indexOf("."))));
		    				ycord.add(Integer.parseInt(s1.substring(0, s1.indexOf("."))));
		    				
		    			}
		    			panel.xPolybuildings_PointQuery.add(xcord);
		    			panel.yPolybuildings_PointQuery.add(ycord);
		    		}
		    	}
		    	
		    	else
		    	{
		    		panel.isBuildingcheked = false;
		    	}
		    	
		    	if(Students.isSelected())	
		    	{
		    		panel.isStudentschecked = true;
		    		
		    		String query1 = "SELECT v1.shape.GET_WKT(), v1.studentID from vrp_students v1 "
		    				+ "WHERE SDO_NN(v1.shape,"
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE("+ panel.xclicked + "," + panel.yclicked + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'sdo_num_res=1', 1) = 'TRUE'"
		    				+ "AND SDO_NN_DISTANCE(1) < 50 ";
		    		
		    		String studentID ="";
		    		ASvertices = mainStatement.executeQuery(query1);
		    		
		    		textArea.append(query_counter + "." +  query1 + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
		    			studentID = ASvertices.getString(2);
		    			String xcord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[0];
		    			String ycord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[1];
		    			panel.xPointsstudents_PointQuery.add(Integer.parseInt(xcord.substring(0, xcord.indexOf("."))));
		    			panel.yPointsstudents_PointQuery.add(Integer.parseInt(ycord.substring(0, ycord.indexOf("."))));
		    		}

		    		String query = "SELECT v1.shape.GET_WKT() from vrp_students v1 "
		    				+ "WHERE SDO_WITHIN_DISTANCE(v1.shape,"
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE("+ panel.xclicked + "," + panel.yclicked + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'distance=50') = 'TRUE' AND studentID != '"+ studentID + "'";
		    		
		    		ASvertices = mainStatement.executeQuery(query);
		    		
		    		textArea.append(query_counter + "." +  query + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
		    			String xcord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[0];
		    			String ycord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[1];
		    			panel.xPointsstudents_PointQuery.add(Integer.parseInt(xcord.substring(0, xcord.indexOf("."))));
		    			panel.yPointsstudents_PointQuery.add(Integer.parseInt(ycord.substring(0, ycord.indexOf("."))));
		    		}
		    		
		    	}

		    	else
		    	{
		    		panel.isStudentschecked = false;
		    	}
		    	
		    	if(AS.isSelected())	
		    	{
		    		panel.isAschecked = true;
		    		
		    		String query1 = "SELECT announcement_name, xcenter, ycenter, announcement_radius from vrp_announcement "
		    				+ "WHERE SDO_NN(shape,"
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE("+ panel.xclicked + "," + panel.yclicked + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'sdo_num_res=1', 1) = 'TRUE'"
		    				+ "AND SDO_NN_DISTANCE(1) < 50 ";
		    		
		    		String announcement_name = "";
		    		ASvertices = mainStatement.executeQuery(query1);
		    		
		    		textArea.append(query_counter + "." +  query1 + "\n");
					query_counter++;
		    		
		    		while(ASvertices.next())
		    		{
		    			announcement_name = ASvertices.getString("announcement_name");
		    			panel.asCords_PointQuery.add(ASvertices.getInt("xcenter"));
		    			panel.asCords_PointQuery.add(ASvertices.getInt("ycenter"));
		    			panel.asCords_PointQuery.add(ASvertices.getInt("announcement_radius"));
		    		}
		    		
		    		
		    		String query = "SELECT xcenter, ycenter, announcement_radius from vrp_announcement "
		    				+ "WHERE SDO_WITHIN_DISTANCE(shape,"
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE("+ panel.xclicked + "," + panel.yclicked + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'distance=50') = 'TRUE' AND announcement_name != '" + announcement_name + "'";
		    		
		    		ASvertices = mainStatement.executeQuery(query);   
		    		
		    		textArea.append(query_counter + "." +  query + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
		    			panel.asCords_PointQuery.add(ASvertices.getInt("xcenter"));
		    			panel.asCords_PointQuery.add(ASvertices.getInt("ycenter"));
		    			panel.asCords_PointQuery.add(ASvertices.getInt("announcement_radius"));
		    		}
		    	}
		    	else
		    	{
		    		panel.isAschecked = false;
		    	}
		    	
		    	panel.repaint();
		    }
		    
		    //Query 3
		    if(Range_Query.isSelected() && panel.polygonPointsX.size() >= 3)
		    {
		    	panel.isRangequery = true;
		    	
		    	if(Building.isSelected())
		    	{
		    		panel.isBuildingcheked = true;
		    		
		    		String points = "";
		    		for(int i = 0; i < panel.polygonPointsX.size(); i++)
		    		{
		    			if(i == (panel.polygonPointsX.size()-1))
		    				points = points + panel.polygonPointsX.get(i) +  "," + panel.polygonPointsY.get(i);
		    			else
		    				points = points + panel.polygonPointsX.get(i) +  "," + panel.polygonPointsY.get(i) + ",";
		    		}
		    		points = points + "," + panel.polygonPointsX.get(0) + "," + panel.polygonPointsY.get(0);
		    		
		    		String query1 = "SELECT v1.SHAPE.GET_WKT() from vrp_buildings v1"
		    				+ " WHERE SDO_ANYINTERACT(v1.shape," 
	                        + " SDO_GEOMETRY(2003, NULL, NULL," 
	                        + " SDO_ELEM_INFO_ARRAY(1,1003,1)," 
	                        + " SDO_ORDINATE_ARRAY(" + points + ")))='TRUE'" ; 
		    				
		    		ASvertices = mainStatement.executeQuery(query1);
		    		
		    		textArea.append(query_counter + "." +  query1 + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
			    		ArrayList<Integer> xcord = new ArrayList<Integer>();
			    		ArrayList<Integer> ycord = new ArrayList<Integer>();
		    			String []polygon = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POLYGON ", "").split(", ");
		    			
		    			for(int i=0; i <polygon.length; i++)
		    			{
		    				String s = 	polygon[i].split(" ")[0];
		    				String s1 = polygon[i].split(" ")[1];
		    				xcord.add(Integer.parseInt(s.substring(0, s.indexOf("."))));
		    				ycord.add(Integer.parseInt(s1.substring(0, s1.indexOf("."))));
		    				
		    			}
		    			
		    			panel.xPolybuildings_RangeQuery.add(xcord);
		    			panel.yPolybuildings_RangeQuery.add(ycord);
		    		}
		    	}
		    	
		    	else
		    	{
		    		panel.isBuildingcheked = false;
		    	}
		    	
		    	if(Students.isSelected())	
		    	{
		    		panel.isStudentschecked = true;
		    		String points = "";
		    		for(int i = 0; i < panel.polygonPointsX.size(); i++)
		    		{
		    			if(i == (panel.polygonPointsX.size()-1))
		    				points = points + panel.polygonPointsX.get(i) +  "," + panel.polygonPointsY.get(i);
		    			else
		    				points = points + panel.polygonPointsX.get(i) +  "," + panel.polygonPointsY.get(i) + ",";
		    		}
		    		points = points + "," + panel.polygonPointsX.get(0) + "," + panel.polygonPointsY.get(0);
		    		
		    		String query1 = "SELECT v1.SHAPE.GET_WKT() from vrp_students v1"
		    				+ " WHERE SDO_ANYINTERACT(v1.shape," 
	                        + " SDO_GEOMETRY(2003, NULL, NULL," 
	                        + " SDO_ELEM_INFO_ARRAY(1,1003,1)," 
	                        + " SDO_ORDINATE_ARRAY(" + points + ")))='TRUE'" ; 
		    				
		    		ASvertices = mainStatement.executeQuery(query1);
		    		
		    		textArea.append(query_counter + "." +  query1 + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
		    			String xcord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[0];
		    			String ycord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[1];
		    			panel.xPointsstudents_RangeQuery.add(Integer.parseInt(xcord.substring(0, xcord.indexOf("."))));
		    			panel.yPointsstudents_RangeQuery.add(Integer.parseInt(ycord.substring(0, ycord.indexOf("."))));
		    		}

		    	}
		    	else
		    	{
		    		panel.isStudentschecked = false;
		    	}
		    	
		    	if(AS.isSelected())	
		    	{
		    		panel.isAschecked = true;
		    		String points = "";
		    		for(int i = 0; i < panel.polygonPointsX.size(); i++)
		    		{
		    			if(i == (panel.polygonPointsX.size()-1))
		    				points = points + panel.polygonPointsX.get(i) +  "," + panel.polygonPointsY.get(i);
		    			else
		    				points = points + panel.polygonPointsX.get(i) +  "," + panel.polygonPointsY.get(i) + ",";
		    		}
		    		points = points + "," + panel.polygonPointsX.get(0) + "," + panel.polygonPointsY.get(0);
		    		
		    		String query1 = "SELECT v1.xcenter, v1.ycenter, v1.announcement_radius from vrp_announcement v1"
		    				+ " WHERE SDO_ANYINTERACT(v1.shape," 
	                        + " SDO_GEOMETRY(2003, NULL, NULL," 
	                        + " SDO_ELEM_INFO_ARRAY(1,1003,1)," 
	                        + " SDO_ORDINATE_ARRAY(" + points + ")))='TRUE'" ;
		    		
		    		ASvertices = mainStatement.executeQuery(query1); 
		    		
		    		textArea.append(query_counter + "." +  query1 + "\n");
					query_counter++;
		    		
		    		while(ASvertices.next())
		    		{
		    			panel.asCords_RangeQuery.add(ASvertices.getInt("xcenter"));
		    			panel.asCords_RangeQuery.add(ASvertices.getInt("ycenter"));
		    			panel.asCords_RangeQuery.add(ASvertices.getInt("announcement_radius"));
		    		}
		    	}
		    	else
		    	{
		    		panel.isAschecked = false;
		    	}
		    	
		    	panel.repaint();
		    }
		    
		    //Query 4
			if(Surrounding_Student.isSelected() && panel.xclicked_surrounding >= 0 && panel.yclicked_surrounding >= 0)
			{
			    panel.isSurroundingquery = true;
			    
			    String query1 = "SELECT v1.shape.GET_WKT() from vrp_students v1 "
	    				+ "WHERE SDO_WITHIN_DISTANCE(v1.shape,"
	    				+ "SDO_GEOMETRY(2001, NULL, "
	    				+ "SDO_POINT_TYPE("+ panel.surroundingquery_nearestX + "," + panel.surroundingquery_nearestY + ", NULL),"
	    				+ "NULL, NULL),"
	    				+ "'distance="+ panel.surroundingquery_nearestR + "') = 'TRUE'";
			    
			    ASvertices = mainStatement.executeQuery(query1); 
			    
	    		textArea.append(query_counter + "." +  query1 + "\n");
				query_counter++;
				
			    while(ASvertices.next())
	    		{
	    			String xcord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[0];
	    			String ycord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[1];
	    			panel.xPointsstudents_SurroundingQuery.add(Integer.parseInt(xcord.substring(0, xcord.indexOf("."))));
	    			panel.yPointsstudents_SurroundingQuery.add(Integer.parseInt(ycord.substring(0, ycord.indexOf("."))));
	    		}
			    
			    panel.repaint();
			}
			
			//Query 5
			if(Emergency_Query.isSelected() && panel.xclicked_emergency >= 0 && panel.yclicked_emergency >= 0)
			{
			    panel.isEmergencyquery = true;
			    String query1 = "SELECT v1.shape.GET_WKT() from vrp_students v1 "
	    				+ "WHERE SDO_WITHIN_DISTANCE(v1.shape,"
	    				+ "SDO_GEOMETRY(2001, NULL, "
	    				+ "SDO_POINT_TYPE("+ panel.emergencyquery_nearestX + "," + panel.emergencyquery_nearestY + ", NULL),"
	    				+ "NULL, NULL),"
	    				+ "'distance="+ panel.emergencyquery_nearestR + "') = 'TRUE'";
			    
			    ASvertices = mainStatement.executeQuery(query1); 
			    
	    		textArea.append(query_counter + "." +  query1 + "\n");
				query_counter++;
				
			    while(ASvertices.next())
	    		{
	    			String xcord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[0];
	    			String ycord = (ASvertices.getString(1)).replace("(", "").replace(")", "").replace("POINT ", "").split(" ")[1];
	    			panel.xPointsstudents_EmergencyQuery.add(Integer.parseInt(xcord.substring(0, xcord.indexOf("."))));
	    			panel.yPointsstudents_EmergencyQuery.add(Integer.parseInt(ycord.substring(0, ycord.indexOf("."))));
	    		}
			    
			    for(int i=0; i<panel.xPointsstudents_EmergencyQuery.size(); i++)
			    {
			    	String query2 = "SELECT announcement_name, xcenter, ycenter, announcement_radius from vrp_announcement "
		    				+ "WHERE SDO_NN(shape,"
		    				+ "SDO_GEOMETRY(2001, NULL, "
		    				+ "SDO_POINT_TYPE(" + panel.xPointsstudents_EmergencyQuery.get(i) + "," + panel.yPointsstudents_EmergencyQuery.get(i) + ", NULL),"
		    				+ "NULL, NULL),"
		    				+ "'sdo_batch_size=0') = 'TRUE' AND announcement_name != '" + panel.emergencyquery_announcementname + "' AND ROWNUM<=1";
			    	
			    	ASvertices = mainStatement.executeQuery(query2);    
			    	
		    		textArea.append(query_counter + "." +  query2 + "\n");
					query_counter++;
					
		    		while(ASvertices.next())
		    		{
		    			if(panel.students_in_AS.get(ASvertices.getString("announcement_name")) == null)
		    			{
		    				emergencySystem obj = new emergencySystem();
		    				obj.radius = ASvertices.getInt("announcement_radius");
		    				obj.xcord = ASvertices.getInt("xcenter");
		    				obj.ycord = ASvertices.getInt("ycenter");
		    				obj.Students.add(panel.xPointsstudents_EmergencyQuery.get(i));
		    				obj.Students.add(panel.yPointsstudents_EmergencyQuery.get(i));
		    				panel.students_in_AS.put(ASvertices.getString("announcement_name"), obj);
		    			}
		    			else
		    			{
		    				emergencySystem obj = panel.students_in_AS.get(ASvertices.getString("announcement_name"));
		    				obj.Students.add(panel.xPointsstudents_EmergencyQuery.get(i));
		    				obj.Students.add(panel.yPointsstudents_EmergencyQuery.get(i));
		    			}
		    		}
			    	
			    	
			    }
			    
			   panel.repaint();
			}

		    	
		}catch(SQLException e) {
				System.out.println(e);
		}
		finally{
			dbConnection.closeDB(connection);
		}
	}
}
	
