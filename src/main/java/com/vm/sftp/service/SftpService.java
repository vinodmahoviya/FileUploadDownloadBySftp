package com.vm.sftp.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.vm.sftp.configuration.ApplicationConfiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SftpService {

	private final ApplicationConfiguration appConfig;

	public File fileDownloadFromSftp() {

		File file = null;
		Session session = null;
		ChannelSftp channelSftp = null;
		try {

			String tmpdir = System.getProperty("java.io.tmpdir");
			String seprator=File.separator;
			// System.getProperty("file.separator");
			//FileSystems.getDefault().getSeparator();
			String fileName="sftpFile.txt";
			String tempFile=tmpdir+seprator+"sftp"+seprator+fileName;
			
			Properties properties = new Properties();
			properties.put("StrictHostKeyChecking", "no");

			JSch jsch = new JSch();
			session = jsch.getSession(appConfig.getUser(), appConfig.getHost(), appConfig.getPort());
			session.setPassword(appConfig.getPassword());
			session.setConfig(properties);
			session.connect();

			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			// SFTP path, destination path
			channelSftp.get("/Contacts/VinodFile/testFile.txt", tempFile);

		} catch (JSchException e) {
			log.error("SFTP connection error : " + e.getMessage());
		} catch (SftpException e) {
			log.error("File upload exception : " + e.getMessage());
		} catch (Exception e) {
			log.error("File not file error : " + e.getMessage());
			e.printStackTrace();
		} finally {

			if (session != null && channelSftp != null) {
				channelSftp.disconnect();
			}
		}
		return file;

	}

	public void fileUploadToSftp(File file) {

		Session session = null;
		ChannelSftp channelSftp = null;
		try {
			Properties properties = new Properties();
			properties.put("StrictHostKeyChecking", "no");

			JSch jsch = new JSch();
			session = jsch.getSession(appConfig.getUser(), appConfig.getHost(), appConfig.getPort());
			session.setPassword(appConfig.getPassword());
			session.setConfig(properties);
			session.connect();

			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.put("/" + file.getName(), "/" + appConfig.getRemoteDirectory() + file.getName());

		} catch (JSchException e) {
			log.error("SFTP connection error : " + e.getMessage());
		} catch (SftpException e) {
			log.error("File upload exception : " + e.getMessage());
		} finally {

			if (session != null && channelSftp != null) {
				channelSftp.disconnect();
			}
		}
	}
}
