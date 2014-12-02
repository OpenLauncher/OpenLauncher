package modmuss50.mods.CustomPacks;

import com.atlauncher.App;
import com.atlauncher.LogManager;
import com.atlauncher.data.Constants;
import com.atlauncher.data.Language;
import com.atlauncher.data.Pack;
import com.atlauncher.gui.dialogs.InstanceInstallerDialog;
import com.atlauncher.gui.tabs.Tab;
import com.atlauncher.gui.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

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
        repoVersion.setFont(Theme.DEFAULT_THEME.getDefaultFont().deriveFont(10F));
        topPanel.add(repoVersion);
        topPanel.add(loadModsFromCode);
        topPanel.add(selectmods);
        ammoutOfMods.setFont(Theme.DEFAULT_THEME.getDefaultFont().deriveFont(15F));
        topPanel.add(ammoutOfMods);
//        topPanel.add(commingSoon);
        add(topPanel, BorderLayout.NORTH);

        middlePanel = new JPanel();
        minecraftVersion.setFont(Theme.DEFAULT_THEME.getDefaultFont().deriveFont(15F));
        middlePanel.add(minecraftVersion);
        forgeVersion.setFont(Theme.DEFAULT_THEME.getDefaultFont().deriveFont(15F));
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
                if (text == null || text.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a pack code!");
                    return;
                }
                try {
                    text = getText(Constants.API_BASE_URL + "view/raw/" + text);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return;
                }
                String[] split = text.split(":");
                String forgeverson = split[0];
                String repoVersion = split[1];
                System.out.println(repoVersion);
                if(!repoVersion.equals(getCurrentRepo().version())){
                    JOptionPane.showMessageDialog(null, "This mod pack was made with an old repo version. Please update the pack to the new version or change the repo version");
                    return;
                }
                for (int i = 2; i < split.length; i++) {
                    try {
                        ModSanner.loadCustomMods(getCurrentRepo().version());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    for (IMod scannermod : ModSanner.customMods) {
                        if (split[i].equals(scannermod.id())) {
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
        return "Custom Pack";
    }

    public void refresh() {
        ammoutOfMods.setText("Amount of mods in pack: " + modsToUse.size());
        repaint();
        topPanel.repaint();
        minecraftVersion.setText("Minecraft version:" + getCurrentRepo().minecraftVersion());
        forgeVersion.setText("Minecraft forge version: " + getCurrentRepo().forgeVersion());
    }

    public String getPackCode() {
        String code = "";
        code = code + getCurrentRepo().forgeVersion() + ":" + getCurrentRepo().version() + ":";
        for (int i = 0; i < modsToUse.size(); i++) {
            IMod mod = modsToUse.get(i);
            code = code + mod.id() + ":";
        }
        code = code.substring(0, code.length() - 1);

        String url  = "error";

        try {
            UploadPackCode.code = code;
            String result = App.TASKPOOL.submit(new UploadPackCode()).get();
            if (result.contains(Constants.PASTE_CHECK_URL)) {
                result = result.replace(Constants.API_BASE_URL + "view/", "");
                App.TOASTER.pop("Code uploaded and link copied to clipboard");
                LogManager.info("Code uploaded and link copied to clipboard: " + result);
                StringSelection text = new StringSelection(result);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(text, null);
                url = result;
            } else {
                App.TOASTER.popError("Code failed to upload!");
                LogManager.error("Code failed to upload: " + result);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }


        return url;
    }

    public IRepo getCurrentRepo() {
        return ModSanner.repoVersions.get(selectedrepo);
    }

    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }

}
