package com.lxl.lincore.auth.schedule.job;

import com.lxl.lincore.auth.token.util.AuthRedisHandler;

import java.util.Date;
import java.util.UUID;

public class ClearInvalidTokenTask implements Runnable {

	@Override
	public void run() {
		String random = UUID.randomUUID().toString();
		System.out.println("-----ClearInvalidTokenTask:执行开始时间：" + random + ":" + new Date());
		
		int count = AuthRedisHandler.clearInvalidAccessToken();
		
		System.out.println("-----ClearInvalidTokenTask:执行结束时间" + random + ":" + new Date() +"执行成功条数：" + count);
	}

}
