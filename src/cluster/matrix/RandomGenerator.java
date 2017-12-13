package cluster.matrix;

import java.util.*;
/**
 *
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: RHUL</p>
 *
 * @author Pedro Contreras
 * @version 1.0
 */



public class RandomGenerator {
  public RandomGenerator() {
  }

  /**
   * @brief  This method renerates a pseudo-random number between 0 and @param upperBound
   *
   * @param  upperBound int random number upper bound
   * @param  arrSize    int number of random numbers to be produced
   * @return array random numbers
   */
  public float[] normal(int upperBound, int vectorSize) {
	  
    float [] randomBuffer = new float[vectorSize];
    Random generator      = new Random();

    for (int i = 0; i < vectorSize; i++) {
      float randomIndex = (float) generator.nextDouble();
      randomBuffer[i] = randomIndex;
    }

    return randomBuffer;
  }

  /**
   * this method help testing the Random generator number
   */
  /*
  public static void main(String[] args) {
    Vector res = new Vector();
    RandomGenerator randomgenerator = new RandomGenerator();
    res = randomgenerator.normal(2,100);

    for (int i = 0; i < res.size(); i++) {
      System.out.println(res.get(i));
    }
  }
  */

}
