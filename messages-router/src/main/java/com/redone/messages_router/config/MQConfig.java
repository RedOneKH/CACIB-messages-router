package com.redone.messages_router.config;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.ErrorHandler;


@Configuration
@EnableJms
public class MQConfig {

    @Autowired
    private MQConfigurationProperties mqProperties;

    @Bean
    public ConnectionFactory mqConnectionFactory() {
        MQConnectionFactory connectionFactory = new MQConnectionFactory();
        try {
            connectionFactory.setHostName(mqProperties.getConnName().split("\\(")[0]);
            connectionFactory.setPort(Integer.parseInt(mqProperties.getConnName().split("\\(")[1].replace(")", "")));
            connectionFactory.setQueueManager(mqProperties.getQueueManager());
            connectionFactory.setChannel(mqProperties.getChannel());
            connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            connectionFactory.setCCSID(1208); // UTF-8

            // Explicitly set user credentials
            connectionFactory.setStringProperty(WMQConstants.USERID, mqProperties.getUser());
            connectionFactory.setStringProperty(WMQConstants.PASSWORD, mqProperties.getPassword());

            connectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
        } catch (Exception e) {
            throw new RuntimeException("Error creating MQConnectionFactory", e);
        }
        return connectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() throws JMSException {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setTargetConnectionFactory(mqConnectionFactory());
        factory.setSessionCacheSize(10);
        factory.setReconnectOnException(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setReceiveTimeout(mqProperties.getReceiveTimeout());
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setConcurrency("3-10");
        factory.setRecoveryInterval(5000L);
        factory.setErrorHandler(new MQErrorHandler());
        return factory;
    }

    private static class MQErrorHandler implements ErrorHandler {
        private static final Logger log = LoggerFactory.getLogger(MQErrorHandler.class);

        @Override
        public void handleError(Throwable t) {
            log.error("Erreur lors du traitement du message MQ", t);
        }
    }
}