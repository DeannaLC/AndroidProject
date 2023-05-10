The current prototype is able to add players into the game, randomly assign them roles, 
and configure game options. Then, it allows players 
to take actions depending on their role, where Cowboys can watch over a location and bandits can choose to either rob or watch a location.
The observations allow for players to see results based on the location they chose, where Cowboys can choose to see number of players or a specific player who visited their location, bandits who watched get to see one of those choices randomly, and bandits who robbed stay at their location. 
There is also a win check if money gained in the one round exceeds the money limit set. 

The program is able to handle any inputs, and if a wrong input is given when prompted for a certain type, it will ask the user again. 

The Main method is contained in the TextUI class and doesn't require arguments. All inputs are handled by a Scanner.

========================================================

**ITERATION 2**

The Android prototype has the same functionality as the Java prototype. It's able to set up options at the start,
add players to the game, randomly assign them roles, allow them to take actions, and view
the results of their actions. There's a win check at the end that will display a message if the bandits get more money than the limit originally set.

Inputs are handled through Android input field declarations. Empty fields or invalid inputs are handled and asks the player to change their selection.

Testing was not done for handling a large number of players. Not sure what it would do in terms of screen display. 
Unit Tests also only pick one set of actions and observations to avoid complications. 

Full system tests can be run using SelectObservationEndScreenTest

========================================================

**CONSTRUCTION**

Final Android app is capable of running full games. Voting for player removals and a start screen with rules and leaderboard have been added since the last iteration.
It has full working dynamic state and data persistence uses a leaderboard showing previous wins.

While the game runs, not much work was done in terms of balancing. It's currently assumed that settings will be put in that won't immediately end the game or make it unreasonably long. There also wasn't a ton done in terms of how the UI looks. 

Espresso tests go through one full win case. Running testResults() in ResultScreenTest goes through every test at once.

Otherwise, the program appears bug free, does what it's supposed to when things are clicked, and displays data correctly.

**CREDIT**

Background image taken from: https://www.pxfuel.com/en/desktop-wallpaper-ztful