package org.o7planning.load_ds_wl.dao;

import org.o7planning.load_ds_wl.dao.BaseDao;
import org.o7planning.load_ds_wl.entity.FileEntity;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class ListFileDao extends BaseDao {

	public ListFileDao(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<FileEntity> getAllData(String type) {
		System.out.println("###");

		StringBuilder sql = new StringBuilder();
		sql.append("select * from BLTZFILE ");
		sql.append("where 1=1 ");
		if (type.equals("checker")) {
			sql.append("AND STATUS NOT IN ('pre-pending', 'failed') ");
		}
		sql.append("ORDER BY DATE_UPLOAD DESC ");

		sql.append("FETCH NEXT 10 ROWS ONLY ");
		System.out.println("st##");

		SQLQuery query = session.createSQLQuery(sql.toString());
		System.out.println("###########################SQL REPORT DETAIL###########" + sql.toString());
		query.addEntity(FileEntity.class);
		List<FileEntity> listData = query.list();
		System.out.println("###########################listData###########" + listData);

		return listData;

	}

	public String insertData(String name, String dir, String status, String dateUpload, String userUpload,
			String userApprove, String commentFile, String full, String mailTo) {
		System.out.println("###");
		session.beginTransaction().begin();
		String sql = " " + "Insert into BLTZFILE  VALUES (:NAME,:DIR,:STATUS,"
				+ ":DATE_UPLOAD,:USER_UPLOAD,:USER_APPROVE,:COMMENT_FILE,:FULL,:MAIL,BLTZFIL_SEQ.nextval) ";
		System.out.println("###1");

		SQLQuery query = session.createSQLQuery(sql.toString());
		System.out.println("###3" + query);
		System.out.println("###3" + name + dir + status);

		query.setParameter("NAME", name);
		query.setParameter("DIR", dir);
		query.setParameter("STATUS", status);
		query.setParameter("DATE_UPLOAD", dateUpload);
		query.setParameter("USER_UPLOAD", userUpload);
		query.setParameter("USER_APPROVE", userApprove);
		query.setParameter("COMMENT_FILE", commentFile);
		query.setParameter("FULL", full);
		query.setParameter("MAIL", mailTo);
		System.out.println("###5");

		query.executeUpdate();
		System.out.println("###2");

		session.getTransaction().commit();
		System.out.println("###3");
		// session.close();
		System.out.println("###########################SQL REPORT DETAIL###########" + sql.toString());

		return "success";

	}

	public String updateData(String name, String dir, String status, String dateUpload, String userUpload,
			String userApprove, String commentFile, String full, String mail) {
		System.out.println("###");
		session.beginTransaction().begin();
		String sql = "UPDATE  BLTZFILE SET MAIL='" + mail + "', STATUS='" + status + "', FULL='" + full
				+ "', COMMENT_FILE='" + commentFile + "', USER_UPLOAD='" + userUpload + "', USER_APPROVE='"
				+ userApprove + "', DATE_UPLOAD='" + dateUpload + "' WHERE NAME='" + name + "'";
		System.out.println("###1");

		SQLQuery query = session.createSQLQuery(sql.toString());
		System.out.println("###3" + query);
		System.out.println("###3" + name + mail);

		query.executeUpdate();
		System.out.println("###2");

		session.getTransaction().commit();
		// session.close();

		System.out.println("###3");

		System.out.println("###########################SQL REPORT DETAIL###########" + sql.toString());

		return "success";
	}

	public String updateDataToChecker(String name, String status, String dateUpload) {
		System.out.println("###");
		session.beginTransaction().begin();
		String sql = "UPDATE  BLTZFILE SET STATUS='" + status + "', DATE_UPLOAD='" + dateUpload + "' WHERE NAME='"
				+ name + "'";
		System.out.println("###1");

		SQLQuery query = session.createSQLQuery(sql.toString());
		System.out.println("###3" + query);
		System.out.println("###3" + name);

		query.executeUpdate();
		System.out.println("###2");

		session.getTransaction().commit();
		// session.close();

		System.out.println("###3");

		System.out.println("###########################SQL REPORT DETAIL###########" + sql.toString());

		return "success";
	}

	public String updateDeleteData(String name, String commentFile, String status, String userApprove) {
		System.out.println("###");
		session.beginTransaction().begin();
		String sql = "UPDATE  BLTZFILE SET COMMENT_FILE='" + commentFile + "', STATUS='" + status + "', USER_APPROVE='"
				+ userApprove + "' WHERE NAME='" + name + "'";
		System.out.println("###1");

		SQLQuery query = session.createSQLQuery(sql.toString());
		System.out.println("###3" + query);

		query.executeUpdate();
		System.out.println("###2");

		session.getTransaction().commit();
		// session.close();

		System.out.println("###3");

		System.out.println("###########################SQL REPORT DETAIL###########" + sql.toString());

		return "success";
	}

	public String deleteData(String id) {
		session.beginTransaction().begin();
		String sql = "DELETE FROM BLTZFILE WHERE ID=" + id;

		SQLQuery query = session.createSQLQuery(sql.toString());
		System.out.println("###########################SQL DELETE DETAIL###########" + sql.toString());

		query.executeUpdate();
		session.getTransaction().commit();

		return "success";

	}
}
