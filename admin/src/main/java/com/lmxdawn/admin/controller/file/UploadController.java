package com.lmxdawn.admin.controller.file;

import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.service.EchoDubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传相关
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/file/upload")
public class UploadController {

    @DubboReference
    private EchoDubboService echoDubboService;

    /**
     * 上传的token
     * @return
     */
    @ApiOperation(value = "获取七牛云上传token")
    @GetMapping("/qiuNiuUpToken")
    public BaseRes qiuNiuUpToken() {

        // TODO 这里接入 七牛云 的SDK 就可以了
        Map<String, Object> res = new HashMap<>();
        res.put("uploadUrl", "/admin/file/upload/createFile"); // 这里可以直接设置成七牛云的上传 url，不用服务端这边去post请求七牛云的上传接口
        res.put("upToken", "xxxxxxx");

        return ResultVOUtils.success(res);
    }

    /**
     * 上传文件（如果是接入的第三方的建议这个接口废弃）
     * @return
     */
    @ApiOperation(value = "上传文件到当前服务器")
    @PostMapping("/createFile")
    public BaseRes createFile() {

        // TODO 这里做上传文件的逻辑，返回文件的 key （也就是路径）

        Map<String, Object> res = new HashMap<>();
        res.put("key", "xxxx.jpg");
        return ResultVOUtils.success(res);
    }

    /**
     * 上传文件（如果是接入的第三方的建议这个接口废弃）
     * @return
     */
    @ApiOperation(value = "测试dubbo")
    @PostMapping("/test")
    public BaseRes test() {

        String test = echoDubboService.echo("test");

        return ResultVOUtils.success(test);
    }

}
