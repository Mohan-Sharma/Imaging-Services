package com.nzion.util;

import com.nzion.domain.File;
import com.nzion.ext.FileEntry;
import com.nzion.ext.Method;
import com.nzion.ext.ZIPFile;
import org.apache.commons.io.IOUtils;
import org.zkoss.zul.Filedownload;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class IoAndFileUtil {
	
	public static void downloadFile(File file) {
	String mimeType = file.getFileType();
	java.io.FileInputStream is = null;
	try {
		is = new java.io.FileInputStream(file.getFilePath());
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	downloadFile(is, file.getFileName(), mimeType);
	}

	public static void downloadFile(FileInputStream fileInputStream, String fileName, String mimeType) {
	
	try {
		org.zkoss.util.media.AMedia media = new org.zkoss.util.media.AMedia(fileName, "", mimeType, fileInputStream);
		Filedownload.save(media);
	} catch (Throwable t) {
		t.printStackTrace();
	}
	}
	
	public static void downloadFile(Reader reader, String fileName, String mimeType) {
	try {
		Filedownload.save(reader, mimeType, fileName);
	} catch (Throwable t) {
		t.printStackTrace();
	}
	}
	
	public static String getStackTrace(Throwable t, StringBuilder builder) {
	StackTraceElement[] trace = t.getStackTrace();
	for (int i = 0; i < trace.length; i++)
		builder.append(trace[i]);
	Throwable ourCause = t.getCause();
	return ourCause == null ? builder.toString() : getStackTrace(ourCause, builder);
	}
	
	public static void downloadZipFile(String zipFileName, Collection<ContentStringAndFileName> contents) throws IOException {
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	ZipOutputStream zipFile = new ZipOutputStream(outputStream);
	for (ContentStringAndFileName content : contents) {
		ZipEntry zipEntry = new ZipEntry(content.fileName);
		zipFile.putNextEntry(zipEntry);
		zipFile.write(content.contentString.getBytes());
		zipFile.closeEntry();
	}
	zipFile.finish();
	zipFile.flush();
	zipFile.close();
	Filedownload.save(outputStream.toByteArray(), "application/x-zip", zipFileName);
	}

	public static void downloadZipFile(String zipFileName, List<File> files) throws IOException {
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	ZipOutputStream zipFile = new ZipOutputStream(outputStream);
	Set<String> filenames = new HashSet<String>();
	for (File file : files) {
		String filename = getFileName(filenames, file.getFileName(), file.getFileName(), 1);
		filenames.add(filename);
		ZipEntry zipEntry = new ZipEntry(filename);
		zipFile.putNextEntry(zipEntry);
		java.io.FileInputStream fis  = new FileInputStream(new java.io.File(file.getFilePath())); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
		IOUtils.copy(fis, baos);
		IOUtils.closeQuietly(fis);
		zipFile.write(baos.toByteArray());
		zipFile.closeEntry();
		IOUtils.closeQuietly(baos);
	}
	zipFile.finish();
	zipFile.flush();
	zipFile.close();
	Filedownload.save(outputStream.toByteArray(), "application/x-zip", zipFileName);
	}

	public static String getFileName(Set<String> taken, String original, String filename, int counter) {
	if (!taken.contains(filename)) return filename;
	String enhance = "(" + (counter++) + ")";
	if (original.lastIndexOf('.') == -1) return getFileName(taken, original, original + enhance, counter);
	String originalNames[] = original.split("\\.");
	return getFileName(taken, original, originalNames[0] + enhance + "." + originalNames[1], counter);
	}

	public static void downloadZipFileWithPassword(String zipFileName, String password, Collection<java.io.File> files) throws IOException {
	ZIPFile zipFile = new ZIPFile(zipFileName);
	Method meth = new Method();
	meth.setMethodValue(Method.Deflated);
	for (java.io.File file : files)
		zipFile.AddFile(UtilValidator.isNotEmpty(password) ? new FileEntry(file.getName(), password, meth) : new FileEntry(file.getName(), meth));
	zipFile.CreateZIP();
	java.io.FileInputStream is = new java.io.FileInputStream(zipFile.getFilePath());
	downloadFile(is, zipFileName, "application/x-zip");
	}
	
	public static class ContentStringAndFileName { 
		
		public String contentString;
		
		public String fileName;

		public ContentStringAndFileName(String contentString, String fileName) {
		this.contentString = contentString;
		this.fileName = fileName;
		}
	}
}