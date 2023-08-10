package kr.co.oslob.oslob.reply.controller;

import kr.co.oslob.oslob.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oslob/reply")
@Slf4j
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;
}
