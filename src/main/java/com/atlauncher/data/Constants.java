package com.atlauncher.data;


public class Constants {
    public static final String launcherName = "OpenLauncher";
    public static final LauncherVersion VERSION = new LauncherVersion(1, 0, 1, 4);
    public static final String API_BASE_URL = "";
    public static final String PASTE_CHECK_URL = "http://rushmead.playat.ch/stikket/";
    public static final String PASTE_API_URL = "http://rushmead.playat.ch/stikket/api/create";
    public static final Server[] SERVERS = { new Server("Auto", "www.creeperrepo.net/OpenLauncher", true, false), new Server("EU - Maidenhead", "england1.creeperrepo.net/OpenLauncher", true, false), new Server("EU - Nottingham", "england2.creeperrepo.net/OpenLauncher", true, false), new Server("EU - Grantham", "england3.creeperrepo.net/OpenLauncher", true, false), new Server("US - Los Angeles", "losangeles1.creeperrepo.net/OpenLauncher", true, false), new Server("US - Atlanta", "atlanta1.creeperrepo.net/OpenLauncher", true, false), new Server("US - Atlanta 2", "atlanta2.creeperrepo.net/OpenLauncher", true, false), new Server("NZ - Auckland", "auckland1.creeperrepo.net/OpenLauncher", true, false)};
}
