/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Your name> <Your login>
 */

import org.newdawn.slick.SlickException;

import java.util.logging.Level;
import java.util.logging.Logger;

/** Represents the camera that controls our viewpoint.
 */
public class Camera
{

    /** The unit this camera is following */
    private Player unitFollow;

    /** The camera's position in the world, in x and y coordinates. */
    private int xPos;
    private int yPos;

    
    public int getxPos() {
        // TO DO: Fill In
        return this.xPos;
    }

    public int getyPos() {
        // TO DO: Fill In
        return this.yPos;
    }

    
    /** Create a new Camera object. */
    public Camera(Player player)
    {   
        // TO DO: Fill In
        this.unitFollow = player;
        try {
            this.update();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** Update the game camera to recentre it's viewpoint around the player 
     */
    public void update()
    throws SlickException
    {
        // TO DO: Fill In
        this.xPos = (int)this.unitFollow.getX();
        this.yPos = (int)this.unitFollow.getY();
    }
    
    /** Returns the minimum x value on screen 
     */
    public int getMinX(){
        // TO DO: Fill In
        return this.xPos - RPG.screenwidth / 2;
    }
    
    /** Returns the maximum x value on screen 
     */
    public int getMaxX(){
        // TO DO: Fill In
        return this.xPos + RPG.screenwidth / 2;
    }
    
    /** Returns the minimum y value on screen 
     */
    public int getMinY(){
        // TO DO: Fill In
        return this.yPos - (RPG.screenheight - RPG.panelheight) / 2;
    }
    
    /** Returns the maximum y value on screen 
     */
    public int getMaxY(){
        // TO DO: Fill In
        return this.yPos + (RPG.screenheight - RPG.panelheight) / 2;
    }

    /** Tells the camera to follow a given unit. 
     */
    public void followUnit(Object unit)
    throws SlickException
    {

        // TO DO: Fill In
    }
    
}