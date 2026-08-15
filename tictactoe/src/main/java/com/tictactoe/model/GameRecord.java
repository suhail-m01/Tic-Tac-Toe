package com.tictactoe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_records")
@Getter @Setter @NoArgsConstructor
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private User player;

    @Column(nullable = false)
    private String opponentName; // "AI (Easy)", "AI (Hard)", or username

    @Column(nullable = false)
    private String result; // WIN, LOSS, DRAW

    @Column(nullable = false)
    private String mode; // VS_AI, VS_FRIEND

    @Column(nullable = false)
    private String difficulty; // EASY, HARD, N/A

    @Column(nullable = false)
    private int moves;

    @Column(nullable = false, updatable = false)
    private LocalDateTime playedAt = LocalDateTime.now();

    // Board snapshot (comma-separated: X,O,_,...)
    @Column(length = 50)
    private String boardSnapshot;
}
