package com.lmxdawn.market.service;

public interface LoginService {
    String createToken(Long uid);
    Long verify(String token);
    void delete(Long uid);
}
