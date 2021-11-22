package com.lmxdawn.admin.service.auth;

import com.lmxdawn.admin.entity.auth.AuthAdmin;
import com.lmxdawn.admin.req.auth.AuthAdminQueryReq;

import java.util.List;

public interface AuthAdminService {

    List<AuthAdmin> listAdminPage(AuthAdminQueryReq authAdminQueryRequest);

    AuthAdmin findByUserName(String userName);


    AuthAdmin findById(Long id);


    AuthAdmin findPwdById(Long id);

    boolean insertAuthAdmin(AuthAdmin authAdmin);

    boolean updateAuthAdmin(AuthAdmin authAdmin);

    boolean deleteById(Long id);

}
