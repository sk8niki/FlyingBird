package cn.tedu.game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Column {
int x,y;//坐标
int width,height;
BufferedImage image;
int gap;//上下柱子间隙
int distance;//水平方向柱子间的距离


public Column(int n){
    try{
    	image = ImageIO.read(getClass().getResource("column.png"));
    	width = image.getWidth();
    	height = image.getHeight();
    	gap = 144;
    	distance = 245;
    	x = 550+(n-1)*distance;
    	y = (int)(Math.random()*218)+132;
    	
    }catch(Exception e){}	
}


public void run(){
	x--;
	if(x<= width/2){
		x = distance*2 - width/2;
		y = (int)(Math.random()*218)+132;
	}
}


}