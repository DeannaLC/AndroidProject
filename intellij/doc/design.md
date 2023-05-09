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
participant ":PlayerList" as players

alt Voting
UI -> gamer: Display list of names
alt add votes
gamer -> UI: Press add on Player
UI -> player: addVote()
else subtract votes
gamer -> UI: Press subtract on Player
UI -> player: subVote()
end
UI -> gamer: Display new vote counts
else Done Voting
gamer -> UI: Press submit
UI -> main: onSubmitVotes()
main -> players: checkTie()
main -> players: camRemove()
alt checkTie() && canRemove()
main -> players: mostVotes()
main -> players: removePlayer()
end
main -> UI: onVotingDone() 
UI -> gamer: Display voting results
end

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

interface IActionSelect.Listener{
observeAt(place : String, player : Player)
stealFrom(place : String) : int
onActionDone()
getCurrent() : Player
}

interface IAddPlayers.Listener{
onAddedPlayer(name : String, addPlayers : IAddPlayers)
checkPlayerCap() : boolean
onPlayersSet()
getPlayers() : PlayerList
showRole() : String
findPlayer(name : String) : Player
}

interface IConfigGame.Listener{
onSetOptions(total : int, bandits : int, dayLim : int, config : IConfigGame)
onOptionsSet()
}

interface ILeaderboard.Listener{
getLeaderBoard() : Leaderboard
onViewed()
onLeaderboardCleared(l : ILeaderboard)
}

interface IPlayerListAction.Listener{
setCurrentPlayer(player : Player)
playerSelected(name : String)
checkPhase() : int
getCanAct() : PlayerList
}

interface IResults.Listener{
getWin() : int
getMoney() : int
onGameDone() : int
}

interface IStart.Listener{
onBegin()
onViewed()
onLeaderboardCheck()
}

interface IViewObservation.Listener{
showObservation(choice : int) : String
onActionDone()
getCurrent() : Player
doViewLoc() : String
}

interface IVote.Listener{
findPlayer(name : String) : Player
onSubmitVotes() : Player
getCurDay() : int
onVotingDone()
getPlayers() : PlayerList
getTestMode() : boolean
}

IActionSelect.Listener <|.. MainActivity
IAddPlayers.Listener <|.. MainActivity
IConfigGame.Listener <|.. MainActivity
ILeaderboard.Listener <|.. MainActivity
IPlayerListAction.Listener <|.. MainActivity
IResults.Listener <|.. MainActivity
IStart.Listener <|.. MainActivity
IViewObservation.Listener <|.. MainActivity
IVote.Listener <|.. MainActivity
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
+Winner()
+setBanditWin()
+setCowboyWin()
+toString() : String
+toBundle() : Bundle
{static} +fromBundle(b : Bundle) : Winner
}

class Leaderboard{
winners : ArrayList
--
+Leaderboard()
+addWinner(win : Winner)
+toString() : String
+toBundle() : Bundle
{static} +fromBundle(b : Bundle) : Leaderboard
}

Winner o-- "Aggregation of" Leaderboard: \t\t
Player o-- "Aggregation of" PlayerList: \t\t
Player -> "Contained in" Location: \t\t
Player <|-- Bandit
Player <|-- Cowboy

```

```plantuml
@startuml
title Persistence
skin rose
hide empty methods

interface IPersistenceFacade{
saveLeaderboard(leaderboard : Leaderboard)
retrieveLeaderboard() : Leaderboard
}

class LocalStorageFacade{
directory : File
--
+saveLeaderboard(leaderboard : Leaderboard)
+retrieveLeaderboard() : Leaderboard
}

IPersistenceFacade <|.. LocalStorageFacade
```

```plantuml
@startuml
title View
skin rose
hide empty methods

class MainView{
fmanager : FragmentManager
binding : MainScreenBinding
--
+MainView(activity : FragmentActivity)
+getRootView() : View
+displayFragment(fragment : Fragment, reversible : boolean, name : String)
}

interface IMainView{
getRootView() : View
displayFragment(fragment : Fragment, reversible : boolean, name : String)
}

class HighNoonHeistFragFactory{
controller : MainActivity
--
HighNoonHeistFragFactory(controller : MainActivity)
instantiate(classLoader : ClassLoader, className : String) : Fragment
}

