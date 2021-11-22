package com.lmxdawn.admin;

import com.lmxdawn.admin.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

class AdminApplicationTest {

    @Test
    void contextLoads() {
        // 验证 token
        Claims claims = JwtUtils.parse("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhZG1pbl9pZCI6MSwiZXhwIjoxNjMwNzMwNzAzfQ.00nXEYtMhshr_5RDNlpKLhWn310hNiedjCxNJrFwKX8");
        long l = Long.parseLong(claims.get("admin_id").toString());
        System.out.println(l);
    }
}