package kr.co.oslob.oslob.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class BoardController {

//    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> list(){


        return ResponseEntity.ok().body("haha");
    }


}
