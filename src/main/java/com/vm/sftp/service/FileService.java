package com.vm.sftp.service;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

	
	private final SftpService sftpService;
	

//	@Scheduled(cron = "${app.cron.expression}")
	public void FileUploadService() {
	
		log.info("File Upload to SFTP server");
		sftpService.fileUploadToSftp(new File("/testFile.txt"));
		log.info("File Successfully uploaded to SFTP server");
	}
	
//	@Scheduled(cron = "${app.cron.expression}")
	public void FileDownloadService() {
	
		log.info("File download to SFTP server");
		sftpService.fileDownloadFromSftp();
		log.info("File Successfully downloaded from SFTP server");
	}
}
