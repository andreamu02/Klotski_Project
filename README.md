# Klotski

A simple puzzle game for a Software Engineering project.
It is implemented in Java using Swing.

### How to run

Download and unzip the project, go to `Klotski`>`jar executable` and double-click on `Klotski.jar`.
If this does not work you have to open it with the terminal.
In Windows:
  Open cmd and go to the `jar executable` folder.
  Write and enter the following command:
  ```java -jar Klotski.jar```

In MacOs:
  Open terminal and go to the `jar executable` folder.
  Write and enter the following command:
  ```java -jar Klotski.jar```

In Linux:
  Open bash/terminal and go to the `jar executable` folder.
  Write and enter the following command:
  ```java -jar Klotski.jar```
  
* _If there are any problems go to the Issues section of this readme file_

You can also open and run this project from an IDE such as Intellij and to do that
you have to download and unzip this folder and go to `Klotski`>`Klotski` and open
this folder as a IntelliJ project.
Alternately you can open your preferred IDE and open an existing project, navigate trought
folders and select the same folder as described the previous line.

### How to play

The goal is to release the large 2x2 tile from the board through the red line
at the bottom. To move a piece you can drag and drop it,
or you can select it and arrow-moving it.
To move a selected block you can use directional arrows or WASD keys.
Alternately you can click on the arrow buttons in the GUI.

You must try to complete the puzzle with few moves possible.

First of all you have to select or insert a username. If there's already that username
saved, the game ask you if you want to load the local saved configuration.

To reset the board you can use the "Reset" button in the GUI
or Reset from the Klotski Menu in the upper side.
You can also use `CTRL+R key` combination to make it faster.

You can choose one of the fourth combination of the Board and to do it
you can select one of these configuration from the Klotski Menu in the upper side.
You can also use ```CTRL+{the combination number}``` to make it faster, for example `CTRL+2`.

The game is by defalut saved in locale, but you can choose from the File>Save as Menu or
pressing `CTRL+S` where to save this game. You can also load an existing configuration
from the same File Menu or with `CTRL+O`.

It is possible everywhen during the game to change the current username and start a new
Game with a new user. You can do it from the File Menu or pressing `CTRL+C`.

With CTRL+N you can make a move suggested by the game, that is the best move possible.

With CTRL+Q or pressing quit buttons you can quit the game and it will be automatically saved.


### Testing

All our test cases are in the `test` folder, divided into packages in the same way
as the `src` folder. Our source code has 96% test code average

### Documentation

Going to `Klotski`>`Klotski`>`out`>`javadoc` you can open `index.html` file to read The javadoc
of this project. Every class, method and field is described in the javadoc and classes all full
of comments.

### Issues

If you have any issues to start the game you may have to control your java version if it's installed.
To do that, open Terminal (or cmd/bash) and type ```java -version```.
If nothing is shown or is displayed an error, that means you have to install JRE on your machine.
If you just want to play the game without coding, you can simply download and install Java 8 from 
the [Oracle Site](https://www.java.com/it/download/).

Otherwise, if you want to code it you have to install the JDK 17. To do that go to the [download section](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
and select the installer for your platform.

After install JDK or JRE or if you have already install them, you can play the game.
If yout JDK version is below 17 version, install JDK 17 with the same process displayed before.
