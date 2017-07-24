package com.yn.vo;

import javax.validation.constraints.NotNull;

import com.yn.domain.QueryVo;
import lombok.Data;

/**
 * 选配项目
 */
@Data
public class ApolegamyVo extends QueryVo{
	
	private Long id;
	@NotNull(message = "apolegamyName不能为空")
	private String apolegamyName;
	@NotNull(message = "price不能为空")
	private Double price;
	@NotNull(message = "imgUrl不能为空")
	private String imgUrl;
	@NotNull(message = "iconUrl不能为空")
	private String iconUrl;
	private String unit;
	private Integer type;
	private Long qualificationsId;
}
