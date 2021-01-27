package com.ssafy.edu.model.mission;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.edu.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 미션
 * id : 미션 pk
 * title : 미션 제목
 * content : 미션 본문
 * category : 미션 카테고리
 * created_at : 미션 제작일
 * updated_at : 미션 수정일
 * difficulty : 난이도
 * code : 코드
 * user : 미션 제작 유저
 * missionCommentsList : 미션 댓글
 * missionLikeUsersList : 미션 좋아요 유저
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="missions")
public class Mission {
    @Id //ID 어노테이션으로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성
    @Column(name = "mission_id")
    private Long id;
    private String title;
    private String content;
    private String category;
    private String code;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated_at;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "mission")
    private List<MissionLikeUsers> missionLikeUsersList = new ArrayList<>();

    
}