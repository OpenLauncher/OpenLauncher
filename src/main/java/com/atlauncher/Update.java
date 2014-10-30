/**
 * Copyright 2013 and onwards by ATLauncher and Contributors
 *
 * This work is licensed under the GNU General Public License v3.0.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */
package com.atlauncher;

import com.atlauncher.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Update {
    public static void main(String[] args) {
        String launcherPath = args[0];
        String temporaryUpdatePath = args[1];
        File launcher = new File(launcherPath);
        File temporaryUpdate = new File(temporaryUpdatePath);
        Utils.copyFile(temporaryUpdate, launcher.getParentFile());

        List<String> arguments = new ArrayList<String>();

        if (Utils.isMac() && new File(new File(System.getProperty("user.dir")).getParentFile().getParentFile(),
                "MacOS").exists()) {
            arguments.add("open");
            arguments.add(new File(System.getProperty("user.dir")).getParentFile().getParentFile().getParentFile()
                    .getAbsolutePath());

        } else {
            String path = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            if (Utils.isWindows()) {
                path += "w";
            }
            arguments.add(path);
            arguments.add("-jar");
            arguments.add(launcherPath);
            arguments.add("--updated=true");
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(arguments);

        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}