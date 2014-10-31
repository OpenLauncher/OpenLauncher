/**
 * Copyright 2013 and onwards by ATLauncher and Contributors
 *
 * This work is licensed under the GNU General Public License v3.0.
 * Link to license: http://www.gnu.org/licenses/gpl-3.0.txt.
 */
package com.atlauncher.gui.components;

import com.atlauncher.App;
import com.atlauncher.gui.CustomLineBorder;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.Border;

public class JLabelWithHover extends JLabel {
    private static final long serialVersionUID = -4371080285355832166L;
    private static final Border HOVER_BORDER = new CustomLineBorder(5, App.THEME.getHoverBorderColor(), 2);

    public JLabelWithHover(Icon icon, String tooltipText, Border border) {
        super();
        super.setIcon(icon);
        super.setToolTipText(tooltipText);
        super.setBorder(border);
    }

    public JLabelWithHover(String label, Icon icon, String tooltipText) {
        super(label);
        super.setIcon(icon);
        super.setToolTipText(tooltipText);
    }

    @Override
    public JToolTip createToolTip() {
        JToolTip tip = super.createToolTip();
        tip.setBorder(HOVER_BORDER);
        return tip;
    }
}
