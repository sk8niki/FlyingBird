package cn.tedu.game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Brid {
  int x,y;
  int width,height;
  int size; //计算小鸟的大小，用于碰撞
  BufferedImage image;
  
  
  double g;//重力加速度
  double t;//两次位置的间隔距离
  double v0;//初始上抛速度
  double speed;//是当前的上抛速度
  double s;//经过时间t后的位移
  double alpha;//鸟的倾角，弧度单位
  
  
  BufferedImage[] images;//定义一组数组图片，鸟的动画帧
  int index; //是动画帧数元素的下标位置
  
  
  
  public Brid() throws Exception{
	  image = ImageIO.read(getClass().getResource("0.png"));
	  width = image.getWidth();
	  height = image.getHeight();
	  size = 40;
	  x=132;
	  y=280;
	  
	  g=4;
	  v0=20;
	  t=0.25;
	  speed=v0;
	  s=0;
	  alpha=0;
	  
	  
	  images = new BufferedImage[8];
	  for(int i =0;i<8;i++){
		  //i=0 1 2 3 4 5 6 7
		  images[i] = ImageIO.read(getClass().getResource(i+".png"));
	  }
	  index=0;
	  
  }
  
  //在Brid添加飞翔(fly)的代码
  public void fly(){
	  index++;
	  image = images[(index/12)%8];
  }
  
  
  
  //添加鸟类的移动方法
  public void run(){
	  double v0 = speed;
	  s=v0*t+g*t*t/2;//计算上抛位移速度
	  y = y-(int)s;//计算鸟的坐标位置
	  double v = v0-g*t;//计算下次的速度
	  speed = v;
	  
	  alpha = Math.atan(s/8);//调用API提供的反正切函数，计算倾角
  }
  
  
  
  public void flappy(){
	  //重新设置初始速度，重新向上飞
	  speed = v0; 
  }
  
  
  //碰撞方法的代码
  public boolean hit(Ground ground){
	  boolean hit = y+size/2>ground.y;
	  if(hit){
		  //将鸟放置在地上
		  y = ground.y - size/2;
		  //使鸟落地时，有摔倒的效果
		  alpha = -3.14159265358979323/2;
	  }
	  return hit;
  }
  
  
  //碰撞柱子的代码
  public boolean hit(Column column){
	  if(x>column.x - column.width/2 - size/2
			  && x<column.x + column.width/2 + size/2){
		  //检测是否在缝隙中
		  if(y>column.y - column.gap/2 + size/2
				  && y<column.y + column.gap/2 - size/2){
			  return false;
		  }
		  return true;
	  }
	  return false;
  }
}
