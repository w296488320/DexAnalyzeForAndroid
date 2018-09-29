package com.makelove.test.bean;



public class TypeIdsItem {
	
	/**
	 * struct type_ids_item
		{
		uint descriptor_idx;
		}
	 */
	//这里的descriptor_idx就是解析之后的字符串中的索引值
	public byte[] descriptor_idx;
	
	public static int getSize(){
		return 4;
	}

//	@Override
//	public byte[] getData() {
//		return this.descriptor_idx;
//	}




//	@Override
//	public String toString(){
//		return Utils.bytesToHexString(Utils.int2Byte(descriptor_idx));
//	}



}
