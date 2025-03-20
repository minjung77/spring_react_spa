package com.example.zzyzzy.semiprojectv2.controller;

import com.example.zzyzzy.semiprojectv2.domain.Board;
import com.example.zzyzzy.semiprojectv2.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins="http://localhost:5173")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<?> writeok(@RequestBody Board board) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();

        log.info("submit 된 게시판 데이터 : {}", board);

        try{
            boardService.newBoard(board);
            response = ResponseEntity.ok().build();
        }catch (IllegalArgumentException e){
            response = ResponseEntity.badRequest().body(e.getMessage());
        }

        return response;
    }
}
