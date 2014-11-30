package modmuss50.mods.CustomPacks;

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
    public static int selectedrepo = 0;
    JButton selectmods = new JButton("Select mods");
    JButton loadModsFromCode = new JButton("Load mods from code");
    JButton make = new JButton("Get my pack code");
    JButton install = new JButton("Install");
    JLabel ammoutOfMods = new JLabel("Amout of mods in pack: " + modsToUse.size());
    JLabel minecraftVersion = new JLabel("Minecraft version: NONE");
    JLabel forgeVersion = new JLabel("Minecraft forge version: NONE");
    JComboBox<String> repoVersion = new JComboBox<String>();
    JLabel commingSoon = new JLabel("This is coming soon! Hang tight!");
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JPanel middlePanel;

    public CustomPacksTab() {
        super(new BorderLayout());

        try {
            ModSanner.loadRepo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(IRepo repo : ModSanner.repoVersions){
            repoVersion.addItem(repo.version());
        }

        try {
            ModSanner.loadCustomMods(repoVersion.getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //This means that it will always get the latest version of the repo. I might add a fuction to use older versions of the repo.
        selectedrepo = 0;

        topPanel = new JPanel();
        topPanel.add(repoVersion);
        topPanel.add(loadModsFromCode);
        topPanel.add(selectmods);
        topPanel.add(ammoutOfMods);
//        topPanel.add(commingSoon);
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
                JTextArea textarea = new JTextArea(getPackCode());
                textarea.setEditable(true);
                JOptionPane.showMessageDialog(null, textarea, "Your custom packcode!", JOptionPane.INFORMATION_MESSAGE);
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
                        InstanceInstallerDialog instanceInstallerDialog = new InstanceInstallerDialog(packi);
                        //TODO Change the repo version here when this is ready to added
                        new InstanceInstallerDialog(packi, getCurrentRepo().version() + " (Minecraft" + getCurrentRepo().minecraftVersion() + ")");
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
                String repoVersion = split[1];
                if(repoVersion != getCurrentRepo().version()){
                    //TODO add a way to change the repo version form inside the launcher
                    JOptionPane.showMessageDialog(null, "This mod pack was made with an old repo version. Please update the pack to the new version or change the repo version");
                    return;
                }
                for (int i = 2; i < split.length; i++) {
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

        repoVersion.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                try {
                    ModSanner.loadCustomMods(repoVersion.getSelectedItem().toString());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        refresh();
    }

    @Override
    public String getTitle() {
        return "Custom Packs";
    }

    public void refresh() {
        ammoutOfMods.setText("Amout of mods in pack: " + modsToUse.size());
        repaint();
        topPanel.repaint();
        minecraftVersion.setText("Minecraft version:" + getCurrentRepo().minecraftVersion());
        forgeVersion.setText("Minecraft forge version: " + getCurrentRepo().forgeVersion());
    }

    public String getPackCode() {
        String code = "";
        //This is the forge version
        code = code + getCurrentRepo().minecraftVersion() + "-" + getCurrentRepo().forgeVersion() + ":";
        //this is the repoversion
        code = code + getCurrentRepo().version() + ":";
        for (int i = 0; i < modsToUse.size(); i++) {
            IMod mod = modsToUse.get(i);
            code = code + mod.id() + ":";
        }
        code = code.substring(0, code.length() - 1);
        return code;
    }

    public IRepo getCurrentRepo() {
        return ModSanner.repoVersions.get(selectedrepo);
    }

}
