# Personal-Shot-List-Generator
**Individual Project by** Jack Sternberg
**Authors:** Jack Sternberg

___
### Compilation and Execution Requirements

**Prerequisites**
- Java (JDK) Version 8 or higher

**Compilation**
From the project directory, run `javac *.java`

**Execution**
Run the following files on your terminal
````
java Main
````

**There is no login needed, as this website is for personal use and will not accept multiple users via server and client**

**Testing**
- You can test this website with varying combinations of Movie Moods and Genres, seeing what the text interface will output each time.
- Movie Moods and Genres are stored in the Moods.txt and Genres.txt file in the repo.
- Any edit to these Moods and Genres must be reflected in switch-statement logic to find a user match in `Main.java`.

- Once Mood and Genre are chosen, the user can choose to generate a shot suggestion designed through simple addition of qualities (like a questionnaire).
- User can then choose to save to a text file afterward. Mind that there are 20 files max that are allowed, and saving once this number is reached means an overwrite of the first file is required.

- Scene duration in the shot suggestion output is also calculated through questionnaire-like arithmetic.

  **Counter.txt**
  This file is very special, as it contains the number of files created over every instance of booting up the site.
  - Counts with the local variable fileCounter, which is more efficient for finding a numbered-name for the next saved file
 
  Date Last Worked On: July 28, 2026
