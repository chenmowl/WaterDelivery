package com.eme.waterdelivery.tools;


import java.security.MessageDigest;
import java.util.List;

public class MD5Util {

	/**
	 * 将字符串转换为16位MD5值
	 * @param sourceStr
	 * @return
	 */
	public static String getMD5Value16(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
	
	/************************ 下面是获取各种文件或者字符串的MD5值 ***********************************/
	/**
	 * 求字节MD5
	 * @param bytes
	 * @return
	 */
	public static String bytes2MD5(byte[] bytes) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		
		byte[] md5Bytes = md5.digest(bytes);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
	
	/************************************************ 下面是用MD5加解密 ***********************************************/
	
	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */
	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}

//	// 测试主函数
//	public static void main(String args[]) throws Exception {
//
//		/*
//		 * String s = new String("tangfuqiang"); System.out.println("原始：" + s);
//		 * System.out.println("MD5后：" + string2MD5(s));
//		 * System.out.println("加密的：" + convertMD5(s)); System.out.println("解密的："
//		 * + convertMD5(convertMD5(s)));
//		 */
//
//		/*File imageFile = new File("H:\\test\\test.jpg");
//		FileInputStream fis = new FileInputStream(imageFile);
//
//		System.out.println("File Size: " + imageFile.length());
//		List<byte[]> listBytes = new ArrayList<byte[]>();
//		byte[] buffer = new byte[1024];
//		int len = 0;
//		while ((len = fis.read(buffer)) != -1) {
//			byte[] mid = Arrays.copyOf(buffer, len);
//			listBytes.add(mid);
//		}
//		ByteBuffer bb = ByteBuffer.allocate(listBytes.size() * 1024);
//		for (byte[] everyBytes : listBytes) {
//			bb.put(everyBytes);
//		}
//		bb.flip();
//
//		byte[] imageFileBytes = bb.array();
//		
//		String result = MD5Util.bytes2MD5(imageFileBytes);
//		System.out.println(result);*/
//		
//		
//		String uuid = UUID.randomUUID().toString();
//		System.out.println(uuid);
//		
//		String result = getMD5Value16(uuid);
//		System.out.println(result);
//		
//		
//	}
		
		
		/**
		 * List 转换成 String 字符串
		 * @param params
		 * @return
		 */
		private static String List2String(List<String> params){
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<params.size()-1;i++){
				if (params.get(i)!=null && !"".equals(params.get(i))) {
					buffer.append(params.get(i)).append("&");
				}
			}
			return buffer.toString();
		}

}
