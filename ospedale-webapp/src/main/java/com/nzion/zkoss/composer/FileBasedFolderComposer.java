package com.nzion.zkoss.composer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;

import com.nzion.domain.File;
import com.nzion.domain.Folder;
import com.nzion.domain.Patient;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.impl.FileBasedServiceImpl;

public class FileBasedFolderComposer extends OspedaleAutowirableComposer {

	private FileBasedServiceImpl fileBasedServiceImpl;
	private Tree folderlist;
	private Folder selectedFolder;
	private Patient patient;
	private CommonCrudService commonCrudService;
	private TreeModel treeModel;
	@Override
	public void doAfterCompose(Component component) throws Exception {
	super.doAfterCompose(component);
	this.patient = (Patient) Executions.getCurrent().getArg().get("patient");
	}

	public List<DefaultTreeNode> createDefaultTreeNode(Folder folder) {
	ArrayList<DefaultTreeNode> children = new ArrayList<DefaultTreeNode>();
	for (Folder f : fileBasedServiceImpl.getSubFolders(folder)) {
		children.add(new DefaultTreeNode(f, createDefaultTreeNode(f)));
	}
	return children;
	}

	public Set<Folder> getFileList() {
	if (selectedFolder == null) return null;
	Folder f = commonCrudService.getById(Folder.class, selectedFolder.getId());
	return f.getChildFolders();
	}

	public FileBasedServiceImpl getFileBasedServiceImpl() {
	return fileBasedServiceImpl;
	}

	public void setFileBasedServiceImpl(FileBasedServiceImpl fileBasedServiceImpl) {
	this.fileBasedServiceImpl = fileBasedServiceImpl;
	}

	public Tree getFolderlist() {
	return folderlist;
	}

	public void setFolderlist(Tree folderlist) {
	this.folderlist = folderlist;
	}

	public Folder getSelectedFolder() {
	return selectedFolder;
	}

	public void setSelectedFolder(Folder selectedFolder) {
	this.selectedFolder = selectedFolder;
	}

	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	public CommonCrudService getCommonCrudService() {
	return commonCrudService;
	}

	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	public DefaultTreeModel getTreeModel(){
	Set<Folder>folders = fileBasedServiceImpl.getRootFolders(patient);
	List<DefaultTreeNode> treeNodes = new ArrayList<DefaultTreeNode>();
	for (Folder folder : folders) {
		treeNodes.add(new DefaultTreeNode(folder, createDefaultTreeNode(folder)));
	}
	treeModel = new DefaultTreeModel(new DefaultTreeNode("/", treeNodes));
	return (DefaultTreeModel) treeModel;
	}

	public void setTreeModel(DefaultTreeModel treeModel) {
	this.treeModel = treeModel;
	}

	public void createFolder(Folder folder) {
	folder.setPatient(this.getPatient());
	if (getSelectedFolder() == null) {
		selectedFolder = fileBasedServiceImpl.getRootFolder(patient);
	}
	folder.setParentFolder(this.getSelectedFolder());
	fileBasedServiceImpl.createFolder(folder);
	}
	
	public void deleteFolder() {
	if(this.selectedFolder!=null) {
		fileBasedServiceImpl.deleteFolder(selectedFolder);
	}
	}
	
	public void createDocument(File file) {
	if(selectedFolder!=null) {
		file.setFolder(selectedFolder);
		fileBasedServiceImpl.createFile(file);
	}
	selectedFolder.getFiles().add(file);
	}
	
	public void deleteDocument(File file) {
	fileBasedServiceImpl.deleteFile(file);
	}
	
	private static final long serialVersionUID = 1L;
}
