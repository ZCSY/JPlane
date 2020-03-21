package com.demo.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.demo.util.HitUtil;
import com.demo.util.ImageUtil;

/**
 * 游戏的主界面 -- 画板
 * 1:英雄机
 * 2:敌机
 * 3:各类子弹
 * 4:背景地图
 * @author dafei
 */
public class ShootGame extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 400;  //地图界面宽度
	public static final int HEIGHT = 654;  //地图界面长度
	
	//背景图
	private static BufferedImage background1 = ImageUtil.readImage("background1.jpg");
	private static BufferedImage background2 = ImageUtil.readImage("background2.png");
	private static BufferedImage start = ImageUtil.readImage("start.png");
	private static BufferedImage pause = ImageUtil.readImage("pause.png");
	private static BufferedImage gameover = ImageUtil.readImage("gameover.png");
	
	
	public static final int START = 0;	//启动状态
	public static final int RUNNING = 1;	//运行状态
	public static final int PAUSE = 2;	//暂停状态
	public static final int GAME_OVER = 3;	//结束状态
	private int state = START;	//游戏的状态(默认启动状态)
	
	
	private List<HeroBullet> heroBullets = new ArrayList<>();  //英雄子弹
	private List<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>(); //敌人子弹
	
	private int y = 0;
	private int y2 = -background1.getHeight();
	
	
	private boolean fireG1 = true;
	private boolean fireG2 = false;
	private boolean fireG3 = false;
	private boolean fireMax = false;
	
	//英雄飞机
	private HeroPlane heroPlane = new HeroPlane();
	
	//英雄机
	private void paintHeroPhone(Graphics g) {
		BufferedImage img = heroPlane.getImage();
		
		if(img == null) {
			return;
		}
		//画上英雄飞机
		if(heroPlane.getX() == 0) {
			g.drawImage(img, WIDTH/2-img.getWidth()/2, HEIGHT/2+img.getHeight(), null);
		}else {
			g.drawImage(img, heroPlane.getX()-img.getWidth()/2, heroPlane.getY()-img.getHeight()/2, null);
		}
	}
	
	//画背景
	private void paintBackground(Graphics g) {
		//参数1：图片， 参数2：x轴 参数3：y轴
		g.drawImage(background1, 0, 0, null);
		g.drawImage(background2, 0, y, null);
		g.drawImage(background2, 0, y2, null);
	}
	
	//背景图移动
	public void backgroundMove() {
		y++;
		y2++;
		
		if(y2 >= 0) {
			y2 =  -background1.getHeight();;
		}
		
		if(y >= background1.getHeight()) {
			y = 0;
		}
	}
	
	//画分数和生命数
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(255, 0, 0));	//设置画笔颜色	红0	绿0	蓝0	(0~255)
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));	//设置字体		字体、样式、字号
		g.drawString("score: "+heroPlane.getScore(), 10, 25);
		g.drawString("life: "+heroPlane.getLife(), 10, 45);
	}
	//画最终的得分
	public void paintResultScore(Graphics g) {
		g.setColor(new Color(255, 0, 0));	//设置画笔颜色	红255	绿0	蓝0	(0~255)
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));	//设置字体		字体、样式、字号
		g.drawString("score: "+heroPlane.getScore(), 100, 220);
	}
		
	//画状态
	public void paintState(Graphics g) {
		switch(state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case RUNNING:
			paintScoreAndLife(g);	//画分数和生命数(调用)
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			paintResultScore(g);		//画玩家最终的得分(调用)
		}
	}
	
	//画英雄机子弹
	public void paintHeroBullet(Graphics g) {
		
		for (int i = 0; i < heroBullets.size(); i++) {
			HeroBullet heroBullet = heroBullets.get(i);
			BufferedImage img = heroBullet.getCurrentImage();
			BufferedImage image = heroPlane.getImage();
			if(img != null) {
				if(fireG1) {
					g.drawImage(img, heroBullet.getX(), heroBullet.getY(), null);
				}
				if(fireG2) {
					g.drawImage(img, heroBullet.getX()-image.getWidth()/2, heroBullet.getY(), null);
					g.drawImage(img, heroBullet.getX()+image.getWidth()/2, heroBullet.getY(), null);
				}
				if(fireG3) {
					g.drawImage(img, heroBullet.getX(), heroBullet.getY(), null);
					g.drawImage(img, heroBullet.getX()-image.getWidth()/2, heroBullet.getY(), null);
					g.drawImage(img, heroBullet.getX()+image.getWidth()/2, heroBullet.getY(), null);
				}
				
			}
		}
	}
	
	//小敌机
	private List<EnemyPlane> enemyPlanes = new ArrayList<>();
	
	//画敌人
	public  void paintEnemy(Graphics g) {
		
		for (int i = 0; i < enemyPlanes.size(); i++) {
			EnemyPlane sp = enemyPlanes.get(i);
			BufferedImage image = sp.getImage();
			if(image != null) {
				g.drawImage(image,sp.getX(),sp.getY(), null);
			}
		}
	}
	
	//画敌机子弹
	public  void enemyBullet(Graphics g) {
		
		for (int i = 0; i < enemyBullets.size(); i++) {
			EnemyBullet enemyBullet = enemyBullets.get(i);
			BufferedImage img = enemyBullet.getCurrentImage();
			if(img != null) {
				g.drawImage(img, enemyBullet.getX()+img.getWidth()/2, enemyBullet.getY(), null);
			}
		}
	}
	
	//重写父类的方法，自定义画布
	public void paint(Graphics g) {
		if(state == RUNNING) {
			this.paintBackground(g);
			this.paintHeroPhone( g);
			this.paintHeroBullet(g);
			this.paintEnemy(g);
			this.enemyBullet(g);
		}
		this.paintState(g);
	}
	
	public void init() {
		JFrame jFrame = new JFrame("飞机大战");  //标题头
		jFrame.add(this);
		
		//设置窗口的长宽
		jFrame.setSize(WIDTH, HEIGHT);
		//设置窗口总是在顶端
		jFrame.setAlwaysOnTop(true);		
		//设置窗口居中
		jFrame.setLocationRelativeTo(null);	
		//关闭窗口时退出程序
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		jFrame.setResizable(false);
		//设置窗口为可见	
		jFrame.setVisible(true);	
		
		//初始化监听器
		this.initListener();
		//初始化定时器
		this.initTimer();
	}
	
	private long count =0l;
	private void initTimer() {
		Timer timer = new Timer();
		int delay = 10;	//延迟时间(单位为毫秒)
		int period = 10;	//周期(单位为毫秒)
		timer.schedule(new TimerTask() {
			//定时干的事
			public void run() {
				if(state == RUNNING) {
					count++;
					if(count % 20 == 0 || fireMax) {
						//英雄飞机开火
						heroPlaneFire();
					}
					//英雄子弹移动
					heroBulletMove();
					
					if(count % 100 == 0) {
						//小敌机出现
						enemyPlaneEnter();
					}
					
					
					//小敌机移动
					if(count % 2 == 0) {
						enemyPlaneMove();
					}
					
					if(count%200 == 0) {	
						enemyPlaneFire();	//敌机发送子弹
					}
					
					//敌机子弹移动
					enemyBulletMove(); 
					
					
					//碰撞
					objectHit();
					
					//清除
					if(count % 100 == 0) {
						objectClear();
					}
					
					//背景移动
					backgroundMove();
					if(count == Long.MAX_VALUE - 10) {
						count = 0L;
					}
				}
				//重新画画布
				repaint();	
			}

			
		}, delay, period);
	}
	//初始化监听器
	private void initListener() {
		//设置鼠标监听器
		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				heroPlane.move(e.getX(), e.getY());
				//重新画画布
				repaint();
			}
			//重写鼠标点击事件
			public void mouseClicked(MouseEvent e) {
				System.out.println(state);
				switch(state) {
					case GAME_OVER:
						state = START;
						//清空
						enemyPlanes = new ArrayList<>();
						enemyBullets = new  ArrayList<>();
						heroBullets = new ArrayList<>();
						heroPlane = new HeroPlane();
						
						break;
					case START:
						state = RUNNING;
				}
			}
			//重写鼠标进入事件
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE) {
					state = RUNNING;
				}
			}
			//重写鼠标移出事件
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING) {
					state = PAUSE;
				}
			}
		};
		
		this.addMouseListener(adapter);
		this.addMouseMotionListener(adapter);
		
		//秘籍
		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				String key = KeyEvent.getKeyText(keyCode);
				if(e.isShiftDown() && "M".equals(key)) {
					fireMax = true;
				}
				if(e.isShiftDown() && "2".equals(key)) {
					fireG1 = false;
					fireG2 = true;
					fireG3 = false;
				}
				if(e.isShiftDown() && "3".equals(key)) {
					fireG1 = false;
					fireG2 = false;
					fireG3 = true;
				}
				if(e.isShiftDown() && "X".equals(key)) {
					fireMax = false;
					fireG1 = true;
					fireG2 = false;
					fireG3 = false;
				}
			}
		});
	}
	//英雄机发射子弹
	public void heroPlaneFire() {
		if(heroPlane.getState() == HeroPlane.STATE_ALIVE) {
			HeroBullet hb = heroPlane.fire();
				heroBullets.add(hb);
		}
	}
	//英雄子弹移动
	public void heroBulletMove() {
			for (HeroBullet heroBullet : heroBullets) {
				if(heroBullet.isAlive()) {
					heroBullet.move();
				}
			}
	}
	//小敌机移动
	public /**/ void enemyPlaneMove() {
		for (EnemyPlane enemyPlane : enemyPlanes) {
			if(enemyPlane.getState() == EnemyPlane.STATE_ALIVE) {
				enemyPlane.move();
			}
		}
	}
	//小敌机进场
	public  void enemyPlaneEnter() {
		EnemyPlane enemyPlane = null;
		int type = new Random().nextInt(6);
		if(type < 3) {
			enemyPlane = new SmallPlane();
		}else if(type < 5) {
			enemyPlane = new BigPlane();
		}else {
			enemyPlane = new BossPlane();
		}
		enemyPlanes.add(enemyPlane);
		
	}
	//敌机发射子弹
	public  void enemyPlaneFire() {
		for (EnemyPlane smallPlane : enemyPlanes) {
			if(!smallPlane.isOutOfBound()&& smallPlane.getState() == SmallPlane.STATE_ALIVE) {
				List<EnemyBullet> fire = smallPlane.fire();
				enemyBullets.addAll(fire);
			}
		}
	}
	//敌机子弹移动
	public  void enemyBulletMove() {
		for (EnemyBullet enemyBullet : enemyBullets) {
			if(!enemyBullet.isOutOfBound()&& enemyBullet.isAlive()) {
				enemyBullet.move();
			}
		}
	}
	//碰撞处理
	private  void objectHit() {
		//英雄子弹与敌机子弹碰撞
		for (HeroBullet heroBullet : heroBullets) {
			for (EnemyBullet enemyBullet : enemyBullets) {
				if(heroBullet.getState() == HeroBullet.STATE_ALIVE && enemyBullet.isAlive() ) {
					if(HitUtil.heroBulletHitBullet(heroBullet, enemyBullet)) {
						heroBullet.setState(HeroBullet.STATE_DEATH);
						enemyBullet.setState(EnemyBullet.STATE_DEATH);
					}
				}
			}
		}
		//英雄子弹与敌机
		for (HeroBullet heroBullet : heroBullets) {
			for (EnemyPlane enemyPlane : enemyPlanes) {
				if(heroBullet.getState() == HeroBullet.STATE_ALIVE && enemyPlane.getState() == SmallPlane.STATE_ALIVE ) {
					if(HitUtil.heroBulletHitPlane(heroBullet, enemyPlane)) {
						heroBullet.setState(HeroBullet.STATE_DEATH);
						//smallPlane.setState(SmallPlane.STATE_DEATH);
						if(enemyPlane.hasLife()) {
							enemyPlane.subLife();
						}else {
							enemyPlane.setState(SmallPlane.STATE_DEATH);
						}
						heroPlane.addScore(enemyPlane.getScore());
					}
				}
			}
		}
		//英雄机与敌机子弹碰撞
		for (EnemyBullet enemyBullet : enemyBullets) {
			if(heroPlane.getState() == HeroBullet.STATE_ALIVE && enemyBullet.isAlive() ) {
				if(HitUtil.heroPlaneHitBullet(heroPlane, enemyBullet)) {
					enemyBullet.setState(EnemyBullet.STATE_DEATH);
					if(heroPlane.hasLife()) {
						heroPlane.subLife(1);
					}else {
						state = GAME_OVER;
					}
					break;
				}
			}
		}
		//英雄机与敌机碰撞
		for (EnemyPlane enemyPlane : enemyPlanes) {
			if(heroPlane.getState() == HeroBullet.STATE_ALIVE && enemyPlane.isAlive() ) {
				if(HitUtil.heroPlaneHitEnamyPlane(heroPlane, enemyPlane)) {
					enemyPlane.setState(EnemyPlane.STATE_DEATH);
					if(heroPlane.hasLife()) {
						heroPlane.subLife(1);
					}else {
						state = GAME_OVER;
					}
					break;
				}
			}
		}
	}
	
	//清除对象
	private  void objectClear() {
		synchronized (heroBullets) {
			//英雄子弹
			Iterator<HeroBullet> iter1 = heroBullets.iterator();
			while (iter1.hasNext()) {
				HeroBullet hb = iter1.next();
				if(hb.isOutOfBound() || !hb.isAlive()) {
					iter1.remove();
				}
			}
		}
		synchronized (enemyBullets) {
			//敌人子弹
			Iterator<EnemyBullet> iter2 = enemyBullets.iterator();
			while (iter2.hasNext()) {
				EnemyBullet eb = iter2.next();
				if(eb.isOutOfBound() || !eb.isAlive()) {
					iter2.remove();
				}
			}
		}
		synchronized (enemyPlanes) {
			//敌机
			Iterator<EnemyPlane> iter3 = enemyPlanes.iterator();
			while (iter3.hasNext()) {
				EnemyPlane enemyPlane = iter3.next();
				if(enemyPlane.isOutOfBound() || enemyPlane.getState() == EnemyPlane.STATE_DELETE) {
					iter3.remove();
				}
			}
		}
		
		synchronized (heroPlane) {
			//英雄
			if(heroPlane.getState() == HeroPlane.STATE_DEATH) {
				if(heroPlane.hasLife()) {
					heroPlane.setState(HeroPlane.STATE_ALIVE);
				}
			}
		}
		
	}
	
}
