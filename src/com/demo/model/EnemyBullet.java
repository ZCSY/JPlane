package com.demo.model;

import java.awt.image.BufferedImage;

import com.demo.util.ImageUtil;

/**
 * �����ӵ�
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
	private int index = 1;  //��������
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
	
	//�ƶ�
	public void move() {
		this.y += 3;
	}
	
	//�Ƿ�Խ��
	public boolean isOutOfBound() {
		return  this.y > ShootGame.HEIGHT + IMAGES[0].getHeight();
	}
}
