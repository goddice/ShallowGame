/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Your name> <Your login>
 */

import org.lwjgl.Sys;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
    private TiledMap maze;

    Player player;
    ArrayList<Monster> monsters;
    ArrayList<Item> items;
    ArrayList<Villagers> villagers;

    Camera camera;

    /** Create a new World object. */
    public World(int screenwidth, int screenheight)
    throws SlickException
    {
        // TODO: Fill in
        maze = new TiledMap("assets/map.tmx", "assets/");

        // calculate the blocked matrix

        boolean [][] blocked = new boolean[maze.getWidth()][maze.getHeight()];
        for (int i=0; i<maze.getWidth(); i++)
        {
            for (int j=0; j<maze.getHeight(); j++)
            {
                int tileID = maze.getTileId(i, j, 0);
                String value = maze.getTileProperty(tileID, "block", "0");
                if (value.equals("1"))
                {
                    blocked[i][j] = true;
                }
            }
        }
        //
        // load units information
        // The main player
        player = new Player("assets/units/player.png", maze);
        player.loadPanel("assets/panel.png");
        player.setBlocked(blocked);

        // npc
        villagers = new ArrayList<>();

        Villagers princeAldric = new Villagers("assets/units/prince.png", maze);
        princeAldric.setName("Aldric");
        Villagers elvira = new Villagers("assets/units/shaman.png", maze);
        elvira.setName("Elvira");
        Villagers garth = new Villagers("assets/units/peasant.png", maze);
        garth.setName("Garth");

        // monster
        monsters = new ArrayList<>();

        //items
        items = new ArrayList<>();
        Item amulet = new Item("assets/items/amulet.png", maze);
        amulet.setPos(965, 3563);
        amulet.setName("Amulet of Vitality");

        Item sword = new Item("assets/items/sword.png", maze);
        sword.setPos(546, 6707);
        sword.setName("Sword of Strength");

        Item tome = new Item("assets/items/tome.png", maze);
        tome.setPos(4791, 1253);
        tome.setName("Tome of Agility");

        Item elixir = new Item("assets/items/elixir.png", maze);
        elixir.setPos(1976, 402);
        elixir.setName("Elixir of Life");

        items.add(amulet);
        items.add(sword);
        items.add(tome);
        items.add(elixir);

        // read units.dat
        try(BufferedReader br = new BufferedReader(new FileReader("data/units.dat"))) {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if (parts[0].equals("Player"))
                {
                    player.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    player.setInitPosX(Float.parseFloat(parts[1]));
                    player.setInitPosY(Float.parseFloat(parts[2]));
                    player.setDamage(26);
                    player.setCooldown(600);
                    player.setMaxHealth(100);
                    player.setFullHealth();
                }
                if (parts[0].equals("PrinceAldric"))
                {
                    princeAldric.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    princeAldric.setMaxHealth(1);
                    princeAldric.setFullHealth();
                    villagers.add(princeAldric);
                }
                if (parts[0].equals("Elvira"))
                {
                    elvira.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    elvira.setMaxHealth(1);
                    elvira.setFullHealth();
                    villagers.add(elvira);
                }
                if (parts[0].equals("Garth"))
                {
                    garth.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    garth.setMaxHealth(1);
                    garth.setFullHealth();
                    villagers.add(garth);
                }
                if (parts[0].equals("GiantBat"))
                {
                    Monster giantBat = new PassiveMonster("assets/units/dreadbat.png", maze);
                    giantBat.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    giantBat.setDamage(0);
                    giantBat.setCooldown(0);
                    giantBat.setMaxHealth(40);
                    giantBat.setFullHealth();
                    giantBat.setName("GiantBat");
                    monsters.add(giantBat);
                }
                if (parts[0].equals("Zombie"))
                {
                    Monster zombie = new AggressiveMonster("assets/units/zombie.png", maze);
                    zombie.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    zombie.setDamage(10);
                    zombie.setCooldown(800);
                    zombie.setMaxHealth(60);
                    zombie.setFullHealth();
                    zombie.setName("Zombie");
                    monsters.add(zombie);
                }
                if (parts[0].equals("Bandit"))
                {
                    Monster bandit = new AggressiveMonster("assets/units/bandit.png", maze);
                    bandit.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    bandit.setDamage(8);
                    bandit.setCooldown(200);
                    bandit.setMaxHealth(40);
                    bandit.setFullHealth();
                    bandit.setName("Bandit");
                    monsters.add(bandit);
                }
                if (parts[0].equals("Skeleton"))
                {
                    Monster skeleton = new AggressiveMonster("assets/units/skeleton.png", maze);
                    skeleton.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    skeleton.setDamage(16);
                    skeleton.setCooldown(500);
                    skeleton.setMaxHealth(100);
                    skeleton.setFullHealth();
                    skeleton.setName("Skeleton");
                    monsters.add(skeleton);
                }
                if (parts[0].equals("Draelic"))
                {
                    Monster draelic = new AggressiveMonster("assets/units/necromancer.png", maze);
                    draelic.setPos(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                    draelic.setDamage(30);
                    draelic.setCooldown(400);
                    draelic.setMaxHealth(140);
                    draelic.setFullHealth();
                    draelic.setName("Draelic");
                    monsters.add(draelic);
                }
            }
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        //

        camera = new Camera(player);

        //
        player.setCamera(camera);
        princeAldric.setCamera(camera);
        elvira.setCamera(camera);
        garth.setCamera(camera);
        for (Monster monster : monsters)
        {
            monster.setCamera(camera);
            monster.setBlocked(blocked);
        }
        for (Item item : items)
        {
            item.setCamera(camera);
        }
    }

    public void checkingDeath()
    {
        if (player.death())
        {
            player.reborn();
        }
        for (Iterator<Monster> it = monsters.iterator(); it.hasNext(); ) {
            if (it.next().death())
            {
                it.remove();
            }
        }

    }
    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(double dir_x, double dir_y, int delta, char keyPressed)
    throws SlickException
    {
        // TODO: Fill in
        player.updatePos(dir_x, dir_y, delta);
        player.checkItems(items);
        if (keyPressed == 'T')
        {
            player.checkVillagers(villagers);
        }
        if (keyPressed == 'A')
        {
            player.checkMonsters(monsters);
        }
        for (Villagers v : villagers)
        {
            v.update(delta);
        }
        for (Monster monster : monsters)
        {
            monster.update(player, delta);
        }

        checkingDeath();
        camera.update();
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */

    private void renderUnits(Graphics g)
    {
        for (Villagers v : villagers)
        {
            v.draw(g);
        }
        for (Monster monster : monsters)
        {
            monster.draw(g);
        }
        for (Item item : items)
        {
            item.draw(g);
        }
        player.draw(g);
    }

    public void render(Graphics g)
    throws SlickException
    {
        // TODO: Fill in
        int tileWidth = maze.getTileWidth();
        int tileHeight = maze.getTileHeight();

        //g.translate(-camera.getMinX(), -camera.getMinY());
        //finally draw the section of the map on the screen
        maze.render(-camera.getMinX() % tileWidth, -camera.getMinY() % tileHeight, camera.getMinX() / tileWidth,  camera.getMinY() / tileHeight, 13, 10);
        renderUnits(g);
    }
}
