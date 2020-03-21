package com.demo.model;

import java.awt.image.BufferedImage;

public abstract class Bullet {
	
	//------------子弹的状态------------------------------------
	public static final int STATE_ALIVE = 0; //活着
	public static final int STATE_DEATH = 1; //死了
	public static final int STATE_DELETE = 3; //死了
	
	//------------子弹的的属性------------------------------------
	protected int state= STATE_ALIVE; //活着
	private BufferedImage image;  //子弹初始图标
	protected int x;  //x轴
	protected int y;  //y轴
	
	
	public Bullet(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}

	//-------------子弹的常规方法----------------------------------
	//子弹的移动
	public abstract void move();
	//判断子弹是否越界
	public abstract boolean isOutOfBound();
	//子弹当前显示图标
	public abstract BufferedImage getCurrentImage();
	//判断自子弹是否活着
	public boolean isAlive() {
		return state == STATE_ALIVE;
	}
	
	//-------------子弹的属性方法----------------------------------
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
	
}
