package eric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LE201612032 {
  public static void main(String[] args) {
    Obstacle obstacle = new Obstacle();
    int[] obstacles = {0, 4, 12, 15,23}; //ans:18
    //int[] obstacles = {0, 4, 10, 11, 12, 14}; //ans:14
    int count = obstacle.getLongestPath(obstacles) + 1;
    System.out.println("worm行走步數為："+count);
  }
}
class Obstacle {
  static int i=0;
  List<Integer> boundaryRight = Arrays.asList(4, 9, 14, 19, 24);
  List<Integer> boundaryLeft = Arrays.asList(0, 5, 10, 15, 20);
  List<Integer> boundaryTop = Arrays.asList(0, 1, 2, 3, 4);
  List<Integer> boundaryBottom = Arrays.asList(20, 21, 22, 23, 24);
  ArrayList<Integer> boundary = new ArrayList<Integer>();
  ArrayList<Integer> boundaryObstacle = new ArrayList<Integer>();
  Obstacle() {
    boundary.addAll(boundaryRight);
    boundary.addAll(boundaryLeft);
    boundary.addAll(boundaryTop);
    boundary.addAll(boundaryBottom);
  }
  public int getLongestPath(int[] obstacles) {
    //initial obstacles
    for(int i=0; i<obstacles.length; i++) boundaryObstacle.add(obstacles[i]);
   
    return wormGo(0, 4, 0, new ArrayList<>(boundaryObstacle));
  }
  private int wormGo(final int now, int position, int step, ArrayList<Integer> boundaryObstacle) {
    //記錄走過障礙物
    boundaryObstacle.add(now);
    for(int i=0; i<step; i++) {
      if(position==0) boundaryObstacle.add(now + 5*i);
      if(position==1) boundaryObstacle.add(now - 5*i);
      if(position==2) boundaryObstacle.add(now + i);
      if(position==3) boundaryObstacle.add(now - i);
    }
    //System.out.println("round:"+i + " "+now);
    i++;
    if(!allDead(now, position, boundaryObstacle) && position!=4) {
      return step;
    }
    //direction 0:上 1:下 2:左 3:右
    int direction0, direction1, direction2, direction3;
    int next0=now, next1=now, next2=now, next3=now;
    boolean b0, b1, b2, b3;
    b0 = b1 = b2 = b3 = false;
    
    //計算移動後位置
    //上
    direction0 = goRow(now, 0, boundaryObstacle);
    if(direction0!=-1 && direction0!=0) {
      b0 = true;
      next0 = now - direction0*5;
    }
    //下
    direction1 = goRow(now, 1, boundaryObstacle);
    if(direction1!=-1 && direction1!=0) {
      b1 = true;
      next1 = now + direction1*5;
    }
    //左
    direction2 = goColumn(now, 0, boundaryObstacle);
    if(direction2!=-1 && direction2!=0) {
      b2 = true;
      next2 = now - direction2;
    } 
    //右
    direction3 = goColumn(now, 1, boundaryObstacle);
    if(direction3!=-1 && direction3!=0) {
      b3 = true;
      next3 = now + direction3;
    }
    
//    System.out.println(" "+direction0+" "+ direction1+" "+ direction2+" "+ direction3);
//    System.out.println(" "+next0+" "+ next1+" "+ next2+" "+ next3);
//    System.out.println(" "+b0+" "+ b1+" "+ b2+" "+ b3);
//    System.out.println(boundaryObstacle.toString());
      
    if(!b0 && !b1 && !b2 && !b3) return step;
    if(!b0 && !b1 && !b2) return step + wormGo(next3, 3, direction3, new ArrayList<>(boundaryObstacle));
    if(!b0 && !b2 && !b3) return step + wormGo(next1, 1, direction1, new ArrayList<>(boundaryObstacle));
    if(!b0 && !b1 && !b3) return step + wormGo(next2, 2, direction2, new ArrayList<>(boundaryObstacle));
    if(!b1 && !b2 && !b3) return step + wormGo(next0, 0, direction0, new ArrayList<>(boundaryObstacle));
    if(!b0 && !b1) return step + Math.max(wormGo(next2, 2, direction2, new ArrayList<>(boundaryObstacle)), wormGo(next3, 3, direction3, new ArrayList<>(boundaryObstacle)));
    if(!b0 && !b2) return step + Math.max(wormGo(next1, 1, direction1, new ArrayList<>(boundaryObstacle)), wormGo(next3, 3, direction3, new ArrayList<>(boundaryObstacle)));
    if(!b0 && !b3) return step + Math.max(wormGo(next1, 1, direction1, new ArrayList<>(boundaryObstacle)), wormGo(next2, 2, direction2, new ArrayList<>(boundaryObstacle)));
    if(!b1 && !b2) return step + Math.max(wormGo(next0, 0, direction0, new ArrayList<>(boundaryObstacle)), wormGo(next3, 3, direction3, new ArrayList<>(boundaryObstacle)));
    if(!b1 && !b3) return step + Math.max(wormGo(next0, 0, direction0, new ArrayList<>(boundaryObstacle)), wormGo(next2, 2, direction2, new ArrayList<>(boundaryObstacle)));
    if(!b2 && !b3) return step + Math.max(wormGo(next0, 0, direction0, new ArrayList<>(boundaryObstacle)), wormGo(next1, 1, direction1, new ArrayList<>(boundaryObstacle)));
    if(!b0) return step + Math.max(wormGo(next1, 1, direction1, new ArrayList<>(boundaryObstacle)), Math.max(wormGo(next2, 2, direction2, new ArrayList<>(boundaryObstacle)), wormGo(next3, 3, direction3, new ArrayList<>(boundaryObstacle))));
    if(!b1) return step + Math.max(wormGo(next0, 0, direction0, new ArrayList<>(boundaryObstacle)), Math.max(wormGo(next2, 2, direction2, new ArrayList<>(boundaryObstacle)), wormGo(next3, 3, direction3, new ArrayList<>(boundaryObstacle))));
    if(!b2) return step + Math.max(wormGo(next1, 1, direction1, new ArrayList<>(boundaryObstacle)), Math.max(wormGo(next0, 0, direction0, new ArrayList<>(boundaryObstacle)), wormGo(next3, 3, direction3, new ArrayList<>(boundaryObstacle))));
    if(!b3) return step + Math.max(wormGo(next1, 1, direction1, new ArrayList<>(boundaryObstacle)), Math.max(wormGo(next2, 2, direction2, new ArrayList<>(boundaryObstacle)), wormGo(next0, 0, direction0, new ArrayList<>(boundaryObstacle))));
    return -10000000;
  }
  private boolean allDead(int now, int position, ArrayList<Integer> boundaryObstacle) {
    boolean i0 = isDead(now, 0, boundaryObstacle);
    boolean i1 = isDead(now, 1, boundaryObstacle);
    boolean i2 = isDead(now, 2, boundaryObstacle);
    boolean i3 = isDead(now, 3, boundaryObstacle);
    if(position==0) i1 = false;
    else if(position==1) i0 = false;
    else if(position==2) i3 = false;
    else if(position==3) i2 = false;
    return i0 || i1 || i2 || i3;
  }
  //direction 0:上 1:下 2:左 3:右
  private boolean isDead(int now, int position, ArrayList<Integer> boundaryObstacle) {
    if(position==0) {
      if(boundaryTop.contains(now)) return false;
      else if(boundaryObstacle.contains(now-5)) return false;
    }
    else if(position==1) {
      if(boundaryBottom.contains(now)) return false;
      else if(boundaryObstacle.contains(now+5)) return false;
    }
    else if(position==2) {
      if(boundaryLeft.contains(now)) return false;
      else if(boundaryObstacle.contains(now-1)) return false;
    }
    else if(position==3) {
      if(boundaryRight.contains(now)) return false;
      else if(boundaryObstacle.contains(now+1)) return false;
    }
    return true;
  }
  //回傳行走步數
  private int goColumn(int now, int isLeftRight, ArrayList<Integer> boundaryObstacle) {
    int count = 0;
    //isLeftRight:0 left
    if(isLeftRight==0) {    
      do{    
        if(boundaryObstacle.contains(now-1)) {
          break;
        }
        else if(boundaryLeft.contains(now)) {
          break;
        }
        now--;
        count++;
      } while(true);   
    }
    //isLeftRight:1 right
    else {
      do{   
        if(boundaryObstacle.contains(now+1)) {
          break;
        }
        else if(boundaryRight.contains(now)) {
          break;
        }
        now++;
        count++;
      } while(true);        
    } 
    return count;
  }
  private int goRow(int now, int isLeftRight, ArrayList<Integer> boundaryObstacle) {
    int count = 0;
    //isLeftRight:0 top
    if(isLeftRight==0) {
      do{     
        if(boundaryObstacle.contains(now-5)) {
          break;
        }
        else if(boundaryTop.contains(now)) {
          break;
        }
        now-=5;
        count++;
      } while(true);   
    }
    //isLeftRight:1 down
    else {
      do{ 
        if(boundaryObstacle.contains(now+5)) {
          break;
        }
        else if(boundaryBottom.contains(now)) {
          break;
        }
        now+=5;
        count++;
      } while(true);        
    }    
    return count;
  }
}