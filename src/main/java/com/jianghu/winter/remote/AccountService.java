package com.jianghu.winter.remote;

import com.jianghu.winter.common.PageResponse;
import com.jianghu.winter.common.RemoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author daniel.hu
 * @date 2019/6/21 14:34
 */
@FeignClient(value = "SERVICE-NAME")
public interface AccountService {

    @RequestMapping(value = "/query/language", method = RequestMethod.POST)
    RemoteResponse<PageResponse<LanguageResponse>> query(LanguageRequest request);
}
