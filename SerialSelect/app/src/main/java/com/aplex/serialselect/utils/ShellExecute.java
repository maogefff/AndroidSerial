package com.aplex.serialselect.utils;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ������Ҫ������Java��ִ��Linux shell�����ȡһЩϵͳ�µ���Ϣ
 * �����е�dmesg��ҪһЩ�����Ȩ�޲���ʹ��
 * @author aplex
 */
public class ShellExecute {
	/**
	 * ����������ִ��Linux shell���� 
	 * 
	 * @param command		shell���֧�ֹܵ����ض���
	 * @param directory 	��ָ��Ŀ¼��ִ������
	 * @return				����shell����ִ�н��
	 * @throws IOException	�׳�IOException
	 */
    public static String execute ( String command, String directory )  
    		throws IOException {  
    	
    	// check the arguments
    	if (null == command || command.trim().equals("")) 
    		return "";

    	if (null == directory || directory.trim().equals("")) 
			directory = "/";

    	String result = "" ;  

    	List<String> cmds = new ArrayList<String>(); 
    	cmds.add("sh"); 
    	cmds.add("-c"); 
    	cmds.add(command); 

    	try {  
    		ProcessBuilder builder = new ProcessBuilder(cmds);  
      
    		if ( directory != null && directory.length() != 0)  
    			builder.directory ( new File ( directory ) ) ;  

    		builder.redirectErrorStream (true) ;  
    		Process process = builder.start ( ) ;  
      
    		//�õ�����ִ�к�Ľ��   
    		InputStream is = process.getInputStream ( ) ;  
    		byte[] buffer = new byte[1024] ;  
    		while ( is.read(buffer) != -1 )
    			result = result + new String (buffer) ;  

    		is.close ( ) ;  
    	} catch ( Exception e ) {  
    		e.printStackTrace ( ) ;  
    	}  
    	return result.trim() ;  
    }  

    /**
     * ����������ִ��Linux shell���ִ��Ŀ¼��ָ��Ϊ:"/"
     * 
	 * @param command		shell���֧�ֹܵ����ض���
	 * @return				����shell����ִ�н��
	 * @throws IOException	�׳�IOException
     */
    public static String execute (String command) throws IOException {  

    	// check the arguments
    	if (null == command || command.trim().equals("")) 
			return "";

    	return execute(command, "/");
    }  
    
    /**
     * �����������ж�dmesg���Ƿ����pattern�ַ�����ִ��Ŀ¼��ָ��Ϊ:"/"
     * 
	 * @param pattern		��grepƥ����ַ���	
	 * @return				true:  dmesg�д���pattern�е��ַ���<br>
	 * 						false��  dmesg�в�����pattern�е��ַ���
	 * @throws IOException	�׳�IOException
     */
    public static boolean devExist(String pattern) throws IOException{

    	// check the arguments
    	if (null == pattern) 
			return false;

    	if (pattern == null || pattern.trim().equals("")) 
			return false;

    	return execute("dmesg | grep " + pattern).length() > 0;
    }
}
