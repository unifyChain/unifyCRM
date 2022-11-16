package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.InquiryDao;
import dafengqi.yunxiang.model.Inquiry;
import dafengqi.yunxiang.model.InquiryProduct;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("inquiryDao") 
public class InquiryDaoHibernate extends GenericDaoHibernate<Inquiry, Long> implements InquiryDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);
	public InquiryDaoHibernate() {
		super(Inquiry.class);
	}
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String rq = new SimpleDateFormat("yyyyMMdd").format(new Date());

	@Override
	public List<Inquiry> getItems(Inquiry purchaseorder) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		List<Inquiry> list = new ArrayList<Inquiry>();
		String mechanismId=purchaseorder.getMechanismId();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "";
			if(purchaseorder.getCreateName().equals("admin")){
				sql = " SELECT id,supplier_id,supplier_name,name,mechanism_id,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,relation_mechanism_id FROM  inquiry    where 1=1 order by create_date desc ";
			}else{	
				sql = " SELECT id,supplier_id,supplier_name,name,mechanism_id,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,relation_mechanism_id FROM  inquiry    where 1=1 and (mechanism_id=? or relation_mechanism_id=?) order by create_date desc ";
			}
			ps = conn.prepareStatement(sql);
			if(purchaseorder.getCreateName().equals("admin")){
				
			}else{
				ps.setString(1, purchaseorder.getMechanismId());
				ps.setString(2, purchaseorder.getMechanismId());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				purchaseorder = new Inquiry();
				purchaseorder.setId(rs.getString(1));
				purchaseorder.setSupplierId(rs.getString(2));
				purchaseorder.setSupplierName(rs.getString(3));
				purchaseorder.setName(rs.getString(4));
//				purchaseorder.setMechanismId(rs.getString(5));
				purchaseorder.setMechanismName(rs.getString(6));
				purchaseorder.setCreateId(rs.getString(7));
				purchaseorder.setCreateName(rs.getString(8));
				purchaseorder.setCreateDate(rs.getString(9));
				purchaseorder.setUpdateId(rs.getString(10));
				purchaseorder.setUpdateName(rs.getString(11));
				purchaseorder.setUpdateDate(rs.getString(12));
				if(mechanismId.equals(rs.getString(13))) {
					purchaseorder.setType("是");
				}else {
					purchaseorder.setType("否");
				}
				list.add(purchaseorder);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Inquiry> getItems(Inquiry purchaseorder)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}
	
	
	
	@Override
	public Inquiry edit(Inquiry purchaseorder) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		List<Inquiry> list = new ArrayList<Inquiry>();
		InquiryProduct inquiryProduct = new InquiryProduct();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String mechanismId = purchaseorder.getMechanismId();
			String sql = " SELECT id,supplier_id,supplier_name,name,mechanism_id,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,relation_mechanism_id,relation_mechanism_name FROM  inquiry    where 1=1 and id=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, purchaseorder.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				purchaseorder = new Inquiry();
				purchaseorder.setId(rs.getString(1));
				if(rs.getString(13)==null) {
					purchaseorder.setSupplierId(rs.getString(2)+"!_"+rs.getString(3)+"!_"+"无"+"!_"+"无");
				}else {
					if(rs.getString(13).equals("")) {
						purchaseorder.setSupplierId(rs.getString(2)+"!_"+rs.getString(3)+"!_"+"无"+"!_"+"无");
					}else {
						purchaseorder.setSupplierId(rs.getString(2)+"!_"+rs.getString(3)+"!_"+rs.getString(13)+"!_"+rs.getString(14));
					}
					
				}
				purchaseorder.setSupplierName(rs.getString(3));
				purchaseorder.setName(rs.getString(4));
				purchaseorder.setMechanismId(rs.getString(5));
				purchaseorder.setMechanismName(rs.getString(6));
				purchaseorder.setCreateId(rs.getString(7));
				purchaseorder.setCreateName(rs.getString(8));
				purchaseorder.setCreateDate(rs.getString(9));
				purchaseorder.setUpdateId(rs.getString(10));
				purchaseorder.setUpdateName(rs.getString(11));
				purchaseorder.setUpdateDate(rs.getString(12));
			}
			String cgdmxSQL = " SELECT id,product_id,product_name,bar_code,quantity,price,create_id,create_name,create_date,update_id,update_name,update_date,image,quoted_price FROM inquirydetail  where 1=1 and inquiry_id=? ";
			ps1 = conn.prepareStatement(cgdmxSQL);
			ps1.setString(1, purchaseorder.getId());
			rs1 = ps1.executeQuery();
			List<InquiryProduct> inquiryProducts = new ArrayList<InquiryProduct>();
			while (rs1.next()) {
				inquiryProduct = new InquiryProduct();
				inquiryProduct.setId(rs1.getString(1));
				inquiryProduct.setProductId(rs1.getString(2));
				inquiryProduct.setProductName(rs1.getString(3));
				inquiryProduct.setBarCode(rs1.getString(4));
				inquiryProduct.setQuantity(rs1.getBigDecimal(5));
				inquiryProduct.setPrice(rs1.getBigDecimal(6));
				inquiryProduct.setCreateId(rs1.getString(7));
				inquiryProduct.setCreateName(rs1.getString(8));
				inquiryProduct.setCreateDate(rs1.getString(9));
				inquiryProduct.setUpdateId(rs1.getString(10));
				inquiryProduct.setUpdateName(rs1.getString(11));
				inquiryProduct.setUpdateDate(rs1.getString(12));
				inquiryProduct.setQuotedPrice(rs1.getBigDecimal(14));
				inquiryProducts.add(inquiryProduct);
			}

			purchaseorder.setInquiryProductList(inquiryProducts);
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("Inquiry edit(Inquiry purchaseorder)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return purchaseorder;
	}

	@Override
	public int saveInquiry(Inquiry purchaseorder, List<InquiryProduct> inquiryProducts) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			  
			String ipurchaseorderSQL = "INSERT INTO inquiry(id,supplier_id,supplier_name,name,mechanism_id,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,relation_mechanism_id,relation_mechanism_name)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(ipurchaseorderSQL);
			String id=UUID.randomUUID().toString();
			ps.setString(1, id);
			if(purchaseorder.getSupplierId()==null || purchaseorder.getSupplierId().equals("")) {
				ps.setString(2,"");
				ps.setString(3,"");
			}else {
				String[] gys=purchaseorder.getSupplierId().split("!_");
				ps.setString(2, gys[0]);
				ps.setString(3, gys[1]);
				if(gys[2].equals("无")) {
					ps.setString(13, "");
					ps.setString(14, "");
				}else {
					ps.setString(13, gys[2]);
					ps.setString(14, gys[3]);
				}
			}
			ps.setString(4, purchaseorder.getName());
			ps.setString(5, purchaseorder.getMechanismId());
			ps.setString(6, purchaseorder.getMechanismName());
			ps.setString(7, purchaseorder.getCreateId());
			ps.setString(8, purchaseorder.getCreateName());
			ps.setString(9, purchaseorder.getCreateDate());
			ps.setString(10, purchaseorder.getUpdateId());
			ps.setString(11, purchaseorder.getUpdateName());
			ps.setString(12, purchaseorder.getUpdateDate());
			rv = ps.executeUpdate(); 
 
			String icgdmxSQL = "insert into inquirydetail (id,product_id,product_name,bar_code,quantity,price,create_id,create_name,create_date,update_id,update_name,update_date,quoted_price,inquiry_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps1 = conn.prepareStatement(icgdmxSQL);
			for (int i = 0; i < inquiryProducts.size(); i++) {
				InquiryProduct inquiryProduct = inquiryProducts.get(i);
				ps1.setString(1, UUID.randomUUID().toString());
				ps1.setString(2, inquiryProduct.getProductId());
				ps1.setString(3, inquiryProduct.getProductName());
				ps1.setString(4, inquiryProduct.getBarCode());
				ps1.setBigDecimal(5, inquiryProduct.getQuantity());
				ps1.setBigDecimal(6, inquiryProduct.getPrice());
				ps1.setString(7, purchaseorder.getCreateId());
				ps1.setString(8, purchaseorder.getCreateName());
				ps1.setString(9, purchaseorder.getCreateDate());
				ps1.setString(10, purchaseorder.getUpdateId());
				ps1.setString(11, purchaseorder.getUpdateName());
				ps1.setString(12, purchaseorder.getUpdateDate());
				ps1.setBigDecimal(13, inquiryProduct.getQuotedPrice());
				ps1.setString(14, id);
				ps1.addBatch();

			}
			ps1.executeBatch();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("saveInquiry(Inquiry purchaseorder, List<InquiryProduct> inquiryProducts)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return rv;
	}


	@Override
	public int update(Inquiry purchaseorder, List<InquiryProduct> inquiryProducts) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			if(purchaseorder.getType().equals("是")) {
				String id=UUID.randomUUID().toString();
				String quote = "INSERT INTO quote(create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,mechanism_name,customer_id,customer_name,app_id,id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				ps2 = conn.prepareStatement(quote);
				ps2.setString(1, purchaseorder.getCreateId());
				ps2.setString(2, purchaseorder.getCreateName());
				ps2.setString(3, df.format(new Date()));
				ps2.setString(4, "");
				ps2.setString(5, "");
				ps2.setString(6, "");
				ps2.setString(7, purchaseorder.getMechanismId());
				ps2.setString(8, purchaseorder.getMechanismName());
				ps2.setString(9, "");
				ps2.setString(10, "");
				ps2.setString(11, "");
				ps2.setString(12, id);
				ps2.executeUpdate();
				String upurchaseorderSQL = "update inquiry set update_date=?,supplier_id=?,supplier_name=?,name=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=? where 1=1 and id=? ";
				ps = conn.prepareStatement(upurchaseorderSQL);
				ps.setString(1, purchaseorder.getUpdateDate());
				if(purchaseorder.getSupplierId()==null || purchaseorder.getSupplierId().equals("")) {
					ps.setString(2,"");
					ps.setString(3,"");
				}else {
					String[] gys=purchaseorder.getSupplierId().split("!_");
					ps.setString(2, gys[0]);
					ps.setString(3, gys[1]);
				}
				ps.setString(4, purchaseorder.getName());
				ps.setString(5, purchaseorder.getCreateId());
				ps.setString(6, purchaseorder.getCreateName());
				ps.setString(7, purchaseorder.getCreateDate());
				ps.setString(8, purchaseorder.getUpdateId());
				ps.setString(9, purchaseorder.getUpdateName());
				ps.setString(10, purchaseorder.getId());
				rv = ps.executeUpdate();

				String dcgdmxSQL = "delete from inquirydetail where inquiry_id=? ";
				ps1 = conn.prepareStatement(dcgdmxSQL);
				ps1.setString(1, purchaseorder.getId());
				ps1.executeUpdate();

				String icgdmxSQL = "insert into inquirydetail (id,product_id,product_name,bar_code,quantity,price,create_id,create_name,create_date,update_id,update_name,update_date,quoted_price,inquiry_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps2 = conn.prepareStatement(icgdmxSQL);
				for (int i = 0; i < inquiryProducts.size(); i++) {
					InquiryProduct inquiryProduct = inquiryProducts.get(i);
					String iquoteSQL = "INSERT INTO quotedetail(id,quote_id,product_id,product_name,bar_code,price,create_id,create_name,create_date,update_id,update_name,update_date,image,quantity)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					ps3 = conn.prepareStatement(iquoteSQL);
					ps3.setString(1, UUID.randomUUID().toString());
					ps3.setString(2, id);
					ps3.setString(3, inquiryProduct.getProductId());
					ps3.setString(4, inquiryProduct.getProductName());
					ps3.setString(5, inquiryProduct.getBarCode());
					ps3.setBigDecimal(6, inquiryProduct.getQuotedPrice());
					ps3.setString(7, purchaseorder.getCreateId());
					ps3.setString(8, purchaseorder.getCreateName());
					ps3.setString(9, df.format(new Date()));
					ps3.setString(10, purchaseorder.getUpdateId());
					ps3.setString(11, purchaseorder.getUpdateName());
					ps3.setString(12, purchaseorder.getUpdateDate());
					ps3.setString(13, "");
					ps3.setBigDecimal(14, inquiryProduct.getQuantity());
					ps3.executeUpdate();
					ps2.setString(1, UUID.randomUUID().toString());
					ps2.setString(2, inquiryProduct.getProductId());
					ps2.setString(3, inquiryProduct.getProductName());
					ps2.setString(4, inquiryProduct.getBarCode());
					ps2.setBigDecimal(5, inquiryProduct.getQuantity());
					ps2.setBigDecimal(6, inquiryProduct.getPrice());
					ps2.setString(7, purchaseorder.getCreateId());
					ps2.setString(8, purchaseorder.getCreateName());
					ps2.setString(9, purchaseorder.getCreateDate());
					ps2.setString(10, purchaseorder.getUpdateId());
					ps2.setString(11, purchaseorder.getUpdateName());
					ps2.setString(12, purchaseorder.getUpdateDate());
					ps2.setBigDecimal(13, inquiryProduct.getQuotedPrice());
					ps2.setString(14, purchaseorder.getId());
					ps2.addBatch();

				}
				ps2.executeBatch();
			}else {

				String upurchaseorderSQL = "update inquiry set update_date=?,supplier_id=?,supplier_name=?,name=?,mechanism_id=?,mechanism_name=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=? where 1=1 and id=? ";
				ps = conn.prepareStatement(upurchaseorderSQL);
				ps.setString(1, purchaseorder.getUpdateDate());
				if(purchaseorder.getSupplierId()==null || purchaseorder.getSupplierId().equals("")) {
					ps.setString(2,"");
					ps.setString(3,"");
				}else {
					String[] gys=purchaseorder.getSupplierId().split("!_");
					ps.setString(2, gys[0]);
					ps.setString(3, gys[1]);
				}
				ps.setString(4, purchaseorder.getName());
				ps.setString(5, purchaseorder.getMechanismId());
				ps.setString(6, purchaseorder.getMechanismName());
				ps.setString(7, purchaseorder.getCreateId());
				ps.setString(8, purchaseorder.getCreateName());
				ps.setString(9, purchaseorder.getCreateDate());
				ps.setString(10, purchaseorder.getUpdateId());
				ps.setString(11, purchaseorder.getUpdateName());
				ps.setString(12, purchaseorder.getId());
				rv = ps.executeUpdate();

				String dcgdmxSQL = "delete from inquirydetail where inquiry_id=? ";
				ps1 = conn.prepareStatement(dcgdmxSQL);
				ps1.setString(1, purchaseorder.getId());
				ps1.executeUpdate();

				String icgdmxSQL = "insert into inquirydetail (id,product_id,product_name,bar_code,quantity,price,create_id,create_name,create_date,update_id,update_name,update_date,quoted_price,inquiry_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps2 = conn.prepareStatement(icgdmxSQL);
				for (int i = 0; i < inquiryProducts.size(); i++) {
					InquiryProduct inquiryProduct = inquiryProducts.get(i);
					ps2.setString(1, UUID.randomUUID().toString());
					ps2.setString(2, inquiryProduct.getProductId());
					ps2.setString(3, inquiryProduct.getProductName());
					ps2.setString(4, inquiryProduct.getBarCode());
					ps2.setBigDecimal(5, inquiryProduct.getQuantity());
					ps2.setBigDecimal(6, inquiryProduct.getPrice());
					ps2.setString(7, purchaseorder.getCreateId());
					ps2.setString(8, purchaseorder.getCreateName());
					ps2.setString(9, purchaseorder.getCreateDate());
					ps2.setString(10, purchaseorder.getUpdateId());
					ps2.setString(11, purchaseorder.getUpdateName());
					ps2.setString(12, purchaseorder.getUpdateDate());
					ps2.setBigDecimal(13, inquiryProduct.getQuotedPrice());
					ps2.setString(14, purchaseorder.getId());
					ps2.addBatch();

				}
				ps2.executeBatch();
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("update(Inquiry purchaseorder, List<InquiryProduct> inquiryProducts)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return rv;
	}
	
	

}