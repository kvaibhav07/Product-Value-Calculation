package com.derivatemeasure.portal.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeerGroup implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String wkn;
    private List<String> peers;
    
	public PeerGroup(String wkn, List<String> peers) {
		super();
		this.wkn = wkn;
		this.peers = peers;
	}

	public String getWkn() {
		return wkn;
	}
	public void setWkn(String wkn) {
		this.wkn = wkn;
	}
	public List<String> getPeers() {
		return peers;
	}
	public void setPeers(List<String> peers) {
		this.peers = peers;
	}
}
