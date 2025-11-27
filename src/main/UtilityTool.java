package main;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class UtilityTool {
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
      if (width <= 0) width = 1;
      if (height <= 0) height = 1;
      if (original == null) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      }

      // Fast path: if requested size equals original size, return original to preserve pixel-perfect look
      if (original.getWidth() == width && original.getHeight() == height) {
        return original;
      }

      int imageType = original.getType();
      if (imageType == BufferedImage.TYPE_CUSTOM || imageType == 0) {
        imageType = BufferedImage.TYPE_INT_ARGB;
      }

      BufferedImage scaledImage = new BufferedImage(width, height, imageType);
      Graphics2D g2 = scaledImage.createGraphics();
      // Use nearest-neighbor interpolation for crisp pixel-art tiles (no blur)
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
      // Prefer speed and exact pixels over smoothing
      g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
      g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
      g2.drawImage(original, 0, 0, width, height, null);
      g2.dispose();

      return scaledImage;
    }
  
}
