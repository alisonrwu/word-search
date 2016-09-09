package com.wu.alison.wordsearch;

import java.util.Random;

/**
 * Created by Alison on 2016-06-12.
 */
public class WordSearch {
//        public static void main(String[] args) {
//        String[] testList = {"Alison", "Timothy", "Leona", "Stella", "Lily"};
//        WordSearch ws = new WordSearch(testList, 10);
//        System.out.println(ws);
//    }
    private final char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private char[][] board;
    private char[][] solutionBoard;
    private String[] words;
    private int size; //board will be size x size

    // Constructor
    public WordSearch(String[] wordList, int size) {
        words = wordList;
        this.size = size;

        board = new char[size][size];
        // fill new board with blank ' '
        for(int r=0; r<board.length; r++) {
            for (int c=0; c<board[0].length; c++) {
                board[r][c] = ' ';
            }
        }

        // add each word in wordList
        for(String word: words) {
            add(word, board);
        }

        // make solution board
        board = fillRestSolution(board);
        solutionBoard = new char[board.length][board.length];
        for (int r=0; r<board.length; r++) {
            for (int c=0; c<board[0].length; c++) {
                solutionBoard[r][c] = board[r][c];
            }
        }
        // fill rest of board with random
        board = fillRestRandom(board);
    }

    private void add(String w, char[][] board) {
        String word = w.toUpperCase();
        char[][] original = new char[board.length][board.length];
        for (int r=0; r<board.length; r++) {
            for (int c=0; c<board[0].length; c++) {
                original[r][c] = board[r][c];
            }
        }

        // make word backwards randomly
        Random r = new Random();
        boolean flip = r.nextBoolean();
        if(flip) {
            word = flip(word);
        }

        // put word in board where possible
        int orientation = r.nextInt(4); // 0 is horizontal, 1 is vertical, 2 is diagonal\, 3 is diagonal/
        int row = board.length;
        int col = board.length;
        if (orientation == 0
                && col>board.length-word.length()) {
            col -= word.length();
        } else if (orientation == 1
                && row>board.length-word.length()) {
            row -= word.length();
        } else if (orientation == 2) {
            if(col>board.length-word.length())
                col -= word.length();
            if(row>board.length-word.length())
                row -= word.length();
        } else if (orientation == 3) {
            if(col>board.length-word.length())
                col = board.length-word.length();
        }
        //System.out.println("ori: " + orientation);
        int tryRow, tryCol;
        boolean filled = false;
        do {
            //System.out.println("Possible: " + row + ", " + col);
            tryRow = r.nextInt(row);
            if (orientation == 3 && tryRow<word.length())
                tryRow = word.length();
            tryCol = r.nextInt(col);
            //System.out.println("Actual: " + tryRow + ", " + tryCol);
            for (int i = 0; i < word.length(); i++) {
                if (board[tryRow][tryCol] == ' ' || board[tryRow][tryCol] == word.charAt(i)) {
                    board[tryRow][tryCol] = word.charAt(i);
                    if (orientation == 0) tryCol++;
                    else if (orientation == 1) tryRow++;
                    else if (orientation == 2) { tryCol++; tryRow++; }
                    else if (orientation == 3) { tryCol++; tryRow--; }
                } else {
                    for(int j=i; j>0; j--) {
                        if(orientation == 0) tryCol--;
                        else if(orientation == 1) tryRow--;
                        else if(orientation == 2) { tryCol--; tryRow--; }
                        else if(orientation == 3) { tryCol--; tryRow++; }
                        board[tryRow][tryCol] = original[tryRow][tryCol];
                    }
                    //board = original;
                    filled = false;
                    break;
                }
                filled = true;
            }
        } while (!filled);
        //System.out.println("Successfully added " + word + "!");
    }

    private String flip(String word) {
        StringBuilder flipped = new StringBuilder();
        for(int i=word.length()-1; i>=0; i--)
            flipped.append(word.charAt(i));
        return flipped.toString();
    }

    private char[][] fillRestRandom(char[][] board) {
        Random rdm = new Random();
        int randChar;
        for(int r=0; r<board.length; r++) {
            for(int c=0; c<board[0].length; c++) {
                if(board[r][c] == '.') {
                    randChar = rdm.nextInt(alphabet.length);
                    board[r][c] = alphabet[randChar];
                }
            }
        }
        return board;
    }

    private char[][] fillRestSolution(char[][] board) {
        for(int r=0; r<board.length; r++) {
            for(int c=0; c<board[0].length; c++) {
                if(board[r][c] == ' ') {
                    board[r][c] = '.';
                }
            }
        }
        return board;
    }

    public char[][] getWordSearch() {
        return board;
    }

    public char[][] getWordSearchSolution() {
        return solutionBoard;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        ret.append("Puzzle:\n");
        for(int r=0; r<board.length; r++) {
            for(int c=0; c<board.length; c++) {
                ret.append(board[r][c]);
                ret.append(' ');
            }
            ret.append("\n");
        }

        ret.append("\n");

        ret.append("Solution:\n");
        for(int r=0; r<solutionBoard.length; r++) {
            for(int c=0; c<solutionBoard.length; c++) {
                ret.append(solutionBoard[r][c]);
                ret.append(' ');
            }
            ret.append("\n");
        }

        return ret.toString();
    }
}
