package pacManPackage;

//makes reading the game speeds easier
public enum GameSpeeds {

	
	SPEED100(7),
	SPEED90(8),
	SPEED85(9),
	SPEED80(10),
	SPEED75(11),
	SPEED70(11),
	SPEED65(12),
	SPEED60(13),
	SPEED55(14),
	SPEED50(15),
	SPEED45(16),
	SPEED40(16),
	SPEED20(20),
	SUPERSPEED(1);
	
	public final Integer value;
	
	private GameSpeeds(Integer value) {
		
		this.value = value;
	}

}
