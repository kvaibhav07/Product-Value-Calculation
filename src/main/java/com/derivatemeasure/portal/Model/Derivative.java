package com.derivatemeasure.portal.Model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Derivative implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer type;
    private String isin;
    private String wkn;
    private String u_isin;
    private String emi;
    private String cur;
    private Integer quanto;
    private Integer exec;
    private Double bv;
    private Double cap;
    private Date matdate;
    private Date valdate;
    private Double ask;
    private Double bid;
    private Double sidewaysReturnPa;
    
    public Derivative() {}
    
    public Derivative(String wkn, Double ask, Double bid) {
    	this.wkn = wkn;
    	this.ask = ask;
    	this.bid = bid;
    }

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getWkn() {
		return wkn;
	}
	public void setWkn(String wkn) {
		this.wkn = wkn;
	}
	public String getU_isin() {
		return u_isin;
	}
	public void setU_isin(String u_isin) {
		this.u_isin = u_isin;
	}
	public String getEmi() {
		return emi;
	}
	public void setEmi(String emi) {
		this.emi = emi;
	}
	public String getCur() {
		return cur;
	}
	public void setCur(String cur) {
		this.cur = cur;
	}
	public Integer getQuanto() {
		return quanto;
	}
	public void setQuanto(Integer quanto) {
		this.quanto = quanto;
	}
	public Integer getExec() {
		return exec;
	}
	public void setExec(Integer exec) {
		this.exec = exec;
	}
	public Double getBv() {
		return bv;
	}
	public void setBv(Double bv) {
		this.bv = bv;
	}
	public Double getCap() {
		return cap;
	}
	public void setCap(Double cap) {
		this.cap = cap;
	}
	public Date getMatdate() {
		return matdate;
	}
	public void setMatdate(Date matdate) {
		this.matdate = matdate;
	}
	public Date getValdate() {
		return valdate;
	}
	public void setValdate(Date valdate) {
		this.valdate = valdate;
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
	public Double getSidewaysReturnPa() {
		return sidewaysReturnPa;
	}
	public void setSidewaysReturnPa(Double sidewaysReturnPa) {
		this.sidewaysReturnPa = sidewaysReturnPa;
	}
}
