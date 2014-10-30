package modmusst50.mods.CustomPacks;

import com.atlauncher.gui.tabs.Tab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Mark on 30/10/2014.
 */
public class CustomPacksTab extends JPanel implements Tab {
    public static ArrayList<IMod> modsToUse = new ArrayList<IMod>();
    JButton selectmods = new JButton("Select mods");
    JButton make = new JButton("Build my mod pack!");
    JLabel ammoutOfMods = new JLabel("Amout of mods in pack: " + modsToUse.size());
    JLabel minecraftVersion = new JLabel("Minecraft version: 1.7.10");
    JLabel forgeVersion = new JLabel("Minecraft forge version: 10.13.2.1232");
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JPanel middlePanel;

    public CustomPacksTab() {
        super(new BorderLayout());
        try {
            ModSanner.loadCustomMods();
        } catch (Exception e) {
            e.printStackTrace();
        }

        topPanel = new JPanel();
        topPanel.add(selectmods);
        topPanel.add(ammoutOfMods);
        add(topPanel, BorderLayout.NORTH);

        middlePanel = new JPanel();
        middlePanel.add(minecraftVersion);
        middlePanel.add(forgeVersion);
        add(middlePanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.add(make);

        add(bottomPanel, BorderLayout.SOUTH);
        make.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("This will make a mod pack code!");

            }
        });

        selectmods.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new SelectMods();
            }
        });
    }

    @Override
    public String getTitle() {
        return "Custom Packs";
    }

    public void refresh() {
        ammoutOfMods.setText("Amout of mods in pack: " + modsToUse.size());
        repaint();
        topPanel.repaint();

    }
}
