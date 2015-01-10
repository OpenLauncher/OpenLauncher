package openlauncher.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mark on 10/01/2015.
 */
public class CenterPanel extends JPanel {
	private Color tintColor;
	private boolean tintActive;
	private JLabel overIcon = null;

	public CenterPanel() {
		tintColor = new Color(0, 0, 0, 0);
	}

	public Color getTintColor() {
		return tintColor;
	}

	public void setTintColor(Color color) {
		this.tintColor = color;
	}

	public void setOverIcon(ImageIcon image) {
		if (overIcon != null) {
			remove(overIcon);
		}

		overIcon = new JLabel(image);
		overIcon.setVisible(false);
		add(overIcon);
		revalidate();
	}

	public boolean isTintActive() {
		return tintActive;
	}

	public void setTintActive(boolean tintActive) {
		this.tintActive = tintActive;

		if (overIcon != null) {
			overIcon.setVisible(tintActive);
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					revalidate();
				}
			});
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	@Override
	public void doLayout() {
		super.doLayout();

		if (overIcon != null) {
			int width = overIcon.getIcon().getIconWidth();
			int height = overIcon.getIcon().getIconHeight();
			overIcon.setBounds(getWidth() / 2 - width / 2, getHeight() / 2 - height / 2, width, height);
		}
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);

		if (tintActive) {
			graphics.setColor(getTintColor());
			graphics.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
