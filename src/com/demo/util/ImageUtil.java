package com.demo.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ��ȡͼƬ�Ĺ�����
 * @author dafei
 *
 */
public class ImageUtil {
	
	
	/**
	 * ͨ��ָ��ͼƬ���֣� ��ȡ���̵�ͼƬ�ļ����뵽������ڴ���
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
