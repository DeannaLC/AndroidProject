The program was mainly tested using 3-7 total players and 1-3 bandits.


Randomly assigning roles worked correctly, different indices of players were assigned bandit every time and the proper number were assigned it.

Observing would put a player into a location correctly. Observations gave the proper data when used. 

Testing was done to check for input error handling by putting in inputs that weren't given or types that didn't match the scanner.
As well, there was testing done for checking making the program non-case sensitive. 

There was a brief test for a duplicate player name, which picks the name in queue order. 

**Transcript of a 7 player, 3 bandit game where bandits reach the money limit**
>How many players?\
z\
Not a valid input, try again\
7\
How many ingame days?\
5\
How much money to win?\
3000\
How many bandits?\
3\
Add player's name\
Jack\
Your role is Bandit\
Add player's name\
Joe\
Your role is Bandit\
Add player's name\
Sam\
Your role is Cowboy\
Add player's name\
Jane\
Your role is Cowboy\
Add player's name\
Jeb\
Your role is Bandit\
Add player's name\
Jeff\
Your role is Cowboy\
Add player's name\
John\
Your role is Cowboy\
7 total players, 5 days, 3000 dollars to win\
Players: Jack, Joe, Sam, Jane, Jeb, Jeff, John\
Select your name\
zebra\
Players: Jack, Joe, Sam, Jane, Jeb, Jeff, John\
Select your name\
joe\
Choose a place: Bank, Saloon, Ranch\
field\
Choose a place: Bank, Saloon, Ranch\
bank\
Choose to watch or rob\
no\
Choose to watch or rob\
rob\
You stole 1701$ from bank\
Players: Jack, Sam, Jane, Jeb, Jeff, John\
Select your name\
jack\
Choose a place: Bank, Saloon, Ranch\
saloon\
Choose to watch or rob\
watch\
You chose to hang out at saloon\
Players: Sam, Jane, Jeb, Jeff, John\
Select your name\
sam\
Choose a place to watch: Bank, Saloon, Ranch\
ranch\
You chose to watch the ranch for the night\
Players: Jane, Jeb, Jeff, John\
Select your name\
john\
Choose a place to watch: Bank, Saloon, Ranch\
SALOON\
You chose to watch the saloon for the night\
Players: Jane, Jeb, Jeff\
Select your name\
jane\
Choose a place to watch: Bank, Saloon, Ranch\
RANCH\
You chose to watch the ranch for the night\
Players: Jeb, Jeff\
Select your name\
jeb\
Choose a place: Bank, Saloon, Ranch\
bank\
Choose to watch or rob\
rob\
You stole 1964$ from bank\
Players: Jeff\
Select your name\
jeff\
Choose a place to watch: Bank, Saloon, Ranch\
ranch\
You chose to watch the ranch for the night\
Players: Jack, Joe, Sam, Jane, Jeb, Jeff, John\
Select your name\
sam\
Players or a name?\
a\
Players or a name?\
name\
Jeff is at ranch\
Players: Jack, Joe, Jane, Jeb, Jeff, John\
Select your name\
sam\
Players: Jack, Joe, Jane, Jeb, Jeff, John\
Select your name\
jack\
John at saloon\
Players: Joe, Jane, Jeb, Jeff, John\
Select your name\
joe\
You wait the night at bank\
Players: Jane, Jeb, Jeff, John\
Select your name\
jane\
Players or a name?\
players\
3 at ranch\
Players: Jeb, Jeff, John\
Select your name\
jeb\
You wait the night at bank\
Players: Jeff, John\
Select your name\
jeff\
Players or a name?\
players\
3 at ranch\
Players: John\
Select your name\
john\
Players or a name?\
name\
Jack is at saloon\
Total money: $3665\
Bandits win!\
Process finished with exit code 0

