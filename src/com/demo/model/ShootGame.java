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
 * ��Ϸ�������� -- ����
 * 1:Ӣ�ۻ�
 * 2:�л�
 * 3:�����ӵ�
 * 4:������ͼ
 * @author dafei
 */
public class ShootGame extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 400;  //��ͼ������
	public static final int HEIGHT = 654;  //��ͼ���泤��
	
	//����ͼ
	private static BufferedImage background1 = ImageUtil.readImage("background1.jpg");
	private static BufferedImage background2 = ImageUtil.readImage("background2.png");
	private static BufferedImage start = ImageUtil.readImage("start.png");
	private static BufferedImage pause = ImageUtil.readImage("pause.png");
	private static BufferedImage gameover = ImageUtil.readImage("gameover.png");
	
	
	public static final int START = 0;	//����״̬
	public static final int RUNNING = 1;	//����״̬
	public static final int PAUSE = 2;	//��ͣ״̬
	public static final int GAME_OVER = 3;	//����״̬
	private int state = START;	//��Ϸ��״̬(Ĭ������״̬)
	
	
	private List<HeroBullet> heroBullets = new ArrayList<>();  //Ӣ���ӵ�
	private List<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>(); //�����ӵ�
	
	private int y = 0;
	private int y2 = -background1.getHeight();
	
	
	private boolean fireG1 = true;
	private boolean fireG2 = false;
	private boolean fireG3 = false;
	private boolean fireMax = false;
	
	//Ӣ�۷ɻ�
	private HeroPlane heroPlane = new HeroPlane();
	
	//Ӣ�ۻ�
	private void paintHeroPhone(Graphics g) {
		BufferedImage img = heroPlane.getImage();
		
		if(img == null) {
			return;
		}
		//����Ӣ�۷ɻ�
		if(heroPlane.getX() == 0) {
			g.drawImage(img, WIDTH/2-img.getWidth()/2, HEIGHT/2+img.getHeight(), null);
		}else {
			g.drawImage(img, heroPlane.getX()-img.getWidth()/2, heroPlane.getY()-img.getHeight()/2, null);
		}
	}
	
	//������
	private void paintBackground(Graphics g) {
		//����1��ͼƬ�� ����2��x�� ����3��y��
		g.drawImage(background1, 0, 0, null);
		g.drawImage(background2, 0, y, null);
		g.drawImage(background2, 0, y2, null);
	}
	
	//����ͼ�ƶ�
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
	
	//��������������
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(255, 0, 0));	//���û�����ɫ	��0	��0	��0	(0~255)
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));	//��������		���塢��ʽ���ֺ�
		g.drawString("score: "+heroPlane.getScore(), 10, 25);
		g.drawString("life: "+heroPlane.getLife(), 10, 45);
	}
	//�����յĵ÷�
	public void paintResultScore(Graphics g) {
		g.setColor(new Color(255, 0, 0));	//���û�����ɫ	��255	��0	��0	(0~255)
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));	//��������		���塢��ʽ���ֺ�
		g.drawString("score: "+heroPlane.getScore(), 100, 220);
	}
		
	//��״̬
	public void paintState(Graphics g) {
		switch(state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case RUNNING:
			paintScoreAndLife(g);	//��������������(����)
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			paintResultScore(g);		//��������յĵ÷�(����)
		}
	}
	
	//��Ӣ�ۻ��ӵ�
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
	
	//С�л�
	private List<EnemyPlane> enemyPlanes = new ArrayList<>();
	
	//������
	public  void paintEnemy(Graphics g) {
		
		for (int i = 0; i < enemyPlanes.size(); i++) {
			EnemyPlane sp = enemyPlanes.get(i);
			BufferedImage image = sp.getImage();
			if(image != null) {
				g.drawImage(image,sp.getX(),sp.getY(), null);
			}
		}
	}
	
	//���л��ӵ�
	public  void enemyBullet(Graphics g) {
		
		for (int i = 0; i < enemyBullets.size(); i++) {
			EnemyBullet enemyBullet = enemyBullets.get(i);
			BufferedImage img = enemyBullet.getCurrentImage();
			if(img != null) {
				g.drawImage(img, enemyBullet.getX()+img.getWidth()/2, enemyBullet.getY(), null);
			}
		}
	}
	
	//��д����ķ������Զ��廭��
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
		JFrame jFrame = new JFrame("�ɻ���ս");  //����ͷ
		jFrame.add(this);
		
		//���ô��ڵĳ���
		jFrame.setSize(WIDTH, HEIGHT);
		//���ô��������ڶ���
		jFrame.setAlwaysOnTop(true);		
		//���ô��ھ���
		jFrame.setLocationRelativeTo(null);	
		//�رմ���ʱ�˳�����
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		jFrame.setResizable(false);
		//���ô���Ϊ�ɼ�	
		jFrame.setVisible(true);	
		
		//��ʼ��������
		this.initListener();
		//��ʼ����ʱ��
		this.initTimer();
	}
	
	private long count =0l;
	private void initTimer() {
		Timer timer = new Timer();
		int delay = 10;	//�ӳ�ʱ��(��λΪ����)
		int period = 10;	//����(��λΪ����)
		timer.schedule(new TimerTask() {
			//��ʱ�ɵ���
			public void run() {
				if(state == RUNNING) {
					count++;
					if(count % 20 == 0 || fireMax) {
						//Ӣ�۷ɻ�����
						heroPlaneFire();
					}
					//Ӣ���ӵ��ƶ�
					heroBulletMove();
					
					if(count % 100 == 0) {
						//С�л�����
						enemyPlaneEnter();
					}
					
					
					//С�л��ƶ�
					if(count % 2 == 0) {
						enemyPlaneMove();
					}
					
					if(count%200 == 0) {	
						enemyPlaneFire();	//�л������ӵ�
					}
					
					//�л��ӵ��ƶ�
					enemyBulletMove(); 
					
					
					//��ײ
					objectHit();
					
					//���
					if(count % 100 == 0) {
						objectClear();
					}
					
					//�����ƶ�
					backgroundMove();
					if(count == Long.MAX_VALUE - 10) {
						count = 0L;
					}
				}
				//���»�����
				repaint();	
			}

			
		}, delay, period);
	}
	//��ʼ��������
	private void initListener() {
		//������������
		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				heroPlane.move(e.getX(), e.getY());
				//���»�����
				repaint();
			}
			//��д������¼�
			public void mouseClicked(MouseEvent e) {
				System.out.println(state);
				switch(state) {
					case GAME_OVER:
						state = START;
						//���
						enemyPlanes = new ArrayList<>();
						enemyBullets = new  ArrayList<>();
						heroBullets = new ArrayList<>();
						heroPlane = new HeroPlane();
						
						break;
					case START:
						state = RUNNING;
				}
			}
			//��д�������¼�
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE) {
					state = RUNNING;
				}
			}
			//��д����Ƴ��¼�
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING) {
					state = PAUSE;
				}
			}
		};
		
		this.addMouseListener(adapter);
		this.addMouseMotionListener(adapter);
		
		//�ؼ�
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
	//Ӣ�ۻ������ӵ�
	public void heroPlaneFire() {
		if(heroPlane.getState() == HeroPlane.STATE_ALIVE) {
			HeroBullet hb = heroPlane.fire();
				heroBullets.add(hb);
		}
	}
	//Ӣ���ӵ��ƶ�
	public void heroBulletMove() {
			for (HeroBullet heroBullet : heroBullets) {
				if(heroBullet.isAlive()) {
					heroBullet.move();
				}
			}
	}
	//С�л��ƶ�
	public /**/ void enemyPlaneMove() {
		for (EnemyPlane enemyPlane : enemyPlanes) {
			if(enemyPlane.getState() == EnemyPlane.STATE_ALIVE) {
				enemyPlane.move();
			}
		}
	}
	//С�л�����
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
	//�л������ӵ�
	public  void enemyPlaneFire() {
		for (EnemyPlane smallPlane : enemyPlanes) {
			if(!smallPlane.isOutOfBound()&& smallPlane.getState() == SmallPlane.STATE_ALIVE) {
				List<EnemyBullet> fire = smallPlane.fire();
				enemyBullets.addAll(fire);
			}
		}
	}
	//�л��ӵ��ƶ�
	public  void enemyBulletMove() {
		for (EnemyBullet enemyBullet : enemyBullets) {
			if(!enemyBullet.isOutOfBound()&& enemyBullet.isAlive()) {
				enemyBullet.move();
			}
		}
	}
	//��ײ����
	private  void objectHit() {
		//Ӣ���ӵ���л��ӵ���ײ
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
		//Ӣ���ӵ���л�
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
		//Ӣ�ۻ���л��ӵ���ײ
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
		//Ӣ�ۻ���л���ײ
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
	
	//�������
	private  void objectClear() {
		synchronized (heroBullets) {
			//Ӣ���ӵ�
			Iterator<HeroBullet> iter1 = heroBullets.iterator();
			while (iter1.hasNext()) {
				HeroBullet hb = iter1.next();
				if(hb.isOutOfBound() || !hb.isAlive()) {
					iter1.remove();
				}
			}
		}
		synchronized (enemyBullets) {
			//�����ӵ�
			Iterator<EnemyBullet> iter2 = enemyBullets.iterator();
			while (iter2.hasNext()) {
				EnemyBullet eb = iter2.next();
				if(eb.isOutOfBound() || !eb.isAlive()) {
					iter2.remove();
				}
			}
		}
		synchronized (enemyPlanes) {
			//�л�
			Iterator<EnemyPlane> iter3 = enemyPlanes.iterator();
			while (iter3.hasNext()) {
				EnemyPlane enemyPlane = iter3.next();
				if(enemyPlane.isOutOfBound() || enemyPlane.getState() == EnemyPlane.STATE_DELETE) {
					iter3.remove();
				}
			}
		}
		
		synchronized (heroPlane) {
			//Ӣ��
			if(heroPlane.getState() == HeroPlane.STATE_DEATH) {
				if(heroPlane.hasLife()) {
					heroPlane.setState(HeroPlane.STATE_ALIVE);
				}
			}
		}
		
	}
	
}
