package com.makelove.test.bean;

import com.makelove.test.utils.Utils;

public class MethodIdsItem implements getData{
	
	/**
	 * struct filed_id_item
		{
		ushort class_idx;
		ushort proto_idx;
		uint name_idx;
		}
	 */
	
	public byte[] class_idx;
	//方法原型 =返回类型 +参数列表
	public byte[] proto_idx;
	public byte[] name_idx;
	
	public static int getSize(){
		return 2 + 2 + 4;
	}

	@Override
	public byte[] getData() {
		return Utils.byteMergerAll(class_idx,proto_idx,
				name_idx);
	}

//	@Override
//	public String toString(){
//		return "class_idx:"+class_idx+",proto_idx:"+proto_idx+",name_idx:"+name_idx;
//	}

}
