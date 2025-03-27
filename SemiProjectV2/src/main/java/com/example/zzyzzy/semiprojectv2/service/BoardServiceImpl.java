package com.example.zzyzzy.semiprojectv2.service;

import com.example.zzyzzy.semiprojectv2.domain.Board;
import com.example.zzyzzy.semiprojectv2.domain.BoardDTO;
import com.example.zzyzzy.semiprojectv2.domain.BoardListDTO;
import com.example.zzyzzy.semiprojectv2.domain.User;
import com.example.zzyzzy.semiprojectv2.repository.BoardRepository;
import com.example.zzyzzy.semiprojectv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    @Value("${board.pagesize}") private int pageSize;

    @Override
    public Board newBoard(Board board) {

        return boardRepository.save(board);
    }

    @Override
    public BoardListDTO readBoard(int cpg) {
        Pageable pageable = PageRequest.of(cpg, pageSize, Sort.Direction.DESC, "bno");

        Page<BoardDTO> pageboards = boardRepository.findBy(pageable);
        List<BoardDTO> boards = pageboards.getContent();
        int totalItems = (int) pageboards.getTotalElements();
        int cntpg = pageboards.getTotalPages();

        return new BoardListDTO(cpg, totalItems, pageSize, boards);
    }

}
