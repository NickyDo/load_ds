package org.o7planning.load_ds_wl.dao;

import org.o7planning.load_ds_wl.dao.BaseDao;
import org.o7planning.load_ds_wl.entity.FileEntity;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class ListFileDao extends BaseDao {

	public ListFileDao(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<FileEntity> getAllData() {
		System.out.println("###");

		StringBuilder sql = new StringBuilder();
		sql.append("select * from (select * from BLTZFILE a inner join (select max(id) as idName, name as nameb from BLTZFILE group by name) b on a.ID = b.idName)");
//		sql.append("where 1=1");
//		sql.append("AND SELECT COUNT (*)");

		System.out.println("st##");

		SQLQuery query = session.createSQLQuery(sql.toString());
		System.out.println("###########################SQL REPORT DETAIL###########" + sql.toString());
		query.addEntity(FileEntity.class);
		List<FileEntity> listData = query.list();
		System.out.println("###########################listData###########" + listData);

		return listData;

	}

	public String insertData(String name, String dir, String typefile, String deletetype, String size,
			String thumbnailurl, String deleteurl, String full) {
		System.out.println("###");
		session.beginTransaction().begin();
		String sql = "Insert into BLTZFILE  VALUES (:NAME,:DIR,:TYPEFILE,"
				+ ":DELETETYPE,:SIZEFILE,:THUMBNAILURL,:DELETEURL,:FULL, BLTZFIL_SEQ.nextval) ";
		

		
		SQLQuery query = session.createSQLQuery(sql.toString());
		query.setParameter("NAME", name);
		query.setParameter("DIR", dir);
		query.setParameter("TYPEFILE", typefile);
		query.setParameter("DELETETYPE", deletetype);
		query.setParameter("SIZEFILE", size);
		query.setParameter("THUMBNAILURL", thumbnailurl);
		query.setParameter("DELETEURL", deleteurl);
		query.setParameter("FULL", full);
		query.executeUpdate();
		session.getTransaction().commit();
		System.out.println("###########################SQL REPORT DETAIL###########" + sql.toString());

		return "success";

	}
}
