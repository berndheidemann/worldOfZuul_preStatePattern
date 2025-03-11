package de.szut.zuul;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        this.player= new Player();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room marketsquare, templePyramid, tavern, sacrificialSite, hut, jungle, secretPassage, cave, beach, wizard, cellar;
      
        // create the rooms
        marketsquare = new Room("on the market square");
        templePyramid = new Room("in a temple pyramid");
        tavern = new Room("in the tavern at the market square");
        sacrificialSite = new Room("at a sacrificial site");
        hut = new Room("in a hut");
        jungle = new Room("in the jungle");
        secretPassage = new Room("in a secret passage");
        cave = new Room("in a cave");
        beach = new Room("on the beach");
        wizard = new Room("in the room of the wizard");
        cellar = new Room("in the cellar");

        // initialise room exits
        marketsquare.setExit("north", tavern);
        marketsquare.setExit("east", templePyramid);
        marketsquare.setExit("west", sacrificialSite);
        templePyramid.setExit("north", hut);
        templePyramid.setExit("west", marketsquare);
        templePyramid.setExit("up", wizard);
        templePyramid.setExit("down", cellar);
        tavern.setExit("south", marketsquare);
        tavern.setExit("east", hut);
        sacrificialSite.setExit("east", marketsquare);
        sacrificialSite.setExit("down", cave);
        hut.setExit("south", templePyramid);
        hut.setExit("east", jungle);
        hut.setExit("west", tavern);
        jungle.setExit("west", hut);
        secretPassage.setExit("east", cave);
        secretPassage.setExit("west", cave);
        cave.setExit("south", beach);
        cave.setExit("east", secretPassage);
        cave.setExit("up", sacrificialSite);
        beach.setExit("north", cave);
        wizard.setExit("down", templePyramid);
        cellar.setExit("west", secretPassage);
        cellar.setExit("up", templePyramid);

        //put items in rooms
        marketsquare.putItem(new Item("bow", "a bow made of wood", 0.5));
        cave.putItem(new Item("treasure", "a little treasure chest with coins", 7.5));
        wizard.putItem(new Item("arrows","a lot of arrows in a quiver", 1.0));
        jungle.putItem(new Item("herb", "a medicine herb", 0.5));
        jungle.putItem(new Item("cocoa", "a little cocoa tree", 5));
        sacrificialSite.putItem(new Item("knife", "a gig, sharp knife", 1));
        hut.putItem(new Item("spear", "a spear with slingshot", 5.0));
        tavern.putItem(new Item("food", "a plate of hearty meat and corn porridge", 0.5));
        cellar.putItem(new Item("jewellery", "a very pretty headdress", 1));
        marketsquare.putItem(new Muffin("muffin", "a magic muffin",0.3, 5));

        player.goTo(marketsquare);  // start game on marketsquare
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        printRoomInformation();
    }

    private void printRoomInformation() {
        System.out.println();
        System.out.println(this.player.getCurrentRoom().getLongDescription());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("look")){
            look();
        }
        else if(commandWord.equals("take"))
        {
            takeItem(command);
            System.out.println(player.showStatus());
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
        else if(commandWord.equals("drop"))
        {
            dropItem(command);
            System.out.println(this.player.showStatus());
            System.out.println(this.player.getCurrentRoom().getLongDescription());
        }
        else if(commandWord.equals("eat"))
        {
            eat(command);
            System.out.println(this.player.showStatus());
            System.out.println(this.player.getCurrentRoom().getLongDescription());
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("through the jungle. At once there is a glade. On it there a buildings...");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(this.parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = this.player.getCurrentRoom().getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.goTo(nextRoom);
            printRoomInformation();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void look(){
        System.out.println(this.player.getCurrentRoom().getLongDescription());
    }

    private void takeItem(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Which item do you want to take?");
            return;
        }

        String name = command.getSecondWord();
        Item item = this.player.getCurrentRoom().removeItem(name);
        if(item == null){
            System.out.println("Item doesn't exist here!");
            return;
        }
        if(this.player.takeItem(item)){
            return;
        }
        else{
            System.out.println("Item is too heavy!");
            this.player.getCurrentRoom().putItem(item);
            return;
        }
    }

    private void dropItem(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Wich item do you want to drop?");
            return;
        }

        String name = command.getSecondWord();
        player.getCurrentRoom().putItem(this.player.dropItem(name));
    }

    private void eat(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Wich item do you want to eat?");
            return;
        }

        if(command.getSecondWord().equals("muffin"))
        {
            // Wir versuchen den Muffin zu essen.
            Muffin m = (Muffin) this.player.getCurrentRoom().removeItem("muffin");
            this.player.increaseLoadingCapacity(m.getLoadingCapacity());
            return;
        }
        System.out.println("There is no muffin in this room!");
    }

}
