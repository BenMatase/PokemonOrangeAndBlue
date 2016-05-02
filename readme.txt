Pokémon Orange and Blue

Created by The Elite Four:
            Jason Corriveau, Eric Marshall, Ben Matase, Alexander Murph

Started: April, 2016

Readme.txt last updated: 5/1/16

Description

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

Controls

    All Menus and Battle
        Keys
            - Arrow Keys to navigate between buttons in the menus
            - Space/Enter to activate the button and have it operate its function
        Mouse
            - Click on the buttons to activate the button

Instructions to Run
    1. WTF

Features
- [x] Splash screen
- [x] Pokémon and trainer objects
- [x] Battle calculator and interactions
- [x] Load Pokémon data from XML
- [x] Battle GUI
- [ ] Overworld GUI
- [x] Simple Animations
- [x] Sound & Music
- [ ] Overworld interactions
- [x] Main Menu


Credits

Thanks to all of the Bucknell University professors that let us use their names
and personas.

This project uses the Slick2D Engine (http://slick.ninjacave.com/), a wrapper 
around the Lightweight Java Game Library (https://www.lwjgl.org/).

For parsing the XML files, we used the jDom library.

We got all Pokemon sprites from http://veekun.com/dex/downloads.

We got the pokemon information data base from:
https://github.com/r4vi/zipper-demo/blob/master/resources/pokemon.xml

We got the moves information data base from:
https://github.com/cathyjf/PokemonLabBot/blob/master/moves.xml


Warning:
We make no claims to creating any of the Pokemon related content such as the
sprites, information about each Pokemon, or about each move.
This game also doesn't necessarily represent the views of Bucknell University or
Nintendo. 
