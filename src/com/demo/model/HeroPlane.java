package com.demo.model;

import java.awt.image.BufferedImage;

import com.demo.util.ImageUtil;

/**
 * 英雄飞机
 * @author dafei
 *
 */
public class HeroPlane {
	//英雄飞机图标
	private static BufferedImage[] IMAGES;
	public static final int STATE_ALIVE = 0; //活着
	public static final int STATE_DEATH = 1; //死了
	public static final int STATE_DELETE = 3; //死了
	private int state= STATE_ALIVE; //活着
	
	private int score;		//分数
	private int life=10;		//几条命
	
	static {
		IMAGES = new BufferedImage[6];
		for (int i = 0; i < IMAGES.length; i++) {
			IMAGES[i] = ImageUtil.readImage("heroplane"+i+".png");
		}
	}
	private int x;  //x轴
	private int y;  //y轴

	//飞机移动
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private int index = 1;  //迭代索引
	private boolean flag = true; //活着时飞机图片切换
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
	
	//开火
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
