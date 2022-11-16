package dafengqi.yunxiang.dao.hibernate;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.QuoteDao;
import dafengqi.yunxiang.model.Product;
import dafengqi.yunxiang.model.Quote;
import dafengqi.yunxiang.model.QuoteProduct;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("quoteDao") 
public class QuoteDaoHibernate extends GenericDaoHibernate<Quote, Long> implements QuoteDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);
	public QuoteDaoHibernate() {
		super(Quote.class);
	}
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String rq = new SimpleDateFormat("yyyyMMdd").format(new Date());

	@Override
	public List<Quote> getItems(Quote purchaseorder) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		List<Quote> list = new ArrayList<Quote>();
		String mechanismId=purchaseorder.getMechanismId();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "";
			if(purchaseorder.getCreateName().equals("admin")){
				sql = " SELECT id,customer_id,customer_name,ppt,excel,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,relation_mechanism_id FROM  quote    where 1=1 order by create_date desc ";
			}else{	
				sql = " SELECT id,customer_id,customer_name,ppt,excel,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,relation_mechanism_id FROM  quote    where 1=1 and (mechanism_id=? or relation_mechanism_id=?) order by create_date desc ";
			}
			ps = conn.prepareStatement(sql);
			if(purchaseorder.getCreateName().equals("admin")){
				
			}else{
				ps.setString(1, purchaseorder.getMechanismId());
				ps.setString(2, purchaseorder.getMechanismId());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				purchaseorder = new Quote();
				purchaseorder.setId(rs.getString(1));
				purchaseorder.setCustomerId(rs.getString(2));
				purchaseorder.setCustomerName(rs.getString(3));
				purchaseorder.setPpt(rs.getString(4));
				purchaseorder.setExcel(rs.getString(5));
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
			System.out.println("List<Quote> getItems(Quote purchaseorder)" + (t2 - t1) + " ms");
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
	public Quote edit(Quote purchaseorder) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		List<Quote> list = new ArrayList<Quote>();
		QuoteProduct quoteProduct = new QuoteProduct();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String mechanismId = purchaseorder.getMechanismId();
			String sql = " SELECT id,customer_id,customer_name,ppt,excel,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,relation_mechanism_id,mechanism_id FROM  quote    where 1=1 and id=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, purchaseorder.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				purchaseorder = new Quote();
				purchaseorder.setId(rs.getString(1));
				if(rs.getString(13)==null) {
					purchaseorder.setCustomerId(rs.getString(2)+"!_"+rs.getString(3)+"!_"+"无"+"!_"+"无");
				}else {
					if(rs.getString(13).equals("")) {
						purchaseorder.setCustomerId(rs.getString(2)+"!_"+rs.getString(3)+"!_"+"无"+"!_"+"无");
					}else {
						purchaseorder.setCustomerId(rs.getString(2)+"!_"+rs.getString(3)+"!_"+rs.getString(13)+"!_"+rs.getString(14));
					}
					
				}
				purchaseorder.setCustomerName(rs.getString(3));
				purchaseorder.setPpt(rs.getString(4));
				purchaseorder.setExcel(rs.getString(5));
				purchaseorder.setMechanismName(rs.getString(6));
				purchaseorder.setCreateId(rs.getString(7));
				purchaseorder.setCreateName(rs.getString(8));
				purchaseorder.setCreateDate(rs.getString(9));
				purchaseorder.setUpdateId(rs.getString(10));
				purchaseorder.setUpdateName(rs.getString(11));
				purchaseorder.setUpdateDate(rs.getString(12));
				purchaseorder.setMechanismId(rs.getString(14));
			}
			String cgdmxSQL = " SELECT id,product_id,product_name,bar_code,quantity,price,create_id,create_name,create_date,update_id,update_name,update_date,image FROM quotedetail  where 1=1 and quote_id=? ";
			ps1 = conn.prepareStatement(cgdmxSQL);
			ps1.setString(1, purchaseorder.getId());
			rs1 = ps1.executeQuery();
			List<QuoteProduct> quoteProducts = new ArrayList<QuoteProduct>();
			while (rs1.next()) {
				quoteProduct = new QuoteProduct();
				quoteProduct.setId(rs1.getString(1));
				quoteProduct.setProductId(rs1.getString(2));
				quoteProduct.setProductName(rs1.getString(3));
				quoteProduct.setBarCode(rs1.getString(4));
				quoteProduct.setQuantity(rs1.getBigDecimal(5));
				quoteProduct.setPrice(rs1.getBigDecimal(6));
				quoteProduct.setCreateId(rs1.getString(7));
				quoteProduct.setCreateName(rs1.getString(8));
				quoteProduct.setCreateDate(rs1.getString(9));
				quoteProduct.setUpdateId(rs1.getString(10));
				quoteProduct.setUpdateName(rs1.getString(11));
				quoteProduct.setUpdateDate(rs1.getString(12));
				quoteProducts.add(quoteProduct);
			}

			purchaseorder.setQuoteProductList(quoteProducts);
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("Quote edit(Quote purchaseorder)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
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
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return purchaseorder;
	}

	@Override
	public int saveQuote(Quote purchaseorder, List<QuoteProduct> quoteProducts) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int rv = 0;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			  
			String ipurchaseorderSQL = "INSERT INTO quote(id,customer_id,customer_name,ppt,mechanism_id,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,relation_mechanism_id,relation_mechanism_name,excel)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(ipurchaseorderSQL);
			String id=UUID.randomUUID().toString();
			ps.setString(1, id);
			if(purchaseorder.getCustomerId()==null || purchaseorder.getCustomerId().equals("")) {
				ps.setString(2,"");
				ps.setString(3,"");
			}else {
				String[] gys=purchaseorder.getCustomerId().split("!_");
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
			ps.setString(4, purchaseorder.getPpt());
			ps.setString(5, purchaseorder.getMechanismId());
			ps.setString(6, purchaseorder.getMechanismName());
			ps.setString(7, purchaseorder.getCreateId());
			ps.setString(8, purchaseorder.getCreateName());
			ps.setString(9, purchaseorder.getCreateDate());
			ps.setString(10, purchaseorder.getUpdateId());
			ps.setString(11, purchaseorder.getUpdateName());
			ps.setString(12, purchaseorder.getUpdateDate());
			ps.setString(15, purchaseorder.getExcel());
			rv = ps.executeUpdate(); 

			 XSSFWorkbook workbook = new XSSFWorkbook();
			    
			 CellStyle cellStyle = workbook.createCellStyle();
			    cellStyle.setWrapText(true);
//			    cellStyle.setBorderBottom(BorderStyle.THIN);
//			    cellStyle.setBorderLeft(BorderStyle.THIN);
//			    cellStyle.setBorderRight(BorderStyle.THIN);
//			    cellStyle.setBorderTop(BorderStyle.THIN); 
			    cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置水平居中
			    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直居中
			    
			    Font font = workbook.createFont();
			    font.setFontName("宋体");
			    font.setFontHeightInPoints((short) 12);
			    cellStyle.setFont(font);
			    
			    
			    XSSFSheet sheet = workbook.createSheet("产品");

			    Row row0 = sheet.createRow(0);
//			    sheet.autoSizeColumn(1, true);
			    Cell cell0 = row0.createCell(0);
			    cell0.setCellValue("序号");
			    Cell cell1 = row0.createCell(1);
			    cell1.setCellValue("品牌");
			    Cell cell2 = row0.createCell(2);
			    cell2.setCellValue("产品名称");
			    Cell cell21 = row0.createCell(3);
			    cell21.setCellValue("型号");
			    Cell cell3 = row0.createCell(4);
			    cell3.setCellValue("吊牌价(零售价)");
			    Cell cell4 = row0.createCell(5);
			    cell4.setCellValue("产品图片");
			    Cell cell5 = row0.createCell(6);
			    cell5.setCellValue("详细信息");
			    Cell cell6 = row0.createCell(7);
			    cell6.setCellValue("条码");
			    Cell cell7 = row0.createCell(8);
			    cell7.setCellValue("颜色");
			    cell7.setCellStyle(cellStyle);
			    Cell cell8 = row0.createCell(9);
			    cell8.setCellValue("经销价（含税不含运）");
			    cell8.setCellStyle(cellStyle);
			    Cell cell9 = row0.createCell(10);
			    cell9.setCellValue("运费");
			    cell9.setCellStyle(cellStyle);
			    Cell cell10 = row0.createCell(11);
			    cell10.setCellValue("一件代发含运费");
			    cell10.setCellStyle(cellStyle);
			    Cell cell11 = row0.createCell(12);
			    cell11.setCellValue("电商售价");
			    cell11.setCellStyle(cellStyle);
			    Cell cell12 = row0.createCell(13);
			    cell12.setCellValue("建议售价（参考价）");
			    cell12.setCellStyle(cellStyle);
			    Cell cell13 = row0.createCell(14);
			    cell13.setCellValue("京东价格");
			    cell13.setCellStyle(cellStyle);
			    Cell cell14 = row0.createCell(15);
			    cell14.setCellValue("京东链接");
			    cell14.setCellStyle(cellStyle);
			    Cell cell15 = row0.createCell(16);
			    cell15.setCellValue("京东来源");
			    cell15.setCellStyle(cellStyle);
			    Cell cell16 = row0.createCell(17);
			    cell16.setCellValue("天猫价格");
			    cell16.setCellStyle(cellStyle);
			    Cell cell17 = row0.createCell(18);
			    cell17.setCellValue("天猫链接");
			    cell17.setCellStyle(cellStyle);
			    Cell cell18 = row0.createCell(19);
			    cell18.setCellValue("天猫来源");
			    cell18.setCellStyle(cellStyle);
			    Cell cell19 = row0.createCell(20);
			    cell19.setCellValue("产品分类");
			    cell19.setCellStyle(cellStyle);
			    Cell cell20 = row0.createCell(21);
			    cell20.setCellValue("规格");
			    cell20.setCellStyle(cellStyle);
			    Cell cell211 = row0.createCell(22);
			    cell211.setCellValue("材质");
			    cell211.setCellStyle(cellStyle);
			    Cell cell222 = row0.createCell(23);
			    cell222.setCellValue("单品毛量（kg）");
			    cell222.setCellStyle(cellStyle);
			    Cell cell223 = row0.createCell(24);
			    cell223.setCellValue("装箱数量");
			    cell223.setCellStyle(cellStyle);
			    Cell cell224 = row0.createCell(25);
			    cell224.setCellValue("备注");
			    cell224.setCellStyle(cellStyle);
			    sheet.setColumnWidth(3, 20*256);
			    sheet.setColumnWidth(5, 18*256);
			
			XMLSlideShow ppt = new XMLSlideShow();
	        String slideWidth = "35.36";
	        String slideHeight = "21.21";
//	      设置幻灯片大小
	        ppt.setPageSize(new Dimension(Double.valueOf(Double.valueOf(slideWidth) / 3.53 * 100).intValue(), Double.valueOf(Double.valueOf(slideHeight) / 3.53 * 100).intValue()));
			String icgdmxSQL = "insert into quotedetail (id,product_id,product_name,bar_code,quantity,price,create_id,create_name,create_date,update_id,update_name,update_date,quote_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps1 = conn.prepareStatement(icgdmxSQL);
		    int c=0;
			for (int i = 0; i < quoteProducts.size(); i++) {
	        	c++;
				QuoteProduct quoteProduct = quoteProducts.get(i);
				ps1.setString(1, UUID.randomUUID().toString());
				ps1.setString(2, quoteProduct.getProductId());
				ps1.setString(3, quoteProduct.getProductName());
				ps1.setString(4, quoteProduct.getBarCode());
				ps1.setBigDecimal(5, quoteProduct.getQuantity());
				ps1.setBigDecimal(6, quoteProduct.getPrice());
				ps1.setString(7, purchaseorder.getCreateId());
				ps1.setString(8, purchaseorder.getCreateName());
				ps1.setString(9, purchaseorder.getCreateDate());
				ps1.setString(10, purchaseorder.getUpdateId());
				ps1.setString(11, purchaseorder.getUpdateName());
				ps1.setString(12, purchaseorder.getUpdateDate());
				ps1.setString(13, id);
				ps1.addBatch();

	        	String sql1 = "SELECT id,bar_code,title,category_id,IFNULL(category_name,''),details,unit_price,retail_price,membership_price,purchase_price,discount_price,reference_price,stock,freight_template_id,freight_template_name,product_type_id,product_type_Name,second_hand,customized,rotation_image,brand_id,brand_name,pre_sale,promise,return_and_exchange,mechanism_id,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,scroll_image,label,IFNULL(specification,''),IFNULL(model,''),IFNULL(texture,''),IFNULL(weight,''),IFNULL(color,''),IFNULL(distribution_price,''),IFNULL(freight,''),IFNULL(undertakes_price,''),IFNULL(electricity_price,''),IFNULL(jingdong_price,''),IFNULL(tmall_price,''),IFNULL(jingdong_link,''),IFNULL(tmall_link,''),IFNULL(jingdong_source,''),IFNULL(tmall_source,''),IFNULL(packing_quantity,''),IFNULL(remarks,'') FROM  product    where 1=1 and id=? and mechanism_id=? order by create_date desc ";
				ps3 = conn.prepareStatement(sql1);
				ps3.setString(1, quoteProduct.getProductId());
				ps3.setString(2, purchaseorder.getMechanismId());
				rs1 = ps3.executeQuery();
				Product product = new Product();
				while (rs1.next()) {
					product = new Product();
					product.setId(rs1.getString(1));
					product.setBarCode(rs1.getString(2));
					product.setTitle(rs1.getString(3));
					product.setCategoryId(rs1.getString(4)+"!_"+rs1.getString(5));
					product.setCategoryName(rs1.getString(5));
					product.setDetails(rs1.getString(6));
					product.setUnitPrice(rs1.getBigDecimal(7));
					product.setRetailPrice(rs1.getBigDecimal(8));
					product.setMembershipPrice(rs1.getBigDecimal(9));
					product.setPurchasePrice(rs1.getBigDecimal(10));
					product.setDiscountPrice(rs1.getBigDecimal(11));
					product.setReferencePrice(rs1.getBigDecimal(12));
					product.setStock(rs1.getBigDecimal(13));
					product.setFreightTemplateId(rs1.getString(14));
					product.setFreightTemplateName(rs1.getString(15));
					product.setProductTypeId(rs1.getString(16)+"!_"+rs1.getString(17));
					product.setProductTypeName(rs1.getString(17));
					product.setSecondHand(rs1.getString(18));
					product.setCustomized(rs1.getString(19));
					product.setRotationImage(rs1.getString(20));
					product.setBrandId(rs1.getString(21)+"!_"+rs1.getString(22));
					product.setBrandName(rs1.getString(22));
					product.setPreSale(rs1.getString(23));
					product.setPromise(rs1.getString(24));
					product.setReturnAndExchange(rs1.getString(25));
					product.setMechanismId(rs1.getString(26));
					product.setMechanismName(rs1.getString(27));
					product.setCreateId(rs1.getString(28));
					product.setCreateName(rs1.getString(29));
					product.setCreateDate(rs1.getString(30));
					product.setUpdateId(rs1.getString(31));
					product.setUpdateName(rs1.getString(32));
					product.setUpdateDate(rs1.getString(33));
					product.setScrollImage(rs1.getString(34));
					product.setLabels(rs1.getString(35));
					product.setSpecification(rs1.getString(36));
					product.setModel(rs1.getString(37));
					product.setTexture(rs1.getString(38));
					product.setWeight(rs1.getString(39));
					product.setColor(rs1.getString(40));
					product.setDistributionPrice(rs1.getBigDecimal(41));
//					product.setFreight(rs4.getString(42));
					product.setUndertakesPrice(rs1.getBigDecimal(43));
					product.setElectricityPrice(rs1.getBigDecimal(44));
					product.setJingdongPice(rs1.getBigDecimal(45));
					product.setTmallPrice(rs1.getBigDecimal(46));
					product.setJingdongLink(rs1.getString(47));
					product.setTmallLink(rs1.getString(48));
					product.setJingdongSource(rs1.getString(49));
					product.setTmallSource(rs1.getString(50));
					product.setPackingQuantity(rs1.getBigDecimal(51));
					product.setRemarks(rs1.getString(52));
				}
				String sql = "SELECT scroll_image FROM  product    where 1=1 and mechanism_id=? and id=? order by create_date desc ";
				ps2 = conn.prepareStatement(sql);
				ps2.setString(1, purchaseorder.getMechanismId());
				ps2.setString(2, quoteProduct.getProductId());
				rs = ps2.executeQuery();
				String image="";
				while (rs.next()) {
					if(rs.getString(1)==null){
						
					}else{
						if(rs.getString(1).equals("")){
							
						}else{
							String[] gdtpArrs=rs.getString(1).split(";");
							for (int ic = 0; ic < gdtpArrs.length; ic++) {
								System.out.println("sortOrder："+gdtpArrs[0]);
								image=gdtpArrs[0];
								break;
							}
						}
					}
				}

				Row row1 = sheet.createRow(c);
			    row1.setHeight((short)(60*20));
			    Cell cell01 = row1.createCell(0);
			    cell01.setCellValue(c);
			    cell01.setCellStyle(cellStyle);
			    Cell cell121 = row1.createCell(1);
			    cell121.setCellValue(product.getBrandName());
			    cell121.setCellStyle(cellStyle);
			    Cell cell23 = row1.createCell(2);
			    cell23.setCellValue(product.getTitle());
			    cell23.setCellStyle(cellStyle);
			    Cell cell214 = row1.createCell(3);
			    cell214.setCellValue(product.getModel());
			    cell214.setCellStyle(cellStyle);
			    Cell cell35 = row1.createCell(4);
			    cell35.setCellValue(""+product.getRetailPrice());	
			    cell35.setCellStyle(cellStyle);
//				
				String filename="C:\\图片下载/baise.png";
			    if(image==null){
			    }else{
			    	if(image.equals("")){
			    	}else{
			    		if(image.equals("null")){
			    			
			    		}else{
					         // 构造URL
					            URL url = new URL(image);
					         // 打开连接
					            URLConnection con = url.openConnection();
					            // 输入流
					            InputStream is = con.getInputStream();
					            // 1K的数据缓冲
					            byte[] bs = new byte[1024];
					            // 读取到的数据长度
					            int len;
					            // 输出的文件流
					            filename = "C:\\图片下载/" + (int)((Math.random()*9+1)*100000)+purchaseorder.getMechanismId() + ".png";  //下载路径及下载图片名称
//						            String filename = "C:\\图片下载/" + random.nextInt(1000)+mechanismId + ".png";  //下载路径及下载图片名称
					            File file = new File(filename);
					            FileOutputStream os = new FileOutputStream(file, true);
					            // 开始读取
					            while ((len = is.read(bs)) != -1) {
					                os.write(bs, 0, len);
					            }
					            System.out.println(i);
					            // 完毕，关闭所有链接
					            os.close();
					            is.close();
			    		}
			    	}
			    }
	            
	            FileInputStream fis = new FileInputStream(filename);
	         // 向Excel中添加一张图片，并返回改图片在Excel中的图片集中的索引
	            int pictureIndex = ((XSSFWorkbook) workbook).addPicture(fis, XSSFWorkbook.PICTURE_TYPE_PNG);
	            // 获取工作簿的绘图工具类
	            CreationHelper creationHelper = workbook.getCreationHelper();
	            // 创建一个绘图对象
	            Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
	            // 创建锚点，设置图片坐标
	            ClientAnchor clientAnchor = creationHelper.createClientAnchor();
	            
	            clientAnchor.setRow1(c);
	            clientAnchor.setCol1(5);
	            // 创建图片
	            Picture picture = drawingPatriarch.createPicture(clientAnchor, pictureIndex);
	            double a = 1;
	            double b = 1;
	            picture.resize(a,b);

//	            FileOutputStream fileOutputStream = new FileOutputStream("D:\测试Excel中绘制图片.xlsx");
//	            workbook.write(fileOutputStream);
//	            fileOutputStream.close();
//	            
//	             BufferedImage bufferedImage = ImageIO.read(file) ;
//	             ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//	             ImageIO.write(bufferedImage, "jpg", byteArrayOut);
//	             byte[] data = byteArrayOut.toByteArray();
//	             XSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch();
//	             XSSFClientAnchor anchor = new XSSFClientAnchor(480, 30, 700, 250, (short)0, i + 1, (short) 1, i + 2);
//	             drawingPatriarch.createPicture(anchor, workbook.addPicture(data, XSSFWorkbook.PICTURE_TYPE_PNG));
//	             sheet.setColumnWidth((short)500, (short)500);
//			    
//			    Cell cell46 = row1.createCell(5);
//			    cell46.setCellValue("image");
			    Cell cell57 = row1.createCell(6);
			    cell57.setCellValue(product.getDetails());
			    cell57.setCellStyle(cellStyle);
			    Cell cell68 = row1.createCell(7);
			    cell68.setCellValue(product.getBarCode());
			    cell68.setCellStyle(cellStyle);
			    Cell cell79 = row1.createCell(8);
			    cell79.setCellValue(product.getColor());
			    cell79.setCellStyle(cellStyle);
			    Cell cell810 = row1.createCell(9);
			    cell810.setCellValue(""+product.getDistributionPrice());
			    cell810.setCellStyle(cellStyle);
			    Cell cell911 = row1.createCell(10);
			    cell911.setCellValue(""+product.getFreight());
			    cell911.setCellStyle(cellStyle);
			    Cell cell1012 = row1.createCell(11);
			    cell1012.setCellValue(""+product.getUndertakesPrice());
			    cell1012.setCellStyle(cellStyle);
			    Cell cell1113 = row1.createCell(12);
			    cell1113.setCellValue(""+product.getElectricityPrice());
			    cell1113.setCellStyle(cellStyle);
			    Cell cell1214 = row1.createCell(13);
			    cell1214.setCellValue(""+product.getReferencePrice());
			    cell1214.setCellStyle(cellStyle);
			    Cell cell1215 = row1.createCell(14);
			    cell1215.setCellValue(""+product.getJingdongPice());
			    cell1215.setCellStyle(cellStyle);
			    Cell cell1216 = row1.createCell(15);
			    cell1216.setCellValue(""+product.getJingdongLink());
			    cell1216.setCellStyle(cellStyle);
			    Cell cell1217 = row1.createCell(16);
			    cell1217.setCellValue(""+product.getJingdongSource());
			    cell1217.setCellStyle(cellStyle);
			    Cell cell1218 = row1.createCell(17);
			    cell1218.setCellValue(""+product.getTmallPrice());
			    cell1218.setCellStyle(cellStyle);
			    Cell cell1219 = row1.createCell(18);
			    cell1219.setCellValue(""+product.getTmallLink());
			    cell1219.setCellStyle(cellStyle);
			    Cell cell1220 = row1.createCell(19);
			    cell1220.setCellValue(""+product.getTmallSource());
			    cell1220.setCellStyle(cellStyle);
			    Cell cell1221 = row1.createCell(20);
			    cell1221.setCellValue(""+product.getCategoryName());
			    cell1221.setCellStyle(cellStyle);
			    Cell cell1222 = row1.createCell(21);
			    cell1222.setCellValue(""+product.getSpecification());
			    cell1222.setCellStyle(cellStyle);
			    Cell cell1223 = row1.createCell(22);
			    cell1223.setCellValue(""+product.getTexture());
			    cell1223.setCellStyle(cellStyle);
			    Cell cell1224 = row1.createCell(23);
			    cell1224.setCellValue(""+product.getWeight());
			    cell1224.setCellStyle(cellStyle);
			    Cell cell1225 = row1.createCell(24);
			    cell1225.setCellValue(""+product.getPackingQuantity());
			    cell1225.setCellStyle(cellStyle);
			    Cell cell1226 = row1.createCell(25);
			    cell1226.setCellValue(""+product.getRemarks());
			    cell1226.setCellStyle(cellStyle);
				String filenames="C:\\图片下载/baise.png";
			    if(image==null){
			    }else{
			    	if(image.equals("")){
			    	}else{
			    		if(image.equals("null")){
			    			
			    		}else{

					         // 构造URL
					            URL urls = new URL(image);
					            // 打开连接
					            URLConnection cons = urls.openConnection();
					            // 输入流
					            InputStream isss = cons.getInputStream();
					            // 1K的数据缓冲
					            byte[] bss = new byte[1024];
					            // 读取到的数据长度
					            int lens;
					            // 输出的文件流
//					            String filename = "C:\\图片下载/" + random.nextInt(1000)+mechanismId + ".jpg";  //下载路径及下载图片名称
					            filenames = "D:\\图片下载/" + (int)((Math.random()*9+1)*100000)+purchaseorder.getMechanismId() + ".jpg";  //下载路径及下载图片名称
					            File files = new File(filenames);
					            FileOutputStream oss = new FileOutputStream(files, true);
					            // 开始读取
					            while ((lens = isss.read(bss)) != -1) {
					                oss.write(bss, 0, lens);
					            }
					            System.out.println(i);
					            // 完毕，关闭所有链接
					            oss.close();
					            isss.close();
			    		}
			    	}
			    }
		            
		            XSLFSlide slide = ppt.createSlide();
			        /** 生成二进制数组 **/
			        String imagePath = filenames;
			        InputStream iss = new FileInputStream(imagePath);
			        byte[] bytes = IOUtils.toByteArray(iss);
			        XSLFPictureData idx = ppt.addPicture(bytes, XSLFPictureData.PictureType.PNG);
			        XSLFPictureShape pictures = slide.createPicture(idx);//设置母版
			          String wid = "21.17";  //水平位置
			          String hei = "21.17"; //垂直位置
			        pictures.setAnchor(new Rectangle2D.Double(0, 0, Double.valueOf(wid) / 3.53 * 100 , Double.valueOf(hei) / 3.53 * 100));
//			        slide.createPicture(idx);
			          XSLFTextBox textBox = slide.createTextBox();
//		            byte[] bt2s = FileUtils.readFileToByteArray(new File(filename));
//		            XSLFPictureData idx2s = ppt.addPicture(bt2s, XSLFPictureData.PictureType.PNG);
			          String x = "21.47";  //水平位置
			          String y = "2.73"; //垂直位置
			          String w = "12.45"; //宽度
			          String h = "0.50";  //高度
			          textBox.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun = textBox.setText(product.getTitle());
			          textRun.setFontFamily("宋体");
			          textRun.setFontSize(18.0);
			          textRun.setBold(true); //设置粗体状态
			          XSLFTextBox textBox1 = slide.createTextBox();
			          String y1 = "4.57"; //垂直位置
			          textBox1.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y1) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun1 = textBox1.setText("条形码:"+product.getBarCode());
			          textRun1.setFontFamily("宋体");
			          textRun1.setFontSize(18.0);
			          textRun1.setBold(true); //设置粗体状态
			          XSLFTextBox textBox2 = slide.createTextBox();
			          String y2 = "7.62"; //垂直位置
			          textBox2.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y2) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun2 = textBox2.setText("品牌:"+product.getBrandName());
			          textRun2.setFontFamily("宋体");
			          textRun2.setFontSize(18.0);
			          XSLFTextBox textBox3 = slide.createTextBox();
			          String y3 = "8.68"; //垂直位置
			          textBox3.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y3) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun3 = textBox3.setText("货号:"+product.getId());
			          textRun3.setFontFamily("宋体");
			          textRun3.setFontSize(18.0);
			          XSLFTextBox textBox4 = slide.createTextBox();
			          String y4 = "9.95"; //垂直位置
			          textBox4.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y4) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun4 = textBox4.setText("零售价:"+product.getRetailPrice());
			          textRun4.setFontFamily("宋体");
			          textRun4.setFontSize(18.0);
			          textRun4.setBold(true); //设置粗体状态
			          XSLFTextBox textBox5 = slide.createTextBox();
			          String y5 = "11.01"; //垂直位置
			          textBox5.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y5) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun5 = textBox5.setText("颜色:"+product.getColor());
			          textRun5.setFontFamily("宋体");
			          textRun5.setFontSize(18.0);
			          XSLFTextBox textBox6 = slide.createTextBox();
			          String y6 = "12.07"; //垂直位置
			          textBox6.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y6) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun6 = textBox6.setText("经销价:"+product.getDistributionPrice());
			          textRun6.setFontFamily("宋体");
			          textRun6.setFontSize(18.0);
			          XSLFTextBox textBox7 = slide.createTextBox();
			          String y7 = "13.08"; //垂直位置
			          textBox7.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y7) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun7 = textBox7.setText("电商售价:"+product.getElectricityPrice());
			          textRun7.setFontFamily("宋体");
			          textRun7.setFontSize(18.0);
			          XSLFTextBox textBox8 = slide.createTextBox();
			          String y8 = "14.09"; //垂直位置
			          textBox8.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y8) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun8 = textBox8.setText("规格:"+product.getSpecification());
			          textRun8.setFontFamily("宋体");
			          textRun8.setFontSize(18.0);
			          XSLFTextBox textBox9 = slide.createTextBox();
			          String y9 = "15.10"; //垂直位置
			          textBox9.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y9) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun9 = textBox9.setText("材质:"+product.getTexture());
			          textRun9.setFontFamily("宋体");
			          textRun9.setFontSize(18.0);
			          XSLFTextBox textBox10 = slide.createTextBox();
			          String y10 = "16.11"; //垂直位置
			          textBox10.setAnchor(new Rectangle2D.Double(Double.valueOf(x) / 3.53 * 100, Double.valueOf(y10) / 3.53 * 100, Double.valueOf(w) / 3.53 * 100, Double.valueOf(h) / 3.53 * 100));
			          XSLFTextRun textRun10 = textBox10.setText("备注:"+product.getRemarks());
			          textRun10.setFontFamily("宋体");
			          textRun10.setFontSize(18.0);
				

			}
			ps1.executeBatch();



		    OutputStream os = null;
		    try {
//		        os = new FileOutputStream("C:\\apache-tomcat-8.5.24\\webapps\\excel\\"+purchaseorder.getExcel()+".xls");
		        os = new FileOutputStream("d:\\"+purchaseorder.getExcel()+".xls");
		        workbook.write(os);
		        os.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
//	        ppt.write(new FileOutputStream("C:\\apache-tomcat-8.5.24\\webapps\\ppt\\"+purchaseorder.getPpt()+".pptx"));
	        ppt.write(new FileOutputStream("d:\\"+purchaseorder.getPpt()+".pptx"));
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("saveQuote(Quote purchaseorder, List<QuoteProduct> quoteProducts)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {ResourceManager.close(conn);}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			e.printStackTrace();
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return rv;
	}


	@Override
	public int update(Quote purchaseorder, List<QuoteProduct> quoteProducts) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
//			if(purchaseorder.getType().equals("是")) {
//				String id=UUID.randomUUID().toString();
//				String quote = "INSERT INTO quote(create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,mechanism_name,customer_id,customer_name,app_id,id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
//				ps2 = conn.prepareStatement(quote);
//				ps2.setString(1, purchaseorder.getCreateId());
//				ps2.setString(2, purchaseorder.getCreateName());
//				ps2.setString(3, df.format(new Date()));
//				ps2.setString(4, "");
//				ps2.setString(5, "");
//				ps2.setString(6, "");
//				ps2.setString(7, purchaseorder.getMechanismId());
//				ps2.setString(8, purchaseorder.getMechanismName());
//				ps2.setString(9, "");
//				ps2.setString(10, "");
//				ps2.setString(11, "");
//				ps2.setString(12, id);
//				ps2.executeUpdate();
//				String upurchaseorderSQL = "update quote set update_date=?,supplier_id=?,supplier_name=?,name=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=? where 1=1 and id=? ";
//				ps = conn.prepareStatement(upurchaseorderSQL);
//				ps.setString(1, purchaseorder.getUpdateDate());
//				if(purchaseorder.getSupplierId()==null || purchaseorder.getSupplierId().equals("")) {
//					ps.setString(2,"");
//					ps.setString(3,"");
//				}else {
//					String[] gys=purchaseorder.getSupplierId().split("!_");
//					ps.setString(2, gys[0]);
//					ps.setString(3, gys[1]);
//				}
//				ps.setString(4, purchaseorder.getName());
//				ps.setString(5, purchaseorder.getCreateId());
//				ps.setString(6, purchaseorder.getCreateName());
//				ps.setString(7, purchaseorder.getCreateDate());
//				ps.setString(8, purchaseorder.getUpdateId());
//				ps.setString(9, purchaseorder.getUpdateName());
//				ps.setString(10, purchaseorder.getId());
//				rv = ps.executeUpdate();
//
//				String dcgdmxSQL = "delete from quotedetail where quote_id=? ";
//				ps1 = conn.prepareStatement(dcgdmxSQL);
//				ps1.setString(1, purchaseorder.getId());
//				ps1.executeUpdate();
//
//				String icgdmxSQL = "insert into quotedetail (id,product_id,product_name,bar_code,quantity,price,create_id,create_name,create_date,update_id,update_name,update_date,quoted_price,quote_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//				ps2 = conn.prepareStatement(icgdmxSQL);
//				for (int i = 0; i < quoteProducts.size(); i++) {
//					QuoteProduct quoteProduct = quoteProducts.get(i);
//					String iquoteSQL = "INSERT INTO quotedetail(id,quote_id,product_id,product_name,bar_code,price,create_id,create_name,create_date,update_id,update_name,update_date,image,quantity)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//					ps3 = conn.prepareStatement(iquoteSQL);
//					ps3.setString(1, UUID.randomUUID().toString());
//					ps3.setString(2, id);
//					ps3.setString(3, quoteProduct.getProductId());
//					ps3.setString(4, quoteProduct.getProductName());
//					ps3.setString(5, quoteProduct.getBarCode());
//					ps3.setBigDecimal(6, quoteProduct.getQuotedPrice());
//					ps3.setString(7, purchaseorder.getCreateId());
//					ps3.setString(8, purchaseorder.getCreateName());
//					ps3.setString(9, df.format(new Date()));
//					ps3.setString(10, purchaseorder.getUpdateId());
//					ps3.setString(11, purchaseorder.getUpdateName());
//					ps3.setString(12, purchaseorder.getUpdateDate());
//					ps3.setString(13, "");
//					ps3.setBigDecimal(14, quoteProduct.getQuantity());
//					ps3.executeUpdate();
//					ps2.setString(1, UUID.randomUUID().toString());
//					ps2.setString(2, quoteProduct.getProductId());
//					ps2.setString(3, quoteProduct.getProductName());
//					ps2.setString(4, quoteProduct.getBarCode());
//					ps2.setBigDecimal(5, quoteProduct.getQuantity());
//					ps2.setBigDecimal(6, quoteProduct.getPrice());
//					ps2.setString(7, purchaseorder.getCreateId());
//					ps2.setString(8, purchaseorder.getCreateName());
//					ps2.setString(9, purchaseorder.getCreateDate());
//					ps2.setString(10, purchaseorder.getUpdateId());
//					ps2.setString(11, purchaseorder.getUpdateName());
//					ps2.setString(12, purchaseorder.getUpdateDate());
//					ps2.setBigDecimal(13, quoteProduct.getQuotedPrice());
//					ps2.setString(14, purchaseorder.getId());
//					ps2.addBatch();
//
//				}
//				ps2.executeBatch();
//			}else {

				String upurchaseorderSQL = "update quote set update_date=?,customer_id=?,customer_name=?,ppt=?,mechanism_id=?,mechanism_name=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,excel=? where 1=1 and id=? ";
				ps = conn.prepareStatement(upurchaseorderSQL);
				ps.setString(1, purchaseorder.getUpdateDate());
				if(purchaseorder.getCustomerId()==null || purchaseorder.getCustomerId().equals("")) {
					ps.setString(2,"");
					ps.setString(3,"");
				}else {
					String[] gys=purchaseorder.getCustomerId().split("!_");
					ps.setString(2, gys[0]);
					ps.setString(3, gys[1]);
				}
				ps.setString(4, purchaseorder.getPpt());
				ps.setString(5, purchaseorder.getMechanismId());
				ps.setString(6, purchaseorder.getMechanismName());
				ps.setString(7, purchaseorder.getCreateId());
				ps.setString(8, purchaseorder.getCreateName());
				ps.setString(9, purchaseorder.getCreateDate());
				ps.setString(10, purchaseorder.getUpdateId());
				ps.setString(11, purchaseorder.getUpdateName());
				ps.setString(12, purchaseorder.getExcel());
				ps.setString(13, purchaseorder.getId());
				rv = ps.executeUpdate();

				String dcgdmxSQL = "delete from quotedetail where quote_id=? ";
				ps1 = conn.prepareStatement(dcgdmxSQL);
				ps1.setString(1, purchaseorder.getId());
				ps1.executeUpdate();

				String icgdmxSQL = "insert into quotedetail (id,product_id,product_name,bar_code,quantity,price,create_id,create_name,create_date,update_id,update_name,update_date,quote_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps2 = conn.prepareStatement(icgdmxSQL);
				for (int i = 0; i < quoteProducts.size(); i++) {
					QuoteProduct quoteProduct = quoteProducts.get(i);
					ps2.setString(1, UUID.randomUUID().toString());
					ps2.setString(2, quoteProduct.getProductId());
					ps2.setString(3, quoteProduct.getProductName());
					ps2.setString(4, quoteProduct.getBarCode());
					ps2.setBigDecimal(5, quoteProduct.getQuantity());
					ps2.setBigDecimal(6, quoteProduct.getPrice());
					ps2.setString(7, purchaseorder.getCreateId());
					ps2.setString(8, purchaseorder.getCreateName());
					ps2.setString(9, purchaseorder.getCreateDate());
					ps2.setString(10, purchaseorder.getUpdateId());
					ps2.setString(11, purchaseorder.getUpdateName());
					ps2.setString(12, purchaseorder.getUpdateDate());
					ps2.setString(13, purchaseorder.getId());
					ps2.addBatch();

				}
				ps2.executeBatch();
//			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("update(Quote purchaseorder, List<QuoteProduct> quoteProducts)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return rv;
	}
	
	

}