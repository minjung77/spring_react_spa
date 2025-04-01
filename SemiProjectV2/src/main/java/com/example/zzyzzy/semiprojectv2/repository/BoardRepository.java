package com.example.zzyzzy.semiprojectv2.repository;

import com.example.zzyzzy.semiprojectv2.domain.Board;
import com.example.zzyzzy.semiprojectv2.domain.BoardDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    
    //연관매핑만으로 데이터 조회
    //Board findByBno(Long bno);

    //join조차도 자동 생성
//    @EntityGraph(attributePaths = {"replies"})
//    Board findByBno(Long bno);

    @Modifying
    @Query("update Board set views = views + 1 where bno = :bno")
    int updateViews(@Param("bno") Long bno);

    //연관매핑 + outer join으로 데이터 조회
    //본문글과 댓글 조회를 위해 outer join 사용
    //즉, 본문글만 있고, 댓글이 없는 경우에도 출력하기 위해 사용
    @Query("select b from Board b left join fetch b.replies where b.bno = :bno")
    Board findByBno(@Param("bno") Long bno);

    //@EntityGraph(attributePaths = {"reply"})
    //Board findByBno(Long bno);
}
