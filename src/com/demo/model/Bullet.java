package com.demo.model;

import java.awt.image.BufferedImage;

public abstract class Bullet {
	
	//------------�ӵ���״̬------------------------------------
	public static final int STATE_ALIVE = 0; //����
	public static final int STATE_DEATH = 1; //����
	public static final int STATE_DELETE = 3; //����
	
	//------------�ӵ��ĵ�����------------------------------------
	protected int state= STATE_ALIVE; //����
	private BufferedImage image;  //�ӵ���ʼͼ��
	protected int x;  //x��
	protected int y;  //y��
	
	
	public Bullet(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}

	//-------------�ӵ��ĳ��淽��----------------------------------
	//�ӵ����ƶ�
	public abstract void move();
	//�ж��ӵ��Ƿ�Խ��
	public abstract boolean isOutOfBound();
	//�ӵ���ǰ��ʾͼ��
	public abstract BufferedImage getCurrentImage();
	//�ж����ӵ��Ƿ����
	public boolean isAlive() {
		return state == STATE_ALIVE;
	}
	
	//-------------�ӵ������Է���----------------------------------
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
