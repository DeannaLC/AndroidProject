```plantuml
@startuml




title Configure Game
hide footbox
skin rose

actor Gamer as gamer
participant "c : Controller" as control
participant "read : Scanner" as UI
participant ":PlayersClass" as players
participant ":Player" as player

control -> gamer: Ask for game parameters
gamer -> UI: Input parameters
UI -> control: Set parameters

loop More Players
control -> gamer: Ask for player name
gamer -> UI: Input player name
UI -> control: Give name
control -> players: Create players()
players -> player: Player(name)
players -> player: AssignRole()
player -> players: addPlayer()
players -> UI: Give player data
UI -> gamer: Display role
else No More Players
control -> gamer: Show configurations
end
```

```plantuml
@startuml

title Take Action
hide footbox
skin rose

actor Gamer as gamer
participant "c : Controller" as control
participant "read : Scanner" as UI
participant ":PlayerList" as players
participant ":Player" as player
participant "l :Location" as loc


loop More Players
control -> gamer: Give list of names
gamer -> UI: Select name
UI -> control : Give name
control -> players: inPlay = listCopy.findPlayer(name)
control -> player: inPlay.role()
player -->> control: role
alt if cowboy
control -> gamer: Ask for location to watch
gamer -> UI: Input location
UI -> control: Give location
control -> player: observe the location
player -> loc: Take action at location
players -> control: Remove player from list
else if bandit
control -> gamer: Ask for location to watch
gamer -> UI: Input location
UI -> control: Give location
control -> gamer: Ask for action
gamer -> UI: Input action
UI -> control: give inputted action
player -> loc: Take action at location
players -> control: Remove player from list
loc -> control: Give action data if money involved

else No More Players
control -> UI: Switch game phase
UI -> gamer: Show phase switch
end
```

```plantuml
@startuml

title Check Observations
hide footbox
skin rose

actor Gamer as gamer
participant "c : Controller" as control
participant "read : Scanner" as UI
participant ":PlayerList" as players
participant ":Player" as player
participant ":Locations" as loc

loop More Players
control -> gamer: Ask for name
gamer -> UI: Input name
UI -> control: Give inputted name
control -> players: findPlayer(name)
alt if Cowboy
control -> gamer: Ask for players or name
gamer -> UI: give input
UI -> control: relay input
control -> gamer: display resulting info
else if Bandit
control -> gamer: display info
end
```

```plantuml


@startuml


title Design Class Diagram
hide empty methods


skin rose
' classes


class Controller{
curDay : int
dayLim : int
playerCount : int
money : int
moneyLim : int
inGame : boolean
--
+draw(bandits : int, amt : int)
+addPlayer(name : String, bands : ArrayList, cur : int)
checkWin(p : PlayerList)
addMoney(money : int)
}

class TextUI{
--
+configurePlayers(c : Controller)
+actions(c : Controller)
+observations(c : Controller, robList : ArrayList<Bandit>)
+main{static}(args : String[])
}

class Player{
alive : boolean
name : String
loc : String
votes : int
--
+getName()
+observe(l : Location, loc : String)
+role()
+vote(votes : int)
+clearVotes()
}

class Cowboy{
--
+observation(l : Location, a : int)
+role()
}

class Bandit{
+robbed : boolean
--
+observation(l : Location)
+role()
+rob(l : Location, place : String)
}

class PlayerList{
players : ArrayList<Player> 
bandits : ArrayList<Bandit>
cowboys : ArrayList<Cowboy>
--
+addPlayer(name : String, bands : ArrayList, cur : int)
+copy()
+toString()
+findPlayer(person : String)
}

class Location{
bank : ArrayList<Player>
saloon : ArrayList<Player>
ranch : ArrayList<Player>
--
+clearLocs()
+randPlayer(name : String, place : String)
+getValue(place : String)
}

Player -> "Contained in" PlayerList: \t\t
Player -> "Contained in" Location: \t\t
Player <|-- Bandit
Player <|-- Cowboy
@enduml
```