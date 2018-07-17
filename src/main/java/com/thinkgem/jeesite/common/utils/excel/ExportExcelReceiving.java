/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sco.entity.AdminAcceptanceExport;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）,对复杂样式进行处理
 * @author ThinkGem
 * @version 2013-04-21
 */
public class ExportExcelReceiving extends ExportExcel {

	private static Logger log = LoggerFactory.getLogger(ExportExcelReceiving.class);

	public ExportExcelReceiving(String title, Class<?> cls) {
		super(title, cls);
	}

	public ExportExcelReceiving(String title, Class<?> cls, int type, int... groups) {
		super(title, cls, type, groups);
	}

	public ExportExcelReceiving(String title, String[] headers) {
		super(title, headers);
	}

	public ExportExcelReceiving(String title, List<String> headerList) {
		super(title, headerList);
	}

	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * @return list 数据列表
	 */
	public <E> ExportExcel setDataList(List<E> list){
		int start = 0;
		int rowSize = 2;
		int lastRow = 2;

		for (E e : list){
			int colunm = 0;
			Row row = this.addRow();

			AdminAcceptanceExport export = (AdminAcceptanceExport)e;
			List<AdminAcceptanceExport.RowData> innerList = export.getRowDatas();
			Cell cell = this.addCell(row, colunm++, export.getSuppliers(), 2, String.class);

			rowSize += innerList.size();
			start = rowSize - innerList.size();
			lastRow = start + innerList.size() -1;
			if(innerList.size() > 1){
				cell.getSheet().addMergedRegion(new CellRangeAddress(start,lastRow,0,0));
			}

			int index = 0; //innerList索引
			int colunm1 = 0;
			DecimalFormat df = new DecimalFormat("### ##0.00") ;
			for(AdminAcceptanceExport.RowData rowData :innerList){
				if(index == 0){
					this.addCell(row, colunm++, rowData.getMonth(), 2, String.class);
					this.addCell(row, colunm++, rowData.getReceiving(), 2, Integer.class);
					this.addCell(row, colunm++, rowData.getGoodsCount(), 2, Integer.class);

					this.addCell(row, colunm++,  df.format(rowData.getTotalAmt()), 2, Double.class);
				} else{
					Row row1 = this.addRow();
					this.addCell(row1, colunm1++, export.getSuppliers(), 2, String.class);
					this.addCell(row1, colunm1++, rowData.getMonth(), 2, String.class);
					this.addCell(row1, colunm1++, rowData.getReceiving(), 2, Integer.class);
					this.addCell(row1, colunm1++, rowData.getGoodsCount(), 2, Integer.class);
					this.addCell(row1, colunm1++, df.format(rowData.getTotalAmt()), 2, Double.class);
					colunm1 = 0;
				}
				index++;
			}
		}
		Row row = this.addRow();
		Cell cell = row.createCell(0);
		cell.setCellValue("    财务科：                  供应科：                  经办人：");
		cell.getSheet().addMergedRegion(new CellRangeAddress(lastRow+2,lastRow+4,0,4));

		return this;
	}
}
