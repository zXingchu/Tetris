package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataDisk implements Data {

	private final String filePath;

	public DataDisk(HashMap<String,String> param) {
		this.filePath=param.get("path");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> loadData() {
		ObjectInputStream ois = null;
		List<Player> dataFromDisk = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filePath));
			dataFromDisk=(List<Player>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataFromDisk;
	}

	@Override
	public void saveData(Player pla) {
		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(filePath));
			oos.writeObject(pla);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
