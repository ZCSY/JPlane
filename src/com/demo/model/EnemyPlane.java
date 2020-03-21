package com.demo.model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * �л�������
 * @author dafei
 */
public abstract class EnemyPlane {
	private BufferedImage image;
	public static final int STATE_ALIVE = 0; //����
	public static final int STATE_DEATH = 1; //����
	public static final int STATE_DELETE = 3; //����
	
	private int state= STATE_ALIVE; //����
	private int score; //����, ʵ����ʱ����
	private int life;	//������ ʵ����������, ����һ��1����
	
	public EnemyPlane(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	private int x;  //x��
	private int y;  //y��
	
	//--------------------------------------------------------
	//�л��ƶ�
	public abstract void move();
	//��ȡͼƬ
	public abstract BufferedImage getImage();
	//����
	public abstract List<EnemyBullet> fire();
	//�Ƿ�Խ��
	public boolean isOutOfBound() {
		return this.y > ShootGame.HEIGHT + image.getHeight();
	}
	//��������
	public void subLife() {
		this.life -= life;
	}
	//�Ƿ�������
	public boolean hasLife() {
		return life > 0;
	}
	
	//�Ƿ����
	public boolean isAlive() {
		return state == STATE_ALIVE;
	}
	//�Ƿ�����
	public boolean isDeath() {
		return state == STATE_DEATH;
	}
	//�Ƿ��ɾ��
	public boolean isDelete() {
		return state == STATE_DELETE;
	}
	
	//----------------------------------------------------------
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
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
