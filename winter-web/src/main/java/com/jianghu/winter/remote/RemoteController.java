package com.jianghu.winter.remote;

import com.jianghu.winter.config.remote.PageResponse;
import com.jianghu.winter.config.remote.RemoteResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.annotation.Resource;

/**
 * @author daniel.hu
 * @date 2019/6/21 14:41
 */
@Api(tags = "remote")
@RestController
public class RemoteController {
    @Resource
    AccountService accountService;

    @GetMapping("/language")
    public List<LanguageResponse> findTenantLanguages() {
        LanguageRequest request = new LanguageRequest();
        request.setTenantCode("880001");
        RemoteResponse<PageResponse<LanguageResponse>> response = accountService.query(request);
        return response.getData().getList();
    }
}
