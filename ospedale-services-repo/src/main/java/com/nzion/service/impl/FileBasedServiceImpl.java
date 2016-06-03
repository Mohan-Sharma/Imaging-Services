package com.nzion.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nzion.domain.Folder;
import com.nzion.domain.Patient;
import com.nzion.domain.Practice;
import com.nzion.domain.docmgmt.FolderCategory;
import com.nzion.service.CategoryService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("fileBasedServiceImpl")
public class FileBasedServiceImpl {

	private CategoryService categoryService;
	private CommonCrudService commonCrudService;
	private File baseDirectory;
	private File workspace;

	public void setPractice(Practice practice) {
	String rootFolder = System.getProperty("drkare.docmgmt.root");
	baseDirectory = new File(rootFolder);
	workspace = new File(baseDirectory, practice.getPracticeName());
	if (!baseDirectory.exists()) {
		baseDirectory.mkdirs();
	}
	}

	public boolean createDefaultFolderStructure(Patient patient) {
	Folder patientHomeFolder = new Folder();
	patientHomeFolder.setPatient(patient);
	patientHomeFolder.setDescription("Root Folder for " + patient.getFirstName());
	patientHomeFolder.setName(getPatientHome(patient));
	patientHomeFolder.setPatient(patient);
	File patientHome = createFolder(patientHomeFolder, workspace);
	List<FolderCategory> categories = categoryService.getChildCategories(null);
	for (FolderCategory each : categories) {
		createFoldersForCategory(patient, each, patientHomeFolder, patientHome);
	}
	return true;
	}

	private String getPatientHome(Patient patient) {
	return patient.getFirstName() + "_" + patient.getLastName() + "_" + patient.getAccountNumber();
	}

	private void createFoldersForCategory(Patient patient, FolderCategory category, Folder parentFolder, File parent) {
	Folder folder = new Folder();
	folder.setName(category.getName());
	folder.setDescription(category.getDescription());
	folder.setParentFolder(parentFolder);
	folder.setPatient(patient);
	folder.setSystemFolder(true);
	File f = createFolder(folder, parent);
	for (FolderCategory each : category.getChildren()) {
		createFoldersForCategory(patient, each, folder, f);
	}
	}

	public File createFolder(Folder folder, File parent) {
	File f = null;
	if (parent != null) {
		f = new File(parent, escapeFileName(folder.getName()));
	} else {
		f = new File(workspace, escapeFileName(folder.getName()));
	}
	boolean result = f.mkdirs();
	if (result) {
		folder.setPath(f.getAbsolutePath());
		commonCrudService.save(folder);
	}
	f.setReadable(true);
	f.setWritable(true);
	f.setExecutable(true, true);
	return f;
	}

	public CommonCrudService getCommonCrudService() {
	return commonCrudService;
	}

	@Resource
	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	public CategoryService getCategoryService() {
	return categoryService;
	}

	@Resource
	public void setCategoryService(CategoryService categoryService) {
	this.categoryService = categoryService;
	}

	public Set<Folder> getRootFolders(Patient patient) {
	return commonCrudService.getRootFolder(patient).getChildFolders();
	}

	public Set<Folder> getSubFolders(Folder folder) {
	return commonCrudService.getById(Folder.class, folder.getId()).getChildFolders();
	}

	public Folder getRootFolder(Patient patient) {
	return commonCrudService.getRootFolder(patient);
	}

	public void createFolder(Folder folder) {
	File parent = new File(folder.getParentFolder().getPath());
	createFolder(folder, parent);
	}

	public void deleteFolder(Folder folder) {
	File file = new File(folder.getPath());
	if (file.exists()) {
		if(file.isDirectory()) {
			for(File f :file.listFiles()) {
				f.delete();
			}
		}
		boolean result = file.delete();
		if (result) commonCrudService.delete(folder);
	}
	}

	public void createFile(com.nzion.domain.File file) {
	File newFile = new File(file.getFolder().getPath(), escapeFileName(file.getFileName()));
	file.setFilePath(newFile.getAbsolutePath());
	try {
		FileOutputStream fos = new FileOutputStream(newFile, false);
		IOUtils.copy(file.getInputStream(), fos);
		fos.flush();
		IOUtils.closeQuietly(fos);
		commonCrudService.save(file);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	
	public void createFileForPatientEducation(com.nzion.domain.File file,String folderName){
	String rootFolder = System.getProperty("drkare.docmgmt.root");
	baseDirectory = new File(rootFolder);
	workspace = new File(baseDirectory, "webuploads");
	File subFolder = new File(workspace,folderName);
	if (!baseDirectory.exists()){ 
		baseDirectory.mkdirs();
	}	
	if(!workspace.exists())
		workspace.mkdir();
	if(!subFolder.exists())
		subFolder.mkdir();
	Folder folder = new Folder();
	folder.setPath(subFolder.getPath());
	file.setFolder(folder);
	commonCrudService.save(folder);
	createFile(file);
	}
	
	public void createFilesForImageSection(com.nzion.domain.File imageFile,Patient patient){
		Folder patientFolder = commonCrudService.getRootFolder(patient);
		File imageFolder = new File(patientFolder.getPath(),"Images");
		if(!imageFolder.exists()){
			imageFolder.mkdir();
		}	
		Folder folder = new Folder();
		folder.setPath(imageFolder.getPath());
		imageFile.setFolder(folder);
		folder.setParentFolder(patientFolder);
		folder.setName("Images");
		commonCrudService.save(folder);
		createFile(imageFile);
	}
	
	private String escapeFileName(String fileName) {
	return StringUtils.replace(fileName, " ", "_");
	}

	public void deleteFile(com.nzion.domain.File file) {
	File f = new File(file.getFilePath());
	if (f.delete()) commonCrudService.delete(file);
	}
}
