package com.ssafy.edu.service.board;

import com.ssafy.edu.model.board.*;
import org.springframework.http.ResponseEntity;

public interface BoardService {

    public ResponseEntity<BoardBasicResponse> getBoardList();
    public ResponseEntity<BoardBasicResponse> insertBoard(BoardRequest boardInsertRequest);
    public ResponseEntity<BoardBasicResponse> getBoard(Long id);
    public ResponseEntity<BoardBasicResponse> updateBoard(Long id, BoardUpdateRequest boardUpdateRequest);
    public ResponseEntity<BoardBasicResponse> deleteBoard(Long id);

}
