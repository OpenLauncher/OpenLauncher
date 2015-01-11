package openlauncher.gui;

import com.atlauncher.gui.tabs.PacksTab;
import com.atlauncher.gui.tabs.SettingsTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LauncherFrame extends JFrame {
	public static final Color COLOR_BLUE = new Color(16, 108, 163);
	public static final String TAB_Packs = "packs";
	public static final String TAB_News = "news";
	public static final String TAB_Instances = "instances";
	public static final String TAB_Settings = "settings";
	public static final String TAB_Custom = "custom";
	private GuiTab packsTab;
	private GuiTab newsTab;
	private GuiTab instancesTab;
	private GuiTab settingsTab;
	private GuiTab customPacksTab;
	private String currentTabName;
	private ResourceLoader resources = new ResourceLoader();
	private JPanel info;
	private CenterPanel centerPanel;

	public LauncherFrame() throws HeadlessException {
		super("OpenLauncher");
		setSize(new Dimension(1000, 615));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initComponets();
		setVisible(true);
	}

	private void initComponets() {
		ActionListener tabListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectTab(e.getActionCommand());
			}
		};

		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
		header.setBackground(new Color(189, 195, 199));
		header.setForeground(COLOR_BLUE);
		header.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
		this.add(header, BorderLayout.PAGE_START);

		ImageIcon headerIcon = resources.getIcon("assets/image/gui_icon_small.png");
		JButton headerLabel = new JButton(headerIcon);
		headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 0));
		headerLabel.setContentAreaFilled(false);
		headerLabel.setFocusPainted(false);
		headerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		header.add(headerLabel);

		header.add(Box.createRigidArea(new Dimension(6, 0)));

		packsTab = new GuiTab("Packs", resources);
		packsTab.setIsActive(true);
		packsTab.setHorizontalTextPosition(SwingConstants.LEADING);
		packsTab.addActionListener(tabListener);
		packsTab.setActionCommand(TAB_Packs);
		header.add(packsTab);

		newsTab = new GuiTab("News", resources);
		newsTab.setIsActive(true);
		newsTab.setHorizontalTextPosition(SwingConstants.LEADING);
		newsTab.addActionListener(tabListener);
		newsTab.setActionCommand(TAB_News);
		header.add(newsTab);

		instancesTab = new GuiTab("Instances", resources);
		instancesTab.setIsActive(true);
		instancesTab.setHorizontalTextPosition(SwingConstants.LEADING);
		instancesTab.addActionListener(tabListener);
		instancesTab.setActionCommand(TAB_Instances);
		header.add(instancesTab);

		settingsTab = new GuiTab("Settings", resources);
		settingsTab.setIsActive(true);
		settingsTab.setHorizontalTextPosition(SwingConstants.LEADING);
		settingsTab.addActionListener(tabListener);
		settingsTab.setActionCommand(TAB_Settings);
		header.add(settingsTab);

		customPacksTab = new GuiTab("Custom Packs", resources);
		customPacksTab.setIsActive(true);
		customPacksTab.setHorizontalTextPosition(SwingConstants.LEADING);
		customPacksTab.addActionListener(tabListener);
		customPacksTab.setActionCommand(TAB_Settings);
		header.add(customPacksTab);

		centerPanel = new CenterPanel();
		centerPanel.setBackground(new Color(149, 165, 166));
		centerPanel.setForeground(new Color(236, 240, 241));
		centerPanel.setTintColor(new Color(52, 73, 94));
		this.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());

		info = new JPanel();
		CardLayout infoLayout = new CardLayout();
		info.setLayout(infoLayout);
		info.setOpaque(false);
		PacksTab newsTab1 = new PacksTab();
		info.add(newsTab1);
		centerPanel.add(info, BorderLayout.CENTER);


	}

	protected void selectTab(String tabName) {
		packsTab.setIsActive(false);
		if (tabName.equalsIgnoreCase(TAB_Packs)) {
			packsTab.setIsActive(true);
		} else if (tabName.equalsIgnoreCase(TAB_Custom)) {
			customPacksTab.setIsActive(true);
		} else if (tabName.equalsIgnoreCase(TAB_Instances)) {
			instancesTab.setIsActive(true);
		} else if (tabName.equalsIgnoreCase(TAB_News)) {
			newsTab.setIsActive(true);
		} else if (tabName.equalsIgnoreCase(TAB_Settings)) {
			settingsTab.setIsActive(true);

			centerPanel = new CenterPanel();
			centerPanel.setBackground(new Color(149, 165, 166));
			centerPanel.setForeground(new Color(236, 240, 241));
			centerPanel.setTintColor(new Color(52, 73, 94));
			this.add(centerPanel, BorderLayout.CENTER);
			centerPanel.setLayout(new BorderLayout());

			info = new JPanel();
			CardLayout infoLayout = new CardLayout();
			info.setLayout(infoLayout);
			info.setOpaque(false);
			SettingsTab newsTab1 = new SettingsTab();
			info.add(newsTab1);
			centerPanel.add(info, BorderLayout.CENTER);
		}
		currentTabName = tabName;
	}
}
