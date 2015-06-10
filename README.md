# MyLoLStats

Android application which users can use to enter their game statistics (Champion played, win/loss, kills, deaths and assists)
from the League of Legends game. Those statistics are saved in SQLite database, and are used to display animated statistics to 
the user, whether they want to see specific champion statistics, or the champion with the best win rate or KDA (Kills-deaths-assists ratio).
Also, user can see every game they played in a table, where they can delete or update desired games. 

The app is built with fragments, one fragment is shown when device is in the portrait mode, and two are shown when its in the
landscape mode. 

UI is pretty bad, straightforward and boring since i didnt really focus on it. As im geting into Material design ill try to 
update it.

The app is not 100% finished yet, but the Core of it is pretty much done.
I'll still be working on it, perhaps use ContentProvider to add suggestions when searching for specific champion data, and Loaders to display table with only the desired champions, not all the entries from the database.

![ScreenShot](http://s29.postimg.org/upjotgk93/Main_menu.png)

![ScreenShot](http://s8.postimg.org/oamjpm3k5/portrait.png)

![ScreenShot](http://s15.postimg.org/hxdt7lgq3/delete_Update.png)

![ScreenShot](http://s16.postimg.org/u3agkuuvp/addnew.png)

![ScreenShot](http://s2.postimg.org/6qx896jx5/best_Kda.png)

![ScreenShot](http://s17.postimg.org/4a60armrj/best_Win_Rate.png)

![ScreenShot](http://s8.postimg.org/wvkguj345/best_Win_Rate2.png)

![ScreenShot](http://s17.postimg.org/gif38054v/check_Champion.png)

![ScreenShot](http://s23.postimg.org/h8ymma5mz/check_Champion2.png)


