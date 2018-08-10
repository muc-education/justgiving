package edu.study.giya.dao.jpa;

import edu.study.giya.dao.CrudDao;
import edu.study.giya.dao.PageDao;
import edu.study.giya.service.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import javax.persistence.*;

public class SimpleBaseCrudDao<T> extends BaseCrudDao<T> implements CrudDao<T>, PageDao<T> {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("justgiving");
    private static Logger logger = LoggerFactory.getLogger(SimpleBaseCrudDao.class);
    private static ThreadLocal<EntityManager> em = new ThreadLocal<EntityManager>();
    private static final long serialVersionUID = 7433224241393375192L;
    //@PersistenceContext(unitName="tutorialsPU")
    private EntityManager entityManager;
    private Class<T> clazz;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    private EntityManager setEntityManager() {
        EntityManager manager = em.get();

        if (manager == null) {
            manager = emf.createEntityManager();
            em.set(manager);
        }
        return manager;
    }

    public SimpleBaseCrudDao(Class clazz) {
        this.clazz = clazz;
        //this.clazz = (Class<T>) (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityManager = setEntityManager();
    }


    public boolean update(Object obj) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(obj);
        getEntityManager().getTransaction().commit();
        return true;
    }

    public Object uniqueResult(String sql) {
        return uniqueResult(sql, null);
    }

    public Object uniqueResult(String sql, Collection paras) {
        Query query = getEntityManager().createQuery(sql);
        int parameterIndex = 0;
        if (paras != null && paras.size() > 0) {
            for (Object obj : paras) {
                query.setParameter(parameterIndex++, obj);
            }
        }
        return query.getSingleResult();
    }

    public boolean modify(Object id,String field,String value) {
        if(id==null||id.equals("")){
            logger.debug("could not modify. do not know primary key.");
            return false;
        }
        Object s=entityManager.find(clazz,id);
        try {
            entityManager.getTransaction().begin();
            Field temp=s.getClass().getDeclaredField(field);
            temp.setAccessible(true);
            temp.set(s,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error("could not modify. IllegalAccessException.");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            logger.error("could not modify no such Field.");
        }finally {
            entityManager.getTransaction().commit();
        }
        return true;
    }

    public boolean modify(T t){
        entityManager.getTransaction().begin();
        try {

            Field input=t.getClass().getDeclaredField("id");
            input.setAccessible(true);
            Object key=input.get(t);
            if(key==null||key.equals("")){
                logger.debug("could not modify. do not know primary key.");
                return false;
            }

            Object s=entityManager.find(clazz,key);
            Field[] fields=s.getClass().getDeclaredFields();
            String v=null;
            Object oo=null;
            for(Field f:fields){
              f.setAccessible(true);
              v=f.getName();
              input=t.getClass().getDeclaredField(v);
              input.setAccessible(true);
              if(input.get(t)!=null)
                  f.set(s,input.get(t));
            }
            entityManager.merge(s);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }finally {
            entityManager.getTransaction().commit();
        }
        return true;
    }

    public T findById(Integer primaryKey){
        entityManager.getTransaction().begin();
        T t=entityManager.find(clazz, primaryKey);
        entityManager.getTransaction().commit();
        return t;
    }

    public List<T> getAll(){
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select o from " + clazz.getSimpleName() + " o");
        List<T> result = null;
        try {
            result = query.getResultList();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
            logger.error("exception {}.", e);
        }finally {
            entityManager.getTransaction().commit();
        }
        return result;
    }


    /**
     * 不需确定新建实体是否已经存在
     */
    @Override
    public T add(T t) throws RuntimeException {
        try {
            entityManager.getTransaction().begin();
            t = entityManager.contains(t) ? t : entityManager.merge(t);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("保存失败，请联系管理员！");
        }
        return t;
    }

    /**
     * 不需确定新建实体是否已经存在 传入对象集合
     */
    @Override
    public Collection<T> add(Collection<T> ts) {
        Collection<T> collection = new HashSet<T>();
        for (T t : ts) {
            collection.add(add(t));
        }
        return collection;
    }

    /**
     * 可确定为新建实体
     */
    @Override
    public T persist(T t) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(t);
            entityManager.getTransaction().commit();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("错误：保存新建实例时，发生异常：" + e.getMessage());
        }
    }

    /**
     * 可确定为新建实体 成功返回true 失败返回false
     */
    @Override
    public boolean persist(Collection<T> ts) {
/*        int batchSize = 1;
        int i = 0;*/
        try {
            for (T t : ts) {
                persist(t);
          /*      i++;
                if (i % batchSize == 0) {
                    getEntityManager().flush();
                    getEntityManager().clear();
                }*/
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 若数据库中已有此记录，置为托管状态并刷新实体信息
     */
    @Override
    public T merge(T t) throws RuntimeException {
        try {
            entityManager.getTransaction().begin();
            t = entityManager.contains(t) ? t : entityManager.merge(t);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("更新失败，请联系管理员！");
        }
        return t;
    }

    /**
     * 若数据库中已有此记录，置为托管状态
     */
    @Override
    public Collection<T> merge(Collection<T> ts) {
        Collection<T> collection = new HashSet<T>();
        for (T t : ts) {
            collection.add(merge(t));
        }
        return collection;
    }

    /**
     * 删除
     */
    @Override
    public void remove(T t) throws RuntimeException {
        if (null == t) {
            throw new RuntimeException("请求删除的对象为空!");
        }
        try {
            entityManager.getTransaction().begin();
            if (entityManager.contains(t)) {
                entityManager.remove(t);
            } else {
                Object id = entityManager.getEntityManagerFactory()
                        .getPersistenceUnitUtil().getIdentifier(t);
                entityManager.remove(entityManager.find(t.getClass(), id));
            }
        } catch (Exception e) {
            throw new RuntimeException("删除对象时出错，请联系管理员!");
        }finally {
            entityManager.getTransaction().commit();
        }
    }

    /**
     * 批量删除 传入集合
     */
    @Override
    public void remove(Collection<T> ts) {
        for (T t : ts) {
            remove(t);
        }
    }

    /**
     * 删除
     */
    @Override
    public void remove(Class<T> clazz, String id) throws RuntimeException {
        if (id == null || id.equals("")) {
            throw new RuntimeException("请求删除的对象为空!");
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(clazz, id));
        } catch (Exception e) {
        }finally {
            entityManager.getTransaction().commit();
        }
    }

    /**
     * 删除
     */
    @Override
    public void remove(Class<T> clazz, Collection<String> ids) {
        for (String id : ids) {
            remove(clazz, id);
        }
    }

    /**
     * 若数据库中存在，返回最新状态；否则，返回空
     *
     * @param clazz
     * @param t
     * @param t
     * @return
     */
    protected T refresh(Class<T> clazz, T t) throws RuntimeException {
        entityManager.getTransaction().begin();
        String id = (String) entityManager.getEntityManagerFactory()
                .getPersistenceUnitUtil().getIdentifier(t);
        if (null == t) {
            throw new RuntimeException("请求刷新的实体为空！");
        }
        if (id == null || id.equals("")) {
            return null;
        }

        if (entityManager.contains(t)) {
            entityManager.refresh(t);
            entityManager.getTransaction().commit();
            return t;
        } else {
            return entityManager.find(clazz, id);
        }
    }

    /**
     * 清除一级缓存
     */
    @Override
    public void clear() {
        entityManager.clear();
    }

    /**
     * 将对象置为游离状态
     */
    @Override
    public void detach(T t) {
        entityManager.detach(t);
    }

    /**
     * 将对象置为游离状态
     */
    @Override
    public void detach(Collection<T> ts) {
        for (T t : ts) {
            detach(t);
        }
    }

    /**
     * 判断实体是否处于托管状态
     */
    @Override
    public boolean isManaged(T t) {
        return entityManager.contains(t);
    }

    /**
     * 同步jpa容器和数据库
     */
    @Override
    public void flush() {
        entityManager.getTransaction().begin();
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    /**
     * 　　* 获取实体名称
     * 　　* @param <T>
     * 　　* @param entityClass实体类
     * 　　* @return
     */
    protected <T> String getEntityName(Class<T> entityClass) {
        String entityname = entityClass.getSimpleName();
        Entity entity = entityClass.getAnnotation(Entity.class);
        if (entity.name() != null && !"".equals(entity.name())) {
            entityname = entity.name();
        }
        return entityname;
    }


    @Override
    public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult) {
        QueryResult qr = new QueryResult<T>();
        String entityname = getEntityName(entityClass);
        Query query = entityManager.createQuery("select o from " + entityname + " o");
        query.setFirstResult(firstindex).setMaxResults(maxresult);
        qr.setResult(query.getResultList());
        query = entityManager.createQuery("select count(o) from " + entityname + " o");
        qr.setSize((Long) query.getSingleResult());
        return qr;
    }


    private Query createNativeQuery(String sql, Object... params) {

        Query q = entityManager.createNativeQuery(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]); // 与Hiberante不同,jpa query从位置1开始

            }
        }

        return q;
    }

    public T getEntityById(Class<T> clszz, Long id) {
        return entityManager.find(clszz, id);
    }

    public T getEntityById(Serializable id) {
        return getEntityManager().find(clazz, id);
    }

    public List<T> getAllByIds(List<String> ids) {
        return getEntityManager().createNativeQuery("From " + clazz.getSimpleName() + " where id in (:ids) ").setParameter("ids", ids).getResultList();
    }

