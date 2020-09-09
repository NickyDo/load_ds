package org.o7planning.load_ds_wl.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.hibernate.Session;

//import vn.bidv.report.HibernateUtil;
import org.o7planning.load_ds_wl.entity.UserEntity;
import org.o7planning.load_ds_wl.services.UserService;
import org.o7planning.load_ds_wl.util.HibernateUtil;

public class BlitzAuthencation extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9027147210833243640L;
	private String userName;
	private String password;
	private String message;
	private String userType;
	private String role;

	private List<UserEntity> userData;

	public String loginExtern() throws Exception {
		String strResult;
		boolean final_result = false;
		if (!password.isEmpty() && !userName.isEmpty()) {
			final_result = checkDomain(userName, password);
//			 final_result = true;
			if (final_result) {
				ActionContext.getContext().getSession().put("userName", userName);
				Session fsession = HibernateUtil.getSessionFactory().openSession();

				UserService userService = new UserService(fsession);
				userData = userService.getAllType();
				fsession.close();

				if (!userData.isEmpty()) {
					for (int i = 0; i < userData.size(); i++) {
						if (userName.equals(userData.get(i).getUserName())) {
							ActionContext.getContext().getSession().put("role", userData.get(i).getTypeUser());
							System.out.println("role" + userData.get(i).getTypeUser());
						}

					}

				}
				Session session = HibernateUtil.getSessionFactory().openSession();
				ActionContext.getContext().getSession().put("userName", getUserName());
				strResult = "LoginSuccess";
				userType = "S";
				ActionContext.getContext().getSession().put("userType", getUserType());
				session.close();

			} else {
				setMessage("LoginErr");
				strResult = "LoginErr";
			}
		} else {
			setMessage("LoginErr");
			strResult = "LoginErr";
		}
		return strResult;
	}

	private static boolean checkLDAP(String user, String password, String domain, String host, String port) {
		DirContext ctx;
		try {

			Hashtable ldapEnv = new Hashtable(11);
			ldapEnv.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put("java.naming.provider.url", "ldap://" + host + ":" + port);
			ldapEnv.put("java.naming.security.authentication", "simple");
			ldapEnv.put("java.naming.security.principal", "mb\\" + user);// domain
																			// +
																			// user
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
			File configFile = new File("/app/setup/tonbeller/sironKYC/client/0001/data/input/config.properties");
			FileReader reader = new FileReader(configFile);
			prop.load(reader);
			String domainLdap = "";
			String ou1 = prop.getProperty("OU1");
			if (ou1.equals(null) || ou1.equals("")) {
				ou1 = " ";
			} else {
				domainLdap = domainLdap + "OU=" + ou1 + ",";
			}
			// String ou1 = "AML";
			// String ou2 = "Projects";
			String ou2 = prop.getProperty("OU2");
			if (ou2.equals(null) || ou2.equals("")) {
				ou2 = " ";
			} else {
				domainLdap = domainLdap + "OU=" + ou2 + ",";
			}
			String dc1 = prop.getProperty("DC1");
			if (dc1.equals(null) || dc1.equals("")) {
				dc1 = " ";
			} else {
				domainLdap = domainLdap + "DC=" + dc1 + ",";
			}
			// String dc1 = "mb";

			String dc2 = prop.getProperty("DC2");
			if (dc2.equals(null) || dc2.equals("")) {
				dc2 = " ";
			} else {
				domainLdap = domainLdap + "DC=" + dc2 + ",";
			}
			// String dc2 = "vibtest";
			String dc3 = prop.getProperty("DC3");
			if (dc3.equals(null) || dc3.equals("")) {
				dc3 = " ";
			} else {
				domainLdap = domainLdap + "DC=" + dc3;
			}
			// String dc3 = "dev";
			System.out.println("*************DC1:  ****************" + dc1);
			System.out.println("*************DC2:  ****************" + dc2);
			System.out.println("*************DC3:  ****************" + dc3);
			System.out.println("*************OU1:  ****************" + ou1);
			System.out.println("*************OU2:  ****************" + ou2);

			// String cn = prop.getProperty("CN");
			// String cn= "Users";

			// System.out.println("*************CN: ****************" + cn);

			String host = prop.getProperty("HOST");
			// String host = "10.36.22.11";

			System.out.println("*************HOST:  ****************" + host);

			String port = prop.getProperty("PORT");
			// String port = "389";
			System.out.println("*************PORT:  ****************" + port);

			// String domainLdap = "OU=" + ou1 + ",OU=" + ou2 + ",DC=" + dc1 +
			// ",DC=" + dc2 + ",DC=" + dc3;

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<UserEntity> getUserData() {
		return userData;
	}

	public void setUserData(List<UserEntity> userData) {
		this.userData = userData;
	}

}
