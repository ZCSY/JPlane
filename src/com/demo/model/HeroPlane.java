package com.demo.model;

import java.awt.image.BufferedImage;

import com.demo.util.ImageUtil;

/**
 * Ӣ�۷ɻ�
 * @author dafei
 *
 */
public class HeroPlane {
	//Ӣ�۷ɻ�ͼ��
	private static BufferedImage[] IMAGES;
	public static final int STATE_ALIVE = 0; //����
	public static final int STATE_DEATH = 1; //����
	public static final int STATE_DELETE = 3; //����
	private int state= STATE_ALIVE; //����
	
	private int score;		//����
	private int life=10;		//������
	
	static {
		IMAGES = new BufferedImage[6];
		for (int i = 0; i < IMAGES.length; i++) {
			IMAGES[i] = ImageUtil.readImage("heroplane"+i+".png");
		}
	}
	private int x;  //x��
	private int y;  //y��

	//�ɻ��ƶ�
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private int index = 1;  //��������
	private boolean flag = true; //����ʱ�ɻ�ͼƬ�л�
	public  BufferedImage getImage() {
		BufferedImage image = null;
		if(state ==STATE_ALIVE ) {
			image = IMAGES[flag?0:1];
			flag = !flag;
		}else if(state == STATE_DEATH){
			
			if(index < IMAGES.length) {
				image = IMAGES[index];
			}else {
				if(life < 0) {
					state = STATE_DELETE;
				}else {
					state =STATE_ALIVE;
				}
			}
			index++;
		}
		return image;
	}
	
	//����
	public HeroBullet fire() {
		return new HeroBullet(this.getX(), this.getY());
	}
	public void addScore(int sc) {
		this.score += sc;
	}
	
	public boolean hasLife() {
		return life > 1;
	}
	public void subLife(int i) {
		this.life -= i;
	}
	
	//----------------------------------------------------------
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	

	
}
