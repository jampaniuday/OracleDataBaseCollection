import java.net.InetAddress;
import java.net.UnknownHostException;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.Swap;

public class SystemInfo {
	// 1.CPU��Դ��Ϣ
	// a)CPU��������λ������
	public static int getCpuCount() throws SigarException {
		Sigar sigar = new Sigar();
		try {
			return sigar.getCpuInfoList().length;
		} finally {
			sigar.close();
		}
	}

	
	public int getCpuMhz() {
		Sigar sigar = new Sigar();
				
			CpuInfo[] infos=null;
			try{
			infos = sigar.getCpuInfoList();
		} catch (SigarException e) {
			e.printStackTrace();
		}
			CpuInfo info = infos[0];
		  System.out.println("mhz=" + info.getMhz());// CPU������Mhz
			return info.getMhz();	  				
	}

  public String getCpuVendor() {
		Sigar sigar = new Sigar();
				
			CpuInfo[] infos=null;
			try{
			infos = sigar.getCpuInfoList();
		} catch (SigarException e) {
			e.printStackTrace();
		}
			CpuInfo info = infos[0];
		  System.out.println("Vendor=" + info.getVendor());// CPU��������
			return info.getVendor();	  				
	}
	
	public String getCpuModel() {
		Sigar sigar = new Sigar();
				
			CpuInfo[] infos=null;
			try{
			infos = sigar.getCpuInfoList();
		} catch (SigarException e) {
			e.printStackTrace();
		}
			CpuInfo info = infos[0];
		  System.out.println("Model=" + info.getModel());// CPU��������
			return info.getModel();	  				
	}
	