/*    public <T> List<T> nativeQueryList(String nativeSql, Object... params) {
        Query q = createNativeQuery(nativeSql, params);
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.TO_LIST);
        return q.getResultList();
    }*/

    public boolean delete(Object obj) {
        getEntityManager().getTransaction().begin();
        if (!getEntityManager().contains(obj))
            obj = getEntityManager().merge(obj);
        getEntityManager().remove(obj);
        getEntityManager().getTransaction().commit();
        return true;
    }

    public int execute(String sql) {
        return execute(sql, null);
    }

    /**
     * add jpa auto parse method
     *
     * @param jpaSql
     * @param paramerter
     * @return
     */
    public T jpaCrud(String jpaSql, Map<String, String> paramerter) {
        entityManager.getTransaction().begin();
        TypedQuery<T> query = entityManager.createNamedQuery(jpaSql, clazz);
        Set<String> keys = paramerter.keySet();
        for (String s : keys) {
            query.setParameter(s, paramerter.get(s));
        }
        T result = null;
        try {
            result = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        entityManager.getTransaction().commit();
        return result;
    }

    public T jpaCrud(String jpaSql) {
        entityManager.getTransaction().begin();
        TypedQuery<T> query = entityManager.createQuery(jpaSql, clazz);
        T result = null;
        try {
            result = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        entityManager.getTransaction().commit();
        return result;
    }


    /**
     * Jpa operation to get list object by entity sql, NamedQuries
     * You should define sql in entity layer
     *
     * @param jpaSql
     * @param paramerter
     * @return
     * @NamedQueries({
     * @NamedQuery(name = "findUserById", query = "SELECT u FROM User u WHERE u.email = :email")
     * })
     */
    public List<T> jpaCrudList(String jpaSql, Map<String, String> paramerter) {
        entityManager.getTransaction().begin();
        TypedQuery<T> query = entityManager.createNamedQuery(jpaSql, clazz);
        Set<String> keys = paramerter.keySet();
        for (String s : keys) {
            query.setParameter(s, paramerter.get(s));
        }
        List<T> result = null;
        try {
            result = query.getResultList();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
            logger.error("exception {}.",e);
        }
        entityManager.getTransaction().commit();
        return result;
    }

    public List<T> jpaCrudList(String jpaSql) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(jpaSql);
        List<T> result = null;
        try {
            result = query.getResultList();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
            logger.error("exception {}.", e);
        }
        entityManager.getTransaction().commit();
        return result;
    }

    public int execute(String sql, Collection paras) {
        Query query = getEntityManager().createNativeQuery(sql);
        int parameterIndex = 0;
        if (paras != null && paras.size() > 0) {
            for (Object obj : paras) {
                query.setParameter(parameterIndex++, obj);
            }
        }
        return query.executeUpdate();
    }

    public T get(Class<T> clz, Serializable id) {
        return getEntityManager().find(clz, id);
    }

    public T getBy(Class<T> clz, String fieldName, Serializable value) {
        Query query = getEntityManager().createQuery(
                "from " + clz + " where fieldName=?");
        query.setParameter(0, value);
        return (T) query.getSingleResult();
    }

    public List query(Class clz, String scope) {
        return query(clz, scope, null);
    }

    public List queryByField(Class clz, String key, String value) {
        return query(clz, key + " = " + value);
    }

    public List query(Class clz, String scope, Collection paras) {
        return query(clz, scope, null, -1, -1);
    }

    public List query(Class clz, String scope, Collection paras, int begin,
                      int max) {
        Query query = getEntityManager().createQuery(
                "from " + clz + " where " + scope);
        int parameterIndex = 0;
        if (paras != null && paras.size() > 0) {
            for (Object obj : paras) {
                query.setParameter(parameterIndex++, obj);
            }
        }
        if (begin >= 0 && max > 0) {
            query.setFirstResult(begin);
            query.setMaxResults(max);
        }
        return query.getResultList();
    }


    public T get(Object id) {
        Query query = getEntityManager().createQuery(
                "from " + clazz.getSimpleName() + " where id=" + id);
        if (query.getResultList().size() > 0) {
            return (T) query.getResultList().get(0);
        } else {
            return null;
        }
    }

}
