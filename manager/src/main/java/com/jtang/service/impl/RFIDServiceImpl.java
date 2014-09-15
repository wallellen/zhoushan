package com.jtang.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jtang.service.IRFIDService;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;

public class RFIDServiceImpl implements IRFIDService {


	public static int getPortAdr() {
		return portAdr;
	}

	public static void setPortAdr(int portAdr) {
		RFIDServiceImpl.portAdr = portAdr;
	}

	public static ByteByReference getComAdr() {
		return comAdr;
	}

	public static void setComAdr(ByteByReference comAdr) {
		RFIDServiceImpl.comAdr = comAdr;
	}

	private static int portAdr;
	private static ByteByReference comAdr=new ByteByReference((byte) 0xFF); 
	private static int frmHandle;
	private final int MAX=256;	
	
	private interface ReaderApiDll extends Library{		
		
		ReaderApiDll INSTANCE = (ReaderApiDll)Native.loadLibrary(RFIDServiceImpl.class.getResource("/").getPath().replace("classes", "dll").substring(1)+"UHFReader18.dll", ReaderApiDll.class);
		int  AutoOpenComPort(IntByReference  port,ByteByReference comAdr,byte baud,IntByReference  frmHandle);
		int CloseComPort();
		int GetReaderInformation(ByteByReference comaddr,Pointer versionInfo,ByteByReference readerType,ByteByReference trType,ByteByReference dmaxFre,ByteByReference dminFre,ByteByReference powerBm,ByteByReference scanTime,int FrmHandel);
		int SetWorkMode(ByteByReference comaddr,byte[] param,int frmHandle);
		int GetWorkModeParameter (ByteByReference comaddr,byte[] param,int frmHandle);
		//int ReadCard_G2(ByteByReference comaddr,byte[] epcNum,byte memArea,byte wordPtr,byte wordNum,byte[] passWd,byte[] data,)
		int Inventory_G2(ByteByReference comaddr,byte adrTID,byte lenTID,byte flagTID,byte[] epcData,IntByReference dataLen,IntByReference cardNum,int frmHandle);
	}
	
	public static int getFrmHandle() {
		return frmHandle;
	}

	public static void setFrmHandle(int frmHandle) {
		RFIDServiceImpl.frmHandle = frmHandle;
	}

	public int getMAX() {
		return MAX;
	}

	
	
	public Map<String,Object> openPort() {
		// TODO Auto-generated method stub
		
		IntByReference  port=new IntByReference();
		IntByReference tmpfrmHandle=new IntByReference();
		//System.out.println("comAddr:"+comAdr.getValue());
		byte baud=4;
		int res=ReaderApiDll.INSTANCE.AutoOpenComPort(port,RFIDServiceImpl.comAdr, baud, tmpfrmHandle);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("res", res);
		if(res!=0) {
			System.out.println("res:"+res+" .Reader open failed");
			tmpfrmHandle=null;
		}
		else{
			map.put("port", port.getValue());
			map.put("handle", tmpfrmHandle.getValue());
			map.put("adr", (int) RFIDServiceImpl.comAdr.getValue());
			System.out.println("Reader open at Port:"+port.getValue());
			System.out.println("Reader open at Handle:"+tmpfrmHandle.getValue());
			System.out.println("Reader open at Adr:"+RFIDServiceImpl.comAdr.getValue());
			RFIDServiceImpl.frmHandle=tmpfrmHandle.getValue();
			RFIDServiceImpl.portAdr = port.getValue();
		}
		return map;
	}

	public int closePort() {
		int res=ReaderApiDll.INSTANCE.CloseComPort();
		if(res!=0)
			System.out.println("Reader close failed");
		else
			System.out.println("Reader closed");
		
		return res;
	}

	/**
	 * 获取读写器配置信息
	 */
	public Map<String,Object> getReaderInfo() {
		// TODO Auto-generated method stub
		Pointer versionInfo=new Memory(2);
		ByteByReference readerType=new ByteByReference();
		ByteByReference trType=new ByteByReference();
		ByteByReference dmaxFre=new ByteByReference();
		ByteByReference dminFre=new ByteByReference();
		ByteByReference powerBm=new ByteByReference();
		ByteByReference scanTime=new ByteByReference();
		int res=ReaderApiDll.INSTANCE.GetReaderInformation(RFIDServiceImpl.comAdr, versionInfo, readerType, trType, dmaxFre, dminFre, powerBm, scanTime,RFIDServiceImpl.frmHandle);
		
		Map<String,Object> data =  new HashMap<String,Object>();
		data.put("res", res);
		data.put("readerType", readerType.getValue());
		data.put("trType", trType.getValue());
		data.put("dmaxFre", dmaxFre.getValue());
		data.put("dminFre", dminFre.getValue());
		data.put("powerBm", powerBm.getValue());
		data.put("scanTime", scanTime.getValue());
		
		//port info
		data.put("portNum", RFIDServiceImpl.portAdr);
		data.put("portAdr", RFIDServiceImpl.comAdr.getValue());
		data.put("frmHandler", RFIDServiceImpl.frmHandle);
		
		return data;
	}
	
