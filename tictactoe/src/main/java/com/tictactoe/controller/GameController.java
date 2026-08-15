package com.tictactoe.controller;

import com.tictactoe.model.User;
import com.tictactoe.repository.UserRepository;
import com.tictactoe.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService    gameService;
    private final UserRepository userRepo;

    // ── Pages ─────────────────────────────────────────────────────────────────

    @GetMapping("/")
    public String root() { return "redirect:/game"; }

    @GetMapping("/game")
    public String gamePage(@AuthenticationPrincipal UserDetails ud, Model model) {
        User user = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("leaderboard", gameService.getLeaderboard());
        return "game";
    }

    @GetMapping("/history")
    public String historyPage(@AuthenticationPrincipal UserDetails ud, Model model) {
        User user = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("records", gameService.getHistory(ud.getUsername()));
        return "history";
    }

    // ── API ───────────────────────────────────────────────────────────────────

    @PostMapping("/api/move")
    @ResponseBody
    public ResponseEntity<?> move(@RequestBody GameService.MoveRequest req) {
        return ResponseEntity.ok(gameService.processMove(req));
    }

    @PostMapping("/api/save")
    @ResponseBody
    public ResponseEntity<?> save(@AuthenticationPrincipal UserDetails ud,
                                  @RequestBody Map<String, String> body) {
        gameService.saveGame(
            ud.getUsername(),
            body.get("result"),
            body.get("mode"),
            body.get("difficulty"),
            body.get("board"),
            body.get("opponentName")
        );
        User updated = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        return ResponseEntity.ok(Map.of(
            "wins",       updated.getWins(),
            "losses",     updated.getLosses(),
            "draws",      updated.getDraws(),
            "totalGames", updated.getTotalGames(),
            "winRate",    updated.getWinRate()
        ));
    }

    @GetMapping("/api/leaderboard")
    @ResponseBody
    public ResponseEntity<?> leaderboard() {
        return ResponseEntity.ok(gameService.getLeaderboard()
            .stream()
            .map(u -> Map.of(
                "username",   u.getUsername(),
                "wins",       u.getWins(),
                "losses",     u.getLosses(),
                "draws",      u.getDraws(),
                "totalGames", u.getTotalGames(),
                "winRate",    u.getWinRate()
            ))
            .toList()
        );
    }
}
