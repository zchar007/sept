package com.sept.util.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class WifiUtil {
	public static void main(String[] args) throws Exception {
//{DW8G=dareway2019, Xiaomi_DAREW 2=null, CMCC=Zsfj1022, Xiaomi_DAREW=26974267, 张超的 iPhone=null, dareway_1=DWDW@1402}
		// {5097=50972017, DW8G=dareway2019, CMCC=Zsfj1022, Xiaomi_WIN-3=57527663}
		HashMap<String, String> hmWifis = getAllWifiAndKey();
		System.out.println(hmWifis);
	}

	private static HashMap<String, String> getAllWifiAndKey() throws Exception {
		HashSet<String> wifis = getWifi();
		HashMap<String, String> hmWifis = new HashMap<>();
		Iterator<String> iterator = wifis.iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			hmWifis.put(name, getWifiPassWord(name));
		}
		return hmWifis;
	}

	private static String getWifiPassWord(String wifiName) throws Exception {
		ArrayList<String> alMessage = CmdUtil.run("netsh wlan  show profiles " + wifiName + " key=clear");
		for (int i = 0; i < alMessage.size(); i++) {
			if (alMessage.get(i).indexOf("关键内容") >= 0) {
				return alMessage.get(i).split(":")[1].trim();
			}
		}
		return null;
	}

	private static HashSet<String> getWifi() throws Exception {
		HashSet<String> hsWifi = new HashSet<>();
		ArrayList<String> alMessage = CmdUtil.run("netsh wlan  show profiles ");
		for (int i = 0; i < alMessage.size(); i++) {
			if (alMessage.get(i).indexOf("所有用户配置文件") >= 0) {
				hsWifi.add(alMessage.get(i).split(":")[1].trim());
			}
		}
		return hsWifi;
	}

}
