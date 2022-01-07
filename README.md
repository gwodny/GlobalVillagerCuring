### *This plugin makes use of the paper api and will only work on paper and it's derivatives. This will not work on spigot.*


# GlobalVillagerCuring

A Paper MC plugin that gives the positive effects of curing a zombie villager to all players, not just the player that cured the villager.

Trading reputation, negative reputation, and demand remain unchnged. This plugin only affects the villager reputaions that are altered from curing a villager.

## Installation

Download a .jar from the releases page and move it into the plugins folder of your paper or paper derivative server.

## Usage
If you install this plugin before any players have cured a villager, you're all set. The plugin will give all players better trades for a villager once it is cured.

If you want to retroactively enable better trades for villagers that have already been cured, simply run the `/setBestReputation` command. This must be run by the server or an opped player. It will set every player's positive reputation for each villager to the same values as the player that has the highest positive reputation for that villager.

Another command added by this plugin is `/reputationQuery`. This command can be run by any player. If there is a villager within five blocks of the sender, it will return the reputation values of that villager for the player that sent the command.




