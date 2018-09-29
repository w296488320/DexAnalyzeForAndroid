package com.makelove.test.bean;



public class StringIdsItem implements getData{
	
	/**
	 * struct string_ids_item
		{
		uint string_data_off;
		}
	 */
	//存放的都是 偏移
	public byte[] string_data_off;
	
	public static int getSize(){
		return 4;
	}

    @Override
    public byte[] getData() {
        return this.string_data_off;
    }



//	@Override
//	public String toString(){
//		return Utils.bytesToHexString(Utils.int2Byte(string_data_off));
//	}




}
