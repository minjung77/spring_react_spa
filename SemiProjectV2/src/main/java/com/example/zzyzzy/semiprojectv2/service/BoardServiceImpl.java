package com.example.zzyzzy.semiprojectv2.service;

import com.example.zzyzzy.semiprojectv2.domain.Board;
import com.example.zzyzzy.semiprojectv2.domain.User;
import com.example.zzyzzy.semiprojectv2.repository.BoardRepository;
import com.example.zzyzzy.semiprojectv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Board newBoard(Board board) {

        return boardRepository.save(board);
    }

}
