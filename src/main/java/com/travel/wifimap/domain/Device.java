package com.travel.wifimap.domain;


//@Entity
//@Table(name = "T_DEVICE")
public class Device extends BaseEntity {
	// ?oauthAccessToken=TOKEN&expirationDate=UNIXTIMESTAMP&vendor=Apple&device=iPad&deviceVer=4S&os=iOS&osVer=5.1.0

	// @JoinColumn(name = "USER_ID")
	// @ManyToOne(fetch = FetchType.EAGER)
	public User user;

	// @Column(name = "VENDOR")
	public String vendor;

	// @Column(name = "DEVICE")
	public String device;

	// @Column(name = "DEVICE_VER")
	public String deviceVer;

	// @Column(name = "OS")
	public String os;

	// @Column(name = "OS_VER")
	public String osVer;

	public Device() {
		super();
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getDeviceVer() {
		return deviceVer;
	}

	public void setDeviceVer(String deviceVer) {
		this.deviceVer = deviceVer;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVer() {
		return osVer;
	}

	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}

}
