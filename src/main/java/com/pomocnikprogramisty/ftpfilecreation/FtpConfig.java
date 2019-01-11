package com.pomocnikprogramisty.ftpfilecreation;

import com.google.common.base.Preconditions;
import com.jcraft.jsch.ChannelSftp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.MessageHandler;

import java.io.InputStream;
import java.time.LocalDate;

@Configuration
class FtpConfig {

    @Bean
    SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
        final DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("ftp.pomocnikprogramisty.com");
        factory.setPort(22);
        factory.setUser("username");
        factory.setPassword("pass");
        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<>(factory);
    }

    @Bean
    @ServiceActivator(inputChannel = "toSftpChannel")
    MessageHandler handler() {
        final SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression("/upload/"));
        handler.setFileNameGenerator(message -> {
            Preconditions.checkArgument(message.getPayload() instanceof InputStream);
            return "myfile" + LocalDate.now() + ".csv";

        });
        return handler;
    }

    @MessagingGateway
    public interface StreamingGateway {

        @Gateway(requestChannel = "toSftpChannel")
        void write(final InputStream is);
    }

}
