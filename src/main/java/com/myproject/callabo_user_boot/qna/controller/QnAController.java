package com.myproject.callabo_user_boot.qna.controller;

import com.myproject.callabo_user_boot.qna.dto.*;
import com.myproject.callabo_user_boot.qna.service.QnAService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api2/qna")
@RequiredArgsConstructor
@Log4j2
public class QnAController {

    private final QnAService qnAService;

    // qna 등록
    @PostMapping("/register")
    public ResponseEntity<Long> registerQna(@RequestBody QnARegisterDTO qnARegisterDTO) {

        log.info("QnA 등록 요청 : {}", qnARegisterDTO);

        Long qnaNo = qnAService.registerQnA(qnARegisterDTO);

        return ResponseEntity.ok(qnaNo);
    }

    // qna 리스트
    @GetMapping("/list")
    public ResponseEntity<List<QnAListDTO>> listQna(Long qnaNo) {

        return ResponseEntity.ok(qnAService.getAllQnA(qnaNo));
    }

    // qna 조회
    @GetMapping("/read/{qnaNo}")
    public ResponseEntity<QnAReadDTO> getQnaRead(@PathVariable("qnaNo") Long qnaNo) {
        log.info("QnA 조회 : qnaNo={}", qnaNo);

        QnAReadDTO qnAReadDTO = qnAService.readQnA(qnaNo);

        return ResponseEntity.ok(qnAReadDTO);
    }
}
