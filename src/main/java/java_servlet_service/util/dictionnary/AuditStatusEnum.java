package java_servlet_service.util.dictionnary;

/**
 * 审核状态
 * @author lichenyi
 *
 * 2017年7月17日 下午3:02:08
 */
public enum AuditStatusEnum {
	/**
	 * 审核中
	 */
	audting(1, "审核中"),
	/**
	 * 审核通过
	 */
	audted(2, "审核通过");
	
	private int code;
	private String description;
	
	AuditStatusEnum(int code, String descption){
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

	public static AuditStatusEnum getAuditStatus(String code){
		for(AuditStatusEnum auditStatusEnum : AuditStatusEnum.values()){
			if(code.equals(auditStatusEnum.getCode()+"")){
				return auditStatusEnum;
			}
		}
		return null;
	}
	
}
