package dao;

import java.util.List;

import dto.Player;

/**
 * 数据持久层接口
 * @author 26492
 *
 */
public interface Data {

	/**
	 * 读取数据
	 * @return
	 */
	public List<Player> loadData();
	
	/**
	 * 存储数据
	 * @param pla
	 */
	public void saveData(Player pla);

}
