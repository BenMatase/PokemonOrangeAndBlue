=======================
Pokémon Orange and Blue
=======================

Created by The Elite Four:
            Jason Corriveau, Eric Marshall, Ben Matase, Alexander Murph

Started: April, 2016

Readme.txt last updated: 5/2/16

===========
Description
===========

        The final project for Software Engineering and Design (CSCI205).  Our take
    on Pokemon set at Bucknell University.  We aimed to mimic the look and feel
    of a real Pokemon game, particularly the 3rd generation games (Ruby, Sapphire,
    Emerald, Fire Red, and Leaf Green).  We put guest appearances by 
    Bucknell University professors as gym leaders with their permission.  As of
    updating this readme, we have gotten the battling in working order.  The
    user can choose a team of Pokemon from the first four generations with any
    moves that the Pokemon could learn in the game.  The player can choose to
    battle either a random trainer or the next professor or Elite Four member.
    The Elite Four consists of the four creators of the game. The game right now
    focuses on the battle sequence and has a lot of the functionality that the
    real games have.  Only physical moves are included and only the damage of
    the moves counts (no extra functions of all moves are accounted for).
        The player can choose their team in the beginning of the game after
    getting past the splash screen.  The user can click on any of the 6 buttons
    and a panel will appear where there is a drop down list of all choosable
    Pokemon.  Once a Pokmeon is chosen, the moves part appears and the user
    can choose up to four moves for that Pokemon.  If everything is valid and
    the user clicks the done button, that Pokemon is populated into the button
    that the user clicked originally.  The user can also click the random
    button to randomly populate all 6 Pokemon slots.  The user can click done
    when they are finished editing their Pokemon team.
        At the menu screen, the user can choose to battle a random trainer who
    has 6 random Pokemon with 4 random moves, the next professor/Elite Four
    member, or to reset their game and choose new Pokemon.  The professors are
    given an order from first to eighth.  You can only advance to the next
    professor by beating the current one.  When you beat Professor King, you
    start to battle the Elite Four, which is the four creators of this game.
    Created using the Scrum methodology.
        A battle in Pokemon happens as such.  There are two Pokemon trainers in
    every battle.  There is one trainer who is the player of the game and one
    trainer who is the NPC (Non-Player Character). Each player has their image
    show on the screen and then the NPC says some dialog to the player.  They 
    both send out their first Pokemon.  The trainers simultaneously choose a 
    move for their Pokemon to use or whether they will switch out their current
    Pokemon for one in their bag.  Once both have chosen, calculations based on
    each Pokemon's speed occur to determine which Pokemon will attack first.
    Then the faster Pokemon performs their attack and the defending Pokemon loses
    HP (Hit Points) according to an equation based on many things including
    attacking Pokemon's attack, defending Pokemon's defense, Pokemon types,
    attacking Pokemon's move's type and other factors.  If the opposing Pokemon
    faints (has no HP left), then that is the end of the battle sequence and the
    trainer whose Pokemon fainted chooses a new one to switch out.  If the 
    defending Pokemon does not faint, then it loses the appropriate HP and
    performs its move.  If the then defending Pokemon faints, then the
    trainer chooses which Pokemon to send out.  This cycle of battle sequences
    happen until one trainer has no Pokemon left to send out and the trainer
    with Pokemon left is considered the winner.

========
Controls
========
    Menus
        Keys
            - Arrow Keys to navigate between buttons in the menus
            - Space/Enter to select the currently highlighted button and have it operate its function
            - Backspace will exit out of a menu if the operation is allowed
        Mouse
            - Click to select a button


=========
Compiling
=========
    - Download and install Java JDK 7 or newer
    - Download and install Netbeans (https://netbeans.org/)
    - Download Jarsplice (http://ninjacave.com/jarsplice)
    - Clone the repository in the directory of your choice
    - In Netbeans, select 'Run > Clean and Build Project'
    - Open Jarsplice
        - Select 'ADD JARS' and add the following Jar files (Paths are relative to the Project folder):
            - dist/csci205FinalProject.jar
            - dist/lib/commons-lang3-3.4.jar
            - dist/lib/ibxm.jar
            - dist/lib/jdom-2.0.6.jar
            - dist/lib/jinput.jar
            - dist/lib/jogg-0.0.7.jar
            - dist/lib/jorbis-0.0.15.jar
            - dist/lib/lwjgl.jar
            - dist/lib/slick.jar
        - Select 'ADD NATIVES' and add all of the files found at:
            - externals/Slick/native/
        - Select 'MAIN CLASS'
            - Under 'Enter Main Class' enter:
                - 'main.Main'
            - Select 'Show Options' and under 'Set VM Arguments' enter:
                - '-Djava.library.path="./externals/Slick/native"'
        - Select 'CREATE FAT JAR' and select the 'Create Fat Jar' button
        - Choose where you would like to save the game


=======
Running
=======
    - Graphical File System (Finder/Nautilus/Windows Explorer)
        1. Navigate to the folder containing 'Pokemon Orange and Blue.jar'
        2. Double click on 'Pokemon Orange and Blue.jar'
    - Command Line
        1. Navigate to the folder containing 'Pokemon Orange and Blue.jar'
        2. Run 'java -jar Pokemon\ Orange\ and\ Blue.jar'


========
Features
========
- [x] Splash screen
- [x] Pokémon and trainer objects
- [x] Battle calculator and interactions
- [x] Load Pokémon data from XML
- [x] Battle GUI
- [x] Simple Animations
- [x] Sound & Music
- [x] Main Menu
- [ ] Overworld GUI
- [ ] Overworld interactions


=======
Credits
=======
Special thanks to all of the Bucknell University professors that let us use their names.

This project uses the Slick2D Engine (http://slick.ninjacave.com/), a wrapper 
around the Lightweight Java Game Library (https://www.lwjgl.org/).

For parsing the XML files, we used the jDom library.

All Pokemon sprites are from http://veekun.com/dex/downloads.

The Pokémon information database can be found at:
https://github.com/r4vi/zipper-demo/blob/master/resources/pokemon.xml

The Pokémon moves database can be found at:
https://github.com/cathyjf/PokemonLabBot/blob/master/moves.xml 

==========
Disclaimer
==========
This game does not represent the views of Bucknell University or Nintendo Co., Ltd. 

Pokémon and its trademarks are ©1995-2016 Nintendo, Creatures, GAMEFREAK and the The Pokémon Company International, Inc. All images in this game are © of their respective owners. Pokemon Orange and Blue is a non-commercial Pokémon® fan game, and means no copyright infringement.

