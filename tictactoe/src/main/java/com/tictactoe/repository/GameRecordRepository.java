package com.tictactoe.repository;

import com.tictactoe.model.GameRecord;
import com.tictactoe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {
    List<GameRecord> findByPlayerOrderByPlayedAtDesc(User player);
    List<GameRecord> findTop10ByOrderByPlayedAtDesc();
    long countByPlayer(User player);
}
