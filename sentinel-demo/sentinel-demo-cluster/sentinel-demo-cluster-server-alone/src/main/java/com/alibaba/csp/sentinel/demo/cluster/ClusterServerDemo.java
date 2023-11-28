/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.demo.cluster;

import java.util.*;

import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;

/**
 * <p>Cluster server demo (alone mode).</p>
 * <p>Here we init the cluster server dynamic data sources in
 * {@link com.alibaba.csp.sentinel.demo.cluster.init.DemoClusterServerInitFunc}.</p>
 *
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ClusterServerDemo {
	
    static {
        System.setProperty("csp.sentinel.dashboard.server","localhost:8080");//控制台地址
        System.setProperty("csp.sentinel.api.port","8719"); //sentinel端口
        System.setProperty("project.name","token-server"); //服务名称
        System.setProperty("csp.sentinel.log.use.pid","true"); //设置pid(可选)
    }

    public static void main(String[] args) throws Exception {
        // Not embedded mode by default (alone mode).
        ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();

        // A sample for manually load config for cluster server.
        // It's recommended to use dynamic data source to cluster manage config and rules.
        // See the sample in DemoClusterServerInitFunc for detail.
        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig()
            .setIdleSeconds(600)
            .setPort(11111));
        
        Set<String> serverNamespaceSet = new HashSet<>(Arrays.asList(DemoConstants.MICRO_SERVICES.split(",")));
        ClusterServerConfigManager.loadServerNamespaceSet(serverNamespaceSet);

        // Start the server.
        tokenServer.start();
    }
}
