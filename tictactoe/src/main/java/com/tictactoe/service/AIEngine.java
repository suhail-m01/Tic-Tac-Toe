package com.tictactoe.service;

import org.springframework.stereotype.Component;

/**
 * AI Engine using Minimax with alpha-beta pruning.
 * HARD mode = unbeatable (perfect play).
 * EASY mode = random moves (beginner-friendly).
 */
@Component
public class AIEngine {

    private static final char AI     = 'O';
    private static final char HUMAN  = 'X';
    private static final char EMPTY  = '_';

    /**
     * Returns best move index (0-8) for the AI.
     * @param board  9-char array ('X','O','_')
     * @param hard   true = minimax, false = random
     */
    public int getBestMove(char[] board, boolean hard) {
        if (!hard) return getRandomMove(board);
        return minimaxRoot(board);
    }

    // ── EASY: random available cell ──────────────────────────────────────────
    private int getRandomMove(char[] board) {
        java.util.List<Integer> available = new java.util.ArrayList<>();
        for (int i = 0; i < 9; i++) if (board[i] == EMPTY) available.add(i);
        if (available.isEmpty()) return -1;
        return available.get((int)(Math.random() * available.size()));
    }

    // ── HARD: minimax root ───────────────────────────────────────────────────
    private int minimaxRoot(char[] board) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove  = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                board[i] = AI;
                int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                board[i] = EMPTY;
                if (score > bestScore) { bestScore = score; bestMove = i; }
            }
        }
        return bestMove;
    }

    private int minimax(char[] board, int depth, boolean isMax, int alpha, int beta) {
        int result = evaluate(board);
        if (result != 0) return result - depth * (result > 0 ? 1 : -1);
        if (isFull(board))  return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == EMPTY) {
                    board[i] = AI;
                    best = Math.max(best, minimax(board, depth+1, false, alpha, beta));
                    board[i] = EMPTY;
                    alpha = Math.max(alpha, best);
                    if (beta <= alpha) break;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == EMPTY) {
                    board[i] = HUMAN;
                    best = Math.min(best, minimax(board, depth+1, true, alpha, beta));
                    board[i] = EMPTY;
                    beta = Math.min(beta, best);
                    if (beta <= alpha) break;
                }
            }
            return best;
        }
    }

    /** +10 = AI wins, -10 = Human wins, 0 = no winner yet */
    public int evaluate(char[] board) {
        int[][] lines = {
            {0,1,2},{3,4,5},{6,7,8}, // rows
            {0,3,6},{1,4,7},{2,5,8}, // cols
            {0,4,8},{2,4,6}          // diagonals
        };
        for (int[] l : lines) {
            if (board[l[0]] != EMPTY && board[l[0]] == board[l[1]] && board[l[1]] == board[l[2]]) {
                return board[l[0]] == AI ? 10 : -10;
            }
        }
        return 0;
    }

    public boolean isFull(char[] board) {
        for (char c : board) if (c == EMPTY) return false;
        return true;
    }

    /** Returns winning line indices, or null */
    public int[] getWinningLine(char[] board) {
        int[][] lines = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };
        for (int[] l : lines) {
            if (board[l[0]] != EMPTY && board[l[0]] == board[l[1]] && board[l[1]] == board[l[2]])
                return l;
        }
        return null;
    }
}
