package dafengqi.yunxiang.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;

import dafengqi.yunxiang.dao.GenericDao;
import dafengqi.yunxiang.dao.SearchException;
import dafengqi.yunxiang.util.ResourceManager;


public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;
    @Resource
    private SessionFactory sessionFactory;
    private Analyzer defaultAnalyzer;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate SessionFactory
     */
    public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getSession() throws HibernateException {
        Session sess = getSessionFactory().getCurrentSession();
        if (sess == null) {
            sess = getSessionFactory().openSession();
        }
        return sess;
    }

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session sess = getSession();
        return sess.createCriteria(persistentClass).list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection<T> result = new LinkedHashSet<T>(getAll());
        return new ArrayList<T>(result);
    }

    /**
     * {@inheritDoc}
     */
    public List<T> search(String searchTerm) throws SearchException {
        Session sess = getSession();
        FullTextSession txtSession = Search.getFullTextSession(sess);

        org.apache.lucene.search.Query qry;
        try {
            qry = HibernateSearchTools.generateQuery(searchTerm, this.persistentClass, sess, defaultAnalyzer);
        } catch (ParseException ex) {
            throw new SearchException(ex);
        }
        org.hibernate.search.FullTextQuery hibQuery = txtSession.createFullTextQuery(qry,
                this.persistentClass);
        return hibQuery.list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        Session sess = getSession();
        return (T) sess.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        Session sess = getSession();
        sess.delete(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        sess.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        Session sess = getSession();
        Query namedQuery = sess.getNamedQuery(queryName);
        for (String s : queryParams.keySet()) {
            Object val = queryParams.get(s);
            if (val instanceof Collection) {
                namedQuery.setParameterList(s, (Collection) val);
            } else {
                namedQuery.setParameter(s, val);
            }
        }
        return namedQuery.list();
    }

    /**
     * {@inheritDoc}
     */
    public void reindex() {
        HibernateSearchTools.reindex(persistentClass, getSessionFactory().getCurrentSession());
    }


    /**
     * {@inheritDoc}
     */
    public void reindexAll(boolean async) {
        HibernateSearchTools.reindexAll(async, getSessionFactory().getCurrentSession());
    }
    DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
    
	public static synchronized String getOrderTypeID(String lx,String table,String rq,String jgid,String type) {
		java.sql.Connection uc = null;
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (uc != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id = "";
		try {
			conn = isConnSupplied ? uc : ResourceManager.getConnection();
			int l=0;
			l=lx.length()+9;
			
			String sql="SELECT max(CONVERT(substring(id,"+l+",length(id)),signed)) FROM "+table+" where 1=1 and id like '"+lx+rq+"%'and mechanism_id=? and type=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, jgid);
			ps.setString(2, type);
			
			rs = ps.executeQuery();
			String djid="";
			while (rs.next()) {
				djid = rs.getString(1);
			}		
			if(djid==null) {
				id=genID(lx,rq,djid);
			}else {
				int len=djid.length();
				if(len==1) {
					djid="000"+djid;
				}else if(len==2) {
					djid="00"+djid;
				}else if(len==3) {
					djid="0"+djid;
				}
				id=genID(lx,rq,djid);
			}

			long t2 = System.currentTimeMillis();
			System.out.println(" static synchronized String getOrderID(String sql)" + (t2 - t1) +" ms");

		} catch (SQLException e) {
			
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}

		return id;
	}

    
	public static synchronized String getOrderID(String lx,String table,String rq,String jgid) {
		java.sql.Connection uc = null;
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (uc != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id = "";
		try {
			conn = isConnSupplied ? uc : ResourceManager.getConnection();
			int l=0;
			l=lx.length()+9;
			
			String sql="SELECT max(CONVERT(substring(id,"+l+",length(id)),signed)) FROM "+table+" where 1=1 and id like '"+lx+rq+"%'and mechanism_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, jgid);
			
			rs = ps.executeQuery();
			String djid="";
			while (rs.next()) {
				djid = rs.getString(1);
			}		
			if(djid==null) {
				id=genID(lx,rq,djid);
			}else {
				int len=djid.length();
				if(len==1) {
					djid="000"+djid;
				}else if(len==2) {
					djid="00"+djid;
				}else if(len==3) {
					djid="0"+djid;
				}
				id=genID(lx,rq,djid);
			}

			long t2 = System.currentTimeMillis();
			System.out.println(" static synchronized String getOrderID(String sql)" + (t2 - t1) +" ms");

		} catch (SQLException e) {
			
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}

		return id;
	}
	
	public static synchronized String genID(String billType,String rq,String dbcode) {

		// 存放最终生成的单据编号的字符串
		String billCode = new String();

		//String dateString = new SimpleDateFormat("yyyyMMdd").format(new Date());

		if ("".equals(dbcode) || dbcode == null) {
			// 如果单号不存在,则设置每天的第一个单号
			billCode = billType + rq + "0001";
		} else {

			// 取出单据号中的固定位
			String str = billType + rq;

			// 取出流水号
			String temp = dbcode.substring(dbcode.length() - 4, dbcode.length());

			// 取出当天的所有单号中最大的单号截取后自增1
			if (Integer.parseInt(temp) >= 1 && Integer.parseInt(temp) < 9999) {
				temp = String.valueOf(Integer.parseInt(temp) + 1);
			}
			switch (temp.length()) {
			case 1:
				temp = "000" + temp;
				break;
			case 2:
				temp = "00" + temp;
				break;
			case 3:
				temp = "0" + temp;
				break;
			default:
				break;
			}
			billCode = str + temp;
		}

		return billCode;
	}
	
	
	public BigDecimal getDwcb(String productId,String warehouseId,BigDecimal quantity,String mechanismId) {
		//最终单位成本
		BigDecimal dwcb = BigDecimal.ZERO;
		BigDecimal sxcgsl = BigDecimal.ZERO;
		BigDecimal sxcgjs = BigDecimal.ZERO;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps6 = null;
		PreparedStatement ps7 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		ResultSet rs6 = null;
		ResultSet rs7 = null;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			//销售数量
			BigDecimal xssl = BigDecimal.ZERO;
			String xsdql = "SELECT IFNULL(sum(quantity),0) FROM  salesorderproduct  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? and audit_status='已审核' group by warehouse_id,product_id  ";
			ps2 = conn.prepareStatement(xsdql);
			ps2.setString(1, mechanismId);
			ps2.setString(2, productId);
			ps2.setString(3, warehouseId);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				xssl = xssl.add(rs2.getBigDecimal(1));
			}
			
			//采购退回数量
			String cgthdql = "SELECT IFNULL(sum(quantity),0) FROM  purchasereturnorderproduct  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? and audit_status='已审核' group by warehouse_id,product_id  ";
			ps3 = conn.prepareStatement(cgthdql);
			ps3.setString(1, mechanismId);
			ps3.setString(2, productId);
			ps3.setString(3, warehouseId);
			rs3 = ps3.executeQuery();
			while (rs3.next()) {
				xssl = xssl.add(rs3.getBigDecimal(1));
			}
			
			//其他出库数量
			String qtckql = "SELECT IFNULL(sum(quantity),0) FROM  othercheckoutorderproduct  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? and audit_status='已审核' group by warehouse_id,product_id  ";
			ps4 = conn.prepareStatement(qtckql);
			ps4.setString(1, mechanismId);
			ps4.setString(2, productId);
			ps4.setString(3, warehouseId);
			rs4 = ps4.executeQuery();
			while (rs4.next()) {
				xssl = xssl.add(rs4.getBigDecimal(1));
			}
			
			//调拨出库数量
			String dbckql = "SELECT IFNULL(sum(quantity),0) FROM  transferorderproduct  where 1=1 and mechanism_id=? and product_id=? and transfer_out_warehouse_id=? and audit_status='已审核' group by transfer_out_warehouse_id,product_id  ";
			ps5 = conn.prepareStatement(dbckql);
			ps5.setString(1, mechanismId);
			ps5.setString(2, productId);
			ps5.setString(3, warehouseId);
			rs5 = ps5.executeQuery();
			while (rs5.next()) {
				xssl = xssl.add(rs5.getBigDecimal(1));
			}

			
			//组装单出库数量
			String zzdckql = "SELECT IFNULL(sum(quantity),0) FROM  assemblyorderproductsubpiece  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? and audit_status='已审核' group by warehouse_id,product_id  ";
			ps6 = conn.prepareStatement(zzdckql);
			ps6.setString(1, mechanismId);
			ps6.setString(2, productId);
			ps6.setString(3, warehouseId);
			rs6 = ps6.executeQuery();
			while (rs6.next()) {
				xssl = xssl.add(rs6.getBigDecimal(1));
			}

			
			//拆卸单出库数量
			String cxdckql = "SELECT IFNULL(sum(quantity),0) FROM  disassemblyorderproduct  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? and audit_status='已审核' group by warehouse_id,product_id  ";
			ps7 = conn.prepareStatement(cxdckql);
			ps7.setString(1, mechanismId);
			ps7.setString(2, productId);
			ps7.setString(3, warehouseId);
			rs7 = ps7.executeQuery();
			while (rs7.next()) {
				xssl = xssl.add(rs7.getBigDecimal(1));
			}

			//期初数量
			BigDecimal qcsl = BigDecimal.ZERO;
			//期初数量
			BigDecimal qccb = BigDecimal.ZERO;
			String sql = "SELECT IFNULL(opening_quantity,0),IFNULL(purchase_price,0) FROM  productDetail  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, mechanismId);
			ps.setString(2, productId);
			ps.setString(3, warehouseId);
			rs = ps.executeQuery();
			while (rs.next()) {
				qcsl = rs.getBigDecimal(1);
				qccb = rs.getBigDecimal(2);
			}
			if(xssl.compareTo(BigDecimal.ZERO)==0) {
				if(quantity.compareTo(qcsl)==1) {
					//所需要的采购数量
					sxcgsl = quantity.subtract(qcsl);
					//采购数量
					BigDecimal cgsl = BigDecimal.ZERO;
					//采购成本
					BigDecimal cgcb = BigDecimal.ZERO;
					

					System.out.println(qcsl.multiply(qccb));
					sxcgjs = qcsl.multiply(qccb);
					if(sxcgsl.compareTo(BigDecimal.ZERO)==1) {
						String cgdSql = "SELECT IFNULL(quantity,0),IFNULL(purchase_price,0) FROM  purchaseorderproduct  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? and audit_status='已审核'  order by create_date  ";
						ps1 = conn.prepareStatement(cgdSql);
						ps1.setString(1, mechanismId);
						ps1.setString(2, productId);
						ps1.setString(3, warehouseId);
						rs1 = ps1.executeQuery();
						while (rs1.next()) {
							cgsl = rs1.getBigDecimal(1);
							cgcb = rs1.getBigDecimal(2);
							
							//销售数量-期初数量 大于采购数量()
							if(sxcgsl.compareTo(cgsl)==1) {
								System.out.println(cgsl.multiply(cgcb));
								sxcgjs = sxcgjs.add(cgsl.multiply(cgcb));
								sxcgsl = sxcgsl.subtract(cgsl);
							}else if(sxcgsl.compareTo(cgsl)==-1 || sxcgsl.compareTo(cgsl)==0) {
								System.out.println(sxcgsl.multiply(cgcb));
								sxcgjs = sxcgjs.add(sxcgsl.multiply(cgcb));
								break;
							}
						}
					}
					
					
					dwcb = sxcgjs.divide(quantity, 2, BigDecimal.ROUND_HALF_UP);
					
					
				}else if(quantity.compareTo(qcsl)==-1) {
					dwcb = qccb;
				}else if(quantity.compareTo(qcsl)==0) {
					dwcb = qccb;
				}
			}else if(xssl.compareTo(BigDecimal.ZERO)==1) {
				//销售数量大于期初数量，期初数量变为0，销售数量=销售数量-期初数量
				if(xssl.compareTo(qcsl)==1) {
					xssl = xssl.subtract(qcsl);
					qcsl=BigDecimal.ZERO;
				}else if(xssl.compareTo(qcsl)==-1) {
					qcsl = qcsl.subtract(xssl);
					xssl=BigDecimal.ZERO;
				}else if(xssl.compareTo(qcsl)==0) {
					qcsl = qcsl.subtract(xssl);
					xssl=BigDecimal.ZERO;
				}
				
				//所需要的采购数量
				sxcgsl = quantity.subtract(qcsl);
				//采购数量
				BigDecimal cgsl = BigDecimal.ZERO;
				//采购成本
				BigDecimal cgcb = BigDecimal.ZERO;
				

				System.out.println(qcsl.multiply(qccb));
				sxcgjs = qcsl.multiply(qccb);
				if(sxcgsl.compareTo(BigDecimal.ZERO)==1) {
					String cgdSql = "SELECT IFNULL(quantity,0),IFNULL(purchase_price,0) FROM  purchaseorderproduct  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? and audit_status='已审核'  order by create_date  ";
					ps1 = conn.prepareStatement(cgdSql);
					ps1.setString(1, mechanismId);
					ps1.setString(2, productId);
					ps1.setString(3, warehouseId);
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						cgsl = rs1.getBigDecimal(1);
						cgcb = rs1.getBigDecimal(2);
						if(xssl.compareTo(cgsl)==-1) {
							xssl=BigDecimal.ZERO;
							//销售数量-期初数量 大于采购数量()
							if(sxcgsl.compareTo(cgsl)==1) {
								System.out.println(cgsl.multiply(cgcb));
								sxcgjs = sxcgjs.add(cgsl.multiply(cgcb));
								sxcgsl = sxcgsl.subtract(cgsl);
							}else if(sxcgsl.compareTo(cgsl)==-1 || sxcgsl.compareTo(cgsl)==0) {
								System.out.println(sxcgsl.multiply(cgcb));
								sxcgjs = sxcgjs.add(sxcgsl.multiply(cgcb));
								break;
							}
							
						}
					}
				}
				
				
				dwcb = sxcgjs.divide(quantity, 2, BigDecimal.ROUND_HALF_UP);
			}

			conn.setAutoCommit(true);
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			ResourceManager.close(ps5);
			ResourceManager.close(ps6);
			ResourceManager.close(ps7);
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(rs4);
			ResourceManager.close(rs5);
			ResourceManager.close(rs6);
			ResourceManager.close(rs7);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			ResourceManager.close(ps5);
			ResourceManager.close(ps6);
			ResourceManager.close(ps7);
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(rs4);
			ResourceManager.close(rs5);
			ResourceManager.close(rs6);
			ResourceManager.close(rs7);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		
		
		return dwcb;
		
	}


	public BigDecimal getYdpjDwcb(String productId,String warehouseId,BigDecimal quantity,String mechanismId) {
		//最终单位成本
		BigDecimal dwcb = BigDecimal.ZERO;
		BigDecimal sxcgsl = BigDecimal.ZERO;
		BigDecimal sxcgjs = BigDecimal.ZERO;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			
			
			//总数量
			BigDecimal zsl = BigDecimal.ZERO;
			//期初数量
			BigDecimal qcsl = BigDecimal.ZERO;
			//期初数量
			BigDecimal qccb = BigDecimal.ZERO;
			
			String sql = "SELECT IFNULL(opening_quantity,0),IFNULL(purchase_price,0) FROM  productDetail  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, mechanismId);
			ps.setString(2, productId);
			ps.setString(3, warehouseId);
			rs = ps.executeQuery();
			while (rs.next()) {
				qcsl = rs.getBigDecimal(1);
				qccb = rs.getBigDecimal(2);

				System.out.println(qcsl.multiply(qccb));
				sxcgjs = qcsl.multiply(qccb);
			}
			//采购数量
			BigDecimal cgsl = BigDecimal.ZERO;
			//采购成本
			BigDecimal cgcb = BigDecimal.ZERO;
			zsl = zsl.add(qcsl);
			String cgdSql = "SELECT IFNULL(quantity,0),IFNULL(purchase_price,0) FROM  purchaseorderproduct  where 1=1 and mechanism_id=? and product_id=? and warehouse_id=? and audit_status='已审核'  order by create_date  ";
			ps1 = conn.prepareStatement(cgdSql);
			ps1.setString(1, mechanismId);
			ps1.setString(2, productId);
			ps1.setString(3, warehouseId);
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				cgsl = rs1.getBigDecimal(1);
				cgcb = rs1.getBigDecimal(2);
				zsl = zsl.add(cgsl);
				sxcgjs = sxcgjs.add(cgsl.multiply(cgcb));
			}
			System.out.println("sxcgjs:"+sxcgjs);
			System.out.println("zsl:"+zsl);
			if(sxcgjs.compareTo(BigDecimal.ZERO)==1 || zsl.compareTo(BigDecimal.ZERO)==1) {

				dwcb = sxcgjs.divide(zsl, 2, BigDecimal.ROUND_HALF_UP);
			}

			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		
		
		return dwcb;
		
	}
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获得本天的开始时间
     *
     * @return
     */
    public static Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本天的结束时间
     *
     * @return
     */
    public static Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public static Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public static Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }
    /**
     * 获得本月的开始时间
     *
     * @return
     */
    public static Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 本月的结束时间
     *
     * @return
     */
    public static Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前年的开始时间
     *
     * @return
     */
    public static Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的结束时间
     *
     * @return
     */
    public static Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }


	public int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String iclueSQL = "INSERT INTO crm_follow_up(id,follow_up,time,content,follow_id,follow_mc,follow_status,contacts_id,contacts_mc,next_follow_time,create_id,create_name,create_date,mechanism_id,froms,department_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(iclueSQL);

			ps.setString(1, UUID.randomUUID().toString());
			ps.setString(2, type);
			ps.setString(3, df.format(time));
			ps.setString(4, content);
			ps.setString(5, id);
			ps.setString(6, name);
			ps.setString(7, status);
			ps.setString(8, lxrid);
			ps.setString(9, lxrmc);
			ps.setString(10, df.format(followtime));
			ps.setString(11, createId);
			ps.setString(12, createName);
			ps.setString(13, cjsj);
			ps.setString(14, mechanismid);
			ps.setString(15, from);
			ps.setString(16, departmentid);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);

			long t2 = System.currentTimeMillis();
			System.out.println("followup( String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String cjr,String cjsj,String jgid,String cjrmc,String ly) " + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
			rv = -1;
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}
	
	

	

	
	
	
	

	public BigDecimal checkProductStock(String productId, String warehouseId, String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal stock = BigDecimal.ZERO;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sspkcSQL = "SELECT stock FROM productDetail  where product_id=? and warehouse_id=? and mechanism_id=?  ";
			ps = conn.prepareStatement(sspkcSQL);
			ps.setString(1, productId);
			ps.setString(2, warehouseId);
			ps.setString(3, mechanismId);
			rs = ps.executeQuery();
			while (rs.next()) {
				stock = rs.getBigDecimal(1);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(" BigDecimal checkProductStock(String productId, String warehouseId, String mechanismId) " + (t2 - t1) + " ms");
		} catch (SQLException e) {
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return stock;
	}
	
	

	
	/**
	 * 销售单审核减少库存增加应收
	 * @param table
	 * @param id
	 * @param key
	 * @param mechanismId
	 * @return
	 */
	public int auditSalesProductDetail(String table, String id, String key,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ssql="";
			if(table.equals("salesorderproduct")) {
				ssql="select product_id, IFNULL(quantity,0), IFNULL(price,0)  ,IFNULL(current_collection,0), `warehouse_id`, `mechanism_id`,account_id,customer_id,IFNULL(current_arrears,0) from "+table+" where mechanism_id=? and "+key+"=?";

			}else if(table.equals("salesreturnorderproduct")) {
				ssql="select product_id, IFNULL(quantity,0), IFNULL(price,0)  ,IFNULL(this_refund,0), `warehouse_id`, `mechanism_id`,account_id,customer_id,IFNULL(current_arrears,0) from "+table+" where mechanism_id=? and "+key+"=?";
			}
			
			ps = conn.prepareStatement(ssql);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rs = ps.executeQuery();
			BigDecimal je = BigDecimal.ZERO;
			String khid="";
			while(rs.next()) {
				je = rs.getBigDecimal(9);
				khid = rs.getString(8);
				//减少商品库存
				String uSQL = "update productDetail set stock=stock-? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps1 = conn.prepareStatement(uSQL);
				ps1.setBigDecimal(1, rs.getBigDecimal(2));
				ps1.setString(2, rs.getString(1));
				ps1.setString(3, rs.getString(5));
				ps1.setString(4, rs.getString(6));
				rv = ps1.executeUpdate();
				

				//减少商品库存
				String u2SQL = "update inventory_plan_detail set stock=stock-? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps4 = conn.prepareStatement(u2SQL);
				ps4.setBigDecimal(1, rs.getBigDecimal(2));
				ps4.setString(2, rs.getString(1));
				ps4.setString(3, rs.getString(5));
				ps4.setString(4, rs.getString(6));
				rv = ps4.executeUpdate();

				//增加账户余额
				String uaccountSQL = "update account set current_balance=IFNULL(current_balance,0)+? where 1=1 and id=?  and  mechanism_id=?";
				ps2 = conn.prepareStatement(uaccountSQL);
				ps2.setBigDecimal(1, rs.getBigDecimal(4));
				ps2.setString(2, rs.getString(7));
				ps2.setString(3, rs.getString(6));
				rv = ps2.executeUpdate();
				
				
			}


			//增加应收账款
			String ucustomerSQL = "update crm_customer set balance_of_accounts_receivable=IFNULL(balance_of_accounts_receivable,0)+? where 1=1 and id=?  and data_type='客户' and  mechanism_id=?";
			ps3 = conn.prepareStatement(ucustomerSQL);
			ps3.setBigDecimal(1, je);
			ps3.setString(2, khid);
			ps3.setString(3, mechanismId);
			rv = ps3.executeUpdate();
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}
	
	/**
	 * 销售单反审核加库存等
	 * @param table
	 * @param id
	 * @param key
	 * @param mechanismId
	 * @return
	 */
	public int auditSalesDeProductDetail(String table, String id, String key,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ssql="";
			if(table.equals("salesorderproduct")) {
				ssql="select product_id, IFNULL(quantity,0), IFNULL(price,0)  ,IFNULL(current_collection,0), `warehouse_id`, `mechanism_id`,account_id,customer_id,IFNULL(current_arrears,0) from "+table+" where mechanism_id=? and "+key+"=?";

			}else if(table.equals("salesreturnorderproduct")) {
				ssql="select product_id, IFNULL(quantity,0), IFNULL(price,0)  ,IFNULL(this_refund,0), `warehouse_id`, `mechanism_id`,account_id,customer_id,IFNULL(current_arrears,0) from "+table+" where mechanism_id=? and "+key+"=?";
			}
			
			ps = conn.prepareStatement(ssql);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rs = ps.executeQuery();
			BigDecimal je = BigDecimal.ZERO;
			String khid="";
			while(rs.next()) {
				je = rs.getBigDecimal(9);
				khid = rs.getString(8);
				//增加商品库存
				String uSQL = "update productDetail set stock=stock+? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps1 = conn.prepareStatement(uSQL);
				ps1.setBigDecimal(1, rs.getBigDecimal(2));
				ps1.setString(2, rs.getString(1));
				ps1.setString(3, rs.getString(5));
				ps1.setString(4, rs.getString(6));
				rv = ps1.executeUpdate();
				
				//增加商品库存
				String u2SQL = "update inventory_plan_detail set stock=stock+? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps4 = conn.prepareStatement(u2SQL);
				ps4.setBigDecimal(1, rs.getBigDecimal(2));
				ps4.setString(2, rs.getString(1));
				ps4.setString(3, rs.getString(5));
				ps4.setString(4, rs.getString(6));
				rv = ps4.executeUpdate();
				
				//增加账户余额
				String uaccountSQL = "update account set current_balance=IFNULL(current_balance,0)-? where 1=1 and id=?  and  mechanism_id=?";
				ps2 = conn.prepareStatement(uaccountSQL);
				ps2.setBigDecimal(1, rs.getBigDecimal(4));
				ps2.setString(2, rs.getString(7));
				ps2.setString(3, rs.getString(6));
				rv = ps2.executeUpdate();
			}

			
			//减少应收账款
			String ucustomerSQL = "update crm_customer set balance_of_accounts_receivable=IFNULL(balance_of_accounts_receivable,0)-? where 1=1 and id=?  and data_type='客户'  and  mechanism_id=?";
			ps3 = conn.prepareStatement(ucustomerSQL);
			ps3.setBigDecimal(1, je);
			ps3.setString(2, khid);
			ps3.setString(3, mechanismId);
			rv = ps3.executeUpdate();
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}




	

}
