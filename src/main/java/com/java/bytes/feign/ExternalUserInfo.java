package com.java.bytes.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "userInfo", url = "https://jsonplaceholder.typicode.com/")

public interface ExternalUserInfo {


	@RequestMapping(method = RequestMethod.GET, value = "/users/{userId}", produces = "application/json")
	User getUserId(@PathVariable("userId") int userId);
}