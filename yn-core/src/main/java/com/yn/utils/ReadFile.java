package com.yn.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ReadFile {
	
	/**
	 * 主方法测试
	 */
	public static void main(String[] args) {
		String filePath = "F:/workspase/java/workspaceSpring/guji/guji-admin/src/main/java/com/hy/guji/admin/vo"; // 要读取的文件夹路径
		File outPath = new File("F:/workspase/java/workspaceSpring/guji/guji-admin/src/main/java/com/hy/guji/admin/vo"); // 文件输出路径(不存在也可以)
		
//		@ApiModelProperty("[创建时间]")
//	    private Date createDtm;
//		@ApiModelProperty("[逻辑删除]{0:未删;1:已删}")
//	    private Integer del;
//		@ApiModelProperty("[删除时间]")
//	    private Date delDtm;
//		@ApiModelProperty("[更新时间]")
//	    private Date updateDtm;
		String source = "com.hy.commons"; // 要替换的字符串
		String target = "org.hy.commons"; // 替换成什么
		retext(filePath, outPath,"@ApiModelProperty(\"[创建时间]\")","");
		retext(filePath, outPath,"private Date createDtm;","");
		retext(filePath, outPath,"@ApiModelProperty(\"[逻辑删除]{0:未删;1:已删}\")","");
		retext(filePath, outPath,"private Integer del;","");
		retext(filePath, outPath,"@ApiModelProperty(\"[删除时间]\")","");
		retext(filePath, outPath,"private Date delDtm;","");
		retext(filePath, outPath,"@ApiModelProperty(\"[更新时间]\")","");
		retext(filePath, outPath,"private Date updateDtm;","");
	}

	private static void retext(String filePath, File outPath,String source,String target) {
		
		readFolder(filePath,outPath, source, target);
	}
	
	/**
	 * 读取文件夹
	 * @return 
	 */
	public static void readFolder(String filePath, File outPath, String source, String target){
		try {
			//读取指定文件夹下的所有文件
			File file = new File(filePath);
			if (!file.isDirectory()) {
				System.out.println("---------- 该文件不是一个目录文件 ----------");
			} else if (file.isDirectory()) {
				System.out.println("---------- 很好，这是一个目录文件夹 ----------");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filePath + "\\" + filelist[i]);
					//String path = readfile.getPath();//文件路径
					String absolutepath = readfile.getAbsolutePath();//文件的绝对路径
					String filename = readfile.getName();//读到的文件名
					readFile(absolutepath, filename, i, outPath, source, target);//调用 readFile 方法读取文件夹下所有文件
				}
				System.out.println("---------- 所有文件操作完毕 ----------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取文件夹下的文件
	 * @return 
	 */
	public static void readFile(String absolutepath, String filename, int index, File outPath, String source, String target){
		try{
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutepath)));//数据流读取文件
			StringBuffer strBuffer = new StringBuffer();
			for (String temp = null; (temp = bufReader.readLine()) != null; temp = null) {
				if(temp.indexOf(source) != -1) { // 判断当前行是否存在想要替换掉的字符
//					tihuan = temp.substring(0, 9); //这里截取多长自己改
					temp = temp.replace(source, target); //替换为你想要的东东
				}
				strBuffer.append(temp);
				strBuffer.append(System.getProperty("line.separator"));//行与行之间的分割
			}
			bufReader.close();
			if(outPath.exists() == false){ //检查输出文件夹是否存在，若不存在先创建
				outPath.mkdirs();
				System.out.println("已成功创建输出文件夹：" + outPath);
			}
			PrintWriter printWriter = new PrintWriter(outPath+"\\"+filename);//替换后输出的文件位置（切记这里的E:/ttt 在你的本地必须有这个文件夹）
			printWriter.write(strBuffer.toString().toCharArray());
			printWriter.flush();
			printWriter.close();
			System.out.println("第 " + (index+1) +" 个文件   "+ absolutepath +"  已成功输出到    " +outPath+"\\"+filename);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
