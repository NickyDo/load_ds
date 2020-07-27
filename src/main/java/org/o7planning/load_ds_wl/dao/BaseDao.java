package org.o7planning.load_ds_wl.dao;


import org.hibernate.Session;

public class BaseDao {
	protected  Session session;
	
	public BaseDao(Session session) {
        this.session = session;
    }

    public BaseDao() {
    }

    public Object insert(Object o){
        session.save(o);
        return o;
    }

    public void delete(Object o){
        session.delete(o);
    }

    public void update(Object o){
        session.update(o);
    }

}
