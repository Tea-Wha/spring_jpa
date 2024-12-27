package com.kh.spring_jpa241217.service;

import com.kh.spring_jpa241217.entity.Board;
import com.kh.spring_jpa241217.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public String save(String boardName) {
        Board board = new Board();
        board.setName(boardName);
        boardRepository.save(board);
        return board.getName();
    }

    public void test() {
        Board targetBoard = boardRepository.findByName("자유게시판").orElseThrow(() -> new NoSuchElementException("자유게시판 not found"));
        targetBoard.getPosts().clear();
    }

    public Board getByName(String boardName) {
        return boardRepository.findByName(boardName).orElseThrow(() -> new NoSuchElementException("해당 게시판은 존재하지 않습니다."));
    }
}
