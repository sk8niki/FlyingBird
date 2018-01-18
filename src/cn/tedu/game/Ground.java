package cn.tedu.game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Ground  {
    int x,y; //地面x,y坐标
    BufferedImage image;//图片
    int width,height;//地面的宽高


public Ground(){
	try{//可能会出现错误
		image = ImageIO.read(getClass().getResource("ground.png"));
		x = 0;
		y = 500;
		width = image.getWidth();
		height = image.getHeight();
          		
	}catch(Exception e){}
	
}



public void run(){//地面走一步
	x--;
	if(x<-109){
		x=0;
	}
}
}
