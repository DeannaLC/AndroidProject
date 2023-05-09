```plantuml
@startuml




title Configure Game
hide footbox
skin rose

actor Gamer as gamer
participant " : UI" as UI
participant "m : MainActivity" as main
participant " : PlayerList" as players
participant " : Player" as player

UI -> gamer: Ask for game parameters
gamer -> UI: Input parameters
UI -> main: onSetOptions(total, bandits, dayLim, moneyLim)
UI -> main: draw()
UI -> gamer: Show configurations

loop More Players
UI -> gamer: Ask for player name
gamer -> UI: Input player name
UI -> main : onAddedPlayer(name)
alt if cowboy
main -> players : addCowboy(name)
players -> player : new Cowboy(name)
else if bandit
main -> players : addBandit(name)
players -> player : new Bandit(name)
end
main -> UI : showNames(players)
UI -> gamer: Display role
UI -> gamer : Show list of added players
else No More Players
main -> UI : onOptionsSet()
UI -> gamer : go to next screen
end
```

```plantuml
@startuml

title Take Action
hide footbox
skin rose

actor Gamer as gamer
participant " : UI" as UI
participant "m : MainActivity" as main
participant ":Player" as player
participant "l :Location" as loc


loop More Players
UI -> gamer: Display list of names
gamer -> UI: Select name
UI -> main : playerSelected(name)
alt chose to observe
main -> player : observeAt(place, player)
player -> loc : observe(l, loc)
else chose to rob
main -> player : stealFrom(place)
player -> loc : rob(l, place)
end
main -> UI : onActionDone()
else No More Players
main -> UI: onActionDone()
UI -> gamer: Change screen
end
```

```plantuml
@startuml

title Check Observations
hide footbox
skin rose

actor Gamer as gamer
participant " : UI" as UI
participant "m : MainActivity" as main
participant ":Player" as player
participant ":Locations" as loc

loop More Players
UI -> gamer: Display list of names
gamer -> UI: Select name
UI -> main : playerSelected(name)
main -> player : showObservation(number)
player -> loc : observation(loc, a)
main -> UI : onActionDone()
else No More Players
main -> UI : onActionDone()
UI -> gamer : Display results screen
end
```

```plantuml
@startuml

title Vote
hide footbox
skin rose

actor Gamer as gamer
participant " : UI" as UI
participant "m : MainActivity" as main
participant ":Player" as player

loop Voting
UI -> gamer: Display list of names

```

```plantuml
@startuml
title Controller
skin rose

hide empty methods

class MainActivity{
curDay : int
dayLim : int
gamePhase: int
curMoney : int
moneyLim : int
playerCount : int
banditCount : int
playersList : PlayerList
banditVals : List
current : Player
canAct : PlayerList
testMode : boolean
loc : Location
leaderboard : Leaderboard
persistenceFacade : IPersistenceFacade
mainView : IMainView
--
+onCreate(savedInstanceState : Bundle)
+onBegin()
+onLeaderboardCheck()
+sharePlayers(b : Bundle)
+onSaveInstanceState(outState : Bundle)
+getPlayerListCopy() : PlayerList
+getMoney() : int
+checkPlayerCap() : boolean
+onPlayersSet
+findPlayer(name : String) : Player
+draw()
+onSetOptions()
+onOptionsSet()
+onAddedPlayer()
+showRole() : String
+setCurrentPlayer(current : Player)
+toString() : String
+getPlayers() : PlayerList
+playerSelected(name : String)
+checkPhase() : int
+onActionDone()
+observeAt(place : String, player : Player)
+showObservation(number : int) : String
+stealFrom(place : String) : int
+checkWin() : int
+getWin() : int
+addVote(p : Player)
+subVote(p : Player)
+onSubmitVotes() : Player
+getCanAct() : PlayerList
+onVotingDone()
+getCurDay() : int
+getCurrent() : Player
+doViewLoc() : String
+onGameDone()
+getTestMode() : boolean
+onViewed()
+onLeaderboardCleared(leaderDisplay : ILeaderboard)
+getLeaderboard() : Leaderboard
}
```

