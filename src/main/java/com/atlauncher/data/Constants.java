package com.atlauncher.data;


public class Constants {
    public static final String launcherName = "OpenLauncher";
    public static final LauncherVersion VERSION = new LauncherVersion(1, 0, 1, 0);
    public static final String API_BASE_URL = "";
    public static final String PASTE_CHECK_URL = "http://rushmead.playat.ch/stikket/";
    public static final String PASTE_API_URL = "http://rushmead.playat.ch/stikket/api/create";
    public static final Server[] SERVERS = new Server[] { new Server("Rushmead", "rushmead.playat.ch", false, true) };
}
