package com.ssafy.edu.repository.mission;

import com.ssafy.edu.model.mission.MissionDifficulty;
import com.ssafy.edu.model.mission.MissionLikeUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionDifficultyJpaRepository  extends JpaRepository<MissionDifficulty,Long> {
    public List<MissionDifficulty> findByUserEmailAndMissionId(String userEmail, Long missionId);
}