/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel;

import com.thinkgem.jeesite.modules.sco.entity.AdminFinalExport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）,对复杂样式进行处理
 * @author ThinkGem
 * @version 2013-04-21
 */
public class ExportExcelFinal extends ExportExcel {

	private static Logger log = LoggerFactory.getLogger(ExportExcelFinal.class);

	public ExportExcelFinal(String title, Class<?> cls) {
		super(title, cls);
	}

	public ExportExcelFinal(String title, Class<?> cls, int type, int... groups) {
		super(title, cls, type, groups);
	}

	public ExportExcelFinal(String title, String[] headers) {
		super(title, headers);
	}

	public ExportExcelFinal(String title, List<String> headerList) {
		super(title, headerList);
	}

	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * @return list 数据列表
	 */
	public <E> ExportExcel setDataList(List<E> list){

		DecimalFormat df = new DecimalFormat("### ##0.00") ;
		int start = 0;
		int rowSize = 2;
		int lastRow = 2;
		for (E e : list){
			int colunm = 0;
			Row row = this.addRow();

			AdminFinalExport export = (AdminFinalExport)e;
			List<AdminFinalExport.RowData> innerList = export.getRowDatas();
			Cell cell = this.addCell(row, colunm++, export.getSuppliers(), 2, String.class);

			rowSize += innerList.size();
			start = rowSize - innerList.size();
			lastRow = start + innerList.size() -1;
			if(innerList.size() > 1){
				cell.getSheet().addMergedRegion(new CellRangeAddress(start,lastRow,0,0));
			}

			int index = 0; //innerList索引
			int colunm1 = 0;
			for(AdminFinalExport.RowData rowData :innerList){
				if(index == 0){
					this.addCell(row, colunm++, rowData.getMonth(), 2, String.class);
					this.addCell(row, colunm++, rowData.getFinalNumbers(), 2, Integer.class);
					this.addCell(row, colunm++, df.format(rowData.getSparepartAmt()), 2, Double.class);
					this.addCell(row, colunm++, df.format(rowData.getLaborAmt()), 2, Double.class);
					this.addCell(row, colunm++, df.format(rowData.getTyreAmt()), 2, Double.class);
					this.addCell(row, colunm++, df.format(rowData.getOilAmt()), 2, Double.class);
					this.addCell(row, colunm++, df.format(rowData.getTotalAmt()), 2, Double.class);
				} else{
					Row row1 = this.addRow();
					this.addCell(row1, colunm1++, export.getSuppliers(), 2, String.class);
					this.addCell(row1, colunm1++, rowData.getMonth(), 2, String.class);
					this.addCell(row1, colunm1++, rowData.getFinalNumbers(), 2, Integer.class);
					this.addCell(row1, colunm1++, df.format( rowData.getSparepartAmt()), 2, Double.class);
					this.addCell(row1, colunm1++, df.format( rowData.getLaborAmt()), 2, Double.class);
					this.addCell(row1, colunm1++, df.format( rowData.getTyreAmt()), 2, Double.class);
					this.addCell(row1, colunm1++, df.format( rowData.getOilAmt()), 2, Double.class);
					this.addCell(row1, colunm1++, df.format( rowData.getTotalAmt()), 2, Double.class);
					colunm1 = 0;
				}
				index++;
			}
		}
		return this;
	}
}
