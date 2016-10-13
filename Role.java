import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by Goddice on 9/20/2016.
 */
public class Role extends Unit {

    protected int health;
    protected int maxHealth;

    Role(String ref, TiledMap map) {
        super(ref, map);
    }

    public void setMaxHealth(int maxHP) {
        this.maxHealth = maxHP;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setFullHealth()
    {
        this.health = this.maxHealth;
    }

    public boolean isFullHealth() { return (this.health == this.maxHealth); };

    protected void renderHealthBar(Graphics g)
    {
        float x = this.posX - camera.getMinX() - image.getWidth() / 2;
        float y = this.posY - camera.getMinY() - image.getHeight() / 2;

        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        float health_percent;       // Player's health, as a percentage

        text = this.getName();
        bar_width = g.getFont().getWidth(text) + 6;
        bar_height = 30;
        bar_x = (int)(x + image.getWidth() / 2 - bar_width / 2);
        bar_y = (int)(y - bar_height);
        health_percent = this.getHealth() * 1.0f / this.getMaxHealth();
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        text_y = bar_y + 5;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
    }

}
