package com.demo.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.demo.util.ImageUtil;
/**
 * ��ը��
 * @author dafei
 */
public class BossPlane extends EnemyPlane{
	//�ɻ�ͼ��
	private static BufferedImage[] IMAGES;
	
	static {
		IMAGES = new BufferedImage[5];
		for (int i = 0; i < IMAGES.length; i++) {
			IMAGES[i] = ImageUtil.readImage("bossplane"+i+".png");
		}
	}
	public BossPlane() {
		super(IMAGES[0], new Random().nextInt(ShootGame.WIDTH - IMAGES[0].getWidth()), -IMAGES[0].getHeight());
		super.setScore(500); //����1����л�5��
		super.setLife(3); //����3�α�ʾ����
	}
	
	private int index = 1;  //��������
	public  BufferedImage getImage() {
		BufferedImage image = null;
		if(super.getState() ==STATE_ALIVE ) {
			image = IMAGES[0];
		}else if(super.getState() == STATE_DEATH){
			if(index < IMAGES.length) {
				image = IMAGES[index];
			}else {
				super.setState(STATE_DELETE);
			}
			index++;
		}
		return image;
	}
	
	//�ƶ�
	public void move() {
		super.setY(super.getY() + 3);
	}
	//����
	public List<EnemyBullet> fire() {
		List<EnemyBullet> list = new ArrayList<>();
		list.add(new EnemyBullet(this.getX()+IMAGES[0].getWidth()/6, this.getY()+IMAGES[0].getHeight()/2));
		list.add(new EnemyBullet(this.getX()+IMAGES[0].getWidth()*2/6, this.getY()+IMAGES[0].getHeight()/2));
		list.add(new EnemyBullet(this.getX()+IMAGES[0].getWidth()*3/6, this.getY()+IMAGES[0].getHeight()/2));
		list.add(new EnemyBullet(this.getX()+IMAGES[0].getWidth()*4/6, this.getY()+IMAGES[0].getHeight()/2));
		list.add(new EnemyBullet(this.getX()+IMAGES[0].getWidth()*5/6, this.getY()+IMAGES[0].getHeight()/2));
		return list;
	}
}
