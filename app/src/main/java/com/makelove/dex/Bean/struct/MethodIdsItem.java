package com.makelove.dex.Bean.struct;

public class MethodIdsItem {
	
	/**
	 * struct filed_id_item
		{
		ushort class_idx;
		ushort proto_idx;
		uint name_idx;
		}
	 */
	
	public short class_idx;
	//方法原型 =返回类型 +参数列表
	public short proto_idx;
	public int name_idx;
	
	public static int getSize(){
		return 2 + 2 + 4;
	}
	
	@Override
	public String toString(){
		return "class_idx:"+class_idx+",proto_idx:"+proto_idx+",name_idx:"+name_idx;
	}

}
