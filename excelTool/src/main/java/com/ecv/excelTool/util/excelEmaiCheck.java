package com.ecv.excelTool.util ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelEmaiCheck {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO �Զ����ɷ������
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("123");
//		list.add("434");
//		list.add("232");
//		list.add("123");
//		Collections.sort(list);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//			
//		}
	}
	
	/**
	 * ��ȡExcel����
	 * @param file
	 * @throws Exception 
	 */
	public static List readExcel(File file,String emailColInt) throws Exception{
		List<String> errorList = new ArrayList<String>();
		try {
			InputStream inputStream = new FileInputStream(file);
			String fileName = file.getName();
			Workbook wb = null;
			if(fileName.endsWith("xls")){
				wb = new HSSFWorkbook(inputStream);//����xls��ʽ
			}else if(fileName.endsWith("xlsx")){
				wb = new XSSFWorkbook(inputStream);//����xlsx��ʽ
			}
			Sheet sheet = wb.getSheetAt(0);//��һ��������
			// ��һ��
			int firstRowIndex = sheet.getFirstRowNum()+1;
			// ���һ��
			int lastRowIndex = sheet.getLastRowNum();
			int novalidEmailCnt = 0;
			List<String> list = new ArrayList<String>();
			for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){
				Row row = sheet.getRow(rIndex);
				if(row != null){
					// ��һ��
					// ���һ��
					int firstCellIndex = row.getFirstCellNum();
					int lastCellIndex = row.getLastCellNum();
					for(int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex ++){
						if(cIndex!=Integer.parseInt(emailColInt)) continue;
						Cell cell = row.getCell(cIndex);
						String value = "";
						if(cell != null){
							value = cell.toString();
							list.add(value);
							// У�������ʽ����ȷ������ȷ��Ǵ���
							if(! checkEmail(value)){
								novalidEmailCnt ++;
							 	System.out.println( "��" + (rIndex+1) + "��"  + "email ��ַ��" + value+" ���Ϸ���");
							 	errorList.add("��" + (rIndex+1) + "��"  + "email ��ַ��" + value+" ���Ϸ���");
							}
						}
					}
					//System.out.println();
				}
			}
			System.out.println("�ܹ���"+ novalidEmailCnt + "�������ַ���Ϸ���");
			errorList.add("�ܹ���"+ novalidEmailCnt + "�������ַ���Ϸ���");
			// �����ظ�����
			Collections.sort(list);
			if (list.size()>0) {
				String repateValueString = list.get(0);
				boolean repateFlag = false;
				int repateCnt =0;
				for (int i = 1 ; i < list.size(); i++) {
					//System.out.println("*******" + list.get(i) + "*****" );
					if(list.get(i).equals(repateValueString) && !repateFlag ) {
						repateFlag = true;
						repateCnt++;
						System.out.println("�����ַ�ظ���" + repateValueString);
						errorList.add("�����ַ�ظ���" + repateValueString);
					} else{
						repateFlag = false;
						repateValueString = list.get(i);
					}
				}
				System.out.println("�����ظ������ܹ���" + repateCnt + "��");
				errorList.add("�����ظ������ܹ���" + repateCnt + "��");
				
			}
		} catch (FileNotFoundException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		return errorList;
	}
	/**  
     * ��֤�����ַ�Ƿ���ȷ  
     * @param email  
     * @return  
     */  
    public static boolean checkEmail(String email){  
     boolean flag = false;  
     try{  
//      String check = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
//      Pattern regex = Pattern.compile(check);  
     Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
      Matcher matcher = regex.matcher(email);  
      flag = matcher.matches();  
     }catch(Exception e){  
      flag = false;  
     }  
       
     return flag;  
    }  
    
    /*
     * д���ļ� ��¼
     * 
     */ 
    
       public  static void writeFile(String value) throws IOException{
    	   File fos=new File("checkList.txt");  
    	   if(!fos.exists()){
    		   fos.createNewFile();    
    	   }
    		   
    	   BufferedWriter bw=new BufferedWriter(new FileWriter(fos,true)); 
    	   bw.write(value);  
    	   bw.newLine();
    	   bw.close();
       }
       public static boolean isNumber(String str)
       {
           java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]+");
           java.util.regex.Matcher match=pattern.matcher(str);
           if(match.matches()==false)
           {
                return false;
           }
           else
           {
                return true;
           }
       }
}
