package com.cannonmc.auto;



public class GoalPicker {
	
	public static int generalGoal = 35; // (X, 0) (Red team)
	
	public static int[] redGoal = {generalGoal, 0};
	public static int[] yellowGoal = {0, generalGoal};
	public static int[] blueGoal = {-generalGoal, 0};
	public static int[] greenGoal = {0, -generalGoal};
	
	public static int getX(int teamChoice) {
		System.out.println(teamChoice);
		if(teamChoice == 1) {
			return redGoal[0];
			
		}else if (teamChoice == 2) {
			return yellowGoal[0];
			
		}else if (teamChoice == 3) {
			return blueGoal[0];
			
		}else if (teamChoice == 4) {
			return greenGoal[0];
			
		}else {
			return 0;	
		}

	}
	
	public static int getZ(int teamChoice) {
		if(teamChoice == 1) {
			return redGoal[1];
			
		}else if (teamChoice == 2) {
			return yellowGoal[1];
			
		}else if (teamChoice == 3) {
			return blueGoal[1];
			
		}else if (teamChoice == 4) {
			return greenGoal[1];
			
		} else {
			return 0;
		}

	}
	
}
