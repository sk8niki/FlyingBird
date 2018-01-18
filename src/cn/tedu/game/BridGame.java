package cn.tedu.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BridGame extends JPanel {
	
	BufferedImage background;
	Ground ground;
	Column column1;
	Column column2;
	Brid brid;
	 //分数
	int score;
	BufferedImage startImage;
	
	/**游戏状态 */
	int state;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAME_OVER = 2;
	
	//boolean gameOver;
	BufferedImage gameOverImage;
	
	
	public BridGame() throws Exception{
		
		    state = START;
			//gameOver = false;
		    startImage = ImageIO.read(getClass().getResource("start.png"));
			gameOverImage = ImageIO.read(getClass().getResource("gameover.png"));
			
			background = ImageIO.read(getClass().getResource("bg.png"));
			ground = new Ground();
			column1 = new Column(1);
			column2 = new Column(2);
			brid = new Brid();
		
	}
	
	
	
	
	
	@Override
    public void paint(Graphics g){
		super.paint(g);
		g.drawImage(background,0,0,null);
		g.drawImage(column1.image,column1.x-column1.width/2,
				column1.y-column1.height/2,null);
		g.drawImage(column2.image,column2.x-column2.width/2,
				column2.y-column2.height/2,null);
		g.drawImage(ground.image,ground.x,ground.y,null);
		//旋转(rotate)绘图坐标系，是API方法
		Graphics2D g2 = (Graphics2D) g;
		g2.rotate(-brid.alpha,brid.x,brid.y);
		g.drawImage(brid.image,brid.x -brid.width/2,
				brid.y-brid.height/2,null);
		g2.rotate(brid.alpha,brid.x,brid.y);
		
		
		//在paint方法中添加绘制分数的算法
		Font f = new Font(Font.SANS_SERIF,Font.BOLD,40);
		g.setFont(f);
		g.drawString(""+score, 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(""+score, 40-3, 60-3);
		
	/**	if(gameOver){
			g.drawImage(gameOverImage,0,0,null);
	}  */
	
		//在paint方法中添加显示游戏结束状态代码
		switch(state){
		case GAME_OVER:
			g.drawImage(gameOverImage,0,0,null);
			break;
		case START:
			g.drawImage(startImage,0,0,null);
			break;
		}
	}
    
	
    //表示游戏流程的控制
	public void action() throws Exception{
		MouseListener l = new MouseAdapter(){
			//鼠标按下
			public void mousePressed(MouseEvent e){
				//鸟向上飞
              //  brid.flappy();
				try{
					switch (state){
					case GAME_OVER:
						column1 = new Column(1);
						column2 = new Column(2);
						brid = new Brid();
						score =0;
						state = START;
						break;
					case START:
						state = RUNNING;
					case RUNNING:
						//鸟向上飞扬
						brid.flappy();
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		};
		//将1挂接到当前的面板上
		addMouseListener(l);
		
		
		while(true){
	/**	if(!gameOver){	
			brid.run();
			brid.fly();
			column1.run();
			column2.run();
			ground.run();
			System.out.println(ground.x);
		}
		if(brid.hit(ground) ||brid.hit(column1) ||brid.hit(column2)){
			gameOver = true;
		}
			//计算分数逻辑
			if(brid.x == column1.x ||brid.x == column2.x){
				score++;
				
			} */
		
			switch(state){
			case START:
				brid.fly();
				ground.run();
				break;
			case RUNNING:
				column1.run();
				column2.run();
				brid.run();//上下移动
				brid.fly();//挥动翅膀
				ground.run();//地面移动
				//计分逻辑
				if(brid.x == column1.x || brid.x == column2.x){
					score++;
				}
				//如果鸟撞上地面游戏就结束
				if(brid.hit(ground)||brid.hit(column1)||brid.hit(column2)){
					state = GAME_OVER;
				}
				break;
			}
			repaint();//重画
			Thread.sleep(20);
		}
	}
	
	
	
	
    
    
	public static void main(String [] args) throws Exception {
	JFrame frame = new JFrame();
	BridGame game = new BridGame();
	frame.setTitle("FlyingBird V1.0");
	frame.add(game);
	frame.setSize(440,670);//设置宽高
	frame.setLocationRelativeTo(null);//窗口居中
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//单机关闭使程序真正退出
	frame.setVisible(true);//设置窗体可见性
	game.action();
	}
}
