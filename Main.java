package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj=new JFrame();
		Gameplay gamePlay=new Gameplay();
		obj.setBounds(200,30,800,700);
		obj.setTitle("Brick Breaker game in Java");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
  }
}
