package com.lmxdawn.other.service;

import com.lmxdawn.other.req.QuestionHotListPageReq;
import com.lmxdawn.other.res.QuestionHotListInfoRes;

import java.util.List;

public interface QuestionHotService {

    List<QuestionHotListInfoRes> listPage(QuestionHotListPageReq req);

}
