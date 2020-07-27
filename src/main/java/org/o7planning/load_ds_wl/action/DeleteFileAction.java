package org.o7planning.load_ds_wl.action;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class DeleteFileAction extends ActionSupport {

	public String execute() throws FileNotFoundException {
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.print("delete idName:" + request.getParameter("idName"));
		String pathFile = "D:/Test/Upload/" + request.getParameter("idName");
		 try
	        { 
	            Files.deleteIfExists(Paths.get(pathFile)); 
	        } 
	        catch(NoSuchFileException e) 
	        { 
	            System.out.println("No such file/directory exists"); 
	        } 
	        catch(DirectoryNotEmptyException e) 
	        { 
	            System.out.println("Directory is not empty."); 
	        } 
	        catch(IOException e) 
	        { 
	            System.out.println("Invalid permissions."); 
	        } 
	          
	        System.out.println("Deletion successful."); 

		return SUCCESS;
	}

}
