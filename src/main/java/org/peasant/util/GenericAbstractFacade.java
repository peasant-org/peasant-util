/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 *
 * @author 谢金光
 * @param <T>
 */
public abstract class GenericAbstractFacade<T> {

    protected Class<T> entityClass;

    public GenericAbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public List<T> findByNamedQuery(String namedquery, Map<String, Object> params) {
        EntityManager em = getEntityManager();
        TypedQuery tq = em.createNamedQuery(namedquery, entityClass);
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                tq.setParameter(e.getKey(), e.getValue());
            }
        }
        return tq.getResultList();

    }

    public int executeUpdateByNamedQuery(String namedquery, Map<String, Object> params) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery(namedquery);
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                query.setParameter(e.getKey(), e.getValue());
            }
        }
        return query.executeUpdate();
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);        
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public int count(Map<String, Object> params) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(root));
        Expression cons = build(cb, root, params);
        if (cons != null) {
            cq.where(cons);
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    protected Expression build(CriteriaBuilder cb, Root root, Map<String, Object> params) {
        Expression cons = null;
        Expression subExp = null;
        Expression path = null;
        for (String field : params.keySet()) {

            path = root.get(field);
            Object value = params.get(field);
            if (null == value) {
                subExp = path.isNull();

            } else if (value instanceof String) {
//                if (((String) value).isEmpty()) {
//                    continue;
//                }
                subExp = cb.like(path, ((String) value));//like 的patten由客户负责构建，以便用户可以控制结果
            } else if (value instanceof java.util.Collection) {
                subExp = path.in((java.util.Collection) value);
            } else if (value.getClass().isArray()) {

                if (Array.getLength(value) < 3) {
                    if (Comparable.class.isAssignableFrom(value.getClass().getComponentType())) {
                        Comparable[] vs = (Comparable[]) value;
                        if (vs[0] != null) {
                            subExp = cb.greaterThanOrEqualTo(path, vs[0]);
                        }
                        if (vs[1] != null) {
                            subExp = (subExp == null) ? cb.lessThan(path, vs[1]) : cb.and(subExp, cb.lessThan(path, vs[1]));
                        }
                        // cb.between((Expression<Comparable>) subExp, vs[0], vs[1]);
                    }
                }

            } else {
                subExp = cb.equal(path, value);
            }

            cons = null == cons ? subExp : cb.and(cons, subExp);

        }
        return cons;
    }

    public List<T> findByConditions(Map<String, Object> params) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        Expression cons = null;
        Expression subExp = null;
        Expression path = null;
        for (String field : params.keySet()) {

            path = root.get(field);
            Object value = params.get(field);
            if (null == value) {
                subExp = path.isNull();

            } else if (value instanceof String) {
//                if (((String) value).isEmpty()) {
//                    continue;
//                }
                subExp = cb.like(path, ((String) value));//like 的patten由客户负责构建，以便用户可以控制结果
            } else if (value instanceof java.util.Collection) {
                subExp = path.in((java.util.Collection) value);
            } else if (value.getClass().isArray()) {

                if (Array.getLength(value) < 3) {
                    if (Comparable.class.isAssignableFrom(value.getClass().getComponentType())) {
                        Comparable[] vs = (Comparable[]) value;
                        if (vs[0] != null) {
                            subExp = cb.greaterThanOrEqualTo(path, vs[0]);
                        }
                        if (vs[1] != null) {
                            subExp = (subExp == null) ? cb.lessThan(path, vs[1]) : cb.and(subExp, cb.lessThan(path, vs[1]));
                        }
                        // cb.between((Expression<Comparable>) subExp, vs[0], vs[1]);
                    }
                }

            } else {
                subExp = cb.equal(path, value);
            }

            cons = null == cons ? subExp : cb.and(cons, subExp);

        }
        if (cons != null) {
            cq.where(cons);
        }
        return getEntityManager().createQuery(cq).getResultList();
    }
}
