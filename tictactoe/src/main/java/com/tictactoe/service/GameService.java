package com.tictactoe.service;

import com.tictactoe.model.GameRecord;
import com.tictactoe.model.User;
import com.tictactoe.repository.GameRecordRepository;
import com.tictactoe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {

    private final AIEngine          aiEngine;
    private final UserRepository    userRepo;
    private final GameRecordRepository gameRepo;

    // ── DTOs ─────────────────────────────────────────────────────────────────

    public record MoveRequest(String board, int cell, String mode, String difficulty) {}

    public record MoveResponse(
        String  board,
        String  status,   // ONGOING, WIN_X, WIN_O, DRAW
        int[]   winLine,
        int     aiMove,
        String  message
    ) {}

    // ── Process a player move ─────────────────────────────────────────────────
    public MoveResponse processMove(MoveRequest req) {
        char[] board = req.board().toCharArray();
        boolean vsAI    = "VS_AI".equals(req.mode());
        boolean hardAI  = "HARD".equals(req.difficulty());

        // Validate cell
        if (req.cell() < 0 || req.cell() > 8 || board[req.cell()] != '_') {
            return new MoveResponse(req.board(), "INVALID", null, -1, "Invalid move");
        }

        // Player (X) moves
        board[req.cell()] = 'X';
        int moveCount = countMoves(board);

        // Check after player move
        String status  = evaluateStatus(board);
        int[]  winLine = aiEngine.getWinningLine(board);
        int    aiMove  = -1;

        if ("ONGOING".equals(status) && vsAI) {
            // AI (O) moves
            aiMove = aiEngine.getBestMove(board, hardAI);
            if (aiMove >= 0) {
                board[aiMove] = 'O';
                status  = evaluateStatus(board);
                winLine = aiEngine.getWinningLine(board);
            }
        }

        return new MoveResponse(new String(board), status, winLine, aiMove, buildMessage(status));
    }

    // ── Save completed game ───────────────────────────────────────────────────
    @Transactional
    public void saveGame(String username, String result, String mode,
                         String difficulty, String board, String opponentName) {
        User user = userRepo.findByUsername(username).orElseThrow();

        switch (result) {
            case "WIN"  -> user.recordWin();
            case "LOSS" -> user.recordLoss();
            case "DRAW" -> user.recordDraw();
        }
        userRepo.save(user);

        GameRecord gr = new GameRecord();
        gr.setPlayer(user);
        gr.setResult(result);
        gr.setMode(mode);
        gr.setDifficulty(difficulty);
        gr.setOpponentName(opponentName);
        gr.setMoves(countMoves(board.toCharArray()));
        gr.setBoardSnapshot(board);
        gameRepo.save(gr);
    }

    // ── History ───────────────────────────────────────────────────────────────
    public List<GameRecord> getHistory(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return gameRepo.findByPlayerOrderByPlayedAtDesc(user);
    }

    // ── Leaderboard ───────────────────────────────────────────────────────────
    public List<User> getLeaderboard() {
        return userRepo.findAll()
            .stream()
            .filter(u -> u.getTotalGames() > 0)
            .sorted(Comparator.comparingInt(User::getWins).reversed()
                .thenComparingInt(User::getWinRate).reversed())
            .limit(10)
            .toList();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private String evaluateStatus(char[] board) {
        int eval = aiEngine.evaluate(board);
        if (eval == 10)  return "WIN_O";
        if (eval == -10) return "WIN_X";
        if (aiEngine.isFull(board)) return "DRAW";
        return "ONGOING";
    }

    private int countMoves(char[] board) {
        int c = 0;
        for (char ch : board) if (ch != '_') c++;
        return c;
    }

    private String buildMessage(String status) {
        return switch (status) {
            case "WIN_X" -> "You Win! 🎉";
            case "WIN_O" -> "AI Wins! 🤖";
            case "DRAW"  -> "It's a Draw! 🤝";
            default      -> "";
        };
    }
}
