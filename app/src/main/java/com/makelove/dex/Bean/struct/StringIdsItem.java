package com.makelove.dex.Bean.struct;


import com.makelove.dex.utils.Utils;

public class StringIdsItem {
	
	/**
	 * struct string_ids_item
		{
		uint string_data_off;
		}
	 */
	//存放的都是 偏移
	public int string_data_off;
	
	public static int getSize(){
		return 4;
	}
	
	@Override
	public String toString(){
		return Utils.bytesToHexString(Utils.int2Byte(string_data_off));
	}

}
