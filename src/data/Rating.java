package data;


/**
 * GenerateArray.java
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 8 Jul 2013. <br>
 */
public class Rating {

	/**
	 * 
	 */
	public Rating() {
	}
	
	/**
	 * @brief convert ranting scale from 0-5 to a 0/1 vector
	 * 
	 * @param rating
	 * @return
	 */
	public  String convertToExpandedBinary(String rating){
	    
		String uRanting ="";
	
		if        (rating.compareToIgnoreCase("0"  ) == 0){
			uRanting = "10000000000";
		} else if (rating.compareToIgnoreCase("0.5") == 0){
			uRanting = "01000000000";
		} else if (rating.compareToIgnoreCase("1"  ) == 0){
			uRanting = "00100000000";
		} else if (rating.compareToIgnoreCase("1.5") == 0){
			uRanting = "00010000000";
		} else if (rating.compareToIgnoreCase("2"  ) == 0){
			uRanting = "00001000000";
		} else if (rating.compareToIgnoreCase("2.5") == 0){
			uRanting = "00000100000";
		} else if (rating.compareToIgnoreCase("3"  ) == 0){
			uRanting = "00000010000";
		} else if (rating.compareToIgnoreCase("3.5") == 0){
			uRanting = "00000001000";
		} else if (rating.compareToIgnoreCase("4"  ) == 0){
			uRanting = "00000000100";
		} else if (rating.compareToIgnoreCase("4.5") == 0){
			uRanting = "00000000010";
		} else if (rating.compareToIgnoreCase("5"  ) == 0){
			uRanting = "00000000001";
		}else{
            System.out.println("no rating match found");
        }

		return uRanting;
	}
	
	/**
     * @brief convert ranting scale from 0-5 to an accumulative rating
     * 
     * @param rating
     * @return
     */
	public  String convertToCummulative(String rating){
        
        String uRanting ="";
    
        if        (rating.compareToIgnoreCase("0"  ) == 0){
            uRanting = "10000000000";
        } else if (rating.compareToIgnoreCase("0.5") == 0){
            uRanting = "11000000000";
        } else if (rating.compareToIgnoreCase("1"  ) == 0){
            uRanting = "11100000000";
        } else if (rating.compareToIgnoreCase("1.5") == 0){
            uRanting = "11110000000";
        } else if (rating.compareToIgnoreCase("2"  ) == 0){
            uRanting = "11111000000";
        } else if (rating.compareToIgnoreCase("2.5") == 0){
            uRanting = "11111100000";
        } else if (rating.compareToIgnoreCase("3"  ) == 0){
            uRanting = "11111110000";
        } else if (rating.compareToIgnoreCase("3.5") == 0){
            uRanting = "11111111000";
        } else if (rating.compareToIgnoreCase("4"  ) == 0){
            uRanting = "11111111100";
        } else if (rating.compareToIgnoreCase("4.5") == 0){
            uRanting = "11111111110";
        } else if (rating.compareToIgnoreCase("5"  ) == 0){
            uRanting = "11111111111";
        }else{
            System.out.println("no rating match found");
        }

        return uRanting;
    }
	
	/**
	 * @brief 
	 * @param rating
	 * 
	 * @return returns 1 if there is a match
	 */
	public  String getBinaryOccurrence(String rating){
        
        String uRanting ="";
    
        if(rating.compareToIgnoreCase("0"  ) == 0){
            uRanting = "0";
        }
        if (rating.compareToIgnoreCase("0.5") == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("1"  ) == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("1.5") == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("2"  ) == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("2.5") == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("3"  ) == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("3.5") == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("4"  ) == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("4.5") == 0){
            uRanting = "1";
        }
        if (rating.compareToIgnoreCase("5"  ) == 0){
            uRanting = "1";
        } 
        
        System.out.println("no rating match found rating:"+uRanting);
        
        

        return uRanting;
    }
	

}
