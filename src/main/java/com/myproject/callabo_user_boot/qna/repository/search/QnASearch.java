package com.myproject.callabo_user_boot.qna.repository.search;

import com.myproject.callabo_user_boot.qna.dto.QnAListDTO;

import java.util.List;

public interface QnASearch {

    List<QnAListDTO> QnAList(Long qnaNo);
}