	// b)CPU����������λ��HZ����CPU�������Ϣ
	public void getCpuTotal() {
		Sigar sigar = new Sigar();
		CpuInfo[] infos;
		try {
			infos = sigar.getCpuInfoList();
			for (int i = 0; i < infos.length; i++) {// �����ǵ���CPU���Ƕ�CPU������
				CpuInfo info = infos[i];
				System.out.println("mhz=" + info.getMhz());// CPU������MHz
				System.out.println("vendor=" + info.getVendor());// ���CPU���������磺Intel
				System.out.println("model=" + info.getModel());// ���CPU������磺Celeron
				System.out.println("cache size=" + info.getCacheSize());// ����洢������
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}

	// c)CPU���û�ʹ������ϵͳʹ��ʣ�������ܵ�ʣ�������ܵ�ʹ��ռ�����ȣ���λ��100%��
	public void testCpuPerc() {
		Sigar sigar = new Sigar();
		// ��ʽһ����Ҫ�����һ��CPU�����
		CpuPerc cpu;
		try {
			cpu = sigar.getCpuPerc();
			printCpuPerc(cpu);
		} catch (SigarException e) {
			e.printStackTrace();
		}
		// ��ʽ���������ǵ���CPU���Ƕ�CPU������
		CpuPerc cpuList[] = null;
		try {
			cpuList = sigar.getCpuPercList();
		} catch (SigarException e) {
			e.printStackTrace();
			return;
		}
		for (int i = 0; i < cpuList.length; i++) {
			printCpuPerc(cpuList[i]);
		}
	}

public String[] getCpuPerc() {
		Sigar sigar = new Sigar();
		// ��ʽһ����Ҫ�����һ��CPU�����
		CpuPerc cpu;
		String[] cpuperf = new String[6];
		
		try {
			cpu = sigar.getCpuPerc();
			
			 cpuperf[0]= CpuPerc.format(cpu.getUser());
			 cpuperf[1]= CpuPerc.format(cpu.getSys());// ϵͳʹ����
		   cpuperf[2]= CpuPerc.format(cpu.getWait());// ��ǰ�ȴ���
		   cpuperf[3]= CpuPerc.format(cpu.getNice());//
		   cpuperf[4]= CpuPerc.format(cpu.getIdle());// ��ǰ������
		   cpuperf[5]= CpuPerc.format(cpu.getCombined());// �ܵ�ʹ����
		   
		System.out.println("User :" + CpuPerc.format(cpu.getUser()));// �û�ʹ����
		System.out.println("Sys :" + CpuPerc.format(cpu.getSys()));// ϵͳʹ����
		System.out.println("Wait :" + CpuPerc.format(cpu.getWait()));// ��ǰ�ȴ���
		System.out.println("Nice :" + CpuPerc.format(cpu.getNice()));//
		System.out.println("Idle :" + CpuPerc.format(cpu.getIdle()));// ��ǰ������
		System.out.println("Total :" + CpuPerc.format(cpu.getCombined()));// �ܵ�ʹ����
			
			
			
		} catch (SigarException e) {
			e.printStackTrace();
		}		
		return cpuperf;
	}
	
	private void printCpuPerc(CpuPerc cpu) {
		System.out.println("User :" + CpuPerc.format(cpu.getUser()));// �û�ʹ����
		System.out.println("Sys :" + CpuPerc.format(cpu.getSys()));// ϵͳʹ����
		System.out.println("Wait :" + CpuPerc.format(cpu.getWait()));// ��ǰ�ȴ���
		System.out.println("Nice :" + CpuPerc.format(cpu.getNice()));//
		System.out.println("Idle :" + CpuPerc.format(cpu.getIdle()));// ��ǰ������
		System.out.println("Total :" + CpuPerc.format(cpu.getCombined()));// �ܵ�ʹ����
	}

	// 2.�ڴ���Դ��Ϣ
	public String[] getPhysicalMemory() {
		// a)�����ڴ���Ϣ
		Sigar sigar = new Sigar();
		Mem mem;
		String[] memperf = new String[8];
		try {
			mem = sigar.getMem();
			// �ڴ�����
			System.out.println("Total = " + mem.getTotal() / 1024L + "K av");
			memperf[0] = mem.getTotal()+"";
			
			// ��ǰ�ڴ�ʹ����
			System.out.println("Used = " + mem.getUsed() / 1024L + "K used");
			memperf[1] = mem.getUsed()+"";
			
			// ��ǰ�ڴ�ʣ����
			System.out.println("Free = " + mem.getFree() / 1024L + "K free");
			memperf[2] = mem.getFree()+"";
			
			// b)ϵͳҳ���ļ���������Ϣ
			Swap swap = sigar.getSwap();
			// ����������
			System.out.println("Total = " + swap.getTotal() / 1024L + "K av");
			memperf[3] = swap.getTotal() +"";
			
			// ��ǰ������ʹ����
			System.out.println("Used = " + swap.getUsed() / 1024L + "K used");
			memperf[4] = swap.getUsed() +"";
			
			// ��ǰ������ʣ����
			System.out.println("Free = " + swap.getFree() / 1024L + "K free");
			memperf[5] = swap.getFree()+"";
			
			System.out.println("PageIn = " + swap.getPageIn());
			memperf[6] = swap.getPageIn()+"";
			
			System.out.println("PageOut = " + swap.getPageOut());
			memperf[7] = swap.getPageOut()+"";
			
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return memperf;
	}

  public long getTotalMemory() {
		// a)�����ڴ���Ϣ
		Sigar sigar = new Sigar();
		Mem mem= null;
		try {
			mem = sigar.getMem();
			// �ڴ�����
			System.out.println("Total RAM = " + mem.getTotal() / 1024L);
			
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return mem.getTotal();
	}

  public long getTotalSwap() {
		// a)�����ڴ���Ϣ
		Sigar sigar = new Sigar();
		Swap swap= null;
		try {
			swap = sigar.getSwap();
			// �ڴ�����
			System.out.println("Total Swap= " + swap.getTotal() / 1024L);
			
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return swap.getTotal();
	}


	// 3.����ϵͳ��Ϣ
	// a)ȡ��������
	public String getHostName() {
		String hostname = "";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (Exception exc) {
			Sigar sigar = new Sigar();
			try {
				hostname = sigar.getNetInfo().getHostName();
			} catch (SigarException e) {
				hostname = "localhost.unknown";
			} finally {
				sigar.close();
			}
		}
		System.out.println(hostname);
		return hostname;
	}

	// b)ȡ��ǰ����ϵͳ����Ϣ
	
	public String[] testGetOSInfo() {
		OperatingSystem OS = OperatingSystem.getInstance();
		// ����ϵͳ�ں������磺 386��486��586��x86
		String[] OSInfo = new String[12];
		 
		OSInfo[0] = OS.getArch();
		System.out.println("OS.getArch() = " + OS.getArch());
		
		OSInfo[1] = OS.getCpuEndian();
		System.out.println("OS.getCpuEndian() = " + OS.getCpuEndian());
		
		OSInfo[2] = OS.getDataModel();
		System.out.println("OS.getDataModel() = " + OS.getDataModel());
		
		// ϵͳ����
		OSInfo[3] = OS.getDescription();
		System.out.println("OS.getDescription() = " + OS.getDescription());
		
		OSInfo[4] = OS.getMachine();
		System.out.println("OS.getMachine() = " + OS.getMachine());
		
		// ����ϵͳ����
		OSInfo[5] = OS.getName();
		System.out.println("OS.getName() = " + OS.getName());
		
		//����ϵͳ������
		OSInfo[6] = OS.getPatchLevel();
		System.out.println("OS.getPatchLevel() = " + OS.getPatchLevel());
		
		// ����ϵͳ������
		OSInfo[7] = OS.getVendor();
		System.out.println("OS.getVendor() = " + OS.getVendor());
		
		// ��������
		OSInfo[8] = OS.getVendorCodeName();
		System.out.println("OS.getVendorCodeName() = " + OS.getVendorCodeName());
		
		// ����ϵͳ����
		OSInfo[9] = OS.getVendorName();
		System.out.println("OS.getVendorName() = " + OS.getVendorName());
		
		// ����ϵͳ�����汾
		OSInfo[10] = OS.getVendorVersion();
		System.out.println("OS.getVendorVersion() = " + OS.getVendorVersion());
		
		// ����ϵͳ�İ汾��
		OSInfo[11] = OS.getVersion();
		System.out.println("OS.getVersion() = " + OS.getVersion());
		return OSInfo;
	}

	// c)ȡ��ǰϵͳ���̱��е��û���Ϣ
	public void testWho() {
		try {
			Sigar sigar = new Sigar();
			org.hyperic.sigar.Who[] who = sigar.getWhoList();
			if (who != null && who.length > 0) {
				for (int i = 0; i < who.length; i++) {
					System.out.println("\n~~~~~~~~~" + String.valueOf(i) + "~~~~~~~~~");
					org.hyperic.sigar.Who _who = who[i];
					System.out.println("getDevice() = " + _who.getDevice());
					System.out.println("getHost() = " + _who.getHost());
					System.out.println("getTime() = " + _who.getTime());
					// ��ǰϵͳ���̱��е��û���
					System.out.println("getUser() = " + _who.getUser());
				}
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}

	// 4.��Դ��Ϣ����Ҫ��Ӳ�̣�
	// a)ȡӲ�����еķ���������ϸ��Ϣ��ͨ��sigar.getFileSystemList()�����FileSystem�б����Ȼ�������б�������
	public void testFileSystemInfo() throws Exception {
		Sigar sigar = new Sigar();
		FileSystem fslist[] = sigar.getFileSystemList();
		// String dir = System.getProperty("user.home");// ��ǰ�û��ļ���·��
		for (int i = 0; i < fslist.length; i++) {
			System.out.println("\n~~~~~~~~~~" + i + "~~~~~~~~~~");
			FileSystem fs = fslist[i];
			// �������̷�����
			System.out.println("fs.getDevName() = " + fs.getDevName());
			// �������̷�����
			System.out.println("fs.getDirName() = " + fs.getDirName());
			System.out.println("fs.getFlags() = " + fs.getFlags());//
			// �ļ�ϵͳ���ͣ����� FAT32��NTFS
			System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());
			// �ļ�ϵͳ�����������籾��Ӳ�̡������������ļ�ϵͳ��
			System.out.println("fs.getTypeName() = " + fs.getTypeName());
			// �ļ�ϵͳ����
			System.out.println("fs.getType() = " + fs.getType());
			
			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
			} catch (SigarException e) {
				if (fs.getType() == 2)
					throw e;
				continue;
			}
			switch (fs.getType()) {
			case 0: // TYPE_UNKNOWN ��δ֪
				break;
			case 1: // TYPE_NONE
				break;
			case 2: // TYPE_LOCAL_DISK : ����Ӳ��
				// �ļ�ϵͳ�ܴ�С
				System.out.println(" Total = " + usage.getTotal() + "KB");
				// �ļ�ϵͳʣ���С
				System.out.println(" Free = " + usage.getFree() + "KB");
				// �ļ�ϵͳ���ô�С
				System.out.println(" Avail = " + usage.getAvail() + "KB");
				// �ļ�ϵͳ�Ѿ�ʹ����
				System.out.println(" Used = " + usage.getUsed() + "KB");
				double usePercent = usage.getUsePercent() * 100D;
				// �ļ�ϵͳ��Դ��������
				System.out.println(" Usage = " + usePercent + "%");
				break;
			case 3:// TYPE_NETWORK ������
				break;
			case 4:// TYPE_RAM_DISK ������
				break;
			case 5:// TYPE_CDROM ������
				break;
			case 6:// TYPE_SWAP ��ҳ�潻��
				break;
			}
			System.out.println(" DiskReads = " + usage.getDiskReads());
			System.out.println(" DiskWrites = " + usage.getDiskWrites());
		}
		return;
	}

public String[][] getFileSystemInfo() throws Exception {
		Sigar sigar = new Sigar();
		FileSystem fslist[] = sigar.getFileSystemList();
		// String dir = System.getProperty("user.home");// ��ǰ�û��ļ���·��
		
		String[][] v_fs = new String[fslist.length][11];
		
		for (int i = 0; i < fslist.length; i++) {
									
			FileSystem fs = fslist[i];
      
      //if (fs.getType() == 2) {
      	
      System.out.println("\n~~~~~~~~~~" + i + "~~~~~~~~~~");

			// �������̷�����
			System.out.println("fs.getDevName() = " + fs.getDevName());
			v_fs[i][0] = fs.getDevName();
			
			// ������Ŀ¼��
			System.out.println("fs.getDirName() = " + fs.getDirName());
			v_fs[i][1] = fs.getDirName();
			
			//��֪��ʲô��־
			System.out.println("fs.getFlags() = " + fs.getFlags());//
			v_fs[i][2] = fs.getFlags()+"";
			
			// �ļ�ϵͳ���ͣ����� FAT32��NTFS
			System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());
			v_fs[i][3] = fs.getSysTypeName();
			
			// �ļ�ϵͳ�����������籾��Ӳ�̡������������ļ�ϵͳ��
			System.out.println("fs.getTypeName() = " + fs.getTypeName());
			v_fs[i][4] = fs.getTypeName();

			// �ļ�ϵͳ����
			System.out.println("fs.getType() = " + fs.getType());
			v_fs[i][5] = fs.getType()+"";
			
			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
			} catch (SigarException e) {
				if (fs.getType() == 2)
					throw e;
				continue;
			}
			switch (fs.getType()) {
			case 0: // TYPE_UNKNOWN ��δ֪
				break;
			case 1: // TYPE_NONE
				break;
			case 2: // TYPE_LOCAL_DISK : ����Ӳ��
				// �ļ�ϵͳ�ܴ�С
				System.out.println(" Total = " + usage.getTotal() + "KB");
				v_fs[i][6] = usage.getTotal()+"";
				
				// �ļ�ϵͳʣ���С
				System.out.println(" Free = " + usage.getFree() + "KB");
				v_fs[i][7] = usage.getFree()+"";
				
				// �ļ�ϵͳ���ô�С
				System.out.println(" Avail = " + usage.getAvail() + "KB");
				v_fs[i][8] = usage.getAvail()+"";
				
				// �ļ�ϵͳ�Ѿ�ʹ����
				System.out.println(" Used = " + usage.getUsed() + "KB");
				v_fs[i][9] = usage.getUsed()+"";
				
				double usePercent = usage.getUsePercent() * 100D;
				// �ļ�ϵͳ��Դ��������
				System.out.println(" Usage = " + usePercent + "%");
				v_fs[i][10] = usage.getUsePercent()+"";
				
				break;
			case 3:// TYPE_NETWORK ������
				break;
			case 4:// TYPE_RAM_DISK ������
				break;
			case 5:// TYPE_CDROM ������
				break;
			case 6:// TYPE_SWAP ��ҳ�潻��
				break;
			  }
			//System.out.println(" DiskReads = " + usage.getDiskReads());
			//System.out.println(" DiskWrites = " + usage.getDiskWrites());
		}
		return v_fs;
	}


	// 5.������Ϣ
	// a)��ǰ��������ʽ����
	public String getFQDN() {
		Sigar sigar = null;
		try {
			System.out.println(InetAddress.getLocalHost().getCanonicalHostName());
			return InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			try {
				sigar = new Sigar();
				System.out.println(sigar.getFQDN());
				return sigar.getFQDN();
			} catch (SigarException ex) {
				return null;
			} finally {
				sigar.close();
			}
		}
	}

	// b)ȡ����ǰ������IP��ַ
	public String getDefaultIpAddress() {
		String address = null;
		try {
			
			address = InetAddress.getLocalHost().getHostAddress();
			
			// û�г����쳣��������ȡ����IPʱ�����ȡ���Ĳ�������ѭ�ص�ַʱ�ͷ���
			// ������ͨ��Sigar���߰��еķ�������ȡ
			if (!NetFlags.LOOPBACK_ADDRESS.equals(address)) {
				System.out.println(address);
				return address;
			}
		} catch (UnknownHostException e) {
			// hostname not in DNS or /etc/hosts
		}
		Sigar sigar = new Sigar();
		try {
			address = sigar.getNetInterfaceConfig().getAddress();
		} catch (SigarException e) {
			address = NetFlags.LOOPBACK_ADDRESS;
		} finally {
			sigar.close();
		}
		System.out.println(address);
		return address;
		
	}

	// c)ȡ����ǰ������MAC��ַ
	public String getMAC() {
		Sigar sigar = null;
		try {
			sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			String hwaddr = null;
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				/*
				 * ������ڶ������������������������Ĭ��ֻȡ��һ��������MAC��ַ�����Ҫ�������е���������������ĺ�����ģ�������޸ķ����ķ�������Ϊ�����Collection
				 * ��ͨ����forѭ����ȡ���Ķ��MAC��ַ��
				 */
				hwaddr = cfg.getHwaddr();
				break;
			}
			return hwaddr != null ? hwaddr : null;
		} catch (Exception e) {
			return null;
		} finally {
			if (sigar != null)
				sigar.close();
		}
	}

	// d)��ȡ������������Ϣ
	public void testNetIfList() throws Exception {
		Sigar sigar = new Sigar();
		String ifNames[] = sigar.getNetInterfaceList();
		for (int i = 0; i < ifNames.length; i++) {
			String name = ifNames[i];
			NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
			print("\nname = " + name);// �����豸��
			print("Address = " + ifconfig.getAddress());// IP��ַ
			print("Netmask = " + ifconfig.getNetmask());// ��������
			if ((ifconfig.getFlags() & 1L) <= 0L) {
				print("!IFF_UP...skipping getNetInterfaceStat");
				continue;
			}
			try {
				NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
				print("RxPackets = " + ifstat.getRxPackets());// ���յ��ܰ�����
				print("TxPackets = " + ifstat.getTxPackets());// ���͵��ܰ�����
				print("RxBytes = " + ifstat.getRxBytes());// ���յ������ֽ���
				print("TxBytes = " + ifstat.getTxBytes());// ���͵����ֽ���
				print("RxErrors = " + ifstat.getRxErrors());// ���յ��Ĵ������
				print("TxErrors = " + ifstat.getTxErrors());// �������ݰ�ʱ�Ĵ�����
				print("RxDropped = " + ifstat.getRxDropped());// ����ʱ�����İ���
				print("TxDropped = " + ifstat.getTxDropped());// ����ʱ�����İ���
			} catch (SigarNotImplementedException e) {
			} catch (SigarException e) {
				print(e.getMessage());
			}
		}
	}

	void print(String msg) {
		System.out.println(msg);
	}

	// e)һЩ��������Ϣ
	public void getEthernetInfo() {
		Sigar sigar = null;
		try {
			sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				System.out.println("cfg.getAddress() = " + cfg.getAddress());// IP��ַ
				System.out.println("cfg.getBroadcast() = " + cfg.getBroadcast());// ���ع㲥��ַ
				System.out.println("cfg.getHwaddr() = " + cfg.getHwaddr());// ����MAC��ַ
				System.out.println("cfg.getNetmask() = " + cfg.getNetmask());// ��������
				System.out.println("cfg.getDescription() = " + cfg.getDescription());// ����������Ϣ
				System.out.println("cfg.getType() = " + cfg.getType());//
				System.out.println("cfg.getDestination() = " + cfg.getDestination());
				System.out.println("cfg.getFlags() = " + cfg.getFlags());//
				System.out.println("cfg.getMetric() = " + cfg.getMetric());
				System.out.println("cfg.getMtu() = " + cfg.getMtu());
				System.out.println("cfg.getName() = " + cfg.getName());
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("Error while creating GUID" + e);
		} finally {
			if (sigar != null)
				sigar.close();
		}
	}

	public static void main(String[] arg) {
		try {
			SystemInfo s = new SystemInfo();
//			s.getCpuTotal();
			s.getCpuMhz();
			s.getCpuVendor();
			s.getCpuModel();
			s.getTotalMemory();
			s.getTotalSwap();
			s.getHostName();
//			s.getFQDN();
//			s.getDefaultIpAddress();
			s.getPhysicalMemory();
//			s.testGetOSInfo();
//			s.testWho();
//      s.testCpuPerc();
//			s.getCpuPerc();
			s.getFileSystemInfo();
//			s.testNetIfList();
//			s.getEthernetInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
