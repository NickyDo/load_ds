package org.o7planning.load_ds_wl.action;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.hibernate.Session;

//import vn.bidv.report.HibernateUtil;
import org.o7planning.load_ds_wl.entity.UserEntity;
import org.o7planning.load_ds_wl.services.UserService;

public class BlitzAuthencation extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9027147210833243640L;
	private String userName;
	private String password;
	private String message;
	private String userType;
	private String hub;

	public String loginExtern() throws Exception {
		String strResult;
		boolean final_result = false;
		if (!password.isEmpty() && !userName.isEmpty()) {
//				final_result = checkDomain(userName, password);
				final_result = true;
			if (final_result) {
				ActionContext.getContext().getSession().put("userName", userName);
				ActionContext.getContext().getSession().put("hub", hub);
//				Session session = HibernateUtil.getSessionFactory().openSession();
				ActionContext.getContext().getSession().put("userName", getUserName());
				strResult = "LoginSuccess";
				userType = "S";
				ActionContext.getContext().getSession().put("userType", getUserType());
//				session.close();

			} else {
				setMessage("Tên hub, Tên đăng nhập hoặc mật khẩu không đúng.");
				strResult = "LoginErr";
			}
		} else {
			setMessage("Tên đăng nhập hoặc mật khẩu không được để trống");
			strResult = "LoginErr";
		}
		return strResult;
	}

	// @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	// private boolean checkLDAP(String user, String password, String domain,
	// String host, String port) {
	//
	// DirContext ctx;
	// try {
	// if(user.equals("DEMO")){
	// return true;
	// }else{
	// user = user.replace("@indovinabank.com.vn", "");
	// Hashtable ldapEnv = new Hashtable();
	//
	// ldapEnv.put("java.naming.factory.initial",
	// "com.sun.jndi.ldap.LdapCtxFactory");
	// ldapEnv.put("java.naming.provider.url", "ldap://" + host + ":" + port);
	// ldapEnv.put("java.naming.security.authentication", "simple");
	// ldapEnv.put("java.naming.security.principal", "cn="+ user + "," + domain
	// );
	// ldapEnv.put("java.naming.security.credentials", password);
	//
	// ctx = new InitialDirContext(ldapEnv);
	// return true;
	// }
	//
	// } catch (Exception e) {
	// log.error(user, "checkLDAP", e);
	// return false;
	// }
	// }

	private boolean checkLDAP(String user, String password, String domain, String host, String port) {
		DirContext ctx;
		try {

			Hashtable ldapEnv = new Hashtable(11);
			ldapEnv.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put("java.naming.provider.url", "ldap://" + host + ":" + port);
			ldapEnv.put("java.naming.security.authentication", "simple");
			ldapEnv.put("java.naming.security.principal", "cn=" + user + "," + domain);
			// ldapEnv.put("java.naming.security.principal", domain +user);
			ldapEnv.put("java.naming.security.credentials", password);

			System.out.println("*********************ldapEnv: " + ldapEnv);
			ctx = new InitialDirContext(ldapEnv);
		} catch (Exception e) {
			System.out.println("*******************Check LDAP Error: " + e.getMessage());

			return false;
		}
		return true;
	}

	boolean checkDomain(String username, String password) {

		try {
			Properties prop = new Properties();
			File configFile = new File("/u01/BLTZ/batch/LDAP/config.properties");
			FileReader reader = new FileReader(configFile);
			prop.load(reader);
			String dc1 = prop.getProperty("DC1");
			// String dc1= "Ldapudtest";

			System.out.println("*************DC1:  ****************" + dc1);

			String dc2 = prop.getProperty("DC2");
			// String dc2= "com";
			System.out.println("*************DC2:  ****************" + dc2);

			String cn = prop.getProperty("CN");
			// String cn= "Users";

			System.out.println("*************CN:  ****************" + cn);

			String host = prop.getProperty("HOST");
			// String host= "10.53.253.88";

			System.out.println("*************HOST:  ****************" + host);

			String port = prop.getProperty("PORT");
			// String port= "389";
			System.out.println("*************PORT:  ****************" + port);

			String domainLdap = "cn=" + cn + ",dc=" + dc1 + ",dc=" + dc2;

			System.out.println("*************domainLdap:  ****************" + domainLdap);

			if (checkLDAP(username, password, domainLdap, host, port)) {
				System.out.println("Check LDAP HOST");

				return true;
			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