class StartFragment{
listener : Listener
binding : FragmentStartBinding
rules1 : boolean = false
rules2 : boolean = false
rules3 : boolean = false
--
+StartFragment()
+StartFragment(listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
+rulesSet1()
+rulesSet2()
+rulesSet3()
+onSaveInstanceState(outState : Bundle)
+onViewStateRestored(savedInstanceState : Bundle)
}

interface IStart{
Listener
}

class ConfigGameFragment{
binding : FragmentConfigGameBinding
listener : Listener
--
+ConfigGameFragment()
+ConfigGameFragment(listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
+showConfig(m : MainActivity)
+onSaveInstanceState(outState : Bundle)
+onViewStateRestored(savedInstanceState : Bundle)
}

interface IConfigGame{
Listener
showConfig()
}

class AddPlayersFragment{
binding : FragmentAddPlayersBinding
listener : Listener
viewingRole : boolean = false
--
+AddPlayersFragment()
+AddPlayersFragment(listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
+showNames(players : PlayerList)
+showRole(main : MainActivity)
+clearRole(players : PlayerList)
+onSaveInstanceState(outState : Bundle)
+onViewStateRestored(savedInstanceState : Bundle)
}

interface IAddPlayers{
Listener
showNames()
showRole()
}

class PlayerListActionFragment{
binding : FragmentPlayerListActionBinding
listener : Listener
--
+PlayerListActionFragment()
+PlayerListActionFragment(listener : Listener, activePlayers : PlayerList)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
}

interface IPlayerListAction{
Listener
}

class ActionSelectFragment{
binding : FragmentActionSelectBinding
listener : Listener
stealing : boolean = false
watchingPlace : boolean = false
onStealConfirm : boolean = false
onWatchConfirm : boolean = false
place : String
stealingVal : int = 0
--
+ActionSelectFragment()
+ActionSelectFragment(active : Player, listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
+generalObserveButton(place : String) : View.OnClickListener
+generalStealButton(place : String) : View.OnClickListener
+addConfirm()
+watchConfirm()
+stealConfirm()
+cowboyAction()
+banditAction()
+stealingOptions()
+onSaveInstanceState(outState : Bundle)
+onViewStateRestored(savedInstanceState : Bundle)
}

interface IActionSelect{
Listener
}

class ViewObservationFragment{
binding : FragmentViewObservationBinding
listener : Listener
viewingPerson : boolean = false
viewingNumber : boolean = false
result : String
--
+viewObservationFragment()
+viewObservationFragment(listener : Listener, current : Player)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
+cowboyObservation()
+banditObservation()
+showNumber()
+showPerson()
+addConfirm()
+onSaveInstanceState(outState : Bundle)
+onViewStateRestored(savedInstanceState : Bundle)
}

interface IViewObservation{
Listener
}

class VoteFragment{
listener : Listener
binding : FragmentVoteBinding
votesDone : boolean = false
votingOut : Player
--
+VoteFragment()
+VoteFragment(listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
+addVoteListener(name : String) : View.OnClickListener
+subVoteListener(name : String) : View.OnClickListener
+doneVoting()
+onSaveInstanceState(outState : Bundle)
+onViewStateRestored(savedInstanceState : Bundle)
}

interface IVote{
Listener
}

class ResultScreenFragment{
binding : FragmentResultScreen
listener : Listener
--
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
}

interface IResults{
Listener
}

class LeaderboardFragment{
listener : Listener
binding : FragmentLeaderboardBinding
--
+LeaderboardFragment()
+LeaderboardFragment(listener : Listener)
+onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
+onViewCreated(view : View, savedInstanceState : Bundle)
+showDisplay()
}

interface ILeaderboard{
Listener
showDisplay()
}

IMainView <|.. MainView
IStart <|.. StartFragment
IConfigGame <|.. ConfigGameFragment
IAddPlayers <|.. AddPlayersFragment
IPlayerListAction <|.. PlayerListActionFragment
IActionSelect <|.. ActionSelectFragment
IViewObservation <|.. ViewObservationFragment
IVote <|.. VoteFragment
IResults <|.. ResultScreenFragment
ILeaderboard <|.. LeaderboardFragment


```
