package com.example.zzyzzy.semiprojectv2.repository;

import com.example.zzyzzy.semiprojectv2.domain.Board;
import com.example.zzyzzy.semiprojectv2.domain.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //@Query(value = "select bno, title, userid, regdate, thumbs, views from boards order by bno desc limit :stnum, :pageSize",
    //       nativeQuery = true)
    //List<BoardDTO> findBoards(int stnum, int pageSize);

    Page<BoardDTO> findBy(Pageable pageable);

    Page<BoardDTO> findByTitleContains(Pageable pageable, String findkey);
    Page<BoardDTO> findByUseridContains(Pageable pageable, String findkey);
    Page<BoardDTO> findByContentsContains(Pageable pageable, String findkey);

    Page<BoardDTO> findByTitleContainsOrContentsContains(Pageable pageable, String fkey1, String fkey2);

    Board findByBno(Long bno);

}
