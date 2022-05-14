package com.lmxdawn.user.service;

public interface LoginService {
    String createToken(Long memberId);
    Long verify(String token);
    void delete(Long memberId);
}
