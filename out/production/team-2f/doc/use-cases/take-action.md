**Level**: User goal

**Primary Actor**: Players

**Stakeholders and Interests**
- Players want to take an action that gets them closer to their win condition
  - Bandits want to steal money from a place or blend in with cowboys
  - Cowboys want to watch over a place to uncover who the bandits are

**Preconditions**
- Players do not share their actions with others

**Postconditions**
- Players do something that puts them closer to winning the game

``` plantuml
@startuml
title Nighttime Player Actions

skin rose
'define swimlanes
|#technology|Players|
|#implementation|System|

|System|
start
while (More players?) is (yes)
:Display list of names;
|Players|
:Select name;
if (Role?) then (Cowboy)
:Execute __Cowboy Action__;
else (Bandit)
:Execute __Bandit Action__;
endif
|System|
:Cross out selected name;
|Players|
:Pass phone;
endwhile (no)
|System|
stop
@enduml

```

```plantuml
@startuml
title Cowboy Action (Subfunction)

skin rose
'define swimlanes
|#technology|Players|
|#implementation|System|

|System|
start
:Display locations;
|Players|
:Select a location;
|System|
:Display confirmation;
stop
@enduml
```

```plantuml
@startuml
title Bandit Action (Subfunction)

skin rose
'define swimlanes
|#technology|Players|
|#implementation|System|

|System|
start
:Display other bandits;
:Display Steal or Watch;
|Players|
:Select an option;
|System|
:Display locations;
|Players|
:Select a location;
|System|
:Display confirmation;
stop
@enduml
```

**Non-functional Requirements**
Usability
- Buttons are well visible and easy to understand
- Confirmation that an action has been taken
- Game disallows more than one action per player

Performance
- Game gives confirmation almost instantly that an action has been done

Supportability
- Flexbility to add more actions in the future


