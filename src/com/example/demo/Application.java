package com.example.demo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Application extends JFrame implements MouseListener, MouseWheelListener, KeyListener {
	
	private final int SCREEN_WIDTH = 800;
	private final int SCREEN_HEIGHT = 600;
	
	private BufferedImage image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private int maxIterations = 35;
	private double zoom = 0.01;
	private double xPos = 0;
	private double yPos = 0;
	
	private double zx, zy, cX, cY, tmp;
	
	private int xMouseStart = 0;
	private int xMouseEnd = 0;
	private int yMouseStart = 0;
	private int yMouseEnd = 0;
	
	
	
	
	public Application() {
		super(" Mandelbrot Set");
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		addMouseListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		
		compute();

	}
	public void compute() {
		
		image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		int modHeight = SCREEN_HEIGHT >> 1;
		int modWidth = SCREEN_WIDTH >> 1;
		
		for(int y = 0; y < SCREEN_HEIGHT; y++) {
			for(int x = 0; x < SCREEN_WIDTH; x++) {
				
				zx = zy = 0;
			
				
				cX = xPos + (x - modWidth) * zoom;
				cY = yPos + (y - modHeight) * zoom;
				
				int iteration;
				
				for(iteration = 0; iteration < maxIterations && zx *zx + zy*zy <4;iteration++) {
					tmp = zx * zx - zy *zy + cX;
					zy = 2.0 * zx * zy + cY;
					zx = tmp;
					
				
				
				}
				
				if(iteration == maxIterations) {
					
				}else {
					double r = iteration | (iteration << 6);
					while(r > 255) {
						r -= 255;
					}
					double g = iteration | (iteration << 2);
					while(g > 255) {
						g -= 255;
					}
					double b = iteration | (iteration << 4);
					while(b > 255) {
						b -= 255;
					}
					Color color = new Color((int) r, (int) g, (int) b);
					image.setRGB(x,y,color.getRGB());
				}
			}
		}
		repaint();
	}
	
	public void paint(Graphics g) {
		g.drawImage(image,  0, 0, this);
	}

	public static void main(String[] args) {
		new Application();
	

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		System.out.println(e);
		if (e.getButton() == 1) {
			xMouseStart = e.getX();
			yMouseStart = e.getY();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == 1) {
			xMouseEnd = e.getX();
			yMouseEnd = e.getY();
			
			double xMove = xMouseEnd - xMouseStart;
			double yMove = yMouseEnd - yMouseStart;
			
			xPos = xPos - (xMove * zoom);
			yPos = yPos - (yMove * zoom);
			
			compute();
		}
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			System.out.println("Mouse Wheel moved up " + -notches + " notches");
			zoom *=0.9;
		}else {
			System.out.println("Mouse Wheel moved down " + notches + " notches");
			zoom *=1.1;
		}
		compute();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			zoom *=0.9;
				compute();
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			zoom *=1.1;
				compute();
		}
		if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
			maxIterations +=5;
			compute();
		}
		if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			maxIterations -=5;
			compute();
		}
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
