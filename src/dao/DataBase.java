package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataBase implements Data{

	@SuppressWarnings("unused")
	private final String driver;
	@SuppressWarnings("unused")
	private final String dbUrl;
	@SuppressWarnings("unused")
	private final String dbUser;
	@SuppressWarnings("unused")
	private final String dbPwd;
	
	public DataBase(HashMap<String,String> param) {
		this.driver=param.get("driver");
		this.dbUrl=param.get("dbUrl");
		this.dbUser=param.get("dbUser");
		this.dbPwd=param.get("dbPwd");
	}
	
	@Override
	public List<Player> loadData() {
		List<Player> players=new ArrayList<Player>();
		players.add(new Player("zxc", 1000));
		players.add(new Player("zxc", 5000));
		players.add(new Player("zxc", 3000));
		players.add(new Player("zxc", 2000));
		players.add(new Player("zxc", 4100));
		players.add(new Player("zxc", 3600));
		return players;
	}

	@Override
	public void saveData(Player pla) {
		
		
	}

}