```plantuml

title Model Classes
skin rose

hide empty methods

Class Player{
name : String
loc : String
votes : int
--
+Player(name : String)
+getName() : String
+observe(l: Location, loc : String)
+role() : int
+vote(votes : int)
+resetVotes()
+viewLoc() : String
+getVotes() : int
+observation(loc : Location, a : int)
+rob(l : Location, place : String)
+displayRole() : String
+addVote()
+subVote()
{static} +fromBundle(b : Bundle)
+checkBundleRole(b : Bundle) : boolean
}

class Bandit{
robbed : boolean
--
+role() : int
+observation(l : Location, a : int) : String
+displayRole() : String
+rob(l : Location, place : String) : int
+toBundle() : Bundle
{static} + fromBundle(b : Bundle) : Bandit
}

class Cowboy{
--
+observation(l : Location, a : int) : String
+role() : int
+displayRole() : String
+toBundle() : Bundle
{static} +fromBundle(b : Bundle) : Cowboy
}

class PlayerList{
players : ArrayList<Player> 
bandits : ArrayList<Bandit>
cowboys : ArrayList<Cowboy>
--
+addCowboy(name : String)
+addBandit(name : String)
+copyPlayers() : PlayerList
+toString() : String
+findPlayer(person : String) : Player
+removePlayer(p : Player)
+voteVals() : int[]
+tallyVotes() : int
+mostVotes() : Player
+checkTie() : boolean
+canRemove() : boolean
+toBundle() : Bundle
{static} +fromBundle(b : Bundle) : PlayerList
}

class Location{
bank : ArrayList<Player>
saloon : ArrayList<Player>
ranch : ArrayList<Player>
--
+clearLocs()
+randPlayer(name : String, place : String) : Player
+getValue(place : String) : int
+addTo(p : Player, place : String)
+inLocation(name : String) : String
+toBundle() : Bundle
{static} +fromBundle(b : Bundle) : Location
}

class Winner{
banditWin : boolean = false
cowboyWin : boolean = false
date : String
winDate : Date
--
+setBanditWin()
+setCowboyWin()
+toString() : String
+toBundle() : Bundle
{static} +fromBundle(b : Bundle) : Winner
}

Player o-- "Aggregation of" PlayerList: \t\t
Player -> "Contained in" Location: \t\t
Player <|-- Bandit
Player <|-- Cowboy

```

```plantuml
@startuml
title View
skin rose
hide empty methods

class ConfigGameFragment{
binding : FragmentConfigGameBinding
listener : Listener
--
+ConfigGameFragment()
+ConfigGameFragment(listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle)
+onViewCreated(view : View, savedInstanceState : Bundle)
+showConfig(m : MainActivity)
}

class AddPlayersFragment{
binding : FragmentAddPlayersBinding
listener : Listener
--
+AddPlayersFragment()
+AddPlayersFragment(listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle)
+onViewCreated(view : View, savedInstanceState : Bundle)
+showName(players : PlayerList)
+showRole(main : MainActivity)
+clearRole(players : PlayerList)
}

class PlayerListActionFragment{
binding : FragmentPlayerListActionBinding
listener : Listener
activePlayers : PlayerList
--
+PlayerListActionFragment()
+PlayerListActionFragment(listener : Listener, activePlayers : PlayerList)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle)
+onViewCreated(view : View, savedInstanceState : Bundle)
}

class ActionSelectFragment{
binding : FragmentActionSelectBinding
listener : Listener
active : Player
--
+ActionSelectFragment()
+ActionSelectFragment(active : Player, listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle)
+onViewCreated(view : View, savedInstanceState : Bundle)
+generalObserveButton(place : String)
+generalStealButton(place : String)
+addConfirm()
+cowboyAction()
+banditAction()
}

class viewObservationFragment{
binding : FragmentViewObservationBinding
listener : Listener
current : Player
--
+viewObservationFragment()
+viewObservationFragment(listener : Listener, current : Player)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle)
+onViewCreated(view : View, savedInstanceState : Bundle)
+cowboyObservation()
+banditObservation()
+addConfirm()
}
```
