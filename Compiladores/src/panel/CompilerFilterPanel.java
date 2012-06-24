package panel;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CompilerFilterPanel extends JPanel {

	private JLabel programLabel;
	private JComboBox program;
	
	public CompilerFilterPanel() {
		initialize();
		layoutCompoenents();
	}

	private void layoutCompoenents() {
		String colunas = "left:30dlu,50dlu,3dlu,pref,115dlu";
		String linhas = "top: 12dlu";
		CellConstraints cell = new CellConstraints();
		
		FormLayout layout = new FormLayout(colunas,linhas);
		PanelBuilder builder = new PanelBuilder(layout/*,new FormDebugPanel()*/);
		
		builder.add(programLabel,cell.xy(2, 1));
		builder.add(program,cell.xy(4, 1));
		
		JPanel panel = builder.getPanel();
		panel.setBackground(Color.RED);
		add(panel);
		
	}

	private void initialize() {
		programLabel = new JLabel("Programa:");
		program = new JComboBox();
		program.addItem("		");
		program.addItem("Factorial");
		program.addItem("BinarySearch");
		program.addItem("BubbleSort");
		program.addItem("TreeVisitor");
		program.addItem("QuickSort");
		program.addItem("LinearSearch");
		
		
		
	}

	public JComboBox getProgram() {
		return program;
	}
	
	
}
