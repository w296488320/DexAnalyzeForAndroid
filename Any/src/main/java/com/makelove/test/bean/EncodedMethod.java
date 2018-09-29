package com.makelove.test.bean;


import com.makelove.test.utils.Utils;

public class EncodedMethod {
	
	/**
	 * struct encoded_method
		{
			uleb128 method_idx_diff;
			uleb128 access_flags;
			uleb128 code_off;
		}
	 */

	//都是 uleb128类型的
	//长度都是 可变的
	public byte[] method_idx_diff;
	public byte[] access_flags;
	//存放 dataItem的 偏移
	public byte[] code_off;

	public CodeItem CodeItem;

//	@Override
//	public byte[] getData() {
//		return Utils.byteMergerAll(method_idx_diff,access_flags,code_off);
//	}

	/**
	 * 返回CodeItem
	 * @return
	 */
	public CodeItem getCodeItem(){
		if(getCodeItemOff()!=0){
			return CodeItem;
		}
		return null;
	}




	/**
	 * 返回CodeItem的偏移
	 * NULL则为0
	 * @return
	 */
	public int getCodeItemOff(){
		int size=0;
		if(code_off!=null) {
			if(code_off.length == 1){
				size = code_off[0];
			}else if(code_off.length == 2){
				size = Utils.byte2Short(code_off);
			}else if(code_off.length == 4){
				size = Utils.byte2int(code_off);
			}
		}
		return size;
	}







//	@Override
//	public String toString(){
//		return "method_idx_diff:"+Utils.bytesToHexString(method_idx_diff)+","+ Utils.bytesToHexString(Utils.int2Byte(Utils.decodeUleb128(method_idx_diff)))
//				+",access_flags:"+Utils.bytesToHexString(access_flags)+","+Utils.bytesToHexString(Utils.int2Byte(Utils.decodeUleb128(access_flags)))
//				+",code_off:"+Utils.bytesToHexString(code_off)+","+Utils.bytesToHexString(Utils.int2Byte(Utils.decodeUleb128(code_off)));
//	}

}
