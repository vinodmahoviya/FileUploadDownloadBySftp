package com.vm.sftp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.Getter;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.sftp")
public class ApplicationConfiguration {

	private String host;
	private int port;
	private String user;
	private String password;
	private String 	remoteDirectory;
	
}
