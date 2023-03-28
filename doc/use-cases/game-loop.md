**Level**: User Goal

**Primary Actor**: Players

**Stakeholders and Interests**:
- Players want to play and win at the game
  - Bandits want to either get enough money or have control over the town
  - Cowboys want to hold out till sheriffs come or remove all the bandits from the town

**Preconditions**
- Players set up the game in a fair way
- Players make choices that help their team to win the game
- Players do not cheat at the game

**Postconditions**
- Game will fully run through
- One team will win

``` plantuml
@startuml
skin rose
title Full Game Loop

start
:Execute __Configure Players__;
while (Met __Win Condition__?) is (no)
:Execute __Take Action__;
:Execute __View Observations__;
:Execute __Discussion and Voting__;
endwhile (yes)
:Display win screen;
stop
```
```plantuml
@startuml
skin rose
title Win Condition

start
if (Enough money stolen?) is (yes) then
:Bandit win condition met;
(no) elseif (More bandits than cowboys?) then (yes)
:Bandit win condition met;
(no) elseif (No bandits alive?) then (yes)
:Cowboy win condition met;
(no) elseif (Cowboys make it past day limit?) then (yes)
:Cowboy win condition met;
else (no)
:Continue game;
endif
stop
@enduml
```

**Usability**
- Game flow is made clear
- Text is easily readable
- Game gives confirmation that players have done something

**Performance**
- Game responds quickly to inputs and screen switches
- Game can end at any time
- Game can be closed and continue running

**Supportability**
- Flexibility to add more ways of winning
- Flexibility to add more game phases