	/**
	 * 
	 * @return byte[12]
	 *    0:wg_mode
	 *    1:wg_data_inteval
	 *    2:wg_pulse_width
	 *    3:wg_pulse_inteval
	 *    4:read_mode 
	 *    	0:应答模式 1：主动模式 2：触发模式（低电平） 3：触发模式（高电平）
	 *    5:mode_state
	 *      
	 *    6:mem_inven
	 *    		当读写器工作在18000-6C协议时才有效，选择要读取的存储区或询查标签。
	 *    		0x00：保留区；0x01：EPC存储器；0x02：TID存储器；0x03：用户存储器；0x04：多张查询；0x05：单张查询；0x06 ：EAS检测。
	 *    		其他值保留，若命令中出现了其它值，将返回参数出错的消息
	 *    7:first_adr
	 *    8:word_num
	 *    9:tag_time
	 *    10:accuracy
	 *    11:result  读取结果 成功为0 其他值失败
	 *    
	 */
	public byte[] getReaderMode(){
		byte[] modeparams=new byte[12];
		int res=ReaderApiDll.INSTANCE.GetWorkModeParameter(RFIDServiceImpl.comAdr, modeparams, RFIDServiceImpl.frmHandle);
		//最后一位标志位操作结果位
		modeparams[11]=(byte)res;
		return modeparams;
	}
	
	private int setReaderAllMode(byte[] modeparam){
		for (int i=0;i<6;i++) System.out.print(modeparam[i]);
		int res=ReaderApiDll.INSTANCE.SetWorkMode(RFIDServiceImpl.comAdr, modeparam, RFIDServiceImpl.frmHandle);
		return res;
	}

	/**
	 * 设置蜂鸣开启关闭
	 * @param mode
	 * 	0:开启蜂鸣 1：关闭蜂鸣
	 * @return
	 */
	public int setSoundMode(int mode){
		byte[] paras=getReaderMode();
		
		byte cur=paras[5];
		byte modeState=(byte) ((cur&0xFB)|(mode<<2));
		System.out.println(modeState);
		paras[5]=modeState;
		byte[] newparas=new byte[6];
		System.arraycopy(paras,4,newparas,0,6);
		int res=setReaderAllMode(newparas);
		return res;
	}
	
	/**
	 * 设置阅读器模式
	 * @param mode
	 * 	 0:应答模式 1：主动模式 2：触发模式（低电平） 3：触发模式（高电平）
	 * @return
	 */

	public int setReadMode(int mode){
		byte[] paras=getReaderMode();
		paras[4]=(byte)mode;
		byte[] newparas=new byte[6];
		System.arraycopy(paras,4,newparas,0,6);
		int res=setReaderAllMode(newparas);
		return res;
	}

	/**
	 * 设置读取区域
	 * @param mode
	 * 	0x00：保留区；0x01：EPC存储器；0x02：TID存储器；0x03：用户存储器；0x04：多张查询；0x05：单张查询；0x06 ：EAS检测。
	 * @return
	 */
	public int setMemArea(int mode){
		byte[] paras=getReaderMode();
		paras[6]=(byte)mode;
		byte[] newparas=new byte[6];
		System.arraycopy(paras,4,newparas,0,6);
		int res=setReaderAllMode(newparas);
		return res;
	}
	

	public HashMap<String,Object> queryActiveTagsEPC() {
		// TODO Auto-generated method stub
		byte[] epcData=new byte[MAX];
		IntByReference dataLen=new IntByReference();
		IntByReference cardNum=new IntByReference();
		int res=ReaderApiDll.INSTANCE.Inventory_G2(RFIDServiceImpl.comAdr,(byte)0,(byte)0,(byte)0,epcData,dataLen,cardNum, RFIDServiceImpl.frmHandle);
		HashMap<String,Object> data=new HashMap<String,Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(res > 0 && res < 5){
			//处理数据
			String[] cardsString=new String[cardNum.getValue()];
			String[] cardsTime=new String[cardNum.getValue()];
			for(int j = 0,i = 0;i < dataLen.getValue() && j < cardNum.getValue();){
				byte[] cards = new byte[epcData[i]];
				System.arraycopy(epcData,i+1,cards,0,cards.length);
				cardsString[j]=bin2hex(cards);
				String nowTime = formatter.format(new Date());
				cardsTime[j]=nowTime;
				j++;
				i+=epcData[i];
				i++;
			}
	
			data.put("cards", cardsString);
			data.put("cardsTime", cardsTime);
			data.put("cardNum", cardNum.getValue());
			data.put("dataLen", dataLen.getValue());
			
			/*System.out.println("");
			System.out.println("cardNum:"+cardNum.getValue());
			System.out.println("dataLen:"+dataLen.getValue());	*/
		}
		else 
			System.out.printf("Failed! res:%02X",res);
		
		return data;
	}

	public HashMap<String,Object> queryActiveTags(int area) {
		// TODO Auto-generated method stub
		HashMap<String,Object> cardData=null;
		if(area==0) cardData=queryActiveTagsEPC();
		else ;
		return cardData;
	}
	
	/**
     * byte[] 转换成十六进制值显示
     * @param bin String 我们看到的要转换成十六进制的字符串
     * @return 
     */
    public  String bin2hex(byte[] bin) {
        char[] digital = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder(bin.length*2);
        int bit;
        for (int i = 0; i < bin.length; i++) {
            bit = (bin[i] & 0x0f0) >> 4;
            sb.append(digital[bit]);
            bit = bin[i] & 0x0f;
            sb.append(digital[bit]);
        }
        return sb.toString();
    }


}
