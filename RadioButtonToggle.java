import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RadioButtonToggle implements ItemListener {
	
	AlterUI panel_toggle;
	hw2 radiobuttons;
	public RadioButtonToggle(AlterUI panel, hw2 frame)
	{
		panel_toggle = panel;
		radiobuttons = frame;
	}
		
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		panel_toggle.isAschecked = false;
		panel_toggle.isBuildingcheked = false;
		panel_toggle.isStudentschecked = false;
		panel_toggle.isSubmitClicked = false;
		
		//Point Query
		panel_toggle.xclicked = -9999; 
		panel_toggle.yclicked = -9999;
		
		//Range Query
		panel_toggle.polygonPointsX.clear();
		panel_toggle.polygonPointsY.clear();
		
		//Surrounding Query
		panel_toggle.xclicked_surrounding = -9999;
		panel_toggle.yclicked_surrounding = -9999;
		
		//Emergency Query
		panel_toggle.xclicked_emergency = -9999;
		panel_toggle.yclicked_emergency = -9999;
		
		if(radiobuttons.Whole_Region.isSelected())
		{
			panel_toggle.repaint();
		}else if(radiobuttons.Point_Query.isSelected())
		{
			panel_toggle.repaint();
		}else if (radiobuttons.Range_Query.isSelected()){
			panel_toggle.repaint();
		}else if (radiobuttons.Surrounding_Student.isSelected())
		{
			panel_toggle.repaint();
		}
		else if (radiobuttons.Emergency_Query.isSelected())
		{
			panel_toggle.repaint();
		}
			
	}	

}
