package openlauncher.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceLoader {
    private String slashResourcePath;

    public ResourceLoader(String... resourcesPath) {
        slashResourcePath = "";

        for (String pathToken : resourcesPath) {
            slashResourcePath += "/" + pathToken;
        }
    }

    public ImageIcon getIcon(String iconName) {
        return new ImageIcon(ResourceLoader.class.getResource(getResourcePath("/" + iconName)));
    }

    public BufferedImage getImage(String imageName) {
        try {
            return ImageIO.read(ResourceLoader.class.getResourceAsStream(getResourcePath("/" + imageName)));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private String getResourcePath(String resource) {
        return slashResourcePath + resource;
    }
}
