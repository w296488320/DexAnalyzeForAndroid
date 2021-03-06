package com.makelove.dex.utils;

import android.util.Log;

import com.makelove.dex.Bean.struct.ClassDataItem;
import com.makelove.dex.Bean.struct.ClassDefItem;
import com.makelove.dex.Bean.struct.CodeItem;
import com.makelove.dex.Bean.struct.EncodedField;
import com.makelove.dex.Bean.struct.EncodedMethod;
import com.makelove.dex.Bean.struct.FieldIdsItem;
import com.makelove.dex.Bean.struct.HeaderType;
import com.makelove.dex.Bean.struct.MapItem;
import com.makelove.dex.Bean.struct.MapList;
import com.makelove.dex.Bean.struct.MethodIdsItem;
import com.makelove.dex.Bean.struct.ProtoIdsItem;
import com.makelove.dex.Bean.struct.StringIdsItem;
import com.makelove.dex.Bean.struct.TypeIdsItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ParseDexUtils {


    //classDef 偏移 + size  = classData 起始偏移
    //classData 偏移 + size =Dex总大小
    //Map数据 在 Data里面
	//用010解析 DataHeader里面的 DataSize是
	// Data的总大小 不是 个数 （其余的是个数 ）
	//DataSize+DataOff==fileSize
    private static int HeaderOffset = 0;
	
	private static int StringListCount = 0;
	private static int StringListOffset = 0;

	private static int typeListCount = 0;
	private static int typeListOffset = 0;

	private static int protoListCount = 0;
	private static int protoListOffset = 0;

	private static int fieldListCount = 0;
	private static int fieldListOffset = 0;

	private static int methodListCount = 0;
	private static int methodListOffset = 0;

	private static int classDefListCount = 0;
	private static int classDefListOffset = 0;

	private static int dataListCount = 0;

	//ClassData  解析时候用不上 需要 根据 ClassDef里面的
    //data_offset拿到 偏移 和大小 然后 分别赋值
	private static int dataListOffset = 0;
	//存放data的 偏移 比如 第一个 StringData 数据
	private static int mapListOffset = 0;



	// public  toString 时候需要用到 数据
	public static List<StringIdsItem> stringIdsList = new ArrayList<StringIdsItem>();



    public static List<TypeIdsItem> typeIdsList = new ArrayList<TypeIdsItem>();
    public static List<ProtoIdsItem> protoIdsList = new ArrayList<ProtoIdsItem>();
    public static List<FieldIdsItem> fieldIdsList = new ArrayList<FieldIdsItem>();
    public static List<MethodIdsItem> methodIdsList = new ArrayList<MethodIdsItem>();


    public static List<ClassDefItem> classDefIdsList = new ArrayList<ClassDefItem>();



    public static List<ClassDataItem> classDataItemList = new ArrayList<ClassDataItem>();

    public static List<CodeItem> directMethodCodeItemList = new ArrayList<CodeItem>();
    public static List<CodeItem> virtualMethodCodeItemList = new ArrayList<CodeItem>();

    public static List<String> stringList = new ArrayList<String>();





    public static HashMap<String, ClassDefItem>
			classDataMap = new HashMap<String, ClassDefItem>();

	/**
	 * 解析头部
	 * @param byteSrc
	 */
	public static void praseDexHeader(byte[] byteSrc){
		HeaderType headerType = new HeaderType();
		//魔教
		byte[] magic = Utils.copyByte(byteSrc, 0, 8);
		headerType.magic = magic;
		
		//checksum 效验 dex是否损毁
		byte[] checksumByte = Utils.copyByte(byteSrc, 8, 4);
		headerType.checksum = Utils.byte2int(checksumByte);
		
		//siganature SHA-1  20个字节
		byte[] siganature = Utils.copyByte(byteSrc, 12, 20);
		headerType.siganature = siganature;
		
		//file_size  文件总大小
		byte[] fileSizeByte = Utils.copyByte(byteSrc, 32, 4);
		headerType.file_size = Utils.byte2int(fileSizeByte);
		
		//header_size  头部大小 一般为70和字节
		byte[] headerSizeByte = Utils.copyByte(byteSrc, 36, 4);
		headerType.header_size = Utils.byte2int(headerSizeByte);
		
		//endian_tag  判断大小端
		byte[] endianTagByte = Utils.copyByte(byteSrc, 40, 4);
		headerType.endian_tag = Utils.byte2int(endianTagByte);
		
		//link_size
		byte[] linkSizeByte = Utils.copyByte(byteSrc, 44, 4);
		headerType.link_size = Utils.byte2int(linkSizeByte);
		
		//link_off
		byte[] linkOffByte = Utils.copyByte(byteSrc, 48, 4);
		headerType.link_off = Utils.byte2int(linkOffByte);
		
		//map_off
		byte[] mapOffByte = Utils.copyByte(byteSrc, 52, 4);
		headerType.map_off = Utils.byte2int(mapOffByte);
		
		//string_ids_size
		byte[] stringIdsSizeByte = Utils.copyByte(byteSrc, 56, 4);
		headerType.string_ids_size = Utils.byte2int(stringIdsSizeByte);
		
		//string_ids_off
		byte[] stringIdsOffByte = Utils.copyByte(byteSrc, 60, 4);
		headerType.string_ids_off = Utils.byte2int(stringIdsOffByte);
		
		// type_ids_size
		byte[] typeIdsSizeByte = Utils.copyByte(byteSrc, 64, 4);
		headerType.type_ids_size = Utils.byte2int(typeIdsSizeByte);
		
		// type_ids_off
		byte[] typeIdsOffByte = Utils.copyByte(byteSrc, 68, 4);
		headerType.type_ids_off = Utils.byte2int(typeIdsOffByte);
		
		// proto_ids_size
		byte[] protoIdsSizeByte = Utils.copyByte(byteSrc, 72, 4);
		headerType.proto_ids_size = Utils.byte2int(protoIdsSizeByte);
		
		// proto_ids_off
		byte[] protoIdsOffByte = Utils.copyByte(byteSrc, 76, 4);
		headerType.proto_ids_off = Utils.byte2int(protoIdsOffByte);
		
		// field_ids_size
		byte[] fieldIdsSizeByte = Utils.copyByte(byteSrc, 80, 4);
		headerType.field_ids_size = Utils.byte2int(fieldIdsSizeByte);
		
		// field_ids_off
		byte[] fieldIdsOffByte = Utils.copyByte(byteSrc, 84, 4);
		headerType.field_ids_off = Utils.byte2int(fieldIdsOffByte);
		
		// method_ids_size
		byte[] methodIdsSizeByte = Utils.copyByte(byteSrc, 88, 4);
		headerType.method_ids_size = Utils.byte2int(methodIdsSizeByte);
		
		// method_ids_off
		byte[] methodIdsOffByte = Utils.copyByte(byteSrc, 92, 4);
		headerType.method_ids_off = Utils.byte2int(methodIdsOffByte);
		
		// class_defs_size
		byte[] classDefsSizeByte = Utils.copyByte(byteSrc, 96, 4);
		headerType.class_defs_size = Utils.byte2int(classDefsSizeByte);
		
		// class_defs_off
		byte[] classDefsOffByte = Utils.copyByte(byteSrc, 100, 4);
		headerType.class_defs_off = Utils.byte2int(classDefsOffByte);
		
		// data_size
		byte[] dataSizeByte = Utils.copyByte(byteSrc, 104, 4);
		headerType.data_size = Utils.byte2int(dataSizeByte);
		
		// data_off
		byte[] dataOffByte = Utils.copyByte(byteSrc, 108, 4);
		headerType.data_off = Utils.byte2int(dataOffByte);

		LogUtils.e(headerType.toString());

		
		HeaderOffset = headerType.header_size;
		
		StringListCount = headerType.string_ids_size;
		StringListOffset = headerType.string_ids_off;
		typeListCount = headerType.type_ids_size;
		typeListOffset = headerType.type_ids_off;
		fieldListCount = headerType.field_ids_size;
		fieldListOffset = headerType.field_ids_off;
		protoListCount = headerType.proto_ids_size;
		protoListOffset = headerType.proto_ids_off;
		methodListCount = headerType.method_ids_size;
		methodListOffset = headerType.method_ids_off;
		classDefListCount = headerType.class_defs_size;
		classDefListOffset = headerType.class_defs_off;
		
		mapListOffset = headerType.map_off;
		
	}

	/**
	 *解析字符串的 Item 的偏移地址
	 * （stringIdsList 存放的是地址 不是数据 ）
     * 然后将 地址里面的 内容 转换成 字符串
	 * @param srcByte
	 */
	public static void parseStringIds(byte[] srcByte){
		//单个大小
		int idSize = StringIdsItem.getSize();
		//个数
		int countIds = StringListCount;

		for(int i=0;i<countIds;i++){
			stringIdsList.add(parseStringIdsItem(Utils.copyByte(srcByte,
					StringListOffset+i*idSize, idSize)));
		}
		LogUtils.e("string size:"+stringIdsList.size());


        for(StringIdsItem item : stringIdsList){
            String str = getString(srcByte, item.string_data_off);
//			LogUtils.e("开始偏移地址:"+item.string_data_off);
            LogUtils.e("str:"+str);
            stringList.add(str);
        }
	}




	/**
	 * 解析TypeId
     * (存放dex里面所有的类型)
	 * @param srcByte
	 */
	public static void parseTypeIds(byte[] srcByte){
		int idSize = TypeIdsItem.getSize();
		int countIds = typeListCount;
		for(int i=0;i<countIds;i++){
			typeIdsList.add(parseTypeIdsItem(Utils.copyByte(srcByte,
					typeListOffset +i*idSize, idSize)));
		}
		//这里的descriptor_idx就是解析之后的字符串中的索引值
		for(TypeIdsItem item : typeIdsList){
			int descriptor_idx = item.descriptor_idx;
//			LogUtils.e("在 StringList中的 位置 "+descriptor_idx);
			LogUtils.e("typeStr:"+stringList.get(descriptor_idx));
		}
	}


	/**
	 * 解析ProtoIds(描述方法的原型 方法返回类型+方法参数 )
	 * @param srcByte
	 */
	public static void parseProtoIds(byte[] srcByte){
		//获取大小
		int idSize = ProtoIdsItem.getSize();
		//个数
		int countIds = protoListCount;

		for(int i=0;i<countIds;i++){
			protoIdsList.add(parseProtoIdsItem(Utils.copyByte(srcByte,
                    protoListOffset +i*idSize, idSize)));
		}

        for(ProtoIdsItem item : protoIdsList){
			//L是变量自定义类 ， V是 void  I是 Int
			LogUtils.e("proto:"+stringList.get(item.shorty_idx)
                    //return_type_idx 指向的是 TypeIndex
                    // 相当于stringList  如果 是 0 返回是 void  没有返回类型
					+","+stringList.get(typeIdsList.get
                    //typeIds 指向的是 StringList的idx
                    //而return_type_idx 指向  typeIds
                    //return_type_idx-> TypeIndex->  stringListIdx
                    (item.return_type_idx).descriptor_idx));

			if(item.parameters_off != 0){
			    //说明有参数 重新赋值打印
                ProtoIdsItem itemHasParmeter = parseParameterTypeList(srcByte, item.parameters_off, item);
                itemHasParmeter.shorty_idx=item.shorty_idx;
                itemHasParmeter.return_type_idx=item.return_type_idx;
			}
			LogUtils.e("ProtoIdsItem  item",item.toString());

		}
	}

	/**
	 *
	 * 解析  ProtoIds—>ParameterTypeList
	 * @param srcByte
	 * @param startOff
	 * @param item
	 * @return
	 */
    //解析方法的所有参数类型
	private static ProtoIdsItem parseParameterTypeList(byte[] srcByte, int startOff, ProtoIdsItem item){
        //ParameterTypeList  是由一个 size 和一个 short 集合表示

        //因为保存的是地址 先拿到地址的byte 转换成 相对应的数值
        //指针4字节
		byte[] sizeByte = Utils.copyByte(srcByte, startOff, 4);
        //size 是 有几个 参数 的 size
		int size = Utils.byte2int(sizeByte);
		//这个 list是参数列表的 list 每一个 方法 都有自己的参数列表
		List<String> parametersList = new ArrayList<String>();
		//存放的也是 index  但是用 short储存的
        //储存的也是 Typelist的 位置
		// 需要先拿到 Typelist的 位置  然后拿到里面的
		// descriptor_idx 在从string 里面找到
		// 所以 默认就是 StringList的 位置
		List<Short> typeList = new ArrayList<Short>(size);
		for(int i=0;i<size;i++){
		    // +4 +2*i 前面的 4是 size的 int 大小
            //后面的 short的大小 short占2字节
            //因为 指向的 是一个 Type类型 开始地址 包含了 具体 可看 图片
			byte[] typeByte = Utils.copyByte(srcByte, startOff+4+2*i, 2);
			typeList.add(Utils.byte2Short(typeByte));
		}
		LogUtils.e("param count:"+size);



		for(int i=0;i<typeList.size();i++){
            //拿到 具体 位置 short 是为了压缩空间
            //int是 4个字节 short是 2个
            //拿到 TypeList的 位置
            //拿到 typeID对应的 string
            //***这块需要注意 TYPEID和 String 不一定一一对应
            int StringIndex = typeIdsList.get(typeList.get(i)).descriptor_idx;
            //参数 名称
            String ParameterStr = stringList.get(StringIndex);
            parametersList.add(ParameterStr);
		}

		item.parameterCount = size;
		item.parametersList = parametersList;

		return item;
	}


	/**
	 * 解析 FieldIds
	 * @param srcByte
	 */
	public static void parseFieldIds(byte[] srcByte){
		int idSize = FieldIdsItem.getSize();
		int countIds = fieldListCount;
		for(int i=0;i<countIds;i++){
			fieldIdsList.add(parseFieldIdsItem(Utils.copyByte(srcByte,
					fieldListOffset +i*idSize, idSize)));
		}
		
		for(FieldIdsItem item : fieldIdsList){
			int classIndex = typeIdsList.get(item.class_idx).descriptor_idx;
			int typeIndex = typeIdsList.get(item.type_idx).descriptor_idx;
			LogUtils.e("class:"+stringList.get(classIndex)+",name:"
					//NameIdx 指向stringList的 idx
					//其他两个指向的typeID
                    +stringList.get(item.name_idx)+
                    ",type:"+stringList.get(typeIndex));
		}
	}

	/**
	 * 解析 MethodIds
	 *
	 * @param srcByte
	 */
	public static void parseMethodIds(byte[] srcByte){
		int idSize = MethodIdsItem.getSize();
		int countIds = methodListCount;
		for(int i=0;i<countIds;i++){
			methodIdsList.add(parseMethodIdsItem(Utils.copyByte(srcByte,
					methodListOffset +i*idSize, idSize)));
		}
		
		for(MethodIdsItem item : methodIdsList){
			//class_idx  指向 typeIdsListIdx
			//proto_idx  指向 protoIdsListIds
			//name_idx   指向  StringListIds

			//方法原型 =返回类型 +参数列表
			int classIndex = typeIdsList.get(item.class_idx).descriptor_idx;

			int returnIndex = protoIdsList.get(item.proto_idx).return_type_idx;
			String returnTypeStr = stringList.get(typeIdsList.get(returnIndex).descriptor_idx);

//			int shortIndex = protoIdsList.get(item.proto_idx).shorty_idx;
//			String shortStr = stringList.get(shortIndex);

			List<String> paramList = protoIdsList.get(item.proto_idx).parametersList;
			StringBuilder parameters = new StringBuilder();
			//if (paramList.size() != 0) {
				parameters.append(returnTypeStr+"(");
				for(String str : paramList){
					parameters.append(str+",");
				}
				//parameters.append(")"+shortStr);
				parameters.append(")");
			//}
			LogUtils.e("class:"+stringList.get(classIndex)+",name:"+stringList.get(item.name_idx)+",proto:"+parameters);
		}
		
	}


	/**
	 * 解析 ClassDefIds
	 * @param srcByte
	 */
	public static void parseClassDefIds(byte[] srcByte){
		LogUtils.e("开始 偏移的16进制 classDefListOffset:"+
				Utils.bytesToHexString(Utils.int2Byte(classDefListOffset)));
		LogUtils.e("classDefSize的 个数:"+ classDefListCount);
		int idSize = ClassDefItem.getSize();
		int countIds = classDefListCount;
		for(int i=0;i<countIds;i++){
			classDefIdsList.add(parseClassDefItem(Utils.copyByte(srcByte,
                    classDefListOffset +i*idSize, idSize)));
		}
		for(ClassDefItem item : classDefIdsList){
            //class_idx指向TypeID 的 index
			TypeIdsItem typeItem = typeIdsList.get(item.class_idx);
			LogUtils.e("ClassDefIds","classIdx:"+stringList.get(typeItem.descriptor_idx));


			TypeIdsItem superTypeItem = typeIdsList.get(item.superclass_idx);
			LogUtils.e("ClassDefIds","superitem:"+stringList.get(superTypeItem.descriptor_idx));

			LogUtils.e("ClassData 偏移 "+item.class_data_off);

			//如果 没有找到源文件名 资源  默认被赋值FFFF FFFF FFFF FFFF也就是 -1
            //LogUtils.e("source_file_idx",item.source_file_idx+"");
            String sourceFile=null;
            // Key用class_idx
            if(item.source_file_idx==-1) {
                sourceFile = stringList.get(typeItem.descriptor_idx);
                LogUtils.e("ClassDefIds","没有找到 source_file_idx");
            }else {
                sourceFile = stringList.get(item.source_file_idx);
                LogUtils.e("ClassDefIds","sourceFile:"+sourceFile);
            }


			classDataMap.put(sourceFile, item);
		}
	}
	/**
	 * 解析 ClassDefIds->ClassData
     * 一个classDef对应 一个 classData
	 * @param srcByte
	 */
	public static void parseClassData(byte[] srcByte){
		for(String key : classDataMap.keySet()){
			int dataOffset = classDataMap.get(key).class_data_off;
			//LogUtils.e("data offset:"+Utils.bytesToHexString(Utils.int2Byte(dataOffset)));
			ClassDataItem item = parseClassDataItem(srcByte, dataOffset);

			classDataItemList.add(item);
			LogUtils.e("class item:"+item);
		}
	}



	/**
	 * 解析 ClassDefIds->ClassData->ClassDataItem
     * 对ClassDataItem 进行解析 可以得到 ClassData 具体
	 * @param srcByte
	 */
	private static ClassDataItem parseClassDataItem(byte[] srcByte, int offset){
        //LogUtils.e("size 开始偏移地址:  "+offset);
		ClassDataItem item = new ClassDataItem();
        // 4个 leb128变量
		for(int i=0;i<4;i++){
			byte[] byteAry = Utils.readUnsignedLeb128(srcByte, offset);
			//LogUtils.e("偏移基地址"+offset);
            //LogUtils.e("ByteLength"+byteAry.length);   最前面一个  1
            //循环了4次 把四个size赋值以后
            // 剩下的 就是 4个  Encoded 的 数据
			offset += byteAry.length;
			int size = 0;

			if(byteAry.length == 1){
				size = byteAry[0];
			}else if(byteAry.length == 2){
				size = Utils.byte2Short(byteAry);
			}else if(byteAry.length == 4){
				size = Utils.byte2int(byteAry);
			}
			if(i == 0){
				item.static_fields_size = size;
			}else if(i == 1){
				item.instance_fields_size = size;
			}else if(i == 2){
				item.direct_methods_size = size;
			}else if(i == 3){
				item.virtual_methods_size = size;
			}
		}
		
		
		// static_fields
        //每一个 ClassData有多个 EncodedField  和 EncodeMethod
        //顺序排列
		EncodedField[] staticFieldAry = new EncodedField[item.static_fields_size];

        //LogUtils.e("Encoded 开始偏移地址:  "+offset);
		for(int i=0;i<item.static_fields_size;i++){
			/**
			 *  public int filed_idx_diff;
				public int access_flags;
			 */
			EncodedField staticField = new EncodedField();
			//  Encode里面都是 Leb128数据  先拿到 第一个 数据
			staticField.filed_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
			offset += staticField.filed_idx_diff.length;
			//分别赋值
			staticField.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
			offset += staticField.access_flags.length;

			staticFieldAry[i] = staticField;
		}
		
		// instance_fields 
		EncodedField[] instanceFieldAry = new EncodedField[item.instance_fields_size];
		for(int i=0;i<item.instance_fields_size;i++){
			/**
			 *  public int filed_idx_diff;
				public int access_flags;
			 */
			EncodedField instanceField = new EncodedField();
			instanceField.filed_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceField.filed_idx_diff.length;
			instanceField.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceField.access_flags.length;
			instanceFieldAry[i] = instanceField;
		}
		
		// static_methods
        //和上面数据 是连续上的 不是分开的
		EncodedMethod[] staticMethodsAry = new EncodedMethod[item.direct_methods_size];
		for(int i=0;i<item.direct_methods_size;i++){
			/**
			 *  public byte[] method_idx_diff;
				public byte[] access_flags;
				public byte[] code_off;
			 */
			EncodedMethod directMethod = new EncodedMethod();
			directMethod.method_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
			offset += directMethod.method_idx_diff.length;
			directMethod.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
			offset += directMethod.access_flags.length;



			directMethod.code_off = Utils.readUnsignedLeb128(srcByte, offset);
            //LogUtils.e("code_off 开始偏移地址:  "+directMethod.code_off.length);
			offset += directMethod.code_off.length;
			staticMethodsAry[i] = directMethod;
		}
		
		// virtual_methods 
		EncodedMethod[] instanceMethodsAry = new EncodedMethod[item.virtual_methods_size];
		for(int i=0;i<item.virtual_methods_size;i++){
			/**
			 *  public byte[] method_idx_diff;
				public byte[] access_flags;
				public byte[] code_off;
			 */
			EncodedMethod instanceMethod = new EncodedMethod();
			instanceMethod.method_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceMethod.method_idx_diff.length;
			instanceMethod.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceMethod.access_flags.length;
			instanceMethod.code_off = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceMethod.code_off.length;
			instanceMethodsAry[i] = instanceMethod;
		}
		
		item.static_fields = staticFieldAry;
		item.instance_fields = instanceFieldAry;
		item.direct_methods = staticMethodsAry;
		item.virtual_methods = instanceMethodsAry;
		
		return item;
	}

    /**
     * 解析 classDataItemList  里面 具体 数据
     * 有几个 Encoded
     * @param srcByte
     */
    public static void parseCode(byte[] srcByte){
		for(ClassDataItem item : classDataItemList){
			//分别对 2个Encode进行解析
			for(EncodedMethod item1 : item.direct_methods){
			    //拿到偏移
				int offset = Utils.decodeUleb128(item1.code_off);

				CodeItem items = parseCodeItem(srcByte, offset);
				directMethodCodeItemList.add(items);
				LogUtils.e("direct method item:"+items);
			}
			
			for(EncodedMethod item1 : item.virtual_methods){
				int offset = Utils.decodeUleb128(item1.code_off);
				CodeItem items = parseCodeItem(srcByte, offset);
				virtualMethodCodeItemList.add(items);
				LogUtils.e("virtual method item:"+items);
			}
			
		}
	}
	
	private static CodeItem parseCodeItem(byte[] srcByte, int offset){
		CodeItem item = new CodeItem();
		
		/**
		 *  public short registers_size;
			public short ins_size;
			public short outs_size;
			public short tries_size;
			public int debug_info_off;
			public int insns_size;
			public short[] insns;
		 */
		byte[] regSizeByte = Utils.copyByte(srcByte, offset, 2);
		item.registers_size = Utils.byte2Short(regSizeByte);
		
		byte[] insSizeByte = Utils.copyByte(srcByte, offset+2, 2);
		item.ins_size = Utils.byte2Short(insSizeByte);
		
		byte[] outsSizeByte = Utils.copyByte(srcByte, offset+4, 2);
		item.outs_size = Utils.byte2Short(outsSizeByte);
		
		byte[] triesSizeByte = Utils.copyByte(srcByte, offset+6, 2);
		item.tries_size = Utils.byte2Short(triesSizeByte);
		
		byte[] debugInfoByte = Utils.copyByte(srcByte, offset+8, 4);
		item.debug_info_off = Utils.byte2int(debugInfoByte);
		
		byte[] insnsSizeByte = Utils.copyByte(srcByte, offset+12, 4);
		item.insns_size = Utils.byte2int(insnsSizeByte);
		
		short[] insnsAry = new short[item.insns_size];
		int aryOffset = offset + 16;
		for(int i=0;i<item.insns_size;i++){
			byte[] insnsByte = Utils.copyByte(srcByte, aryOffset+i*2, 2);
			insnsAry[i] = Utils.byte2Short(insnsByte);
		}
		item.insns = insnsAry;
		
		return item;
	}
	
	public static void parseMapItemList(byte[] srcByte){
		MapList mapList = new MapList();
		byte[] sizeByte = Utils.copyByte(srcByte, mapListOffset, 4);
		//size是 Item的个数
		int size = Utils.byte2int(sizeByte);
		for(int i=0;i<size;i++){
			mapList.map_item.add(parseMapItem(Utils.copyByte(srcByte,
                    //因为只有一个size所以只+4
                    mapListOffset+4+i*MapItem.getSize(), MapItem.getSize())));
		}
	}
	
	private static StringIdsItem parseStringIdsItem(byte[] srcByte){
		StringIdsItem item = new StringIdsItem();
		byte[] idsByte = Utils.copyByte(srcByte, 0, 4);
		item.string_data_off = Utils.byte2int(idsByte);
		return item;
	}
	
	private static TypeIdsItem parseTypeIdsItem(byte[] srcByte){
		TypeIdsItem item = new TypeIdsItem();
		byte[] descriptorIdxByte = Utils.copyByte(srcByte, 0, 4);
		item.descriptor_idx = Utils.byte2int(descriptorIdxByte);
		return item;
	}
	
	private static ProtoIdsItem parseProtoIdsItem(byte[] srcByte){
		ProtoIdsItem item = new ProtoIdsItem();
		byte[] shortyIdxByte = Utils.copyByte(srcByte, 0, 4);
		item.shorty_idx = Utils.byte2int(shortyIdxByte);

		byte[] returnTypeIdxByteTest = Utils.copyByte(srcByte, 4, 4);
		item.return_type_idx = Utils.byte2int(returnTypeIdxByteTest);

		byte[] parametersOffByte = Utils.copyByte(srcByte, 8, 4);
		item.parameters_off = Utils.byte2int(parametersOffByte);
		return item;
	}
	
	
	private static FieldIdsItem parseFieldIdsItem(byte[] srcByte){
		FieldIdsItem item = new FieldIdsItem();
		byte[] classIdxByte = Utils.copyByte(srcByte, 0, 2);
		item.class_idx = Utils.byte2Short(classIdxByte);
		byte[] typeIdxByte = Utils.copyByte(srcByte, 2, 2);
		item.type_idx = Utils.byte2Short(typeIdxByte);
		byte[] nameIdxByte = Utils.copyByte(srcByte, 4, 4);
		item.name_idx = Utils.byte2int(nameIdxByte);
		return item;
	}
	
	private static MethodIdsItem parseMethodIdsItem(byte[] srcByte){
		MethodIdsItem item = new MethodIdsItem();
		byte[] classIdxByte = Utils.copyByte(srcByte, 0, 2);
		item.class_idx = Utils.byte2Short(classIdxByte);
		byte[] protoIdxByte = Utils.copyByte(srcByte, 2, 2);
		item.proto_idx = Utils.byte2Short(protoIdxByte);
		byte[] nameIdxByte = Utils.copyByte(srcByte, 4, 4);
		item.name_idx = Utils.byte2int(nameIdxByte);
		return item;
	}
	
	private static ClassDefItem parseClassDefItem(byte[] srcByte){
		ClassDefItem item = new ClassDefItem();
		byte[] classIdxByte = Utils.copyByte(srcByte, 0, 4);
		item.class_idx = Utils.byte2int(classIdxByte);
		byte[] accessFlagsByte = Utils.copyByte(srcByte, 4, 4);
		item.access_flags = Utils.byte2int(accessFlagsByte);
		byte[] superClassIdxByte = Utils.copyByte(srcByte, 8, 4);
		item.superclass_idx = Utils.byte2int(superClassIdxByte);
		byte[] iterfacesOffByte = Utils.copyByte(srcByte, 12, 4);
		item.iterfaces_off = Utils.byte2int(iterfacesOffByte);
		byte[] sourceFileIdxByte = Utils.copyByte(srcByte, 16, 4);
		item.source_file_idx = Utils.byte2int(sourceFileIdxByte);
		
		byte[] annotationsOffByte = Utils.copyByte(srcByte, 20, 4);
		item.annotations_off = Utils.byte2int(annotationsOffByte);
		
		byte[] classDataOffByte = Utils.copyByte(srcByte, 24, 4);
		item.class_data_off = Utils.byte2int(classDataOffByte);
		
		byte[] staticValueOffByte = Utils.copyByte(srcByte, 28, 4);
		item.static_value_off = Utils.byte2int(staticValueOffByte);
		return item;
	}
	
	private static MapItem parseMapItem(byte[] srcByte){
		MapItem item = new MapItem();
		byte[] typeByte = Utils.copyByte(srcByte, 0, 2);
		item.type = Utils.byte2Short(typeByte);
		byte[] unuseByte = Utils.copyByte(srcByte, 2, 2);
		item.unuse = Utils.byte2Short(unuseByte);
		byte[] sizeByte = Utils.copyByte(srcByte, 4, 4);
		item.size = Utils.byte2int(sizeByte);
		byte[] offsetByte = Utils.copyByte(srcByte, 8, 4);
		item.offset = Utils.byte2int(offsetByte);
		return item;
	}


	/**
	 *
	 * 根据偏移地址 返回字符串
	 * @param srcByte  原数据
	 * @param startOff 开始偏移地址 （位置 index）
	 * @return
	 */
	private static String getString(byte[] srcByte, int startOff){
		//第一个字节是字符串的长度
		byte size = srcByte[startOff];
		byte[] strByte = Utils.copyByte(srcByte, startOff+1, size);

		String result = "";
		try{
			result = new String(strByte, "UTF-8");
		}catch(Exception e){
		}
		return result;

		
	}
	
}
