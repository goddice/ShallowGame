import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.ceil;
import static java.lang.Math.round;
import static java.lang.Math.floor;


/**
 * Created by Goddice on 9/6/2016.
 */
public class Player extends Movable{
    private float initPosX;
    private float initPosY;
    private Image panel;
    private ArrayList<Item> inventory;

    Player(String ref, TiledMap map) {
        super(ref, map);
        inventory = new ArrayList<>();
    }

    public void setInitPosX(float initX) {
        this.initPosX = initX;
    }

    public void setInitPosY(float initY) {
        this.initPosY = initY;
    }

    public void loadPanel(String ref)
    {
        try {
            panel = new Image(ref);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setFullHealth() {
        this.health = this.maxHealth;
    }

    public void pickItem(Item item) {
        inventory.add(item);
        if (item.getName().equals("Amulet of Vitality"))
        {
            this.maxHealth += 80;
            this.health += 80;
        }
        if (item.getName().equals("Sword of Strength"))
        {
            this.damage += 30;
        }
        if (item.getName().equals("Tome of Agility"))
        {
            this.cooldown -= 300;
        }
    }

    public boolean hasItem(String itemName)
    {
        for (Item item : inventory)
        {
            if (item.getName().equals(itemName))
            {
                return true;
            }
        }
        return false;
    }

    public void checkItems(ArrayList<Item> items)
    {
        Item pickedItem = null;
        boolean picked =false;
        for (Item item : items)
        {
            if (this.dist(item) <= 50.0)
            {
                picked = true;
                pickedItem = item;
                break;
            }
        }
        if (picked && (pickedItem != null))
        {
            items.remove(pickedItem);
            this.pickItem(pickedItem);
        }
    }

    public void checkVillagers(ArrayList<Villagers> villagers)
    {
        for (Villagers v : villagers)
        {
            if (this.dist(v) < 50.0)
            {
                v.interaction(this);
                break;
            }
        }
    }

    public void checkMonsters(ArrayList<Monster> monsters)
    {
        for (Monster monster : monsters)
        {
            if (this.dist(monster) <= 50)
            {
                this.attack(monster);
            }
        }
    }

    public void updatePos(double dir_x, double dir_y, int delta) {
        this.timeSinceLastHit += delta;
        move(dir_x * delta, dir_y * delta);
    }

    public void reborn() {
        this.setPos(initPosX, initPosY);
        this.setFullHealth();
        this.isDeath = false;
    }

    public void renderPanel(Graphics g) {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float health_percent;       // Player's health, as a percentage

        // Panel background image
        panel.draw(0, RPG.screenheight - RPG.panelheight);

        // Display the player's health
        text_x = 15;
        text_y = RPG.screenheight - RPG.panelheight + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = Integer.toString(this.getHealth()) + "/" + Integer.toString(this.getMaxHealth());                                 // TODO: HP / Max-HP
        //text = Float.toString(this.getX()) + "/" + Float.toString(this.getY());                                 // TODO: HP / Max-HP

        bar_x = 90;
        bar_y = RPG.screenheight - RPG.panelheight + 20;
        bar_width = 90;
        bar_height = 30;
        health_percent = this.getHealth() * 1.0f / this.getMaxHealth();                         // TODO: HP / Max-HP
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = Integer.toString(this.getDamage());                                    // TODO: Damage
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = Integer.toString(this.getCooldown());                                    // TODO: Cooldown
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, text_y);
        bar_x = 490;
        bar_y = RPG.screenheight - RPG.panelheight + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490;
        inv_y = RPG.screenheight - RPG.panelheight
                + ((RPG.panelheight - 72) / 2);
        for (Item item : inventory)                // TODO
        {
            // Render the item to (inv_x, inv_y)
            item.getImage().draw(inv_x, inv_y);
            inv_x += 72;
        }
    }

    @Override
    public void draw(Graphics g)
    {
        super.draw(g);
        renderPanel(g);
    }
}
