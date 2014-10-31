package modmusst50.mods.CustomPacks;

import com.atlauncher.App;
import com.atlauncher.data.Language;
import com.atlauncher.data.Pack;
import com.atlauncher.gui.dialogs.InstanceInstallerDialog;
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
    JButton loadModsFromCode = new JButton("Load mods from code");
    JButton make = new JButton("Get my pack code");
    JButton install = new JButton("Install");
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
        topPanel.add(loadModsFromCode);
        topPanel.add(selectmods);
        topPanel.add(ammoutOfMods);
        add(topPanel, BorderLayout.NORTH);

        middlePanel = new JPanel();
        middlePanel.add(minecraftVersion);
        middlePanel.add(forgeVersion);
        add(middlePanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.add(make);
        bottomPanel.add(install);

        add(bottomPanel, BorderLayout.SOUTH);
        make.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.out.println(getPackCode());
            }
        });

        install.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modsToUse.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select 1 or more mods to install and use");
                    return;
                }
                if (App.settings.isInOfflineMode()) {
                    String[] options = {Language.INSTANCE.localize("common.ok")};
                    JOptionPane.showOptionDialog(App.settings.getParent(), Language.INSTANCE.localize("pack" + "" +
                                    ".offlinenewinstance"), Language.INSTANCE.localize("common.offline"),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                } else {
                    java.util.List<Pack> packs = App.settings.getPacks();

                    Pack packi = null;
                    for (Pack pack : packs) {
                        if (pack.getID() == 3) {
                            packi = pack;
                        }
                    }

                    if (App.settings.getAccount() == null) {
                        String[] options = {Language.INSTANCE.localize("common.ok")};
                        JOptionPane.showOptionDialog(App.settings.getParent(), Language.INSTANCE.localize("instance"
                                        + ".cannotcreate"), Language.INSTANCE.localize("instance.noaccountselected"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                    } else if (packi != null) {
                        new InstanceInstallerDialog(packi);
                    }
                }

            }
        });

        loadModsFromCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                Object result = JOptionPane.showInputDialog(frame, "Enter Pack code:");
                String text = (String) result;
                if (text.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a pack code!");
                    return;
                }
                String[] split = text.split(":");
                String forgeverson = split[0];
                for (int i = 1; i < split.length; i++) {
                    for (IMod scannermod : ModSanner.customMods) {
                        if (Integer.parseInt(split[i]) == scannermod.id()) {
                            modsToUse.add(scannermod);
                        }
                    }
                }
                refresh();
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

    public String getPackCode() {
        String code = "";
        //This is the forge version
        code = code + "1.7.10-10.13.2.1232:";
        for (int i = 0; i < modsToUse.size(); i++) {
            IMod mod = modsToUse.get(i);
            code = code + mod.id() + ":";
        }
        code = code.substring(0, code.length() - 1);
        return code;
    }
}
