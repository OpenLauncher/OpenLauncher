package openlauncher.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GuiTab extends JButton implements MouseListener {
	private boolean isActive;
	private DefaultButtonModel model;

	public GuiTab(String text, ResourceLoader resources) {
		super(text);

		model = new DefaultButtonModel();
		setIsActive(false);
		setFont(new Font("Arial", Font.PLAIN, 26));
		setForeground(Color.black);
		setBackground(new Color(189, 195, 199));
		setBorder(BorderFactory.createEmptyBorder(20, 18, 20, 18));
		addMouseListener(this);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
		this.setOpaque(isActive);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	public void addActionListener(ActionListener listener) {
		model.addActionListener(listener);
	}

	public String getActionCommand() {
		return model.getActionCommand();
	}

	public void setActionCommand(String command) {
		model.setActionCommand(command);
	}

	public ActionListener[] getActionListeners() {
		return model.getActionListeners();
	}

	public void removeActionListener(ActionListener listener) {
		model.removeActionListener(listener);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		model.setPressed(true);
		model.setArmed(true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		model.setPressed(false);
		model.setArmed(false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		model.setRollover(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		model.setRollover(false);
	}
}