package com.first;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class FirstMain {

	public static Jedis jedis = new Jedis("192.168.1.130", 6379, 500);

	public static void main(String[] args) {
		jedis.set("ttt".getBytes(), "tttvalue".getBytes());

		System.out.println(jedis.get("ttt").getBytes());
		KeyClass key = new KeyClass();
		key.name = "key1";
		key.id = "1";
		ValClass val = new ValClass();
		val.val1 = "val1";
		val.val2 = "val2";
		jedis.set(ObjectToByte(key), ObjectToByte(val));
		byte[] val3 = jedis.get(ObjectToByte(key));
		System.out.println(val3.length);

		ValClass res = (ValClass) ByteToObject(val3);
		System.out.println(res.val1);
		ValClass val2 = new ValClass();
		val2.val1 = "val2";
		val2.val2 = "val2";
		jedis.append(ObjectToByte(key), "kkkk".getBytes());

		byte[] val4 = jedis.get(ObjectToByte(key));
		System.out.println(val4.length);
		ValClass res1 = (ValClass)ByteToObject(val4);
		System.out.println(res1.val1);
		
//		List reslist = ByteToObjects(val4);
//		for (Object o : reslist) {
//			ValClass res1 = (ValClass)o;
//			System.out.println(res1.val1);
//		}

	}

	public static List ByteToObjects(byte[] bytes) {
		List objs = new ArrayList();
		Object obj = null;
		Object obj1 = null;
		try {
			// bytearray to object
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);

			obj = oi.readObject();
			objs.add(obj);
			
			oi.skipBytes(94);
			obj1 = oi.readObject();
			objs.add(obj);

			bi.close();
			oi.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return objs;
	}

	public static Object ByteToObject(byte[] bytes) {
		Object obj = null;
		try {
			// bytearray to object
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);

			obj = oi.readObject();
			bi.close();
			oi.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	public static byte[] ObjectToByte(java.lang.Object obj) {
		byte[] bytes = null;
		try {
			// object to bytearray
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);

			bytes = bo.toByteArray();

			bo.close();
			oo.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}
}
