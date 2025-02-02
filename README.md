# Wordle-Game
a simple Wordle game implemented in Java using Swing for the graphical user interface. The game allows players to guess a five-letter word within six attempts, receiving feedback on their guesses through color-coded hints.

Features

Graphical UI: Built using Java Swing for an interactive experience.

Word Checking: Ensures guesses are valid words from the provided dictionary (words.txt).

Color-Coded Feedback:

🟩 Green: Correct letter in the correct position.

🟨 Yellow: Correct letter in the wrong position.

⬜ Gray: Incorrect letter.

Shake Effect for Invalid Words: Provides visual feedback for invalid guesses.

Game Restart: Automatically resets after a win or loss.



Installation & Setup

Prerequisites
Java Development Kit (JDK) 8 or later
Git (optional, for version control)

STEPS:

1. Clone the repository:
git clone https://github.com/Eng-AlaaHosny/Wordle-Game.git
cd Wordle-Game

2.Compile the game:
javac WordleGame.java

3.run the game 
java WordleGame

Files;

WordleGame.java - Main Java file containing game logic and UI.
words.txt - List of possible five-letter words.
.gitignore - Excludes .class files and IDE-specific files.

How to Play
Enter a five-letter word and press Submit.
Color-coded hints will guide you:
Green: Correct letter in the correct position.
Yellow: Correct letter in the wrong position.
Gray: Incorrect letter.
You have six attempts to guess the word.
If you guess the word, you win! 🎉
If you use all attempts, the game displays the correct word and restarts.
