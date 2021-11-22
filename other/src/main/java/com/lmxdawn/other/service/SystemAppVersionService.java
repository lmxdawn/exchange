package com.lmxdawn.other.service;

import com.lmxdawn.other.req.SystemAppVersionReq;
import com.lmxdawn.other.res.SystemAppVersionInfoRes;


public interface SystemAppVersionService {

    SystemAppVersionInfoRes update(SystemAppVersionReq req);

}
