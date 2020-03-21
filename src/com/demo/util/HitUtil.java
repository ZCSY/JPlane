package com.demo.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.demo.model.EnemyBullet;
import com.demo.model.EnemyPlane;
import com.demo.model.HeroBullet;
import com.demo.model.HeroPlane;

/**
 * 碰撞工具类
 * @author dafei
 */
public class HitUtil {
		
	/**
	 * 
	 *  矩形1的参数是：左上角的坐标是(x1,y1)，宽度是w1，高度是h1;
	 *  矩形2的参数是：左上角的坐标是(x2,y2)，宽度是w2，高度是h2。
	 *  x方向：| (x1 + w1 / 2) – (x2 +w2/2) | < |(w1 + w2) / 2|
	 *  y方向：| (y1 + h1 / 2 ) – (y2 + h2/2) | < |(h1 + h2) / 2 |
	 * 
	 * 碰撞逻辑
	 */
	private static boolean isHit(int x1, int y1, int w1,int h1, int x2,int y2,int w2, int h2) {
		//两者的矩形范围	
		Rectangle tank1 = new Rectangle(x1,y1,w1,h1);
		Rectangle tank2 = new Rectangle(x2,y2,w2,h2);
		//判断两个矩形是否有交集，crash 为 true 说明碰撞了
		return tank1.intersects(tank2);
	}
	
	//子弹与子弹
	public static boolean heroBulletHitBullet(HeroBullet hb, EnemyBullet eb) {
		BufferedImage x = hb.getCurrentImage();
		BufferedImage y = eb.getCurrentImage();
		return isHit(hb.getX(), hb.getY(), x.getWidth(), x.getHeight(), eb.getX(), eb.getY(), y.getWidth(), y.getHeight());
	}
	
	public static boolean heroBulletHitPlane(HeroBullet hb, EnemyPlane eb) {
		BufferedImage x = hb.getCurrentImage();
		BufferedImage y = eb.getImage();
		return isHit(hb.getX(), hb.getY(), x.getWidth(), x.getHeight(), eb.getX(), eb.getY(), y.getWidth(), y.getHeight());
	}
	
	
	public static boolean heroPlaneHitBullet(HeroPlane hb, EnemyBullet eb) {
		BufferedImage x = hb.getImage();
		BufferedImage y = eb.getCurrentImage();
		return isHit(hb.getX()-x.getWidth()/6, hb.getY()-x.getHeight()/6, x.getWidth()/3, x.getHeight()/3, eb.getX(), eb.getY(), y.getWidth(), y.getHeight());
	}
	
	public static boolean heroPlaneHitEnamyPlane(HeroPlane hb, EnemyPlane eb) {
		BufferedImage x = hb.getImage();
		BufferedImage y = eb.getImage();
		
		int centerX1 =hb.getX();
		int centerY1 =hb.getY();
		int centerX2 = eb.getX() + y.getWidth()/2, centerY2 = eb.getY() + y.getHeight()/2;
		// 求两圆的圆心距
		double length = Math.sqrt(Math.pow(centerX1 - centerX2, 2)+ Math.pow(centerY1 - centerY2, 2));
		
		int r1 = Math.min(x.getWidth(), x.getHeight())/2;
		int r2 = Math.min(y.getWidth(), y.getHeight())/2;
		
		return length < (r1 + r2);
		
		
	}
	
}
