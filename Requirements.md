Milestone 1
Nathan, Andy, Mike, Jason

Current System:
	The acheivments and news feeds are scattered accross the steam app and website. The most convenient way of access them quickly is via the steam library, which is only for the games the user owns. This is inconveint if a person is trying to track many games.

Goals:
	The goal of this project is to provide a system in which a user can set a profile for selected games, not necessarily ones that they have purchased in their Steam Libraries, and track the news feeds for said games. In addition to the news feeds, users will be able to track acheivment data, live player counts, and custom data that is uniquely avaible from each game. The users favorites will be tracked in a local database.

Stakeholders:
	This app is meant for people who want to check acheivements and news for games, such as people who are scanning for a publication to talk about in their own news service or stat trackers for a company.

Input:
	Very little direct input will be required from the user. They will need to set up and edit their favorites profile by entering the name of the game they wish to add. From there all input will be navigation. There is also the possiblity of adding a prefered number of news items.

Processing:
	Every piece of software availible on steam has its own unique identifier. The program will track down this identifier using the necessary API and check that identifier against the web and community apis to retrieve news items and other statistics.

Output:
	The output will be the news feed, acheivments and the rest of the availble statistics for the user's favorited games

APIs
	Steam Community API: https://partner.steamgames.com/documentation/community_data
	Steam Web API: https://developer.valvesoftware.com/wiki/Steam_Web_API
	Steam Software API: https://api.steampowered.com/ISteamApps/GetAppList/v2/