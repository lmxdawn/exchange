package com.lmxdawn.trade.service;

public interface LoginService {
    String createToken(Long uid);
    Long verify(String token);
    void delete(Long uid);
}
