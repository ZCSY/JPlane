package com.demo.model;

import java.awt.image.BufferedImage;

import com.demo.util.ImageUtil;

/**
 * 敌人子弹
 * @author dafei
 *
 */
public class EnemyBullet extends Bullet{
	
	private static BufferedImage[] IMAGES;
	static {
		IMAGES = new BufferedImage[5];
		for(int i = 0; i < IMAGES.length; i++) {
			IMAGES[i] = ImageUtil.readImage("enemybullet"+i+".png");
		}
	}
	
	public EnemyBullet(int x, int y) {
		super(IMAGES[0], x, y);
	}
	private int index = 1;  //迭代索引
	public  BufferedImage getCurrentImage() {
		BufferedImage image = null;
		if(state ==STATE_ALIVE ) {
			image = IMAGES[0];
		}else if(state == STATE_DEATH){
			if(index < IMAGES.length) {
				image = IMAGES[index];
			}else {
				state = STATE_DELETE;
			}
			index++;
		}
		return image;
	}
	
	//移动
	public void move() {
		this.y += 3;
	}
	
	//是否越界
	public boolean isOutOfBound() {
		return  this.y > ShootGame.HEIGHT + IMAGES[0].getHeight();
	}
}
