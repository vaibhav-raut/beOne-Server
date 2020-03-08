package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:19 PM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GMId generated by hbm2java
 */
@SuppressWarnings("serial")
@Embeddable

public class GMId  implements java.io.Serializable {

    // Fields    
     private long groupAcNo;
     private long memberAcNo;

    // Constructors

    /** default constructor */
    public GMId() {
    }
    
    /** full constructor */
    public GMId(long groupAcNo, long memberAcNo) {
        this.groupAcNo = groupAcNo;
        this.memberAcNo = memberAcNo;
    }
    @Column(name="g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

    public long getGroupAcNo() {
        return this.groupAcNo;
    }
    
    public void setGroupAcNo(long groupAcNo) {
        this.groupAcNo = groupAcNo;
    }
    
    // Property accessors
    @Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

    public long getMemberAcNo() {
        return this.memberAcNo;
    }
    
    public void setMemberAcNo(long memberAcNo) {
        this.memberAcNo = memberAcNo;
    }

   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof GMId) ) return false;
		 GMId castOther = ( GMId ) other; 
         
		 return (this.getMemberAcNo()==castOther.getMemberAcNo())
 && (this.getGroupAcNo()==castOther.getGroupAcNo());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + (int) this.getMemberAcNo();
         result = 37 * result + (int) this.getGroupAcNo();
         return result;
   }   
}
