package com.demo;

import com.demo.model.ShootGame;

/**
 * 启动类， 游戏开始的类
 * @author dafei
 */
public class StratUp {
	
	public static void main(String[] args) {
		System.out.println("游戏开始了.....");
		//主界面
		ShootGame game = new ShootGame();
		game.init(); //显示主界面
	}
}
