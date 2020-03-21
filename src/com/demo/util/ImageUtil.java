package com.demo.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 读取图片的工具类
 * @author dafei
 *
 */
public class ImageUtil {
	
	
	/**
	 * 通过指定图片名字， 读取磁盘的图片文件进入到程序的内存中
	 * @param name
	 * @return
	 */
	public static BufferedImage readImage(String name) {
		
		try {
			return ImageIO.read(new File("images/", name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
