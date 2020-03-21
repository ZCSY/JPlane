package com.demo.model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 敌机共用类
 * @author dafei
 */
public abstract class EnemyPlane {
	private BufferedImage image;
	public static final int STATE_ALIVE = 0; //活着
	public static final int STATE_DEATH = 1; //死了
	public static final int STATE_DELETE = 3; //死了
	
	private int state= STATE_ALIVE; //活着
	private int score; //分数, 实例化时设置
	private int life;	//生命， 实例化是设置, 打中一次1条命
	
	public EnemyPlane(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	private int x;  //x轴
	private int y;  //y轴
	
	//--------------------------------------------------------
	//敌机移动
	public abstract void move();
	//获取图片
	public abstract BufferedImage getImage();
	//开火
	public abstract List<EnemyBullet> fire();
	//是否越界
	public boolean isOutOfBound() {
		return this.y > ShootGame.HEIGHT + image.getHeight();
	}
	//减少生命
	public void subLife() {
		this.life -= life;
	}
	//是否有生命
	public boolean hasLife() {
		return life > 0;
	}
	
	//是否活着
	public boolean isAlive() {
		return state == STATE_ALIVE;
	}
	//是否死了
	public boolean isDeath() {
		return state == STATE_DEATH;
	}
	//是否可删除
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
