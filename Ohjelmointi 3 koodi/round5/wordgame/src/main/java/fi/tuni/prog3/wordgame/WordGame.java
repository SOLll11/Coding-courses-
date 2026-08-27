/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fi.tuni.prog3.wordgame;

 import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordGame {

    private WordGameState gameState;
    private String wordFilename;
    private ArrayList<String> WordList = new ArrayList<>();
    private boolean GameOn = false;
    private String GamemasterWord;
    
    
    public WordGame(String wordFilename) {
        this.wordFilename = wordFilename;
        
         try (BufferedReader reader = new BufferedReader(new FileReader(wordFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                WordList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    

   
    public WordGameState getGameState()throws GameStateException {
        
        if(!GameOn){
            throw new GameStateException("There is currently no active word game!");
        }
        return gameState;
    }

    public static class WordGameState {
        private String Word;
        private int Mistakes;
        private int MistakeLimit;
        private int MissingChars;

        private WordGameState(String Word, int Mistakes, int MistakeLimit, int MissingChars) {
            this.Word = Word;
            this.Mistakes = Mistakes;
            this.MistakeLimit = MistakeLimit;
            this.MissingChars = MissingChars;
        }

        public String getWord() {
            return Word;
        }

        public int getMistakes() {
            return Mistakes;
        }

        public int getMistakeLimit() {
            return MistakeLimit;
        }

        public int getMissingChars() {
            return MissingChars;
        }
        
        
    }

    
  public void initGame(int wordIndex, int mistakeLimit) {

        int N = WordList.size();
        String chosenWord = WordList.get(wordIndex % N);

        String displayWord = "_".repeat(chosenWord.length());
        gameState = new WordGameState(displayWord, 0, mistakeLimit, chosenWord.length());

        this.GamemasterWord = WordList.get(wordIndex % N);

        GameOn = true;
    }

        
        
        
    
    public boolean isGameActive(){
        return GameOn;
          
    }
    public WordGameState guess(char c) throws GameStateException {

        if (!GameOn) {
            throw new GameStateException("There is currently no active word game!");
        }

        char lowerC = Character.toLowerCase(c);
        String lowerChosenWord = GamemasterWord.toLowerCase();
        StringBuilder newDisplayWord = new StringBuilder(gameState.getWord());
        boolean found = false;
        for (int i = 0; i < lowerChosenWord.length(); i++) {
            if (lowerChosenWord.charAt(i) == lowerC && newDisplayWord.charAt(i) == '_') {
                newDisplayWord.setCharAt(i, GamemasterWord.charAt(i));
                gameState.MissingChars--;
                found = true;
            }
        }

        if (!found) {
            gameState.Mistakes++;
        }

        gameState.Word = newDisplayWord.toString();

        if (gameState.MissingChars == 0 || gameState.Mistakes > gameState.MistakeLimit) {
            gameState.Word = GamemasterWord;
            GameOn = false;
        }

        return gameState;
    }

     
    public WordGameState guess(String word) throws GameStateException{
           if (!GameOn) {
            throw new GameStateException("There is currently no active word game!");
        }
        if (GamemasterWord != null && !GamemasterWord.isBlank()) {
            String lowercaseGuess = word.toLowerCase();
            String lowercaseWord = GamemasterWord.toLowerCase();
        
            if (!lowercaseGuess.equals(lowercaseWord)){
            gameState.Mistakes++; 
            }
            else{
            gameState.Word = GamemasterWord;
            gameState.MissingChars = 0;
            
            }
        
        }
         if (gameState.MissingChars == 0 || gameState.Mistakes > gameState.MistakeLimit) {
            gameState.Word = GamemasterWord;
            GameOn = false;
        }
       
        return gameState;
    }
            
            
}



