import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by Goddice on 9/20/2016.
 */
public class Villagers extends Role {
    private long talkingTime;
    private String sentance;

    Villagers(String ref, TiledMap map) {
        super(ref, map);
        talkingTime = 5000;
        sentance = "";
    }

    public void interaction(Player player) {

        if (this.getName().equals("Aldric"))
        {
            if (player.hasItem("Elixir of Life"))
            {
                sentance = "The elixir! My father is cured! Thank you!";
            }
            else
            {
                sentance = "Please seek out the Elixir of Life to cure the king.";
            }
        }

        if (this.getName().equals("Elvira"))
        {
            if (player.isFullHealth())
            {
                sentance = "Return to me if you ever need healing.";
            }
            else
            {
                player.setFullHealth();
                sentance = "You're looking much healthier now.";
            }
        }

        if (this.getName().equals("Garth"))
        {
            if (!(player.hasItem("Amulet of Vitality")))
            {
                sentance = "Find the Amulet of Vitality, across the river to the west.";
            }
            else if (!(player.hasItem("Sword of Strength")))
            {
                sentance = "Find the Sword of Strength - cross the bridge to the east, then head south.";
            }
            else if(!(player.hasItem("Tome of Agility")))
            {
                sentance = "Find the Tome of Agility, in the Land of Shadows.";
            }
            else
            {
                sentance = "You have found all the treasure I know of.";
            }
        }
        talkingTime = 0;
    }

    public void update(int delta)
    {
        talkingTime += delta;
    }

    private void renderSentance(Graphics g)
    {
        float x = this.posX - camera.getMinX() - image.getWidth() / 2;
        float y = this.posY - camera.getMinY() - image.getHeight() / 2;
        String text = sentance;
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw

        bar_width = g.getFont().getWidth(text) + 6;
        bar_height = 30;
        bar_x = (int)(x + image.getWidth() / 2 - bar_width / 2);
        bar_y = (int)(y - 2 * bar_height);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        text_y = bar_y + 5;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        this.renderHealthBar(g);
        if (talkingTime <= 4000)
        {
            this.renderSentance(g);
        }
    }
}
