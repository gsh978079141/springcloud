package com.codingapi.tm.listener;

import com.codingapi.tm.Constants;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ApplicationStartListener implements ApplicationListener<ApplicationEvent> {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //TODO Spring boot 2.0.0没有EmbeddedServletContainerInitializedEvent 此处写死;modify by young
//        int serverPort = event.getEmbeddedServletContainer().getPort();
        String ip = getIp();
        Constants.address = ip+":48888";//写死端口号，反正TxManager端口也是配置文件配好的(●′ω`●)
    }

    private String getIp(){
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }
}