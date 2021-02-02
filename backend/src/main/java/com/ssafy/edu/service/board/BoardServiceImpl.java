package com.ssafy.edu.service.board;

import com.ssafy.edu.model.board.*;
import com.ssafy.edu.model.user.User;
import com.ssafy.edu.repository.BoardCommentJpaRepository;
import com.ssafy.edu.repository.BoardJpaRepository;
import com.ssafy.edu.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private BoardJpaRepository boardJpaRepository;

    @Autowired
    private BoardCommentJpaRepository boardCommentJpaRepository;

    @Override
    public ResponseEntity<BoardBasicResponse> getBoardList() {

        BoardBasicResponse result = new BoardBasicResponse();

        List<Board> boardList = boardJpaRepository.findAll();

        if(!boardList.isEmpty()){ // 공지사항이 게시글이 있는 경우

            List<BoardResponse> boardResponseList = new ArrayList<>();
            for(Board b : boardList){
                BoardResponse br = new BoardResponse();
                br.setBoardId(b.getBoardId());
                br.setTitle(b.getTitle());
                br.setEmail(b.getUser().getEmail());
                br.setViews(b.getViews());
                br.setCreatedAt(b.getCreatedDate());
                br.setUpdatedAt(b.getModifiedDate());
                boardResponseList.add(br);
            }

            result.status = true;
            result.data = boardResponseList;
            return new ResponseEntity<>(result, HttpStatus.OK);

        }

        result.status = false;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* 공지사항 조회 */
    @Override
    public ResponseEntity<BoardBasicResponse> getBoard(Long id) {

        BoardBasicResponse result = new BoardBasicResponse();
        Optional<Board> boardOptional = boardJpaRepository.findById(id);

        if(boardOptional.isPresent()){

            Board board = boardOptional.get();

            // 조회수 증가 처리
            board.setViews(board.getViews()+1L);
            boardJpaRepository.save(board);

            BoardOneResult boardOne = new BoardOneResult();
            boardOne.setBoardId(board.getBoardId());
            boardOne.setTitle(board.getTitle());
            boardOne.setEmail(board.getUser().getEmail());
            boardOne.setContent(board.getContent());
            boardOne.setViews(board.getViews());
            boardOne.setCreatedDate(board.getCreatedDate());
            boardOne.setUpdatedDate(board.getModifiedDate());

            List<BoardCommentResponse> bCommentList = new ArrayList<>();
            for(BoardComment c : board.getBoardCommentList()){
                BoardCommentResponse bc = BoardCommentResponse.builder()
                        .commentId(c.getCommentId())
                        .content(c.getContent())
                        .email(c.getUser().getEmail())
                        .createdAt(c.getCreatedDate())
                        .updatedAt(c.getModifiedDate())
                        .build();
                bCommentList.add(bc);
            }

            boardOne.setComments(bCommentList);

            result.status = true;
            result.data = boardOne;
            return new ResponseEntity<>(result, HttpStatus.OK);

        }

        result.status = false;
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    /* 공지사항 등록 */
    @Override
    public ResponseEntity<BoardBasicResponse> insertBoard(BoardRequest boardInsertRequest) {

        BoardBasicResponse result = new BoardBasicResponse();
        Optional<User> userOptional = userJpaRepository.findByEmail(boardInsertRequest.getEmail());

        if(userOptional.isPresent()){

            Board board = Board.builder()
                    .title(boardInsertRequest.getTitle())
                    .content(boardInsertRequest.getContent())
                    .user(userOptional.get())
                    .views(0L)
                    .build();
            Board save = boardJpaRepository.save(board);

            BoardResponse br = BoardResponse.builder()
                    .boardId(save.getBoardId())
                    .title(save.getTitle())
                    .email(save.getUser().getEmail())
                    .views(save.getViews())
                    .createdAt(save.getCreatedDate())           // null에 대한 위험성?
                    .updatedAt(save.getModifiedDate())
                    .build();

            result.status = true;
            result.data = br;
            return new ResponseEntity<>(result, HttpStatus.OK);

        }

        result.status = false;
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    /* 공지사항 수정 */
    @Override
    public ResponseEntity<BoardBasicResponse> updateBoard(Long id, BoardUpdateRequest boardUpdateRequest) {

        BoardBasicResponse result = new BoardBasicResponse();
        Optional<Board> boardOptional = boardJpaRepository.findById(id);

        if(boardOptional.isPresent()){
            Board board = boardOptional.get();
            board.setBoardId(id);
            board.setTitle(boardUpdateRequest.getTitle());
            board.setContent(boardUpdateRequest.getContent());
            Board save = boardJpaRepository.save(board);

            BoardResponse br = BoardResponse.builder()
                    .boardId(save.getBoardId())
                    .title(save.getTitle())
                    .email(save.getUser().getEmail())        // user_pk?
                    .views(save.getViews())
                    .createdAt(save.getCreatedDate())       // null에 대한 위험성?
                    .updatedAt(save.getModifiedDate())
                    .build();

            result.status = true;
            result.data = br;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.status = false;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* 공지사항 삭제 */
    @Override
    public ResponseEntity<BoardBasicResponse> deleteBoard(Long id) {

        BoardBasicResponse result = new BoardBasicResponse();
        Optional<Board> boardOptional = boardJpaRepository.findById(id);

        boardCommentJpaRepository.deleteAllByBoard(boardOptional.get());
        boardJpaRepository.deleteById(id);

        if(!boardJpaRepository.findById(id).isPresent()){
            result.status = true;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else {
            result.status = false;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }

}