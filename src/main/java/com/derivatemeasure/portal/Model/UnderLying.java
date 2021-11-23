package com.derivatemeasure.portal.Model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnderLying implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String u_isin;
	private Double ask;
	private Double bid;
	
	public UnderLying(String u_isin, Double ask, Double bid) {
		this.u_isin = u_isin;
		this.ask = ask;
		this.bid = bid;
	}

	public String getU_isin() {
		return u_isin;
	}
	public void setU_isin(String u_isin) {
		this.u_isin = u_isin;
	}
	public Double getAsk() {
		return ask;
	}
	public void setAsk(Double ask) {
		this.ask = ask;
	}
	public Double getBid() {
		return bid;
	}
	public void setBid(Double bid) {
		this.bid = bid;
	}
}
