/**
 * Copyright 2013 by ATLauncher and Contributors
 *
 * ATLauncher is licensed under CC BY-NC-ND 3.0 which allows others you to
 * share this software with others as long as you credit us by linking to our
 * website at http://www.atlauncher.com. You also cannot modify the application
 * in any way or make commercial use of this software.
 *
 * Link to license: http://creativecommons.org/licenses/by-nc-nd/3.0/
 */
package com.atlauncher.data;

public class PrivatePack extends Pack {

    private Player[] allowedPlayers;

    public PrivatePack(int id, String name, Player owner, Version[] versions,
            String description, String changelog, Player[] allowedPlayers) {
        super(id, name, owner, versions, description, changelog);
    }

}