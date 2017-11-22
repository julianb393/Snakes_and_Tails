package controllers;

import java.awt.EventQueue;

import views.GameFrame;


public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				GameFrame gb = new GameFrame();
				gb.setVisible(true);
			}
			
		});
	}
	
}
