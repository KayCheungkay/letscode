package org.springframework.beanload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Created by nuc on 2015/11/25.
 */
@Service
public class LoadBeans implements ApplicationListener<ApplicationEvent> {
    @Autowired
    private Superclass sp;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        sp.display();
    }
}
