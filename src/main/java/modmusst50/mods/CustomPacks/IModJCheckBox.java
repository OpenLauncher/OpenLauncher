package modmusst50.mods.CustomPacks;

import com.atlauncher.App;
import com.atlauncher.data.Mod;
import com.atlauncher.gui.CustomLineBorder;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by Mark on 30/10/2014.
 */
public class IModJCheckBox extends JCheckBox

{
    /**
     * Auto generated serial.
     */
    private static final long serialVersionUID = -4560260483416099547L;
    /**
     * Static object for the {@link javax.swing.border.Border} to show around the tooltips for mods with descriptions.
     */
    private static final Border HOVER_BORDER = new CustomLineBorder(5, App.THEME.getHoverBorderColor(), 2);
    /**
     * The mod this object will use to display it's data. Will be type {@link com.atlauncher.data.Mod}, {@link com.atlauncher.data.json.Mod}
     * or {@link com.atlauncher.data.DisableableMod}.
     */
    private IMod mod;

    /**
     * Constructor for use in the {@link com.atlauncher.gui.dialogs.ModsChooser} dialog.
     *
     * @param mod The mod this object is displaying data for
     */
    public IModJCheckBox(IMod mod) {
        super(mod.name());

        this.mod = mod;
    }


    /**
     * Gets the {@link Mod} object associated with this.
     *
     * @return The mod for this object
     */
    public IMod getMod() {
        return this.mod;
    }

    @Override
    public JToolTip createToolTip() {
        JToolTip tip = super.createToolTip();
        tip.setBorder(HOVER_BORDER);
        return tip;
    }
}
