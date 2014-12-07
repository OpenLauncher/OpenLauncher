package modmuss50.mods.CustomPacks;

import com.atlauncher.App;
import com.atlauncher.data.Instance;
import com.atlauncher.data.Language;
import com.atlauncher.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by Mark on 30/10/2014.
 */
public class SelectMods extends JDialog {
    private static final long serialVersionUID = 7004414192679481818L;
    private Instance instance; // The instance this is for
    private JPanel bottomPanel, enabledModsPanel;
    private JScrollPane scroller2;
    private JButton doneButton;
    private JLabel topLabelLeft;
    private ArrayList<IModJCheckBox> enabledMods;

    public SelectMods() {
        super(App.settings.getParent(), "Select mods", ModalityType.APPLICATION_MODAL);
        setSize(550, 450);
        setLocationRelativeTo(App.settings.getParent());
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                dispose();
            }
        });

        topLabelLeft = new JLabel(Language.INSTANCE.localize("instance.enabledmods"));
        topLabelLeft.setHorizontalAlignment(SwingConstants.CENTER);
        add(topLabelLeft, BorderLayout.CENTER);

        enabledModsPanel = new JPanel();
        enabledModsPanel.setLayout(null);
        enabledModsPanel.setBackground(App.THEME.getModSelectionBackgroundColor());

        scroller2 = new JScrollPane(enabledModsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller2.getVerticalScrollBar().setUnitIncrement(16);
        scroller2.setPreferredSize(new Dimension(275, 350));
        add(scroller2);

        bottomPanel = new JPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableMods();
                GuiUtils.customPacksTab.refresh();
                dispose();
            }
        });
        bottomPanel.add(doneButton);

        loadMods();

        setVisible(true);
    }

    private void loadMods() {
        ArrayList<IMod> mods = ModSanner.customMods;
        enabledMods = new ArrayList<IModJCheckBox>();

        int eCount = 0;
        for (IMod mod : mods) {
            IModJCheckBox checkBox = null;
            int nameSize = getFontMetrics(Utils.getFont()).stringWidth(mod.name());
            checkBox = new IModJCheckBox(mod);
            checkBox.setBounds(0, (eCount * 20), nameSize + 23, 20);
            for(IMod iMod : CustomPacksTab.modsToUse){
                if(iMod.id().equals(mod.id())){
                    checkBox.setSelected(true);
                }
            }
            enabledMods.add(checkBox);
            eCount++;

        }
        for (int i = 0; i < enabledMods.size(); i++) {
            IModJCheckBox checkBox = enabledMods.get(i);
            enabledModsPanel.add(checkBox);
        }

        enabledModsPanel.setPreferredSize(new Dimension(0, enabledMods.size() * 20));
    }

    private void enableMods() {
        ArrayList<IModJCheckBox> mods = new ArrayList<IModJCheckBox>(enabledMods);
        for (IModJCheckBox mod : mods) {
            if (mod.isSelected()) {
                if (!CustomPacksTab.modsToUse.contains(mod.getMod())) {
                    CustomPacksTab.modsToUse.add(mod.getMod());
                }
            } else {
                if (CustomPacksTab.modsToUse.contains(mod.getMod())) {
                    CustomPacksTab.modsToUse.remove(mod.getMod());
                }
            }
        }
        reloadPanels();
    }

    private void reloadPanels() {
        enabledModsPanel.removeAll();
        loadMods();
        enabledModsPanel.repaint();
    }

}
