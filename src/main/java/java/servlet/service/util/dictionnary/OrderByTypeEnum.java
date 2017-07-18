package java.servlet.service.util.dictionnary;

public enum OrderByTypeEnum {
	/**
	 * 时间
	 */
	create_time(1, "create_time"),
	/**
	 * 点赞数量
	 */
	fabulous_count(2, "fabulous_count");
	
	private int code;
	private String description;
	
	OrderByTypeEnum(int code, String descption){
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
	public static OrderByTypeEnum getOrderByTypeEnum(int code){
		for (OrderByTypeEnum orderByTypeEnum : OrderByTypeEnum.values()){
			if(code == orderByTypeEnum.getCode()){
				return orderByTypeEnum;
			}
		}
		return null;
	}
}
