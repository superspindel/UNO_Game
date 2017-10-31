UNO_Game
=====
## D7032E Home exam

Home exam for course D7032E Software engineering. Tasked with refactoring source code for a UNO game, whereby a server-client architecture is used and which allows for users to play against bots.



### How to run

Example 1:

Use Intellij or Eclipse to build and run the project. This way you get colors when playing, since i have not implemented any another way then through unicode.


Example 2:

1. Run `compile.bat`
2. Go into bin folder
3. To run client: execute `java Client.Client BOT/USER` ( you decide if it should be a bot or user )
4. To run Server: execute `java Server.Server INT` ( Where INT is a integer for the number of clients to use )
5. Run Tests through `unittest.bat`

Example 3:

1. Go inside the directory called `vikfll-0`
2. Execute `dir /s /B *.java > sources.txt`.
3. Execute `javac @sources.txt`.
4. Step into the src folder.
5. Run as stated in example 2.


Example 4:
1. Go to folder `vikfll-0`
2. Execute `mkdir bin`
3. Execute `javac.exe -d bin -cp src src/Client/Client.java src/Server/Server.java`
4. Step into `src` folder
5. Run as stated in Example 2, steps 3 & 4.
