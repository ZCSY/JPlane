package com.demo.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.demo.util.ImageUtil;
/**
 * С�ɻ�
 * @author dafei
 */
public class SmallPlane extends EnemyPlane{
	//Ӣ�۷ɻ�ͼ��
	private static BufferedImage[] IMAGES;
	
	static {
		IMAGES = new BufferedImage[5];
		for (int i = 0; i < IMAGES.length; i++) {
			IMAGES[i] = ImageUtil.readImage("smallplane"+i+".png");
		}
	}
	public SmallPlane() {
		super(IMAGES[0], new Random().nextInt(ShootGame.WIDTH - IMAGES[0].getWidth()), -IMAGES[0].getHeight());
		super.setScore(100); //����1��С�л�1��
		super.setLife(1); //����1�α�ʾ����
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
		list.add(new EnemyBullet(this.getX()+IMAGES[0].getWidth()/2-4, this.getY()));
		return list;
	}
}
