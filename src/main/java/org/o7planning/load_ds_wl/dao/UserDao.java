package org.o7planning.load_ds_wl.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import org.o7planning.load_ds_wl.entity.UserEntity;

public class UserDao extends BaseDao {

	public UserDao(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> getAllData() {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select DISTINCT BENUTZERNAME as userName, KENNWORT_LANG AS password, ERFDATTIME, BENUTZERTYP as typeUser FROM"
						+ " GWGBENUT where   ERFDATTIME in  (select MAX(ERFDATTIME)   from "
						+ " GWGBENUT GROUP BY BENUTZERNAME)");

		List<UserEntity> listBranch = session.createSQLQuery(sql.toString()).addScalar("userName", Hibernate.STRING)
				.addScalar("password", Hibernate.STRING).addScalar("typeUser", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(UserEntity.class)).list();
		return listBranch;
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> getChecker() {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select DISTINCT BENUTZERNAME as userName, KENNWORT_LANG AS password, ERFDATTIME, BENUTZERTYP as typeUser FROM"
						+ " GWGBENUT where   ERFDATTIME in  (select MAX(ERFDATTIME)   from "
						+ " GWGBENUT GROUP BY BENUTZERNAME) and BENUTZERTYP = '4'");

		List<UserEntity> listBranch = session.createSQLQuery(sql.toString()).addScalar("userName", Hibernate.STRING)
				.addScalar("password", Hibernate.STRING).addScalar("typeUser", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(UserEntity.class)).list();
		return listBranch;
	}
}
