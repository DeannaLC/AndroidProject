**Level**: User goal

**Primary Actor**: Players

**Stakeholders and Interests**
- Players want to be given information about what's happening in the game
  - Cowboys want information that helps them figure out who the bandits are
  - Bandits want information that helps them blend in with the cowboys

**Preconditions**
- Player has taken an action earlier
- Player does not share the screen with others
- Player passes the phone after they view their information

**Postconditions**
- Players are given information from the location they're at that helps them get to their goal

```plantuml

@startuml
title View Observations

skin rose
'define swimlanes
|#technology|Players|
|#implementation|System|

|System|
start
while (More players?) is (yes)
:Display list of player names;
|Players|
:Select name;
|System|
if (Bandit or Cowboy?) is (Bandit) then
:Execute __Bandit Observation__; 
else (Cowboy)
:Execute __Cowboy Observation__;
endif
|Players|
:Confirm viewing;
|System|
:Cross out name that was selected;
|Players|
:Pass phone;
endwhile (no)
|System|
stop

```

```plantuml

@startuml
title Bandit Observation

skin rose
'define swimlanes
|#technology|Players|
|#implementation|System|

|System|
start
if (Robbed earlier?) is (yes) then
:Show no observation at location;
else (no)
:Show number of players or name of player randomly;
endif
|Players|
:Confirm viewing;
stop

```

```plantuml

@startuml
title Cowboy Observation

skin rose
'define swimlanes
|#technology|Players|
|#implementation|System|

|System|
start
:Display player or names choice;
|Players|
if (Players or Name?) is (Players) then
|System|
:Show number of players at location;
else (Name)
:Show a name of another player at location;
endif
|Players|
:Confirm viewing;
stop

```

**Usability**
- Text should be well visible
- No ability to view observations again after they're seen

**Performance**
- Displays information almost immediately

**Supportability**
- Ability to change or add more things a player can see