package java.servlet.service.util.dictionnary;

/**
 * 启用状态
 * @author lichenyi
 *
 * 2017年7月17日 下午3:01:57
 */
public enum EnableEnum {
	/**
	 * 启用
	 */
	enable(1, "启用"),
	/**
	 * 不启用（伪删除）
	 */
	disable(0, "不启用（伪删除）");
	
	private int code;
	private String description;
	
	EnableEnum(int code, String descption){
		this.code = code;
		this.description = descption;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static EnableEnum getEnableEnum(int code){
		for (EnableEnum enableEnum : EnableEnum.values()){
			if(code == enableEnum.getCode()){
				return enableEnum;
			}
		}
		return null;
	}
}
