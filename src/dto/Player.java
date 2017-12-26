package dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Comparable<Player>, Serializable {

	private String name;

	private int point;

	public Player(String name, int point) {
		this.name = name;
		this.point = point;
	}

	public String getName() {
		return name;
	}

	public int getPoint() {
		return point;
	}

	@Override
	public int compareTo(Player pla) {
		return pla.point - this.point;
	}

}
