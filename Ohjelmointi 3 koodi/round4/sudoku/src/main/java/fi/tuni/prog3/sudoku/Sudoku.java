/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fi.tuni.prog3.sudoku;

/**
 *
 * @author Sakari
 */
public class Sudoku {
    private char[][] grid;

    public Sudoku() {
        grid = new char[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col] = ' ';
            }
        }
    }

public void set(int i, int j, char c) {
    if (i >= 9 || j >= 9 || i < 0 || j < 0) {
        System.out.println("Trying to access illegal cell ("+i+", "+j+")!");
    } else {
        // Check if the character is a valid Sudoku digit or a space
        if (c == ' ' || (c >= '1' && c <= '9')) {
            grid[i][j] = c;
        } else {
            System.out.println("Trying to set illegal character "+c+" to ("+i+", "+j+")!");
            grid[i][j] = ' ';
        }
    }
}
    
    public boolean check() {
    // Check rows
    for (int row = 0; row < 9; row++) {
        if (!checkArray(grid[row], "Row " + row)) {
            return false;
        }
    }

    // Check columns
    for (int col = 0; col < 9; col++) {
        char[] columnArray = new char[9];
        for (int row = 0; row < 9; row++) {
            columnArray[row] = grid[row][col];
        }
        if (!checkArray(columnArray, "Column " + col)) {
            return false;
        }
    }

    // Check sub-blocks
    for (int x = 0; x < 9; x += 3) {
        for (int y = 0; y < 9; y += 3) {
            char[] subBlockArray = new char[9];
            int index = 0;
            for (int row = x; row < x + 3; row++) {
                for (int col = y; col < y + 3; col++) {
                    subBlockArray[index++] = grid[row][col];
                }
            }
            if (!checkArray(subBlockArray, "Block at (" + x + ", " + y + ")")) {
                return false;
            }
        }
    }

    // If all checks pass, the Sudoku grid is legal
    return true;
}

// Helper function to check for repeated digits in an array
private boolean checkArray(char[] arr, String errorMsg) {
    boolean[] seen = new boolean[10]; // To keep track of seen digits (0-9)
    for (char c : arr) {
        if (c != ' ' && seen[c - '0']) {
            // Found a repeated digit
            System.out.println(errorMsg + " has multiple " + c + "'s!");
            return false;
        } else if (c != ' ') {
            seen[c - '0'] = true;
        }
    }
    return true;
}
public void print() {
    // Define the horizontal and vertical line segments
    String horizontalLine = "#####################################";
    String verticalLine = "#";
    String cellSeparator = "|";

    // Print the top border
    System.out.println(horizontalLine);

    // Iterate through each row in the Sudoku grid
    for (int row = 0; row < 9; row++) {
        // Print the left border for the row
        System.out.print(verticalLine);

        // Iterate through each column in the current row
        for (int col = 0; col < 9; col++) {
            char cellValue = grid[row][col];
            System.out.print(" ");
            System.out.print(cellValue);
            System.out.print(" ");
            
            // Add vertical separator every 3 columns (except the last column)
            if ((col+1) % 3 == 0) {
                System.out.print(verticalLine);
            }
            else {
                System.out.print(cellSeparator);
            }
        }

        

        // Add horizontal separator every 3 rows (except the last row)
        if ((row+1) % 3 == 0) {
            System.out.println("\n"+horizontalLine);  
        } else {
            System.out.println("\n"+"#---+---+---#---+---+---#---+---+---#");
            
        }
    }
}


}





