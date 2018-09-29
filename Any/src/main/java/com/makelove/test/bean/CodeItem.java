package com.makelove.test.bean;


import com.makelove.test.utils.Utils;

/**
 *
 */
public class CodeItem {


	private int startOff;

	public CodeItem(int startOff){
		this.startOff=startOff;
	}


	
	/**
	 * struct code_item
		{
			ushort registers_size;
			ushort ins_size;
			ushort outs_size;
			ushort tries_size;
			uint debug_info_off;

			uint insns_size;
			ushort insns [ insns_size ];



			ushort paddding; // optional
			try_item tries [ tyies_size ]; // optional
			encoded_catch_handler_list handlers; // optional
		}
	 /**
	 *将DebugInfoOff 偏移改成 0
	 * aijiami的 会改偏移地址导致
	 * jadx解析报错
	 */
	public byte[] setDebug_info_off_Zero(byte[] src){
		//先拿到 DebugInfoOff
		byte[] bytes = Utils.copyByte(src, startOff + 8, 4);
		//设置成0
		Utils.byteSetZero(bytes);
		//替换
		return Utils.setByte(src, startOff + 8, bytes);

	}




	/*
	 拿到 insns_size的 大小
	 * @param src
	 * @param startOff
	 * @return
	 */
	public  int getInsnsSize(byte[] src){
		//
		byte[] bytes = Utils.copyByte(src, startOff + 12, 4);

		int size = Utils.byte2int(bytes);

		return size;
	}

	/**
	 * 将 Insns数据段 设置成指定数据
	 * 返回最新的 总数据段
	 * @param src
	 * @param rep
	 * @return
	 */
	public  byte[] SetInsns(byte[] src,byte[] rep) {
		//return Utils.copyByte(src, startOff + 16, getInsnsSize(src, startOff));
		return  Utils.setByte(src,startOff+12,rep);
	}

	/**
	 * 返回Insns数据
	 * @param src
	 * @return
	 */
	public  byte[] getInsns(byte[] src) {
		return Utils.copyByte(src, startOff + 16, getInsnsSize(src));
	}




//	public byte[] registers_size;
//	public byte[] ins_size;
//	public byte[] outs_size;
//	public byte[] tries_size;
//	public byte[] debug_info_off;
//	public byte[] insns_size;
//	public byte[] insns;
//
//	@Override
//	public byte[] getData() {
//		return Utils.byteMergerAll(registers_size,ins_size,
//				outs_size,tries_size,debug_info_off,insns_size,insns);
//	}












//	@Override
//	public String toString(){
//		return "regsize:"+registers_size+",ins_size:"+ins_size
//				+",outs_size:"+outs_size+",tries_size:"+tries_size+",debug_info_off:"+debug_info_off
//				+",insns_size:"+insns_size + "\ninsns:"+getInsnsStr();
//	}
//
//	private String getInsnsStr(){
//		StringBuilder sb = new StringBuilder();
//		for(int i=0;i<insns.length;i++){
//			sb.append(Utils.bytesToHexString(Utils.short2Byte(insns[i]))+",");
//		}
//		return sb.toString();
//	}
	
}
