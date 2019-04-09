package com.lemon.video.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHelper {

//	public final static String TEBAO = "tebao";
	public final static String TEBAO = "video_shows";
	public final static String FILECACHE = "FileCache";
	public final static String CONFIG_PATH = "config";
	public final static String IMAGECACHE = "ImageCache";
	public final static String DEFAULT_PATH = "/object/object/com.lemon.video/FileCache";
	//app存放静态资源的地方
	public final static String APP_DATA_DEFAULT_PATH = "/object/object/com.lemon.video/app_root_data";
//	public final static String DEFAULT_PATH = "/object/object/com.chinadailyhk.vdoenglish/FileCache";
	
	public static String getFileCachePath(){
		String path = APP_DATA_DEFAULT_PATH ;
		String sdDir = FileHelper.getSDCardDirectory();
		
		if(sdDir != null && !sdDir.equals("")){
			path = sdDir+ File.separator +FILECACHE;
			File dir = new File(path);
			
			if(!dir.exists()) dir.mkdirs();
		}

		return path;
	}

	//获取配置文件路径
	public static String getConfigFilePath(){
		String path = APP_DATA_DEFAULT_PATH ;
		String sdDir = FileHelper.getSDCardDirectory();

		if(sdDir != null && !sdDir.equals("")){
			path = sdDir+ File.separator +CONFIG_PATH;
			File dir = new File(path);

			if(!dir.exists()) dir.mkdirs();
		}

		return path;
	}
	
	public static File getFile(String path, String fileName) throws Exception {
		File dir = new File(path);
		
		if(dir == null || !dir.exists()){
			dir.mkdirs();
		}
		
		File file = new File(path + File.separator + fileName);
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new Exception(e);
			}
		}
		
		return file;
	}
	
	public static File getFile(String fileName) throws Exception {
		File file = new File(fileName);
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new Exception(e);
			}
		}
		
		return file;
	}
	
	public static  boolean writeFile(File file, String content) throws Exception {
		FileOutputStream outStream = null;
		
		try {
			outStream = new FileOutputStream(file);
			
			if(content != null){
				byte[] stream = content.getBytes("UTF-8");
				outStream.write(stream);
				outStream.flush();
			}
			
		}catch (IOException e) {
			throw new Exception(e);
		}finally{
			
			try {
				if(outStream != null) outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
//				ExceptionManager.sendException((new DataException(e))
//						.getShowMessage(),false);
			}
		}
		
		return true;
	}
	
	public static String readFile(File file) throws Exception {
		String content = null;
		FileInputStream inputStream = null;
		StringBuffer buffer = new StringBuffer();
		
		if(file.exists()){
			try {
				inputStream = new FileInputStream(file);
				byte[] unitData = new byte[3072];
				int readLength = 0;
				
				while(readLength > -1){
					readLength = inputStream.read(unitData);
					
					if(readLength > 0){
						buffer.append(new String(unitData,0,readLength,"UTF-8"));
					}
				}
			} catch (FileNotFoundException e) {
				throw new Exception(e);
			} catch (IOException e) {
				throw new Exception(e);
			}finally{
				
				try {
					if(inputStream != null) inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
//					ExceptionManager.sendException((new VqIOException(e)).getShowMessage(),false);
				}
			}
			
			content = buffer.toString();
		}
		
		return content;
	}
	
	public static boolean saveStringToFile(String path , String fileName, String content)
		throws Exception {
		File file = getFile(path,fileName);
		
		return writeFile(file, content);
	}
	
	public static String readStringFromFile(String fileName)
			throws Exception {
        File file = getFile(getFileCachePath(), fileName);
        
		return readFile(file);
	}
	/**
	 *  Function: 保存Object到本地文件,默认是本应用的缓存路径
	 *  <p>/object/object/com.VDOENGLISH.frame/mesofine/FileCache
	 *  @param fileName
	 *  @param object
	 *  @return true:写成功，false写失败
	 */
	public static boolean writeObjectToFile(String fileName, Object object) throws Exception {
		return writeObjectToFile(getFileCachePath(), fileName, object);
	}
	/**
	 *  Function: 保存Object到本地文件
	 *  @param path
	 *  @param fileName
	 *  @param object
	 *  @return true:写成功，false写失败
	 */
	public static boolean writeObjectToFile(String path, String fileName,
                                            Object object) throws Exception {
		
		if (object == null)  return false;

		File file = getFile(path,fileName);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(object);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
//			ExceptionManager.sendException((new VqIOException(e)).getShowMessage(),false);
		}
		
		return true;
	}
	/**
	 *  Function: 读取Object从本地文件
	 *  @param fileName
	 *  @return Object
	 */
	public static Object readObjectFromFile(String fileName) throws Exception {
	    Object object = null;

		File file = getFile(getFileCachePath(), fileName);
		
		readObjectFromFile(file);

		return object;
	}
	public static Object readObjectFromFile(File file) throws Exception {
		Object object = null;
		try {
			if (file != null && file.exists()) {
				FileInputStream fis = null;
				fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				object = ois.readObject();
				ois.close();
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
//			ExceptionManager.sendException((new VqIOException(e)).getShowMessage(),false);
		}
		
		return object;
	}

	
	
	public static String getSDCardDirectory(){
		String sdDir = null;
        String SDCardState = android.os.Environment.getExternalStorageState();
        
        if (SDCardState.equals(android.os.Environment.MEDIA_MOUNTED)){ // 判断sdcard是否存在(可读可写权限)
            // ②获取扩展存储设备的文件目录
            File SDFile = android.os.Environment.getExternalStorageDirectory();
            sdDir = SDFile.getAbsolutePath();
        }
		
		return sdDir;
	}
	
	
	/* 文件写入（文件保存到SDCard中） */
    public static void writeToSDCard(String filename, String dataparem)
    {
    	writeToSDCard(TEBAO + File.separator + FILECACHE,filename,dataparem);
    }


	/**
	 *
	 * @param urlEnd   		url的后半部分地址
	 * @param dataString	数据字符串
     */
	public static void writeConfigToSDCard(String urlEnd, String dataString){
		int index = urlEnd.lastIndexOf("/");
		writeConfigToSDCard(urlEnd.substring(0,index),urlEnd.substring(index),dataString);
	}


	//将全局配置文件写入sd卡  已经处理了删除旧文件
	/**
	 *
	 * @param dirName  目录名（前面不带分隔符）
	 * @param fileName  文件名（ 比如 index.json 特报要求带有文件后缀 或者index ）
	 * @param dataString  数据字符串
	 */
	public static void writeConfigToSDCard(String dirName, String fileName, String dataString)
	{
		//文件更新了进行写入，如果该文件存在，那就是旧文件了，进行删除
		String sdDir = getSDCardDirectory();
		String path = sdDir + File.separator + dirName ;
		File file = new File(path + File.separator + fileName);
		if (file.exists()){
			file.delete();
		}
		writeToSDCard(TEBAO + File.separator + dirName,fileName,dataString);
	}

    /** 
     * 文件写入（文件保存到SDCard中）
     * dirName:目录名而不是路径
     */
    public static void writeToSDCard(String dirName, String filename, String dataparem)
    {
    	try {
	         String sdDir = getSDCardDirectory();
	         
	         if(sdDir != null && !sdDir.equals("")){
	             // 创建一个文件
	             String path = sdDir + File.separator + dirName ;
	             
	             File file = getFile(path,filename);
    			// 写数据
    			FileOutputStream fos = new FileOutputStream(file, false); // 追加获取输出流
    			fos.write(dataparem.trim().getBytes("utf-8"));
    			fos.close();
    			
    		} else {
//    			ExceptionManager.sendException(InitParam.getString(R.string.sddirfail));
    		}
    		
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (Exception e){
        	e.printStackTrace();
        }
    }

	public static String readStringFromSDCardByUrlEnd(String urlEnd){
		int index = urlEnd.lastIndexOf("/");
		return readStringFromSDCard(urlEnd.substring(0,index),urlEnd.substring(index));
	}

    //文件读取
	/* 文件读取（文件加载） */
	public static String readStringFromSDCard(String dirname, String filename)
	{
		try
		{
			String sdDir = getSDCardDirectory();

			if(sdDir != null && !sdDir.equals("")){
				String path = sdDir+ File.separator + TEBAO + File.separator + dirname;
				File file = getFile(path, filename);
				// 判断文件是否存在
				if (file.exists()){
					// 读数据
					StringBuffer sb = new StringBuffer();
					FileInputStream fis = new FileInputStream(file);
					byte[] buffer = new byte[fis.available()];
					int fstatus = fis.read(buffer);

					while(fstatus > 0  ){
						sb.append(new String(buffer,"utf-8"));
						fstatus = fis.read(buffer);
					}// 读到缓存区

					fis.close();

					return sb.toString();

				}else{
					return null;
				}
			}else{
//    			ExceptionManager.sendException(InitParam.getString(R.string.sddirfail));
				return null;
			}

		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}

    /* 文件读取（文件加载） */
    public static String readStringFromSDCard(String filename)
    {
    	try        
    	{
    		String sdDir = getSDCardDirectory();
	         
	         if(sdDir != null && !sdDir.equals("")){
                String path = sdDir+ File.separator + TEBAO + File.separator + FILECACHE;
                File file = getFile(path, filename);
    			// 判断文件是否存在
    			if (file.exists()){
    				// 读数据
     				StringBuffer sb = new StringBuffer();
    				FileInputStream fis = new FileInputStream(file);
    				byte[] buffer = new byte[fis.available()];
    				int fstatus = fis.read(buffer);
    				
    				while(fstatus > 0  ){
    					sb.append(new String(buffer,"utf-8"));
    					fstatus = fis.read(buffer);
    				}// 读到缓存区
    				
    				fis.close();
    				
    				return sb.toString();

    			}else{
    				return null;
    			}
    		}else{
//    			ExceptionManager.sendException(InitParam.getString(R.string.sddirfail));
    			return null;
    		}
	         
    	} catch (FileNotFoundException e){
    		e.printStackTrace();
    	} catch (IOException e){
    		e.printStackTrace();
    	} catch (Exception e){
        	e.printStackTrace();
        }
    	
    	return null;
    }
    
    public static void saveBitmapToSDCard(Bitmap bm, String fileName) throws IOException {
    	String sdDir = getSDCardDirectory();
    	
        try{
        	
	        if(sdDir != null && !sdDir.equals("")){
	            // 创建一个文件
	            String path = sdDir + File.separator + TEBAO + File.separator + IMAGECACHE;
	            File file = getFile(path, fileName);
		        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
		        bos.flush();  
		        bos.close();  
	        }
	        
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
    public static Bitmap readBitmapFromSDCard(String fileName){
    	String sdDir = getSDCardDirectory();
        	
        if(sdDir != null && !sdDir.equals("")){
			String path = sdDir + File.separator + TEBAO + File.separator + IMAGECACHE;
			Bitmap image = BitmapFactory.decodeFile(path + File.separator+ fileName);
			
			return image;
        }
        
        return null;  
    } 

    /* 文件删除 */
    public static void deleteSDFile(File file){
        if(file.isDirectory()){
        	File[] files = file.listFiles();
        	
        	for(int i=0;i<files.length;i++){
        		
        		if(files[i].isDirectory()){
        			deleteSDFile(files[i]);
        		}else{
        			files[i].delete();
        		}
        	}
        }
        
        file.delete();

    }
    
    public static void clearSDCache(){
    	String sdDir = getSDCardDirectory();
    	
        if(sdDir != null && !sdDir.equals("")){
            String path = sdDir + File.separator + TEBAO;
            File disFile = new File(path);
            
	        if(disFile.exists()){  
	        	deleteSDFile(disFile);
	        }
        }
    }
    
    /**
	 * 程序退出时，需要清除的本地文件缓存
	 */
	public static void clearCache() {
		File file = new File(FileHelper.getFileCachePath());
		deleteFile(file);
	}
	/**
	 * 删除文件或目录
	 */
	public static void deleteFile(File file) {
		if (file != null && file.exists()) {
			
			if (file.isFile()){
				file.delete();				
			}
			
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);//递归
				}
				
				file.delete();
			}
			
		}
	}


}
