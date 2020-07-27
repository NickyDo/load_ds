package org.o7planning.load_ds_wl.entity;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "BLTZFILE")
public class FileEntity {
	private String name;
	private String dir;
	private String deleteUrl;
	private String deleteType;
	private String thumbnailUrl;
	private String full;
	private int id;
	private String fileType;
	private String size;


	@Id
	@Column(name = "ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "DIR")
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	@Column(name = "DELETEURL")
	public String getDeleteUrl() {
		return deleteUrl;
	}
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	
	@Column(name = "DELETETYPE")
	public String getDeleteType() {
		return deleteType;
	}
	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}
	
	@Column(name = "THUMBNAILURL")
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnail) {
		this.thumbnailUrl = thumbnail;
	}
	
	@Column(name = "FULL")
	public String getFull() {
		return full;
	}
	public void setFull(String full) {
		this.full = full;
	}
	
	
	@Column(name = "TYPEFILE")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Column(name = "SIZEFILE")
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	

	
	
}
