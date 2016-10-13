import org.newdawn.slick.tiled.TiledMap;

import java.util.Random;

/**
 * Created by Goddice on 9/20/2016.
 */
public class Movable extends Role {
    private boolean blocked[][];
    protected int cooldown;
    protected int damage;
    protected long timeSinceLastFight;
    protected long timeSinceLastHit;
    protected boolean isDeath;

    Movable(String ref, TiledMap map) {
        super(ref, map);
        isDeath = false;
        timeSinceLastHit = 1000000;
        timeSinceLastFight = 100000;
    }

    public int getDamage() { return this.damage; }
    public int getCooldown() { return this.cooldown; }
    public void setBlocked(boolean [][] mBlocked) { this.blocked = mBlocked; }
    public void setCooldown(int mCooldown) { this.cooldown = mCooldown; }
    public void setDamage(int mDamage) { this.damage = mDamage; }

    public void move(double dx, double dy)
    {
        double futureX = this.posX + dx;
        double futureY = this.posY + dy;
        boolean isBlocked = blocked[(int)((futureX + image.getWidth() / 2 - 5) / maze.getTileWidth())][(int)((futureY + image.getHeight() / 2 - 5) / maze.getTileHeight())] ||
                blocked[(int)((futureX + image.getWidth() / 2 - 5) / maze.getTileWidth())][(int)((futureY - image.getHeight() / 2 + 5) / maze.getTileHeight())] ||
                blocked[(int)((futureX - image.getWidth() / 2 + 5) / maze.getTileWidth())][(int)((futureY + image.getHeight() / 2 - 5) / maze.getTileHeight())] ||
                blocked[(int)((futureX - image.getWidth() / 2 + 5) / maze.getTileWidth())][(int)((futureY - image.getHeight() / 2 + 5) / maze.getTileHeight())];

        boolean xBlocked =  blocked[(int)((futureX + image.getWidth() / 2 - 5) / maze.getTileWidth())][(int)((this.posY + image.getHeight() / 2 - 5) / maze.getTileHeight())] ||
                blocked[(int)((futureX + image.getWidth() / 2 - 5) / maze.getTileWidth())][(int)((this.posY - image.getHeight() / 2 + 5) / maze.getTileHeight())] ||
                blocked[(int)((futureX - image.getWidth() / 2 + 5) / maze.getTileWidth())][(int)((this.posY + image.getHeight() / 2 - 5) / maze.getTileHeight())] ||
                blocked[(int)((futureX - image.getWidth() / 2 + 5) / maze.getTileWidth())][(int)((this.posY - image.getHeight() / 2 + 5) / maze.getTileHeight())];

        boolean yBlocked =  blocked[(int)((this.posX + image.getWidth() / 2 - 5) / maze.getTileWidth())][(int)((futureY + image.getHeight() / 2 - 5) / maze.getTileHeight())] ||
                blocked[(int)((this.posX + image.getWidth() / 2 - 5) / maze.getTileWidth())][(int)((futureY - image.getHeight() / 2 + 5) / maze.getTileHeight())] ||
                blocked[(int)((this.posX - image.getWidth() / 2 + 5) / maze.getTileWidth())][(int)((futureY + image.getHeight() / 2 - 5) / maze.getTileHeight())] ||
                blocked[(int)((this.posX - image.getWidth() / 2 + 5) / maze.getTileWidth())][(int)((futureY - image.getHeight() / 2 + 5) / maze.getTileHeight())];
        if (!isBlocked)
        {
            setPos((float)futureX, (float)futureY);
        }
        else
        {
            if (!xBlocked)
            {
                setPos((float)futureX, this.posY);
            }
            if (!yBlocked)
            {
                setPos(this.posX, (float)futureY);
            }
        }
    }

    public int attackingDamage() {
        return (new Random()).nextInt(this.getDamage() + 1);
    }

    public void attack(Movable other) {
        timeSinceLastFight = 0;
        if (timeSinceLastHit >= cooldown)
        {
            int dmg = attackingDamage();
            other.takeDamage(dmg);
            timeSinceLastHit = 0;
        }
    }

    public boolean death()
    {
        return this.isDeath;
    }

    public void takeDamage(int damage) {
        this.timeSinceLastFight = 0;
        health -= damage;
        if (health <= 0)
        {
            isDeath = true;
        }
    }
}
