package org.o7planning.load_ds_wl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BLTZFILE")
public class FileEntity {
	private String name;
	private String dir;
	private String status;
	private String dateUpload;
	private String userUpload;
	private String full;
	private int id;
	private String userAprrove;
	private String commentFile;
	private String mailTo;

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

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	


	@Column(name = "DATE_UPLOAD")
	public String getDateUpload() {
		return dateUpload;
	}

	public void setDateUpload(String dateUpload) {
		this.dateUpload = dateUpload;
	}

	@Column(name = "USER_UPLOAD")
	public String getUserUpload() {
		return userUpload;
	}

	public void setUserUpload(String userUpload) {
		this.userUpload = userUpload;
	}
	@Column(name = "COMMENT_FILE")

	public String getCommentFile() {
		return commentFile;
	}

	public void setCommentFile(String commentFile) {
		this.commentFile = commentFile;
	}
	
	@Column(name = "USER_APPROVE")
	public String getUserAprrove() {
		return userAprrove;
	}

	public void setUserAprrove(String userAprrove) {
		this.userAprrove = userAprrove;
	}

	@Column(name = "FULL")
	public String getFull() {
		return full;
	}

	public void setFull(String full) {
		this.full = full;
	}

	@Column(name = "MAIL")
	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

}
