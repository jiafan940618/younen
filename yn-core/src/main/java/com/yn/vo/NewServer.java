package com.yn.vo;

import java.util.Set;


public class NewServer {
	
	    private Integer id;
	    
	    private String companyLogo;

	    private String companyName;
	
	    private Set<NewServerPlan02> newServerPlan02;

		

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getCompanyLogo() {
			return companyLogo;
		}

		public void setCompanyLogo(String companyLogo) {
			this.companyLogo = companyLogo;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public Set<NewServerPlan02> getNewServerPlan02() {
			return newServerPlan02;
		}

		public void setNewServerPlan02(Set<NewServerPlan02> newServerPlan02) {
			this.newServerPlan02 = newServerPlan02;
		}

	
	    
	    
}
