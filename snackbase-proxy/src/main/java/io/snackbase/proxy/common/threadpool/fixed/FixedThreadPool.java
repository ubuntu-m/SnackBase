/*
 * Copyright (C) 2016 alchemystar, Inc. All Rights Reserved.
 */
package io.snackbase.proxy.common.threadpool.fixed;

import alchemystar.lancelot.common.threadpool.AbortPolicyWithReport;
import alchemystar.lancelot.common.threadpool.AbstractThreadPool;
import alchemystar.lancelot.common.threadpool.NamedThreadFactory;
import alchemystar.lancelot.common.threadpool.config.ThreadPoolConfig;

import java.util.concurrent.*;

/**
 * 此线程池启动时即创建固定大小的线程数，不做任何伸缩
 *
 * @Author lizhuyang
 */
public class FixedThreadPool extends AbstractThreadPool {

    @Override
    public Executor getExecutor(ThreadPoolConfig config) {
        return getExecutor(config.getName(), config.getCoreSize(), config.getQueues());
    }

    public Executor getExecutor(String name, int threadSize, int queues) {

        return new ThreadPoolExecutor(threadSize, threadSize, 0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>() :
                        (queues < 0 ? new LinkedBlockingQueue<Runnable>()
                                 : new LinkedBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }

}