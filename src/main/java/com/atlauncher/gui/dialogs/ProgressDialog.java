/**
 * Copyright 2013 and onwards by ATLauncher and Contributors
 *
 * This work is licensed under the GNU General Public License v3.0.
 * Link to license: http://www.gnu.org/licenses/gpl-3.0.txt
 */
package com.atlauncher.gui.dialogs;

import com.atlauncher.App;
import com.atlauncher.LogManager;
import com.atlauncher.data.Language;
import com.atlauncher.utils.Utils;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgressDialog extends JDialog {
    private static final long serialVersionUID = -4665490255300884927L;
    private String labelText; // The text to add to the JLabel
    private JProgressBar progressBar; // The Progress Bar
    private int max; // The maximum the progress bar should get to
    private Thread thread = null; // The Thread were optionally running
    private String closedLogMessage; // The message to log to the console when dialog closed
    private Object returnValue = null; // The value returned
    private int tasksToDo;
    private int tasksDone;

    public ProgressDialog(String title, int initMax, String initLabelText, String initClosedLogMessage) {
        super(App.settings.getParent(), ModalityType.APPLICATION_MODAL);
        this.labelText = initLabelText;
        this.max = initMax;
        this.closedLogMessage = initClosedLogMessage;
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setIconImage(Utils.getImage("/assets/image/Icon.png"));
        setSize(300, 80);
        setTitle(title);
        setLocationRelativeTo(App.settings.getParent());
        setLayout(new BorderLayout());
        setResizable(false);
        progressBar = new JProgressBar();
        if (max <= 0) {
            progressBar.setIndeterminate(true);
        }
        JLabel label = new JLabel(this.labelText, SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);
        if (this.closedLogMessage != null) {
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    LogManager.error(closedLogMessage);
                    if (thread != null) {
                        if (thread.isAlive()) {
                            thread.interrupt();
                        }
                    }
                    close(); // Close the dialog
                }
            });
        }
    }

    public void addThread(Thread thread) {
        this.thread = thread;
    }

    public void start() {
        if (this.thread != null) {
            thread.start();
        }
        setVisible(true);
    }

    public void setTotalTasksToDo(int tasksToDo) {
        this.tasksToDo = tasksToDo;
        this.tasksDone = 0;
        this.progressBar.setString("0/" + this.tasksToDo + " " + Language.INSTANCE.localize("common.tasksdone"));
        this.progressBar.setStringPainted(true);
        this.progressBar.setMaximum(this.tasksToDo);
    }

    public void doneTask() {
        this.progressBar.setString(++this.tasksDone + "/" + tasksToDo + " " + Language.INSTANCE.localize("common" + "" +
                ".tasksdone"));
        this.progressBar.setValue(this.tasksDone);
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Object getReturnValue() {
        return this.returnValue;
    }

    public void close() {
        setVisible(false); // Remove the dialog
        dispose(); // Dispose the dialog
    }
}
